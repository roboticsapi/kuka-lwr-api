/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.runtime.rpi.sensor;

import org.roboticsapi.core.realtimevalue.realtimedouble.DriverBasedRealtimeDouble;
import org.roboticsapi.facet.runtime.rpi.mapping.IndexedActuatorDriver;
import org.roboticsapi.framework.multijoint.Joint;

public final class LwrJointTorqueRealtimeDouble extends DriverBasedRealtimeDouble<IndexedActuatorDriver<Joint>> {

	public LwrJointTorqueRealtimeDouble(IndexedActuatorDriver<Joint> driver) {
		super(driver);
	}

}