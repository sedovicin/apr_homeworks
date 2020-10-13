/**
 *
 */
package hr.fer.apr.hw2.function;

/**
 * @author Sebastian
 *
 */
public class Function2 extends Function {

	/*
	 * (non-Javadoc)
	 *
	 * @see hr.fer.apr.hw2.function.Function#calculate(double[])
	 */
	@Override
	public Double calculateValue(final double... x) {
		return Math.pow((x[0] - 4d), 2) + (4d * Math.pow((x[1] - 2), 2));
	}

}
