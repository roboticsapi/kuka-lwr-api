/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr;

import java.util.List;
import java.util.Map;

import org.roboticsapi.cartesianmotion.parameter.CartesianParameters;
import org.roboticsapi.configuration.ConfigurationProperty;
import org.roboticsapi.core.Device;
import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.core.DeviceParameters;
import org.roboticsapi.core.InvalidParametersException;
import org.roboticsapi.core.RoboticsObject;
import org.roboticsapi.core.Sensor;
import org.roboticsapi.core.exception.CommunicationException;
import org.roboticsapi.core.sensor.DoubleSensor;
import org.roboticsapi.kuka.lwr.controller.LwrPositionController;
import org.roboticsapi.multijoint.AbstractJoint;
import org.roboticsapi.multijoint.link.BaseLink;
import org.roboticsapi.multijoint.link.FlangeLink;
import org.roboticsapi.multijoint.link.JointLink;
import org.roboticsapi.multijoint.link.Link;
import org.roboticsapi.multijoint.parameter.ControllerParameter;
import org.roboticsapi.robot.AbstractRobotArm;
import org.roboticsapi.robot.KinematicsException;
import org.roboticsapi.robot.RedundancyStrategy;
import org.roboticsapi.robot.parameter.CompliantRobotTool;
import org.roboticsapi.robot.parameter.RobotTool;
import org.roboticsapi.robot.parameter.RobotToolParameter;
import org.roboticsapi.world.Frame;
import org.roboticsapi.world.Rotation;
import org.roboticsapi.world.Transformation;

/**
 * A {@link Device} representing a KUKA Light Weight Robot.
 */
public abstract class AbstractLWR extends AbstractRobotArm<LWRJoint, LWRDriver> {

	/**
	 * Instantiates a new LWR.
	 */
	public AbstractLWR() {
		super(7);
	}

	/**
	 * Gets a {@link Sensor} measuring the torque applied at the
	 * {@link AbstractJoint} with the given number.
	 * 
	 * @param axis the {@link AbstractJoint} number.
	 * @return a {@link Sensor} measuring the joint torque
	 */
	public DoubleSensor getTorqueSensor(int axis) {
		return getJoint(axis).getTorqueSensor();
	}

	/**
	 * Gets a {@link Sensor} measuring the estimated force applied to the LWR's
	 * flange {@link Frame} along its x axis.
	 * 
	 * @return a {@link Sensor} measuring the force at the flange {@link Frame} 's x
	 *         axis
	 */
	public DoubleSensor getForceXSensor() {
		return getDriver().getForceXSensor();
	}

	/**
	 * Gets a {@link Sensor} measuring the estimated force applied to the LWR's
	 * flange {@link Frame} along its y axis.
	 * 
	 * @return a {@link Sensor} measuring the force at the flange {@link Frame} 's y
	 *         axis
	 */
	public DoubleSensor getForceYSensor() {
		return getDriver().getForceYSensor();
	}

	/**
	 * Gets a {@link Sensor} measuring the estimated force applied to the LWR's
	 * flange {@link Frame} along its z axis.
	 * 
	 * @return a {@link Sensor} measuring the force at the flange {@link Frame} 's z
	 *         axis
	 */
	public DoubleSensor getForceZSensor() {
		return getDriver().getForceZSensor();
	}

	/**
	 * Gets a {@link Sensor} measuring the estimated torque applied to the LWR's
	 * flange {@link Frame} around its z axis.
	 * 
	 * @return a {@link Sensor} measuring the torque around the flange
	 *         {@link Frame}'s z axis
	 */
	public DoubleSensor getTorqueASensor() {
		return getDriver().getTorqueASensor();
	}

	/**
	 * Gets a {@link Sensor} measuring the estimated torque applied to the LWR's
	 * flange {@link Frame} around its y axis.
	 * 
	 * @return a {@link Sensor} measuring the torque around the flange
	 *         {@link Frame}'s y axis
	 */
	public DoubleSensor getTorqueBSensor() {
		return getDriver().getTorqueBSensor();
	}

