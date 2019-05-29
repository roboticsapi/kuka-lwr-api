/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.javarcc.primitives;

import org.roboticsapi.kuka.lwr.javarcc.devices.JMockLwr;
import org.roboticsapi.runtime.core.types.RPIbool;
import org.roboticsapi.runtime.core.types.RPIdouble;
import org.roboticsapi.runtime.core.types.RPIstring;
import org.roboticsapi.runtime.javarcc.JInPort;
import org.roboticsapi.runtime.javarcc.JOutPort;
import org.roboticsapi.runtime.javarcc.JParameter;
import org.roboticsapi.runtime.javarcc.JPrimitive;
import org.roboticsapi.runtime.world.types.RPIFrame;

/**
 * Direct kinematics module for LWR robots
 */
public class JKin extends JPrimitive {
	JMockLwr devins;
	RPIFrame ret = new RPIFrame();
	RPIdouble alpha = new RPIdouble();

	/** Activation port */
	final JInPort<RPIbool> inActive = add("inActive", new JInPort<RPIbool>());

	/** Value of Axis 1 */
	final JInPort<RPIdouble> inJ1 = add("inJ1", new JInPort<RPIdouble>());

	/** Value of Axis 2 */
	final JInPort<RPIdouble> inJ2 = add("inJ2", new JInPort<RPIdouble>());

	/** Value of Axis 3 */
	final JInPort<RPIdouble> inJ3 = add("inJ3", new JInPort<RPIdouble>());

	/** Value of Axis 4 */
	final JInPort<RPIdouble> inJ4 = add("inJ4", new JInPort<RPIdouble>());

	/** Value of Axis 5 */
	final JInPort<RPIdouble> inJ5 = add("inJ5", new JInPort<RPIdouble>());

	/** Value of Axis 6 */
	final JInPort<RPIdouble> inJ6 = add("inJ6", new JInPort<RPIdouble>());

	/** Value of Axis 7 */
	final JInPort<RPIdouble> inJ7 = add("inJ7", new JInPort<RPIdouble>());

	/** Resulting alpha value */
	final JOutPort<RPIdouble> outAlpha = add("outAlpha", new JOutPort<RPIdouble>());

	/** Resulting frame */
	final JOutPort<RPIFrame> outFrame = add("outFrame", new JOutPort<RPIFrame>());

	/** Name of the LBR */
	final JParameter<RPIstring> propRobot = add("Robot", new JParameter<RPIstring>(new RPIstring("")));

	@Override
	public void checkParameters() throws IllegalArgumentException {
		devins = device(propRobot, JMockLwr.class);
	}

	@Override
	public void updateData() {
		double[] joints = new double[] { inJ1.get().get(), inJ2.get().get(), inJ3.get().get(), inJ4.get().get(),
				inJ5.get().get(), inJ6.get().get(), inJ7.get().get() };
		devins.kin(joints, ret);
		alpha.set(devins.alphaKin(joints[0], joints[1], joints[2], joints[3], joints[4], joints[5], joints[6]));
		outFrame.set(ret);
		outAlpha.set(alpha);
	}

}
