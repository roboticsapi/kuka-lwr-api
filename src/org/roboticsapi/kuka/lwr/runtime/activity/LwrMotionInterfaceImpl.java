/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.roboticsapi.activity.AbstractRtActivity;
import org.roboticsapi.activity.Activity;
import org.roboticsapi.activity.ActivityNotCompletedException;
import org.roboticsapi.activity.PlannedRtActivity;
import org.roboticsapi.activity.RtActivity;
import org.roboticsapi.activity.SingleDeviceRtActivity;
import org.roboticsapi.cartesianmotion.activity.CartesianSplineActivity;
import org.roboticsapi.cartesianmotion.parameter.MotionCenterParameter;
import org.roboticsapi.core.Command;
import org.roboticsapi.core.Device;
import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.core.DeviceParameters;
import org.roboticsapi.core.RoboticsRuntime;
import org.roboticsapi.core.SensorState;
import org.roboticsapi.core.TransactionCommand;
import org.roboticsapi.core.actuator.OverrideParameter;
import org.roboticsapi.core.eventhandler.CommandCanceller;
import org.roboticsapi.core.eventhandler.CommandStarter;
import org.roboticsapi.core.eventhandler.ExceptionIgnorer;
import org.roboticsapi.core.eventhandler.ExceptionThrower;
import org.roboticsapi.core.exception.ActionCancelledException;
import org.roboticsapi.core.exception.CommunicationException;
import org.roboticsapi.core.exception.RoboticsException;
import org.roboticsapi.core.sensor.BooleanSensor;
import org.roboticsapi.core.sensor.DoubleSensor;
import org.roboticsapi.kuka.lwr.AbstractLWR;
import org.roboticsapi.kuka.lwr.activity.ForceNotReachedException;
import org.roboticsapi.kuka.lwr.activity.LwrMotionInterface;
import org.roboticsapi.kuka.lwr.controller.ImpedanceSpringSetting;
import org.roboticsapi.kuka.lwr.controller.LwrJointImpedanceController;
import org.roboticsapi.kuka.lwr.parameter.AlphaParameter;
import org.roboticsapi.kuka.lwr.runtime.action.SwitchControllerAction;
import org.roboticsapi.multijoint.IllegalJointValueException;
import org.roboticsapi.multijoint.action.FollowJointGoal;
import org.roboticsapi.multijoint.parameter.Controller;
import org.roboticsapi.multijoint.parameter.ControllerParameter;
import org.roboticsapi.robot.KinematicsException;
import org.roboticsapi.robot.activity.MotionInterfaceImpl;
import org.roboticsapi.world.Frame;
import org.roboticsapi.world.Transformation;

public class LwrMotionInterfaceImpl extends MotionInterfaceImpl<AbstractLWR> implements LwrMotionInterface {

	private final class LinToContactActivity extends AbstractRtActivity {
		private final double contactForceY;
		private final double contactForceZ;
		private final double contactForceX;
		private final RtActivity lin;

		private LinToContactActivity(double contactForceY, double contactForceZ, double contactForceX, RtActivity lin) {
			this.contactForceY = contactForceY;
			this.contactForceZ = contactForceZ;
			this.contactForceX = contactForceX;
			this.lin = lin;
		}

		@Override
		public Set<Device> prepare(Map<Device, Activity> prevActivities)
				throws RoboticsException, ActivityNotCompletedException {
			Set<Device> devicesTakenOver = lin.prepare(prevActivities);

			AbstractLWR device = getDevice();
			BooleanSensor inXRange = device.getForceXSensor().equals(0, contactForceX);
			BooleanSensor inYRange = device.getForceYSensor().equals(0, contactForceY);
			BooleanSensor inZRange = device.getForceZSensor().equals(0, contactForceZ);

			TransactionCommand trans = getDevice().getDriver().getRuntime().createTransactionCommand(lin.getCommand());

			trans.addStartCommand(lin.getCommand());

			SensorState contactForceReached = inXRange.and(inYRange, inZRange).isFalse();

			trans.addDoneStateCondition(contactForceReached);

			trans.addStateFirstEnteredHandler(lin.getCompletedState(), new ExceptionThrower(
					getDevice().getDriver().defineActuatorDriverException(ForceNotReachedException.class)));

			setCommand(trans, prevActivities);

			ControllerProperty cProp = lin.getProperty(getDevice(), ControllerProperty.class);

			if (cProp != null) {
				addProperty(getDevice(), cProp);
			}

			return devicesTakenOver;
		}

