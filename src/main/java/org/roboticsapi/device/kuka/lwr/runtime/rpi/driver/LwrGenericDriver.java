/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.runtime.rpi.driver;

import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.core.actuator.ActuatorDriverRealtimeException;
import org.roboticsapi.core.exception.CommunicationException;
import org.roboticsapi.core.realtimevalue.RealtimeValueReadException;
import org.roboticsapi.core.realtimevalue.realtimedouble.RealtimeDouble;
import org.roboticsapi.core.realtimevalue.realtimedouble.RealtimeDoubleArray;
import org.roboticsapi.core.world.Frame;
import org.roboticsapi.core.world.Pose;
import org.roboticsapi.core.world.Transformation;
import org.roboticsapi.core.world.TransformationException;
import org.roboticsapi.core.world.realtimevalue.realtimetransformation.RealtimeTransformation;
import org.roboticsapi.device.kuka.lwr.AbstractLwr;
import org.roboticsapi.device.kuka.lwr.LwrDriver;
import org.roboticsapi.device.kuka.lwr.LwrRedundancyStrategy;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.sensor.LwrCartesianForceTorqueRealtimeDouble;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.sensor.LwrCartesianForceTorqueRealtimeDouble.CartesianDirection;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.sensor.LwrInverseKinematicsRealtimeDoubleArray;
import org.roboticsapi.facet.runtime.rpi.RpiParameters;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIdouble;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIdoubleArray;
import org.roboticsapi.facet.runtime.rpi.world.types.RPIFrame;
import org.roboticsapi.facet.runtime.rpi.world.types.RPIRotation;
import org.roboticsapi.facet.runtime.rpi.world.types.RPIVector;
import org.roboticsapi.framework.multijoint.Joint;
import org.roboticsapi.framework.multijoint.JointDriver;
import org.roboticsapi.framework.robot.KinematicsException;
import org.roboticsapi.framework.robot.RedundancyStrategy;
import org.roboticsapi.framework.robot.parameter.CompliantRobotTool;
import org.roboticsapi.framework.robot.parameter.RedundancyDeviceParameter;
import org.roboticsapi.framework.robot.parameter.RobotTool;
import org.roboticsapi.framework.robot.parameter.RobotToolParameter;
import org.roboticsapi.framework.robot.runtime.rpi.driver.RobotArmGenericDriver;

public class LwrGenericDriver extends RobotArmGenericDriver<AbstractLwr> implements LwrDriver {

	@Override
	protected RpiParameters getRpiDeviceParameters() {
		RpiParameters parameters = super.getRpiDeviceParameters();

		// default tool mass
		parameters = parameters.with("mass", Double.toString(getDevice().getDefaultPayloadMass()));

		// default moment of inertia
		parameters = parameters.with("moi", toString(getDevice().getDefaultInertiaX(), getDevice().getDefaultInertiaY(),
				getDevice().getDefaultInertiaZ()));

		// default center of mass
		Frame defaultCenterOfMass = getDevice().getDefaultCenterOfMass();
		// use current transformation from flange to com as initial value
		Transformation comT;
		try {
			comT = getDevice().getFlange().getTransformationTo(defaultCenterOfMass);
			parameters = parameters.with("com", toRPIFrame(comT));
		} catch (TransformationException e) {
			throw new IllegalStateException("Could not determine default center of mass during device loading", e);
		}

		// default center of compliance; use default CompliantRobotTool to
		// determine this
		RobotToolParameter robotToolParameter = getDevice().getDefaultParameters().get(RobotToolParameter.class);
		if (robotToolParameter == null) {
			throw new IllegalStateException("Device " + getDevice().getName() + " has no default RobotToolParameter");
		}
		RobotTool robotTool = robotToolParameter.getRobotTool();
		if (!(robotTool instanceof CompliantRobotTool)) {
			throw new IllegalStateException(
					"Device " + getDevice().getName() + " has no CompliantRobotTool as default tool");
		}
		// use current transformation from flange to coc as initial value
		Pose coc = ((CompliantRobotTool) robotTool).getCenterOfCompliance();
		Transformation cocT;
		try {
			cocT = getDevice().getFlange().asPose().getCommandedTransformationTo(coc);
			parameters = parameters.with("tcp", toRPIFrame(cocT));
		} catch (TransformationException e) {
			throw new IllegalStateException("Could not determine default center of compliance during device loading",
					e);
		}

		// Link lengths
		double[] _link_lengths = getDevice().getLinkLengths();
		RPIdoubleArray link_lengths = new RPIdoubleArray(_link_lengths.length);
		for (int i = 0; i < _link_lengths.length; i++) {
			link_lengths.set(i, new RPIdouble(_link_lengths[i]));
		}
		parameters = parameters.with("link_lengths", link_lengths);

		return parameters;
	}

