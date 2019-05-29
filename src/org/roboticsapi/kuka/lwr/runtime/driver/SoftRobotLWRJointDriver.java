/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.driver;

import org.roboticsapi.core.sensor.DoubleSensor;
import org.roboticsapi.kuka.lwr.LWRJointDriver;
import org.roboticsapi.kuka.lwr.runtime.sensor.SoftRobotLWRJointTorqueSensor;
import org.roboticsapi.runtime.SoftRobotRuntime;
import org.roboticsapi.runtime.multijoint.driver.SoftRobotJointDriver;

public class SoftRobotLWRJointDriver extends SoftRobotJointDriver implements LWRJointDriver {

	public SoftRobotLWRJointDriver(SoftRobotRuntime runtime, String deviceName, String deviceType, int jointNumber) {
		super(runtime, deviceName, deviceType, jointNumber);
	}

	@Override
	public DoubleSensor getTorqueSensor() {
		return new SoftRobotLWRJointTorqueSensor(this);
	}

}
