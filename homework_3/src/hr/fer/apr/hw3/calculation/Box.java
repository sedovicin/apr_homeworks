package hr.fer.apr.hw3.calculation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.apr.hw3.function.Function;
import hr.fer.apr.hw3.limit.ExplicitLimit;
import hr.fer.apr.hw3.limit.Limit;

public class Box extends Calculation {

	private Double precision;
	private Double alpha;

	private List<Limit> limits = new ArrayList<>();

	public Box(final Function f) {
		super(f);
		precision = Double.valueOf(1e-6);
		alpha = Double.valueOf(1.3d);
	}

	public Box(final Function f, final Double precision, final Double alpha) {
		this(f);
		if (precision != null) {
			this.precision = precision;
		}
		if (alpha != null) {
			this.alpha = alpha;
		}
	}

	public void setLimits(final List<Limit> limits) {
		this.limits = limits;
	}

	public double[] calculate(final Double[] point) {
		double[] x0 = duplicatePoint(point);

		double[] lowerLimit = getLowerLimit();
		double[] upperLimit = getUpperLimit();
		Random rand = new Random();
		if (testLimits(x0)) {
			double[] xC = duplicatePoint(x0);

			double[][] pointsSet = new double[2 * xC.length][xC.length];
			for (int t = 0; t < (2 * xC.length); ++t) {
				for (int i = 0; i < xC.length; ++i) {
					double r = rand.nextDouble();
					pointsSet[t][i] = lowerLimit[i] + (r * (upperLimit[i] - lowerLimit[i]));
				}
				while (!(testLimits(pointsSet[t]))) {
					moveTowardCentroid(pointsSet[t], xC);

				}
				xC = new double[xC.length];
				for (int i = 0; i < xC.length; ++i) {
					xC[i] = 0d;
				}

				for (int j = 0; j < xC.length; ++j) {
					int avgCount = 0;
					for (int t2 = 0; t2 <= t; ++t2) {
						xC[j] += ((pointsSet[t2][j] - xC[j]) / (++avgCount));
					}
				}
			}
			do {
				int h = getWorstPointIndex(pointsSet);
				int h2 = getSecondWorstPointIndex(pointsSet, h);

				xC = calculateCentroid(pointsSet, h);

				double[] xR = reflection(pointsSet, h, xC);

				for (int i = 0; i < xC.length; ++i) {
					if (xR[i] < lowerLimit[i]) {
						xR[i] = lowerLimit[i];
					} else {
						if (xR[i] > upperLimit[i]) {
							xR[i] = upperLimit[i];
						}
					}
				}
				while (!(testLimits(xR))) {
					moveTowardCentroid(xR, xC);
				}

				if (f.calculate(xR) > f.calculate(pointsSet[h2])) {
					moveTowardCentroid(xR, xC);
				}

				pointsSet[h] = xR;

			} while (stopConditionUnsatisfied(pointsSet[getWorstPointIndex(pointsSet)], xC));
			return xC;
		}
		return x0;
	}

	private boolean testLimits(final double[] point) {
		for (Limit limit : limits) {
			boolean res = limit.test(point);
			if (res == false) {
				return false;
			}
		}
		return true;
	}

	private double[] getUpperLimit() {
		for (Limit limit : limits) {
			if (limit instanceof ExplicitLimit) {
				return ((ExplicitLimit) limit).getUpperLimit();
			}
		}
		return new double[] { Double.POSITIVE_INFINITY };
	}

	private double[] getLowerLimit() {
		for (Limit limit : limits) {
			if (limit instanceof ExplicitLimit) {
				return ((ExplicitLimit) limit).getLowerLimit();
			}
		}
		return new double[] { Double.NEGATIVE_INFINITY };
	}

	private void moveTowardCentroid(final double[] point, final double[] xC) {
		for (int i = 0; i < xC.length; ++i) {
			point[i] = (1d / 2d) * (point[i] + xC[i]);
		}
	}

	private double[] calculateCentroid(final double[][] points, final int worstIndex) {
		double[] xC = new double[points[0].length];
		for (int i = 0; i < xC.length; ++i) {
			xC[i] = 0d;
		}

		for (int j = 0; j < points[0].length; ++j) {
			int avgCount = 0;
			for (int i = 0; i < points.length; ++i) {
				if (i != worstIndex) {
					xC[j] += ((points[i][j] - xC[j]) / (++avgCount));
				}
			}
		}

		return xC;
	}

	private int getWorstPointIndex(final double[][] points) {
		int h = 0;
		double maxValue = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < points.length; ++i) {
			double value = f.calculate(points[i]);
			if (value > maxValue) {
				maxValue = value;
				h = i;
			}
		}
		return h;
	}

	private int getSecondWorstPointIndex(final double[][] points, final int worstPoint) {
		int h = 0;
		double maxValue = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < points.length; ++i) {
			if (i != worstPoint) {
				double value = f.calculate(points[i]);
				if (value > maxValue) {
					maxValue = value;
					h = i;
				}
			}
		}
		return h;
	}

	private double[] reflection(final double[][] points, final int h, final double[] xC) {
		double[] xR = new double[xC.length];
		for (int i = 0; i < xR.length; ++i) {
			xR[i] = ((1 + alpha) * xC[i]) - (alpha * points[h][i]);
		}
		return xR;
	}

	private boolean stopConditionUnsatisfied(final double[] x, final double[] xC) {
		return !(pointDistance(x, xC) < precision);
	}
}
