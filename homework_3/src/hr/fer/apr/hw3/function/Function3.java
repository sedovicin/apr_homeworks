/**
 *
 */
package hr.fer.apr.hw3.function;

/**
 * @author Sebastian
 *
 */
public class Function3 extends Function {

	@Override
	public Double calculateValue(final double... x) {

		return (Math.pow((x[0] - 2), 2)) + (Math.pow((x[1] + 3), 2));
	}

}
