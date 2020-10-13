/**
 *
 */
package hr.fer.apr.hw2.function;

/**
 * @author Sebastian
 *
 */
public class Function4 extends Function {

	/*
	 * (non-Javadoc)
	 *
	 * @see hr.fer.apr.hw2.function.Function#calculate(double[])
	 */
	@Override
	public Double calculateValue(final double... x) {

		return Math.abs((x[0] - x[1]) * (x[0] + x[1])) + Math.sqrt((Math.pow(x[0], 2) + Math.pow(x[1], 2)));
	}

}
