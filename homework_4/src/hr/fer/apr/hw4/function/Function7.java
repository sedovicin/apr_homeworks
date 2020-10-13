/**
 *
 */
package hr.fer.apr.hw4.function;

/**
 * @author Sebastian
 *
 */
public class Function7 extends Function {

	@Override
	public Double calculateValue(final double... x) {
		double sum = 0d;
		for (int i = 0; i < x.length; ++i) {
			sum += Math.pow((x[i]), 2);
		}
		return Math.pow(sum, 0.25d) * (1d + Math.pow(Math.sin(50d * Math.pow(sum, 0.1d)), 2d));
	}

}
