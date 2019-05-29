/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.controller;

import org.roboticsapi.core.util.HashCodeUtil;

public class LwrJointAdmittanceController extends LwrPositionController {
	private final ImpedanceSpringSetting[] jointImpedances;

	public LwrJointAdmittanceController(ImpedanceSpringSetting[] jointImpedances) {
		super();
		this.jointImpedances = copyArray(jointImpedances);

	}

	private ImpedanceSpringSetting[] copyArray(ImpedanceSpringSetting[] source) {
		ImpedanceSpringSetting[] target = new ImpedanceSpringSetting[source.length];

		for (int i = 0; i < source.length; i++) {
			target[i] = source[i];
		}

		return target;
	}

	public LwrJointAdmittanceController(ImpedanceSpringSetting jointImpedance0, ImpedanceSpringSetting jointImpedance1,
			ImpedanceSpringSetting jointImpedance2, ImpedanceSpringSetting jointImpedance3,
			ImpedanceSpringSetting jointImpedance4, ImpedanceSpringSetting jointImpedance5,
			ImpedanceSpringSetting jointImpedance6) {

		this(new ImpedanceSpringSetting[] { jointImpedance0, jointImpedance1, jointImpedance2, jointImpedance3,
				jointImpedance4, jointImpedance5, jointImpedance6 });
	}

	public ImpedanceSpringSetting getJointImpedances(int jointNumber) {
		return jointImpedances[jointNumber];
	}

	public ImpedanceSpringSetting[] getJointImpedances() {
		return jointImpedances;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}

		if (!(obj instanceof LwrJointAdmittanceController)) {
			return false;
		}

		LwrJointAdmittanceController other = (LwrJointAdmittanceController) obj;

		if (getJointImpedances().length != other.getJointImpedances().length) {
			return false;
		}

		for (int i = 0; i < getJointImpedances().length; i++) {
			if (!getJointImpedances()[i].equals(other.getJointImpedances()[i])) {
				return false;
			}
		}

		return true;
	}

	@Override
	public int hashCode() {
		int hash = super.hashCode();

		for (ImpedanceSpringSetting s : getJointImpedances()) {
			hash = HashCodeUtil.hash(hash, s);
		}

		return hash;
	}

}
