/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr;

import org.roboticsapi.core.Device;

/**
 * A {@link Device} representing a KUKA Light Weight Robot.
 */
public class LWRiiwa14 extends AbstractLWRiiwa {

	private final static double[] LINK_LENGTHS = { 0.1575, 0.2025, 0.2045, 0.2155, 0.1845, 0.2155, 0.081, 0.045 };

	/**
	 * Instantiates a new LWR.
	 */
	public LWRiiwa14() {
	}

	@Override
	public double[] getLinkLengths() {
		return LINK_LENGTHS;
	}

	@Override
	protected int getMaximumPayload() {
		return 14;
	}

	@Override
	protected double[] getMaximumJointAngles() {
		return new double[] { Math.toRadians(170), Math.toRadians(120), Math.toRadians(170), Math.toRadians(120),
				Math.toRadians(170), Math.toRadians(120), Math.toRadians(175) };
	}

	@Override
	protected double[] getMaximumJointVelocities() {
		return new double[] { Math.toRadians(85), Math.toRadians(85), Math.toRadians(100), Math.toRadians(75),
				Math.toRadians(130), Math.toRadians(135), Math.toRadians(135) };
	}

}
