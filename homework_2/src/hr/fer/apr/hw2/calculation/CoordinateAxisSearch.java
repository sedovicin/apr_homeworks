/**
 *
 */
package hr.fer.apr.hw2.calculation;

import hr.fer.apr.hw2.function.Function;

/**
 * @author Sebastian
 *
 */
public class CoordinateAxisSearch extends Calculation {

	private Double precision;

	private class FunctionWrapper extends Function {

		Function fOriginal;
		int axisAmount;
		int axis;
		Double[] originalPoint;

		private FunctionWrapper(final Function fOriginal, final int axisAmount, final int axis,
				final Double[] originalPoint) {
			this.fOriginal = fOriginal;
			this.axisAmount = axisAmount;
			this.axis = axis;
			this.originalPoint = originalPoint;

		}

		@Override
		public Double calculateValue(final double... x) {
			double[] point = new double[axisAmount];
			for (int i = 0; i < axisAmount; ++i) {
				point[i] = originalPoint[i].doubleValue();
				if (i == axis) {
					point[i] += x[0];
				}
			}
			return this.fOriginal.calculate(x);

		}

	}

	public CoordinateAxisSearch(final Function f) {
		super(f);
		this.precision = Double.valueOf(1e-6);
	}

	public CoordinateAxisSearch(final Function f, final double precision) {
		this(f);
		this.precision = precision;
	}

	public double[] calculate(final Double[] point) {
		Function fWrapped;
		double[] x = new double[point.length];

		for (int i = 0; i < x.length; ++i) {
			x[i] = point[i].doubleValue();
		}

		double[] xPrev = new double[point.length];
		double sum;
		do {
			sum = 0d;
			xPrev = x;
			x = new double[point.length];
			for (int i = 0; i < point.length; ++i) {
				fWrapped = new FunctionWrapper(f, point.length, i, point);
				GoldenRatio ratio = new GoldenRatio(fWrapped, precision);
				x[i] = ratio.calculateMean(point[i], 1d);
			}

			for (int i = 0; i < x.length; ++i) {
				sum += Math.pow((x[i] - xPrev[i]), 2);
			}
			sum = Math.sqrt(sum);

		} while (sum <= precision);
		return x;
	}
//	public Double calculateMinimum(final Double[] point, final Double precision) {

//	}
}
