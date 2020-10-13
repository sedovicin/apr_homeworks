/**
 *
 */
package hr.fer.apr.hw2.function;

/**
 * @author Sebastian
 *
 */
public abstract class Function {

	private long evaluationsCount;

	/**
	 * @return the evaluationsCount
	 */
	public long getEvaluationsCount() {
		return evaluationsCount;
	}

	public Function() {
		evaluationsCount = 0;
	}

	public final Double calculate(final double... x) {
		++evaluationsCount;
		return calculateValue(x);
	}

	public abstract Double calculateValue(double... x);
}
