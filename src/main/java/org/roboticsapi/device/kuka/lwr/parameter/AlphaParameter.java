/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.parameter;

import org.roboticsapi.core.DeviceParameters;

/**
 * Indicates to use a certain alpha value for motions.
 */
public class AlphaParameter implements DeviceParameters {
	private final double alpha;

	/**
	 * Instantiates a new alpha parameter with the given alpha value.
	 * 
	 * @param alpha the alpha value
	 */
	public AlphaParameter(double alpha) {
		this.alpha = alpha;

	}

	/**
	 * Gets the alpha value.
	 * 
	 * @return the alpha value
	 */
	public double getAlpha() {
		return alpha;
	}

	@Override
	public boolean respectsBounds(DeviceParameters boundingObject) {
		if (!(boundingObject instanceof AlphaParameter)) {
			throw new IllegalArgumentException("Argument must be of type " + getClass().getName());
		}

		AlphaParameter bound = (AlphaParameter) boundingObject;

		return getAlpha() <= bound.getAlpha();
	}
}
