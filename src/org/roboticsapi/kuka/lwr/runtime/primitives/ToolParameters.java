package org.roboticsapi.kuka.lwr.runtime.primitives;

import org.roboticsapi.runtime.core.types.RPIstring;
import org.roboticsapi.runtime.rpi.InPort;
import org.roboticsapi.runtime.rpi.OutPort;
import org.roboticsapi.runtime.rpi.Parameter;
import org.roboticsapi.runtime.rpi.Primitive;

/**
 * Module for configuring the tool of LWR robot
 */
public class ToolParameters extends Primitive {
	/** Type name of the primitive */
	public static final String PRIMITIVE_TYPE = "KUKA::LWR::ToolParameters";

	/** Activation port */
	private final InPort inActive = new InPort("inActive");

	/** Center of mass */
	private final InPort inCOM = new InPort("inCOM");

	/** Moment of inertia */
	private final InPort inMOI = new InPort("inMOI");

	/** Mass of load (in kg) */
	private final InPort inMass = new InPort("inMass");

	/** tool center point and orientation */
	private final InPort inTCP = new InPort("inTCP");

	/** true, when the switching action has completed */
	private final OutPort outCompleted = new OutPort("outCompleted");

	/** Result (nonzero means error) */
	private final OutPort outError = new OutPort("outError");

	/** Name of the LBR */
	private final Parameter<RPIstring> paramRobot = new Parameter<RPIstring>("robot", new RPIstring(""));

	public ToolParameters() {
		super(PRIMITIVE_TYPE);

		// Add all ports
		add(inActive);
		add(inCOM);
		add(inMOI);
		add(inMass);
		add(inTCP);
		add(outCompleted);
		add(outError);

		// Add all parameters
		add(paramRobot);
	}

	/**
	 * Creates module for configuring the tool of LWR robot
	 * 
	 * @param robot Name of the LBR
	 */
	public ToolParameters(RPIstring paramRobot) {
		this();

		// Set the parameters
		setrobot(paramRobot);
	}

	/**
	 * Creates module for configuring the tool of LWR robot
	 * 
	 * @param robot Name of the LBR
	 */
	public ToolParameters(String paramRobot) {
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
	 * Center of mass
	 * 
	 * @return the input port of the block
	 */
	public final InPort getInCOM() {
		return this.inCOM;
	}

	/**
	 * Moment of inertia
	 * 
	 * @return the input port of the block
	 */
	public final InPort getInMOI() {
		return this.inMOI;
	}

	/**
	 * Mass of load (in kg)
	 * 
	 * @return the input port of the block
	 */
	public final InPort getInMass() {
		return this.inMass;
	}

	/**
	 * tool center point and orientation
	 * 
	 * @return the input port of the block
	 */
	public final InPort getInTCP() {
		return this.inTCP;
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
	public final Parameter<RPIstring> getrobot() {
		return this.paramRobot;
	}

	/**
	 * Sets a parameter of the block: Name of the LBR
	 * 
	 * @param value new value of the parameter
	 */
	public final void setrobot(RPIstring value) {
		this.paramRobot.setValue(value);
	}

	/**
	 * Sets a parameter of the block: Name of the LBR
	 * 
	 * @param value new value of the parameter
	 */
	public final void setrobot(String value) {
		this.setrobot(new RPIstring(value));
	}

}
