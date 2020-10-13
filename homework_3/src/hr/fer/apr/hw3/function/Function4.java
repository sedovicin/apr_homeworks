/**
 *
 */
package hr.fer.apr.hw3.function;

/**
 * @author Sebastian
 *
 */
public class Function4 extends Function {

	@Override
	public Double calculateValue(final double... x) {
		return (Math.pow((x[0] - 3), 2)) + (Math.pow(x[1], 2));
	}

}
