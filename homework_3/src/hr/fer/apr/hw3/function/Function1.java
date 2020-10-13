/**
 *
 */
package hr.fer.apr.hw3.function;

/**
 * @author Sebastian
 *
 */
public class Function1 extends Function {

	@Override
	public Double calculateValue(final double... x) {
		return (100d * Math.pow((x[1] - Math.pow(x[0], 2)), 2)) + Math.pow((1d - x[0]), 2);
	}

}
