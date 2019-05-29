/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.sensor;

import org.roboticsapi.core.sensor.DeviceBasedDoubleSensor;
import org.roboticsapi.kuka.lwr.runtime.driver.SoftRobotLWRDriver;

public final class SoftRobotLWRCartesianForceSensor extends DeviceBasedDoubleSensor<SoftRobotLWRDriver> {

	public enum CartesianDirection {
		X, Y, Z, A, B, C
	}

	private final CartesianDirection direction;

	public SoftRobotLWRCartesianForceSensor(SoftRobotLWRDriver device, CartesianDirection direction) {
		super(device);
		this.direction = direction;
	}

	public CartesianDirection getDirection() {
		return direction;
	}

	@Override
	public boolean equals2(Object obj) {
		return direction == ((SoftRobotLWRCartesianForceSensor) obj).direction;
	}

	@Override
	protected Object[] getMoreObjectsForHashCode() {
		return new Object[] { direction };
	}

}