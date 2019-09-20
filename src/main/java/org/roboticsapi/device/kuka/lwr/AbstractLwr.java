/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr;

import java.util.List;

import org.roboticsapi.core.Device;
import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.core.InvalidParametersException;
import org.roboticsapi.core.RealtimeValue;
import org.roboticsapi.core.exception.CommunicationException;
import org.roboticsapi.core.realtimevalue.realtimedouble.RealtimeDouble;
import org.roboticsapi.core.world.Frame;
import org.roboticsapi.core.world.Transformation;
import org.roboticsapi.framework.multijoint.AbstractJoint;
import org.roboticsapi.framework.robot.AbstractRobotArm;
import org.roboticsapi.framework.robot.KinematicsException;
import org.roboticsapi.framework.robot.RedundancyStrategy;
import org.roboticsapi.framework.robot.parameter.CompliantRobotTool;
import org.roboticsapi.framework.robot.parameter.RobotToolParameter;

/**
 * A {@link Device} representing a KUKA Light Weight Robot.
 */
public abstract class AbstractLwr extends AbstractRobotArm<LwrJoint, LwrDriver> {

	/**
	 * Instantiates a new LWR.
	 */
	public AbstractLwr() {
		super(7);
	}

	/**
	 * Gets a {@link RealtimeValue} measuring the torque applied at the
	 * {@link AbstractJoint} with the given number.
	 *
	 * @param axis the {@link AbstractJoint} number.
	 * @return a {@link RealtimeValue} measuring the joint torque
	 */
	public RealtimeDouble getTorqueSensor(int axis) {
		return getJoint(axis).getTorqueSensor();
	}

	/**
	 * Gets a {@link RealtimeValue} measuring the estimated force applied to the LWR's
	 * flange {@link Frame} along its x axis.
	 *
	 * @return a {@link RealtimeValue} measuring the force at the flange {@link Frame} 's x
	 *         axis
	 */
	public RealtimeDouble getForceXSensor() {
		return getDriver().getForceXSensor();
	}

	/**
	 * Gets a {@link RealtimeValue} measuring the estimated force applied to the LWR's
	 * flange {@link Frame} along its y axis.
	 *
	 * @return a {@link RealtimeValue} measuring the force at the flange {@link Frame} 's y
	 *         axis
	 */
	public RealtimeDouble getForceYSensor() {
		return getDriver().getForceYSensor();
	}

	/**
	 * Gets a {@link RealtimeValue} measuring the estimated force applied to the LWR's
	 * flange {@link Frame} along its z axis.
	 *
	 * @return a {@link RealtimeValue} measuring the force at the flange {@link Frame} 's z
	 *         axis
	 */
	public RealtimeDouble getForceZSensor() {
		return getDriver().getForceZSensor();
	}

	/**
	 * Gets a {@link RealtimeValue} measuring the estimated torque applied to the LWR's
	 * flange {@link Frame} around its z axis.
	 *
	 * @return a {@link RealtimeValue} measuring the torque around the flange
	 *         {@link Frame}'s z axis
	 */
	public RealtimeDouble getTorqueASensor() {
		return getDriver().getTorqueASensor();
	}

	/**
	 * Gets a {@link RealtimeValue} measuring the estimated torque applied to the LWR's
	 * flange {@link Frame} around its y axis.
	 *
	 * @return a {@link RealtimeValue} measuring the torque around the flange
	 *         {@link Frame}'s y axis
	 */
	public RealtimeDouble getTorqueBSensor() {
		return getDriver().getTorqueBSensor();
	}

	/**
	 * Gets a {@link RealtimeValue} measuring the estimated torque applied to the LWR's
	 * flange {@link Frame} around its x axis.
	 *
	 * @return a {@link RealtimeValue} measuring the torque around the flange
	 *         {@link Frame}'s x axis
	 */
	public RealtimeDouble getTorqueCSensor() {
		return getDriver().getTorqueCSensor();
	}

	public double[] getInverseKinematics(Transformation point, double[] hintJoints, Double alpha,
			DeviceParameterBag parameters) throws KinematicsException, CommunicationException {
		if (!isPresent()) {
			throw new CommunicationException("Kinematics calculation not possible: Device is in State " + getState());
		}
		return getDriver().getInverseKinematics(point, hintJoints, alpha, parameters);
	}

	@Override
	public List<RedundancyStrategy> getRedundancyStrategies() {
		final List<RedundancyStrategy> ret = super.getRedundancyStrategies();
		ret.add(new ConstantAlpha());
		ret.add(new MinimizeVelocity());
		return ret;
	}

	@Override
	protected void defineDefaultParameters() throws InvalidParametersException {
		super.defineDefaultParameters();

		addDefaultParameters(new RobotToolParameter(new CompliantRobotTool(getDefaultPayloadMass(),
				getFlange().asPose(), getDefaultCenterOfMass().asPose())));
	}

	public abstract double[] getLinkLengths();

}
