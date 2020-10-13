package hr.fer.apr.hw4.genalg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import hr.fer.apr.hw4.function.Function;
import hr.fer.apr.hw4.genalg.fitness.FitnessFunction;

public abstract class GeneticAlgorithm implements IGeneticAlgorithm, Runnable {

	protected List<double[]> population;
	protected Integer dimension;
	protected final Integer populationSize;

	protected final Double lowerLimit;
	protected final Double upperLimit;

	protected Function f;

	protected FitnessFunction fitnessFunction;
	protected Map<Integer, Double> fitnessMap;
	protected int bestUnitIndex;

	protected Double mutationProbability;

	protected Long evaluationCount;

	private static Random rand = null;

	public GeneticAlgorithm(final Function f, final Integer populationSize, final Integer dimension,
			final Double lowerLimit, final Double upperLimit, final Double mutationProbability,
			final Long evaluationCount) {
		this.f = f;
		this.populationSize = populationSize;
		this.dimension = dimension;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
		this.mutationProbability = mutationProbability;
		this.evaluationCount = evaluationCount;
		fitnessFunction = new FitnessFunction(f, lowerLimit, upperLimit);

		rand = getRandomGenerator();
	}

	public static Random getRandomGenerator() {
		if (rand == null) {
			rand = new Random();
		}
		return rand;
	}

	@Override
	public void init() {
		population = createNewRandomPopulation(populationSize, dimension, lowerLimit, upperLimit);
	}

	public List<double[]> createNewRandomPopulation(final Integer populationSize, final Integer dimension,
			final Double lowerLimit, final Double upperLimit) {
		List<double[]> population = new ArrayList<>();
		Random rand = GeneticAlgorithm.getRandomGenerator();
		for (long i = 0; i < populationSize; ++i) {
			double[] unit = new double[dimension];
			for (int j = 0; j < dimension; ++j) {
				unit[j] = lowerLimit + ((upperLimit - lowerLimit) * rand.nextDouble());
			}
			population.add(unit);
		}
		return population;
	}

	@Override
	public void run() {
		init();
		do {
			evaluate();
			select();
			cross();
			mutate();
		} while (stoppingCriteriaUnsatisfied());
		evaluate();
	}

	protected boolean stoppingCriteriaUnsatisfied() {
		return f.getEvaluationsCount() < evaluationCount;
	}

}
