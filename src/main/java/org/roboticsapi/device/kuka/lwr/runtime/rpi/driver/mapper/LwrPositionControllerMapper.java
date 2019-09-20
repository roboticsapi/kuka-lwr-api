/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.runtime.rpi.driver.mapper;

import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.core.actuator.ActuatorNotOperationalException;
import org.roboticsapi.core.actuator.ConcurrentAccessException;
import org.roboticsapi.core.realtimevalue.realtimeboolean.RealtimeBoolean;
import org.roboticsapi.core.realtimevalue.realtimedouble.RealtimeDouble;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.driver.LwrGenericDriver;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.primitives.ControlStrategy;
import org.roboticsapi.facet.runtime.rpi.RpiException;
import org.roboticsapi.facet.runtime.rpi.core.primitives.IntEquals;
import org.roboticsapi.facet.runtime.rpi.core.primitives.IntValue;
import org.roboticsapi.facet.runtime.rpi.mapping.ActuatorDriverMapper;
import org.roboticsapi.facet.runtime.rpi.mapping.ActuatorFragment;
import org.roboticsapi.facet.runtime.rpi.mapping.MapperRegistry;
import org.roboticsapi.facet.runtime.rpi.mapping.MappingException;
import org.roboticsapi.facet.runtime.rpi.mapping.RealtimeValueConsumerFragment;
import org.roboticsapi.framework.multijoint.runtime.rpi.result.JointPositionControllerActionResult;

public class LwrPositionControllerMapper
		implements ActuatorDriverMapper<LwrGenericDriver, JointPositionControllerActionResult> {

	private static final int POSITION_STRATEGY = 1;

	@Override
	public RealtimeValueConsumerFragment map(LwrGenericDriver actuatorDriver,
			JointPositionControllerActionResult actionResult, DeviceParameterBag parameters, MapperRegistry registry,
			RealtimeBoolean cancel, RealtimeDouble override, RealtimeDouble time)
			throws MappingException, RpiException {

		ControlStrategy strategyBlock = new ControlStrategy(actuatorDriver.getRpiDeviceName());
		ActuatorFragment ret = new ActuatorFragment(actuatorDriver, strategyBlock.getOutCompleted());

		ret.connect(ret.add(new IntValue(POSITION_STRATEGY)).getOutValue(), strategyBlock.getInStrategy());

		IntEquals concurrentAccess = ret.add(new IntEquals(0, 1, 0));
		ret.connect(strategyBlock.getOutError(), concurrentAccess.getInFirst());
		IntEquals actuatorNotOperational = ret.add(new IntEquals(0, 2, 0));
		ret.connect(strategyBlock.getOutError(), actuatorNotOperational.getInFirst());
		IntEquals tenseSpring = ret.add(new IntEquals(0, 2, 0));
		ret.connect(strategyBlock.getOutError(), tenseSpring.getInFirst());

		ret.addException(ConcurrentAccessException.class, concurrentAccess.getOutValue(), "outConcurrentAccess");
		ret.addException(ActuatorNotOperationalException.class, actuatorNotOperational.getOutValue(),
				"outNotOperational");
		// ret.addException(TenseSpringException.class, tenseSpring.getOutValue(),
		// "outTenseSpring");
		return ret;
	}

}
