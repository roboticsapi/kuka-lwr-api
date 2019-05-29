/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.sensor.mapper;

import org.roboticsapi.kuka.lwr.runtime.primitives.FTMonitor;
import org.roboticsapi.kuka.lwr.runtime.sensor.SoftRobotLWRJointTorqueSensor;
import org.roboticsapi.runtime.SoftRobotRuntime;
import org.roboticsapi.runtime.mapping.MappingException;
import org.roboticsapi.runtime.mapping.net.DataflowOutPort;
import org.roboticsapi.runtime.mapping.net.DoubleDataflow;
import org.roboticsapi.runtime.mapping.net.NetFragment;
import org.roboticsapi.runtime.mapping.parts.SensorMapper;
import org.roboticsapi.runtime.mapping.parts.SensorMappingContext;
import org.roboticsapi.runtime.mapping.parts.SensorMappingPorts;
import org.roboticsapi.runtime.mapping.result.SensorMapperResult;
import org.roboticsapi.runtime.mapping.result.impl.DoubleSensorMapperResult;
import org.roboticsapi.runtime.rpi.OutPort;

public class SoftRobotLWRJointTorqueSensorMapper
		implements SensorMapper<SoftRobotRuntime, Double, SoftRobotLWRJointTorqueSensor> {

	@Override
	public SensorMapperResult<Double> map(final SoftRobotRuntime runtime, final SoftRobotLWRJointTorqueSensor sensor,
			SensorMappingPorts ports, SensorMappingContext context) throws MappingException {

		NetFragment ret = new NetFragment("SoftRobotLWRJointTorqueSensor");
		final NetFragment snf = new NetFragment("Sensor value");
		final FTMonitor lm = new FTMonitor(sensor.getDriver().getDeviceName());
		snf.add(lm);

		OutPort out = null;
		switch (sensor.getDriver().getJointNumber()) {
		case 0:
			out = lm.getOutEstJ1();
			break;
		case 1:
			out = lm.getOutEstJ2();
			break;
		case 2:
			out = lm.getOutEstJ3();
			break;
		case 3:
			out = lm.getOutEstJ4();
			break;
		case 4:
			out = lm.getOutEstJ5();
			break;
		case 5:
			out = lm.getOutEstJ6();
			break;
		case 6:
			out = lm.getOutEstJ7();
			break;
		}
		DataflowOutPort port = snf.addOutPort(new DoubleDataflow(), true, out);
		ret.add(snf);
		return new DoubleSensorMapperResult(ret, port);

	}

}
