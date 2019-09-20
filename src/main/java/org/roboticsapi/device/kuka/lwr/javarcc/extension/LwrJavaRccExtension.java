/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.javarcc.extension;

import org.roboticsapi.device.kuka.lwr.javarcc.devices.JMockLwr;
import org.roboticsapi.facet.javarcc.extension.JavaRccExtension;
import org.roboticsapi.facet.javarcc.extension.JavaRccExtensionPoint;
import org.roboticsapi.facet.runtime.rpi.RpiParameters;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIdoubleArray;

public class LwrJavaRccExtension extends JavaRccExtension {

	@Override
	public void extend(JavaRccExtensionPoint ep) {
		ep.registerDevice("kuka_lwr_sim", (p, d) -> new JMockLwr(param(p, "link_lengths"), param(p, "min_joint"),
				param(p, "max_joint"), param(p, "max_vel"), param(p, "max_acc"), false));
		ep.registerDevice("kuka_iiwa_sim", (p, d) -> new JMockLwr(param(p, "link_lengths"), param(p, "min_joint"),
				param(p, "max_joint"), param(p, "max_vel"), param(p, "max_acc"), true));
	}

	private double[] param(RpiParameters p, String name) {
		RPIdoubleArray val = p.get(RPIdoubleArray.class, name);
		double[] ret = new double[val.getSize()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = val.get(i).get();
		}
		return ret;
	}
}
