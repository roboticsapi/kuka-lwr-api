/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr;

import org.roboticsapi.robot.RedundancyStrategy;

public abstract class LWRRedundancyStrategy implements RedundancyStrategy {
	private final int nr;

	public LWRRedundancyStrategy(final int nr) {
		this.nr = nr;
	}

	public int getNr() {
		return nr;
	}
}