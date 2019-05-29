/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr;

import org.roboticsapi.core.exception.RoboticsException;
import org.roboticsapi.core.sensor.DoubleSensor;
import org.roboticsapi.multijoint.RevoluteJoint;

/**
 * A joint of a lightweight robot (including a torque sensor)
 */
public class LWRJoint extends RevoluteJoint<LWRJointDriver> {

	public final double getTorque() throws RoboticsException {
		return getTorqueSensor().getCurrentValue();
	}

	public final DoubleSensor getTorqueSensor() {
		return getDriver().getTorqueSensor();
	}
}
