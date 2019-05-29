/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.javarcc.primitives;

import org.roboticsapi.runtime.core.types.RPIbool;
import org.roboticsapi.runtime.core.types.RPIint;
import org.roboticsapi.runtime.core.types.RPIstring;
import org.roboticsapi.runtime.javarcc.JInPort;
import org.roboticsapi.runtime.javarcc.JOutPort;
import org.roboticsapi.runtime.javarcc.JParameter;
import org.roboticsapi.runtime.javarcc.JPrimitive;

/**
 * Module for switching the control mode of the LWR robot
 */
public class JControlStrategy extends JPrimitive {

	/** Activation port */
	final JInPort<RPIbool> inActive = add("inActive", new JInPort<RPIbool>());

	/** The required control strategy */
	final JInPort<RPIint> inStrategy = add("inStrategy", new JInPort<RPIint>());

	/** true, when the switching action has completed */
	final JOutPort<RPIbool> outCompleted = add("outCompleted", new JOutPort<RPIbool>());

	/** Result (nonzero means error) */
	final JOutPort<RPIint> outError = add("outError", new JOutPort<RPIint>());

	/** Name of the LBR */
	final JParameter<RPIstring> propRobot = add("Robot", new JParameter<RPIstring>(new RPIstring("")));

	private RPIbool completed = new RPIbool(true);
	private RPIint error = new RPIint(0);

	@Override
	public void checkParameters() throws IllegalArgumentException {
		// TODO: do parameter checks
	}

	@Override
	public void updateData() {
		outCompleted.set(completed);
		outError.set(error);
	}

}
