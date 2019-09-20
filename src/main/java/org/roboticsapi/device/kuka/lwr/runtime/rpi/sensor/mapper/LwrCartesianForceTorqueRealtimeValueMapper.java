/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.runtime.rpi.sensor.mapper;

import org.roboticsapi.device.kuka.lwr.runtime.rpi.primitives.FTMonitor;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.sensor.LwrCartesianForceTorqueRealtimeDouble;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.sensor.LwrCartesianForceTorqueRealtimeDouble.CartesianDirection;
import org.roboticsapi.facet.runtime.rpi.OutPort;
import org.roboticsapi.facet.runtime.rpi.RpiException;
import org.roboticsapi.facet.runtime.rpi.mapping.MappingException;
import org.roboticsapi.facet.runtime.rpi.mapping.RealtimeValueFragment;
import org.roboticsapi.facet.runtime.rpi.mapping.TypedRealtimeValueFragmentFactory;
import org.roboticsapi.facet.runtime.rpi.mapping.core.RealtimeDoubleFragment;

public class LwrCartesianForceTorqueRealtimeValueMapper
		extends TypedRealtimeValueFragmentFactory<Double, LwrCartesianForceTorqueRealtimeDouble> {

	public LwrCartesianForceTorqueRealtimeValueMapper() {
		super(LwrCartesianForceTorqueRealtimeDouble.class);
	}

	@Override
	protected RealtimeValueFragment<Double> createFragment(LwrCartesianForceTorqueRealtimeDouble value)
			throws MappingException, RpiException {
		RealtimeDoubleFragment result = new RealtimeDoubleFragment(value);

		FTMonitor monitor = result.add(new FTMonitor(value.getDriver().getRpiDeviceName()));

		OutPort out = null;

		if (value.getDirection() == CartesianDirection.X) {
			out = monitor.getOutTcpFx();
		}
		if (value.getDirection() == CartesianDirection.Y) {
			out = monitor.getOutTcpFy();
		}
		if (value.getDirection() == CartesianDirection.Z) {
			out = monitor.getOutTcpFz();
		}
		if (value.getDirection() == CartesianDirection.A) {
			out = monitor.getOutTcpTz();
		}
		if (value.getDirection() == CartesianDirection.B) {
			out = monitor.getOutTcpTy();
		}
		if (value.getDirection() == CartesianDirection.C) {
			out = monitor.getOutTcpTx();
		}

		result.defineResult(out);

		return result;
	}
}
