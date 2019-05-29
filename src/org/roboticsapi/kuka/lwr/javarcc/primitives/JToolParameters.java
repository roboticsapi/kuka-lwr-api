/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.javarcc.primitives;

import org.roboticsapi.runtime.core.types.RPIbool;
import org.roboticsapi.runtime.core.types.RPIdouble;
import org.roboticsapi.runtime.core.types.RPIint;
import org.roboticsapi.runtime.core.types.RPIstring;
import org.roboticsapi.runtime.javarcc.JInPort;
import org.roboticsapi.runtime.javarcc.JOutPort;
import org.roboticsapi.runtime.javarcc.JParameter;
import org.roboticsapi.runtime.javarcc.JPrimitive;
import org.roboticsapi.runtime.world.types.RPIFrame;
import org.roboticsapi.runtime.world.types.RPIVector;

/**
 * Module for configuring the tool of LWR robot
 */
public class JToolParameters extends JPrimitive {
	
	/** Activation port */
	final JInPort<RPIbool> inActive = add("inActive", new JInPort<RPIbool>());

	/** Center of mass */
	final JInPort<RPIFrame> inCOM = add("inCOM", new JInPort<RPIFrame>());

	/** Moment of inertia */
	final JInPort<RPIVector> inMOI = add("inMOI", new JInPort<RPIVector>());

	/** Mass of load (in kg) */
	final JInPort<RPIdouble> inMass = add("inMass", new JInPort<RPIdouble>());

	/** tool center point and orientation */
	final JInPort<RPIFrame> inTCP = add("inTCP", new JInPort<RPIFrame>());

	/** true, when the switching action has completed */
	final JOutPort<RPIbool> outCompleted = add("outCompleted", new JOutPort<RPIbool>());

	/** Result (nonzero means error) */
	final JOutPort<RPIint> outError = add("outError", new JOutPort<RPIint>());

	/** Name of the LBR */
	final JParameter<RPIstring> propRobot = add("robot", new JParameter<RPIstring>(new RPIstring("")));


	@Override
	public void checkParameters() throws IllegalArgumentException {
		// TODO: do parameter checks
	}
	
	private RPIbool completed = new RPIbool(true);
	private RPIint error = new RPIint(0);
	
	@Override
	public void updateData() {
		outCompleted.set(completed);
		outError.set(error);
	}
	
}
