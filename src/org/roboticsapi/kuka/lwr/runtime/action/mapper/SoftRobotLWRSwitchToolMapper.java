/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.action.mapper;

import org.roboticsapi.core.Action;
import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.kuka.lwr.runtime.action.SwitchToolAction;
import org.roboticsapi.robot.parameter.RobotTool;
import org.roboticsapi.runtime.SoftRobotRuntime;
import org.roboticsapi.runtime.mapping.MappingException;
import org.roboticsapi.runtime.mapping.net.NetFragment;
import org.roboticsapi.runtime.mapping.parts.ActionMapper;
import org.roboticsapi.runtime.mapping.parts.ActionMappingContext;
import org.roboticsapi.runtime.mapping.result.ActionMapperResult;
import org.roboticsapi.runtime.mapping.result.ActionResult;
import org.roboticsapi.runtime.mapping.result.impl.GoalActionMapperResult;
import org.roboticsapi.runtime.rpi.RPIException;

public class SoftRobotLWRSwitchToolMapper implements ActionMapper<SoftRobotRuntime, SwitchToolAction> {

	@Override
	public ActionMapperResult map(SoftRobotRuntime runtime, SwitchToolAction action, DeviceParameterBag parameters,
			ActionMappingContext ports) throws MappingException, RPIException {

		RobotTool tool = action.getRobotTool();

		NetFragment ret = new NetFragment("SwitchToolAction");

		return new SwitchToolActionMapperResult(action, ret, new ToolActionResult(tool));
	}

	public static class SwitchToolActionMapperResult extends GoalActionMapperResult {

		public SwitchToolActionMapperResult(Action action, NetFragment fragment, ActionResult result) {
			super(action, fragment, result);
		}
	}

}
