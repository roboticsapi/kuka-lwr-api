/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.kr.systemtest;

import org.junit.runner.RunWith;
import org.roboticsapi.device.kuka.lwr.Lwr4;
import org.roboticsapi.device.kuka.lwr.LwrIiwa14;
import org.roboticsapi.device.kuka.lwr.LwrIiwa7;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.driver.LwrIiwaMockDriver;
import org.roboticsapi.device.kuka.lwr.runtime.rpi.driver.LwrMockDriver;
import org.roboticsapi.framework.multijoint.activity.JointPtpInterfaceTests;
import org.roboticsapi.systemtest.RoboticsTestSuite;
import org.roboticsapi.systemtest.RoboticsTestSuite.DeviceInterfaceTests;
import org.roboticsapi.systemtest.WithDevice;
import org.roboticsapi.systemtest.WithRcc;
import org.roboticsapi.systemtest.WithRcc.Rcc;

@RunWith(RoboticsTestSuite.class)
@DeviceInterfaceTests({ JointPtpInterfaceTests.class })
@WithDevice(device = Lwr4.class, deviceDrivers = { LwrMockDriver.class })
@WithDevice(device = LwrIiwa7.class, deviceDrivers = { LwrIiwaMockDriver.class })
@WithDevice(device = LwrIiwa14.class, deviceDrivers = { LwrIiwaMockDriver.class })
@WithRcc(Rcc.DedicatedJavaRcc)
public class LwrSystemTest {

}
