/**
 *
 */
package hr.fer.apr.hw2.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.apr.hw2.calculation.Calculation;
import hr.fer.apr.hw2.calculation.CoordinateAxisSearch;
import hr.fer.apr.hw2.calculation.HookeJeeves;
import hr.fer.apr.hw2.calculation.NelderMeadSimplex;
import hr.fer.apr.hw2.function.Function;
import hr.fer.apr.hw2.function.Function1;
import hr.fer.apr.hw2.function.Function2;
import hr.fer.apr.hw2.function.Function3;
import hr.fer.apr.hw2.function.Function4;
import hr.fer.apr.hw2.function.Function5;

/**
 * @author Sebastian
 *
 */
public class Tasks {

	private static class Function31D extends Function3 {
		@Override
		public Double calculateValue(final double... x) {
			return Math.pow((x[0] - 3), 2);
		}
	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		try {
			task1();
			task2();
			task3();
			task4();
			task5();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void task1() {
		Double startPoint = Double.valueOf(10d);

		Function casF = new Function31D();
		CoordinateAxisSearch cas = new CoordinateAxisSearch(casF);
		double[] casPoint = cas.calculate(new Double[] { startPoint });

		Function hjF = new Function31D();
		HookeJeeves hj = new HookeJeeves(hjF);
		double[] hjPoint = hj.calculate(new Double[] { startPoint });

		Function nmsF = new Function31D();
		NelderMeadSimplex nms = new NelderMeadSimplex(nmsF);
		double[] nmsPoint = nms.calculate(new Double[] { startPoint });

		System.out.println(Calculation.pointToString("CoordinateAxisSearch minimum", casPoint));
		System.out.println("CoordinateAxisSearch evaluations: " + casF.getEvaluationsCount());
		System.out.println();

		System.out.println(Calculation.pointToString("HookeJeeves minimum", hjPoint));
		System.out.println("HookeJeeves evaluations: " + hjF.getEvaluationsCount());
		System.out.println();

		System.out.println(Calculation.pointToString("NelderMeadSimplex minimum", nmsPoint));
		System.out.println("NelderMeadSimplex evaluations: " + nmsF.getEvaluationsCount());
	}

	private static void task2() throws Exception {

		List<double[]> points = new ArrayList<>();
		List<Long> evals = new ArrayList<>();

		task2Helper(new Double[] { -1.9d, 2d }, new Function1(), points, evals);
		task2Helper(new Double[] { 0.1d, 0.3d }, new Function2(), points, evals);
		task2Helper(new Double[] { 0d, 0d, 0d, 0d, 0d }, new Function3(), points, evals);
		task2Helper(new Double[] { 5.1d, 1.1d }, new Function4(), points, evals);

		System.out.println("F\tHJ point\tHJ eval\t NMS point\tNMS eval");
		for (int i = 0; i < 4; ++i) {
			System.out.println(i + 1 + "\t" + Calculation.pointToString("", points.get((i * 2) + 0)) + "\t"
					+ evals.get((i * 2) + 0) + "\t" + Calculation.pointToString("", points.get((i * 2) + 1)) + "\t"
					+ evals.get((i * 2) + 1));
		}
	}

	private static void task3() throws Exception {
		List<double[]> points = new ArrayList<>();
		List<Long> evals = new ArrayList<>();
		task3Helper(new Double[] { 5d, 5d }, new Function4(), points, evals);

		System.out.println("F\tHJ point\tHJ eval\t NMS point\tNMS eval");
		int i = 0;
		System.out.println(i + 1 + "\t" + Calculation.pointToString("", points.get((i * 2) + 0)) + "\t"
				+ evals.get((i * 2) + 0) + "\t" + Calculation.pointToString("", points.get((i * 2) + 1)) + "\t"
				+ evals.get((i * 2) + 1));

	}

	private static void task4() throws Exception {
		List<double[]> points = new ArrayList<>();
		List<Long> evals = new ArrayList<>();
		task4Helper(new Double[] { 0.5d, 0.5d }, new Function1(), points, evals, 1d);
		task4Helper(new Double[] { 0.5d, 0.5d }, new Function1(), points, evals, 5d);
		task4Helper(new Double[] { 0.5d, 0.5d }, new Function1(), points, evals, 10d);
		task4Helper(new Double[] { 0.5d, 0.5d }, new Function1(), points, evals, 15d);
		task4Helper(new Double[] { 0.5d, 0.5d }, new Function1(), points, evals, 20d);

		task4Helper(new Double[] { 20d, 20d }, new Function1(), points, evals, 1d);
		task4Helper(new Double[] { 20d, 20d }, new Function1(), points, evals, 5d);
		task4Helper(new Double[] { 20d, 20d }, new Function1(), points, evals, 10d);
		task4Helper(new Double[] { 20d, 20d }, new Function1(), points, evals, 15d);
		task4Helper(new Double[] { 20d, 20d }, new Function1(), points, evals, 20d);

		System.out.println("i\t NMS point\tNMS eval");
		for (int i = 0; i < 5; ++i) {
			System.out.println(i + 1 + "\t" + Calculation.pointToString("", points.get(i)) + "\t" + evals.get(i));
		}
		System.out.println();
		for (int i = 5; i < 10; ++i) {
			System.out.println(i + 1 + "\t" + Calculation.pointToString("", points.get(i)) + "\t" + evals.get(i));
		}
	}

	private static void task5() {
		Random rand = new Random();

		Function hjF = new Function5();
		HookeJeeves hj = new HookeJeeves(hjF);

//		NelderMeadSimplex hj = new NelderMeadSimplex(hjF, 1e-2, 0.1d, 1d, 0.5d, 2d, 0.05d);
		int evals = 1000;
		int globalMinCount = 0;
		for (int i = 0; i < evals; ++i) {
			Double[] point = new Double[] { (rand.nextDouble() * 50d) - 50d, (rand.nextDouble() * 50d) - 50d };

			double[] hjPoint = hj.calculate(point);
			Double val = hjF.calculateValue(hjPoint);
			if (val < 1e-4) {
				++globalMinCount;
			}
		}

		System.out.println(((Double.valueOf(globalMinCount) / Double.valueOf(evals)) * 100) + "%");

	}

	private static void task2Helper(final Double[] point, final Function function, final List<double[]> points,
			final List<Long> evals) throws Exception {

		Function hjF = function.getClass().getDeclaredConstructor().newInstance();
		HookeJeeves hj = new HookeJeeves(hjF);
		double[] hjPoint = hj.calculate(point);
		points.add(hjPoint);
		evals.add(hjF.getEvaluationsCount());

		Function nmsF = function.getClass().getDeclaredConstructor().newInstance();
		NelderMeadSimplex nms = new NelderMeadSimplex(nmsF, 1e-1, 1d, 1d, 0.5d, 2d, 0.5d);
		double[] nmsPoint = nms.calculate(point);
		points.add(nmsPoint);
		evals.add(nmsF.getEvaluationsCount());
	}

	private static void task3Helper(final Double[] point, final Function function, final List<double[]> points,
			final List<Long> evals) throws Exception {

		Function hjF = function.getClass().getDeclaredConstructor().newInstance();
		HookeJeeves hj = new HookeJeeves(hjF);
		double[] hjPoint = hj.calculate(point);
		points.add(hjPoint);
		evals.add(hjF.getEvaluationsCount());

		Function nmsF = function.getClass().getDeclaredConstructor().newInstance();
		NelderMeadSimplex nms = new NelderMeadSimplex(nmsF);
		double[] nmsPoint = nms.calculate(point);
		points.add(nmsPoint);
		evals.add(nmsF.getEvaluationsCount());
	}

	private static void task4Helper(final Double[] point, final Function function, final List<double[]> points,
			final List<Long> evals, final Double shift) throws Exception {

		Function nmsF = function.getClass().getDeclaredConstructor().newInstance();
		NelderMeadSimplex nms = new NelderMeadSimplex(nmsF, 1e-1, shift, 1d, 0.5d, 2d, 0.5d);
		double[] nmsPoint = nms.calculate(point);
		points.add(nmsPoint);
		evals.add(nmsF.getEvaluationsCount());
	}
}