		@Override
		public List<Device> getControlledDevices() {
			return lin.getControlledDevices();
		}

		@Override
		public List<Device> getAffectedDevices() {
			return lin.getAffectedDevices();
		}

		@Override
		public RoboticsRuntime getRuntime() {
			return getDevice().getDriver().getRuntime();
		}
	}

	public LwrMotionInterfaceImpl(AbstractLWR robot) {
		super(robot);
	}

	@Override
	public PlannedRtActivity ptp(double[] to, DeviceParameters... parameters) throws RoboticsException {
		PlannedRtActivity ptp = super.ptp(to, parameters);

		return new LwrMotionProgressActivity(getDevice(), ptp, getDefaultParameters().withParameters(parameters));
	}

	@Override
	protected double[] calculateInverseKinematics(Transformation point, double[] hintJoints, DeviceParameterBag params)
			throws KinematicsException, CommunicationException, RoboticsException {

		AlphaParameter alphaParameter = params.get(AlphaParameter.class);

		Double alpha = alphaParameter == null ? null : alphaParameter.getAlpha();

		return getDevice().getInverseKinematics(point, hintJoints, alpha, params);
	}

	@Override
	public PlannedRtActivity lin(Frame to, double[] nullspaceJoints, double speedFactor, DeviceParameters... parameters)
			throws RoboticsException {
		PlannedRtActivity lin = super.lin(to, nullspaceJoints, speedFactor, parameters);
		AbstractLWR device = getDevice();
		return new LwrMotionProgressActivity(device, lin, getDefaultParameters().withParameters(parameters));
	}

	@Override
	public CartesianSplineActivity spline(DeviceParameters[] parameters, Frame splinePoint,
			Frame... furtherSplinePoints) throws RoboticsException {
		CartesianSplineActivity spline = super.spline(parameters, splinePoint, furtherSplinePoints);
		AbstractLWR device = getDevice();
		return new LwrCartesianSplineActivity(device, spline, getDefaultParameters().withParameters(parameters));
	}

	@Override
	public RtActivity gravComp(DeviceParameters... parameters) throws RoboticsException {
		Command toJointImpedance;
		Command followPosition;
		Command toPosition;

		// parameter for GravComp
		ImpedanceSpringSetting spring = new ImpedanceSpringSetting(0.01, 1);
		Controller jntImpController = new LwrJointImpedanceController(spring, spring, spring, spring, spring, spring,
				spring);

		toJointImpedance = getDevice().getDriver().getRuntime().createRuntimeCommand(getDevice(),
				new SwitchControllerAction(jntImpController), getDefaultParameters());

		// OTG should follow the measured sensor values
		DoubleSensor sensors[] = new DoubleSensor[getDevice().getJointCount()];
		for (int i = 0; i < getDevice().getJointCount(); i++) {
			sensors[i] = getDevice().getJoint(i).getDriver().getMeasuredPositionSensor();
		}

		followPosition = getDevice().getDriver().getRuntime().createRuntimeCommand(getDevice(),
				new FollowJointGoal(sensors),
				getDefaultParameters().withParameters(OverrideParameter.MAXIMUM).withParameters(parameters));

		followPosition.addExceptionHandler(IllegalJointValueException.class, new ExceptionIgnorer());

		followPosition.addExceptionHandler(ActionCancelledException.class, new ExceptionIgnorer(), true);

		Controller defaultController = getDefaultParameters().get(ControllerParameter.class).getController();

		toPosition = getDevice().getDriver().getRuntime().createRuntimeCommand(getDevice(),
				new SwitchControllerAction(defaultController), getDefaultParameters());

		TransactionCommand cmd = getDevice().getDriver().getRuntime().createTransactionCommand(toJointImpedance);
		cmd.addStartCommand(toJointImpedance);

		cmd.addCommand(followPosition);
		cmd.addStateFirstEnteredHandler(toJointImpedance.getCompletedState(), new CommandStarter(followPosition));

		cmd.addCommand(toPosition);
		cmd.addStateFirstEnteredHandler(followPosition.getCompletedState(), new CommandStarter(toPosition));

		// cancel of the transaction cancels only the FollowOTG Command, but
		// never the switch commands
		cmd.addStateFirstEnteredHandler(cmd.getCancelState(), new CommandCanceller(followPosition));

		final AbstractLWR device = getDevice();
		return new SingleDeviceRtActivity<AbstractLWR>(device, cmd, null) {

			@Override
			protected boolean prepare(Activity prevActivity) throws RoboticsException, ActivityNotCompletedException {
				return false;
			}
		};
	}

