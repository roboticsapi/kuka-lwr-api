/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.controller;

import org.roboticsapi.multijoint.parameter.AbstractPositionController;

public class LwrPositionController extends AbstractPositionController {

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}

		if (!(obj instanceof LwrPositionController)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
