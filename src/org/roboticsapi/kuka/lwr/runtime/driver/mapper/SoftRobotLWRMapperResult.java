/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.driver.mapper;

import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.kuka.lwr.LWRDriver;
import org.roboticsapi.kuka.lwr.TenseSpringException;
import org.roboticsapi.runtime.SoftRobotRuntime;
import org.roboticsapi.runtime.mapping.MappingException;
import org.roboticsapi.runtime.mapping.net.DataflowOutPort;
import org.roboticsapi.runtime.mapping.net.DataflowType;
import org.roboticsapi.runtime.mapping.net.NetFragment;
import org.roboticsapi.runtime.mapping.result.ActuatorDriverMapperResult;
import org.roboticsapi.runtime.mapping.result.impl.StatePortFactory;
import org.roboticsapi.runtime.multijoint.JointsDataflow;
import org.roboticsapi.runtime.robot.mapper.AbstractRobotArmDriverMapperResult;

public class SoftRobotLWRMapperResult<R extends LWRDriver> extends AbstractRobotArmDriverMapperResult<R> {

	public SoftRobotLWRMapperResult(NetFragment fragment, DataflowOutPort tenseSpringState, SoftRobotRuntime runtime,
			final R robot, final DeviceParameterBag parameters, ActuatorDriverMapperResult[] jointResult,
			StatePortFactory completePortFactory) throws MappingException {

		super(robot, fragment, parameters, runtime, jointResult, completePortFactory);

		addExceptionPort(TenseSpringException.class, tenseSpringState);

	}

	public SoftRobotLWRMapperResult(NetFragment fragment, DataflowOutPort tenseSpringState, SoftRobotRuntime runtime,
			final R robot, final DeviceParameterBag parameters, ActuatorDriverMapperResult[] jointResult,
			DataflowOutPort complete) throws MappingException {

		super(robot, fragment, parameters, runtime, jointResult, complete);

		addExceptionPort(TenseSpringException.class, tenseSpringState);

	}

	protected DataflowType getJointDataflowType(final LWRDriver robot) {
		return new JointsDataflow(7, robot);
	}
}