package hr.fer.apr.hw4.genalg.crossover.floating;

import hr.fer.apr.hw4.genalg.crossover.Crossover;

public abstract class FloatingCrossover implements Crossover {

	@Override
	public double[] createChildUnit(final double[] parent1, final double[] parent2) {
		double[] child = new double[parent1.length];
		for (int i = 0; i < parent1.length; ++i) {
			child[i] = createChild(parent1[i], parent2[i]);
		}
		return child;
	}
}
