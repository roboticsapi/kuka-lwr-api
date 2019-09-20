/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr;

import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.core.RealtimeValue;
import org.roboticsapi.core.exception.CommunicationException;
import org.roboticsapi.core.realtimevalue.realtimedouble.RealtimeDouble;
import org.roboticsapi.core.world.Frame;
import org.roboticsapi.core.world.Transformation;
import org.roboticsapi.core.world.realtimevalue.realtimetransformation.RealtimeTransformation;
import org.roboticsapi.framework.robot.KinematicsException;
import org.roboticsapi.framework.robot.RobotArmDriver;

public interface LwrDriver extends RobotArmDriver {

	double[] getInverseKinematics(Transformation point, double[] hintJoints, Double alpha,
			DeviceParameterBag parameters) throws KinematicsException, CommunicationException;

	RealtimeDouble[] getInverseKinematics(RealtimeTransformation point, RealtimeDouble[] hintJoints,
			RealtimeDouble alpha, DeviceParameterBag parameters) throws KinematicsException, CommunicationException;

	/**
	 * Gets a {@link RealtimeValue} measuring the estimated force applied to the LWR's
	 * flange {@link Frame} along its x axis.
	 *
	 * @return a {@link RealtimeValue} measuring the force at the flange {@link Frame} 's x
	 *         axis
	 */
	public RealtimeDouble getForceXSensor();

	/**
	 * Gets a {@link RealtimeValue} measuring the estimated force applied to the LWR's
	 * flange {@link Frame} along its y axis.
	 *
	 * @return a {@link RealtimeValue} measuring the force at the flange {@link Frame} 's y
	 *         axis
	 */
	public RealtimeDouble getForceYSensor();

	/**
	 * Gets a {@link RealtimeValue} measuring the estimated force applied to the LWR's
	 * flange {@link Frame} along its z axis.
	 *
	 * @return a {@link RealtimeValue} measuring the force at the flange {@link Frame} 's z
	 *         axis
	 */
	public RealtimeDouble getForceZSensor();

	/**
	 * Gets a {@link RealtimeValue} measuring the estimated torque applied to the LWR's
	 * flange {@link Frame} around its z axis.
	 *
	 * @return a {@link RealtimeValue} measuring the torque around the flange
	 *         {@link Frame}'s z axis
	 */
	public RealtimeDouble getTorqueASensor();

	/**
	 * Gets a {@link RealtimeValue} measuring the estimated torque applied to the LWR's
	 * flange {@link Frame} around its y axis.
	 *
	 * @return a {@link RealtimeValue} measuring the torque around the flange
	 *         {@link Frame}'s y axis
	 */
	public RealtimeDouble getTorqueBSensor();

	/**
	 * Gets a {@link RealtimeValue} measuring the estimated torque applied to the LWR's
	 * flange {@link Frame} around its x axis.
	 *
	 * @return a {@link RealtimeValue} measuring the torque around the flange
	 *         {@link Frame}'s x axis
	 */
	public RealtimeDouble getTorqueCSensor();

}
