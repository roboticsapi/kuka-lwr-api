/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.activity;

import org.roboticsapi.activity.RtActivity;
import org.roboticsapi.cartesianmotion.activity.CartesianVelocityMotionInterfaceImpl;
import org.roboticsapi.core.DeviceParameters;
import org.roboticsapi.core.exception.RoboticsException;
import org.roboticsapi.kuka.lwr.AbstractLWR;
import org.roboticsapi.world.sensor.VelocitySensor;

public class LwrCartesianVelocityMotionInterfaceImpl<T extends AbstractLWR> extends CartesianVelocityMotionInterfaceImpl<T> {

	public LwrCartesianVelocityMotionInterfaceImpl(T device) {
		super(device);
	}

	@Override
	public RtActivity moveVelocity(VelocitySensor velocity, DeviceParameters... parameters) throws RoboticsException {
		RtActivity moveVelocity = super.moveVelocity(velocity, parameters);

		return new LwrMotionActivity(getDevice(), moveVelocity, getDefaultParameters().withParameters(parameters));
	}

}
