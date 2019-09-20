/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.runtime.rpi.action;

import org.roboticsapi.core.Action;
import org.roboticsapi.framework.multijoint.controller.JointController;

public class BlendJointController extends Action {

	private final JointController startController, endController;
	private final double duration;

	public JointController getStartController() {
		return startController;
	}

	public JointController getEndController() {
		return endController;
	}

	public double getDuration() {
		return duration;
	}

	public BlendJointController(JointController startController, JointController endController, double duration) {
		super(0, true, false);
		this.startController = startController;
		this.endController = endController;
		this.duration = duration;
	}
}
