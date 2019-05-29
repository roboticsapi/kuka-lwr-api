/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.activity;

import org.roboticsapi.cartesianmotion.activity.CartesianSplineActivity;
import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.core.State;
import org.roboticsapi.kuka.lwr.AbstractLWR;

public class LwrCartesianSplineActivity extends LwrMotionProgressActivity implements CartesianSplineActivity {

	private final CartesianSplineActivity spline;

	public LwrCartesianSplineActivity(AbstractLWR device, CartesianSplineActivity motion, DeviceParameterBag parameters) {
		super(device, motion, parameters);
		spline = motion;
	}

	@Override
	public State atSplinePoint(int pointIndex) {
		return spline.atSplinePoint(pointIndex);
	}

}
