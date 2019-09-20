/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr;

import java.util.List;

import org.roboticsapi.core.DeviceParameters;
import org.roboticsapi.core.InvalidParametersException;
import org.roboticsapi.framework.cartesianmotion.parameter.CartesianParameters;
import org.roboticsapi.framework.robot.RedundancyStrategy;
import org.roboticsapi.framework.robot.parameter.CompliantRobotTool;
import org.roboticsapi.framework.robot.parameter.RobotToolParameter;

public abstract class AbstractLwrIiwa extends AbstractLwr {

	public AbstractLwrIiwa() {
	}

	@Override
	public void validateParameters(DeviceParameters parameters) throws InvalidParametersException {
		if (parameters instanceof CartesianParameters) {
			if (((CartesianParameters) parameters).getMaximumPositionVelocity() > 2.0) {
				throw new InvalidParametersException("Velocity too high");
			}
		}
	}

	@Override
	protected void defineDefaultParameters() throws InvalidParametersException {
		super.defineDefaultParameters();
		double JOINT_VEL_SAFETY_MARGIN = Math.toRadians(20);

		addDefaultParameters(new RobotToolParameter(new CompliantRobotTool(getDefaultPayloadMass(),
				getFlange().asPose(), getDefaultCenterOfMass().asPose())));
		addDefaultParameters(new CartesianParameters(0.5, 2, 25, Math.PI / 4f, Math.PI / 2f, 10 * Math.PI));
		addDefaultParameters(getJointDeviceParameters(0, JOINT_VEL_SAFETY_MARGIN));
	}

	@Override
	protected void defineMaximumParameters() throws InvalidParametersException {
		double JOINT_VEL_SAFETY_MARGIN = Math.toRadians(5);

		addMaximumParameters(new CartesianParameters(1.5, 3, 25, Math.PI / 2f, Math.PI, 10.0 * Math.PI));
		addMaximumParameters(getJointDeviceParameters(0, JOINT_VEL_SAFETY_MARGIN));

		// TODO: some of those values are guessed
		addMaximumParameters(new RobotToolParameter(
				new CompliantRobotTool(7, getFlange().asPose(), getDefaultCenterOfMass().asPose(), 3.5, 3.5, 3.5)));
	}

	@Override
	public List<RedundancyStrategy> getRedundancyStrategies() {
		final List<RedundancyStrategy> ret = super.getRedundancyStrategies();
		// ret.add(new ConstantAlpha());
		// ret.add(new MinimizeVelocity());
		return ret;
	}

}
