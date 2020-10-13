/**
 *
 */
package hr.fer.apr.hw4.function;

/**
 * @author Sebastian
 *
 */
public class Function6 extends Function {

	@Override
	public Double calculateValue(final double... x) {
		double sum = 0d;
		for (int i = 0; i < x.length; ++i) {
			sum += Math.pow((x[i]), 2);
		}
		return 0.5d + ((Math.pow(Math.sin(Math.sqrt(sum)), 2) - 0.5d) / (Math.pow(1d + (0.001d * sum), 2)));
	}

}
