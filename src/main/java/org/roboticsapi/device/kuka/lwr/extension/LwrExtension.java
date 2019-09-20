/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.extension;

import org.roboticsapi.core.RoboticsObject;
import org.roboticsapi.device.kuka.lwr.Lwr4;
import org.roboticsapi.device.kuka.lwr.LwrIiwa14;
import org.roboticsapi.device.kuka.lwr.LwrIiwa7;
import org.roboticsapi.device.kuka.lwr.LwrJoint;
import org.roboticsapi.extension.AbstractRoboticsObjectBuilder;
import org.roboticsapi.extension.RoboticsObjectListener;

public class LwrExtension extends AbstractRoboticsObjectBuilder implements RoboticsObjectListener {

	public LwrExtension() {
		super(Lwr4.class, LwrIiwa7.class, LwrIiwa14.class, LwrJoint.class);
	}

	@Override
	public void onAvailable(RoboticsObject object) {
		// TODO register DeviceInterfaces

	}

	@Override
	public void onUnavailable(RoboticsObject object) {
		// TODO Auto-generated method stub

	}

}
