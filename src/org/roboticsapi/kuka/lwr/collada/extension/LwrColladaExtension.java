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
import org.roboticsapi.kuka.lwr.LWR4;
import org.roboticsapi.multijoint.link.Link;
import org.roboticsapi.visualization.property.Visualisation;
import org.roboticsapi.visualization.property.VisualizationProperty;

public class LwrColladaExtension implements RoboticsObjectListener, PluginableExtension {

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
		if (object instanceof LWR4) {
			LWR4 lwr = (LWR4) object;

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
		return LwrColladaExtension.class.getClassLoader().getResource("models/" + resource);
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
