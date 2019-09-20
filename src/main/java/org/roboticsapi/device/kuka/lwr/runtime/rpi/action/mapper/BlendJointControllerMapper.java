/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.runtime.rpi.action.mapper;

import java.util.Map;

import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.core.action.Plan;
import org.roboticsapi.core.action.PlannedAction;
import org.roboticsapi.core.realtimevalue.realtimeboolean.RealtimeBoolean;
import org.roboticsapi.core.realtimevalue.realtimedouble.RealtimeDouble;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.action.BlendJointController;
import org.roboticsapi.facet.runtime.rpi.RpiException;
import org.roboticsapi.facet.runtime.rpi.mapping.ActionMapper;
import org.roboticsapi.facet.runtime.rpi.mapping.ActionResult;
import org.roboticsapi.facet.runtime.rpi.mapping.MapperRegistry;
import org.roboticsapi.facet.runtime.rpi.mapping.MappingException;
import org.roboticsapi.framework.multijoint.controller.JointImpedanceController;
import org.roboticsapi.framework.multijoint.runtime.rpi.result.JointImpedanceControllerActionResult;

public class BlendJointControllerMapper implements ActionMapper<BlendJointController> {

	@Override
	public ActionResult map(BlendJointController action, DeviceParameterBag parameters, MapperRegistry registry,
			RealtimeBoolean cancel, RealtimeDouble override, RealtimeDouble time, Map<PlannedAction<?>, Plan> plans)
			throws MappingException, RpiException {

		if (action.getStartController() instanceof JointImpedanceController
				&& action.getEndController() instanceof JointImpedanceController) {
			JointImpedanceController from = (JointImpedanceController) action.getStartController(),
					to = (JointImpedanceController) action.getEndController();
			RealtimeDouble[] stiffness = new RealtimeDouble[from.getJointCount()];
			RealtimeDouble[] damping = new RealtimeDouble[from.getJointCount()];
			RealtimeDouble[] addTorque = new RealtimeDouble[from.getJointCount()];
			double[] maxTorques = new double[from.getJointCount()];
			RealtimeDouble fromAmount = time.divide(action.getDuration());
			RealtimeDouble toAmount = time.add(-action.getDuration()).divide(-action.getDuration());
			for (int i = 0; i < from.getJointCount(); i++) {
				stiffness[i] = fromAmount.multiply(from.getImpedanceSettings(i).getStiffness())
						.add(toAmount.multiply(to.getImpedanceSettings(i).getStiffness()));
				damping[i] = fromAmount.multiply(from.getImpedanceSettings(i).getDamping())
						.add(toAmount.multiply(to.getImpedanceSettings(i).getDamping()));
				addTorque[i] = fromAmount.multiply(from.getImpedanceSettings(i).getAddTorque())
						.add(toAmount.multiply(to.getImpedanceSettings(i).getAddTorque()));
				maxTorques[i] = Math.max(from.getImpedanceSettings(i).getMaxTorque(),
						to.getImpedanceSettings(i).getMaxTorque());
			}
			return new JointImpedanceControllerActionResult(action, time.greater(action.getDuration()).or(cancel),
					from.getJointCount(), stiffness, damping, addTorque, maxTorques);
		}
		return null;
	}

}
