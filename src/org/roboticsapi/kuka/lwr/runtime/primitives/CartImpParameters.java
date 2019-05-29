package org.roboticsapi.kuka.lwr.runtime.primitives;

import org.roboticsapi.runtime.core.types.RPIstring;
import org.roboticsapi.runtime.rpi.InPort;
import org.roboticsapi.runtime.rpi.Parameter;
import org.roboticsapi.runtime.rpi.Primitive;

/**
 * Module for changing Cartesian impedance values (stiffness etc.)
 */
public class CartImpParameters extends Primitive {
	/** Type name of the primitive */
	public static final String PRIMITIVE_TYPE = "KUKA::LWR::CartImpParameters";

	/** Activation port */
	private final InPort inActive = new InPort("inActive");

	/** Additional torque for selected axis */
	private final InPort inAddTorque = new InPort("inAddTorque");

	/** Damping value for selected axis */
	private final InPort inDamping = new InPort("inDamping");

	/** Stiffness value for selected axis */
	private final InPort inStiffness = new InPort("inStiffness");

	/** Name of the LBR */
	private final Parameter<RPIstring> paramRobot = new Parameter<RPIstring>("Robot", new RPIstring(""));

	public CartImpParameters() {
		super(PRIMITIVE_TYPE);

		// Add all ports
		add(inActive);
		add(inAddTorque);
		add(inDamping);
		add(inStiffness);

		// Add all parameters
		add(paramRobot);
	}

	/**
	 * Creates module for changing Cartesian impedance values (stiffness etc.)
	 * 
	 * @param robot Name of the LBR
	 */
	public CartImpParameters(RPIstring paramRobot) {
		this();

		// Set the parameters
		setRobot(paramRobot);
	}

	/**
	 * Creates module for changing Cartesian impedance values (stiffness etc.)
	 * 
	 * @param robot Name of the LBR
	 */
	public CartImpParameters(String paramRobot) {
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
	 * Additional torque for selected axis
	 * 
	 * @return the input port of the block
	 */
	public final InPort getInAddTorque() {
		return this.inAddTorque;
	}

	/**
	 * Damping value for selected axis
	 * 
	 * @return the input port of the block
	 */
	public final InPort getInDamping() {
		return this.inDamping;
	}

	/**
	 * Stiffness value for selected axis
	 * 
	 * @return the input port of the block
	 */
	public final InPort getInStiffness() {
		return this.inStiffness;
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
