/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.runtime.rpi.extension;

import org.roboticsapi.device.kuka.lwr.runtime.rpi.action.BlendJointController;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.action.mapper.BlendJointControllerMapper;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.driver.LwrFriDriver;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.driver.LwrGenericDriver;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.driver.LwrIiwaFriDriver;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.driver.LwrIiwaMockDriver;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.driver.LwrMockDriver;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.driver.mapper.LwrJointImpedanceControllerMapper;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.driver.mapper.LwrPositionControllerMapper;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.driver.mapper.LwrToolMapper;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.sensor.mapper.LwrCartesianForceTorqueRealtimeValueMapper;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.sensor.mapper.LwrInverseKinematicsRealtimeValueMapper;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.sensor.mapper.LwrJointTorqueRealtimeValueMapper;
import org.roboticsapi.facet.runtime.rpi.extension.RpiExtension;
import org.roboticsapi.facet.runtime.rpi.mapping.MapperRegistry;
import org.roboticsapi.framework.multijoint.runtime.rpi.result.JointImpedanceControllerActionResult;
import org.roboticsapi.framework.multijoint.runtime.rpi.result.JointPositionControllerActionResult;
import org.roboticsapi.framework.robot.runtime.rpi.mapper.ToolActionResult;

public final class LwrRuntimeExtension extends RpiExtension {

	public LwrRuntimeExtension() {
		super(LwrMockDriver.class, LwrFriDriver.class, LwrIiwaMockDriver.class, LwrIiwaFriDriver.class);
	}

	@Override
	protected void registerMappers(MapperRegistry mr) {

		mr.registerActionMapper(BlendJointController.class, new BlendJointControllerMapper());

		mr.registerActuatorDriverMapper(LwrGenericDriver.class, JointPositionControllerActionResult.class,
				new LwrPositionControllerMapper());
		mr.registerActuatorDriverMapper(LwrGenericDriver.class, JointImpedanceControllerActionResult.class,
				new LwrJointImpedanceControllerMapper());
		mr.registerActuatorDriverMapper(LwrGenericDriver.class, ToolActionResult.class, new LwrToolMapper());

		mr.registerRealtimeValueMapper(new LwrInverseKinematicsRealtimeValueMapper());
		mr.registerRealtimeValueMapper(new LwrCartesianForceTorqueRealtimeValueMapper());
		mr.registerRealtimeValueMapper(new LwrJointTorqueRealtimeValueMapper());

	}

	@Override
	protected void unregisterMappers(MapperRegistry mr) {
		// TODO remove mappers?

	}

}
