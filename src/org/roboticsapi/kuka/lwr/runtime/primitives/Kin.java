package org.roboticsapi.kuka.lwr.runtime.primitives;

import org.roboticsapi.runtime.core.types.RPIstring;
import org.roboticsapi.runtime.rpi.InPort;
import org.roboticsapi.runtime.rpi.OutPort;
import org.roboticsapi.runtime.rpi.Parameter;
import org.roboticsapi.runtime.rpi.Primitive;

/**
 * Direct kinematics module for LWR robots
 */
public class Kin extends Primitive {
	/** Type name of the primitive */
	public static final String PRIMITIVE_TYPE = "KUKA::LWR::Kin";

	/** Activation port */
	private final InPort inActive = new InPort("inActive");

	/** Value of Axis 1 */
	private final InPort inJ1 = new InPort("inJ1");

	/** Value of Axis 2 */
	private final InPort inJ2 = new InPort("inJ2");

	/** Value of Axis 3 */
	private final InPort inJ3 = new InPort("inJ3");

	/** Value of Axis 4 */
	private final InPort inJ4 = new InPort("inJ4");

	/** Value of Axis 5 */
	private final InPort inJ5 = new InPort("inJ5");

	/** Value of Axis 6 */
	private final InPort inJ6 = new InPort("inJ6");

	/** Value of Axis 7 */
	private final InPort inJ7 = new InPort("inJ7");

	/** Resulting alpha value */
	private final OutPort outAlpha = new OutPort("outAlpha");

	/** Resulting frame */
	private final OutPort outFrame = new OutPort("outFrame");

	/** Name of the LBR */
	private final Parameter<RPIstring> paramRobot = new Parameter<RPIstring>("Robot", new RPIstring(""));

	public Kin() {
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
		add(outAlpha);
		add(outFrame);

		// Add all parameters
		add(paramRobot);
	}

	/**
	 * Creates direct kinematics module for LWR robots
	 *
	 * @param robot Name of the LBR
	 */
	public Kin(RPIstring paramRobot) {
		this();

		// Set the parameters
		setRobot(paramRobot);
	}

	/**
	 * Creates direct kinematics module for LWR robots
	 *
	 * @param robot Name of the LBR
	 */
	public Kin(String paramRobot) {
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
	 * Value of Axis 1
	 *
	 * @return the input port of the block
	 */
	public final InPort getInJ1() {
		return this.inJ1;
	}

	/**
	 * Value of Axis 2
	 *
	 * @return the input port of the block
	 */
	public final InPort getInJ2() {
		return this.inJ2;
	}

	/**
	 * Value of Axis 3
	 *
	 * @return the input port of the block
	 */
	public final InPort getInJ3() {
		return this.inJ3;
	}

	/**
	 * Value of Axis 4
	 *
	 * @return the input port of the block
	 */
	public final InPort getInJ4() {
		return this.inJ4;
	}

	/**
	 * Value of Axis 5
	 *
	 * @return the input port of the block
	 */
	public final InPort getInJ5() {
		return this.inJ5;
	}

	/**
	 * Value of Axis 6
	 *
	 * @return the input port of the block
	 */
	public final InPort getInJ6() {
		return this.inJ6;
	}

	/**
	 * Value of Axis 7
	 *
	 * @return the input port of the block
	 */
	public final InPort getInJ7() {
		return this.inJ7;
	}

	/**
	 * Resulting alpha value
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutAlpha() {
		return this.outAlpha;
	}

	/**
	 * Resulting frame
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutFrame() {
		return this.outFrame;
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