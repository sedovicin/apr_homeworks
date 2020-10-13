package hr.fer.apr.hw4.genalg.crossover.binary;

import java.util.Random;

import hr.fer.apr.hw4.genalg.GeneticAlgorithm;
import hr.fer.apr.hw4.genalg.representation.BinaryRepresentation;

public class UniformBinaryCrossover extends BinaryCrossover {

	private final Random rand;

	public UniformBinaryCrossover(final BinaryRepresentation binaryRepresentation) {
		super(binaryRepresentation);
		rand = GeneticAlgorithm.getRandomGenerator();
	}

	@Override
	public int createChild(final int parent1, final int parent2) {
		return ((parent1 & parent2) | (rand.nextInt() & (parent1 ^ parent2)));
	}

}
