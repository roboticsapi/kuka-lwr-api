/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.sensor.mapper;

import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.core.sensor.DoubleSensor;
import org.roboticsapi.kuka.lwr.runtime.fragment.LWRInvKinematicsFragment;
import org.roboticsapi.kuka.lwr.runtime.sensor.SoftRobotLWRInverseKinematicsSensor;
import org.roboticsapi.runtime.SoftRobotRuntime;
import org.roboticsapi.runtime.core.primitives.DoubleArray;
import org.roboticsapi.runtime.core.primitives.DoubleArraySet;
import org.roboticsapi.runtime.mapping.MappingException;
import org.roboticsapi.runtime.mapping.net.ComposedDataflowOutPort;
import org.roboticsapi.runtime.mapping.net.DoubleArrayDataflow;
import org.roboticsapi.runtime.mapping.net.NetFragment;
import org.roboticsapi.runtime.mapping.parts.SensorMapper;
import org.roboticsapi.runtime.mapping.parts.SensorMappingContext;
import org.roboticsapi.runtime.mapping.parts.SensorMappingPorts;
import org.roboticsapi.runtime.mapping.result.SensorMapperResult;
import org.roboticsapi.runtime.mapping.result.impl.DoubleArraySensorMapperResult;
import org.roboticsapi.runtime.multijoint.JointsDataflow;
import org.roboticsapi.runtime.robot.driver.SoftRobotRobotArmDriver;
import org.roboticsapi.runtime.rpi.InPort;
import org.roboticsapi.runtime.rpi.OutPort;
import org.roboticsapi.runtime.world.dataflow.RelationDataflow;
import org.roboticsapi.world.Transformation;

public class SoftRobotLWRInverseKinematicsSensorMapper
		implements SensorMapper<SoftRobotRuntime, Double[], SoftRobotLWRInverseKinematicsSensor> {

	@Override
	public SensorMapperResult<Double[]> map(SoftRobotRuntime runtime, SoftRobotLWRInverseKinematicsSensor sensor,
			SensorMappingPorts ports, SensorMappingContext context) throws MappingException {
		NetFragment fragment = new NetFragment("SoftRobotLWRInverseKinematicsSensor");
		SoftRobotRobotArmDriver driver = sensor.getDriver();
		LWRInvKinematicsFragment invKin = fragment
				.add(new LWRInvKinematicsFragment(driver, new DeviceParameterBag()));

		SensorMapperResult<Transformation> transformation = runtime.getMapperRegistry().mapSensor(runtime,
				sensor.getTransformation(), fragment, context);

		ComposedDataflowOutPort hintJoints = new ComposedDataflowOutPort(true);
		for (int i = 0; i < sensor.getHintJoints().length; i++) {
			SensorMapperResult<Double> joint = runtime.getMapperRegistry().mapSensor(runtime, sensor.getHintJoints()[i],
					fragment, context);
			hintJoints.addDataflow(joint.getSensorPort());
		}
		

		ComposedDataflowOutPort nullspaceJoints = new ComposedDataflowOutPort(true);
		if(sensor.getAlpha() == null) {
			nullspaceJoints = hintJoints;
		} else {
			SensorMapperResult<Double> zero = runtime.getMapperRegistry().mapSensor(runtime, DoubleSensor.fromValue(0),
					fragment, context);
			SensorMapperResult<Double> alpha = runtime.getMapperRegistry().mapSensor(runtime, sensor.getAlpha(),
					fragment, context);
			for (int i = 0; i < sensor.getHintJoints().length; i++) {
				nullspaceJoints.addDataflow(i == 2 ? alpha.getSensorPort() : zero.getSensorPort());
			}
		}

		fragment.connect(fragment.reinterpret(transformation.getSensorPort(),
				new RelationDataflow(driver.getBase(), driver.getFlange())), invKin.getInFrame());

		fragment.connect(fragment.reinterpret(hintJoints, new JointsDataflow(driver.getJointCount(), driver)),
				invKin.getInHintJoints());

		fragment.connect(fragment.reinterpret(nullspaceJoints, new JointsDataflow(driver.getJointCount(), driver)),
				invKin.getInNullspaceJoints());

		DoubleArray arr = fragment.add(new DoubleArray(driver.getJointCount()));
		OutPort out = arr.getOutArray();
		InPort[] ins = new InPort[driver.getJointCount()];
		for (int i = 0; i < driver.getJointCount(); i++) {
			DoubleArraySet set = fragment.add(new DoubleArraySet(driver.getJointCount(), i));
			fragment.connect(out, set.getInArray());
			ins[i] = set.getInValue();
			out = set.getOutArray();
		}
		fragment.connect(invKin.getOutJoints(),
				fragment.addInPort(new JointsDataflow(driver.getJointCount(), driver), true, ins));

		return new DoubleArraySensorMapperResult(fragment,
				fragment.addOutPort(new DoubleArrayDataflow(driver.getJointCount()), false, out));
	}
}
