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
import org.roboticsapi.runtime.javarcc.JParameter;
import org.roboticsapi.runtime.javarcc.JPrimitive;

/**
 * Module for changing joint impedance control values (stiffness etc.)
 */
public class JJointImpParameters extends JPrimitive {
	
	/** Activation port */
	final JInPort<RPIbool> inActive = add("inActive", new JInPort<RPIbool>());

	/** Additional torque for selected axis */
	final JInPort<RPIdouble> inAddTorque = add("inAddTorque", new JInPort<RPIdouble>());

	/** Damping value for selected axis */
	final JInPort<RPIdouble> inDamping = add("inDamping", new JInPort<RPIdouble>());

	/** Stiffness value for selected axis */
	final JInPort<RPIdouble> inStiffness = add("inStiffness", new JInPort<RPIdouble>());

	/** Name of the LBR */
	final JParameter<RPIstring> propRobot = add("Robot", new JParameter<RPIstring>(new RPIstring("")));

	/** Number of axis to control (0-based) */
	final JParameter<RPIint> propAxis = add("Axis", new JParameter<RPIint>(new RPIint("0")));


	@Override
	public void checkParameters() throws IllegalArgumentException {
		// TODO: do parameter checks
	}
	
	@Override
	public void updateData() {
		// TODO: perform computations based on local variables and InPort values, 
		// write local variables and OutPorts
	}
	
}
