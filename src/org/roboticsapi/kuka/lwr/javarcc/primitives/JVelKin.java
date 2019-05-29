/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.javarcc.primitives;

import org.roboticsapi.runtime.core.types.RPIbool;
import org.roboticsapi.runtime.core.types.RPIdouble;
import org.roboticsapi.runtime.core.types.RPIstring;
import org.roboticsapi.runtime.javarcc.JInPort;
import org.roboticsapi.runtime.javarcc.JOutPort;
import org.roboticsapi.runtime.javarcc.JParameter;
import org.roboticsapi.runtime.javarcc.JPrimitive;
import org.roboticsapi.runtime.world.types.RPITwist;

/**
 * Direct velocity kinematics module for LWR robots
 */
public class JVelKin extends JPrimitive {
	
	/** Activation port */
	final JInPort<RPIbool> inActive = add("inActive", new JInPort<RPIbool>());

	/** Position of Axis 1 */
	final JInPort<RPIdouble> inJ1 = add("inJ1", new JInPort<RPIdouble>());

	/** Position of Axis 2 */
	final JInPort<RPIdouble> inJ2 = add("inJ2", new JInPort<RPIdouble>());

	/** Position of Axis 3 */
	final JInPort<RPIdouble> inJ3 = add("inJ3", new JInPort<RPIdouble>());

	/** Position of Axis 4 */
	final JInPort<RPIdouble> inJ4 = add("inJ4", new JInPort<RPIdouble>());

	/** Position of Axis 5 */
	final JInPort<RPIdouble> inJ5 = add("inJ5", new JInPort<RPIdouble>());

	/** Position of Axis 6 */
	final JInPort<RPIdouble> inJ6 = add("inJ6", new JInPort<RPIdouble>());

	/** Position of Axis 7 */
	final JInPort<RPIdouble> inJ7 = add("inJ7", new JInPort<RPIdouble>());

	/** Velocity of Axis 1 */
	final JInPort<RPIdouble> inV1 = add("inV1", new JInPort<RPIdouble>());

	/** Velocity of Axis 2 */
	final JInPort<RPIdouble> inV2 = add("inV2", new JInPort<RPIdouble>());

	/** Velocity of Axis 3 */
	final JInPort<RPIdouble> inV3 = add("inV3", new JInPort<RPIdouble>());

	/** Velocity of Axis 4 */
	final JInPort<RPIdouble> inV4 = add("inV4", new JInPort<RPIdouble>());

	/** Velocity of Axis 5 */
	final JInPort<RPIdouble> inV5 = add("inV5", new JInPort<RPIdouble>());

	/** Velocity of Axis 6 */
	final JInPort<RPIdouble> inV6 = add("inV6", new JInPort<RPIdouble>());

	/** Velocity of Axis 7 */
	final JInPort<RPIdouble> inV7 = add("inV7", new JInPort<RPIdouble>());

	/** Resulting Twist */
	final JOutPort<RPITwist> outTwist = add("outTwist", new JOutPort<RPITwist>());

	/** Resulting alpha velocity */
	final JOutPort<RPIdouble> outVAlpha = add("outVAlpha", new JOutPort<RPIdouble>());

	/** Name of the LBR */
	final JParameter<RPIstring> propRobot = add("Robot", new JParameter<RPIstring>(new RPIstring("")));


	@Override
	public void checkParameters() throws IllegalArgumentException {
		// TODO: do parameter checks
	}
	
	@Override
	public void updateData() {
		throw new RuntimeException("Not implemented");
		// TODO: perform computations based on local variables and InPort values, 
		// write local variables and OutPorts
	}
	
}
