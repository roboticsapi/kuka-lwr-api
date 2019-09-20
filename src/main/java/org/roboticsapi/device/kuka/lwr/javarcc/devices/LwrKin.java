/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.lwr.javarcc.devices;

import org.roboticsapi.core.world.Transformation;
import org.roboticsapi.core.world.mutable.MutableTransformation;
import org.roboticsapi.facet.javarcc.primitives.world.RPICalc;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIdouble;
import org.roboticsapi.facet.runtime.rpi.world.types.RPIFrame;

public class LwrKin {

	double[] q_min, q_max;

	private final double[] linklength;

	public LwrKin(double[] linklength, double[] min, double[] max) {
		this.linklength = linklength;
		q_min = min;
		q_max = max;
	}

	public RPIFrame kin(double[] joints, RPIFrame pos, RPIdouble alpha) {
		double L0 = linklength[0] + linklength[1], L1 = linklength[2] + linklength[3],
				L2 = linklength[4] + linklength[5], L3 = linklength[6] + linklength[7];
		double j0 = joints[0], j1 = joints[1], j2 = joints[2], j3 = joints[3], j4 = joints[4], j5 = joints[5],
				j6 = joints[6];

		// prepare sin and cos
		double c6 = Math.cos(j6);
		double s6 = Math.sin(j6);
		double c5 = Math.cos(-j5);
		double s5 = Math.sin(-j5);
		double c4 = Math.cos(j4);
		double s4 = Math.sin(j4);
		double c3 = Math.cos(j3);
		double s3 = Math.sin(j3);
		double c2 = Math.cos(j2);
		double s2 = Math.sin(j2);
		double c1 = Math.cos(-j1);
		double s1 = Math.sin(-j1);
		double c0 = Math.cos(j0);
		double s0 = Math.sin(j0);

		// calculate transformation
		double hw11 = c3 * c2 * c1 * c0 + c3 * s2 * -s0 + -s3 * s1 * c0;
		double hw12 = c3 * c2 * c1 * s0 + c3 * s2 * c0 + -s3 * s1 * s0;
		double hw13 = c3 * c2 * -s1 + -s3 * c1;
		double hw21 = -s2 * c1 * c0 + c2 * -s0;
		double hw22 = -s2 * c1 * s0 + c2 * c0;
		double hw23 = -s2 * -s1;
		double hw31 = s3 * c2 * c1 * c0 + s3 * s2 * -s0 + c3 * s1 * c0;
		double hw32 = s3 * c2 * c1 * s0 + s3 * s2 * c0 + c3 * s1 * s0;
		double hw33 = s3 * c2 * -s1 + c3 * c1;
		double hw41 = L2 * s3 * c2 * c1 * c0 + L2 * s3 * s2 * -s0 + L2 * c3 * s1 * c0 + L1 * s1 * c0;
		double hw42 = L2 * s3 * c2 * c1 * s0 + L2 * s3 * s2 * c0 + L2 * c3 * s1 * s0 + L1 * s1 * s0;
		double hw43 = L2 * s3 * c2 * -s1 + L2 * c3 * c1 + L1 * c1;
		double M11 = (((c6 * c5 * c4 + s6 * -s4) * hw11) + ((c6 * c5 * s4 + s6 * c4) * hw21)) + (c6 * -s5 * hw31);
		double M12 = (((c6 * c5 * c4 + s6 * -s4) * hw12) + ((c6 * c5 * s4 + s6 * c4) * hw22)) + (c6 * -s5 * hw32);
		double M13 = (((c6 * c5 * c4 + s6 * -s4) * hw13) + ((c6 * c5 * s4 + s6 * c4) * hw23)) + (c6 * -s5 * hw33);
		// double M14 = 0;
		double M21 = (((-s6 * c5 * c4 + c6 * -s4) * hw11) + ((-s6 * c5 * s4 + c6 * c4) * hw21)) + (-s6 * -s5 * hw31);
		double M22 = (((-s6 * c5 * c4 + c6 * -s4) * hw12) + ((-s6 * c5 * s4 + c6 * c4) * hw22)) + (-s6 * -s5 * hw32);
		double M23 = (((-s6 * c5 * c4 + c6 * -s4) * hw13) + ((-s6 * c5 * s4 + c6 * c4) * hw23)) + (-s6 * -s5 * hw33);
		// double M24 = 0;
		double M31 = ((s5 * c4 * hw11) + (s5 * s4 * hw21)) + (c5 * hw31);
		double M32 = ((s5 * c4 * hw12) + (s5 * s4 * hw22)) + (c5 * hw32);
		double M33 = ((s5 * c4 * hw13) + (s5 * s4 * hw23)) + (c5 * hw33);
		// double M34 = 0;
		double M41 = (((L3 * s5 * c4 * hw11) + (L3 * s5 * s4 * hw21)) + (L3 * c5 * hw31)) + hw41;
		double M42 = (((L3 * s5 * c4 * hw12) + (L3 * s5 * s4 * hw22)) + (L3 * c5 * hw32)) + hw42;
		double M43 = (((L3 * s5 * c4 * hw13) + (L3 * s5 * s4 * hw23)) + (L3 * c5 * hw33)) + hw43 + L0;
		// double M44 = 1;

		MutableTransformation t = new MutableTransformation();
		t.setMatrixVector(M11, M21, M31, M12, M22, M32, M13, M23, M33, M41, M42, M43);
		RPICalc.frameToRpi(t, pos);

		// calculate alpha
		if (Math.abs(j3) < 1e-5) {
			alpha.set(j2);
		} else {
			// calculate ellbow position
			double ellX = L1 * s1 * c0;
			double ellY = L1 * s1 * s0;
			double ellZ = L1 * c1;

			// calculate hwp
			double m41 = L2 * s3 * c2;
			double m42 = L2 * s3 * s2;
			double m43 = L2 * c3;
			double hwpX = m41 * c1 * c0 + m42 * -s0 + m43 * s1 * c0 + ellX;
			double hwpY = m41 * c1 * s0 + m42 * c0 + m43 * s1 * s0 + ellY;
			double hwpZ = m41 * -s1 + m43 * c1 + ellZ;
			double hwpLen = Math.sqrt(((hwpX * hwpX) + (hwpY * hwpY)) + (hwpZ * hwpZ));

			// calculate ellbow direction in hwp-orthogonal plane
			double hwpDotEll = (((ellX * (hwpX / hwpLen)) + (ellY * (hwpY / hwpLen))) + (ellZ * (hwpZ / hwpLen)));
			double dirX = ellX - hwpX / hwpLen * hwpDotEll;
			double dirY = ellY - hwpY / hwpLen * hwpDotEll;
			double dirZ = ellZ - hwpZ / hwpLen * hwpDotEll;

			// calculate "up" vector in hwp-orthogonal plane
			double zeroX = -(hwpX / hwpLen * (hwpZ / hwpLen));
			double zeroY = -(hwpY / hwpLen * (hwpZ / hwpLen));
			double zeroZ = 1 - hwpZ / hwpLen * (hwpZ / hwpLen);

			// calculate "right" vector in hwp-orthogonal plane as hwp x zero
			double rightX = hwpY * zeroZ - hwpZ * zeroY;
			double rightY = hwpZ * zeroX - hwpX * zeroZ;
			double rightZ = hwpX * zeroY - hwpY * zeroX;

			double zeroLen = ((zeroX * zeroX) + (zeroY * zeroY)) + (zeroZ * zeroZ);
			double dirLen = ((dirX * dirX) + (dirY * dirY)) + (dirZ * dirZ);

			// calculate alpha
			double cos = (((zeroX * dirX) + (zeroY * dirY)) + (zeroZ * dirZ))
					/ (Math.sqrt(zeroLen) * Math.sqrt(dirLen));

			// catch rounding errors
			if (cos < -1) {
				cos = -1;
			}
			if (cos > 1) {
				cos = 1;
			}

			double dir = (rightX * dirX) + (rightY * dirY) + (rightZ * dirZ);

			alpha.set((dir < 0 ? -1 : 1) * Math.acos(cos));
		}
		return pos;
	}

