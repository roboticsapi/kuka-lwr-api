/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.driver.mapper;

import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.kuka.lwr.runtime.driver.SoftRobotLWRDriver;
import org.roboticsapi.kuka.lwr.runtime.fragment.LWRInvKinematicsFragment;
import org.roboticsapi.kuka.lwr.runtime.fragment.LWRKinematicsFragment;
import org.roboticsapi.runtime.SoftRobotRuntime;
import org.roboticsapi.runtime.mapping.MappingException;
import org.roboticsapi.runtime.mapping.net.NetFragment;
import org.roboticsapi.runtime.mapping.parts.DeviceMappingPorts;
import org.roboticsapi.runtime.mapping.result.ActuatorDriverMapperResult;
import org.roboticsapi.runtime.multijoint.mapper.JointPositionActionResult;
import org.roboticsapi.runtime.robot.driver.SoftRobotRobotArmDriver;
import org.roboticsapi.runtime.robot.driver.mapper.SoftRobotRobotArmDriverJointPositionMapper;
import org.roboticsapi.runtime.rpi.RPIException;

public class SoftRobotLWRDriverJointPositionMapper<DD extends SoftRobotLWRDriver>
		extends SoftRobotRobotArmDriverJointPositionMapper<DD> implements SoftRobotLWRMapper {

	@Override
	public ActuatorDriverMapperResult map(final SoftRobotRuntime runtime, final DD driver,
			JointPositionActionResult actionResult, DeviceParameterBag parameters, DeviceMappingPorts ports)
			throws MappingException, RPIException {

		ActuatorDriverMapperResult mapResult = super.map(runtime, driver, actionResult, parameters, ports);
		NetFragment fragment = mapResult.getNetFragment();

		fragment.addLinkBuilder(new SoftRobotLWRLinkBuilder(driver, this));

		return mapResult;
	}

	@Override
	public LWRKinematicsFragment createKinematicsFragment(SoftRobotLWRDriver robot) throws MappingException {
		return new LWRKinematicsFragment(robot);
	}

	@Override
	public InvKinematicsFragment createInvKinematicsFragment(SoftRobotRobotArmDriver driver,
			DeviceParameterBag parameters) throws MappingException {
		LWRInvKinematicsFragment ret = new LWRInvKinematicsFragment(driver, parameters);
		ret.connect(null, ret.getInHintJoints());
		return ret;
	}
}
