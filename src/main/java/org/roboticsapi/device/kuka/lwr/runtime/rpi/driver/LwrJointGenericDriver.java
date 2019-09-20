/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.runtime.rpi.driver;

import org.roboticsapi.core.realtimevalue.realtimedouble.RealtimeDouble;
import org.roboticsapi.device.kuka.lwr.LwrJointDriver;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.sensor.LwrJointTorqueRealtimeDouble;
import org.roboticsapi.facet.runtime.rpi.mapping.RpiRuntime;
import org.roboticsapi.framework.multijoint.Joint;
import org.roboticsapi.framework.multijoint.runtime.rpi.JointGenericDriver;

public class LwrJointGenericDriver extends JointGenericDriver implements LwrJointDriver {

	public LwrJointGenericDriver(RpiRuntime runtime, String rpiDeviceName, String rpiDeviceType, int jointNumber,
			Joint joint) {
		super(runtime, rpiDeviceName, rpiDeviceType, jointNumber, joint);
	}

	@Override
	public RealtimeDouble getTorqueSensor() {
		return new LwrJointTorqueRealtimeDouble(this);
	}

}
