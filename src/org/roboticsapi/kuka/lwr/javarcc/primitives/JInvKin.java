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
import org.roboticsapi.runtime.core.types.RPIint;
import org.roboticsapi.runtime.core.types.RPIstring;
import org.roboticsapi.runtime.javarcc.JInPort;
import org.roboticsapi.runtime.javarcc.JOutPort;
import org.roboticsapi.runtime.javarcc.JParameter;
import org.roboticsapi.runtime.javarcc.JPrimitive;
import org.roboticsapi.runtime.world.types.RPIFrame;

/**
 * Inverse kinematics module for LBR robots
 */
public class JInvKin extends JPrimitive {
	JMockLwr devins;
	RPIdouble r0 = new RPIdouble(), r1 = new RPIdouble(), r2 = new RPIdouble(), r3 = new RPIdouble(),
			r4 = new RPIdouble(), r5 = new RPIdouble(), r6 = new RPIdouble();
	RPIFrame pos = new RPIFrame();

	/** Activation port */
	final JInPort<RPIbool> inActive = add("inActive", new JInPort<RPIbool>());

	/** Destination frame */
	final JInPort<RPIFrame> inFrame = add("inFrame", new JInPort<RPIFrame>());

	/** Hint joint value 1 */
	final JInPort<RPIdouble> inHintJ1 = add("inHintJ1", new JInPort<RPIdouble>());

	/** Hint joint value 2 */
	final JInPort<RPIdouble> inHintJ2 = add("inHintJ2", new JInPort<RPIdouble>());

	/** Hint joint value 3 */
	final JInPort<RPIdouble> inHintJ3 = add("inHintJ3", new JInPort<RPIdouble>());

	/** Hint joint value 4 */
	final JInPort<RPIdouble> inHintJ4 = add("inHintJ4", new JInPort<RPIdouble>());

	/** Hint joint value 5 */
	final JInPort<RPIdouble> inHintJ5 = add("inHintJ5", new JInPort<RPIdouble>());

	/** Hint joint value 6 */
	final JInPort<RPIdouble> inHintJ6 = add("inHintJ6", new JInPort<RPIdouble>());

	/** Hint joint value 7 */
	final JInPort<RPIdouble> inHintJ7 = add("inHintJ7", new JInPort<RPIdouble>());

	/** Nullspace joint value 1 */
	final JInPort<RPIdouble> inNullspaceJ1 = add("inNullspaceJ1", new JInPort<RPIdouble>());

	/** Nullspace joint value 2 */
	final JInPort<RPIdouble> inNullspaceJ2 = add("inNullspaceJ2", new JInPort<RPIdouble>());

	/** Nullspace joint value 3 */
	final JInPort<RPIdouble> inNullspaceJ3 = add("inNullspaceJ3", new JInPort<RPIdouble>());

	/** Nullspace joint value 4 */
	final JInPort<RPIdouble> inNullspaceJ4 = add("inNullspaceJ4", new JInPort<RPIdouble>());

	/** Nullspace joint value 5 */
	final JInPort<RPIdouble> inNullspaceJ5 = add("inNullspaceJ5", new JInPort<RPIdouble>());

	/** Nullspace joint value 6 */
	final JInPort<RPIdouble> inNullspaceJ6 = add("inNullspaceJ6", new JInPort<RPIdouble>());

	/** Nullspace joint value 7 */
	final JInPort<RPIdouble> inNullspaceJ7 = add("inNullspaceJ7", new JInPort<RPIdouble>());

	/** Result angle for joint 1 */
	final JOutPort<RPIdouble> outJ1 = add("outJ1", new JOutPort<RPIdouble>());

	/** Result angle for joint 2 */
	final JOutPort<RPIdouble> outJ2 = add("outJ2", new JOutPort<RPIdouble>());

	/** Result angle for joint 3 */
	final JOutPort<RPIdouble> outJ3 = add("outJ3", new JOutPort<RPIdouble>());

	/** Result angle for joint 4 */
	final JOutPort<RPIdouble> outJ4 = add("outJ4", new JOutPort<RPIdouble>());

	/** Result angle for joint 5 */
	final JOutPort<RPIdouble> outJ5 = add("outJ5", new JOutPort<RPIdouble>());

	/** Result angle for joint 6 */
	final JOutPort<RPIdouble> outJ6 = add("outJ6", new JOutPort<RPIdouble>());

	/** Result angle for joint 7 */
	final JOutPort<RPIdouble> outJ7 = add("outJ7", new JOutPort<RPIdouble>());

	/** Redundancy strategy */
	final JParameter<RPIint> propStrategy = add("Strategy", new JParameter<RPIint>(new RPIint("1")));

	/** Name of the LBR */
	final JParameter<RPIstring> propRobot = add("Robot", new JParameter<RPIstring>(new RPIstring("")));

	@Override
	public void checkParameters() throws IllegalArgumentException {
		devins = device(propRobot, JMockLwr.class);
	}

	@Override
	public void updateData() {
		RPIdouble j0 = inHintJ1.get(), j1 = inHintJ2.get(), j2 = inHintJ3.get(), j3 = inHintJ4.get(),
				j4 = inHintJ5.get(), j5 = inHintJ6.get(), j6 = inHintJ7.get();
		RPIdouble n0 = inNullspaceJ1.get(j0), n1 = inNullspaceJ2.get(j1), n2 = inNullspaceJ3.get(j2),
				n3 = inNullspaceJ4.get(j3), n4 = inNullspaceJ5.get(j4), n5 = inNullspaceJ6.get(j5),
				n6 = inNullspaceJ7.get(j6);

		double alpha = devins.alphaKin(n0.get(), n1.get(), n2.get(), n3.get(), n4.get(), n5.get(), n6.get());
		double[] ret = devins.alphaInvKin(inFrame.get(), alpha,
				new double[] { j0.get(), j1.get(), j2.get(), j3.get(), j4.get(), j5.get(), j6.get() });

		r0.set(ret[0]);
		r1.set(ret[1]);
		r2.set(ret[2]);
		r3.set(ret[3]);
		r4.set(ret[4]);
		r5.set(ret[5]);
		r6.set(ret[6]);

		outJ1.set(r0);
		outJ2.set(r1);
		outJ3.set(r2);
		outJ4.set(r3);
		outJ5.set(r4);
		outJ6.set(r5);
		outJ7.set(r6);

	}

}
