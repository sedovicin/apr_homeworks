package hr.fer.apr.hw3.Main;

import hr.fer.apr.hw3.function.Function;
import hr.fer.apr.hw3.limit.ExplicitLimit;
import hr.fer.apr.hw3.limit.ImplicitLimitEquation;
import hr.fer.apr.hw3.limit.ImplicitLimitInequation;

public class Limits {

	public static ExplicitLimit getTask3ExplicitLimit() {
		return new ExplicitLimit(new double[] { -100d, -100d }, new double[] { 100d, 100d });
	}

	public static ImplicitLimitInequation getTask3ImplicitLimitIneq1() {
		Function f = new Function() {

			@Override
			public Double calculateValue(final double... x) {
				return x[1] - x[0];
			}
		};

		return new ImplicitLimitInequation(f);
	}

	public static ImplicitLimitInequation getTask3ImplicitLimitIneq2() {
		Function f = new Function() {

			@Override
			public Double calculateValue(final double... x) {
				return 2d - x[0];
			}
		};

		return new ImplicitLimitInequation(f);
	}

	public static ImplicitLimitInequation getTask5ImplicitLimitIneq1() {
		Function f = new Function() {

			@Override
			public Double calculateValue(final double... x) {

				return 3d - x[0] - x[1];
			}
		};

		return new ImplicitLimitInequation(f);
	}

	public static ImplicitLimitInequation getTask5ImplicitLimitIneq2() {
		Function f = new Function() {

			@Override
			public Double calculateValue(final double... x) {

				return (3d + (1.5d * x[0])) - x[1];
			}
		};

		return new ImplicitLimitInequation(f);
	}

	public static ImplicitLimitEquation getTask5ImplicitLimitEq1() {
		Function f = new Function() {

			@Override
			public Double calculateValue(final double... x) {

				return x[1] - 1d;
			}
		};

		return new ImplicitLimitEquation(f);
	}
}
