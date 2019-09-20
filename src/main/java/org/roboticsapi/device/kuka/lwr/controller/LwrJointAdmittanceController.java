/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.controller;

import org.roboticsapi.framework.multijoint.controller.JointController;
import org.roboticsapi.framework.multijoint.controller.JointImpedanceSettings;

public class LwrJointAdmittanceController implements JointController {
	private final JointImpedanceSettings[] jointImpedances;

	public LwrJointAdmittanceController(JointImpedanceSettings[] jointImpedances) {
		super();
		this.jointImpedances = copyArray(jointImpedances);

	}

	private JointImpedanceSettings[] copyArray(JointImpedanceSettings[] source) {
		JointImpedanceSettings[] target = new JointImpedanceSettings[source.length];

		for (int i = 0; i < source.length; i++) {
			target[i] = source[i];
		}

		return target;
	}

	public LwrJointAdmittanceController(JointImpedanceSettings jointImpedance0, JointImpedanceSettings jointImpedance1,
			JointImpedanceSettings jointImpedance2, JointImpedanceSettings jointImpedance3,
			JointImpedanceSettings jointImpedance4, JointImpedanceSettings jointImpedance5,
			JointImpedanceSettings jointImpedance6) {

		this(new JointImpedanceSettings[] { jointImpedance0, jointImpedance1, jointImpedance2, jointImpedance3,
				jointImpedance4, jointImpedance5, jointImpedance6 });
	}

	public JointImpedanceSettings getJointImpedances(int jointNumber) {
		return jointImpedances[jointNumber];
	}

	public JointImpedanceSettings[] getJointImpedances() {
		return jointImpedances;
	}

}
