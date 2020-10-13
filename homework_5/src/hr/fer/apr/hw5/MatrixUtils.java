package hr.fer.apr.hw5;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

public class MatrixUtils {

	/**
	 * Reads matrix from file. Number of rows in file is based on the number of rows
	 * in file, and number of columns is based on number of space-separated value in
	 * the first row.
	 *
	 * @param matrixFile file to be loaded
	 * @return Matrix representation of matrix
	 * @throws IOException
	 */
	public static Matrix readFromFile(final File matrixFile) throws IOException {
		if (!matrixFile.canRead()) {
			return null;
		}
		LineNumberReader reader = new LineNumberReader(new FileReader(matrixFile));

		Matrix matrix = null;
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] elements = line.split("\\s");
				if (matrix == null) {
					matrix = new Matrix(1, elements.length);
				} else {
					matrix.expandMatrix(1, 0);
				}
				for (int i = 0; i < elements.length; ++i) {
					if (i < matrix.getColumns()) {
						matrix.setElementValue(new Coordinate(reader.getLineNumber() - 1, i),
								Double.parseDouble(elements[i]));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		reader.close();
		return matrix;
	}

	public static File writeToFile(final Matrix matrix, final File file) throws IOException {
		FileWriter writer = new FileWriter(file, false);
		writer.write(matrix.toString());
		writer.flush();
		writer.close();

		return file;
	}

	public static void writeToConsole(final Matrix matrix) {
		System.out.println(matrix.toString());
	}

	public static Matrix identityMatrix(final int dimension) throws Exception {
		Matrix matrix = new Matrix(dimension, dimension);
		for (int i = 0; i < matrix.getRows(); ++i) {
			for (int j = 0; j < matrix.getColumns(); ++j) {
				matrix.setElementValue(new Coordinate(i, j), (i == j ? 1d : 0d));
			}
		}
		return matrix;
	}

}