	private RPIFrame toRPIFrame(Transformation trans) {
		return new RPIFrame(
				new RPIVector(new RPIdouble(trans.getTranslation().getX()),
						new RPIdouble(trans.getTranslation().getY()), new RPIdouble(trans.getTranslation().getZ())),
				new RPIRotation(new RPIdouble(trans.getRotation().getA()), new RPIdouble(trans.getRotation().getB()),
						new RPIdouble(trans.getRotation().getC())));
	}

	@Override
	public double[] getInverseKinematics(Transformation point, double[] hintJoints, Double alpha,
			DeviceParameterBag parameters) throws KinematicsException, CommunicationException {
		RealtimeDouble[] hintJointRealtime = DoubleStream.of(hintJoints)
				.mapToObj(j -> RealtimeDouble.createFromConstant(j)).toArray(x -> new RealtimeDouble[x]);
		RealtimeDouble[] invKin = getInverseKinematics(RealtimeTransformation.createfromConstant(point),
				hintJointRealtime, RealtimeDouble.createFromConstant(alpha), parameters);
		try {
			Double[] result = RealtimeDoubleArray.createFromComponents(invKin).getCurrentValue();

			return Stream.of(result).mapToDouble(x -> x).toArray();
		} catch (RealtimeValueReadException e) {
			throw new CommunicationException(e);
		}
	}

	@Override
	public RealtimeDouble[] getInverseKinematics(RealtimeTransformation point, RealtimeDouble[] hintJoints,
			RealtimeDouble alpha, DeviceParameterBag parameters) throws KinematicsException, CommunicationException {
		RedundancyDeviceParameter redundancyDeviceParameter = parameters.get(RedundancyDeviceParameter.class);

		if (redundancyDeviceParameter == null) {
			throw new IllegalArgumentException(
					"RedundancyDeviceParameter not specified, but is needed for kinematics calculation");
		}

		RedundancyStrategy s = redundancyDeviceParameter.getRedundancyStrategy();

		if (!(s instanceof LwrRedundancyStrategy)) {
			throw new IllegalArgumentException(
					"RedundancyStrategy specified in RedundancyDeviceParameter is not an LWRRedundancyStrategy");
		}

		return new LwrInverseKinematicsRealtimeDoubleArray(this, point, hintJoints, alpha, (LwrRedundancyStrategy) s)
				.getDoubles();
	}

	@Override
	public RealtimeDouble getForceXSensor() {
		return new LwrCartesianForceTorqueRealtimeDouble(this, CartesianDirection.X);
	}

	@Override
	public RealtimeDouble getForceYSensor() {
		return new LwrCartesianForceTorqueRealtimeDouble(this, CartesianDirection.Y);
	}

	@Override
	public RealtimeDouble getForceZSensor() {
		return new LwrCartesianForceTorqueRealtimeDouble(this, CartesianDirection.Z);
	}

	@Override
	public RealtimeDouble getTorqueASensor() {
		return new LwrCartesianForceTorqueRealtimeDouble(this, CartesianDirection.A);

	}

	@Override
	public RealtimeDouble getTorqueBSensor() {
		return new LwrCartesianForceTorqueRealtimeDouble(this, CartesianDirection.B);
	}

	@Override
	public RealtimeDouble getTorqueCSensor() {
		return new LwrCartesianForceTorqueRealtimeDouble(this, CartesianDirection.C);
	}

	@Override
	public List<ActuatorDriverRealtimeException> defineActuatorDriverExceptions() {
		List<ActuatorDriverRealtimeException> exceptions = super.defineActuatorDriverExceptions();

		// exceptions.add(new ForceNotReachedException(scope, this));
		// exceptions.add(new TenseSpringException(scope, this));

		return exceptions;
	}

	@Override
	protected JointDriver createJointDriver(int jointNumber, Joint joint) {
		return new LwrJointGenericDriver(getRuntime(), getRpiDeviceName(), getRpiDeviceType(), jointNumber, joint);
	}

}
