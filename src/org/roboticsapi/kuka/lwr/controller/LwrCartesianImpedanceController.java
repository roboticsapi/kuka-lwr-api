/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.controller;

import org.roboticsapi.core.util.HashCodeUtil;
import org.roboticsapi.multijoint.parameter.AbstractPositionController;

public class LwrCartesianImpedanceController extends AbstractPositionController {

	private final ImpedanceSpringSetting[] jointImpedances;
	private final ImpedanceSpringSetting xImpedance, yImpedance, zImpedance, aImpedance, bImpedance, cImpedance;

	public LwrCartesianImpedanceController(double jointStiffness, double jointDamping, double cartesianStiffness,
			double cartesianDamping) {
		this(new ImpedanceSpringSetting(jointStiffness, jointDamping),
				new ImpedanceSpringSetting(jointStiffness, jointDamping),
				new ImpedanceSpringSetting(jointStiffness, jointDamping),
				new ImpedanceSpringSetting(jointStiffness, jointDamping),
				new ImpedanceSpringSetting(jointStiffness, jointDamping),
				new ImpedanceSpringSetting(jointStiffness, jointDamping),
				new ImpedanceSpringSetting(jointStiffness, jointDamping),
				new ImpedanceSpringSetting(cartesianStiffness, cartesianDamping),
				new ImpedanceSpringSetting(cartesianStiffness, cartesianDamping),
				new ImpedanceSpringSetting(cartesianStiffness, cartesianDamping),
				new ImpedanceSpringSetting(cartesianStiffness, cartesianDamping),
				new ImpedanceSpringSetting(cartesianStiffness, cartesianDamping),
				new ImpedanceSpringSetting(cartesianStiffness, cartesianDamping));
	}

	public LwrCartesianImpedanceController(double jointStiffness, double jointDamping, double cartesianXStiffness,
			double cartesianXDamping, double cartesianYStiffness, double cartesianYDamping, double cartesianZStiffness,
			double cartesianZDamping, double cartesianAStiffness, double cartesianADamping, double cartesianBStiffness,
			double cartesianBDamping, double cartesianCStiffness, double cartesianCDamping) {
		this(new ImpedanceSpringSetting(jointStiffness, jointDamping),
				new ImpedanceSpringSetting(jointStiffness, jointDamping),
				new ImpedanceSpringSetting(jointStiffness, jointDamping),
				new ImpedanceSpringSetting(jointStiffness, jointDamping),
				new ImpedanceSpringSetting(jointStiffness, jointDamping),
				new ImpedanceSpringSetting(jointStiffness, jointDamping),
				new ImpedanceSpringSetting(jointStiffness, jointDamping),
				new ImpedanceSpringSetting(cartesianXStiffness, cartesianXDamping),
				new ImpedanceSpringSetting(cartesianYStiffness, cartesianYDamping),
				new ImpedanceSpringSetting(cartesianZStiffness, cartesianZDamping),
				new ImpedanceSpringSetting(cartesianAStiffness, cartesianADamping),
				new ImpedanceSpringSetting(cartesianBStiffness, cartesianBDamping),
				new ImpedanceSpringSetting(cartesianCStiffness, cartesianCDamping));
	}

	public LwrCartesianImpedanceController(ImpedanceSpringSetting[] jointImpedances, ImpedanceSpringSetting xImpedance,
			ImpedanceSpringSetting yImpedance, ImpedanceSpringSetting zImpedance, ImpedanceSpringSetting aImpedance,
			ImpedanceSpringSetting bImpedance, ImpedanceSpringSetting cImpedance) {
		super();
		this.jointImpedances = copyArray(jointImpedances);
		this.xImpedance = xImpedance;
		this.yImpedance = yImpedance;
		this.zImpedance = zImpedance;
		this.aImpedance = aImpedance;
		this.bImpedance = bImpedance;
		this.cImpedance = cImpedance;
	}

	private ImpedanceSpringSetting[] copyArray(ImpedanceSpringSetting[] source) {
		ImpedanceSpringSetting[] target = new ImpedanceSpringSetting[source.length];

		for (int i = 0; i < source.length; i++) {
			target[i] = source[i];
		}

		return target;
	}

	public LwrCartesianImpedanceController(ImpedanceSpringSetting jointImpedance0,
			ImpedanceSpringSetting jointImpedance1, ImpedanceSpringSetting jointImpedance2,
			ImpedanceSpringSetting jointImpedance3, ImpedanceSpringSetting jointImpedance4,
			ImpedanceSpringSetting jointImpedance5, ImpedanceSpringSetting jointImpedance6,
			ImpedanceSpringSetting xImpedance, ImpedanceSpringSetting yImpedance, ImpedanceSpringSetting zImpedance,
			ImpedanceSpringSetting aImpedance, ImpedanceSpringSetting bImpedance, ImpedanceSpringSetting cImpedance) {

		this(new ImpedanceSpringSetting[] { jointImpedance0, jointImpedance1, jointImpedance2, jointImpedance3,
				jointImpedance4, jointImpedance5, jointImpedance6 }, xImpedance, yImpedance, zImpedance, aImpedance,
				bImpedance, cImpedance);
	}

	public ImpedanceSpringSetting getJointImpedances(int jointNumber) {
		return jointImpedances[jointNumber];
	}

	public ImpedanceSpringSetting[] getJointImpedances() {
		return jointImpedances;
	}

	public ImpedanceSpringSetting getXImpedance() {
		return xImpedance;
	}

	public ImpedanceSpringSetting getYImpedance() {
		return yImpedance;
	}

	public ImpedanceSpringSetting getZImpedance() {
		return zImpedance;
	}

	public ImpedanceSpringSetting getAImpedance() {
		return aImpedance;
	}

	public ImpedanceSpringSetting getBImpedance() {
		return bImpedance;
	}

	public ImpedanceSpringSetting getCImpedance() {
		return cImpedance;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}

		if (!(obj instanceof LwrCartesianImpedanceController)) {
			return false;
		}

		LwrCartesianImpedanceController c = (LwrCartesianImpedanceController) obj;

		if (getJointImpedances().length != c.getJointImpedances().length) {
			return false;
		}

		for (int i = 0; i < getJointImpedances().length; i++) {
			if (!(getJointImpedances()[i].equals(c.getJointImpedances()[i]))) {
				return false;
			}
		}

		return getAImpedance().equals(c.getAImpedance()) && getBImpedance().equals(c.getBImpedance())
				&& getCImpedance().equals(c.getCImpedance()) && getXImpedance().equals(c.getXImpedance())
				&& getYImpedance().equals(c.getYImpedance()) && getZImpedance().equals(c.getZImpedance());
	}

	@Override
	public int hashCode() {
		int hash = super.hashCode();

		for (ImpedanceSpringSetting jimp : getJointImpedances()) {
			hash = HashCodeUtil.hash(hash, jimp);
		}

		hash = HashCodeUtil.hash(hash, getAImpedance());
		hash = HashCodeUtil.hash(hash, getBImpedance());
		hash = HashCodeUtil.hash(hash, getCImpedance());
		hash = HashCodeUtil.hash(hash, getXImpedance());
		hash = HashCodeUtil.hash(hash, getYImpedance());
		return HashCodeUtil.hash(hash, getZImpedance());
	}
}
