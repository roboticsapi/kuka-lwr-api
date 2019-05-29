/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.collada.extension;

import java.net.URL;

import org.roboticsapi.core.RoboticsObject;
import org.roboticsapi.extension.PluginableExtension;
import org.roboticsapi.extension.RoboticsObjectListener;
import org.roboticsapi.kuka.lwr.LWRiiwa;
import org.roboticsapi.kuka.lwr.LWRiiwa14;
import org.roboticsapi.multijoint.link.Link;
import org.roboticsapi.visualization.property.Visualisation;
import org.roboticsapi.visualization.property.VisualizationProperty;
import org.roboticsapi.world.Transformation;;

public class IiwaColladaExtension implements RoboticsObjectListener, PluginableExtension {

	private static final Visualisation[] IIWA7_MODELS = new Visualisation[] {
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("IIWA7_Link0.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, -0.17, 0, 0, 0), getResource("IIWA7_Link1.dae")),
			new Visualisation("COLLADA", new Transformation(0, -0.34, 0, Math.PI, 0, Math.PI / 2), getResource("IIWA7_Link2.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, -0.54, 0, 0, 0), getResource("IIWA7_Link3.dae")),
			new Visualisation("COLLADA", new Transformation(0, -0.74, 0, 0, 0, -Math.PI / 2), getResource("IIWA7_Link4.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, -0.94, Math.PI, 0, 0), getResource("IIWA7_Link5.dae")),
			new Visualisation("COLLADA", new Transformation(0, -1.14, 0, Math.PI, 0, Math.PI / 2), getResource("IIWA7_Link6.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, -1.221, 0, 0, 0), getResource("IIWA7_Link7.dae")), };

	private static final Visualisation[] IIWA14_MODELS = new Visualisation[] {
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("IIWA14_Link0.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, -0.1575, 0, 0, 0), getResource("IIWA14_Link1.dae")),
			new Visualisation("COLLADA", new Transformation(0, -0.36, 0, Math.PI, 0, Math.PI / 2), getResource("IIWA14_Link2.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, -0.5645, 0, 0, 0), getResource("IIWA14_Link3.dae")),
			new Visualisation("COLLADA", new Transformation(0, -0.78, 0, 0, 0, -Math.PI / 2), getResource("IIWA14_Link4.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, -0.9645, Math.PI, 0, 0), getResource("IIWA14_Link5.dae")),
			new Visualisation("COLLADA", new Transformation(0, -1.18, 0, Math.PI, 0, Math.PI / 2), getResource("IIWA14_Link6.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, -1.261, 0, 0, 0), getResource("IIWA14_Link7.dae")), };

	@Override
	public void onAvailable(RoboticsObject object) {
		if (object instanceof LWRiiwa) {
			LWRiiwa lwr = (LWRiiwa) object;
			for (int i = 0; i < 8; i++) {
				Link link = lwr.getLink(i);
				if (link == null) {
					continue;
				}
				link.addProperty(new VisualizationProperty(IIWA7_MODELS[i]));
			}
		}
		if (object instanceof LWRiiwa14) {
			LWRiiwa14 lwr = (LWRiiwa14) object;
			for (int i = 0; i < 8; i++) {
				Link link = lwr.getLink(i);
				if (link == null) {
					continue;
				}
				link.addProperty(new VisualizationProperty(IIWA14_MODELS[i]));
			}
		}
	}

	@Override
	public void onUnavailable(RoboticsObject object) {
		// TODO Auto-generated method stub

	}

	private static final URL getResource(String resource) {
		return IiwaColladaExtension.class.getClassLoader().getResource("models/" + resource);
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
		return "KUKA IIWA Collada visualization model";
	}

	@Override
	public String getDescription() {
		return "Collada model of KUKA LWR to display it in Robotics API visualization";
	}

}
