/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.sensor;

import org.roboticsapi.core.sensor.DoubleArraySensor;
import org.roboticsapi.core.sensor.DoubleSensor;
import org.roboticsapi.kuka.lwr.runtime.driver.SoftRobotLWRDriver;
import org.roboticsapi.runtime.robot.driver.SoftRobotRobotArmDriver;
import org.roboticsapi.world.sensor.TransformationSensor;

public final class SoftRobotLWRInverseKinematicsSensor extends DoubleArraySensor {

	private final SoftRobotLWRDriver driver;
	private final TransformationSensor transformation;
	private final DoubleSensor[] hintJoints;
	private DoubleSensor alpha;

	public SoftRobotLWRInverseKinematicsSensor(SoftRobotLWRDriver driver, TransformationSensor transformation, DoubleSensor alpha,
			DoubleSensor[] hintJoints) {
		super(driver.getRuntime(), driver.getJointCount());
		this.driver = driver;
		this.transformation = transformation;
		this.alpha = alpha;
		this.hintJoints = hintJoints;
	}

	public SoftRobotRobotArmDriver getDriver() {
		return driver;
	}

	public TransformationSensor getTransformation() {
		return transformation;
	}
	
	public DoubleSensor getAlpha() {
		return alpha;
	}

	public DoubleSensor[] getHintJoints() {
		return hintJoints;
	}

	@Override
	public boolean equals(Object obj) {
		return classEqual(obj) && driver.equals(((SoftRobotLWRInverseKinematicsSensor) obj).driver)
				&& transformation.equals(((SoftRobotLWRInverseKinematicsSensor) obj).transformation)
				&& hintJoints.equals(((SoftRobotLWRInverseKinematicsSensor) obj).hintJoints) 
				&& alpha.equals(((SoftRobotLWRInverseKinematicsSensor) obj).alpha);
	}

	@Override
	public int hashCode() {
		return classHash(driver, transformation, hintJoints, alpha);
	}

	@Override
	public boolean isAvailable() {
		return driver.isPresent() && areAvailable(hintJoints) && transformation.isAvailable() && alpha.isAvailable();
	}
}
