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
import org.roboticsapi.kuka.lwr.runtime.action.BlendControllerAction;
import org.roboticsapi.multijoint.parameter.Controller;
import org.roboticsapi.runtime.SoftRobotRuntime;
import org.roboticsapi.runtime.core.primitives.BooleanNot;
import org.roboticsapi.runtime.core.primitives.Clock;
import org.roboticsapi.runtime.core.primitives.IntValue;
import org.roboticsapi.runtime.core.primitives.Interval;
import org.roboticsapi.runtime.core.primitives.Lerp;
import org.roboticsapi.runtime.mapping.MappingException;
import org.roboticsapi.runtime.mapping.UnimplementedMappingException;
import org.roboticsapi.runtime.mapping.net.ComposedDataflowOutPort;
import org.roboticsapi.runtime.mapping.net.DataflowOutPort;
import org.roboticsapi.runtime.mapping.net.DataflowType;
import org.roboticsapi.runtime.mapping.net.NetFragment;
import org.roboticsapi.runtime.mapping.net.StateDataflow;
import org.roboticsapi.runtime.mapping.parts.ActionMapper;
import org.roboticsapi.runtime.mapping.parts.ActionMappingContext;
import org.roboticsapi.runtime.mapping.result.ActionMapperResult;
import org.roboticsapi.runtime.mapping.result.ActionResult;
import org.roboticsapi.runtime.mapping.result.impl.BaseActionMapperResult;
import org.roboticsapi.runtime.rpi.OutPort;
import org.roboticsapi.runtime.rpi.RPIException;

public class SoftRobotLWRBlendControllerActionMapper implements ActionMapper<SoftRobotRuntime, BlendControllerAction> {

	@Override
	public ActionMapperResult map(SoftRobotRuntime runtime, BlendControllerAction action, DeviceParameterBag parameters,
			ActionMappingContext ports) throws MappingException, RPIException {
		NetFragment ret = new NetFragment("BlendController");
		NetFragment fragment = new NetFragment("BlendController");
		ComposedDataflowOutPort retPort = fragment.addOutPort(new ComposedDataflowOutPort(true));

		int controllertype = 0;
		Controller startController = action.getStartController();
		Controller endController = action.getEndController();

		if (!startController.getClass().equals(endController.getClass())) {
			throw new MappingException("Cannot blend between different controller modes.");
		}

		if (startController instanceof LwrPositionController) {
			controllertype = 1;

		} else if (startController instanceof LwrCartesianImpedanceController) {

			controllertype = 2;

		} else if (startController instanceof LwrJointImpedanceController) {
			controllertype = 3;
		}

		IntValue controllerValue = fragment.add(new IntValue(controllertype));

		DataflowOutPort controlMode = fragment.addOutPort(new ControlModeDataflowType(), false,
				controllerValue.getOutValue());
		retPort.addDataflow(controlMode);

		Clock clock = fragment.add(new Clock());
		Interval scale = fragment.add(new Interval(0.0, action.getDuration()));
		scale.getInValue().connectTo(clock.getOutValue());

		BooleanNot completed = fragment.add(new BooleanNot());
		completed.getInValue().connectTo(scale.getOutActive());
		DataflowOutPort completedPort = fragment.addOutPort(new StateDataflow(), false, completed.getOutValue());

		if (startController instanceof LwrCartesianImpedanceController) {

			LwrCartesianImpedanceController cartStart = ((LwrCartesianImpedanceController) startController),
					cartEnd = ((LwrCartesianImpedanceController) endController);

			ImpedanceSpringSetting[] startSettings = { cartStart.getXImpedance(), cartStart.getYImpedance(),
					cartStart.getZImpedance(), cartStart.getAImpedance(), cartStart.getBImpedance(),
					cartStart.getCImpedance() },
					endSettings = { cartEnd.getXImpedance(), cartEnd.getYImpedance(), cartEnd.getZImpedance(),
							cartEnd.getAImpedance(), cartEnd.getBImpedance(), cartEnd.getCImpedance() };

			List<OutPort> cartPorts = new Vector<OutPort>();
			for (int i = 0; i < 6; i++) {
				Lerp stiffBlock = fragment
						.add(new Lerp(startSettings[i].getStiffness(), endSettings[i].getStiffness()));
				stiffBlock.getInAmount().connectTo(scale.getOutValue());
				cartPorts.add(stiffBlock.getOutValue());
				Lerp dampBlock = fragment.add(new Lerp(startSettings[i].getDamping(), endSettings[i].getDamping()));
				cartPorts.add(dampBlock.getOutValue());
				dampBlock.getInAmount().connectTo(scale.getOutValue());
			}
			DataflowOutPort cartStiff = fragment.addOutPort(new CartesianStiffnessDataflowType(), false,
					cartPorts.toArray(new OutPort[12]));
			retPort.addDataflow(cartStiff);
		}

		if (startController instanceof LwrJointImpedanceController
				|| startController instanceof LwrCartesianImpedanceController) {
			ImpedanceSpringSetting[] startImpedances, endImpedances;

			if (startController instanceof LwrJointImpedanceController) {
				startImpedances = ((LwrJointImpedanceController) startController).getJointImpedances();
				endImpedances = ((LwrJointImpedanceController) endController).getJointImpedances();
			} else {
				startImpedances = ((LwrCartesianImpedanceController) startController).getJointImpedances();
				endImpedances = ((LwrCartesianImpedanceController) endController).getJointImpedances();
			}

			List<OutPort> jointPorts = new Vector<OutPort>();
			for (int i = 0; i < 7; i++) {

				Lerp stiffBlock = fragment
						.add(new Lerp(startImpedances[i].getStiffness(), endImpedances[i].getStiffness()));
				stiffBlock.getInAmount().connectTo(scale.getOutValue());
				jointPorts.add(stiffBlock.getOutValue());

				Lerp dampBlock = fragment.add(new Lerp(startImpedances[i].getDamping(), endImpedances[i].getDamping()));
				dampBlock.getInAmount().connectTo(scale.getOutValue());
				jointPorts.add(dampBlock.getOutValue());
			}
			DataflowOutPort cartStiff = fragment.addOutPort(new JointStiffnessDataflowType(), false,
					jointPorts.toArray(new OutPort[14]));
			retPort.addDataflow(cartStiff);

		}
		ret.add(fragment);
		return new SwitchControllerActionMapperResult(action, ret,
				new ControllerActionResult(retPort, action.getStartController()), completedPort);
	}

