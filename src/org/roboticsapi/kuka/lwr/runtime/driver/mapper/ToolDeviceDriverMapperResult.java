/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.driver.mapper;

import org.roboticsapi.core.actuator.ActuatorNotOperationalException;
import org.roboticsapi.core.actuator.ConcurrentAccessException;
import org.roboticsapi.core.actuator.GeneralActuatorException;
import org.roboticsapi.kuka.lwr.LWRDriver;
import org.roboticsapi.kuka.lwr.TenseSpringException;
import org.roboticsapi.runtime.mapping.net.DataflowOutPort;
import org.roboticsapi.runtime.mapping.net.NetFragment;
import org.roboticsapi.runtime.mapping.result.impl.BaseActuatorDriverMapperResult;

public class ToolDeviceDriverMapperResult extends BaseActuatorDriverMapperResult {

	public ToolDeviceDriverMapperResult(LWRDriver lwrDriver, NetFragment fragment, DataflowOutPort completed,
			DataflowOutPort concurrentAccess, DataflowOutPort drivesNotEnabled, DataflowOutPort tenseSpringError,
			DataflowOutPort unknownError) {
		super(lwrDriver, fragment, completed);

		addExceptionPort(ConcurrentAccessException.class, concurrentAccess);
		addExceptionPort(ActuatorNotOperationalException.class, drivesNotEnabled);
		addExceptionPort(TenseSpringException.class, tenseSpringError);
		addExceptionPort(GeneralActuatorException.class, unknownError);

	}

}