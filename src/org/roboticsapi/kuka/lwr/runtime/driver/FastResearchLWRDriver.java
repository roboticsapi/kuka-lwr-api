/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.lwr.runtime.driver;

import java.util.Map;

import org.roboticsapi.configuration.ConfigurationProperty;
import org.roboticsapi.core.exception.ConfigurationException;

public final class FastResearchLWRDriver extends SoftRobotLWRDriver {

	private String address = null;
	private Integer port = null;
	private int handshake = 15, communication = 14;
	private int iportsdig = 8, oportsdig = 8, iportsan = 8, oportsan = 8;

	@Override
	public String getDeviceType() {
		return "kuka_lwr_fri";
	}

	public String getAddress() {
		return address;
	}

	@ConfigurationProperty(Optional = true)
	public void setAddress(String address) {
		immutableWhenInitialized();
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	@ConfigurationProperty(Optional = false)
	public void setPort(int port) {
		immutableWhenInitialized();
		this.port = port;
	}

	public int getHandshake() {
		return handshake;
	}

	@ConfigurationProperty(Optional = true)
	public void setHandshake(int handshake) {
		immutableWhenInitialized();
		this.handshake = handshake;
	}

	public int getCommunication() {
		return communication;
	}

	@ConfigurationProperty(Optional = true)
	public void setCommunication(int communication) {
		immutableWhenInitialized();
		this.communication = communication;
	}

	public int getNumDigitalInputs() {
		return iportsdig;
	}

	@ConfigurationProperty(Optional = true)
	public void setNumDigitalInputs(int iportsdig) {
		immutableWhenInitialized();
		this.iportsdig = iportsdig;
	}

	public int getNumDigitalOutputs() {
		return oportsdig;
	}

	@ConfigurationProperty(Optional = true)
	public void setNumDigitalOutputs(int oportsdig) {
		immutableWhenInitialized();
		this.oportsdig = oportsdig;
	}

	public int getNumAnalogInputs() {
		return iportsan;
	}

	@ConfigurationProperty(Optional = true)
	public void setNumAnalogInputs(int iportsan) {
		immutableWhenInitialized();
		this.iportsan = iportsan;
	}

	public int getNumAnalogOutputs() {
		return oportsan;
	}

	@ConfigurationProperty(Optional = true)
	public void setNumAnalogOutputs(int oportsan) {
		immutableWhenInitialized();
		this.oportsan = oportsan;
	}

	@Override
	protected void validateConfigurationProperties() throws ConfigurationException {
		super.validateConfigurationProperties();

		checkNotNull("address", address);
		checkNotNull("port", port);

		checkInClosedInterval("numDigitalInputs", iportsdig, 0, 8);
		checkInClosedInterval("numDigitalOutputs", oportsdig, 0, 8);
		checkInClosedInterval("numAnalogInputs", iportsan, 0, 8);
		checkInClosedInterval("numAnalogOutputs", oportsan, 0, 8);
	}

	@Override
	protected void collectDriverSpecificParameters(Map<String, String> parameters) {
		parameters.put("ip", address);
		parameters.put("port", toString(port));
		parameters.put("handshake", toString(handshake));
		parameters.put("communication", toString(communication));

		parameters.put("iportsdig", toString(iportsdig));
		parameters.put("oportsdig", toString(oportsdig));
		parameters.put("iportsan", toString(iportsan));
		parameters.put("oportsan", toString(oportsan));
	}

}