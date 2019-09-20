/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.visualization.extension;

import java.net.URL;

import org.roboticsapi.core.RoboticsObject;
import org.roboticsapi.device.kuka.lwr.Lwr4;
import org.roboticsapi.extension.PluginableExtension;
import org.roboticsapi.extension.RoboticsObjectListener;
import org.roboticsapi.facet.visualization.property.Visualisation;
import org.roboticsapi.facet.visualization.property.VisualizationProperty;
import org.roboticsapi.framework.multijoint.link.Link;

public class Lwr4ColladaExtension implements RoboticsObjectListener, PluginableExtension {

	private static final Visualisation[] LINK_MODELS = new Visualisation[] {
			new Visualisation("COLLADA", getResource("LWR_Link0.dae")),
			new Visualisation("COLLADA", getResource("LWR_Link1.dae")),
			new Visualisation("COLLADA", getResource("LWR_Link2.dae")),
			new Visualisation("COLLADA", getResource("LWR_Link3.dae")),
			new Visualisation("COLLADA", getResource("LWR_Link4.dae")),
			new Visualisation("COLLADA", getResource("LWR_Link5.dae")),
			new Visualisation("COLLADA", getResource("LWR_Link6.dae")),
			new Visualisation("COLLADA", getResource("LWR_Link7.dae")), };

	@Override
	public void onAvailable(RoboticsObject object) {
		if (object instanceof Lwr4) {
			Lwr4 lwr = (Lwr4) object;

			// ...
			for (int i = 0; i < 8; i++) {
				Link link = lwr.getLink(i);

				if (link == null) {
					continue;
				}
				link.addProperty(new VisualizationProperty(LINK_MODELS[i]));
			}
		}
	}

	@Override
	public void onUnavailable(RoboticsObject object) {
		// TODO Auto-generated method stub

	}

	private static final URL getResource(String resource) {
		return Lwr4ColladaExtension.class.getResource(resource);
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
		return "KUKA LWR Collada visualization model";
	}

	@Override
	public String getDescription() {
		return "Collada model of KUKA LWR to display it in Robotics API visualization";
	}

}
