package hr.fer.apr.hw3.limit;

import hr.fer.apr.hw3.function.Function;

public class ImplicitLimitInequation implements Limit {

	private final Function f;

	/**
	 * @param f
	 */
	public ImplicitLimitInequation(final Function f) {
		super();
		this.f = f;
	}

	@Override
	public boolean test(final double... x) {
		return calculate(x) >= 0;
	}

	public double calculate(final double... x) {
		return f.calculate(x);
	}

}
