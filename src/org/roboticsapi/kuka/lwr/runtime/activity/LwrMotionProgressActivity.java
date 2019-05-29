/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.activity;

import org.roboticsapi.activity.PlannedRtActivity;
import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.core.State;
import org.roboticsapi.kuka.lwr.AbstractLWR;

class LwrMotionProgressActivity extends LwrMotionActivity implements PlannedRtActivity {

	private final PlannedRtActivity motion;

	public LwrMotionProgressActivity(AbstractLWR device, PlannedRtActivity motion, DeviceParameterBag parameters) {
		super(device, motion, parameters);
		this.motion = motion;
	}

	@Override
	public State getMotionTimeProgress(double progress) {
		return motion.getMotionTimeProgress(progress);
	}

}