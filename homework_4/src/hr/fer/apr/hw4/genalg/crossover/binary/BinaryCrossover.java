package hr.fer.apr.hw4.genalg.crossover.binary;

import hr.fer.apr.hw4.genalg.crossover.Crossover;
import hr.fer.apr.hw4.genalg.representation.BinaryRepresentation;

public abstract class BinaryCrossover implements Crossover {

	protected BinaryRepresentation binaryRepresentation;

	/**
	 * @param binaryRepresentation
	 */
	public BinaryCrossover(final BinaryRepresentation binaryRepresentation) {
		super();
		this.binaryRepresentation = binaryRepresentation;
	}

	public abstract int createChild(int parent1, int parent2);

	public int[] createChildUnit(final int[] parent1, final int[] parent2) {
		int[] child = new int[parent1.length];
		for (int i = 0; i < parent1.length; ++i) {
			child[i] = createChild(parent1[i], parent2[i]);
		}

		return child;
	}

	@Override
	public double createChild(final double parent1, final double parent2) {
		int binaryParent1 = binaryRepresentation.toBinary(parent1);
		int binaryParent2 = binaryRepresentation.toBinary(parent2);

		int child = createChild(binaryParent1, binaryParent2);

		return binaryRepresentation.toReal(child);
	}

	@Override
	public double[] createChildUnit(final double[] parent1, final double[] parent2) {
		int[] binaryParent1 = binaryRepresentation.toBinary(parent1);
		int[] binaryParent2 = binaryRepresentation.toBinary(parent2);

		int[] child = new int[parent1.length];
		for (int i = 0; i < parent1.length; ++i) {
			child[i] = createChild(binaryParent1[i], binaryParent2[i]);
		}
//		System.out.println("roditelj " + Arrays.toString(binaryParent1));
//		System.out.println("roditelj " + Arrays.toString(binaryParent2));
//		System.out.println("dijete " + Arrays.toString(child));
		return binaryRepresentation.toReal(child);
	}

}
