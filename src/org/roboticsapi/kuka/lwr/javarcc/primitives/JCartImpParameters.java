/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.javarcc.primitives;

import org.roboticsapi.runtime.core.types.RPIbool;
import org.roboticsapi.runtime.core.types.RPIstring;
import org.roboticsapi.runtime.javarcc.JInPort;
import org.roboticsapi.runtime.javarcc.JParameter;
import org.roboticsapi.runtime.javarcc.JPrimitive;
import org.roboticsapi.runtime.world.types.RPIWrench;

/**
 * Module for changing Cartesian impedance values (stiffness etc.)
 */
public class JCartImpParameters extends JPrimitive {
	
	/** Activation port */
	final JInPort<RPIbool> inActive = add("inActive", new JInPort<RPIbool>());

	/** Additional torque for selected axis */
	final JInPort<RPIWrench> inAddTorque = add("inAddTorque", new JInPort<RPIWrench>());

	/** Damping value for selected axis */
	final JInPort<RPIWrench> inDamping = add("inDamping", new JInPort<RPIWrench>());

	/** Stiffness value for selected axis */
	final JInPort<RPIWrench> inStiffness = add("inStiffness", new JInPort<RPIWrench>());

	/** Name of the LBR */
	final JParameter<RPIstring> propRobot = add("Robot", new JParameter<RPIstring>(new RPIstring("")));


	@Override
	public void checkParameters() throws IllegalArgumentException {
		// TODO: do parameter checks
	}
	
	@Override
	public void updateData() {
	}
	
}
