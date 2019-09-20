package org.roboticsapi.device.kuka.lwr.runtime.rpi.primitives;

import org.roboticsapi.facet.runtime.rpi.InPort;
import org.roboticsapi.facet.runtime.rpi.OutPort;
import org.roboticsapi.facet.runtime.rpi.Parameter;
import org.roboticsapi.facet.runtime.rpi.Primitive;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIstring;

/**
 * Direct velocity kinematics module for LWR robots
 */
public class VelKin extends Primitive {
	/** Type name of the primitive */
	public static final String PRIMITIVE_TYPE = "KUKA::LWR::VelKin";

	/** Activation port */
	private final InPort inActive = new InPort("inActive");

	/** Position of Axis 1 */
	private final InPort inJ1 = new InPort("inJ1");

	/** Position of Axis 2 */
	private final InPort inJ2 = new InPort("inJ2");

	/** Position of Axis 3 */
	private final InPort inJ3 = new InPort("inJ3");

	/** Position of Axis 4 */
	private final InPort inJ4 = new InPort("inJ4");

	/** Position of Axis 5 */
	private final InPort inJ5 = new InPort("inJ5");

	/** Position of Axis 6 */
	private final InPort inJ6 = new InPort("inJ6");

	/** Position of Axis 7 */
	private final InPort inJ7 = new InPort("inJ7");

	/** Velocity of Axis 1 */
	private final InPort inV1 = new InPort("inV1");

	/** Velocity of Axis 2 */
	private final InPort inV2 = new InPort("inV2");

	/** Velocity of Axis 3 */
	private final InPort inV3 = new InPort("inV3");

	/** Velocity of Axis 4 */
	private final InPort inV4 = new InPort("inV4");

	/** Velocity of Axis 5 */
	private final InPort inV5 = new InPort("inV5");

	/** Velocity of Axis 6 */
	private final InPort inV6 = new InPort("inV6");

	/** Velocity of Axis 7 */
	private final InPort inV7 = new InPort("inV7");

	/** Resulting Twist */
	private final OutPort outTwist = new OutPort("outTwist");

	/** Resulting alpha velocity */
	private final OutPort outVAlpha = new OutPort("outVAlpha");

	/** Name of the LBR */
	private final Parameter<RPIstring> paramRobot = new Parameter<RPIstring>("Robot", new RPIstring(""));

	public VelKin() {
		super(PRIMITIVE_TYPE);

		// Add all ports
		add(inActive);
		add(inJ1);
		add(inJ2);
		add(inJ3);
		add(inJ4);
		add(inJ5);
		add(inJ6);
		add(inJ7);
		add(inV1);
		add(inV2);
		add(inV3);
		add(inV4);
		add(inV5);
		add(inV6);
		add(inV7);
		add(outTwist);
		add(outVAlpha);

		// Add all parameters
		add(paramRobot);
	}

	/**
	 * Creates direct velocity kinematics module for LWR robots
	 *
	 * @param robot Name of the LBR
	 */
	public VelKin(RPIstring paramRobot) {
		this();

		// Set the parameters
		setRobot(paramRobot);
	}

	/**
	 * Creates direct velocity kinematics module for LWR robots
	 *
	 * @param robot Name of the LBR
	 */
	public VelKin(String paramRobot) {
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
	 * Position of Axis 1
	 *
	 * @return the input port of the block
	 */
	public final InPort getInJ1() {
		return this.inJ1;
	}

	/**
	 * Position of Axis 2
	 *
	 * @return the input port of the block
	 */
	public final InPort getInJ2() {
		return this.inJ2;
	}

	/**
	 * Position of Axis 3
	 *
	 * @return the input port of the block
	 */
	public final InPort getInJ3() {
		return this.inJ3;
	}

	/**
	 * Position of Axis 4
	 *
	 * @return the input port of the block
	 */
	public final InPort getInJ4() {
		return this.inJ4;
	}

	/**
	 * Position of Axis 5
	 *
	 * @return the input port of the block
	 */
	public final InPort getInJ5() {
		return this.inJ5;
	}

	/**
	 * Position of Axis 6
	 *
	 * @return the input port of the block
	 */
	public final InPort getInJ6() {
		return this.inJ6;
	}

	/**
	 * Position of Axis 7
	 *
	 * @return the input port of the block
	 */
	public final InPort getInJ7() {
		return this.inJ7;
	}

	/**
	 * Velocity of Axis 1
	 *
	 * @return the input port of the block
	 */
	public final InPort getInV1() {
		return this.inV1;
	}

	/**
	 * Velocity of Axis 2
	 *
	 * @return the input port of the block
	 */
	public final InPort getInV2() {
		return this.inV2;
	}

	/**
	 * Velocity of Axis 3
	 *
	 * @return the input port of the block
	 */
	public final InPort getInV3() {
		return this.inV3;
	}

	/**
	 * Velocity of Axis 4
	 *
	 * @return the input port of the block
	 */
	public final InPort getInV4() {
		return this.inV4;
	}

	/**
	 * Velocity of Axis 5
	 *
	 * @return the input port of the block
	 */
	public final InPort getInV5() {
		return this.inV5;
	}

	/**
	 * Velocity of Axis 6
	 *
	 * @return the input port of the block
	 */
	public final InPort getInV6() {
		return this.inV6;
	}

	/**
	 * Velocity of Axis 7
	 *
	 * @return the input port of the block
	 */
	public final InPort getInV7() {
		return this.inV7;
	}

	/**
	 * Resulting Twist
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutTwist() {
		return this.outTwist;
	}

	/**
	 * Resulting alpha velocity
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutVAlpha() {
		return this.outVAlpha;
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