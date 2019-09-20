/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.runtime.rpi.sensor.mapper;

import java.util.Optional;

import org.roboticsapi.device.kuka.lwr.ConstantAlpha;
import org.roboticsapi.device.kuka.lwr.LwrRedundancyStrategy;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.primitives.InvKin;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.sensor.LwrInverseKinematicsRealtimeDoubleArray;
import org.roboticsapi.facet.runtime.rpi.RpiException;
import org.roboticsapi.facet.runtime.rpi.mapping.MappingException;
import org.roboticsapi.facet.runtime.rpi.mapping.RealtimeArrayFragment;
import org.roboticsapi.facet.runtime.rpi.mapping.RealtimeValueFragment;
import org.roboticsapi.facet.runtime.rpi.mapping.TypedRealtimeValueFragmentFactory;
import org.roboticsapi.facet.runtime.rpi.mapping.core.RealtimeDoubleArrayFragment;

public class LwrInverseKinematicsRealtimeValueMapper
		extends TypedRealtimeValueFragmentFactory<Double[], LwrInverseKinematicsRealtimeDoubleArray> {

	public LwrInverseKinematicsRealtimeValueMapper() {
		super(LwrInverseKinematicsRealtimeDoubleArray.class);
	}

	@Override
	protected RealtimeValueFragment<Double[]> createFragment(LwrInverseKinematicsRealtimeDoubleArray value)
			throws MappingException, RpiException {
		RealtimeArrayFragment<Double[]> ret = new RealtimeDoubleArrayFragment(value);

		// use an LWR IK module
		InvKin ik = new InvKin(value.getStrategy().getNr(), value.getDriver().getRpiDeviceName());

		// use the appropriate redundancy strategy
		LwrRedundancyStrategy strategy = Optional.ofNullable(value.getStrategy()).orElse(new ConstantAlpha());
		ik.setStrategy(strategy.getNr());

		ret.add(ik);

		// the IK module depends on the requested transformation
		ret.addDependency(value.getTransformation(), "inFrame", ik.getInFrame());

		// furthermore, it depends on hint joint values to choose the best
		// solution
		ret.addDependency(value.getHintJoints()[0], "inHintJ1", ik.getInHintJ1());
		ret.addDependency(value.getHintJoints()[1], "inHintJ2", ik.getInHintJ2());
		ret.addDependency(value.getHintJoints()[2], "inHintJ3", ik.getInHintJ3());
		ret.addDependency(value.getHintJoints()[3], "inHintJ4", ik.getInHintJ4());
		ret.addDependency(value.getHintJoints()[4], "inHintJ5", ik.getInHintJ5());
		ret.addDependency(value.getHintJoints()[5], "inHintJ6", ik.getInHintJ6());
		ret.addDependency(value.getHintJoints()[6], "inHintJ7", ik.getInHintJ7());

		// finally, it depends on nullspace joint values to resolve redundancy
		ret.addDependency(value.getHintJoints()[0], "inNullspaceJ1", ik.getInNullspaceJ1());
		ret.addDependency(value.getHintJoints()[1], "inNullspaceJ2", ik.getInNullspaceJ2());
		ret.addDependency(value.getHintJoints()[2], "inNullspaceJ3", ik.getInNullspaceJ3());
		ret.addDependency(value.getHintJoints()[3], "inNullspaceJ4", ik.getInNullspaceJ4());
		ret.addDependency(value.getHintJoints()[4], "inNullspaceJ5", ik.getInNullspaceJ5());
		ret.addDependency(value.getHintJoints()[5], "inNullspaceJ6", ik.getInNullspaceJ6());
		ret.addDependency(value.getHintJoints()[6], "inNullspaceJ7", ik.getInNullspaceJ7());

		// the mapping result consists of the ik outports
		ret.defineResult(ik.getOutPorts());

		return ret;
	}

}
