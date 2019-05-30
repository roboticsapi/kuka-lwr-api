/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.driver.mapper;

import org.roboticsapi.kuka.lwr.runtime.driver.SoftRobotLWRDriver;
import org.roboticsapi.kuka.lwr.runtime.fragment.LWRKinematicsFragment;
import org.roboticsapi.runtime.mapping.MappingException;
import org.roboticsapi.runtime.robot.driver.mapper.SoftRobotRobotArmMapper;

public interface SoftRobotLWRMapper extends SoftRobotRobotArmMapper {

	public abstract LWRKinematicsFragment createKinematicsFragment(SoftRobotLWRDriver robot) throws MappingException;

}