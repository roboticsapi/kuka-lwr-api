/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.activity;

import org.roboticsapi.core.actuator.ActuatorDriverRealtimeException;
import org.roboticsapi.device.kuka.lwr.LwrDriver;

public class ForceNotReachedException extends ActuatorDriverRealtimeException {

	private static final long serialVersionUID = 5801663641975369377L;

	public ForceNotReachedException(LwrDriver actuatorDriver) {
		super(actuatorDriver);
	}

}
