/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.activity;

import org.roboticsapi.activity.ActivityProperty;
import org.roboticsapi.multijoint.parameter.Controller;

public class ControllerProperty implements ActivityProperty {
	private Controller controller;

	public ControllerProperty(Controller controller) {
		this.setController(controller);

	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public Controller getController() {
		return controller;
	}
}
