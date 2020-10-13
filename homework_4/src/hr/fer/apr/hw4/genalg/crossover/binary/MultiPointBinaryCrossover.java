package hr.fer.apr.hw4.genalg.crossover.binary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import hr.fer.apr.hw4.genalg.GeneticAlgorithm;
import hr.fer.apr.hw4.genalg.representation.BinaryRepresentation;

public class MultiPointBinaryCrossover extends BinaryCrossover {

	private int pointsCount;
	private final Random rand;

	public MultiPointBinaryCrossover(final BinaryRepresentation binaryRepresentation) {
		super(binaryRepresentation);
		rand = GeneticAlgorithm.getRandomGenerator();
		this.pointsCount = rand.nextInt(5) + 1;
	}

	public MultiPointBinaryCrossover(final BinaryRepresentation binaryRepresentation, final int pointsCount) {
		this(binaryRepresentation);
		this.pointsCount = pointsCount;
	}

	@Override
	public int createChild(final int parent1, final int parent2) {
//		System.out.println(Integer.toBinaryString(parent1));
//		System.out.println(Integer.toBinaryString(parent2));
		int num = parent1 ^ parent2;
		int trailingZeros = Integer.numberOfLeadingZeros(num);
		if (pointsCount > (Integer.SIZE - trailingZeros)) {
			trailingZeros = Integer.SIZE - pointsCount;
		}

		List<Integer> crossPoints = new ArrayList<>();
		while (crossPoints.size() < pointsCount) {
			int crossPoint = rand.nextInt(Integer.SIZE - trailingZeros) + 1 + trailingZeros;
			if (!(crossPoints.contains(crossPoint))) {
				crossPoints.add(crossPoint);
			}
		}
		Collections.sort(crossPoints);
//		System.out.println(crossPoints.toString());

		boolean firstParMostLeft = rand.nextBoolean();
		int mask = firstParMostLeft ? ~0 : 0;

		for (int i = 0; i < crossPoints.size(); ++i) {
			int crossPoint = crossPoints.get(i) - (i == 0 ? 0 : crossPoints.get(i - 1));
			mask <<= crossPoint;
			if (!firstParMostLeft) {
				mask |= (int) (Math.pow(2, crossPoint) - 1);
			}
			firstParMostLeft = !firstParMostLeft;
		}
		mask <<= (Integer.SIZE - crossPoints.get(crossPoints.size() - 1));
		if (!firstParMostLeft) {
			mask |= (int) (Math.pow(2, (Integer.SIZE - crossPoints.get(crossPoints.size() - 1))) - 1);
		}

		int child = (parent1 & mask) | (parent2 & ~mask);

//		System.out.println(Integer.toBinaryString(mask));
//		System.out.println(Integer.toBinaryString(child));
		return child;
	}

}
