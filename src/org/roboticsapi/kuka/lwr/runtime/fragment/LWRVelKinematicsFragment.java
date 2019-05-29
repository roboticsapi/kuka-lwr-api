/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.fragment;

import org.roboticsapi.kuka.lwr.runtime.driver.SoftRobotLWRDriver;
import org.roboticsapi.kuka.lwr.runtime.primitives.VelKin;
import org.roboticsapi.runtime.mapping.net.DataflowInPort;
import org.roboticsapi.runtime.mapping.net.DataflowOutPort;
import org.roboticsapi.runtime.multijoint.JointVelsDataflow;
import org.roboticsapi.runtime.multijoint.JointsDataflow;
import org.roboticsapi.runtime.robot.driver.mapper.SoftRobotRobotArmMapper.VelKinematicsFragment;
import org.roboticsapi.runtime.world.dataflow.VelocityDataflow;
import org.roboticsapi.world.sensor.VelocitySensor;

public class LWRVelKinematicsFragment extends VelKinematicsFragment {
	private final DataflowInPort jointInPort;
	private final DataflowInPort jointVelInPort;
	private final DataflowOutPort twistOutPort;

	public LWRVelKinematicsFragment(SoftRobotLWRDriver driver) {
		super("OLBR.VelKinematics");
		final VelKin kin = new VelKin(driver.getDeviceName());
		add(kin);

		jointInPort = addInPort(new JointsDataflow(7, driver), true, kin.getInJ1(), kin.getInJ2(), kin.getInJ3(),
				kin.getInJ4(), kin.getInJ5(), kin.getInJ6(), kin.getInJ7());
		jointVelInPort = addInPort(new JointVelsDataflow(7, driver), true, kin.getInV1(), kin.getInV2(), kin.getInV3(),
				kin.getInV4(), kin.getInV5(), kin.getInV6(), kin.getInV7());
		VelocitySensor sensor = driver.getMeasuredVelocitySensor();
		twistOutPort = addOutPort(new VelocityDataflow(sensor.getMovingFrame(), sensor.getReferenceFrame(),
				sensor.getPivotPoint(), sensor.getOrientation()), false, kin.getOutTwist());
	}

	@Override
	public DataflowInPort getInJoints() {
		return jointInPort;
	}

	@Override
	public DataflowInPort getInJointVels() {
		return jointVelInPort;
	}

	@Override
	public DataflowOutPort getOutVelocity() {
		return twistOutPort;
	}
}
