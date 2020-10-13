package hr.fer.apr.hw4.genalg.crossover.floating;

import java.util.Random;

import hr.fer.apr.hw4.genalg.GeneticAlgorithm;

public class ArithmeticFloatingCrossover extends FloatingCrossover {

	private final Random rand;

	public ArithmeticFloatingCrossover() {
		super();
		rand = GeneticAlgorithm.getRandomGenerator();
	}

	@Override
	public double createChild(final double parent1, final double parent2) {
		double random = rand.nextDouble();
		return (random * parent1) + ((1d - random) * parent2);
	}

}
