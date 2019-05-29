/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.action.mapper;

import java.util.List;
import java.util.Vector;

import org.roboticsapi.core.Action;
import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.kuka.lwr.controller.ImpedanceSpringSetting;
import org.roboticsapi.kuka.lwr.controller.LwrCartesianImpedanceController;
import org.roboticsapi.kuka.lwr.controller.LwrJointImpedanceController;
import org.roboticsapi.kuka.lwr.controller.LwrPositionController;
import org.roboticsapi.kuka.lwr.runtime.action.SwitchControllerAction;
import org.roboticsapi.kuka.lwr.runtime.action.mapper.SoftRobotLWRBlendControllerActionMapper.CartesianStiffnessDataflowType;
import org.roboticsapi.kuka.lwr.runtime.action.mapper.SoftRobotLWRBlendControllerActionMapper.ControlModeDataflowType;
import org.roboticsapi.kuka.lwr.runtime.action.mapper.SoftRobotLWRBlendControllerActionMapper.ControllerActionResult;
import org.roboticsapi.kuka.lwr.runtime.action.mapper.SoftRobotLWRBlendControllerActionMapper.JointStiffnessDataflowType;
import org.roboticsapi.multijoint.parameter.Controller;
import org.roboticsapi.runtime.SoftRobotRuntime;
import org.roboticsapi.runtime.core.primitives.DoubleValue;
import org.roboticsapi.runtime.core.primitives.IntValue;
import org.roboticsapi.runtime.mapping.MappingException;
import org.roboticsapi.runtime.mapping.net.ComposedDataflowOutPort;
import org.roboticsapi.runtime.mapping.net.DataflowOutPort;
import org.roboticsapi.runtime.mapping.net.NetFragment;
import org.roboticsapi.runtime.mapping.parts.ActionMapper;
import org.roboticsapi.runtime.mapping.parts.ActionMappingContext;
import org.roboticsapi.runtime.mapping.result.ActionMapperResult;
import org.roboticsapi.runtime.mapping.result.ActionResult;
import org.roboticsapi.runtime.mapping.result.impl.GoalActionMapperResult;
import org.roboticsapi.runtime.rpi.OutPort;
import org.roboticsapi.runtime.rpi.RPIException;

public class SoftRobotLWRSwitchControllerMapper implements ActionMapper<SoftRobotRuntime, SwitchControllerAction> {

	@Override
	public ActionMapperResult map(SoftRobotRuntime runtime, SwitchControllerAction action,
			DeviceParameterBag parameters, ActionMappingContext ports) throws MappingException, RPIException {
		NetFragment ret = new NetFragment("SwitchController");
		ComposedDataflowOutPort retPort = ret.addOutPort(new ComposedDataflowOutPort(true));

		int controllertype = 0;
		Controller controller = action.getController();
		if (controller instanceof LwrPositionController) {
			controllertype = 1;

		} else if (controller instanceof LwrCartesianImpedanceController) {

			controllertype = 2;

		} else if (controller instanceof LwrJointImpedanceController) {
			controllertype = 3;
		}

		IntValue controllerValue = ret.add(new IntValue(controllertype));

		DataflowOutPort controlMode = ret.addOutPort(new ControlModeDataflowType(), false,
				controllerValue.getOutValue());
		retPort.addDataflow(controlMode);

		if (controller instanceof LwrCartesianImpedanceController) {

			LwrCartesianImpedanceController cartcontroller = ((LwrCartesianImpedanceController) controller);

			ImpedanceSpringSetting[] cartsettings = { cartcontroller.getXImpedance(), cartcontroller.getYImpedance(),
					cartcontroller.getZImpedance(), cartcontroller.getAImpedance(), cartcontroller.getBImpedance(),
					cartcontroller.getCImpedance() };

			List<OutPort> cartPorts = new Vector<OutPort>();
			for (int i = 0; i < 6; i++) {
				DoubleValue stiffBlock = ret.add(new DoubleValue(cartsettings[i].getStiffness()));
				cartPorts.add(stiffBlock.getOutValue());
				DoubleValue dampBlock = ret.add(new DoubleValue(cartsettings[i].getDamping()));
				cartPorts.add(dampBlock.getOutValue());
			}
			DataflowOutPort cartStiff = ret.addOutPort(new CartesianStiffnessDataflowType(), false,
					cartPorts.toArray(new OutPort[12]));
			retPort.addDataflow(cartStiff);
		}

		if (controller instanceof LwrJointImpedanceController
				|| controller instanceof LwrCartesianImpedanceController) {
			ImpedanceSpringSetting[] jointImpedances;

			if (controller instanceof LwrJointImpedanceController) {
				jointImpedances = ((LwrJointImpedanceController) controller).getJointImpedances();
			} else {
				jointImpedances = ((LwrCartesianImpedanceController) controller).getJointImpedances();
			}

			List<OutPort> jointPorts = new Vector<OutPort>();
			for (int i = 0; i < 7; i++) {

				DoubleValue stiffBlock = ret.add(new DoubleValue(jointImpedances[i].getStiffness()));
				jointPorts.add(stiffBlock.getOutValue());

				DoubleValue dampBlock = ret.add(new DoubleValue(jointImpedances[i].getDamping()));
				jointPorts.add(dampBlock.getOutValue());
			}
			DataflowOutPort jointStiff = ret.addOutPort(new JointStiffnessDataflowType(), false,
					jointPorts.toArray(new OutPort[14]));
			retPort.addDataflow(jointStiff);

		}
		return new SwitchControllerActionMapperResult(action, ret,
				new ControllerActionResult(retPort, action.getController()));
	}

	public static class SwitchControllerActionMapperResult extends GoalActionMapperResult {

		public SwitchControllerActionMapperResult(Action action, NetFragment fragment, ActionResult result) {
			super(action, fragment, result);
		}
	}

}
