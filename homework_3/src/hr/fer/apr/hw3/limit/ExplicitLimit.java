package hr.fer.apr.hw3.limit;

public class ExplicitLimit implements Limit {

	private final double[] lowerLimit;
	private final double[] upperLimit;

	/**
	 * @param lowerLimit
	 * @param upperLimit
	 */
	public ExplicitLimit(final double[] lowerLimit, final double[] upperLimit) {
		super();
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}

	/**
	 * @return the lowerLimit
	 */
	public double[] getLowerLimit() {
		return lowerLimit;
	}

	/**
	 * @return the upperLimit
	 */
	public double[] getUpperLimit() {
		return upperLimit;
	}

	@Override
	public boolean test(final double... x) {
		for (int i = 0; i < x.length; ++i) {
			if ((x[i] < lowerLimit[i]) || (x[i] > upperLimit[i])) {
				return false;
			}
		}
		return true;
	}

}
