/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.activity;

import org.roboticsapi.activity.RtActivity;
import org.roboticsapi.core.DeviceParameters;
import org.roboticsapi.core.exception.RoboticsException;
import org.roboticsapi.kuka.lwr.AbstractLWR;
import org.roboticsapi.robot.activity.MotionInterface;
import org.roboticsapi.world.Frame;

/**
 * The Interface LwrMotionInterface extends the basic {@link MotionInterface} by
 * motions specific to the {@link AbstractLWR}.
 */
public interface LwrMotionInterface extends MotionInterface {

	/**
	 * Creates an {@link RtActivity} that activates the {@link AbstractLWR}'s gravitation
	 * compensation mode.
	 * 
	 * @param parameters optional {@link DeviceParameters}
	 * @return the RtActivity activating gravitation compensation mode
	 * @throws RoboticsException thrown if RtActivity could not be created
	 */
	RtActivity gravComp(DeviceParameters... parameters) throws RoboticsException;

	/**
	 * Creates an {@link RtActivity} that performs a linear motion to the given
	 * cartesian goal. The RtActivity expects to establish contact on the way to the
	 * goal and will then build up the specified cartesian force and stop.
	 * 
	 * The created RtActivity will throw an exception at the end of its execution if
	 * the expected contact force could not be established.
	 * 
	 * The method releaseForce() should be used to release contact forces before
	 * performing further non-contact motions.
	 * 
	 * @param to           the goal to drive to
	 * @param contactForce the magnitude of the contact force to establish
	 * @param parameters   optional {@link DeviceParameters}
	 * @return the RtActivity that performs linear motion to contact
	 * @throws RoboticsException thrown if RtActivity could not be created
	 */
	RtActivity linToContact(Frame to, double contactForce, DeviceParameters... parameters) throws RoboticsException;

	/**
	 * Creates an {@link RtActivity} that performs a linear motion to the given
	 * cartesian goal. The RtActivity expects to establish contact on the way to the
	 * goal and will then build up the specified cartesian force and stop.
	 * 
	 * The created RtActivity will throw an exception at the end of its execution if
	 * the expected contact force could not be established.
	 * 
	 * The method releaseForce() should be used to release contact forces before
	 * performing further non-contact motions.
	 * 
	 * @param to            the goal to drive to
	 * @param contactForceX the contact force to establish in x direction of the
	 *                      {@link AbstractLWR}'s compliance frame
	 * @param contactForceY the contact force to establish in y direction of the
	 *                      {@link AbstractLWR}'s compliance frame
	 * @param contactForceZ the contact force to establish in z direction of the
	 *                      {@link AbstractLWR}'s compliance frame
	 * @param parameters    optional {@link DeviceParameters}
	 * @return the RtActivity that performs linear motion to contact
	 * @throws RoboticsException thrown if RtActivity could not be created
	 */
	RtActivity linToContact(Frame to, double contactForceX, double contactForceY, double contactForceZ,
			DeviceParameters... parameters) throws RoboticsException;

	/**
	 * Creates an {@link RtActivity} that performs a linear motion to the given
	 * cartesian goal. The RtActivity expects to establish contact on the way to the
	 * goal and will then build up the specified cartesian force and stop.
	 * 
	 * The created RtActivity will throw an exception at the end of its execution if
	 * the expected contact force could not be established.
	 * 
	 * The method releaseForce() should be used to release contact forces before
	 * performing further non-contact motions.
	 * 
	 * @param to           the goal to drive to
	 * @param contactForce the magnitude of the contact force to establish
	 * @param speedFactor  factor by which to scale motion speed (0..1)
	 * @param parameters   optional {@link DeviceParameters}
	 * @return the RtActivity that performs linear motion to contact
	 * @throws RoboticsException thrown if RtActivity could not be created
	 */
	RtActivity linToContact(Frame to, double contactForce, double speedFactor, DeviceParameters... parameters)
			throws RoboticsException;

	/**
	 * Creates an {@link RtActivity} that performs a linear motion to the given
	 * cartesian goal. The RtActivity expects to establish contact on the way to the
	 * goal and will then build up the specified cartesian force and stop.
	 * 
	 * The created RtActivity will throw an exception at the end of its execution if
	 * the expected contact force could not be established.
	 * 
	 * The method releaseForce() should be used to release contact forces before
	 * performing further non-contact motions.
	 * 
	 * @param to            the goal to drive to
	 * @param contactForceX the contact force to establish in x direction of the
	 *                      {@link AbstractLWR}'s compliance frame
	 * @param contactForceY the contact force to establish in y direction of the
	 *                      {@link AbstractLWR}'s compliance frame
	 * @param contactForceZ the contact force to establish in z direction of the
	 *                      {@link AbstractLWR}'s compliance frame
	 * @param speedFactor   factor by which to scale motion speed (0..1)
	 * @param parameters    optional {@link DeviceParameters}
	 * @return the RtActivity that performs linear motion to contact
	 * @throws RoboticsException thrown if RtActivity could not be created
	 */
	RtActivity linToContact(Frame to, double contactForceX, double contactForceY, double contactForceZ,
			double speedFactor, DeviceParameters... parameters) throws RoboticsException;

	/**
	 * Creates an {@link RtActivity} that releases contact force by performing a
	 * linear motion from the {@link AbstractLWR}'s commanded position to its measured
	 * position.
	 * 
	 * @param parameters optional {@link DeviceParameters}
	 * @return the RtActivity that releases contact force
	 * @throws RoboticsException thrown if RtActivity could not be created
	 */
	RtActivity releaseForce(DeviceParameters... parameters) throws RoboticsException;

	@Override
	AbstractLWR getDevice();
}
