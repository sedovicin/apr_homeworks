package hr.fer.apr.hw4.genalg.mutation;

import java.util.Random;

import hr.fer.apr.hw4.genalg.GeneticAlgorithm;

public class GaussianFloatingMutation implements Mutation {

	@Override
	public double[] mutate(final double[] unit) {
		Random rand = GeneticAlgorithm.getRandomGenerator();
		for (int i = 0; i < unit.length; ++i) {
			unit[i] = rand.nextGaussian();
		}
		return unit;
	}

}
