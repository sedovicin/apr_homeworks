/**
 *
 */
package hr.fer.apr.hw4.function;

/**
 * @author Sebastian
 *
 */
public class Function3 extends Function {

	@Override
	public Double calculateValue(final double... x) {
		double sum = 0d;
		for (int i = 0; i < x.length; ++i) {
			sum += Math.pow(((x[i] - i) - 1d), 2);
		}
		return sum;
	}

}
