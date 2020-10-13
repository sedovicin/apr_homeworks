package hr.fer.apr.hw3.calculation;

import hr.fer.apr.hw3.function.Function;

public class GradientDescent extends Calculation {

	protected Double precision;

	public GradientDescent(final Function f) {
		super(f);
		precision = Double.valueOf(1e-6);
	}

	public GradientDescent(final Function f, final Double precision) {
		this(f);
		this.precision = precision;
	}

	public double[] calculate(final Double[] point, final boolean goldenRatio) {
		if (goldenRatio) {
			return calculateGoldenRatio(point);
		} else {
			return calculateOnlyShift(point);
		}
	}

	protected double[] calculateOnlyShift(final Double[] point) {
		double[] x0 = new double[point.length];
		for (int i = 0; i < x0.length; ++i) {
			x0[i] = point[i].doubleValue();
		}
		double[] xNew = new double[x0.length];
		for (int i = 0; i < x0.length; ++i) {
			xNew[i] = x0[i];
		}
		double[] gradient = f.calculateGradient(x0);

		int counter = 0;
		int divergenceCount = 0;
		double[] xPrevious = duplicatePoint(xNew);
		double previousDistance = Double.POSITIVE_INFINITY;
		while ((getEuclidanNorm(gradient) >= precision) && (counter++ < 1000) && (divergenceCount < 100)) {
			for (int i = 0; i < x0.length; ++i) {
				xNew[i] += -gradient[i];
			}
//			System.out.println(Calculation.pointToString("gradient", gradient));
//			System.out.println(Calculation.pointToString("xNew", xNew));
//			System.out.println();
			gradient = f.calculateGradient(xNew);

			double currentDistance = pointDistance(xPrevious, xNew);
//			System.out.println("Udaljenost: " + currentDistance);
			if (currentDistance < previousDistance) {
				divergenceCount = 0;
			} else {
				++divergenceCount;
			}
			xPrevious = duplicatePoint(xNew);
			previousDistance = currentDistance;
		}

		if (divergenceCount >= 100) {
			System.out.println("Doslo je do divergencije!");
		}
		return xNew;
	}

	protected double[] calculateGoldenRatio(final Double[] point) {
		double[] x0 = new double[point.length];
		for (int i = 0; i < x0.length; ++i) {
			x0[i] = point[i].doubleValue();
		}
		double[] xNew = new double[x0.length];
		for (int i = 0; i < x0.length; ++i) {
			xNew[i] = x0[i];
		}
		double[] gradient = f.calculateGradient(x0);
		double norm = getEuclidanNorm(gradient);

		int counter = 0;
		while ((norm >= precision) && (counter++ < 1000)) {
			double[] v = new double[gradient.length];
			for (int i = 0; i < v.length; ++i) {
				v[i] = -gradient[i];

			}
			GoldenRatio gr = new GoldenRatio(new FunctionWrapper(f, xNew, v), precision);

			double lambda = gr.calculateMean(0d, 1d);

			for (int i = 0; i < x0.length; ++i) {
				xNew[i] += lambda * v[i];
			}
//			System.out.println(Calculation.pointToString("gradient", gradient));
//			System.out.println(Calculation.pointToString("xNew", xNew));
//			System.out.println();
			gradient = f.calculateGradient(xNew);
			norm = getEuclidanNorm(gradient);
		}
		return xNew;
	}

	protected double getEuclidanNorm(final double[] vector) {
		double rootContent = 0d;
		for (int i = 0; i < vector.length; ++i) {
			rootContent += Math.pow(vector[i], 2);
		}
		return Math.sqrt(rootContent);
	}

	protected class FunctionWrapper extends Function {

		private final Function fOriginal;
		private final double[] x0;
		private final double[] v;

		public FunctionWrapper(final Function f, final double[] x0, final double[] v) {
			this.fOriginal = f;
			this.x0 = x0;
			this.v = v;
		}

		@Override
		public Double calculateValue(final double... x) {

			double[] newX = new double[x0.length];
			for (int i = 0; i < newX.length; ++i) {
				newX[i] = x0[i] + (x[0] * v[i]);
			}
			return fOriginal.calculate(newX);
		}

		@Override
		public long getEvaluationsCount() {
			return f.getEvaluationsCount();
		}

	}

}
