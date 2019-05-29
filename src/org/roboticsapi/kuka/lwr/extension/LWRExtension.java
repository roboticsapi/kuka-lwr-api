/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.extension;

import org.roboticsapi.extension.AbstractRoboticsObjectBuilder;
import org.roboticsapi.kuka.lwr.LWR4;
import org.roboticsapi.kuka.lwr.LWRJoint;
import org.roboticsapi.kuka.lwr.LWRiiwa;
import org.roboticsapi.kuka.lwr.LWRiiwa14;

public class LWRExtension extends AbstractRoboticsObjectBuilder {

	public LWRExtension() {
		super(LWR4.class, LWRiiwa.class, LWRiiwa14.class, LWRJoint.class);
	}

}
