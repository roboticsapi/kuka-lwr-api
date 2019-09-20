/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.runtime.rpi.sensor.mapper;

import org.roboticsapi.device.kuka.lwr.runtime.rpi.primitives.FTMonitor;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.sensor.LwrJointTorqueRealtimeDouble;
import org.roboticsapi.facet.runtime.rpi.OutPort;
import org.roboticsapi.facet.runtime.rpi.RpiException;
import org.roboticsapi.facet.runtime.rpi.mapping.MappingException;
import org.roboticsapi.facet.runtime.rpi.mapping.RealtimeValueFragment;
import org.roboticsapi.facet.runtime.rpi.mapping.TypedRealtimeValueFragmentFactory;
import org.roboticsapi.facet.runtime.rpi.mapping.core.RealtimeDoubleFragment;

public class LwrJointTorqueRealtimeValueMapper
		extends TypedRealtimeValueFragmentFactory<Double, LwrJointTorqueRealtimeDouble> {

	public LwrJointTorqueRealtimeValueMapper() {
		super(LwrJointTorqueRealtimeDouble.class);
	}

	@Override
	protected RealtimeValueFragment<Double> createFragment(LwrJointTorqueRealtimeDouble value)
			throws MappingException, RpiException {
		RealtimeDoubleFragment result = new RealtimeDoubleFragment(value);

		FTMonitor monitor = result.add(new FTMonitor());

		OutPort out = null;
		switch (value.getDriver().getIndex()) {
		case 0:
			out = monitor.getOutEstJ1();
			break;
		case 1:
			out = monitor.getOutEstJ2();
			break;
		case 2:
			out = monitor.getOutEstJ3();
			break;
		case 3:
			out = monitor.getOutEstJ4();
			break;
		case 4:
			out = monitor.getOutEstJ5();
			break;
		case 5:
			out = monitor.getOutEstJ6();
			break;
		case 6:
			out = monitor.getOutEstJ7();
			break;
		}

		result.defineResult(out);

		return result;
	}

}
