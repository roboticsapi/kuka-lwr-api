/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.driver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.core.actuator.ActuatorDriverRealtimeException;
import org.roboticsapi.core.exception.CommunicationException;
import org.roboticsapi.core.sensor.DoubleArraySensor;
import org.roboticsapi.core.sensor.DoubleSensor;
import org.roboticsapi.core.sensor.SensorReadException;
import org.roboticsapi.kuka.lwr.AbstractLWR;
import org.roboticsapi.kuka.lwr.LWRDriver;
import org.roboticsapi.kuka.lwr.LWRJointDriver;
import org.roboticsapi.kuka.lwr.TenseSpringException;
import org.roboticsapi.kuka.lwr.activity.ForceNotReachedException;
import org.roboticsapi.kuka.lwr.runtime.sensor.SoftRobotLWRCartesianForceSensor;
import org.roboticsapi.kuka.lwr.runtime.sensor.SoftRobotLWRCartesianForceSensor.CartesianDirection;
import org.roboticsapi.kuka.lwr.runtime.sensor.SoftRobotLWRInverseKinematicsSensor;
import org.roboticsapi.robot.KinematicsException;
import org.roboticsapi.runtime.driver.DeviceBasedLoadable;
import org.roboticsapi.runtime.robot.driver.SoftRobotRobotArmDriver;
import org.roboticsapi.world.Transformation;
import org.roboticsapi.world.sensor.TransformationSensor;

public class SoftRobotLWRDriver extends SoftRobotRobotArmDriver implements LWRDriver, DeviceBasedLoadable<AbstractLWR> {

	@Override
	public LWRJointDriver createJointDriver(int jointNumber) {
		return new SoftRobotLWRJointDriver(getRuntime(), getDeviceName(), getDeviceType(), jointNumber);
	}

	@Override
	public String getDeviceType() {
		return null;
	}

	@Override
	public double[] getInverseKinematics(Transformation point, double[] hintJoints, Double alpha,
			DeviceParameterBag parameters) throws KinematicsException, CommunicationException {
		try {
			Double[] joints;
			if(hintJoints == null) hintJoints = getJointAngles();
			joints = new SoftRobotLWRInverseKinematicsSensor(this, TransformationSensor.fromConstant(point),
					alpha == null ? null : DoubleSensor.fromValue(alpha), DoubleArraySensor.fromConstant(hintJoints).getSensors())
							.getCurrentValue();
			double[] ret = new double[joints.length];
			for (int i = 0; i < joints.length; i++) {
				if(Double.isNaN(joints[i])) throw new KinematicsException("The given point is unreachable.");
				ret[i] = joints[i];
			}
			return ret;
		} catch (SensorReadException e) {
			throw new CommunicationException(e);
		}
	}

	@Override
	public DoubleSensor getForceXSensor() {
		return getCartesianForceComponentValue(CartesianDirection.X);
	}

	@Override
	public DoubleSensor getForceYSensor() {
		return getCartesianForceComponentValue(CartesianDirection.Y);
	}

	@Override
	public DoubleSensor getForceZSensor() {
		return getCartesianForceComponentValue(CartesianDirection.Z);
	}

	@Override
	public DoubleSensor getTorqueASensor() {
		return getCartesianForceComponentValue(CartesianDirection.A);
	}

	@Override
	public DoubleSensor getTorqueBSensor() {
		return getCartesianForceComponentValue(CartesianDirection.B);
	}

	@Override
	public DoubleSensor getTorqueCSensor() {
		return getCartesianForceComponentValue(CartesianDirection.C);
	}

	private DoubleSensor getCartesianForceComponentValue(CartesianDirection direction) {
		return new SoftRobotLWRCartesianForceSensor(this, direction);
	}

	@Override
	public List<ActuatorDriverRealtimeException> defineActuatorDriverExceptions() {
		List<ActuatorDriverRealtimeException> exceptions = super.defineActuatorDriverExceptions();

		exceptions.add(new ForceNotReachedException(this));
		exceptions.add(new TenseSpringException(this));

		return exceptions;
	}

	@Override
	public boolean build(AbstractLWR lwr) {
		if (getDeviceType() == null) {
			return false;
		}
		Map<String, String> parameters = new HashMap<String, String>();

		parameters.put("mass", toString(lwr.getDefaultPayloadMass()));
		parameters.put("com", toString(lwr.getDefaultCenterOfMass(), lwr.getFlange()));
		parameters.put("moi", toString(lwr.getDefaultInertiaX(), lwr.getDefaultInertiaY(), lwr.getDefaultInertiaZ()));
		parameters.put("tcp", toString(lwr.getCenterOfCompliance(), lwr.getFlange()));

		StringBuffer min_joint = new StringBuffer("{"), max_joint = new StringBuffer("{"),
				link_lengths = new StringBuffer("{");
		for (int i = 0; i < lwr.getJointCount(); i++)
			min_joint.append(lwr.getJoint(i).getMinimumPosition()).append(i == lwr.getJointCount() - 1 ? "}" : ",");
		for (int i = 0; i < lwr.getJointCount(); i++)
			max_joint.append(lwr.getJoint(i).getMaximumPosition()).append(i == lwr.getJointCount() - 1 ? "}" : ",");
		double[] lengths = lwr.getLinkLengths();
		for (int i = 0; i < lengths.length; i++)
			link_lengths.append(lengths[i]).append(i == lwr.getLinks().length - 1 ? "}" : ",");
		parameters.put("link_lengths", link_lengths.toString());
		parameters.put("min_joint", min_joint.toString());
		parameters.put("max_joint", max_joint.toString());

		collectDriverSpecificParameters(parameters);
		return loadDeviceDriver(parameters);
	}

	@Override
	public void delete() {
		deleteDeviceDriver();
	}

	protected void collectDriverSpecificParameters(Map<String, String> parameters) {

	}

}
