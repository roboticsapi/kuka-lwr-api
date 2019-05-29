/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.javarcc.primitives;

import org.roboticsapi.runtime.core.types.RPIdouble;
import org.roboticsapi.runtime.core.types.RPIstring;
import org.roboticsapi.runtime.javarcc.JOutPort;
import org.roboticsapi.runtime.javarcc.JParameter;
import org.roboticsapi.runtime.javarcc.JPrimitive;

/**
 * Force Sensor for LBR robot
 */
public class JFMonitor extends JPrimitive {
	RPIdouble zero = new RPIdouble(0);
	
	/** Estimated force in X direction */
	final JOutPort<RPIdouble> outTcpFx = add("outTcpFx", new JOutPort<RPIdouble>());

	/** Estimated force in Y direction */
	final JOutPort<RPIdouble> outTcpFy = add("outTcpFy", new JOutPort<RPIdouble>());

	/** Estimated force in Z direction */
	final JOutPort<RPIdouble> outTcpFz = add("outTcpFz", new JOutPort<RPIdouble>());

	/** Estimated torque around X direction */
	final JOutPort<RPIdouble> outTcpTx = add("outTcpTx", new JOutPort<RPIdouble>());

	/** Estimated torque around Y direction */
	final JOutPort<RPIdouble> outTcpTy = add("outTcpTy", new JOutPort<RPIdouble>());

	/** Estimated torque around Z direction */
	final JOutPort<RPIdouble> outTcpTz = add("outTcpTz", new JOutPort<RPIdouble>());

	/** Name of the LBR */
	final JParameter<RPIstring> propRobot = add("Robot", new JParameter<RPIstring>(new RPIstring("")));


	@Override
	public void checkParameters() throws IllegalArgumentException {
		// TODO: do parameter checks
	}
	
	@Override
	public void updateData() {
		outTcpFx.set(zero);
		outTcpFy.set(zero);
		outTcpFz.set(zero);
		outTcpTx.set(zero);
		outTcpTy.set(zero);
		outTcpTz.set(zero);
	}
	
}
