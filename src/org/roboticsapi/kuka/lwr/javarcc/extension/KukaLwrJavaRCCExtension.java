/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.javarcc.extension;

import java.util.Map;

import org.roboticsapi.kuka.lwr.javarcc.devices.JMockLwr;
import org.roboticsapi.kuka.lwr.javarcc.primitives.JCartImpParameters;
import org.roboticsapi.kuka.lwr.javarcc.primitives.JControlStrategy;
import org.roboticsapi.kuka.lwr.javarcc.primitives.JFMonitor;
import org.roboticsapi.kuka.lwr.javarcc.primitives.JFTMonitor;
import org.roboticsapi.kuka.lwr.javarcc.primitives.JInvKin;
import org.roboticsapi.kuka.lwr.javarcc.primitives.JJointImpParameters;
import org.roboticsapi.kuka.lwr.javarcc.primitives.JKin;
import org.roboticsapi.kuka.lwr.javarcc.primitives.JTMonitor;
import org.roboticsapi.kuka.lwr.javarcc.primitives.JToolParameters;
import org.roboticsapi.kuka.lwr.javarcc.primitives.JVelKin;
import org.roboticsapi.runtime.core.types.RPIdoubleArray;
import org.roboticsapi.runtime.javarcc.extension.JavaRCCExtension;
import org.roboticsapi.runtime.javarcc.extension.JavaRCCExtensionPoint;

public class KukaLwrJavaRCCExtension extends JavaRCCExtension {

	@Override
	public void extend(JavaRCCExtensionPoint ep) {
		ep.registerPrimitive("KUKA::LWR::CartImpParameters", JCartImpParameters.class);
		ep.registerPrimitive("KUKA::LWR::ControlStrategy", JControlStrategy.class);
		ep.registerPrimitive("KUKA::LWR::FMonitor", JFMonitor.class);
		ep.registerPrimitive("KUKA::LWR::FTMonitor", JFTMonitor.class);
		ep.registerPrimitive("KUKA::LWR::InvKin", JInvKin.class);
		ep.registerPrimitive("KUKA::LWR::JointImpParameters", JJointImpParameters.class);
		ep.registerPrimitive("KUKA::LWR::Kin", JKin.class);
		ep.registerPrimitive("KUKA::LWR::TMonitor", JTMonitor.class);
		ep.registerPrimitive("KUKA::LWR::ToolParameters", JToolParameters.class);
		ep.registerPrimitive("KUKA::LWR::VelKin", JVelKin.class);

		ep.registerDevice("kuka_lwr_sim",
				(p, d) -> new JMockLwr(param(p, "link_lengths"), param(p, "min_joint"), param(p, "max_joint"), false));
		ep.registerDevice("kuka_iiwa_sim",
				(p, d) -> new JMockLwr(param(p, "link_lengths"), param(p, "min_joint"), param(p, "max_joint"), true));
	}

	private double[] param(Map<String, String> p, String name) {
		RPIdoubleArray val = new RPIdoubleArray(p.get(name).replace('{', '[').replace('}', ']'));
		double[] ret = new double[val.getSize()];
		for (int i = 0; i < ret.length; i++)
			ret[i] = val.get(i).get();
		return ret;
	}
}
