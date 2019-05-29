/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.driver.mapper;

import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.core.exception.RoboticsException;
import org.roboticsapi.kuka.lwr.runtime.action.mapper.ToolActionResult;
import org.roboticsapi.kuka.lwr.runtime.driver.SoftRobotLWRDriver;
import org.roboticsapi.kuka.lwr.runtime.primitives.ToolParameters;
import org.roboticsapi.robot.parameter.CompliantRobotTool;
import org.roboticsapi.robot.parameter.RobotTool;
import org.roboticsapi.runtime.SoftRobotRuntime;
import org.roboticsapi.runtime.core.primitives.DoubleValue;
import org.roboticsapi.runtime.mapping.MappingException;
import org.roboticsapi.runtime.mapping.net.DataflowOutPort;
import org.roboticsapi.runtime.mapping.net.IntDataflow;
import org.roboticsapi.runtime.mapping.net.NetFragment;
import org.roboticsapi.runtime.mapping.net.StateDataflow;
import org.roboticsapi.runtime.mapping.parts.ActuatorDriverMapper;
import org.roboticsapi.runtime.mapping.parts.DeviceMappingPorts;
import org.roboticsapi.runtime.mapping.parts.ErrorNumberSwitchFragment;
import org.roboticsapi.runtime.mapping.result.ActuatorDriverMapperResult;
import org.roboticsapi.runtime.rpi.RPIException;
import org.roboticsapi.runtime.world.primitives.FrameFromPosRot;
import org.roboticsapi.runtime.world.primitives.VectorFromXYZ;
import org.roboticsapi.world.Frame;
import org.roboticsapi.world.Transformation;

public class SoftRobotLWRToolDeviceMapper
		implements ActuatorDriverMapper<SoftRobotRuntime, SoftRobotLWRDriver, ToolActionResult> {

	@Override
	public ActuatorDriverMapperResult map(SoftRobotRuntime runtime, SoftRobotLWRDriver deviceDriver,
			ToolActionResult actionResult, DeviceParameterBag parameters, DeviceMappingPorts ports)
			throws MappingException, RPIException {

		try {
			NetFragment ret = new NetFragment("LWRToolDevice");

			// TODO: Use FrameValue and VectorValue blocks - needs JSON

			RobotTool robotTool = actionResult.getRobotTool();

			Frame coc;
			if (robotTool instanceof CompliantRobotTool) {
				coc = ((CompliantRobotTool) robotTool).getCenterOfCompliance();
			} else {
				coc = deviceDriver.getDefaultMotionCenter();
			}

			Transformation transTCP = deviceDriver.getFlange().getTransformationTo(coc, false);
			Transformation transCOM = deviceDriver.getFlange()
					.getTransformationTo(actionResult.getRobotTool().getCenterOfMass(), false);

			FrameFromPosRot fTCP = ret.add(new FrameFromPosRot(transTCP.getTranslation().getX(),
					transTCP.getTranslation().getY(), transTCP.getTranslation().getZ(), transTCP.getRotation().getA(),
					transTCP.getRotation().getB(), transTCP.getRotation().getC()));

			FrameFromPosRot fCOM = ret.add(new FrameFromPosRot(transCOM.getTranslation().getX(),
					transCOM.getTranslation().getY(), transCOM.getTranslation().getZ(), transCOM.getRotation().getA(),
					transCOM.getRotation().getB(), transCOM.getRotation().getC()));

			VectorFromXYZ vMOI = ret.add(new VectorFromXYZ(actionResult.getRobotTool().getJx(),
					actionResult.getRobotTool().getJy(), actionResult.getRobotTool().getJz()));

			DoubleValue dv = ret.add(new DoubleValue(actionResult.getRobotTool().getMass()));

			ToolParameters tp = ret.add(new ToolParameters());
			tp.setrobot(deviceDriver.getDeviceName());

			tp.getInTCP().connectTo(fTCP.getOutValue());
			tp.getInCOM().connectTo(fCOM.getOutValue());
			tp.getInMOI().connectTo(vMOI.getOutValue());
			tp.getInMass().connectTo(dv.getOutValue());

			ErrorNumberSwitchFragment error = ret
					.add(new ErrorNumberSwitchFragment(ret.addOutPort(new IntDataflow(), false, tp.getOutError())));

			DataflowOutPort concurrentAccessPort = error.getCasePort(1);
			DataflowOutPort drivesNotEnabledPort = error.getCasePort(2);
			DataflowOutPort tenseSpringPort = error.getCasePort(3);
			DataflowOutPort unknownErrorPort = error.getDefaultPort();

			DataflowOutPort completed = ret.addOutPort(new StateDataflow(), false, tp.getOutCompleted());

			return new ToolDeviceDriverMapperResult(deviceDriver, ret, completed, concurrentAccessPort,
					drivesNotEnabledPort, tenseSpringPort, unknownErrorPort);
		} catch (RoboticsException e) {
			throw new MappingException(e);
		}
	}
}
