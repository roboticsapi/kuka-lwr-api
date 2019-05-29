/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.javarcc.devices;

import org.roboticsapi.runtime.core.javarcc.devices.JDevice;
import org.roboticsapi.runtime.core.types.RPIdouble;
import org.roboticsapi.runtime.multijoint.javarcc.devices.JMockMultijointDevice;
import org.roboticsapi.runtime.multijoint.javarcc.interfaces.JMultijointInterface;
import org.roboticsapi.runtime.robot.javarcc.interfaces.JArmKinematicsInterface;
import org.roboticsapi.runtime.world.javarcc.primitives.RPICalc;
import org.roboticsapi.runtime.world.types.RPIFrame;
import org.roboticsapi.world.mutable.MutableTransformation;

public class JMockLwr extends JMockMultijointDevice implements JDevice, JArmKinematicsInterface, JMultijointInterface {
	private final LwrKin kin;
	MutableTransformation iiwaRotation = RPICalc.frameCreate();
	MutableTransformation trans = RPICalc.frameCreate();
	RPIdouble alpha = RPICalc.rpiDoubleCreate();
	RPIFrame pos = RPICalc.rpiFrameCreate();

	public JMockLwr(double[] linklength, double[] min, double[] max, boolean iiwaKinematics) {
		super(7, min, max, new double[] { 0, 0, 0, 0, 0, 0, 0 });
		kin = new LwrKin(linklength, min, max);
		if (iiwaKinematics) {
			iiwaRotation.setVectorEuler(0, 0, 0, Math.PI, 0, 0);
		}
	}

	@Override
	public RPIFrame kin(double[] joints, RPIFrame ret) {
		kin.kin(joints, pos, alpha);

		RPICalc.rpiToFrame(pos, trans);
		iiwaRotation.multiplyTo(trans, trans);
		RPICalc.frameToRpi(trans, ret);

		return ret;
	}

	@Override
	public double[] invKin(double[] hintJoints, RPIFrame frame) {
		kin.kin(hintJoints, pos, alpha);

		RPICalc.rpiToFrame(frame, trans);
		iiwaRotation.multiplyTo(trans, trans);
		RPICalc.frameToRpi(trans, pos);

		return kin.invKin(pos, hintJoints, alpha.get(), new double[7]);
	}

	public double alphaKin(double j0, double j1, double j2, double j3, double j4, double j5, double j6) {
		kin.kin(new double[] { j0, j1, j2, j3, j4, j5, j6 }, pos, alpha);

		return alpha.get();
	}

	public double[] alphaInvKin(RPIFrame frame, double alpha, double[] hintJoints) {
		RPICalc.rpiToFrame(frame, trans);
		iiwaRotation.multiplyTo(trans, trans);
		RPICalc.frameToRpi(trans, pos);

		return kin.invKin(pos, hintJoints, alpha, new double[7]);
	}

}
