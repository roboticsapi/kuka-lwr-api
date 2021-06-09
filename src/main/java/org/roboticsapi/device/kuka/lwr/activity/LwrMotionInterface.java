/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.activity;

import org.roboticsapi.core.DeviceParameters;
import org.roboticsapi.core.activity.Activity;
import org.roboticsapi.core.exception.RoboticsException;
import org.roboticsapi.core.world.Pose;
import org.roboticsapi.device.kuka.lwr.Lwr4;
import org.roboticsapi.framework.cartesianmotion.controller.CartesianController;
import org.roboticsapi.framework.multijoint.controller.JointController;
import org.roboticsapi.framework.multijoint.controller.JointPositionController;
import org.roboticsapi.framework.robot.activity.MotionInterface;

/**
 * The Interface LwrMotionInterface extends the basic {@link MotionInterface} by
 * motions specific to the {@link Lwr4}.
 */
public interface LwrMotionInterface extends MotionInterface {

	/**
	 * Creates an {@link Activity} that activates the {@link Lwr4}'s
	 * gravitation compensation mode. Will switch to {@link JointPositionController}
	 * when cancelled.
	 *
	 * @param parameters optional {@link DeviceParameters}
	 * @return the RtActivity activating gravitation compensation mode
	 * @throws RoboticsException thrown if RtActivity could not be created
	 */
	Activity gravComp(DeviceParameters... parameters) throws RoboticsException;

	/**
	 * Creates an {@link Activity} that activates the {@link Lwr4}'s
	 * gravitation compensation mode. Will switch to supplied
	 * {@link JointController} when cancelled.
	 *
	 * @param finalController the controller to switch to when cancelled
	 * @param parameters      optional {@link DeviceParameters}
	 * @return the RtActivity activating gravitation compensation mode
	 * @throws RoboticsException thrown if RtActivity could not be created
	 */
	Activity gravComp(JointController finalController, DeviceParameters... parameters) throws RoboticsException;

	/**
	 * Creates an {@link Activity} that activates the {@link Lwr4}'s
	 * gravitation compensation mode. Will switch to supplied
	 * {@link CartesianController} when cancelled.
	 *
	 * @param finalController the controller to switch to when cancelled
	 * @param parameters      optional {@link DeviceParameters}
	 * @return the RtActivity activating gravitation compensation mode
	 * @throws RoboticsException thrown if RtActivity could not be created
	 */
	Activity gravComp(CartesianController finalController, DeviceParameters... parameters) throws RoboticsException;

	/**
	 * Creates an {@link Activity} that performs a linear motion to the given
	 * cartesian goal. The RtActivity expects to establish contact on the way to the
	 * goal and will then build up the specified (absolute) cartesian force and
	 * stop.
	 *
	 * The created RtActivity will throw an exception at the end of its execution if
	 * the expected contact force could not be established.
	 *
	 * The method releaseForce() should be used to release contact forces before
	 * performing further non-contact motions.
	 *
	 * @param to           the goal to drive to
	 * @param contactForce the magnitude of the contact force to establish; the
	 *                     absolute value will be considered
	 * @param parameters   optional {@link DeviceParameters}
	 * @return the RtActivity that performs linear motion to contact
	 * @throws RoboticsException thrown if RtActivity could not be created
	 */
	Activity linToContact(Pose to, double contactForce, DeviceParameters... parameters) throws RoboticsException;

	/**
	 * Creates an {@link Activity} that performs a linear motion to the given
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
	 * @param contactForce  the magnitude of the contact force to establish
	 * @param absoluteForce whether the absolute value of the force should be
	 *                      considered
	 * @param parameters    optional {@link DeviceParameters}
	 * @return the RtActivity that performs linear motion to contact
	 * @throws RoboticsException thrown if RtActivity could not be created
	 */
	Activity linToContact(Pose to, double contactForce, boolean absoluteForce, DeviceParameters... parameters)
			throws RoboticsException;

	/**
	 * Creates an {@link Activity} that performs a linear motion to the given
	 * cartesian goal. The RtActivity expects to establish contact on the way to the
	 * goal and will then build up the specified (absolute) cartesian force and
	 * stop.
	 *
	 * The created RtActivity will throw an exception at the end of its execution if
	 * the expected contact force could not be established.
	 *
	 * The method releaseForce() should be used to release contact forces before
	 * performing further non-contact motions.
	 *
	 * @param to            the goal to drive to
	 * @param contactForceX the contact force to establish in x direction of the
	 *                      {@link Lwr4}'s compliance frame; the absolute value
	 *                      will be considered
	 * @param contactForceY the contact force to establish in y direction of the
	 *                      {@link Lwr4}'s compliance frame; the absolute value
	 *                      will be considered
	 * @param contactForceZ the contact force to establish in z direction of the
	 *                      {@link Lwr4}'s compliance frame; the absolute value
	 *                      will be considered
	 * @param parameters    optional {@link DeviceParameters}
	 * @return the RtActivity that performs linear motion to contact
	 * @throws RoboticsException thrown if RtActivity could not be created
	 */
	Activity linToContact(Pose to, double contactForceX, double contactForceY, double contactForceZ,
			DeviceParameters... parameters) throws RoboticsException;

