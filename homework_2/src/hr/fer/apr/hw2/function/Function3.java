/**
 *
 */
package hr.fer.apr.hw2.function;

/**
 * @author Sebastian
 *
 */
public class Function3 extends Function {

	/*
	 * (non-Javadoc)
	 *
	 * @see hr.fer.apr.hw2.function.Function#calculate(double[])
	 */
	@Override
	public Double calculateValue(final double... x) {
		double sum = 0d;
		for (int i = 0; i < x.length; ++i) {
			sum += Math.pow((x[i] - i), 2);
		}
		return sum;
	}

}
