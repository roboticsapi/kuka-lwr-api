/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.javarcc.primitives;

import org.roboticsapi.runtime.core.types.RPIdouble;
import org.roboticsapi.runtime.core.types.RPIint;
import org.roboticsapi.runtime.core.types.RPIstring;
import org.roboticsapi.runtime.javarcc.JOutPort;
import org.roboticsapi.runtime.javarcc.JParameter;
import org.roboticsapi.runtime.javarcc.JPrimitive;

/**
 * Torque Sensor for LBR robot
 */
public class JTMonitor extends JPrimitive {
	RPIdouble zero = new RPIdouble(0);
	
	/** Estimated torque of axis */
	final JOutPort<RPIdouble> outEst = add("outEst", new JOutPort<RPIdouble>());

	/** Measured torque of axis */
	final JOutPort<RPIdouble> outMsr = add("outMsr", new JOutPort<RPIdouble>());

	/** Name of the LBR */
	final JParameter<RPIstring> propRobot = add("Robot", new JParameter<RPIstring>(new RPIstring("")));

	/** Axis of LWR for Torque */
	final JParameter<RPIint> propAxis = add("Axis", new JParameter<RPIint>(new RPIint("0")));


	@Override
	public void checkParameters() throws IllegalArgumentException {
		// TODO: do parameter checks
	}
	
	@Override
	public void updateData() {
		outMsr.set(zero);
		outEst.set(zero);
	}
	
}