	/**
	 * Creates an {@link Activity} that performs a linear motion to the given
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
	 *                      {@link Lwr4}'s compliance frame
	 * @param contactForceY the contact force to establish in y direction of the
	 *                      {@link Lwr4}'s compliance frame
	 * @param contactForceZ the contact force to establish in z direction of the
	 *                      {@link Lwr4}'s compliance frame
	 * @param absoluteForce whether the absolute value of the force should be
	 *                      considered
	 * @param parameters    optional {@link DeviceParameters}
	 * @return the RtActivity that performs linear motion to contact
	 * @throws RoboticsException thrown if RtActivity could not be created
	 */
	Activity linToContact(Pose to, double contactForceX, double contactForceY, double contactForceZ,
			boolean absoluteForce, DeviceParameters... parameters) throws RoboticsException;

	/**
	 * Creates an {@link Activity} that performs a linear motion to the given
	 * cartesian goal. The RtActivity expects to establish contact on the way to the
	 * goal and will then build up the specified (absolute) cartesian force and
	 * stop.
	 *
	 * The created RtActivity will throw an exception at the end of its execution if
	 * the expected contact force could not be established.
	 *
	 * The method releaseForce() should be used to release contact forces before
	 * performing further non-contact motions.
	 *
	 * @param to           the goal to drive to
	 * @param contactForce the magnitude of the contact force to establish; the
	 *                     absolute value will be considered
	 * @param speedFactor  factor by which to scale motion speed (0..1)
	 * @param parameters   optional {@link DeviceParameters}
	 * @return the RtActivity that performs linear motion to contact
	 * @throws RoboticsException thrown if RtActivity could not be created
	 */
	Activity linToContact(Pose to, double contactForce, double speedFactor, DeviceParameters... parameters)
			throws RoboticsException;

	/**
	 * Creates an {@link Activity} that performs a linear motion to the given
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
	 * @param contactForce  the magnitude of the contact force to establish
	 * @param absoluteForce whether the absolute value of the force should be
	 *                      considered
	 * @param speedFactor   factor by which to scale motion speed (0..1)
	 * @param parameters    optional {@link DeviceParameters}
	 * @return the RtActivity that performs linear motion to contact
	 * @throws RoboticsException thrown if RtActivity could not be created
	 */
	Activity linToContact(Pose to, double contactForce, boolean absoluteForce, double speedFactor,
			DeviceParameters... parameters) throws RoboticsException;

	/**
	 * Creates an {@link Activity} that performs a linear motion to the given
	 * cartesian goal. The RtActivity expects to establish contact on the way to the
	 * goal and will then build up the specified (absolute) cartesian force and
	 * stop.
	 *
	 * The created RtActivity will throw an exception at the end of its execution if
	 * the expected contact force could not be established.
	 *
	 * The method releaseForce() should be used to release contact forces before
	 * performing further non-contact motions.
	 *
	 * @param to            the goal to drive to
	 * @param contactForceX the contact force to establish in x direction of the
	 *                      {@link Lwr4}'s compliance frame; the absolute value
	 *                      will be considered
	 * @param contactForceY the contact force to establish in y direction of the
	 *                      {@link Lwr4}'s compliance frame; the absolute value
	 *                      will be considered
	 * @param contactForceZ the contact force to establish in z direction of the
	 *                      {@link Lwr4}'s compliance frame; the absolute value
	 *                      will be considered
	 * @param speedFactor   factor by which to scale motion speed (0..1)
	 * @param parameters    optional {@link DeviceParameters}
	 * @return the RtActivity that performs linear motion to contact
	 * @throws RoboticsException thrown if RtActivity could not be created
	 */
	Activity linToContact(Pose to, double contactForceX, double contactForceY, double contactForceZ, double speedFactor,
			DeviceParameters... parameters) throws RoboticsException;

	/**
	 * Creates an {@link Activity} that performs a linear motion to the given
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
	 *                      {@link Lwr4}'s compliance frame
	 * @param contactForceY the contact force to establish in y direction of the
	 *                      {@link Lwr4}'s compliance frame
	 * @param contactForceZ the contact force to establish in z direction of the
	 *                      {@link Lwr4}'s compliance frame
	 * @param absoluteForce whether the absolute value of the force should be
	 *                      considered
	 * @param speedFactor   factor by which to scale motion speed (0..1)
	 * @param parameters    optional {@link DeviceParameters}
	 * @return the RtActivity that performs linear motion to contact
	 * @throws RoboticsException thrown if RtActivity could not be created
	 */
	Activity linToContact(Pose to, double contactForceX, double contactForceY, double contactForceZ,
			boolean absoluteForce, double speedFactor, DeviceParameters... parameters) throws RoboticsException;

	/**
	 * Creates an {@link Activity} that releases contact force by performing a
	 * linear motion from the {@link Lwr4}'s commanded position to its measured
	 * position.
	 *
	 * @param parameters optional {@link DeviceParameters}
	 * @return the RtActivity that releases contact force
	 * @throws RoboticsException thrown if RtActivity could not be created
	 */
	Activity releaseForce(DeviceParameters... parameters) throws RoboticsException;

}