	/**
	 * Gets a {@link Sensor} measuring the estimated torque applied to the LWR's
	 * flange {@link Frame} around its x axis.
	 * 
	 * @return a {@link Sensor} measuring the torque around the flange
	 *         {@link Frame}'s x axis
	 */
	public DoubleSensor getTorqueCSensor() {
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
	public double[] getInverseKinematicsInternal(Transformation point, double[] hintJoints,
			DeviceParameterBag parameters) throws KinematicsException, CommunicationException {
		return getInverseKinematics(point, hintJoints, null, parameters);
	}

	@Override
	protected Link createLink(int number) {
		double[] LINK_LENGTHS = getLinkLengths();
		switch (number) {
		
		case 0:
			return new BaseLink(getBase(), getJoint(0), new Transformation(0, 0, LINK_LENGTHS[0], 0, 0, 0));
		case 1:
			return new JointLink(getJoint(0), getJoint(1),
					new Transformation(0, 0, LINK_LENGTHS[1], 0, 0, Rotation.deg2rad(90)));
		case 2:
			return new JointLink(getJoint(1), getJoint(2), new Transformation(0, LINK_LENGTHS[2], 0, 0, 0, Rotation.deg2rad(-90)));
		case 3:
			return new JointLink(getJoint(2), getJoint(3), new Transformation(0, 0, LINK_LENGTHS[3], 0, 0, Rotation.deg2rad(-90)));
		case 4:
			return new JointLink(getJoint(3), getJoint(4), new Transformation(0, -LINK_LENGTHS[4], 0, 0, 0, Rotation.deg2rad(90)));
		case 5:
			return new JointLink(getJoint(4), getJoint(5), new Transformation(0, 0, LINK_LENGTHS[5], 0, 0, Rotation.deg2rad(90)));
		case 6:
			return new JointLink(getJoint(5), getJoint(6),
					new Transformation(0, LINK_LENGTHS[6], 0, 0, 0, Rotation.deg2rad(-90)));
		case 7:
			return new FlangeLink(getJoint(6), getFlange(), new Transformation(0, 0, LINK_LENGTHS[7], 0, 0, 0));
		}
		return null;
	}

	/** Center of compliance */
	private Frame centerOfCompliance;

	public Frame getCenterOfCompliance() {
		return centerOfCompliance;
	}

	@ConfigurationProperty
	public void setCenterOfCompliance(Frame centerOfCompliance) {
		immutableWhenInitialized();
		this.centerOfCompliance = centerOfCompliance;
	}

	@Override
	protected void fillAutomaticRobotArmProperties(Map<String, RoboticsObject> createdObjects) {
		if (centerOfCompliance == null || !centerOfCompliance.isInitialized()) {
			centerOfCompliance = getFlange();
			createdObjects.put("centerOfCompliance", null);
		}
	}

	@Override
	protected void clearAutomaticRobotArmProperties(Map<String, RoboticsObject> createdObjects) {
		if (createdObjects.containsKey("centerOfCompliance")) {
			centerOfCompliance = null;
		}
	}

	@Override
	public void validateParameters(DeviceParameters parameters) throws InvalidParametersException {
		if (parameters instanceof CartesianParameters) {
			if (((CartesianParameters) parameters).getMaximumPositionVelocity() > 2.0) {
				throw new InvalidParametersException("Velocity too high");
			}
		}
	}

	@Override
	protected void defineDefaultParameters() throws InvalidParametersException {
		super.defineDefaultParameters();
		double JOINT_VEL_SAFETY_MARGIN = Math.toRadians(20);

		addDefaultParameters(new RobotToolParameter(
				new CompliantRobotTool(getDefaultPayloadMass(), getCenterOfCompliance(), getDefaultCenterOfMass())));
		addDefaultParameters(new CartesianParameters(0.5, 2, Math.PI / 4f, Math.PI / 2f));
		addDefaultParameters(getJointDeviceParameters(0, JOINT_VEL_SAFETY_MARGIN));
		addDefaultParameters(new ControllerParameter(new LwrPositionController()));
	}

	@Override
	protected void defineMaximumParameters() throws InvalidParametersException {
		double JOINT_VEL_SAFETY_MARGIN = Math.toRadians(5);

		addMaximumParameters(new CartesianParameters(1.5, 3, Math.PI / 2f, Math.PI));
		addMaximumParameters(getJointDeviceParameters(0, JOINT_VEL_SAFETY_MARGIN));

		// TODO: some of those values are guessed
		addMaximumParameters(new RobotToolParameter(new RobotTool(getMaximumPayload(), getFlange(), 3.5, 3.5, 3.5)));
	}

	protected abstract int getMaximumPayload();

	@Override
	protected LWRJoint createJoint(int number, String name) {
		LWRJoint joint = new LWRJoint();
		LWRJointDriver driver = getDriver().createJointDriver(number);

		joint.setName(name);
		joint.setDriver(driver);
		
		joint.setMinimumPosition(-getMaximumJointAngles()[number]);
		joint.setMaximumPosition(getMaximumJointAngles()[number]);
		joint.setMaximumVelocity(getMaximumJointVelocities()[number]);
		joint.setMaximumAcceleration(joint.getMaximumVelocity()*3);

		return joint;
	}

	@Override
	public List<RedundancyStrategy> getRedundancyStrategies() {
		final List<RedundancyStrategy> ret = super.getRedundancyStrategies();
		ret.add(new ConstantAlpha());
		ret.add(new MinimizeVelocity());
		return ret;
	}

	public abstract double[] getLinkLengths();

	protected abstract double[] getMaximumJointAngles();
	
	protected abstract double[] getMaximumJointVelocities();
	
}
