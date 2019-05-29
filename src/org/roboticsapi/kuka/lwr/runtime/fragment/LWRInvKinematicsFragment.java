/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.fragment;

import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.kuka.lwr.LWRRedundancyStrategy;
import org.roboticsapi.kuka.lwr.runtime.primitives.InvKin;
import org.roboticsapi.robot.parameter.RedundancyDeviceParameter;
import org.roboticsapi.runtime.mapping.MappingException;
import org.roboticsapi.runtime.mapping.net.DataflowInPort;
import org.roboticsapi.runtime.mapping.net.DataflowOutPort;
import org.roboticsapi.runtime.multijoint.JointsDataflow;
import org.roboticsapi.runtime.robot.driver.SoftRobotRobotArmDriver;
import org.roboticsapi.runtime.robot.driver.mapper.SoftRobotRobotArmMapper.NullspaceInvKinematicsFragment;
import org.roboticsapi.runtime.world.dataflow.RelationDataflow;

public class LWRInvKinematicsFragment extends NullspaceInvKinematicsFragment {

	private final DataflowInPort inFrame;
	private final DataflowInPort inHintJoints;
	private final DataflowInPort inNullspaceJoints;
	private final DataflowOutPort outJoints;

	public LWRInvKinematicsFragment(SoftRobotRobotArmDriver driver, DeviceParameterBag parameters)
			throws MappingException {
		super("OLWR.InvKinematics");

		int strategyNr = 0;
		final RedundancyDeviceParameter rdp = parameters.get(RedundancyDeviceParameter.class);

		if (rdp != null && rdp.getRedundancyStrategy() instanceof LWRRedundancyStrategy) {
			strategyNr = ((LWRRedundancyStrategy) rdp.getRedundancyStrategy()).getNr();
		}

		final InvKin kin = new InvKin();
		kin.setStrategy(strategyNr);
		kin.setRobot(driver.getDeviceName());
		add(kin);
		inFrame = addInPort(new RelationDataflow(driver.getBase(), driver.getFlange()), true, kin.getInFrame());
		inHintJoints = addInPort(new JointsDataflow(7, driver), true, kin.getInHintJ1(), kin.getInHintJ2(),
				kin.getInHintJ3(), kin.getInHintJ4(), kin.getInHintJ5(), kin.getInHintJ6(), kin.getInHintJ7());
		inNullspaceJoints = addInPort(new JointsDataflow(7, driver), false, kin.getInNullspaceJ1(),
				kin.getInNullspaceJ2(), kin.getInNullspaceJ3(), kin.getInNullspaceJ4(), kin.getInNullspaceJ5(),
				kin.getInNullspaceJ6(), kin.getInNullspaceJ7());
		outJoints = addOutPort(new JointsDataflow(7, driver), true, kin.getOutJ1(), kin.getOutJ2(), kin.getOutJ3(),
				kin.getOutJ4(), kin.getOutJ5(), kin.getOutJ6(), kin.getOutJ7());

	}

	@Override
	public DataflowInPort getInFrame() {
		return inFrame;
	}

	@Override
	public DataflowOutPort getOutJoints() {
		return outJoints;
	}

	@Override
	public DataflowInPort getInHintJoints() {
		return inHintJoints;
	}

	@Override
	public DataflowInPort getInNullspaceJoints() {
		return inNullspaceJoints;
	}

}
