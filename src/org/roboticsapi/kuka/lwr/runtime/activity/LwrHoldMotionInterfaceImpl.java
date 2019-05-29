/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.activity;

import org.roboticsapi.activity.Activity;
import org.roboticsapi.activity.ActivityNotCompletedException;
import org.roboticsapi.activity.RtActivity;
import org.roboticsapi.activity.SingleDeviceRtActivity;
import org.roboticsapi.cartesianmotion.action.HoldCartesianPosition;
import org.roboticsapi.cartesianmotion.activity.BlendingStartCartesianStatusProperty;
import org.roboticsapi.cartesianmotion.activity.FrameGoalProperty;
import org.roboticsapi.cartesianmotion.activity.HoldMotionInterface;
import org.roboticsapi.cartesianmotion.activity.HoldMotionInterfaceImpl;
import org.roboticsapi.cartesianmotion.parameter.MotionCenterParameter;
import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.core.DeviceParameters;
import org.roboticsapi.core.RuntimeCommand;
import org.roboticsapi.core.exception.RoboticsException;
import org.roboticsapi.kuka.lwr.AbstractLWR;
import org.roboticsapi.multijoint.parameter.ControllerParameter;
import org.roboticsapi.robot.activity.RobotToolProperty;
import org.roboticsapi.robot.parameter.RobotToolParameter;
import org.roboticsapi.world.Frame;
import org.roboticsapi.world.Twist;

public final class LwrHoldMotionInterfaceImpl extends HoldMotionInterfaceImpl<AbstractLWR> implements HoldMotionInterface {

	public LwrHoldMotionInterfaceImpl(AbstractLWR device) {
		super(device);
	}

	@Override
	public RtActivity holdCartesianPosition(final Frame position, final DeviceParameters... parameters)
			throws RoboticsException {
		return new SingleDeviceRtActivity<AbstractLWR>(getDevice()) {

			@Override
			protected boolean prepare(Activity prevActivity) throws RoboticsException, ActivityNotCompletedException {

				DeviceParameterBag params = getDefaultParameters().withParameters(parameters);
				RuntimeCommand cmd = getDevice().getDriver().getRuntime().createRuntimeCommand(
						LwrHoldMotionInterfaceImpl.this.getDevice(), new HoldCartesianPosition(position), params);

				cmd.addTakeoverAllowedCondition(cmd.getStartedState());

				if (params.get(ControllerParameter.class) != null) {
					addProperty(getDevice(),
							new ControllerProperty(params.get(ControllerParameter.class).getController()));
				}

				if (params.get(RobotToolParameter.class) != null) {
					addProperty(getDevice(),
							new RobotToolProperty(params.get(RobotToolParameter.class).getRobotTool()));
				}

				addProperty(getDevice(),
						new FrameGoalProperty(position, params.get(MotionCenterParameter.class).getMotionCenter()));

				addProperty(getDevice(), new BlendingStartCartesianStatusProperty(
						params.get(MotionCenterParameter.class).getMotionCenter(), position, new Twist()));

				setCommand(cmd, prevActivity, cmd.getStartedState());

				return false;
			}
		};
	}

}
