/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.runtime.rpi.driver;

import org.roboticsapi.configuration.ConfigurationProperty;
import org.roboticsapi.core.Dependency;
import org.roboticsapi.core.exception.ConfigurationException;
import org.roboticsapi.facet.runtime.rpi.RpiParameters;

public final class LwrFriDriver extends LwrGenericDriver {

	private final Dependency<String> address;
	private final Dependency<Integer> port;
	private final Dependency<Integer> handshake;
	private final Dependency<Integer> communication;
	private final Dependency<Integer> digitalInCount;
	private final Dependency<Integer> digitalOutCount;
	private final Dependency<Integer> analogInCount;
	private final Dependency<Integer> analogOutCount;

	public LwrFriDriver() {
		address = createDependency("address");
		port = createDependency("port");
		handshake = createDependency("handshake", 15);
		communication = createDependency("communication", 14);
		digitalInCount = createDependency("digitalInCount", 8);
		digitalOutCount = createDependency("digitalOutCount", 8);
		analogInCount = createDependency("analogInCount, 8");
		analogOutCount = createDependency("analogOutCount", 8);
	}

	@Override
	public String getRpiDeviceType() {
		return "kuka_lwr_fri";
	}

	public String getAddress() {
		return address.get();
	}

	@ConfigurationProperty(Optional = true)
	public void setAddress(String address) {
		this.address.set(address);
	}

	public int getPort() {
		return port.get();
	}

	@ConfigurationProperty(Optional = false)
	public void setPort(int port) {
		this.port.set(port);
	}

	public int getHandshake() {
		return handshake.get();
	}

	@ConfigurationProperty(Optional = true)
	public void setHandshake(int handshake) {
		this.handshake.set(handshake);
	}

	public int getCommunication() {
		return communication.get();
	}

	@ConfigurationProperty(Optional = true)
	public void setCommunication(int communication) {
		this.communication.set(communication);
	}

	public int getNumDigitalInputs() {
		return digitalInCount.get();
	}

	@ConfigurationProperty(Optional = true)
	public void setNumDigitalInputs(int iportsdig) {
		this.digitalInCount.set(iportsdig);
	}

	public int getNumDigitalOutputs() {
		return digitalOutCount.get();
	}

	@ConfigurationProperty(Optional = true)
	public void setNumDigitalOutputs(int oportsdig) {
		this.digitalOutCount.set(oportsdig);
	}

	public int getNumAnalogInputs() {
		return analogInCount.get();
	}

	@ConfigurationProperty(Optional = true)
	public void setNumAnalogInputs(int iportsan) {
		this.analogInCount.set(iportsan);
	}

	public int getNumAnalogOutputs() {
		return analogOutCount.get();
	}

	@ConfigurationProperty(Optional = true)
	public void setNumAnalogOutputs(int oportsan) {
		this.analogOutCount.set(oportsan);
	}

	@Override
	protected void validateConfigurationProperties() throws ConfigurationException {
		super.validateConfigurationProperties();

		assertTrue("numDigitalInputs", getNumAnalogInputs() >= 0, "numDigitalInputs must be greater than zero");
		assertTrue("numDigitalInputs", getNumAnalogInputs() <= 8, "numDigitalInputs must be at most 8");

		assertTrue("numDigitalOutputs", getNumAnalogInputs() >= 0, "numDigitalOutputs must be greater than zero");
		assertTrue("numDigitalOutputs", getNumAnalogInputs() <= 8, "numDigitalOutputs must be at most 8");

		assertTrue("numAnalogInputs", getNumAnalogInputs() >= 0, "numAnalogInputs must be greater than zero");
		assertTrue("numAnalogInputs", getNumAnalogInputs() <= 8, "numAnalogInputs must be at most 8");

		assertTrue("numAnalogOutputs", getNumAnalogInputs() >= 0, "numAnalogOutputs must be greater than zero");
		assertTrue("numAnalogOutputs", getNumAnalogInputs() <= 8, "numAnalogOutputs must be at most 8");

	}

	@Override
	protected RpiParameters getRpiDeviceParameters() {
		// TODO Auto-generated method stub
		return super.getRpiDeviceParameters().with("ip", getAddress()).with("port", Integer.toString(getPort()))
				.with("handshake", Integer.toString(getHandshake()))
				.with("communication", Integer.toString(getCommunication()))
				.with("iportsdig", Integer.toString(getNumDigitalInputs()))
				.with("oportsdig", Integer.toString(getNumDigitalOutputs()))
				.with("iportsan", Integer.toString(getNumAnalogInputs()))
				.with("oportsan", Integer.toString(getNumAnalogOutputs()));
	}

}