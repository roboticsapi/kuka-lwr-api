/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.runtime.rpi.activity;

import java.util.List;

import org.roboticsapi.core.Command;
import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.core.DeviceParameters;
import org.roboticsapi.core.activity.Activities;
import org.roboticsapi.core.activity.Activity;
import org.roboticsapi.core.activity.runtime.FromCommandActivity;
import org.roboticsapi.core.actuator.OverrideParameter;
import org.roboticsapi.core.exception.RoboticsException;
import org.roboticsapi.core.realtimevalue.realtimeboolean.RealtimeBoolean;
import org.roboticsapi.core.realtimevalue.realtimedouble.RealtimeDouble;
import org.roboticsapi.core.world.Pose;
import org.roboticsapi.core.world.Transformation;
import org.roboticsapi.device.kuka.lwr.AbstractLwr;
import org.roboticsapi.device.kuka.lwr.activity.LwrMotionInterface;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.driver.LwrGenericDriver;
import org.roboticsapi.framework.cartesianmotion.activity.CartesianControllerInterface;
import org.roboticsapi.framework.cartesianmotion.controller.CartesianController;
import org.roboticsapi.framework.cartesianmotion.parameter.MotionCenterParameter;
import org.roboticsapi.framework.multijoint.IllegalJointValueException;
import org.roboticsapi.framework.multijoint.action.FollowJointGoal;
import org.roboticsapi.framework.multijoint.activity.JointControllerInterface;
import org.roboticsapi.framework.multijoint.controller.JointController;
import org.roboticsapi.framework.multijoint.controller.JointImpedanceController;
import org.roboticsapi.framework.multijoint.controller.JointImpedanceSettings;
import org.roboticsapi.framework.multijoint.controller.JointPositionController;
import org.roboticsapi.framework.robot.runtime.rpi.activity.MotionInterfaceImpl;

public class LwrMotionInterfaceImpl extends MotionInterfaceImpl implements LwrMotionInterface {

	private final AbstractLwr lwr;

	public LwrMotionInterfaceImpl(LwrGenericDriver driver) {
		super(driver);
		this.lwr = driver.getDevice();
	}

	@Override
	public Activity gravComp(DeviceParameters... parameters) throws RoboticsException {
		Activity switchActivity = getDevice().use(JointControllerInterface.class)
				.switchJointController(new JointPositionController());

		return createGravComp(switchActivity, parameters);
	}

	@Override
	public Activity gravComp(JointController finalController, DeviceParameters... parameters) throws RoboticsException {
		Activity switchActivity = getDevice().use(JointControllerInterface.class)
				.switchJointController(finalController);

		return createGravComp(switchActivity, parameters);
	}

	@Override
	public Activity gravComp(CartesianController finalController, DeviceParameters... parameters)
			throws RoboticsException {
		Activity switchActivity = getDevice().use(CartesianControllerInterface.class)
				.switchCartesianController(finalController);

		return createGravComp(switchActivity, parameters);
	}

	protected Activity createGravComp(Activity switchToFinalController, DeviceParameters... parameters)
			throws RoboticsException {

		// parameter for GravComp
		JointImpedanceSettings spring = new JointImpedanceSettings(0.01, 1, 0);
		JointController jntImpController = new JointImpedanceController(spring, spring, spring, spring, spring, spring,
				spring);

		// OTG should follow the measured sensor values
		RealtimeDouble sensors[] = new RealtimeDouble[lwr.getJointCount()];
		for (int i = 0; i < lwr.getJointCount(); i++) {
			sensors[i] = lwr.getJoint(i).getDriver().getMeasuredPositionSensor();
		}

		Activity toJointImpedance = getDevice().use(JointControllerInterface.class)
				.switchJointController(jntImpController, parameters);

		Activity followPosition = new FromCommandActivity(() -> {
			// FIXME: Upon cancel, following should only be completed when close
			// enough to goal!
			Command ret = getDevice().getDriver().getRuntime().createRuntimeCommand(getDevice().getDriver(),
					new FollowJointGoal(sensors),
					getDefaultParameters().withParameters(OverrideParameter.MAXIMUM).withParameters(parameters));
			ret.ignoreException(IllegalJointValueException.class);
			return ret;
		}, getDevice());

		// JointController defaultController =
		// getDefaultParameters().get(ControllerParameter.class).getController();

		// toPosition =
		// getDevice().getDriver().getRuntime().createRuntimeCommand(getDevice().getDriver(),
		// new SwitchController(defaultController), getDefaultParameters());

		return Activities.strictlySequential(toJointImpedance, followPosition, switchToFinalController);
	}

