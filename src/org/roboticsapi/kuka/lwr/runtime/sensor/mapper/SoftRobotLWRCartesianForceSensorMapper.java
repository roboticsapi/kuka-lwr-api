/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.sensor.mapper;

import org.roboticsapi.kuka.lwr.runtime.primitives.FTMonitor;
import org.roboticsapi.kuka.lwr.runtime.sensor.SoftRobotLWRCartesianForceSensor;
import org.roboticsapi.kuka.lwr.runtime.sensor.SoftRobotLWRCartesianForceSensor.CartesianDirection;
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

public class SoftRobotLWRCartesianForceSensorMapper
		implements SensorMapper<SoftRobotRuntime, Double, SoftRobotLWRCartesianForceSensor> {

	@Override
	public SensorMapperResult<Double> map(SoftRobotRuntime runtime, SoftRobotLWRCartesianForceSensor sensor,
			SensorMappingPorts ports, SensorMappingContext context) throws MappingException {
		NetFragment ret = new NetFragment("SoftRobotForceSensor");
		final NetFragment snf = new NetFragment("Sensor Value");
		final FTMonitor lfm = new FTMonitor(sensor.getDriver().getDeviceName());
		snf.add(lfm);

		OutPort valueOut = null;

		if (sensor.getDirection() == CartesianDirection.X) {
			valueOut = lfm.getOutTcpFx();
		}
		if (sensor.getDirection() == CartesianDirection.Y) {
			valueOut = lfm.getOutTcpFy();
		}
		if (sensor.getDirection() == CartesianDirection.Z) {
			valueOut = lfm.getOutTcpFz();
		}
		if (sensor.getDirection() == CartesianDirection.A) {
			valueOut = lfm.getOutTcpTz();
		}
		if (sensor.getDirection() == CartesianDirection.B) {
			valueOut = lfm.getOutTcpTy();
		}
		if (sensor.getDirection() == CartesianDirection.C) {
			valueOut = lfm.getOutTcpTx();
		}

		DataflowOutPort port = snf.addOutPort(new DoubleDataflow(), true, valueOut);
		ret.add(snf);
		return new DoubleSensorMapperResult(ret, port);
	}

}
