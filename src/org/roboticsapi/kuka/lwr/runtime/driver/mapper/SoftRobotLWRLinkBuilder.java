/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.driver.mapper;

import org.roboticsapi.kuka.lwr.runtime.driver.SoftRobotLWRDriver;
import org.roboticsapi.kuka.lwr.runtime.fragment.LWRKinematicsFragment;
import org.roboticsapi.runtime.mapping.LinkBuilder;
import org.roboticsapi.runtime.mapping.LinkBuilderResult;
import org.roboticsapi.runtime.mapping.MappingException;
import org.roboticsapi.runtime.mapping.net.DataflowType;
import org.roboticsapi.runtime.mapping.net.NetFragment;
import org.roboticsapi.runtime.robot.AlphaDataflow;

public class SoftRobotLWRLinkBuilder implements LinkBuilder {
	private final SoftRobotLWRDriver driver;
	private final SoftRobotLWRMapper mapper;

	public SoftRobotLWRLinkBuilder(SoftRobotLWRDriver driver, SoftRobotLWRMapper mapper) {
		this.driver = driver;
		this.mapper = mapper;
	}

	@Override
	public LinkBuilderResult buildLink(final DataflowType from, final DataflowType to) {
		if (from == null && to != null && to instanceof AlphaDataflow) {
			try {
				NetFragment ret = new NetFragment("Alpha monitor");
				LWRKinematicsFragment kin = ret.add(mapper.createKinematicsFragment(driver));
				ret.connect(null, kin.getInJoints());
				return new LinkBuilderResult(ret, null, kin.getAlphaOutPort());
			} catch (MappingException e) {
				return null;
			}
		}
		return null;
	}
}