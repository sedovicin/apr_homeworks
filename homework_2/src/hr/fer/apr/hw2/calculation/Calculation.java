/**
 *
 */
package hr.fer.apr.hw2.calculation;

import hr.fer.apr.hw2.function.Function;

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
}
