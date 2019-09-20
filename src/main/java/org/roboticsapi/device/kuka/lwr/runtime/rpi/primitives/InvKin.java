package org.roboticsapi.device.kuka.lwr.runtime.rpi.primitives;

import org.roboticsapi.facet.runtime.rpi.InPort;
import org.roboticsapi.facet.runtime.rpi.OutPort;
import org.roboticsapi.facet.runtime.rpi.Parameter;
import org.roboticsapi.facet.runtime.rpi.Primitive;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIint;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIstring;

/**
 * Inverse kinematics module for LBR robots
 */
public class InvKin extends Primitive {
	/** Type name of the primitive */
	public static final String PRIMITIVE_TYPE = "KUKA::LWR::InvKin";

	/** Activation port */
	private final InPort inActive = new InPort("inActive");

	/** Destination frame */
	private final InPort inFrame = new InPort("inFrame");

	/** Hint joint value 1 */
	private final InPort inHintJ1 = new InPort("inHintJ1");

	/** Hint joint value 2 */
	private final InPort inHintJ2 = new InPort("inHintJ2");

	/** Hint joint value 3 */
	private final InPort inHintJ3 = new InPort("inHintJ3");

	/** Hint joint value 4 */
	private final InPort inHintJ4 = new InPort("inHintJ4");

	/** Hint joint value 5 */
	private final InPort inHintJ5 = new InPort("inHintJ5");

	/** Hint joint value 6 */
	private final InPort inHintJ6 = new InPort("inHintJ6");

	/** Hint joint value 7 */
	private final InPort inHintJ7 = new InPort("inHintJ7");

	/** Nullspace joint value 1 */
	private final InPort inNullspaceJ1 = new InPort("inNullspaceJ1");

	/** Nullspace joint value 2 */
	private final InPort inNullspaceJ2 = new InPort("inNullspaceJ2");

	/** Nullspace joint value 3 */
	private final InPort inNullspaceJ3 = new InPort("inNullspaceJ3");

	/** Nullspace joint value 4 */
	private final InPort inNullspaceJ4 = new InPort("inNullspaceJ4");

	/** Nullspace joint value 5 */
	private final InPort inNullspaceJ5 = new InPort("inNullspaceJ5");

	/** Nullspace joint value 6 */
	private final InPort inNullspaceJ6 = new InPort("inNullspaceJ6");

	/** Nullspace joint value 7 */
	private final InPort inNullspaceJ7 = new InPort("inNullspaceJ7");

	/** Result angle for joint 1 */
	private final OutPort outJ1 = new OutPort("outJ1");

	/** Result angle for joint 2 */
	private final OutPort outJ2 = new OutPort("outJ2");

	/** Result angle for joint 3 */
	private final OutPort outJ3 = new OutPort("outJ3");

	/** Result angle for joint 4 */
	private final OutPort outJ4 = new OutPort("outJ4");

	/** Result angle for joint 5 */
	private final OutPort outJ5 = new OutPort("outJ5");

	/** Result angle for joint 6 */
	private final OutPort outJ6 = new OutPort("outJ6");

	/** Result angle for joint 7 */
	private final OutPort outJ7 = new OutPort("outJ7");

	/** Redundancy strategy */
	private final Parameter<RPIint> paramStrategy = new Parameter<RPIint>("Strategy", new RPIint("1"));

	/** Name of the LBR */
	private final Parameter<RPIstring> paramRobot = new Parameter<RPIstring>("Robot", new RPIstring(""));

	public InvKin() {
		super(PRIMITIVE_TYPE);

		// Add all ports
		add(inActive);
		add(inFrame);
		add(inHintJ1);
		add(inHintJ2);
		add(inHintJ3);
		add(inHintJ4);
		add(inHintJ5);
		add(inHintJ6);
		add(inHintJ7);
		add(inNullspaceJ1);
		add(inNullspaceJ2);
		add(inNullspaceJ3);
		add(inNullspaceJ4);
		add(inNullspaceJ5);
		add(inNullspaceJ6);
		add(inNullspaceJ7);
		add(outJ1);
		add(outJ2);
		add(outJ3);
		add(outJ4);
		add(outJ5);
		add(outJ6);
		add(outJ7);

		// Add all parameters
		add(paramStrategy);
		add(paramRobot);
	}

