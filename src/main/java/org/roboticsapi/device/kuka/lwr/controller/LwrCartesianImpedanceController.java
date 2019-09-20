/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.controller;

import org.roboticsapi.framework.cartesianmotion.controller.CartesianController;
import org.roboticsapi.framework.cartesianmotion.controller.CartesianImpedanceSettings;
import org.roboticsapi.framework.multijoint.controller.JointImpedanceSettings;

public class LwrCartesianImpedanceController implements CartesianController {

	private final JointImpedanceSettings[] jointImpedances;
	private final CartesianImpedanceSettings xImpedance, yImpedance, zImpedance, aImpedance, bImpedance, cImpedance;

	public LwrCartesianImpedanceController(double jointStiffness, double jointDamping, double cartesianStiffness,
			double cartesianDamping) {
		this(jointStiffness, jointDamping, 0, cartesianStiffness, cartesianDamping, 0);
	}

	public LwrCartesianImpedanceController(double jointStiffness, double jointDamping, double jointAdditionalTorque,
			double cartesianStiffness, double cartesianDamping, double cartesianAdditionalForce) {
		this(new JointImpedanceSettings(jointStiffness, jointDamping, jointAdditionalTorque),
				new JointImpedanceSettings(jointStiffness, jointDamping, jointAdditionalTorque),
				new JointImpedanceSettings(jointStiffness, jointDamping, jointAdditionalTorque),
				new JointImpedanceSettings(jointStiffness, jointDamping, jointAdditionalTorque),
				new JointImpedanceSettings(jointStiffness, jointDamping, jointAdditionalTorque),
				new JointImpedanceSettings(jointStiffness, jointDamping, jointAdditionalTorque),
				new JointImpedanceSettings(jointStiffness, jointDamping, jointAdditionalTorque),
				new CartesianImpedanceSettings(cartesianStiffness, cartesianDamping, cartesianAdditionalForce),
				new CartesianImpedanceSettings(cartesianStiffness, cartesianDamping, cartesianAdditionalForce),
				new CartesianImpedanceSettings(cartesianStiffness, cartesianDamping, cartesianAdditionalForce),
				new CartesianImpedanceSettings(cartesianStiffness, cartesianDamping, cartesianAdditionalForce),
				new CartesianImpedanceSettings(cartesianStiffness, cartesianDamping, cartesianAdditionalForce),
				new CartesianImpedanceSettings(cartesianStiffness, cartesianDamping, cartesianAdditionalForce));
	}

	public LwrCartesianImpedanceController(JointImpedanceSettings[] jointImpedances,
			CartesianImpedanceSettings xImpedance, CartesianImpedanceSettings yImpedance,
			CartesianImpedanceSettings zImpedance, CartesianImpedanceSettings aImpedance,
			CartesianImpedanceSettings bImpedance, CartesianImpedanceSettings cImpedance) {
		super();
		this.jointImpedances = copyArray(jointImpedances);
		this.xImpedance = xImpedance;
		this.yImpedance = yImpedance;
		this.zImpedance = zImpedance;
		this.aImpedance = aImpedance;
		this.bImpedance = bImpedance;
		this.cImpedance = cImpedance;
	}

	private JointImpedanceSettings[] copyArray(JointImpedanceSettings[] source) {
		JointImpedanceSettings[] target = new JointImpedanceSettings[source.length];

		for (int i = 0; i < source.length; i++) {
			target[i] = source[i];
		}

		return target;
	}

	public LwrCartesianImpedanceController(JointImpedanceSettings jointImpedance0,
			JointImpedanceSettings jointImpedance1, JointImpedanceSettings jointImpedance2,
			JointImpedanceSettings jointImpedance3, JointImpedanceSettings jointImpedance4,
			JointImpedanceSettings jointImpedance5, JointImpedanceSettings jointImpedance6,
			CartesianImpedanceSettings xImpedance, CartesianImpedanceSettings yImpedance,
			CartesianImpedanceSettings zImpedance, CartesianImpedanceSettings aImpedance,
			CartesianImpedanceSettings bImpedance, CartesianImpedanceSettings cImpedance) {

		this(new JointImpedanceSettings[] { jointImpedance0, jointImpedance1, jointImpedance2, jointImpedance3,
				jointImpedance4, jointImpedance5, jointImpedance6 }, xImpedance, yImpedance, zImpedance, aImpedance,
				bImpedance, cImpedance);
	}

	public JointImpedanceSettings getJointImpedances(int jointNumber) {
		return jointImpedances[jointNumber];
	}

	public JointImpedanceSettings[] getJointImpedances() {
		return jointImpedances;
	}

	public CartesianImpedanceSettings getXImpedance() {
		return xImpedance;
	}

	public CartesianImpedanceSettings getYImpedance() {
		return yImpedance;
	}

	public CartesianImpedanceSettings getZImpedance() {
		return zImpedance;
	}

	public CartesianImpedanceSettings getAImpedance() {
		return aImpedance;
	}

	public CartesianImpedanceSettings getBImpedance() {
		return bImpedance;
	}

	public CartesianImpedanceSettings getCImpedance() {
		return cImpedance;
	}
}
