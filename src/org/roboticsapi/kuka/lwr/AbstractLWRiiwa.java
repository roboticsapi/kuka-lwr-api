package org.roboticsapi.kuka.lwr;

import org.roboticsapi.core.Device;
import org.roboticsapi.multijoint.link.BaseLink;
import org.roboticsapi.multijoint.link.FlangeLink;
import org.roboticsapi.multijoint.link.JointLink;
import org.roboticsapi.multijoint.link.Link;
import org.roboticsapi.world.Rotation;
import org.roboticsapi.world.Transformation;

/**
 * A {@link Device} representing a KUKA Light Weight Robot iiwa.
 */
public abstract class AbstractLWRiiwa extends AbstractLWR {

	public AbstractLWRiiwa() {
		super();
	}

	@Override
	protected Link createLink(int number) {
		double[] LINK_LENGTHS = getLinkLengths();
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

}