	@Override
	public final RtActivity linToContact(Frame to, final double contactForce, DeviceParameters... parameters)
			throws RoboticsException {
		return linToContact(to, contactForce, 1, parameters);

	}

	@Override
	public final RtActivity linToContact(Frame to, double contactForce, double speedFactor,
			DeviceParameters... parameters) throws RoboticsException {
		return linToContact(to, contactForce, contactForce, contactForce, speedFactor, parameters);
	}

	@Override
	public final RtActivity linToContact(Frame to, final double contactForceX, final double contactForceY,
			final double contactForceZ, DeviceParameters... parameters) throws RoboticsException {
		return linToContact(to, contactForceX, contactForceY, contactForceZ, 1, parameters);
	}

	@Override
	public RtActivity linToContact(Frame to, final double contactForceX, final double contactForceY,
			final double contactForceZ, double speedFactor, DeviceParameters... parameters) throws RoboticsException {
		// standard lin without controller switch
		final RtActivity lin = super.lin(to, getHintJointsFromFrame(to, parameters), speedFactor, parameters);

		// wrapping contact handling around standard lin
		AbstractRtActivity linToContact = new LinToContactActivity(contactForceY, contactForceZ, contactForceX, lin);

		AbstractLWR device = getDevice();
		// wrapping result in LwrMotionActivity for controller handling
		return new LwrMotionActivity(device, linToContact, getDefaultParameters().withParameters(parameters));

	}

	@Override
	public RtActivity releaseForce(final DeviceParameters... parameters) throws RoboticsException {

		return new AbstractRtActivity("LwrMotionInterfaceImpl#releaseForce") {

			@Override
			public List<Device> getControlledDevices() {
				List<Device> result = new ArrayList<Device>();
				result.add(getDevice());
				return result;
			}

			@Override
			public Set<Device> prepare(Map<Device, Activity> prevActivities)
					throws RoboticsException, ActivityNotCompletedException {
				DeviceParameterBag givenParams = new DeviceParameterBag().withParameters(parameters);

				ControllerParameter controllerParameter = givenParams.get(ControllerParameter.class);

				Controller controller = controllerParameter == null ? null : controllerParameter.getController();

				if (controller == null) {
					Activity activity = prevActivities.get(getDevice());

					if (activity == null) {
						throw new RoboticsException(
								"Could not determine previously used controller (no previous activity for device)");
					}

					ControllerProperty controllerProp = activity.getProperty(getDevice(), ControllerProperty.class);

					if (controllerProp == null) {
						throw new RoboticsException(
								"Could not determine previously used controller (ControllerProperty not set)");
					}

					controller = controllerProp.getController();

					if (controller == null) {
						throw new RoboticsException(
								"Could not determine previously used controller (ControllerProperty has null Controller)");
					}
				}

				DeviceParameterBag params = getDefaultParameters()
						.withParameters(new ControllerParameter(controller), OverrideParameter.MAXIMUM)
						.withParameters(parameters);

				MotionCenterParameter mcp = params.get(MotionCenterParameter.class);

				if (mcp == null) {
					throw new RoboticsException("No motion center specified");
				}

				Transformation measuredBaseToMC = getDevice().getBase()
						.getMeasuredTransformationTo(mcp.getMotionCenter());

				List<DeviceParameters> paramsList = params.getAll();
				DeviceParameters[] paramsArray = paramsList.toArray(new DeviceParameters[paramsList.size()]);

				final RtActivity lin = lin(getDevice().getBase().plus(measuredBaseToMC), paramsArray);

				Set<Device> prepare = lin.prepare(prevActivities);

				TransactionCommand trans = getDevice().getDriver().getRuntime().createTransactionCommand();

				trans.addStartCommand(lin.getCommand());

				// TODO: add/propagate states?

				setCommand(trans, prevActivities);

				addProperty(getDevice(), new ControllerProperty(controller));

				return prepare;
			}

			@Override
			public RoboticsRuntime getRuntime() {
				return getDevice().getDriver().getRuntime();
			}
		};
	}

}
