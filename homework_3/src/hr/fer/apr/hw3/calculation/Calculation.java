/**
 *
 */
package hr.fer.apr.hw3.calculation;

import hr.fer.apr.hw3.function.Function;

/**
 * @author Sebastian
 *
 */
public abstract class Calculation {

	protected final Function f;

	public Calculation(final Function f) {
		this.f = f;
	}

	public static String pointToString(final String pointName, final double[] point) {
		StringBuilder sb = new StringBuilder();
		sb.append(pointName).append(": (").append(point[0]);
		for (int i = 1; i < point.length; ++i) {
			sb.append(", ");
			sb.append(point[i]);
		}
		sb.append(")");

		return sb.toString();
	}

	protected double[] duplicatePoint(final double[] point) {
		double[] xNew = new double[point.length];
		for (int i = 0; i < point.length; ++i) {
			xNew[i] = point[i];
		}

		return xNew;
	}

	protected double[] duplicatePoint(final Double[] point) {
		double[] xNew = new double[point.length];
		for (int i = 0; i < point.length; ++i) {
			xNew[i] = point[i].doubleValue();
		}

		return xNew;
	}

	protected double pointDistance(final double[] x1, final double[] x2) {
		double sum = 0;
		for (int i = 0; i < x2.length; ++i) {
			sum += Math.pow((x1[i] - x2[i]), 2);
		}
		return Math.sqrt(sum);
	}
}