	@Override
	public final Activity linToContact(Pose to, final double contactForce, DeviceParameters... parameters)
			throws RoboticsException {
		return linToContact(to, contactForce, true, parameters);

	}

	@Override
	public Activity linToContact(Pose to, double contactForce, boolean absoluteForce, DeviceParameters... parameters)
			throws RoboticsException {
		return linToContact(to, contactForce, absoluteForce, 1, parameters);
	}

	@Override
	public final Activity linToContact(Pose to, double contactForce, double speedFactor, DeviceParameters... parameters)
			throws RoboticsException {
		return linToContact(to, contactForce, true, speedFactor, parameters);
	}

	@Override
	public Activity linToContact(Pose to, double contactForce, boolean absoluteForce, double speedFactor,
			DeviceParameters... parameters) throws RoboticsException {
		return linToContact(to, contactForce, contactForce, contactForce, absoluteForce, speedFactor, parameters);
	}

	@Override
	public final Activity linToContact(Pose to, final double contactForceX, final double contactForceY,
			final double contactForceZ, DeviceParameters... parameters) throws RoboticsException {
		return linToContact(to, contactForceX, contactForceY, contactForceZ, true, parameters);
	}

	@Override
	public Activity linToContact(Pose to, double contactForceX, double contactForceY, double contactForceZ,
			boolean absoluteForce, DeviceParameters... parameters) throws RoboticsException {
		return linToContact(to, contactForceX, contactForceY, contactForceZ, absoluteForce, 1, parameters);
	}

	@Override
	public Activity linToContact(Pose to, final double contactForceX, final double contactForceY,
			final double contactForceZ, double speedFactor, DeviceParameters... parameters) throws RoboticsException {
		return linToContact(to, contactForceX, contactForceY, contactForceZ, true, speedFactor, parameters);
	}

	@Override
	public Activity linToContact(Pose to, final double contactForceX, final double contactForceY,
			final double contactForceZ, boolean absoluteForce, double speedFactor, DeviceParameters... parameters)
			throws RoboticsException {
		// standard lin without controller switch
		final Activity lin = super.lin(to, getHintJointsFromFrame(to, parameters), speedFactor, parameters);

		// wrapping contact handling around standard lin
		RealtimeDouble xSensor = lwr.getForceXSensor();
		RealtimeDouble ySensor = lwr.getForceYSensor();
		RealtimeDouble zSensor = lwr.getForceZSensor();

		boolean absoluteForces = false;
		RealtimeBoolean xForceExceeded = absoluteForces ? xSensor.abs().greater(Math.abs(contactForceX))
				: xSensor.greater(contactForceX);
		RealtimeBoolean yForceExceeded = absoluteForces ? ySensor.abs().greater(Math.abs(contactForceY))
				: ySensor.greater(contactForceY);
		RealtimeBoolean zForceExceeded = absoluteForces ? zSensor.abs().greater(Math.abs(contactForceZ))
				: zSensor.greater(contactForceZ);

		lin.addCancelCondition(xForceExceeded.or(yForceExceeded).or(zForceExceeded));
		return lin;
	}

	@Override
	public Activity releaseForce(final DeviceParameters... parameters) throws RoboticsException {

		DeviceParameterBag params = getDefaultParameters().withParameters(OverrideParameter.MAXIMUM)
				.withParameters(parameters);

		MotionCenterParameter mcp = params.get(MotionCenterParameter.class);

		if (mcp == null) {
			throw new RoboticsException("No motion center specified");
		}

		Transformation measuredBaseToMC = lwr.getBase().asPose().getMeasuredTransformationTo(mcp.getMotionCenter());

		List<DeviceParameters> paramsList = params.getAll();
		DeviceParameters[] paramsArray = paramsList.toArray(new DeviceParameters[paramsList.size()]);

		return lin(lwr.getBase().asPose().plus(measuredBaseToMC), paramsArray);

	}

}
