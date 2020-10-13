package hr.fer.apr.hw5.Main;

import java.io.File;

import hr.fer.apr.hw5.Matrix;
import hr.fer.apr.hw5.MatrixUtils;
import hr.fer.apr.hw5.numIntegration.NumericalIntegration;
import hr.fer.apr.hw5.numIntegration.RungeKutta;
import hr.fer.apr.hw5.numIntegration.Trapezium;

public class Tasks {

	public static void main(final String[] args) {
		task1();
		task2();
		task3();
		task4();
	}

	private static void task1() {
		System.out.println("----------------------------TASK 1--------------------------------");
		try {
			Matrix m = new Matrix(3, 3, 1, 2, 3, 4, 5, 6, 7, 8, 9);
			System.out.println(m.inverse());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void task2() {
		System.out.println("----------------------------TASK 2--------------------------------");
		try {
			Matrix m = new Matrix(3, 3, 4, -5, -2, 5, -6, -2, -8, 9, 3);
			System.out.println(m.inverse());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void task3() {
		System.out.println("----------------------------TASK 3--------------------------------");
		try {
			Matrix A = MatrixUtils.readFromFile(new File("task3matrixA.txt"));
			Matrix B = MatrixUtils.readFromFile(new File("task3matrixB.txt"));
			Matrix initialX = MatrixUtils.readFromFile(new File("task3matrixX.txt"));

			NumericalIntegration trapezium = new Trapezium(Double.valueOf(0.1d), A, B, initialX, Double.valueOf(1d));
			trapezium.calculate();
			System.out.println();

			NumericalIntegration rungeKutta = new RungeKutta(Double.valueOf(0.1d), A, B, initialX, Double.valueOf(1d));
			rungeKutta.calculate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void task4() {
		System.out.println("----------------------------TASK 4--------------------------------");
		try {
			Matrix A = MatrixUtils.readFromFile(new File("task4matrixA.txt"));
			Matrix B = MatrixUtils.readFromFile(new File("task4matrixB.txt"));
			Matrix initialX = MatrixUtils.readFromFile(new File("task4matrixX.txt"));

			NumericalIntegration trapezium = new Trapezium(Double.valueOf(0.1d), A, B, initialX, Double.valueOf(1d));
			trapezium.setIterationsCountOutput(1);
			trapezium.calculate();
			System.out.println();

			NumericalIntegration rungeKutta = new RungeKutta(Double.valueOf(0.1d), A, B, initialX, Double.valueOf(1d));
			rungeKutta.setIterationsCountOutput(1);
			rungeKutta.calculate();
			System.out.println();

			NumericalIntegration rungeKutta2 = new RungeKutta(Double.valueOf(0.02d), A, B, initialX,
					Double.valueOf(1d));
			rungeKutta2.setIterationsCountOutput(5);
			rungeKutta2.calculate();
			System.out.println();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
