/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.action;

import org.roboticsapi.core.Action;
import org.roboticsapi.robot.parameter.RobotTool;

public class SwitchToolAction extends Action {

	private final RobotTool tool;

	public RobotTool getRobotTool() {
		return tool;
	}

	public SwitchToolAction(RobotTool tool) {
		super(0);
		this.tool = tool;
	}
}
