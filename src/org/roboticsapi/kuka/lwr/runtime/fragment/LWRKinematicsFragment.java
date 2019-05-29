/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.fragment;

import org.roboticsapi.kuka.lwr.runtime.driver.SoftRobotLWRDriver;
import org.roboticsapi.kuka.lwr.runtime.primitives.Kin;
import org.roboticsapi.runtime.mapping.net.DataflowInPort;
import org.roboticsapi.runtime.mapping.net.DataflowOutPort;
import org.roboticsapi.runtime.multijoint.JointsDataflow;
import org.roboticsapi.runtime.robot.AlphaDataflow;
import org.roboticsapi.runtime.robot.driver.mapper.SoftRobotRobotArmMapper.KinematicsFragment;
import org.roboticsapi.runtime.world.dataflow.RelationDataflow;

public class LWRKinematicsFragment extends KinematicsFragment {
	private final DataflowOutPort alphaOutPort;
	private final DataflowInPort jointInPort;
	private final DataflowOutPort frameOutPort;

	public LWRKinematicsFragment(SoftRobotLWRDriver robotDriver) {
		super("OLBR.Kinematics");
		final Kin kin = new Kin(robotDriver.getDeviceName());
		add(kin);

		alphaOutPort = addOutPort(new AlphaDataflow(), false, kin.getOutAlpha());
		jointInPort = addInPort(new JointsDataflow(7, robotDriver), true, kin.getInJ1(), kin.getInJ2(), kin.getInJ3(),
				kin.getInJ4(), kin.getInJ5(), kin.getInJ6(), kin.getInJ7());
		frameOutPort = addOutPort(new RelationDataflow(robotDriver.getBase(), robotDriver.getFlange()), false,
				kin.getOutFrame());
	}

	@Override
	public DataflowInPort getInJoints() {
		return jointInPort;
	}

	@Override
	public DataflowOutPort getOutFrame() {
		return frameOutPort;
	}

	public DataflowOutPort getAlphaOutPort() {
		return alphaOutPort;
	}
}
