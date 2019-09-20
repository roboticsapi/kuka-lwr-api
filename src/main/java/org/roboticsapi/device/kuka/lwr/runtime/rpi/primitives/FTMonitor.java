package org.roboticsapi.device.kuka.lwr.runtime.rpi.primitives;

import org.roboticsapi.facet.runtime.rpi.OutPort;
import org.roboticsapi.facet.runtime.rpi.Parameter;
import org.roboticsapi.facet.runtime.rpi.Primitive;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIstring;

/**
 * Force/Torque Sensor for LBR robot
 */
public class FTMonitor extends Primitive {
	/** Type name of the primitive */
	public static final String PRIMITIVE_TYPE = "KUKA::LWR::FTMonitor";

	/** Estimated torque of axis 1 */
	private final OutPort outEstJ1 = new OutPort("outEstJ1");

	/** Estimated torque of axis 2 */
	private final OutPort outEstJ2 = new OutPort("outEstJ2");

	/** Estimated torque of axis 3 */
	private final OutPort outEstJ3 = new OutPort("outEstJ3");

	/** Estimated torque of axis 4 */
	private final OutPort outEstJ4 = new OutPort("outEstJ4");

	/** Estimated torque of axis 5 */
	private final OutPort outEstJ5 = new OutPort("outEstJ5");

	/** Estimated torque of axis 6 */
	private final OutPort outEstJ6 = new OutPort("outEstJ6");

	/** Estimated torque of axis 7 */
	private final OutPort outEstJ7 = new OutPort("outEstJ7");

	/** Measured torque of axis 1 */
	private final OutPort outMsrJ1 = new OutPort("outMsrJ1");

	/** Measured torque of axis 2 */
	private final OutPort outMsrJ2 = new OutPort("outMsrJ2");

	/** Measured torque of axis 3 */
	private final OutPort outMsrJ3 = new OutPort("outMsrJ3");

	/** Measured torque of axis 4 */
	private final OutPort outMsrJ4 = new OutPort("outMsrJ4");

	/** Measured torque of axis 5 */
	private final OutPort outMsrJ5 = new OutPort("outMsrJ5");

	/** Measured torque of axis 6 */
	private final OutPort outMsrJ6 = new OutPort("outMsrJ6");

	/** Measured torque of axis 7 */
	private final OutPort outMsrJ7 = new OutPort("outMsrJ7");

	/** Estimated force in X direction */
	private final OutPort outTcpFx = new OutPort("outTcpFx");

	/** Estimated force in Y direction */
	private final OutPort outTcpFy = new OutPort("outTcpFy");

	/** Estimated force in Z direction */
	private final OutPort outTcpFz = new OutPort("outTcpFz");

	/** Estimated torque around X direction */
	private final OutPort outTcpTx = new OutPort("outTcpTx");

	/** Estimated torque around Y direction */
	private final OutPort outTcpTy = new OutPort("outTcpTy");

	/** Estimated torque around Z direction */
	private final OutPort outTcpTz = new OutPort("outTcpTz");

	/** Name of the LBR */
	private final Parameter<RPIstring> paramRobot = new Parameter<RPIstring>("Robot", new RPIstring(""));

	public FTMonitor() {
		super(PRIMITIVE_TYPE);

		// Add all ports
		add(outEstJ1);
		add(outEstJ2);
		add(outEstJ3);
		add(outEstJ4);
		add(outEstJ5);
		add(outEstJ6);
		add(outEstJ7);
		add(outMsrJ1);
		add(outMsrJ2);
		add(outMsrJ3);
		add(outMsrJ4);
		add(outMsrJ5);
		add(outMsrJ6);
		add(outMsrJ7);
		add(outTcpFx);
		add(outTcpFy);
		add(outTcpFz);
		add(outTcpTx);
		add(outTcpTy);
		add(outTcpTz);

		// Add all parameters
		add(paramRobot);
	}

	/**
	 * Creates force/Torque Sensor for LBR robot
	 *
	 * @param robot Name of the LBR
	 */
	public FTMonitor(RPIstring paramRobot) {
		this();

		// Set the parameters
		setRobot(paramRobot);
	}

	/**
	 * Creates force/Torque Sensor for LBR robot
	 *
	 * @param robot Name of the LBR
	 */
	public FTMonitor(String paramRobot) {
		this(new RPIstring(paramRobot));
	}

	/**
	 * Estimated torque of axis 1
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutEstJ1() {
		return this.outEstJ1;
	}

	/**
	 * Estimated torque of axis 2
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutEstJ2() {
		return this.outEstJ2;
	}

	/**
	 * Estimated torque of axis 3
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutEstJ3() {
		return this.outEstJ3;
	}

	/**
	 * Estimated torque of axis 4
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutEstJ4() {
		return this.outEstJ4;
	}

	/**
	 * Estimated torque of axis 5
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutEstJ5() {
		return this.outEstJ5;
	}

	/**
	 * Estimated torque of axis 6
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutEstJ6() {
		return this.outEstJ6;
	}

	/**
	 * Estimated torque of axis 7
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutEstJ7() {
		return this.outEstJ7;
	}

	/**
	 * Measured torque of axis 1
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutMsrJ1() {
		return this.outMsrJ1;
	}

	/**
	 * Measured torque of axis 2
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutMsrJ2() {
		return this.outMsrJ2;
	}

	/**
	 * Measured torque of axis 3
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutMsrJ3() {
		return this.outMsrJ3;
	}

	/**
	 * Measured torque of axis 4
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutMsrJ4() {
		return this.outMsrJ4;
	}

	/**
	 * Measured torque of axis 5
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutMsrJ5() {
		return this.outMsrJ5;
	}

	/**
	 * Measured torque of axis 6
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutMsrJ6() {
		return this.outMsrJ6;
	}

	/**
	 * Measured torque of axis 7
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutMsrJ7() {
		return this.outMsrJ7;
	}

	/**
	 * Estimated force in X direction
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutTcpFx() {
		return this.outTcpFx;
	}

	/**
	 * Estimated force in Y direction
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutTcpFy() {
		return this.outTcpFy;
	}

	/**
	 * Estimated force in Z direction
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutTcpFz() {
		return this.outTcpFz;
	}

	/**
	 * Estimated torque around X direction
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutTcpTx() {
		return this.outTcpTx;
	}

	/**
	 * Estimated torque around Y direction
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutTcpTy() {
		return this.outTcpTy;
	}

	/**
	 * Estimated torque around Z direction
	 *
	 * @return the output port of the block
	 */
	public final OutPort getOutTcpTz() {
		return this.outTcpTz;
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
