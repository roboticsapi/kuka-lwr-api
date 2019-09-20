package org.roboticsapi.device.kuka.lwr.runtime.rpi.primitives;

import org.roboticsapi.facet.runtime.rpi.InPort;
import org.roboticsapi.facet.runtime.rpi.Parameter;
import org.roboticsapi.facet.runtime.rpi.Primitive;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIint;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIstring;

/**
 * Module for changing joint impedance control values (stiffness etc.)
 */
public class JointImpParameters extends Primitive {
	/** Type name of the primitive */
	public static final String PRIMITIVE_TYPE = "KUKA::LWR::JointImpParameters";

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

	/** Number of axis to control (0-based) */
	private final Parameter<RPIint> paramAxis = new Parameter<RPIint>("Axis", new RPIint("0"));

	public JointImpParameters() {
		super(PRIMITIVE_TYPE);

		// Add all ports
		add(inActive);
		add(inAddTorque);
		add(inDamping);
		add(inStiffness);

		// Add all parameters
		add(paramRobot);
		add(paramAxis);
	}

	/**
	 * Creates module for changing joint impedance control values (stiffness etc.)
	 *
	 * @param robot Name of the LBR
	 * @param axis  Number of axis to control (0-based)
	 */
	public JointImpParameters(RPIstring paramRobot, RPIint paramAxis) {
		this();

		// Set the parameters
		setRobot(paramRobot);
		setAxis(paramAxis);
	}

	/**
	 * Creates module for changing joint impedance control values (stiffness etc.)
	 *
	 * @param robot Name of the LBR
	 * @param axis  Number of axis to control (0-based)
	 */
	public JointImpParameters(String paramRobot, Integer paramAxis) {
		this(new RPIstring(paramRobot), new RPIint(paramAxis));
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

	/**
	 * Number of axis to control (0-based)
	 *
	 * @return the parameter of the block
	 */
	public final Parameter<RPIint> getAxis() {
		return this.paramAxis;
	}

	/**
	 * Sets a parameter of the block: Number of axis to control (0-based)
	 *
	 * @param value new value of the parameter
	 */
	public final void setAxis(RPIint value) {
		this.paramAxis.setValue(value);
	}

	/**
	 * Sets a parameter of the block: Number of axis to control (0-based)
	 *
	 * @param value new value of the parameter
	 */
	public final void setAxis(Integer value) {
		this.setAxis(new RPIint(value));
	}

}
