/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr;

public class LWR4 extends AbstractLWR {

	private final static double B1 = 0.3105, B3 = 0.4, B5 = 0.39, B7 = 0.078;
	private final static double[] LINK_LENGTHS = { 0, B1, 0.2, B3 - 0.2, 0.2, B5 - 0.2, B7, 0 };

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
				Math.toRadians(170), Math.toRadians(120), Math.toRadians(170) };
	}

	@Override
	protected double[] getMaximumJointVelocities() {
		return new double[] { Math.toRadians(112.5), Math.toRadians(112.5), Math.toRadians(112.5),
				Math.toRadians(112.5), Math.toRadians(180), Math.toRadians(112.5), Math.toRadians(112.5),
				Math.toRadians(112.5) };
	}

}
