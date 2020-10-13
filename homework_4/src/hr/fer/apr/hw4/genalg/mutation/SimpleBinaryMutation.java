package hr.fer.apr.hw4.genalg.mutation;

import java.util.Random;

import hr.fer.apr.hw4.genalg.GeneticAlgorithm;
import hr.fer.apr.hw4.genalg.representation.BinaryRepresentation;

public class SimpleBinaryMutation implements Mutation {

	protected final BinaryRepresentation binaryRepresentation;

	/**
	 * @param binaryRepresentation
	 */
	public SimpleBinaryMutation(final BinaryRepresentation binaryRepresentation) {
		super();
		this.binaryRepresentation = binaryRepresentation;
	}

	@Override
	public double[] mutate(final double[] unit) {
		int[] binary = binaryRepresentation.toBinary(unit);
//		System.out.println(Arrays.toString(binary));
		Random rand = GeneticAlgorithm.getRandomGenerator();

		int bit = rand.nextInt(Integer.SIZE);
//		System.out.println(bit);
		if (bit == 0) {
			bit = Integer.MIN_VALUE;
		} else {
			bit = (int) Math.pow(2, bit);
		}
//		System.out.println(bit);

		int index = rand.nextInt(unit.length);
//		System.out.println(index);
		binary[index] ^= bit;

		return binaryRepresentation.toReal(binary);
	}

}
