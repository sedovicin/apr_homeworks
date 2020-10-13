/**
 *
 */
package hr.fer.apr.hw3.function;

/**
 * @author Sebastian
 *
 */
public class Function2 extends Function {

	@Override
	public Double calculateValue(final double... x) {
		return Math.pow((x[0] - 4d), 2) + (4d * Math.pow((x[1] - 2), 2));
	}

}
