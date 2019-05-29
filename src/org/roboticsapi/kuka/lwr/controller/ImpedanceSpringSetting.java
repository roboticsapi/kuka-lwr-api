/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.controller;

import org.roboticsapi.core.util.HashCodeUtil;

public class ImpedanceSpringSetting {

	protected double stiffness;
	protected double damping;

	public ImpedanceSpringSetting(double stiffness, double damping) {
		super();
		this.stiffness = stiffness;
		this.damping = damping;
	}

	public double getDamping() {
		return damping;
	}

	public double getStiffness() {
		return stiffness;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ImpedanceSpringSetting)) {
			return false;
		}

		ImpedanceSpringSetting other = (ImpedanceSpringSetting) obj;

		return getStiffness() == other.getStiffness() && getDamping() == other.getDamping();
	}

	@Override
	public int hashCode() {
		int hash = HashCodeUtil.SEED;

		hash = HashCodeUtil.hash(hash, getClass());

		hash = HashCodeUtil.hash(hash, getStiffness());

		return HashCodeUtil.hash(hash, getDamping());
	}

}
