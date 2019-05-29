/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.extension;

import java.util.HashMap;
import java.util.Map;

import org.roboticsapi.core.RoboticsObject;
import org.roboticsapi.core.SingleDeviceInterfaceFactory;
import org.roboticsapi.kuka.lwr.AbstractLWR;
import org.roboticsapi.kuka.lwr.LWRDriver;
import org.roboticsapi.kuka.lwr.runtime.action.BlendControllerAction;
import org.roboticsapi.kuka.lwr.runtime.action.SwitchControllerAction;
import org.roboticsapi.kuka.lwr.runtime.action.SwitchToolAction;
import org.roboticsapi.kuka.lwr.runtime.action.mapper.SoftRobotLWRBlendControllerActionMapper;
import org.roboticsapi.kuka.lwr.runtime.action.mapper.SoftRobotLWRBlendControllerActionMapper.ControllerActionResult;
import org.roboticsapi.kuka.lwr.runtime.action.mapper.SoftRobotLWRSwitchControllerMapper;
import org.roboticsapi.kuka.lwr.runtime.action.mapper.SoftRobotLWRSwitchToolMapper;
import org.roboticsapi.kuka.lwr.runtime.action.mapper.ToolActionResult;
import org.roboticsapi.kuka.lwr.runtime.activity.LwrCartesianVelocityMotionInterfaceImpl;
import org.roboticsapi.kuka.lwr.runtime.activity.LwrHoldMotionInterfaceImpl;
import org.roboticsapi.kuka.lwr.runtime.activity.LwrMotionInterfaceImpl;
import org.roboticsapi.kuka.lwr.runtime.driver.FastResearchLWRDriver;
import org.roboticsapi.kuka.lwr.runtime.driver.SimulatedIiwaDriver;
import org.roboticsapi.kuka.lwr.runtime.driver.SimulatedLWRDriver;
import org.roboticsapi.kuka.lwr.runtime.driver.SoftRobotLWRDriver;
import org.roboticsapi.kuka.lwr.runtime.driver.SoftRobotLWRJointDriver;
import org.roboticsapi.kuka.lwr.runtime.driver.mapper.SoftRobotLWRControllerDeviceMapper;
import org.roboticsapi.kuka.lwr.runtime.driver.mapper.SoftRobotLWRDriverJointGoalMapper;
import org.roboticsapi.kuka.lwr.runtime.driver.mapper.SoftRobotLWRDriverJointPositionMapper;
import org.roboticsapi.kuka.lwr.runtime.driver.mapper.SoftRobotLWRToolDeviceMapper;
import org.roboticsapi.kuka.lwr.runtime.sensor.SoftRobotLWRCartesianForceSensor;
import org.roboticsapi.kuka.lwr.runtime.sensor.SoftRobotLWRInverseKinematicsSensor;
import org.roboticsapi.kuka.lwr.runtime.sensor.SoftRobotLWRJointTorqueSensor;
import org.roboticsapi.kuka.lwr.runtime.sensor.mapper.SoftRobotLWRCartesianForceSensorMapper;
import org.roboticsapi.kuka.lwr.runtime.sensor.mapper.SoftRobotLWRInverseKinematicsSensorMapper;
import org.roboticsapi.kuka.lwr.runtime.sensor.mapper.SoftRobotLWRJointTorqueSensorMapper;
import org.roboticsapi.runtime.SoftRobotRuntime;
import org.roboticsapi.runtime.driver.DeviceBasedInstantiator;
import org.roboticsapi.runtime.extension.AbstractSoftRobotRoboticsBuilder;
import org.roboticsapi.runtime.mapping.MapperRegistry;
import org.roboticsapi.runtime.multijoint.mapper.JointGoalActionResult;
import org.roboticsapi.runtime.multijoint.mapper.JointPositionActionResult;

public final class SoftRobotLWRExtension extends AbstractSoftRobotRoboticsBuilder {

	private final Map<SoftRobotLWRDriver, DeviceBasedInstantiator<AbstractLWR>> map = new HashMap<SoftRobotLWRDriver, DeviceBasedInstantiator<AbstractLWR>>();

	public SoftRobotLWRExtension() {
		super(SoftRobotLWRDriver.class, SoftRobotLWRJointDriver.class, SimulatedLWRDriver.class,
				SimulatedIiwaDriver.class, FastResearchLWRDriver.class);
	}

	@Override
	protected String[] getRuntimeExtensions() {
		return new String[] { "kuka_lwr", "kuka_lwr_fri", "kuka_lwr_sim", "kuka_iiwa_sim", "kuka_iiwa_fri" };
	}

