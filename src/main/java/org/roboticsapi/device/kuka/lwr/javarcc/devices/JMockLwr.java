/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.javarcc.devices;

import org.roboticsapi.core.world.mutable.MutableTransformation;
import org.roboticsapi.facet.javarcc.devices.JDevice;
import org.roboticsapi.facet.javarcc.primitives.world.RPICalc;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIdouble;
import org.roboticsapi.facet.runtime.rpi.world.types.RPIFrame;
import org.roboticsapi.framework.multijoint.javarcc.devices.JMockMultijointDevice;
import org.roboticsapi.framework.multijoint.javarcc.interfaces.JMultijointInterface;
import org.roboticsapi.framework.robot.javarcc.interfaces.JArmKinematicsInterface;

public class JMockLwr extends JMockMultijointDevice implements JDevice, JArmKinematicsInterface, JMultijointInterface {
	private final LwrKin kin;
	MutableTransformation iiwaRotation = RPICalc.frameCreate();
	MutableTransformation trans = RPICalc.frameCreate();
	RPIdouble alpha = RPICalc.rpiDoubleCreate();
	RPIFrame pos = RPICalc.rpiFrameCreate();

	public JMockLwr(double[] linklength, double[] min, double[] max, double[] maxVel, double[] maxAcc,
			boolean iiwaKinematics) {
		super(7, min, max, new double[] { 0, 0, 0, 0, 0, 0, 0 }, maxVel, maxAcc);
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

}
