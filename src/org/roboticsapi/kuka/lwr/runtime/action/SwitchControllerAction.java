/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.action;

import org.roboticsapi.core.Action;
import org.roboticsapi.multijoint.parameter.Controller;

public class SwitchControllerAction extends Action {

	private final Controller controller;

	public Controller getController() {
		return controller;
	}

	public SwitchControllerAction(Controller controller) {
		super(0);
		this.controller = controller;
	}
}
