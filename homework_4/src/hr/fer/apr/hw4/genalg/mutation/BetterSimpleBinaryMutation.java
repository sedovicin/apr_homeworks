package hr.fer.apr.hw4.genalg.mutation;

import java.util.Random;

import hr.fer.apr.hw4.genalg.GeneticAlgorithm;
import hr.fer.apr.hw4.genalg.representation.BinaryRepresentation;

public class BetterSimpleBinaryMutation extends SimpleBinaryMutation {

	public BetterSimpleBinaryMutation(final BinaryRepresentation binaryRepresentation) {
		super(binaryRepresentation);
	}

	@Override
	public double[] mutate(final double[] unit) {
		int[] binary = binaryRepresentation.toBinary(unit);
//		System.out.println(Arrays.toString(binary));
		Random rand = GeneticAlgorithm.getRandomGenerator();

		for (int i = 0; i < unit.length; ++i) {
			int bit = rand.nextInt(Integer.SIZE);
//		System.out.println(bit);
			if (bit == 0) {
				bit = Integer.MIN_VALUE;
			} else {
				bit = (int) Math.pow(2, bit);
			}
//		System.out.println(bit);

//		System.out.println(index);
			binary[i] ^= bit;
		}
		return binaryRepresentation.toReal(binary);
	}

}
