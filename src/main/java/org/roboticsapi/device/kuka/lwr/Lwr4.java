/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr;

import java.util.List;

import org.roboticsapi.core.Device;
import org.roboticsapi.core.DeviceParameters;
import org.roboticsapi.core.InvalidParametersException;
import org.roboticsapi.core.world.Rotation;
import org.roboticsapi.core.world.Transformation;
import org.roboticsapi.framework.cartesianmotion.parameter.CartesianParameters;
import org.roboticsapi.framework.multijoint.link.BaseLink;
import org.roboticsapi.framework.multijoint.link.FlangeLink;
import org.roboticsapi.framework.multijoint.link.JointLink;
import org.roboticsapi.framework.multijoint.link.Link;
import org.roboticsapi.framework.robot.RedundancyStrategy;
import org.roboticsapi.framework.robot.parameter.CompliantRobotTool;
import org.roboticsapi.framework.robot.parameter.RobotToolParameter;

/**
 * A {@link Device} representing a KUKA Light Weight Robot (gen. 4).
 */
public class Lwr4 extends AbstractLwr {

	private final static double[] LINK_LENGTHS = { 0, 0.3105, 0.2, 0.2, 0.2, 0.19, 0.078, 0 };

	@Override
	public double[] getLinkLengths() {
		return LINK_LENGTHS;
	}

	@Override
	protected Link createLink(int number) {
		switch (number) {

		case 0:
			return new BaseLink(getBase(), getJoint(0), new Transformation(0, 0, LINK_LENGTHS[0], 0, 0, 0));
		case 1:
			return new JointLink(getJoint(0), getJoint(1),
					new Transformation(0, 0, LINK_LENGTHS[1], 0, 0, Rotation.deg2rad(90)));
		case 2:
			return new JointLink(getJoint(1), getJoint(2),
					new Transformation(0, LINK_LENGTHS[2], 0, 0, 0, Rotation.deg2rad(-90)));
		case 3:
			return new JointLink(getJoint(2), getJoint(3),
					new Transformation(0, 0, LINK_LENGTHS[3], 0, 0, Rotation.deg2rad(-90)));
		case 4:
			return new JointLink(getJoint(3), getJoint(4),
					new Transformation(0, -LINK_LENGTHS[4], 0, 0, 0, Rotation.deg2rad(90)));
		case 5:
			return new JointLink(getJoint(4), getJoint(5),
					new Transformation(0, 0, LINK_LENGTHS[5], 0, 0, Rotation.deg2rad(90)));
		case 6:
			return new JointLink(getJoint(5), getJoint(6),
					new Transformation(0, LINK_LENGTHS[6], 0, 0, 0, Rotation.deg2rad(-90)));
		case 7:
			return new FlangeLink(getJoint(6), getFlange(), new Transformation(0, 0, LINK_LENGTHS[7], 0, 0, 0));
		}
		return null;
	}

	@Override
	protected void defineDefaultParameters() throws InvalidParametersException {
		super.defineDefaultParameters();
		double JOINT_VEL_SAFETY_MARGIN = Math.toRadians(20);

		addDefaultParameters(new CartesianParameters(2d, 4d, 40d, Math.PI / 2d, Math.PI, Math.PI * 10d));
		addDefaultParameters(getJointDeviceParameters(0, JOINT_VEL_SAFETY_MARGIN));
	}

	@Override
	protected void defineMaximumParameters() throws InvalidParametersException {
		double JOINT_VEL_SAFETY_MARGIN = Math.toRadians(5);

		addMaximumParameters(new CartesianParameters(3, 6, 60, Math.PI / 2d, Math.PI, Math.PI * 10d));
		addMaximumParameters(getJointDeviceParameters(0, JOINT_VEL_SAFETY_MARGIN));

		// TODO: some of those values are guessed
		addMaximumParameters(new RobotToolParameter(
				new CompliantRobotTool(7, getFlange().asPose(), getDefaultCenterOfMass().asPose(), 3.5, 3.5, 3.5)));
	}

	@Override
	protected LwrJoint createJoint(int number, String name) {
		LwrJoint joint = new LwrJoint();

		joint.setName(name);
		switch (number) {
		case 4:
			joint.setMinimumPosition(Math.toRadians(-170));
			joint.setMaximumPosition(Math.toRadians(+170));
			joint.setMaximumVelocity(Math.toRadians(180.0));
			break;
		case 0:
		case 2:
		case 6:
			joint.setMinimumPosition(Math.toRadians(-170));
			joint.setMaximumPosition(Math.toRadians(+170));
			joint.setMaximumVelocity(Math.toRadians(112.5));
			break;
		default:
			joint.setMinimumPosition(Math.toRadians(-120));
			joint.setMaximumPosition(Math.toRadians(+120));
			joint.setMaximumVelocity(Math.toRadians(112.5));
			break;
		}
		joint.setMaximumAcceleration(4);

		return joint;
	}

	@Override
	public List<RedundancyStrategy> getRedundancyStrategies() {
		final List<RedundancyStrategy> ret = super.getRedundancyStrategies();
		ret.add(new ConstantAlpha());
		ret.add(new MinimizeVelocity());
		return ret;
	}

	@Override
	public void validateParameters(DeviceParameters parameters) throws InvalidParametersException {
		// TODO Auto-generated method stub

	}

}
