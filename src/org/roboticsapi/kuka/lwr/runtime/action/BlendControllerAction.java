/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.action;

import org.roboticsapi.core.Action;
import org.roboticsapi.multijoint.parameter.Controller;

public class BlendControllerAction extends Action {

	private final Controller startController, endController;
	private final double duration;

	public Controller getStartController() {
		return startController;
	}

	public Controller getEndController() {
		return endController;
	}

	public double getDuration() {
		return duration;
	}

	public BlendControllerAction(Controller startController, Controller endController, double duration) {
		super(0);
		this.startController = startController;
		this.endController = endController;
		this.duration = duration;
	}
}
