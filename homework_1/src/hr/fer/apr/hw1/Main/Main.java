/**
 *
 */
package hr.fer.apr.hw1.Main;

import java.io.File;

import hr.fer.apr.hw1.Matrix;
import hr.fer.apr.hw1.MatrixUtils;

/**
 * @author Sebastian
 *
 */
public class Main {

	public static void main(final String[] args) {
//		String path = System.getProperty("user.dir");
		try {

			Matrix matrix = MatrixUtils.readFromFile(new File(args[0]));
			System.out.println("Matrix from file:\n" + matrix);

			matrix.setThreshold(1e-6);

			Matrix vectorB = MatrixUtils.readFromFile(new File(args[1]));
			System.out.println("Vector B from file:\n" + vectorB);

			try {
				System.out.println("Solving matrix * x = b using LU decomposition...");

				System.out.println("Creating LU matrix...");
				Matrix matrixLU = matrix.decomposeLU();
				System.out.println("LU matrix:");
				System.out.println(matrixLU);

				System.out.println("Doing forward supstitution...");
				Matrix vectorY = matrixLU.forwardSupstitution(vectorB);
				System.out.println("Vector Y:");
				System.out.println(vectorY);

				System.out.println("Doing backward supstitution...");
				Matrix vectorX = matrixLU.backwardSupstitution(vectorY);
				System.out.println("Vector X (solution):");
				System.out.println(vectorX);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				System.out.println("Solving matrix * x = b using LUP decomposition...");

				System.out.println("Creating LUP matrix...");
				Matrix matrixLUP = matrix.decomposeLUP();
				System.out.println("LUP matrix:");
				System.out.println(matrixLUP);

				System.out.println("Doing forward supstitution...");
				Matrix vectorY = matrixLUP.forwardSupstitution(vectorB);
				System.out.println("Vector Y:");
				System.out.println(vectorY);

				System.out.println("Doing backward supstitution...");
				Matrix vectorX = matrixLUP.backwardSupstitution(vectorY);
				System.out.println("Vector X (solution):");
				System.out.println(vectorX);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
