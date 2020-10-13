package hr.fer.apr.hw4.genalg.crossover.floating;

import java.util.Random;

import hr.fer.apr.hw4.function.Function;
import hr.fer.apr.hw4.genalg.GeneticAlgorithm;

public class HeuristicFloatingCrossover extends FloatingCrossover {

	private final Random rand;

	private Function f;
	private double lowerLimit;
	private double upperLimit;

	private Long evaluationCount;

	private HeuristicFloatingCrossover() {
		super();
		rand = GeneticAlgorithm.getRandomGenerator();
	}

	/**
	 * @param lowerLimit
	 * @param upperLimit
	 */
	public HeuristicFloatingCrossover(final Function f, final double lowerLimit, final double upperLimit,
			final Long evaluationCount) {
		this();
		this.f = f;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
		this.evaluationCount = evaluationCount;
	}

	@Override
	public double createChild(final double parent1, final double parent2) {
		double child;
		int count = 100;
		do {
			child = (rand.nextDouble() * (parent2 - parent1)) + parent2;
		} while (((child > upperLimit) || (child < lowerLimit)) && (--count > 0));
		if (child < lowerLimit) {
			return lowerLimit;
		}
		if (child > upperLimit) {
			return upperLimit;
		}
		return child;
	}

	@Override
	public double[] createChildUnit(final double[] parent1, final double[] parent2) {
		double[] child;
		double[] worserParent;
		double[] betterParent;
		double parent1Res = f.calculate(parent1);
		double parent2Res = f.calculate(parent2);

		if (parent1Res < parent2Res) {
			betterParent = parent1;
			worserParent = parent2;
		} else {
			betterParent = parent2;
			worserParent = parent1;
		}

		do {
			child = super.createChildUnit(worserParent, betterParent);
//			System.out.println(Arrays.toString(child));
		} while (!(childWithinMargins(child)) && (f.getEvaluationsCount() < evaluationCount));

		return child;
	}

	private boolean childWithinMargins(final double[] child) {
		for (int i = 0; i < child.length; ++i) {
			if ((child[i] > upperLimit) || (child[i] < lowerLimit)) {
				return false;
			}
		}
		return true;
	}

}
