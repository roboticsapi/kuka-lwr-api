/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.runtime.rpi.sensor;

import org.roboticsapi.core.realtimevalue.realtimedouble.RealtimeDouble;
import org.roboticsapi.core.world.realtimevalue.realtimetransformation.RealtimeTransformation;
import org.roboticsapi.device.kuka.lwr.AbstractLwr;
import org.roboticsapi.device.kuka.lwr.LwrRedundancyStrategy;
import org.roboticsapi.facet.runtime.rpi.mapping.NamedActuatorDriver;
import org.roboticsapi.framework.robot.runtime.rpi.driver.RobotArmInverseKinematicsRealtimeDoubleArray;

public class LwrInverseKinematicsRealtimeDoubleArray extends RobotArmInverseKinematicsRealtimeDoubleArray {

	private final RealtimeDouble alpha;
	private final LwrRedundancyStrategy strategy;

	public LwrInverseKinematicsRealtimeDoubleArray(NamedActuatorDriver<? extends AbstractLwr> driver,
			RealtimeTransformation transformation, RealtimeDouble[] hintJoints, RealtimeDouble alpha,
			LwrRedundancyStrategy strategy) {
		super(driver, transformation, hintJoints);
		this.alpha = alpha;
		this.strategy = strategy;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && ((LwrInverseKinematicsRealtimeDoubleArray) obj).getAlpha().equals(getAlpha());
	}

	@Override
	public int hashCode() {
		return hash(super.hashCode(), getAlpha());
	}

	public RealtimeDouble getAlpha() {
		return alpha;
	}

	public LwrRedundancyStrategy getStrategy() {
		return strategy;
	}

}
