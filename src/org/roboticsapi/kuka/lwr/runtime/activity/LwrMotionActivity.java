/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.activity;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

import org.roboticsapi.activity.Activity;
import org.roboticsapi.activity.ActivityNotCompletedException;
import org.roboticsapi.activity.ActivityProperty;
import org.roboticsapi.activity.RtActivity;
import org.roboticsapi.activity.SingleDeviceRtActivity;
import org.roboticsapi.core.Action;
import org.roboticsapi.core.Command;
import org.roboticsapi.core.Device;
import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.core.State;
import org.roboticsapi.core.TransactionCommand;
import org.roboticsapi.core.eventhandler.CommandCanceller;
import org.roboticsapi.core.eventhandler.CommandStarter;
import org.roboticsapi.core.exception.RoboticsException;
import org.roboticsapi.kuka.lwr.AbstractLWR;
import org.roboticsapi.kuka.lwr.runtime.action.BlendControllerAction;
import org.roboticsapi.kuka.lwr.runtime.action.SwitchControllerAction;
import org.roboticsapi.kuka.lwr.runtime.action.SwitchToolAction;
import org.roboticsapi.multijoint.parameter.Controller;
import org.roboticsapi.multijoint.parameter.ControllerParameter;
import org.roboticsapi.robot.activity.RobotToolProperty;
import org.roboticsapi.robot.parameter.RobotTool;
import org.roboticsapi.robot.parameter.RobotToolParameter;

class LwrMotionActivity extends SingleDeviceRtActivity<AbstractLWR> {
	// private final DeviceParameterBag params;
	private final RtActivity motion;
	private final DeviceParameterBag parameters;

	LwrMotionActivity(AbstractLWR device, RtActivity motion, DeviceParameterBag parameters) {
		super(motion.getName(), device);
		this.motion = motion;
		this.parameters = parameters;
	}

	@Override
	protected boolean prepare(Activity prevActivity) throws RoboticsException, ActivityNotCompletedException {

		Command ptpcmd = motion.getCommand();
		State maintainingState = motion.getMaintainingState();

		Controller newController = parameters.get(ControllerParameter.class).getController();
		RobotTool newTool = parameters.get(RobotToolParameter.class).getRobotTool();

		addProperty(getDevice(), new ControllerProperty(newController));
		// FIXME: Robot tool ist nicht LWR spezifisch => in den allgemeinen
		// RobotArm!
		addProperty(getDevice(), new RobotToolProperty(newTool));

		ControllerProperty oldControllerProp = null;
		RobotToolProperty oldToolProp = null;

		if (prevActivity != null) {
			oldControllerProp = prevActivity.getProperty(getDevice(), ControllerProperty.class);
			oldToolProp = prevActivity.getProperty(getDevice(), RobotToolProperty.class);
		}

		// nothing has changed, no need to switch controller or tool
		if (oldControllerProp != null && newController.equals(oldControllerProp.getController()) && oldToolProp != null
				&& newTool.equals(oldToolProp.getRobotTool())) {
			TransactionCommand trans = getDevice().getDriver().getRuntime().createTransactionCommand(ptpcmd);
			trans.addStartCommand(ptpcmd);

			trans.addDoneStateCondition(ptpcmd.getCompletedState());

			trans.addTakeoverAllowedCondition(ptpcmd.getTakeoverAllowedState());
			trans.addStateFirstEnteredHandler(trans.getCancelState().and(ptpcmd.getActiveState()),
					new CommandCanceller(ptpcmd));

			setCommand(trans, prevActivity, maintainingState);
			return true;
		}

		TransactionCommand trans = getDevice().getDriver().getRuntime().createTransactionCommand(ptpcmd);

		trans.addStateEnteredHandler(trans.getCancelState().and(ptpcmd.getActiveState()), new CommandCanceller(ptpcmd));
		trans.addTakeoverAllowedCondition(ptpcmd.getTakeoverAllowedState());

		// if controller type stays the same and only parameters change, we
		// may use a BlendControllerAction
		// TODO: this is done only if Tool does not change, may we also do
		// that if Tool changes?
		if (oldControllerProp != null && newController.getClass().equals(oldControllerProp.getController().getClass())
				&& oldToolProp != null && newTool.equals(oldToolProp.getRobotTool())) {
			Action switchAction = new BlendControllerAction(oldControllerProp.getController(), newController, 0.3);

			AbstractLWR r = getDevice();

			Command switchCmd = r.getDriver().getRuntime().createRuntimeCommand(r, switchAction, parameters);

			trans.addStartCommand(switchCmd);

			trans.addStateEnteredHandler(switchCmd.getCompletedState(), new CommandStarter(ptpcmd));

		}
		// otherwise, we need a SwitchControllerAction
		else {
			Action switchAction = new SwitchControllerAction(newController);
			SwitchToolAction switchToolAction = new SwitchToolAction(newTool);
			AbstractLWR r = getDevice();

			Command switchCmd = r.getDriver().getRuntime().createRuntimeCommand(r, switchAction, parameters);
			Command switchToolCmd = r.getDriver().getRuntime().createRuntimeCommand(r, switchToolAction, parameters);

			trans.addStartCommand(switchCmd);
			trans.addStartCommand(switchToolCmd);

			trans.addStateEnteredHandler(switchCmd.getCompletedState().and(switchToolCmd.getCompletedState()),
					new CommandStarter(ptpcmd));
		}

		setCommand(trans, prevActivity, maintainingState);

		return false;
	}

	@Override
	public Set<Device> prepare(Map<Device, Activity> prevActivities)
			throws RoboticsException, ActivityNotCompletedException {
		Set<Device> prepare = motion.prepare(prevActivities);
		Set<Device> superPrepare = super.prepare(prevActivities);
		return (superPrepare != null) ? prepare : null;
	}

	@Override
	public <T extends ActivityProperty> Future<T> getFutureProperty(Device device, Class<T> property) {
		Future<T> ptpProp = motion.getFutureProperty(device, property);
		if (ptpProp != null) {
			return ptpProp;
		}

		return super.getFutureProperty(device, property);
	}
}