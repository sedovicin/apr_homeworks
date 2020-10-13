package hr.fer.apr.hw2.calculation;

import hr.fer.apr.hw2.function.Function;

public class HookeJeeves extends Calculation {

	private Double deltaX;
	private Double precision;

	public HookeJeeves(final Function f) {
		super(f);
		deltaX = Double.valueOf(0.5d);
		precision = Double.valueOf(1e-6);
	}

	public HookeJeeves(final Function f, final Double deltaX, final Double precision) {
		this(f);
		this.deltaX = deltaX;
		this.precision = precision;
	}

	public double[] calculate(final Double[] point) {
		double Dx = deltaX.doubleValue();

		double[] x0 = duplicatePoint(point);
		double[] xP = duplicatePoint(x0);
		double[] xB = duplicatePoint(x0);

		do {
			double[] xN = HJexplore(xP, Dx);
			double FxB = f.calculate(xB);
			double FxP = f.calculate(xP);
			double FxN = f.calculate(xN);

			System.out.println(pointToString("xB", xB) + "; " + "FxB: " + FxB);
			System.out.println(pointToString("xP", xP) + "; " + "FxP: " + FxP);
			System.out.println(pointToString("xN", xN) + "; " + "FxN: " + FxN);
			System.out.println();

			if (FxN < FxB) {
				for (int i = 0; i < xP.length; ++i) {
					xP[i] = (2 * xN[i]) - xB[i];
				}
				xB = duplicatePoint(xN);

			} else {
				Dx /= 2;
				xP = duplicatePoint(xB);
			}
		} while (Dx > precision.doubleValue());

//		Double[] xBreturn = new Double[xB.length];
//		for (int i = 0; i < xB.length; ++i) {
//			xBreturn[i] = Double.valueOf(xB[i]);
//		}
		return xB;
	}

	private double[] HJexplore(final double[] xP, final double dx) {
		double[] x = duplicatePoint(xP);
		for (int i = 0; i < x.length; ++i) {
			double P = f.calculate(x);
			x[i] += dx;
			double N = f.calculate(x);
			if (N > P) {
				x[i] -= 2 * dx;
				N = f.calculate(x);
				if (N > P) {
					x[i] += dx;
				}
			}
		}

		return x;
	}

	private double[] duplicatePoint(final double[] point) {
		double[] xNew = new double[point.length];
		for (int i = 0; i < point.length; ++i) {
			xNew[i] = point[i];
		}

		return xNew;
	}

	private double[] duplicatePoint(final Double[] point) {
		double[] xNew = new double[point.length];
		for (int i = 0; i < point.length; ++i) {
			xNew[i] = point[i].doubleValue();
		}

		return xNew;
	}
}
