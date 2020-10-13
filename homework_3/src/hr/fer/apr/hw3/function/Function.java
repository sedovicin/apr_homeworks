/**
 *
 */
package hr.fer.apr.hw3.function;

import hr.fer.apr.hw3.Coordinate;
import hr.fer.apr.hw3.Matrix;

/**
 * @author Sebastian
 *
 */
public abstract class Function {

	private long evaluationsCount;
	private long gradientCount;
	private long hesseCount;

	private static final double derivativeConstant = 1e-6;

	/**
	 * @return the evaluationsCount
	 */
	public long getEvaluationsCount() {
		return evaluationsCount;
	}

	public long getGradientCount() {
		return gradientCount;
	}

	public long getHesseCount() {
		return hesseCount;
	}

	public Function() {
		evaluationsCount = 0;
	}

	public final Double calculate(final double... x) {
		++evaluationsCount;
		return calculateValue(x);
	}

	public abstract Double calculateValue(double... x);

	public Double calculateFirstDerivative(final int variableIndex, final double... x) {
		double[] xH = new double[x.length];
		for (int i = 0; i < xH.length; ++i) {
			xH[i] = x[i];
		}
		xH[variableIndex] += derivativeConstant;

		return (calculate(xH) - calculate(x)) / derivativeConstant;
	}

	public double[] calculateGradient(final double... x) {
		double[] gradient = new double[x.length];

		for (int i = 0; i < gradient.length; ++i) {
			gradient[i] = calculateFirstDerivative(i, x);
		}

		++gradientCount;
		return gradient;
	}

	public Double calculateSecondDerivative(final int[] variableIndex, final double... x) {
		double[] xH = new double[x.length];
		for (int i = 0; i < xH.length; ++i) {
			xH[i] = x[i];
		}

		for (int j = 0; j < xH.length; ++j) {
			xH[j] = x[j];
		}

		xH[variableIndex[1]] += derivativeConstant;

		return (calculateFirstDerivative(variableIndex[0], xH) - calculateFirstDerivative(variableIndex[0], x))
				/ (derivativeConstant);
	}

	public Matrix getHesse(final double[] point) throws Exception {
		Matrix result = new Matrix(point.length, point.length);
		for (int i = 0; i < point.length; ++i) {
			for (int j = 0; j < point.length; ++j) {
				result.setElementValue(new Coordinate(i, j), calculateSecondDerivative(new int[] { i, j }, point));
			}
		}

		++hesseCount;
		return result;
	}
}
