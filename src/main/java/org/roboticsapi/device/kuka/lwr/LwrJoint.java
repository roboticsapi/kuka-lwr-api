/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr;

import org.roboticsapi.core.exception.RoboticsException;
import org.roboticsapi.core.realtimevalue.realtimedouble.RealtimeDouble;
import org.roboticsapi.framework.multijoint.RevoluteJoint;

/**
 * A joint of a lightweight robot (including a torque sensor)
 */
public class LwrJoint extends RevoluteJoint<LwrJointDriver> {

	public final double getTorque() throws RoboticsException {
		return getTorqueSensor().getCurrentValue();
	}

	public final RealtimeDouble getTorqueSensor() {
		return getDriver().getTorqueSensor();
	}
}
