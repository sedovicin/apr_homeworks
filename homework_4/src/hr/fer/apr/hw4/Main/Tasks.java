package hr.fer.apr.hw4.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.apr.hw4.function.Function;
import hr.fer.apr.hw4.function.Function1;
import hr.fer.apr.hw4.function.Function3;
import hr.fer.apr.hw4.function.Function6;
import hr.fer.apr.hw4.function.Function7;
import hr.fer.apr.hw4.genalg.TournamentEliminationGA;
import hr.fer.apr.hw4.genalg.crossover.Crossover;
import hr.fer.apr.hw4.genalg.crossover.binary.MultiPointBinaryCrossover;
import hr.fer.apr.hw4.genalg.crossover.binary.UniformBinaryCrossover;
import hr.fer.apr.hw4.genalg.crossover.floating.ArithmeticFloatingCrossover;
import hr.fer.apr.hw4.genalg.crossover.floating.HeuristicFloatingCrossover;

public class Tasks {

	public static void main(final String[] args) {
		try {
			System.out.println(
					"-----------------------------------------------------TASK 1-----------------------------------------------");
			task1();
			System.out.println(
					"-----------------------------------------------------TASK 2-----------------------------------------------");
			task2();
			System.out.println(
					"-----------------------------------------------------TASK 3-----------------------------------------------");
			task3();
			System.out.println(
					"-----------------------------------------------------TASK 4-----------------------------------------------");
			task4();
			System.out.println(
					"-----------------------------------------------------TASK 5-----------------------------------------------");
			task5();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void task1() throws Exception {
		TournamentEliminationGA.setContestants(3);
		List<Class<? extends Function>> fs = new ArrayList<>(
				Arrays.asList(Function1.class, Function3.class, Function6.class, Function7.class));
		int[] dims = new int[] { 2, 5, 2, 2 };
		List<Class<? extends Crossover>> crosses = new ArrayList<>(
				Arrays.asList(MultiPointBinaryCrossover.class, HeuristicFloatingCrossover.class));

		int popSize = 50;
		double mutationProbab = 0.01d;

		for (int i = 0; i < fs.size(); ++i) {
			for (Class<? extends Crossover> cross : crosses) {
				TournamentEliminationGA ga = new TournamentEliminationGA(
						fs.get(i).getDeclaredConstructor().newInstance(), popSize, dims[i], -50d, 150d, 3d, cross,
						mutationProbab, 10000000l, true);

				ga.run();
				System.out.println("Function: " + fs.get(i).getSimpleName() + ", cross: " + cross.getSimpleName());
				ga.toOutput();
			}
		}

	}

	private static void task2() {
		TournamentEliminationGA.setContestants(3);
		int[] dimensionality = new int[] { 1, 3, 6, 10 };

		int populationSize = 50;
		double lowerLimit = -50d;
		double upperLimit = 150d;
		double binaryPrecision = 5d;
		double mutationProbability = 0.01d;
		Class<? extends Crossover> cross = HeuristicFloatingCrossover.class;
		long evaluationCount = 10000000l;

		System.out.println("FUNKCIJA 6");
		for (int i = 0; i < dimensionality.length; ++i) {
			TournamentEliminationGA ga = new TournamentEliminationGA(new Function6(), populationSize, dimensionality[i],
					lowerLimit, upperLimit, binaryPrecision, cross, mutationProbability, evaluationCount, true);
			ga.run();
			System.out.println("Dimenzionalnost: " + dimensionality[i]);
			ga.toOutput();
		}
		System.out.println();
		System.out.println("FUNKCIJA 7");
		for (int i = 0; i < dimensionality.length; ++i) {
			TournamentEliminationGA ga = new TournamentEliminationGA(new Function7(), populationSize, dimensionality[i],
					lowerLimit, upperLimit, binaryPrecision, cross, mutationProbability, evaluationCount, true);
			ga.run();
			System.out.println("Dimenzionalnost: " + dimensionality[i]);
			ga.toOutput();
		}

	}

	private static void task3() {
		TournamentEliminationGA.setContestants(3);
		int[] dimensionality = new int[] { 3, 6 };

		int populationSize = 1000;
		double lowerLimit = -50d;
		double upperLimit = 150d;
		double binaryPrecision = 4d;
		double mutationProbability = 0.01d;
		List<Class<? extends Crossover>> crosses = new ArrayList<>(
				Arrays.asList(UniformBinaryCrossover.class, ArithmeticFloatingCrossover.class));

		long evaluationCount = 200000l;

		System.out.println("FUNKCIJA 6");
		System.out.println("B3, F3, B6, F6");
		for (int j = 0; j < 50; ++j) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < dimensionality.length; ++i) {
				for (Class<? extends Crossover> cross : crosses) {
					TournamentEliminationGA ga = new TournamentEliminationGA(new Function6(), populationSize,
							dimensionality[i], lowerLimit, upperLimit, binaryPrecision, cross, mutationProbability,
							evaluationCount, true);

					ga.run();
					sb.append(ga.getBestValue());
					sb.append(",");
				}
			}
			sb.deleteCharAt(sb.length() - 1);
			System.out.println(sb.toString());
		}
		System.out.println();

		System.out.println("FUNKCIJA 7");
		System.out.println("B3, F3, B6, F6");
		for (int j = 0; j < 50; ++j) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < dimensionality.length; ++i) {
				for (Class<? extends Crossover> cross : crosses) {
					TournamentEliminationGA ga = new TournamentEliminationGA(new Function7(), populationSize,
							dimensionality[i], lowerLimit, upperLimit, binaryPrecision, cross, mutationProbability,
							evaluationCount, true);

					ga.run();
					sb.append(ga.getBestValue());
					sb.append(",");
				}
			}
			sb.deleteCharAt(sb.length() - 1);
			System.out.println(sb.toString());
		}
	}

	private static void task4() {
		TournamentEliminationGA.setContestants(3);

		int[] populationSize = new int[] { 30, 50, 100, 200 };
		double lowerLimit = -50d;
		double upperLimit = 150d;
		double binaryPrecision = 4d;
		double[] mutationProbability = new double[] { 0.1d, 0.3d, 0.6d, 0.9d };

		Class<? extends Crossover> cross = HeuristicFloatingCrossover.class;
		long evaluationCount = 100000l;

		StringBuilder sb1 = new StringBuilder();
		for (int i = 0; i < populationSize.length; ++i) {
			for (int j = 0; j < mutationProbability.length; ++j) {
				sb1.append(populationSize[i] + " " + mutationProbability[j] + ",");
			}
		}
		sb1.deleteCharAt(sb1.length() - 1);
		System.out.println(sb1.toString());

		for (int k = 0; k < 50; ++k) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < populationSize.length; ++i) {
				for (int j = 0; j < mutationProbability.length; ++j) {
					TournamentEliminationGA ga = new TournamentEliminationGA(new Function6(), populationSize[i], 2,
							lowerLimit, upperLimit, binaryPrecision, cross, mutationProbability[j], evaluationCount,
							true);

					ga.run();
					sb.append(ga.getBestValue() * 200);
					sb.append(",");
				}
			}
			sb.deleteCharAt(sb.length() - 1);
			System.out.println(sb.toString());
		}
	}

	private static void task5() {

		int populationSize = 100;
		double lowerLimit = -50d;
		double upperLimit = 150d;
		double binaryPrecision = 5d;
		double mutationProbability = 0.01d;

		int[] contestants = new int[] { 3, 5, 10, 20 };

		Class<? extends Crossover> cross = HeuristicFloatingCrossover.class;
		long evaluationCount = 200000l;

		StringBuilder sb1 = new StringBuilder();
		for (int j = 0; j < contestants.length; ++j) {
			sb1.append(contestants[j] + ",");
		}

		sb1.deleteCharAt(sb1.length() - 1);
		System.out.println(sb1.toString());

		for (int k = 0; k < 50; ++k) {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < contestants.length; ++j) {
				TournamentEliminationGA ga = new TournamentEliminationGA(new Function7(), populationSize, 3, lowerLimit,
						upperLimit, binaryPrecision, cross, mutationProbability, evaluationCount, true);
				TournamentEliminationGA.setContestants(contestants[j]);

				ga.run();
				sb.append(ga.getBestValue());
				sb.append(",");
			}

			sb.deleteCharAt(sb.length() - 1);
			System.out.println(sb.toString());
		}
	}

}
