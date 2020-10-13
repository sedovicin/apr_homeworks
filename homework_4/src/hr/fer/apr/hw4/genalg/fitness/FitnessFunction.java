package hr.fer.apr.hw4.genalg.fitness;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.apr.hw4.function.Function;

public class FitnessFunction {

	private final Function f;
	private final Double lowerLimit;
	private final Double upperLimit;

	private int bestUnitIndex;
	private int worstUnitIndex;

	public FitnessFunction(final Function f, final Double lowerLimit, final Double upperLimit) {
		this.f = f;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}

	public Map<Integer, Double> createFitnessMap(final List<double[]> population) {

		Double best = Double.POSITIVE_INFINITY, worst = Double.NEGATIVE_INFINITY;
		Map<Integer, Double> values = new HashMap<>();

		for (int i = 0; i < population.size(); ++i) {
			double[] unit = population.get(i);
			Double value = f.calculate(unit);
			values.put(i, value);
			if (value.compareTo(best) < 0) {
				best = value;
				bestUnitIndex = i;
			}
			if (value.compareTo(worst) > 0) {
				worst = value;
				worstUnitIndex = i;
			}
		}

		Map<Integer, Double> fitnessMap = new HashMap<>();
		for (int i = 0; i < population.size(); ++i) {
			double fitness = lowerLimit + ((upperLimit - lowerLimit) * ((values.get(i) - worst) / (best - worst)));
			fitnessMap.put(i, fitness);
		}

		return fitnessMap;
	}

	public int getBestUnitIndex() {
		return bestUnitIndex;
	}

	public int getWorstUnitIndex() {
		return worstUnitIndex;
	}

}
