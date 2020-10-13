package hr.fer.apr.hw3.calculation;

import java.util.ArrayList;
import java.util.List;

import hr.fer.apr.hw3.function.Function;
import hr.fer.apr.hw3.limit.ImplicitLimitEquation;
import hr.fer.apr.hw3.limit.ImplicitLimitInequation;

public class TUPBOMN extends Calculation {

	private Double precision;
	private Double t;

	private List<ImplicitLimitInequation> inequationLimits = new ArrayList<>();
	private List<ImplicitLimitEquation> equationLimits = new ArrayList<>();

	public TUPBOMN(final Function f) {
		super(f);
		precision = Double.valueOf(1e-6);
		t = Double.valueOf(1d);
	}

	/**
	 * @param f
	 * @param precision
	 * @param t
	 */
	public TUPBOMN(final Function f, final Double precision, final Double t) {
		this(f);
		if (precision != null) {
			this.precision = precision;
		}
		if (t != null) {
			this.t = t;
		}
	}

	public void setInequationLimits(final List<ImplicitLimitInequation> limits) {
		this.inequationLimits = limits;
	}

	public void setEquationLimits(final List<ImplicitLimitEquation> limits) {
		this.equationLimits = limits;
	}

	public double[] calculate(final Double[] point) {
		double t = this.t;

		double[] previousPoint;
		double[] currentPoint = duplicatePoint(point);

		if (!(testInequationLimits(currentPoint))) {
			currentPoint = findInnerPoint(currentPoint);
		}

		HookeJeeves hj = new HookeJeeves(new MixedFunction(f, t));

		do {
			previousPoint = duplicatePoint(currentPoint);
			currentPoint = hj.calculate(previousPoint);

			t *= 10;
		} while (stopConditionUnsatisfied(previousPoint, currentPoint));

		return currentPoint;
	}

	private double[] findInnerPoint(final double[] currentPoint) {
		HookeJeeves hj = new HookeJeeves(new MixedFunction(f, t) {
			@Override
			public Double calculateValue(final double... x) {
				double inequationSum = 0d;
				for (ImplicitLimitInequation limit : inequationLimits) {
					if (!(limit.test(x))) {
						inequationSum += t * limit.calculate(x);
					}
				}
				return -inequationSum;
			}
		});

		return hj.calculate(currentPoint);
	}

	private boolean stopConditionUnsatisfied(final double[] previousPoint, final double[] currentPoint) {
		for (int i = 0; i < currentPoint.length; ++i) {
			if (Math.abs(previousPoint[i] - currentPoint[i]) > precision) {
				return true;
			}
		}
		return false;
	}

	private boolean testInequationLimits(final double[] point) {
		for (ImplicitLimitInequation limit : inequationLimits) {
			boolean res = limit.test(point);
			if (res == false) {
				return false;
			}
		}
		return true;
	}

	protected class MixedFunction extends Function {

		private final Function f;
		private final double t;

		public MixedFunction(final Function f, final double t) {
			this.f = f;
			this.t = t;
		}

		@Override
		public Double calculateValue(final double... x) {
			double inequationSum = 0d;
			for (ImplicitLimitInequation limit : inequationLimits) {
				if (limit.test(x)) {
					inequationSum += Math.log(limit.calculate(x));
				} else {
					return Double.POSITIVE_INFINITY;
//					inequationSum = Double.POSITIVE_INFINITY;
				}
			}
			double equationSum = 0d;
			for (ImplicitLimitEquation limit : equationLimits) {
				equationSum += Math.pow(limit.calculate(x), 2);
			}
			return (f.calculate(x) - ((1d / t) * inequationSum)) + (t * (equationSum));
		}

	}

}
