package hr.fer.apr.hw3.calculation;

import hr.fer.apr.hw3.Coordinate;
import hr.fer.apr.hw3.Matrix;
import hr.fer.apr.hw3.function.Function;

public class NewtonRaphson extends GradientDescent {

	public NewtonRaphson(final Function f) {
		super(f);
	}

	public NewtonRaphson(final Function f, final Double precision) {
		super(f);
	}

	@Override
	protected double[] calculateOnlyShift(final Double[] point) {
		double[] xNew;
		double[] x0 = new double[point.length];
		for (int i = 0; i < x0.length; ++i) {
			x0[i] = point[i].doubleValue();
		}
		xNew = new double[x0.length];
		for (int i = 0; i < x0.length; ++i) {
			xNew[i] = x0[i];
		}
		try {

			double[] gradient = f.calculateGradient(x0);
			Matrix gradientMatrix = toMatrix(gradient);
			Matrix inverseHesse = f.getHesse(xNew).inverse();

			Matrix shiftMatrix = inverseHesse.multiply(gradientMatrix);
			double[] shift = toDoubleArray(shiftMatrix);

			double norm = getEuclidanNorm(shift);

			int counter = 0;
			int divergenceCount = 0;
			double[] xPrevious = duplicatePoint(xNew);
			double previousDistance = Double.POSITIVE_INFINITY;
			while ((norm >= precision) && (counter++ < 1000) && (divergenceCount < 100)) {
				for (int i = 0; i < x0.length; ++i) {
					xNew[i] += -shift[i];
				}
//				System.out.println(Calculation.pointToString("shift", shift));
//				System.out.println(Calculation.pointToString("xNew", xNew));
//				System.out.println();
				gradient = f.calculateGradient(xNew);
				gradientMatrix = toMatrix(gradient);
				inverseHesse = f.getHesse(xNew).inverse();

				shiftMatrix = inverseHesse.multiply(gradientMatrix);
				shift = toDoubleArray(shiftMatrix);
				norm = getEuclidanNorm(shift);

				double currentDistance = pointDistance(xPrevious, xNew);
				if (currentDistance < previousDistance) {
					divergenceCount = 0;
				} else {
					++divergenceCount;
				}
				xPrevious = duplicatePoint(xNew);
				previousDistance = currentDistance;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xNew;
	}

	@Override
	protected double[] calculateGoldenRatio(final Double[] point) {
		double[] xNew;
		double[] x0 = new double[point.length];
		for (int i = 0; i < x0.length; ++i) {
			x0[i] = point[i].doubleValue();
		}
		xNew = new double[x0.length];
		for (int i = 0; i < x0.length; ++i) {
			xNew[i] = x0[i];
		}
		try {

			double[] gradient = f.calculateGradient(x0);
			Matrix gradientMatrix = toMatrix(gradient);
			Matrix inverseHesse = f.getHesse(xNew).inverse();

			Matrix shiftMatrix = inverseHesse.multiply(gradientMatrix);
			double[] shift = toDoubleArray(shiftMatrix);

			double norm = getEuclidanNorm(shift);

			int counter = 0;
			while ((norm >= precision) && (counter++ < 1000)) {
				double[] v = new double[shift.length];
				for (int i = 0; i < v.length; ++i) {
					v[i] = -shift[i];

				}
				GoldenRatio gr = new GoldenRatio(new FunctionWrapper(f, xNew, v), precision);

				double lambda = gr.calculateMean(0d, 1d);

				for (int i = 0; i < x0.length; ++i) {
					xNew[i] += lambda * v[i];
				}
//				System.out.println(Calculation.pointToString("shift", shift));
//				System.out.println(Calculation.pointToString("xNew", xNew));
//				System.out.println();
				gradient = f.calculateGradient(xNew);
				gradientMatrix = toMatrix(gradient);
				inverseHesse = f.getHesse(xNew).inverse();

				shiftMatrix = inverseHesse.multiply(gradientMatrix);
				shift = toDoubleArray(shiftMatrix);
				norm = getEuclidanNorm(shift);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xNew;
	}

	protected double getEuclidanNorm(final Matrix vector) {
		double[] vectorDouble = new double[vector.getRows()];
		for (int i = 0; i < vectorDouble.length; ++i) {
			vectorDouble[i] = vector.getElementValue(new Coordinate(i, 0));
		}
		return getEuclidanNorm(vectorDouble);
	}

	private Matrix toMatrix(final double[] vector) throws Exception {
		Matrix matrix = new Matrix(vector.length, 1);
		for (int i = 0; i < vector.length; ++i) {
			matrix.setElementValue(new Coordinate(i, 0), vector[i]);
		}
		return matrix;
	}

	private double[] toDoubleArray(final Matrix matrix) {
		double[] array = new double[matrix.getRows()];
		for (int i = 0; i < array.length; ++i) {
			array[i] = matrix.getElementValue(new Coordinate(i, 0));
		}
		return array;
	}

}
