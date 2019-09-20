/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.collision.extension;

import java.util.HashSet;
import java.util.Set;

import org.roboticsapi.core.RoboticsObject;
import org.roboticsapi.core.world.PhysicalObject;
import org.roboticsapi.core.world.Transformation;
import org.roboticsapi.device.kuka.lwr.Lwr4;
import org.roboticsapi.extension.PluginableExtension;
import org.roboticsapi.extension.RoboticsObjectListener;
import org.roboticsapi.facet.collision.properties.CollisionFriendsProperty;
import org.roboticsapi.facet.collision.properties.CollisionShapeProperty;
import org.roboticsapi.facet.collision.shapes.CapsuleShape;
import org.roboticsapi.facet.collision.shapes.SphereShape;
import org.roboticsapi.framework.multijoint.Joint;
import org.roboticsapi.framework.multijoint.link.Link;

public class LwrCollisionExtension implements RoboticsObjectListener, PluginableExtension {

	private static final CollisionShapeProperty[] HULLPROPERTIES = new CollisionShapeProperty[] {
			new CollisionShapeProperty("LWR_Link0",
					new CapsuleShape(new Transformation(0, 0, 0.03571, 0, 0, 0), 0.09, 0.1)),
			new CollisionShapeProperty("LWR_Link1",
					new CapsuleShape(new Transformation(0, 0.01705, 0.21435, 0, 0, Math.toRadians(-9.413f)), 0.08,
							0.2)),
			new CollisionShapeProperty("LWR_Link2",
					new CapsuleShape(new Transformation(0, 0.09867, 0.01815, 0, 0, Math.toRadians(90 - 9.413f)), 0.08,
							0.2)),
			new CollisionShapeProperty("LWR_Link3",
					new CapsuleShape(new Transformation(0, -0.01977, 0.10285, 0, 0, Math.toRadians(+9.413f)), 0.08,
							0.2)),
			new CollisionShapeProperty("LWR_Link4",
					new CapsuleShape(new Transformation(0, -0.10285, 0.01740, 0, 0, Math.toRadians(90 + 9.413f)), 0.08,
							0.2)),
			new CollisionShapeProperty("LWR_Link5",
					new CapsuleShape(new Transformation(0, 0.02121, 0.08926, 0, 0, Math.toRadians(-13.716f)), 0.08,
							0.2)),
			new CollisionShapeProperty("LWR_Link6", new CapsuleShape(Transformation.IDENTITY, 0.072, 0.016)),
			new CollisionShapeProperty("LWR_Link7",
					new SphereShape(new Transformation(0, 0, -0.0315, 0, 0, 0), 0.05)) };

	@Override
	public void onAvailable(RoboticsObject object) {
		if (object instanceof Lwr4) {
			Lwr4 lwr = (Lwr4) object;

			for (int i = 0; i < lwr.getLinks().length; i++) {
				Link link = lwr.getLink(i);

				if (link == null) {
					continue;
				}
				link.addProperty(HULLPROPERTIES[i]);
			}

			for (int i = 0; i < lwr.getJointCount() - 1; i++) {
				Joint joint = lwr.getJoint(i);

				Set<PhysicalObject> friends = new HashSet<PhysicalObject>();
				friends.add(lwr.getLink(i));
				friends.add(lwr.getLink(i + 1));

				if (i == 5) {
					friends.add(lwr.getLink(i + 2));
				}

				CollisionFriendsProperty prop = new CollisionFriendsProperty(friends);
				joint.addProperty(prop);
			}
		}
	}

	@Override
	public void onUnavailable(RoboticsObject object) {
		// do nothing
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "KUKA LWR collision model";
	}

	@Override
	public String getDescription() {
		return "Collision model of KUKA LWR";
	}
}