	/**
	 * Creates inverse kinematics module for LBR robots
	 *
	 * @param strategy Redundancy strategy
	 * @param robot    Name of the LBR
	 */
	public InvKin(RPIint paramStrategy, RPIstring paramRobot) {
		this();

		// Set the parameters
		setStrategy(paramStrategy);
		setRobot(paramRobot);
	}

	/**
	 * Creates inverse kinematics module for LBR robots
	 *
	 * @param strategy Redundancy strategy
	 * @param robot    Name of the LBR
	 */
	public InvKin(Integer paramStrategy, String paramRobot) {
		this(new RPIint(paramStrategy), new RPIstring(paramRobot));
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
	 * Destination frame
	 *
	 * @return the input port of the block
	 */
	public final InPort getInFrame() {
		return this.inFrame;
	}

	/**
	 * Hint joint value 1
	 *
	 * @return the input port of the block
	 */
	public final InPort getInHintJ1() {
		return this.inHintJ1;
	}

	/**
	 * Hint joint value 2
	 *
	 * @return the input port of the block
	 */
	public final InPort getInHintJ2() {
		return this.inHintJ2;
	}

	/**
	 * Hint joint value 3
	 *
	 * @return the input port of the block
	 */
	public final InPort getInHintJ3() {
		return this.inHintJ3;
	}

	/**
	 * Hint joint value 4
	 *
	 * @return the input port of the block
	 */
	public final InPort getInHintJ4() {
		return this.inHintJ4;
	}

	/**
	 * Hint joint value 5
	 *
	 * @return the input port of the block
	 */
	public final InPort getInHintJ5() {
		return this.inHintJ5;
	}

	/**
	 * Hint joint value 6
	 *
	 * @return the input port of the block
	 */
	public final InPort getInHintJ6() {
		return this.inHintJ6;
	}

	/**
	 * Hint joint value 7
	 *
	 * @return the input port of the block
	 */
	public final InPort getInHintJ7() {
		return this.inHintJ7;
	}

	/**
	 * Nullspace joint value 1
	 *
	 * @return the input port of the block
	 */
	public final InPort getInNullspaceJ1() {
		return this.inNullspaceJ1;
	}

	/**
	 * Nullspace joint value 2
	 *
	 * @return the input port of the block
	 */
	public final InPort getInNullspaceJ2() {
		return this.inNullspaceJ2;
	}

	/**
	 * Nullspace joint value 3
	 *
	 * @return the input port of the block
	 */
	public final InPort getInNullspaceJ3() {
		return this.inNullspaceJ3;
	}

	/**
	 * Nullspace joint value 4
	 *
	 * @return the input port of the block
	 */
	public final InPort getInNullspaceJ4() {
		return this.inNullspaceJ4;
	}

	/**
	 * Nullspace joint value 5
	 *
	 * @return the input port of the block
	 */
	public final InPort getInNullspaceJ5() {
		return this.inNullspaceJ5;
	}

	/**
	 * Nullspace joint value 6
	 *
	 * @return the input port of the block
	 */
	public final InPort getInNullspaceJ6() {
		return this.inNullspaceJ6;
	}

	/**
	 * Nullspace joint value 7
	 *
	 * @return the input port of the block
	 */
	public final InPort getInNullspaceJ7() {
		return this.inNullspaceJ7;
	}

	/**
	 * Result angle for joint 1
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutJ1() {
		return this.outJ1;
	}

	/**
	 * Result angle for joint 2
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutJ2() {
		return this.outJ2;
	}

	/**
	 * Result angle for joint 3
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutJ3() {
		return this.outJ3;
	}

	/**
	 * Result angle for joint 4
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutJ4() {
		return this.outJ4;
	}

	/**
	 * Result angle for joint 5
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutJ5() {
		return this.outJ5;
	}

	/**
	 * Result angle for joint 6
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutJ6() {
		return this.outJ6;
	}

	/**
	 * Result angle for joint 7
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutJ7() {
		return this.outJ7;
	}

	/**
	 * Redundancy strategy
	 *
	 * @return the parameter of the block
	 */
	public final Parameter<RPIint> getStrategy() {
		return this.paramStrategy;
	}

	/**
	 * Sets a parameter of the block: Redundancy strategy
	 *
	 * @param value new value of the parameter
	 */
	public final void setStrategy(RPIint value) {
		this.paramStrategy.setValue(value);
	}

	/**
	 * Sets a parameter of the block: Redundancy strategy
	 *
	 * @param value new value of the parameter
	 */
	public final void setStrategy(Integer value) {
		this.setStrategy(new RPIint(value));
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