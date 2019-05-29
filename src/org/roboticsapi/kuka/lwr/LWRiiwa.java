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
public class LWRiiwa extends AbstractLWRiiwa {

	private final static double B1 = 0.34, B3 = 0.4, B5 = 0.4, B7 = 0.126;
	private final static double[] LINK_LENGTHS = { 0.17, B1 - 0.17, 0.2, B3 - 0.2, 0.2, B5 - 0.2, B7 - 0.045, 0.045 };

	/**
	 * Instantiates a new LWR.
	 */
	public LWRiiwa() {
	}

	@Override
	public double[] getLinkLengths() {
		return LINK_LENGTHS;
	}

	@Override
	protected int getMaximumPayload() {
		return 7;
	}
	
	@Override
	protected double[] getMaximumJointAngles() {
		return new double[] { Math.toRadians(170), Math.toRadians(120), Math.toRadians(170), Math.toRadians(120),
				Math.toRadians(170), Math.toRadians(120), Math.toRadians(175) };
	}

	@Override
	protected double[] getMaximumJointVelocities() {
		return new double[] { Math.toRadians(98), Math.toRadians(98), Math.toRadians(100), Math.toRadians(130),
				Math.toRadians(140), Math.toRadians(180), Math.toRadians(180) };
	}

}
