/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr;

import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.core.Sensor;
import org.roboticsapi.core.exception.CommunicationException;
import org.roboticsapi.core.sensor.DoubleSensor;
import org.roboticsapi.robot.KinematicsException;
import org.roboticsapi.robot.RobotArmDriver;
import org.roboticsapi.world.Frame;
import org.roboticsapi.world.Transformation;

public interface LWRDriver extends RobotArmDriver {

	@Override
	public LWRJointDriver createJointDriver(int jointNumber);

	double[] getInverseKinematics(Transformation point, double[] hintJoints, Double alpha,
			DeviceParameterBag parameters) throws KinematicsException, CommunicationException;

	/**
	 * Gets a {@link Sensor} measuring the estimated force applied to the LWR's
	 * flange {@link Frame} along its x axis.
	 * 
	 * @return a {@link Sensor} measuring the force at the flange {@link Frame} 's x
	 *         axis
	 */
	public DoubleSensor getForceXSensor();

	/**
	 * Gets a {@link Sensor} measuring the estimated force applied to the LWR's
	 * flange {@link Frame} along its y axis.
	 * 
	 * @return a {@link Sensor} measuring the force at the flange {@link Frame} 's y
	 *         axis
	 */
	public DoubleSensor getForceYSensor();

	/**
	 * Gets a {@link Sensor} measuring the estimated force applied to the LWR's
	 * flange {@link Frame} along its z axis.
	 * 
	 * @return a {@link Sensor} measuring the force at the flange {@link Frame} 's z
	 *         axis
	 */
	public DoubleSensor getForceZSensor();

	/**
	 * Gets a {@link Sensor} measuring the estimated torque applied to the LWR's
	 * flange {@link Frame} around its z axis.
	 * 
	 * @return a {@link Sensor} measuring the torque around the flange
	 *         {@link Frame}'s z axis
	 */
	public DoubleSensor getTorqueASensor();

	/**
	 * Gets a {@link Sensor} measuring the estimated torque applied to the LWR's
	 * flange {@link Frame} around its y axis.
	 * 
	 * @return a {@link Sensor} measuring the torque around the flange
	 *         {@link Frame}'s y axis
	 */
	public DoubleSensor getTorqueBSensor();

	/**
	 * Gets a {@link Sensor} measuring the estimated torque applied to the LWR's
	 * flange {@link Frame} around its x axis.
	 * 
	 * @return a {@link Sensor} measuring the torque around the flange
	 *         {@link Frame}'s x axis
	 */
	public DoubleSensor getTorqueCSensor();

}
