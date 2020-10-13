/**
 *
 */
package hr.fer.apr.hw2.calculation;

import hr.fer.apr.hw2.function.Function;

/**
 * @author Sebastian
 *
 */
public class NelderMeadSimplex extends Calculation {

	private Double epsilon;
	private Double shift;
	private Double alpha;
	private Double beta;
	private Double gamma;
	private Double sigma;

	public NelderMeadSimplex(final Function f) {
		super(f);
		this.epsilon = Double.valueOf(1e-6);
		this.shift = Double.valueOf(1d);
		this.alpha = Double.valueOf(1d);
		this.beta = Double.valueOf(0.5d);
		this.gamma = Double.valueOf(2d);
		this.sigma = Double.valueOf(0.5d);
	}

	public NelderMeadSimplex(final Function f, final Double precision, final Double shift, final Double alpha,
			final Double beta, final Double gamma, final Double sigma) {
		this(f);
		this.epsilon = precision;
		this.shift = shift;
		this.alpha = alpha;
		this.beta = beta;
		this.gamma = gamma;
		this.sigma = sigma;
	}

	public double[] calculate(final Double[] point) {

		double[][] simplexDots = calculateSimplexPoints(point, shift);

//		for (int i = 0; i < simplexDots.length; ++i) {
//			System.out.println(pointToString("x" + i, simplexDots[i]));
//		}

		double[] xC = null;
		do {
			double maxValue = Double.MIN_VALUE; // negative infinity i positive infinity?!
			double minValue = Double.MAX_VALUE;
			int h = 0;
			int l = 0;
			for (int i = 0; i < simplexDots.length; ++i) {
				double value = f.calculate(simplexDots[i]);
//				System.out.println(pointToString("x" + i, simplexDots[i]) + "; Fx" + i + ": " + value);
				if (value > maxValue) {
					maxValue = value;
					h = i;
				}
				if (value < minValue) {
					minValue = value;
					l = i;
				}
			}

			xC = calculateCentroid(point, simplexDots, h);

			double[] xR = reflection(simplexDots, h, xC, alpha);
			double FxR = f.calculate(xR);
			double Fxl = f.calculate(simplexDots[l]);
			if (FxR < Fxl) {
				double[] xE = expansion(xC, xR, gamma);
				double FxE = f.calculate(xE);
				Fxl = f.calculate(simplexDots[l]);
				if (FxE < Fxl) {
					for (int i = 0; i < simplexDots[h].length; ++i) {
						simplexDots[h][i] = xE[i];
					}
				} else {
					for (int i = 0; i < simplexDots[h].length; ++i) {
						simplexDots[h][i] = xR[i];
					}
				}
			} else {
				boolean xRbiggest = true;
				for (int i = 0; i < simplexDots.length; ++i) {
					if (i != h) {
						double Fxi = f.calculate(simplexDots[i]);
						if (FxR < Fxi) {
							xRbiggest = false;
							break;
						}
					}
				}
				if (xRbiggest) {
					double Fxh = f.calculate(simplexDots[h]);
					if (FxR < Fxh) {
						for (int i = 0; i < simplexDots[h].length; ++i) {
							simplexDots[h][i] = xR[i];
						}
					}
					double[] xK = contraction(simplexDots, h, xC, beta);
					double FxK = f.calculate(xK);
					Fxh = f.calculate(simplexDots[h]);
					if (FxK < Fxh) {
						for (int i = 0; i < simplexDots[h].length; ++i) {
							simplexDots[h][i] = xK[i];
						}
					} else {
						simplexDots = moveTowardLowest(simplexDots, l, sigma);
					}
				} else {
					for (int i = 0; i < simplexDots[h].length; ++i) {
						simplexDots[h][i] = xR[i];
					}
				}
			}

		} while (stopCriteriaUnsatisfied(simplexDots, xC, epsilon));

		return xC;
	}

	private double[] reflection(final double[][] simplexDots, final int h, final double[] xC, final double alpha) {
		double[] xR = new double[xC.length];
		for (int i = 0; i < xR.length; ++i) {
			xR[i] = ((1 + alpha) * xC[i]) - (alpha * simplexDots[h][i]);
		}
		return xR;
	}

	private double[] expansion(final double[] xC, final double[] xR, final double gamma) {
		double[] xE = new double[xC.length];
		for (int i = 0; i < xE.length; ++i) {
			xE[i] = ((1 - gamma) * xC[i]) + (gamma * xR[i]);
		}
		return xE;
	}

	private double[] contraction(final double[][] simplexDots, final int h, final double[] xC, final double beta) {
		double[] xK = new double[xC.length];
		for (int i = 0; i < xK.length; ++i) {
			xK[i] = ((1 - beta) * xC[i]) + (beta * simplexDots[h][i]);
		}
		return xK;
	}

	private double[][] moveTowardLowest(final double[][] simplexDots, final int l, final Double sigma) {
		for (int i = 0; i < simplexDots.length; ++i) {
			for (int j = 0; j < simplexDots[i].length; ++j) {
//				simplexDots[i][j] = (simplexDots[i][j] + simplexDots[l][j]) / 2;
				simplexDots[i][j] += ((simplexDots[i][j] > simplexDots[l][j]) ? -sigma.doubleValue()
						: sigma.doubleValue());
			}
		}
		return simplexDots;
	}

	private boolean stopCriteriaUnsatisfied(final double[][] simplexDots, final double[] xC, final double epsilon) {

		double FxC = f.calculate(xC);
		double sum = 0d;

		System.out.println(pointToString("xC", xC));
		System.out.println("FxC:" + FxC);

		for (int i = 0; i < simplexDots.length; ++i) {
			double Fxi = f.calculate(simplexDots[i]);
			sum += Math.pow((Fxi - FxC), 2);
		}
		sum /= simplexDots.length;
		sum = Math.sqrt(sum);

		return !(sum <= epsilon);
	}

	private double[][] calculateSimplexPoints(final Double[] point, final Double shift) {
		double[][] simplexDots = new double[point.length + 1][point.length];
		for (int i = 0; i < point.length; ++i) {
			simplexDots[0][i] = point[i].doubleValue();
		}
		for (int i = 1; i < simplexDots.length; ++i) {
			for (int j = 0; j < simplexDots[i].length; ++j) {
				simplexDots[i][j] = simplexDots[0][j];
				if ((i - 1) == j) {
					simplexDots[i][j] += shift;
				}
			}
		}
		return simplexDots;
	}

	private double[] calculateCentroid(final Double[] point, final double[][] simplexDots, final int worstIndex) {
		double[] xC = new double[point.length];
		for (int i = 0; i < xC.length; ++i) {
			xC[i] = 0d;
		}
		for (int j = 0; j < simplexDots[0].length; ++j) {
			for (int i = 0; i < simplexDots.length; ++i) {
				if (i != worstIndex) {
					xC[j] += ((simplexDots[i][j] - xC[j]) / (i + 1));
				}
			}
		}

		return xC;
	}

}
