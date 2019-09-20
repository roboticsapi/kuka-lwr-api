/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.runtime.rpi.sensor;

import org.roboticsapi.core.realtimevalue.realtimedouble.DriverBasedRealtimeDouble;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.driver.LwrGenericDriver;

public final class LwrCartesianForceTorqueRealtimeDouble extends DriverBasedRealtimeDouble<LwrGenericDriver> {

	public enum CartesianDirection {
		X, Y, Z, A, B, C
	}

	private final CartesianDirection direction;

	public LwrCartesianForceTorqueRealtimeDouble(LwrGenericDriver device, CartesianDirection direction) {
		super(device);
		this.direction = direction;
	}

	public CartesianDirection getDirection() {
		return direction;
	}

	@Override
	public boolean equals2(Object obj) {
		return direction == ((LwrCartesianForceTorqueRealtimeDouble) obj).direction;
	}

	@Override
	protected Object[] getMoreObjectsForHashCode() {
		return new Object[] { direction };
	}

}