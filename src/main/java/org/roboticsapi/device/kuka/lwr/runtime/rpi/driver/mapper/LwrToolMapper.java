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
import org.roboticsapi.device.kuka.lwr.runtime.rpi.primitives.ToolParameters;
import org.roboticsapi.facet.runtime.rpi.RpiException;
import org.roboticsapi.facet.runtime.rpi.core.primitives.IntEquals;
import org.roboticsapi.facet.runtime.rpi.mapping.ActuatorDriverMapper;
import org.roboticsapi.facet.runtime.rpi.mapping.ActuatorFragment;
import org.roboticsapi.facet.runtime.rpi.mapping.MapperRegistry;
import org.roboticsapi.facet.runtime.rpi.mapping.MappingException;
import org.roboticsapi.facet.runtime.rpi.mapping.RealtimeValueConsumerFragment;
import org.roboticsapi.framework.robot.runtime.rpi.mapper.ToolActionResult;

public class LwrToolMapper implements ActuatorDriverMapper<LwrGenericDriver, ToolActionResult> {

	@Override
	public RealtimeValueConsumerFragment map(LwrGenericDriver actuatorDriver, ToolActionResult actionResult,
			DeviceParameterBag parameters, MapperRegistry registry, RealtimeBoolean cancel, RealtimeDouble override,
			RealtimeDouble time) throws MappingException, RpiException {

		ToolParameters tp = new ToolParameters(actuatorDriver.getRpiDeviceName());
		ActuatorFragment ret = new ActuatorFragment(actuatorDriver, tp.getOutCompleted());

		ret.addDependency(actionResult.getCenterOfMass(), "inCOM", tp.getInCOM());
		ret.addDependency(actionResult.getMomentsOfInertia(), "inMOI", tp.getInMOI());
		ret.addDependency(actionResult.getMass(), "inMass", tp.getInMass());

		// TODO: TCP
		// if (robotTool instanceof CompliantRobotTool) {
		// Frame coc = ((CompliantRobotTool) robotTool).getCenterOfCompliance();
		// } else {
		// Frame coc = deviceDriver.getDefaultMotionCenter();
		// }
		// transTCP = flange -> coc
		// ret.addDependency(transTCP, "inTCP", tp.getInTCP());

		IntEquals concurrentAccess = ret.add(new IntEquals(0, 1, 0));
		ret.connect(tp.getOutError(), concurrentAccess.getInFirst());
		IntEquals actuatorNotOperational = ret.add(new IntEquals(0, 2, 0));
		ret.connect(tp.getOutError(), actuatorNotOperational.getInFirst());
		IntEquals tenseSpring = ret.add(new IntEquals(0, 2, 0));
		ret.connect(tp.getOutError(), tenseSpring.getInFirst());

		ret.addException(ConcurrentAccessException.class, concurrentAccess.getOutValue(), "outConcurrentAccess");
		ret.addException(ActuatorNotOperationalException.class, actuatorNotOperational.getOutValue(),
				"outNotOperational");
		// ret.addException(TenseSpringException.class, tenseSpring.getOutValue(),
		// "outTenseSpring");
		return ret;
	}
}
