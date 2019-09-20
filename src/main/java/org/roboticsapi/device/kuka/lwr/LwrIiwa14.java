/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr;

import org.roboticsapi.core.Device;
import org.roboticsapi.core.world.Rotation;
import org.roboticsapi.core.world.Transformation;
import org.roboticsapi.framework.multijoint.link.BaseLink;
import org.roboticsapi.framework.multijoint.link.FlangeLink;
import org.roboticsapi.framework.multijoint.link.JointLink;
import org.roboticsapi.framework.multijoint.link.Link;

/**
 * A {@link Device} representing a KUKA Light Weight Robot.
 */
public class LwrIiwa14 extends AbstractLwrIiwa {

	private final static double[] LINK_LENGTHS = { 0.1575, 0.2025, 0.2045, 0.2155, 0.1845, 0.2155, 0.081, 0.045 };

	/**
	 * Instantiates a new LWR.
	 */
	public LwrIiwa14() {
	}

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
					new Transformation(0, 0, LINK_LENGTHS[1], Math.PI, 0, Rotation.deg2rad(90)));
		case 2:
			return new JointLink(getJoint(1), getJoint(2),
					new Transformation(0, LINK_LENGTHS[2], 0, Math.PI, 0, Rotation.deg2rad(90)));
		case 3:
			return new JointLink(getJoint(2), getJoint(3),
					new Transformation(0, 0, LINK_LENGTHS[3], 0, 0, Rotation.deg2rad(90)));
		case 4:
			return new JointLink(getJoint(3), getJoint(4),
					new Transformation(0, LINK_LENGTHS[4], 0, Math.PI, 0, Rotation.deg2rad(90)));
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
	protected LwrJoint createJoint(int number, String name) {
		LwrJoint joint = new LwrJoint();

		joint.setName(name);

		switch (number) {

		case 0:
			joint.setMinimumPosition(Math.toRadians(-170));
			joint.setMaximumPosition(Math.toRadians(+170));
			joint.setMaximumVelocity(Math.toRadians(85));
			joint.setMaximumAcceleration(joint.getMaximumVelocity() * 3);
			joint.setMaximumJerk(joint.getMaximumVelocity() * 20);
			break;
		case 1:
			joint.setMinimumPosition(Math.toRadians(-120));
			joint.setMaximumPosition(Math.toRadians(+120));
			joint.setMaximumVelocity(Math.toRadians(85));
			joint.setMaximumAcceleration(joint.getMaximumVelocity() * 3);
			joint.setMaximumJerk(joint.getMaximumVelocity() * 20);
			break;
		case 2:
			joint.setMinimumPosition(Math.toRadians(-170));
			joint.setMaximumPosition(Math.toRadians(+170));
			joint.setMaximumVelocity(Math.toRadians(100));
			joint.setMaximumAcceleration(joint.getMaximumVelocity() * 3);
			joint.setMaximumJerk(joint.getMaximumVelocity() * 20);
			break;
		case 3:
			joint.setMinimumPosition(Math.toRadians(-120));
			joint.setMaximumPosition(Math.toRadians(+120));
			joint.setMaximumVelocity(Math.toRadians(75));
			joint.setMaximumAcceleration(joint.getMaximumVelocity() * 3);
			joint.setMaximumJerk(joint.getMaximumVelocity() * 20);
			break;
		case 4:
			joint.setMinimumPosition(Math.toRadians(-170));
			joint.setMaximumPosition(Math.toRadians(+170));
			joint.setMaximumVelocity(Math.toRadians(130));
			joint.setMaximumAcceleration(joint.getMaximumVelocity() * 3);
			joint.setMaximumJerk(joint.getMaximumVelocity() * 20);
			break;
		case 5:
			joint.setMinimumPosition(Math.toRadians(-120));
			joint.setMaximumPosition(Math.toRadians(+120));
			joint.setMaximumVelocity(Math.toRadians(135));
			joint.setMaximumAcceleration(joint.getMaximumVelocity() * 3);
			joint.setMaximumJerk(joint.getMaximumVelocity() * 20);
			break;
		case 6:
			joint.setMinimumPosition(Math.toRadians(-175));
			joint.setMaximumPosition(Math.toRadians(+175));
			joint.setMaximumVelocity(Math.toRadians(135));
			joint.setMaximumAcceleration(joint.getMaximumVelocity() * 3);
			joint.setMaximumJerk(joint.getMaximumVelocity() * 20);
			break;
		}

		return joint;
	}

}