	// public static class SwitchControllerActionResult extends ActionResult {
	//
	// private final Controller controller;
	//
	// public SwitchControllerActionResult(DataflowOutPort outPort,
	// Controller controller) {
	// super(outPort);
	// this.controller = controller;
	// }
	//
	// public Controller getController() {
	// return controller;
	// }
	// }

	public static class SwitchControllerActionMapperResult extends BaseActionMapperResult {

		public SwitchControllerActionMapperResult(Action action, NetFragment fragment, ActionResult result,
				DataflowOutPort active) {
			super(action, fragment, result, active);
		}

	}

	public static class ControlModeDataflowType extends DataflowType {
		public ControlModeDataflowType() {
			super(1);
		}

		@Override
		public ValueReader[] createValueReaders(String uniqueKeyPrefix, List<OutPort> valuePorts, NetFragment fragment)
				throws RPIException, MappingException {
			// TODO: implement createValueReaders
			throw new UnimplementedMappingException();
		}
	}

	public static class JointStiffnessDataflowType extends DataflowType {
		public JointStiffnessDataflowType() {
			super(14);
		}

		@Override
		public ValueReader[] createValueReaders(String uniqueKeyPrefix, List<OutPort> valuePorts, NetFragment fragment)
				throws RPIException, MappingException {
			// TODO: implement createValueReaders
			throw new UnimplementedMappingException();
		}
	}

	public static class CartesianStiffnessDataflowType extends DataflowType {
		public CartesianStiffnessDataflowType() {
			super(12);
		}

		@Override
		public ValueReader[] createValueReaders(String uniqueKeyPrefix, List<OutPort> valuePorts, NetFragment fragment)
				throws RPIException, MappingException {
			// TODO: implement createValueReaders
			throw new UnimplementedMappingException();
		}
	}

	public static class ControllerActionResult extends ActionResult {

		private final Controller controller;

		public ControllerActionResult(DataflowOutPort outPort, Controller controller) {
			super(outPort);
			this.controller = controller;
		}

		public Controller getController() {
			return controller;
		}
	}
}
