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
 * Force/Torque Sensor for LBR robot
 */
public class JFTMonitor extends JPrimitive {
	RPIdouble zero = new RPIdouble(0);
	
	/** Estimated torque of axis 1 */
	final JOutPort<RPIdouble> outEstJ1 = add("outEstJ1", new JOutPort<RPIdouble>());

	/** Estimated torque of axis 2 */
	final JOutPort<RPIdouble> outEstJ2 = add("outEstJ2", new JOutPort<RPIdouble>());

	/** Estimated torque of axis 3 */
	final JOutPort<RPIdouble> outEstJ3 = add("outEstJ3", new JOutPort<RPIdouble>());

	/** Estimated torque of axis 4 */
	final JOutPort<RPIdouble> outEstJ4 = add("outEstJ4", new JOutPort<RPIdouble>());

	/** Estimated torque of axis 5 */
	final JOutPort<RPIdouble> outEstJ5 = add("outEstJ5", new JOutPort<RPIdouble>());

	/** Estimated torque of axis 6 */
	final JOutPort<RPIdouble> outEstJ6 = add("outEstJ6", new JOutPort<RPIdouble>());

	/** Estimated torque of axis 7 */
	final JOutPort<RPIdouble> outEstJ7 = add("outEstJ7", new JOutPort<RPIdouble>());

	/** Measured torque of axis 1 */
	final JOutPort<RPIdouble> outMsrJ1 = add("outMsrJ1", new JOutPort<RPIdouble>());

	/** Measured torque of axis 2 */
	final JOutPort<RPIdouble> outMsrJ2 = add("outMsrJ2", new JOutPort<RPIdouble>());

	/** Measured torque of axis 3 */
	final JOutPort<RPIdouble> outMsrJ3 = add("outMsrJ3", new JOutPort<RPIdouble>());

	/** Measured torque of axis 4 */
	final JOutPort<RPIdouble> outMsrJ4 = add("outMsrJ4", new JOutPort<RPIdouble>());

	/** Measured torque of axis 5 */
	final JOutPort<RPIdouble> outMsrJ5 = add("outMsrJ5", new JOutPort<RPIdouble>());

	/** Measured torque of axis 6 */
	final JOutPort<RPIdouble> outMsrJ6 = add("outMsrJ6", new JOutPort<RPIdouble>());

	/** Measured torque of axis 7 */
	final JOutPort<RPIdouble> outMsrJ7 = add("outMsrJ7", new JOutPort<RPIdouble>());

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
		outEstJ1.set(zero);
		outEstJ2.set(zero);
		outEstJ3.set(zero);
		outEstJ4.set(zero);
		outEstJ5.set(zero);
		outEstJ6.set(zero);
		outEstJ7.set(zero);
		outTcpFx.set(zero);
		outTcpFy.set(zero);
		outTcpFz.set(zero);
		outTcpTx.set(zero);
		outTcpTy.set(zero);
		outTcpTz.set(zero);
	}
	
}
