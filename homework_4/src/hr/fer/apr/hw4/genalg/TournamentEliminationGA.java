package hr.fer.apr.hw4.genalg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

import hr.fer.apr.hw4.function.Function;
import hr.fer.apr.hw4.genalg.crossover.Crossover;
import hr.fer.apr.hw4.genalg.crossover.binary.BinaryCrossover;
import hr.fer.apr.hw4.genalg.crossover.floating.ArithmeticFloatingCrossover;
import hr.fer.apr.hw4.genalg.crossover.floating.FloatingCrossover;
import hr.fer.apr.hw4.genalg.crossover.floating.HeuristicFloatingCrossover;
import hr.fer.apr.hw4.genalg.mutation.BetterSimpleBinaryMutation;
import hr.fer.apr.hw4.genalg.mutation.GaussianFloatingMutation;
import hr.fer.apr.hw4.genalg.mutation.Mutation;
import hr.fer.apr.hw4.genalg.representation.BinaryRepresentation;

public class TournamentEliminationGA extends GeneticAlgorithm {

	private static int numOfContestants = 3;

	private int worstUnitIndex;
	private List<Integer> otherPickedUnitsIndexes;

	private BinaryRepresentation binaryRepresentation;

	private final Crossover cross;

	private boolean writeToOutput;

	private final boolean valueUnderPrecision;

	/**
	 *
	 * @param f
	 * @param populationSize
	 * @param dimension
	 * @param lowerLimit
	 * @param upperLimit
	 * @param binaryPrecision
	 * @param cross
	 * @param mutationProbability
	 * @param evaluationCount
	 */
	public TournamentEliminationGA(final Function f, final Integer populationSize, final Integer dimension,
			final Double lowerLimit, final Double upperLimit, final Double binaryPrecision,
			final Class<? extends Crossover> cross, final Double mutationProbability, final Long evaluationCount,
			final boolean valueUnderPrecision) {
		super(f, populationSize, dimension, lowerLimit, upperLimit, mutationProbability, evaluationCount);

		if (binaryPrecision != null) {
			binaryRepresentation = new BinaryRepresentation(lowerLimit, upperLimit, binaryPrecision);
		} else {
			binaryRepresentation = null;
		}
		this.cross = getCrossover(cross);

		this.valueUnderPrecision = valueUnderPrecision;
	}

	private Crossover getCrossover(final Class<? extends Crossover> cross) {
		try {
			if (BinaryCrossover.class.isAssignableFrom(cross) && (binaryRepresentation != null)) {
				return cross.getDeclaredConstructor(BinaryRepresentation.class).newInstance(binaryRepresentation);
			} else if (FloatingCrossover.class.isAssignableFrom(cross)) {

				if (HeuristicFloatingCrossover.class.isAssignableFrom(cross)) {
					return cross.getDeclaredConstructor(Function.class, double.class, double.class, Long.class)
							.newInstance(f, lowerLimit, upperLimit, evaluationCount);

				} else if (ArithmeticFloatingCrossover.class.isAssignableFrom(cross)) {
					return cross.getDeclaredConstructor().newInstance();

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void evaluate() {
		fitnessMap = fitnessFunction.createFitnessMap(population);
		int newBestUnitIndex = fitnessFunction.getBestUnitIndex();
		if (newBestUnitIndex != bestUnitIndex) {
			if (writeToOutput) {
				createReport(newBestUnitIndex);
			}
			bestUnitIndex = newBestUnitIndex;

		}
	}

	private void createReport(final int newBestUnitIndex) {
		System.out.println("Evaluation count: " + f.getEvaluationsCount());
		double[] bestUnit = population.get(newBestUnitIndex);
		System.out.println("Best current unit: " + Arrays.toString(bestUnit));
		System.out.println("Value at best current unit: " + f.calculate(bestUnit));
		System.out.println();
	}

	public void toOutput() {
		createReport(bestUnitIndex);
//		for (double[] unit : population) {
//			System.out.println(Arrays.toString(unit));
//		}
	}

	public Double getBestFitness() {
		return fitnessMap.get(fitnessFunction.getBestUnitIndex());
	}

	public Double getBestValue() {
		return f.calculate(population.get(bestUnitIndex));
	}

	@Override
	public void select() {
		worstUnitIndex = 0;
		Double worstUnit = Double.POSITIVE_INFINITY;
		List<Integer> pickedUnitsIndexes = new ArrayList<>();
		Random rand = getRandomGenerator();
		while (pickedUnitsIndexes.size() < numOfContestants) {
			Integer picked = rand.nextInt(populationSize);
			if (!(pickedUnitsIndexes.contains(picked))) {
				pickedUnitsIndexes.add(picked);
			}
		}
		for (Integer pickedUnit : pickedUnitsIndexes) {
			Double fitness = fitnessMap.get(pickedUnit);
			if (fitness.compareTo(worstUnit) < 0) {
				worstUnit = fitness;
				worstUnitIndex = pickedUnit;
			}
		}
		pickedUnitsIndexes.remove(Integer.valueOf(worstUnitIndex));
		otherPickedUnitsIndexes = pickedUnitsIndexes;

//		for (double[] unit : population) {
//			System.out.println(Arrays.toString(unit));
//		}
//		System.out.println();
	}

	@Override
	public void cross() {
		Map<Integer, Double> m = new HashMap<>();
		for (Entry<Integer, Double> entry : fitnessMap.entrySet()) {
			if (otherPickedUnitsIndexes.contains(entry.getKey())) {
				m.put(entry.getKey(), entry.getValue());
			}
		}

		List<Entry<Integer, Double>> parents = m.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(2).collect(Collectors.toList());

//		System.out.println(Arrays.toString(population.get(parents.get(0).getKey())));
//		System.out.println(Arrays.toString(population.get(parents.get(1).getKey())));
//		System.out.println(Arrays.toString(population.get(worstUnitIndex)));
//		System.out.println();
		population.set(worstUnitIndex, cross.createChildUnit(population.get(parents.get(0).getKey()),
				population.get(parents.get(1).getKey())));
	}

	@Override
	public void mutate() {
		Random rand = getRandomGenerator();
		double probability = rand.nextDouble();
		if (probability < mutationProbability) {
			Mutation mutation;
			if (cross instanceof BinaryCrossover) {
				mutation = new BetterSimpleBinaryMutation(binaryRepresentation);
			} else {
				mutation = new GaussianFloatingMutation();
			}
//			System.out.println(mutation.getClass().getSimpleName() + worstUnitIndex);

			double[] mutated = mutation.mutate(population.get(worstUnitIndex));
			population.set(worstUnitIndex, mutated);
		}
	}

	public void run(final boolean output) {
		writeToOutput = output;
		run();
	}

	@Override
	protected boolean stoppingCriteriaUnsatisfied() {
		if (valueUnderPrecision) {
			return super.stoppingCriteriaUnsatisfied() && (f.calculate(population.get(bestUnitIndex)) > 1e-6);
		} else {
			return super.stoppingCriteriaUnsatisfied();
		}
	}

	public static void setContestants(final int number) {
		numOfContestants = number;
	}
}