	@Override
	protected void onRoboticsObjectAvailable(final RoboticsObject builtObject) {
		if (builtObject instanceof AbstractLWR) {
			final AbstractLWR lwr = (AbstractLWR) builtObject;
			lwr.addInterfaceFactory(new SingleDeviceInterfaceFactory<LwrMotionInterfaceImpl>() {
				@Override
				protected LwrMotionInterfaceImpl build() {
					return new LwrMotionInterfaceImpl(lwr);
				}
			});
			lwr.addInterfaceFactory(new SingleDeviceInterfaceFactory<LwrHoldMotionInterfaceImpl>() {
				@Override
				protected LwrHoldMotionInterfaceImpl build() {
					return new LwrHoldMotionInterfaceImpl(lwr);
				}
			});
			lwr.addInterfaceFactory(new SingleDeviceInterfaceFactory<LwrCartesianVelocityMotionInterfaceImpl<AbstractLWR>>() {
				@Override
				protected LwrCartesianVelocityMotionInterfaceImpl<AbstractLWR> build() {
					return new LwrCartesianVelocityMotionInterfaceImpl<AbstractLWR>(lwr);
				}
			});

			LWRDriver d = lwr.getDriver();

			if (d instanceof SimulatedLWRDriver) {
				final SoftRobotLWRDriver driver = (SoftRobotLWRDriver) d;
				final DeviceBasedInstantiator<AbstractLWR> loader = new DeviceBasedInstantiator<AbstractLWR>(lwr, driver);

				map.put(driver, loader);
				driver.addOperationStateListener(loader);
			}

			if (d instanceof SimulatedIiwaDriver) {
				final SoftRobotLWRDriver driver = (SoftRobotLWRDriver) d;
				final DeviceBasedInstantiator<AbstractLWR> loader = new DeviceBasedInstantiator<AbstractLWR>(lwr, driver);

				map.put(driver, loader);
				driver.addOperationStateListener(loader);
			}
			
			// never delete FRI driver...
			if (d instanceof FastResearchLWRDriver) {
				final SoftRobotLWRDriver driver = (SoftRobotLWRDriver) d;
				final DeviceBasedInstantiator<AbstractLWR> loader = new DeviceBasedInstantiator<AbstractLWR>(lwr, driver, true);

				map.put(driver, loader);
				driver.addOperationStateListener(loader);
			}
		}
	}

	@Override
	protected void onRoboticsObjectUnavailable(RoboticsObject object) {
		if (object instanceof SoftRobotLWRDriver) {
			final SoftRobotLWRDriver driver = (SoftRobotLWRDriver) object;
			final DeviceBasedInstantiator<AbstractLWR> loader = map.remove(driver);
			driver.removeOperationStateListener(loader);
		}
	}

	@Override
	protected void onRuntimeAvailable(SoftRobotRuntime runtime) {
		MapperRegistry mapperregistry = runtime.getMapperRegistry();

		mapperregistry.registerActionMapper(SoftRobotRuntime.class, SwitchControllerAction.class,
				new SoftRobotLWRSwitchControllerMapper());
		mapperregistry.registerActionMapper(SoftRobotRuntime.class, BlendControllerAction.class,
				new SoftRobotLWRBlendControllerActionMapper());
		mapperregistry.registerActuatorDriverMapper(SoftRobotRuntime.class, SoftRobotLWRDriver.class,
				ControllerActionResult.class, new SoftRobotLWRControllerDeviceMapper());
		mapperregistry.registerActionMapper(SoftRobotRuntime.class, SwitchToolAction.class,
				new SoftRobotLWRSwitchToolMapper());
		mapperregistry.registerActuatorDriverMapper(SoftRobotRuntime.class, SoftRobotLWRDriver.class,
				ToolActionResult.class, new SoftRobotLWRToolDeviceMapper()); // FIXME: ToolActionResult in
																				// RobotArm!

		mapperregistry.registerActuatorDriverMapper(SoftRobotRuntime.class, SoftRobotLWRDriver.class,
				JointPositionActionResult.class, new SoftRobotLWRDriverJointPositionMapper<SoftRobotLWRDriver>());
		mapperregistry.registerActuatorDriverMapper(SoftRobotRuntime.class, SoftRobotLWRDriver.class,
				JointGoalActionResult.class, new SoftRobotLWRDriverJointGoalMapper<SoftRobotLWRDriver>());

		mapperregistry.registerSensorMapper(SoftRobotRuntime.class, SoftRobotLWRCartesianForceSensor.class,
				new SoftRobotLWRCartesianForceSensorMapper());
		mapperregistry.registerSensorMapper(SoftRobotRuntime.class, SoftRobotLWRJointTorqueSensor.class,
				new SoftRobotLWRJointTorqueSensorMapper());
		mapperregistry.registerSensorMapper(SoftRobotRuntime.class, SoftRobotLWRInverseKinematicsSensor.class,
				new SoftRobotLWRInverseKinematicsSensorMapper());

		// TODO:
		// mapperregistry.registerActuatorMapper(SoftRobotRuntime.class,
		// SoftRobotLWR.class, JointGoalActionResult.class,
		// new SoftRobotLWRJointGoalOTGMapper());
	}

	@Override
	protected void onRuntimeUnavailable(SoftRobotRuntime runtime) {
	}

}
