/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.driver;

import org.roboticsapi.core.exception.ConfigurationException;

public final class IIWAFriDriver extends SoftRobotLWRDriver { // <LWRiiwa> {

	private final static String DEVICE_TYPE = "kuka_iiwa_fri";

	public IIWAFriDriver() {
		super();
	}

	@Override
	public String getDeviceType() {
		return DEVICE_TYPE;
	}

	@Override
	protected void validateConfigurationProperties() throws ConfigurationException {
		super.validateConfigurationProperties();

	}

}