	public double[] invKin(RPIFrame frame, double[] hintjoints, double alpha, double[] ret) {
		Transformation pos = RPICalc.rpiToFrame(frame);
		double L0 = linklength[0] + linklength[1], L1 = linklength[2] + linklength[3],
				L2 = linklength[4] + linklength[5], L3 = linklength[6] + linklength[7], PI = Math.PI;

		double r0 = 0, r1 = 0, r2 = 0, r3 = 0, r4 = 0, r5 = 0, r6 = 0;
		double j0 = hintjoints[0], j1 = hintjoints[1], j2 = hintjoints[2], j3 = hintjoints[3], j4 = hintjoints[4],
				j5 = hintjoints[5], j6 = hintjoints[6];

		double m11 = pos.getRotation().get(0, 0); // pos[0, 0];
		double m21 = pos.getRotation().get(0, 1); // pos[0, 1];
		double m31 = pos.getRotation().get(0, 2); // pos[0, 2];
		double m41 = pos.getTranslation().getX(); // pos[0, 3];
		double m12 = pos.getRotation().get(1, 0); // pos[1, 0];
		double m22 = pos.getRotation().get(1, 1); // pos[1, 1];
		double m32 = pos.getRotation().get(1, 2); // pos[1, 2];
		double m42 = pos.getTranslation().getY(); // pos[1, 3];
		double m13 = pos.getRotation().get(2, 0); // pos[2, 0];
		double m23 = pos.getRotation().get(2, 1); // pos[2, 1];
		double m33 = pos.getRotation().get(2, 2); // pos[2, 2];
		double m43 = pos.getTranslation().getZ(); // pos[2, 3];

		double hwpX = m41 - m31 * L3;
		double hwpY = m42 - m32 * L3;
		double hwpZ = m43 - L0 - m33 * L3;

		// do inverse kinematics for hwp

		// calculate simple (alpha=0) solution
		double a1 = Math.atan2(hwpY, hwpX);
		double gamma = Math.atan2(Math.sqrt(hwpX * hwpX + hwpY * hwpY), hwpZ);
		double a = L1, b = L2, c = Math.sqrt(hwpX * hwpX + hwpY * hwpY + hwpZ * hwpZ);

		if (c > a + b && c < a + b + 0.001) {
			c = a + b;
		}
		double a2 = Math.acos((-b * b + a * a + c * c) / (2 * a * c)) - gamma;
		double a3 = PI - Math.acos((-c * c + a * a + b * b) / (2 * a * b));

		r0 = a1;
		r1 = a2;
		r3 = a3;

		// integrate alpha
		if (Math.abs(a3) < 1e-5) {
			// ellbow is straight, just apply alpha
			r2 = alpha;
		} else {
			// rotate ellbow point around alpha

			// calculate rotation direction
			double hwplen = Math.sqrt(((hwpX * hwpX) + (hwpY * hwpY)) + (hwpZ * hwpZ));
			double hx = hwpX / hwplen;
			double hy = hwpY / hwplen;
			double hz = hwpZ / hwplen;

			// calculate ellbow position
			double c1a = Math.cos(-a2);
			double s1a = Math.sin(-a2);
			double c0a = Math.cos(a1);
			double s0a = Math.sin(a1);
			double sa = Math.sin(alpha);
			double ca = Math.cos(alpha);
			double rotEllX = ((L1 * (s1a * c0a) * (hx * hx + (ca * (1.0 - hx * hx))))
					+ (L1 * (s1a * s0a) * ((hx * hy - (ca * (hx * hy))) - (sa * hz))))
					+ (L1 * c1a * ((hx * hz - (ca * (hx * hz))) + (sa * hy)));
			double rotEllY = ((L1 * (s1a * c0a) * ((hx * hy - (ca * (hx * hy))) + (sa * hz)))
					+ (L1 * (s1a * s0a) * (hy * hy + (ca * (1.0 - hy * hy)))))
					+ (L1 * c1a * ((hy * hz - (ca * (hy * hz))) - (sa * hx)));
			double rotEllZ = ((L1 * (s1a * c0a) * ((hx * hz - (ca * (hx * hz))) - (sa * hy)))
					+ (L1 * (s1a * s0a) * ((hy * hz - (ca * (hy * hz))) + (sa * hx))))
					+ (L1 * c1a * (hz * hz + (ca * (1.0 - hz * hz))));

			// calculate angles for ellbow
			r0 = Math.atan2(rotEllY, rotEllX);
			r1 = -Math.atan2(Math.sqrt(rotEllX * rotEllX + rotEllY * rotEllY), rotEllZ);

			// calculate q3

			// current ellbow position
			c1a = Math.cos(-r1);
			s1a = Math.sin(-r1);
			c0a = Math.cos(r0);
			s0a = Math.sin(r0);

			// we want to know how far we have to turn the "Up" vector at the ellbow around
			// "Backward"
			// to point towards hwp
			double toHwX = hwpX - rotEllX;
			double toHwY = hwpY - rotEllY;
			double toHwZ = hwpZ - rotEllZ;
			double ellCrossHwX = (s1a * s0a * toHwZ) - (c1a * toHwY);
			double ellCrossHwY = (c1a * toHwX) - (s1a * c0a * toHwZ);
			double ellCrossHwZ = (s1a * c0a * toHwY) - (s1a * s0a * toHwX);
			double ellYLen = -s0a * -s0a + c0a * c0a;
			double ellCrossHwLen = ((ellCrossHwX * ellCrossHwX) + (ellCrossHwY * ellCrossHwY))
					+ (ellCrossHwZ * ellCrossHwZ);
			double cos = (((ellCrossHwX * -s0a) + (ellCrossHwY * c0a)))
					/ (Math.sqrt(ellCrossHwLen) * Math.sqrt(ellYLen));

			if (cos < -1) {
				cos = -1;
			}
			if (cos > 1) {
				cos = 1;
			}
			double q3 = Math.acos(cos);
			if (alpha < 0) {
				q3 = -q3;
			}

			r2 = q3;
		}

		// select j1/j2
		double mindist = 999, rr0 = r0, rr1 = r1, rr2 = r2;
		while (r0 > q_min[0]) {
			r0 -= PI;
			r1 = -r1;
			r2 -= PI;
		}
		while (r0 < q_max[0]) {
			if (r0 > q_min[0] && Math.abs(r0 - j0) + Math.abs(r1 - j1) < mindist) {
				rr0 = r0;
				rr1 = r1;
				rr2 = r2;
				mindist = Math.abs(r0 - j0) + Math.abs(r1 - j1);
			}
			r0 += PI;
			r1 = -r1;
			r2 += PI;
		}
		r0 = rr0;
		r1 = rr1;
		r2 = rr2;

		// select j3/j4
		mindist = 999;
		rr0 = r2;
		rr1 = r3;
		rr2 = r4;
		while (r2 > q_min[2]) {
			r2 -= PI;
			r3 = -r3;
			r4 -= PI;
		}
		while (r2 < q_max[2]) {
			if (r2 > q_min[2] && Math.abs(r2 - j2) + Math.abs(r3 - j3) < mindist) {
				rr0 = r2;
				rr1 = r3;
				rr2 = r4;
				mindist = Math.abs(r2 - j2) + Math.abs(r3 - j3);
			}
			r2 += PI;
			r3 = -r3;
			r4 += PI;
		}
		r2 = rr0;
		r3 = rr1;
		r4 = rr2;

		// calculate hwp matrix
		double c3 = Math.cos(r3);
		double s3 = Math.sin(r3);
		double c2 = Math.cos(r2);
		double s2 = Math.sin(r2);
		double c1 = Math.cos(-r1);
		double s1 = Math.sin(-r1);
		double c0 = Math.cos(r0);
		double s0 = Math.sin(r0);
		double hwpM11 = c3 * c2 * c1 * c0 + c3 * s2 * -s0 + -s3 * s1 * c0;
		double hwpM12 = c3 * c2 * c1 * s0 + c3 * s2 * c0 + -s3 * s1 * s0;
		double hwpM13 = c3 * c2 * -s1 + -s3 * c1;
		double hwpM21 = -s2 * c1 * c0 + c2 * -s0;
		double hwpM22 = -s2 * c1 * s0 + c2 * c0;
		double hwpM23 = -s2 * -s1;
		double hwpM31 = s3 * c2 * c1 * c0 + s3 * s2 * -s0 + c3 * s1 * c0;
		double hwpM32 = s3 * c2 * c1 * s0 + s3 * s2 * c0 + c3 * s1 * s0;
		double hwpM33 = s3 * c2 * -s1 + c3 * c1;

		// calculate required rotation matrix
		double rotM13 = (((m11 * hwpM31) + (m12 * hwpM32)) + (m13 * hwpM33));
		double rotM23 = (((m21 * hwpM31) + (m22 * hwpM32)) + (m23 * hwpM33));
		double rotM31 = (((m31 * hwpM11) + (m32 * hwpM12)) + (m33 * hwpM13));
		double rotM32 = (((m31 * hwpM21) + (m32 * hwpM22)) + (m33 * hwpM23));
		double rotM33 = (((m31 * hwpM31) + (m32 * hwpM32)) + (m33 * hwpM33));

		// calculate zyz angles
		r4 = Math.atan2(rotM32, rotM31);
		r5 = -Math.atan2(Math.sqrt(rotM31 * rotM31 + rotM32 * rotM32), rotM33);
		r6 = Math.atan2(rotM23, -rotM13);

		// select j5/j6
		mindist = 999;
		rr0 = r4;
		rr1 = r5;
		rr2 = r6;
		while (r4 > q_min[4]) {
			r4 -= PI;
			r5 = -r5;
			r6 -= PI;
		}
		while (r4 < q_max[4]) {
			while (r6 > q_max[6]) {
				r6 -= 2 * PI;
			}
			while (r6 < q_min[6]) {
				r6 += 2 * PI;
			}
			if (r4 > q_min[4] && (Math.abs(r4 - j4) + Math.abs(r5 - j5) + Math.abs(r6 - j6) < mindist)) {
				if (r6 < q_max[6]) {
					rr0 = r4;
					rr1 = r5;
					rr2 = r6;
					mindist = Math.abs(r4 - j4) + Math.abs(r5 - j5) + Math.abs(r6 - j6);
				} else if (mindist == 999) {
					rr0 = r4;
					rr1 = r5;
					rr2 = r6;
				}
			}
			r4 += PI;
			r5 = -r5;
			r6 += PI;
		}

		if (mindist != 999) {
			r4 = rr0;
			r5 = rr1;
			r6 = rr2;
		} else {
			r4 = rr0;
			r5 = rr1;
			r6 = rr2;
			if (Math.abs(r2) > PI + q_min[2] && Math.abs(r0) > PI + q_min[0]) {
				double d2 = r2 > 0 ? r2 - PI : r2 + PI;
				double d0 = r0 > 0 ? r0 - PI : r0 + PI;
				if (Math.abs(d2 - j2) > Math.abs(d0 - j0)) {
					// use j0r
					r0 = d0;
					r1 = -r1;
					r3 = -r3;
					r5 = -r5;
					r6 -= PI;
				} else {
					// use j2
					r2 = d2;
					r3 = -r3;
					r5 = -r5;
					r6 -= PI;
				}
			} else if (Math.abs(r2) > PI + q_min[2]) {
				double d2 = r2 > 0 ? r2 - PI : r2 + PI;
				// use j2
				r2 = d2;
				r3 = -r3;
				r5 = -r5;
				r6 -= PI;
			} else if (Math.abs(r0) > PI + q_min[0]) {
				double d0 = r0 > 0 ? r0 - PI : r0 + PI;
				// use j0
				r0 = d0;
				r1 = -r1;
				r3 = -r3;
				r5 = -r5;
				r6 -= PI;
			} else {
				// impossible - cannot solve the problem
				// just return an invalid position
			}
		}

		if (Math.abs(r3) < 1e-3) {
			// singularity: r3 = 0
			// keep r2 = alpha
			if (Math.abs(r1) < 1e-3 && Math.abs(r5) < 1e-3) {
				// singularity: r1 = r3 = r5 = 0
				// distribute angle change over r0, r4, r6
				double sum = r0 + r2 + r4 + r6;
				double old = j0 + j2 + j4 + r6;
				r0 = j0 + (sum - old) / 3;
				r4 = j4 + (sum - old) / 3;
				r6 = j6 + (sum - old) / 3;
			} else if (Math.abs(r1) < 1e-3) {
				// singularity: r1 = r3 = 0
				// distribute angle change over r0 and r4
				double sum = r0 + r2 + r4;
				double old = j0 + j2 + j4;
				r0 = j0 + (sum - old) / 2;
				r4 = j4 + (sum - old) / 2;
			} else if (Math.abs(r5) < 1e-3) {
				// singularity: r3 = r5 = 0
				// distribute angle change over r4 and r6
				double sum = r2 + r4 + r6;
				double old = j2 + j4 + j6;
				r4 = j4 + (sum - old) / 2;
				r6 = j6 + (sum - old) / 2;
			}

		} else {
			// singularity: r1 = 0
			// distribute angle change over r0 and r2
			if (Math.abs(r1) < 1e-3) {
				double sum = r0 + r2;
				double old = j0 + j2;
				r0 = j0 + (sum - old) / 2;
				r2 = j2 + (sum - old) / 2;
			}

			// singularity: r5 = 0
			// distribute angle change over r4 and r6
			if (Math.abs(r5) < 1e-3) {
				double sum = r4 + r6;
				double old = j4 + j6;
				r4 = j4 + (sum - old) / 2;
				r6 = j6 + (sum - old) / 2;
			}
		}

		ret[0] = r0;
		ret[1] = r1;
		ret[2] = r2;
		ret[3] = r3;
		ret[4] = r4;
		ret[5] = r5;
		ret[6] = r6;

		return ret;
	}

}
