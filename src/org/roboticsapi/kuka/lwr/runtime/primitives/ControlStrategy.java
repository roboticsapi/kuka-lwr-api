package org.roboticsapi.kuka.lwr.runtime.primitives;

import org.roboticsapi.runtime.core.types.RPIstring;
import org.roboticsapi.runtime.rpi.InPort;
import org.roboticsapi.runtime.rpi.OutPort;
import org.roboticsapi.runtime.rpi.Parameter;
import org.roboticsapi.runtime.rpi.Primitive;

/**
 * Module for switching the control mode of the LWR robot
 */
public class ControlStrategy extends Primitive {
	/** Type name of the primitive */
	public static final String PRIMITIVE_TYPE = "KUKA::LWR::ControlStrategy";

	/** Activation port */
	private final InPort inActive = new InPort("inActive");

	/** The required control strategy */
	private final InPort inStrategy = new InPort("inStrategy");

	/** true, when the switching action has completed */
	private final OutPort outCompleted = new OutPort("outCompleted");

	/** Result (nonzero means error) */
	private final OutPort outError = new OutPort("outError");

	/** Name of the LBR */
	private final Parameter<RPIstring> paramRobot = new Parameter<RPIstring>("Robot", new RPIstring(""));

	public ControlStrategy() {
		super(PRIMITIVE_TYPE);

		// Add all ports
		add(inActive);
		add(inStrategy);
		add(outCompleted);
		add(outError);

		// Add all parameters
		add(paramRobot);
	}

	/**
	 * Creates module for switching the control mode of the LWR robot
	 * 
	 * @param robot Name of the LBR
	 */
	public ControlStrategy(RPIstring paramRobot) {
		this();

		// Set the parameters
		setRobot(paramRobot);
	}

	/**
	 * Creates module for switching the control mode of the LWR robot
	 * 
	 * @param robot Name of the LBR
	 */
	public ControlStrategy(String paramRobot) {
		this(new RPIstring(paramRobot));
	}

	/**
	 * Activation port
	 * 
	 * @return the input port of the block
	 */
	public final InPort getInActive() {
		return this.inActive;
	}

	/**
	 * The required control strategy
	 * 
	 * @return the input port of the block
	 */
	public final InPort getInStrategy() {
		return this.inStrategy;
	}

	/**
	 * true, when the switching action has completed
	 * 
	 * @return the output port of the block
	 */
	public final OutPort getOutCompleted() {
		return this.outCompleted;
	}

	/**
	 * Result (nonzero means error)
	 * 
	 * @return the output port of the block
	 */
	public final OutPort getOutError() {
		return this.outError;
	}

	/**
	 * Name of the LBR
	 * 
	 * @return the parameter of the block
	 */
	public final Parameter<RPIstring> getRobot() {
		return this.paramRobot;
	}

	/**
	 * Sets a parameter of the block: Name of the LBR
	 * 
	 * @param value new value of the parameter
	 */
	public final void setRobot(RPIstring value) {
		this.paramRobot.setValue(value);
	}

	/**
	 * Sets a parameter of the block: Name of the LBR
	 * 
	 * @param value new value of the parameter
	 */
	public final void setRobot(String value) {
		this.setRobot(new RPIstring(value));
	}

}
