/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.driver.mapper;

import java.util.Vector;

import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.core.actuator.ActuatorNotOperationalException;
import org.roboticsapi.core.actuator.ConcurrentAccessException;
import org.roboticsapi.core.actuator.GeneralActuatorException;
import org.roboticsapi.kuka.lwr.TenseSpringException;
import org.roboticsapi.kuka.lwr.controller.LwrCartesianImpedanceController;
import org.roboticsapi.kuka.lwr.controller.LwrJointImpedanceController;
import org.roboticsapi.kuka.lwr.runtime.action.mapper.SoftRobotLWRBlendControllerActionMapper.CartesianStiffnessDataflowType;
import org.roboticsapi.kuka.lwr.runtime.action.mapper.SoftRobotLWRBlendControllerActionMapper.ControlModeDataflowType;
import org.roboticsapi.kuka.lwr.runtime.action.mapper.SoftRobotLWRBlendControllerActionMapper.ControllerActionResult;
import org.roboticsapi.kuka.lwr.runtime.action.mapper.SoftRobotLWRBlendControllerActionMapper.JointStiffnessDataflowType;
import org.roboticsapi.kuka.lwr.runtime.driver.SoftRobotLWRDriver;
import org.roboticsapi.kuka.lwr.runtime.primitives.CartImpParameters;
import org.roboticsapi.kuka.lwr.runtime.primitives.ControlStrategy;
import org.roboticsapi.kuka.lwr.runtime.primitives.JointImpParameters;
import org.roboticsapi.runtime.SoftRobotRuntime;
import org.roboticsapi.runtime.mapping.MappingException;
import org.roboticsapi.runtime.mapping.net.ComposedDataflowInPort;
import org.roboticsapi.runtime.mapping.net.DataflowOutPort;
import org.roboticsapi.runtime.mapping.net.IntDataflow;
import org.roboticsapi.runtime.mapping.net.NetFragment;
import org.roboticsapi.runtime.mapping.net.StateDataflow;
import org.roboticsapi.runtime.mapping.parts.ActuatorDriverMapper;
import org.roboticsapi.runtime.mapping.parts.DeviceMappingPorts;
import org.roboticsapi.runtime.mapping.parts.ErrorNumberSwitchFragment;
import org.roboticsapi.runtime.mapping.result.impl.BaseActuatorDriverMapperResult;
import org.roboticsapi.runtime.rpi.InPort;
import org.roboticsapi.runtime.rpi.RPIException;
import org.roboticsapi.runtime.world.primitives.WrenchFromXYZ;

public class SoftRobotLWRControllerDeviceMapper
		implements ActuatorDriverMapper<SoftRobotRuntime, SoftRobotLWRDriver, ControllerActionResult> {

	@Override
	public BaseActuatorDriverMapperResult map(SoftRobotRuntime runtime, SoftRobotLWRDriver deviceDriver,
			ControllerActionResult actionResult, DeviceParameterBag parameters, DeviceMappingPorts ports)
			throws MappingException, RPIException {
		NetFragment fragment = new NetFragment("SwitchController");

		ComposedDataflowInPort inPort = fragment.addInPort(new ComposedDataflowInPort(true));

		ControlStrategy strategyBlock = fragment.add(new ControlStrategy(deviceDriver.getDeviceName()));

		inPort.addInPort(fragment.addInPort(new ControlModeDataflowType(), true, strategyBlock.getInStrategy()));

		if (actionResult.getController() instanceof LwrCartesianImpedanceController) {

			CartImpParameters cartParam = fragment.add(new CartImpParameters(deviceDriver.getDeviceName()));

			WrenchFromXYZ stiffwr = fragment.add(new WrenchFromXYZ());
			WrenchFromXYZ dampwr = fragment.add(new WrenchFromXYZ());

			InPort[] stiffinp = { stiffwr.getInX(), stiffwr.getInY(), stiffwr.getInZ(), stiffwr.getInA(),
					stiffwr.getInB(), stiffwr.getInC() };
			InPort[] dampinp = { dampwr.getInX(), dampwr.getInY(), dampwr.getInZ(), dampwr.getInA(), dampwr.getInB(),
					dampwr.getInC() };

			Vector<InPort> cartPorts = new Vector<InPort>();
			for (int i = 0; i < 6; i++) {
				cartPorts.add(stiffinp[i]);
				cartPorts.add(dampinp[i]);
			}
			inPort.addInPort(
					fragment.addInPort(new CartesianStiffnessDataflowType(), true, cartPorts.toArray(new InPort[12])));

			cartParam.getInStiffness().connectTo(stiffwr.getOutValue());
			cartParam.getInDamping().connectTo(dampwr.getOutValue());

		}

		if (actionResult.getController() instanceof LwrJointImpedanceController
				|| actionResult.getController() instanceof LwrCartesianImpedanceController) {

			Vector<InPort> jointPorts = new Vector<InPort>();
			for (int i = 0; i < 7; i++) {
				JointImpParameters jointParam = fragment.add(new JointImpParameters(deviceDriver.getDeviceName(), i));

				jointPorts.add(jointParam.getInStiffness());
				jointPorts.add(jointParam.getInDamping());
			}
			inPort.addInPort(
					fragment.addInPort(new JointStiffnessDataflowType(), true, jointPorts.toArray(new InPort[14])));

		}

		final DataflowOutPort completed = fragment.addOutPort(new StateDataflow(), false,
				strategyBlock.getOutCompleted());

		ErrorNumberSwitchFragment errorNumber = fragment.add(new ErrorNumberSwitchFragment(
				fragment.addOutPort(new IntDataflow(), false, strategyBlock.getOutError())));
		DataflowOutPort concurrentAccessP = errorNumber.getCasePort(1);
		DataflowOutPort drivesNotEnabledP = errorNumber.getCasePort(2);
		DataflowOutPort tenseSpringP = errorNumber.getCasePort(3);
		DataflowOutPort unknownErrorP = errorNumber.getDefaultPort();

		inPort.connectTo(actionResult.getOutPort());
		BaseActuatorDriverMapperResult ret = new BaseActuatorDriverMapperResult(deviceDriver, fragment, completed);
		ret.addExceptionPort(ConcurrentAccessException.class, concurrentAccessP);
		ret.addExceptionPort(ActuatorNotOperationalException.class, drivesNotEnabledP);
		ret.addExceptionPort(TenseSpringException.class, tenseSpringP);
		ret.addExceptionPort(GeneralActuatorException.class, unknownErrorP);
		return ret;
	}

}
