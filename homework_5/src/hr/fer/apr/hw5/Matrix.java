package hr.fer.apr.hw5;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Matrix {

	/**
	 * Since double is not precise, we need to use a threshold which acts as a
	 * barrier between treating two doubles equal or not.
	 */
	private double threshold = 1e-12;

	private int rows;
	private int columns;
	private final Map<Coordinate, Double> matrixValues;

	private int pivotArray[] = null;

	/**
	 * Creates a new empty matrix
	 *
	 * @param rows    amount of rows
	 * @param columns amount of columns
	 * @throws Exception if either of the matrix dimensions is a negative number
	 */
	/**
	 *
	 * @param rows
	 * @param columns
	 *
	 */
	public Matrix(final int rows, final int columns) throws Exception {
		if ((rows < 0) || (columns < 0)) {
			throw new Exception("Matrix dimensions must be greater or equal to zero!");
		}

		this.rows = rows;
		this.columns = columns;
		matrixValues = new HashMap<>();
	}

	/**
	 * Creates a duplicate of the given matrix
	 *
	 * @param matrix matrix to be duplicated
	 */
	public Matrix(final Matrix matrix) {
		this.rows = matrix.rows;
		this.columns = matrix.columns;
		matrixValues = new HashMap<>(matrix.matrixValues);
		this.pivotArray = matrix.pivotArray;
		this.threshold = matrix.threshold;
	}

	/**
	 * Creates a new matrix filled with given values. Amount of values must be equal
	 * to given matrix dimensions (product of {@link rows} and {@link columns}).
	 *
	 * @param rows    Amount of rows of new matrix
	 * @param columns Amount of columns of new matrix
	 * @param values  Values to put in new matrix
	 * @throws Exception if either of the matrix dimensions is a negative number; if
	 *                   the amount of values does not match the given matrix
	 *                   dimensions
	 */
	public Matrix(final int rows, final int columns, final double... values) throws Exception {
		this(rows, columns);

		if (values.length != (rows * columns)) {
			throw new Exception("Amount of values does not match the given matrix dimensions! (" + rows + " * "
					+ columns + " != " + values.length + ")");
		}

		int valueIndex = 0;
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < columns; ++j) {
				this.matrixValues.put(new Coordinate(i, j), Double.valueOf(values[valueIndex++]));
			}
		}
	}

	/**
	 * Sets the value of threshold, or double imperfection tolerance
	 *
	 * @param threshold
	 */
	public void setThreshold(final double threshold) {
		this.threshold = threshold;
	}

	/**
	 * Returns the value of threshold, or double imperfection tolerance
	 *
	 * @param threshold
	 */
	public double getThreshold() {
		return threshold;
	}

	/**
	 * Fetches value that is located at given coordinates in matrix.
	 *
	 * @param elementCoordinate coordinates of the requested field
	 * @return Double representation of value
	 */
	public Double getElementValue(final Coordinate elementCoordinate) {
		return matrixValues.get(elementCoordinate);
	}

	/**
	 * Sets value of an matrix element. If the value exists, replaces it with
	 * specified value.
	 *
	 * @param elementCoordinate coordinates of the element
	 * @param value             value of the element
	 * @throws Exception if the given coordinates point to a field outside of matrix
	 */
	public void setElementValue(final Coordinate elementCoordinate, final Double value) throws Exception {
		if ((elementCoordinate.getColumnIndex().compareTo(columns) >= 0)
				|| (elementCoordinate.getRowIndex().compareTo(rows) >= 0)) {
			throw new Exception("Coordinates point to a field outside of matrix");
		}
		matrixValues.put(elementCoordinate, value);
	}

	/**
	 * Removes value from the given element
	 *
	 * @param elementCoordinate element to be removed
	 * @return true if the value existed and not equal to null, false otherwise
	 */
	public boolean removeElementValue(final Coordinate elementCoordinate) {
		Double removedValue = matrixValues.remove(elementCoordinate);
		if (removedValue == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Adds additional space to the matrix
	 *
	 * @param additionalRows    amount of rows to be added
	 * @param additionalColumns amount of columns to be added
	 */
	public void expandMatrix(final int additionalRows, final int additionalColumns) {
		rows += additionalRows;
		columns += additionalColumns;
	}

	/**
	 * Getter for amount of rows in matrix
	 *
	 * @return
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Getter for amount of columns in matrix
	 *
	 * @return
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * Checks if this matrix is square
	 *
	 * @return true if is square, false otherwise
	 */
	public boolean isSquare() {
		return rows == columns;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < columns; ++j) {
				sb.append(matrixValues.get(new Coordinate(i, j)));
				if (j != (columns - 1)) {
					sb.append(' ');
				}
			}
			if (i != (rows - 1)) {
				sb.append(System.lineSeparator());
			}
		}
		return sb.toString();
	}

	/**
	 * Does mathematical addition (sum) of the two matrices, but the result is put
	 * in a new matrix object.
	 *
	 * @param anotherMatrix matrix to be added to this
	 * @return new matrix object that is equal to matrices's sum result
	 */
	public Matrix add(final Matrix anotherMatrix) {
		Matrix result = new Matrix(this);

		result.addToThis(anotherMatrix);

		return result;
	}

	/**
	 * Does mathematical addition (sum) of the two matrices.
	 *
	 * @param anotherMatrix matrix to be added to this one.
	 */
	public void addToThis(final Matrix anotherMatrix) {
		for (Map.Entry<Coordinate, Double> entry : this.matrixValues.entrySet()) {
			double res = entry.getValue() + anotherMatrix.matrixValues.get(entry.getKey());
			if (compare(res, 0.0d) == 0) {
				res = 0.0d;
			}
			entry.setValue(res);
		}
	}

	/**
	 * Does mathematical subtraction of the two matrices, but the result is put in a
	 * new matrix object.
	 *
	 * @param anotherMatrix matrix that subtracts this one.
	 * @return new matrix object that is equal to matrices's subtraction result
	 */
	public Matrix subtract(final Matrix anotherMatrix) {
		Matrix result = new Matrix(this);

		result.subtractFromThis(anotherMatrix);

		return result;
	}

	/**
	 * Does mathematical subtraction of the two matrices.
	 *
	 * @param anotherMatrix matrix that subtracts this one.
	 */
	public void subtractFromThis(final Matrix anotherMatrix) {
		for (Map.Entry<Coordinate, Double> entry : this.matrixValues.entrySet()) {
			double res = entry.getValue() - anotherMatrix.matrixValues.get(entry.getKey());
			if (compare(res, 0.0d) == 0) {
				res = 0.0d;
			}
			entry.setValue(res);
		}
	}

	/**
	 * Does matrix scalar multiplication
	 *
	 * @param scalar scalar that multiplies this matrix
	 * @return new Matrix that is equal to matrices's scalar multiplication result
	 */
	public Matrix multiply(final Double scalar) {
		Matrix result = new Matrix(this);

		for (Map.Entry<Coordinate, Double> entry : result.matrixValues.entrySet()) {
			double res = Double.valueOf(entry.getValue().doubleValue() * scalar.doubleValue());
			if (compare(res, 0.0d) == 0) {
				res = 0.0d;
			}
			entry.setValue(res);
		}

		return result;
	}

	/**
	 * Does matrix scalar division
	 *
	 * @param scalar scalar that divides this matrix
	 * @return new Matrix that is equal to matrices's scalar division result
	 */
	public Matrix divide(final Double scalar) throws Exception {
		if (compare(scalar.doubleValue(), 0.0d) == 0) {
			throw new Exception("Division by zero is not allowed.");
		}
		Matrix result = new Matrix(this);

		for (Map.Entry<Coordinate, Double> entry : result.matrixValues.entrySet()) {
			double res = Double.valueOf(entry.getValue().doubleValue() / scalar.doubleValue());
			if (compare(res, 0.0d) == 0) {
				res = 0.0d;
			}
			entry.setValue(res);
		}

		return result;
	}

	/**
	 * Does matrix multiplication. Amount of columns of this matrix and amount of
	 * rows of the second matrix must be the same number.
	 *
	 * @param anotherMatrix second matrix to be multiplied with
	 * @return new matrix that represents multiplication result of this and
	 *         anotherMatrix matrix
	 */
	public Matrix multiply(final Matrix anotherMatrix) {
		if (this.columns != anotherMatrix.rows) {
			throw new IllegalArgumentException(
					"Amount of columns of the first matrix and amount of rows of the second matrix must be the same number!");

		}
		Matrix result = null;
		try {
			result = new Matrix(this.rows, anotherMatrix.columns);
		} catch (Exception e) {
			// this should never happen
			e.printStackTrace();
		}

		for (int i = 0; i < this.rows; ++i) {
			for (int k = 0; k < anotherMatrix.columns; ++k) {
				double newValue = 0;
				for (int j = 0; j < this.columns; ++j) {
					newValue = newValue + (this.matrixValues.get(new Coordinate(i, j)).doubleValue()
							* anotherMatrix.matrixValues.get(new Coordinate(j, k)).doubleValue());
					if (compare(newValue, 0.0d) == 0) {
						newValue = 0.0d;
					}
				}
				result.matrixValues.put(new Coordinate(i, k), Double.valueOf(newValue));
			}
		}

		return result;
	}

	/**
	 * Does matrix transposition.
	 *
	 * @return new matrix that represents transposed (this) matrix
	 */
	public Matrix transpose() {
		Matrix result = null;
		try {
			result = new Matrix(this.columns, this.rows);
		} catch (Exception e) {
			// this should never happen
			e.printStackTrace();
		}
		for (int i = 0; i < this.rows; ++i) {
			for (int j = 0; j < this.columns; ++j) {
				result.matrixValues.put(new Coordinate(j, i), this.matrixValues.get(new Coordinate(i, j)));
			}
		}

		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Matrix)) {
			return false;
		}
		Matrix second = (Matrix) obj;

		if ((this.getRows() != second.getRows()) || (this.getColumns() != second.getColumns())) {
			return false;
		}

		for (int i = 0; i < this.rows; ++i) {
			for (int j = 0; j < this.columns; ++j) {
				if (compare(this.matrixValues.get(new Coordinate(i, j)).doubleValue(),
						second.matrixValues.get(new Coordinate(i, j)).doubleValue()) != 0) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.matrixValues.values());
	}

	/**
	 * Does the LU decomposition of this matrix. Both L and U matrices are stored in
	 * a single matrix, where L is located in lower triangular part and U in upper
	 * triangular part
	 *
	 * @return new matrix that represents the requested operation.
	 * @throws Exception if the matrix is not square
	 */
	public Matrix decomposeLU() throws Exception {
		Matrix result = new Matrix(this);

		result.decomposeLUThis();

		return result;
	}

	/**
	 * Does the LU decomposition of this matrix and stores the result in this
	 * matrix. Both L and U matrices are stored in a single matrix, where L is
	 * located in lower triangular part and U in upper triangular part
	 *
	 * @throws Exception if the matrix is not square, or if division by zero happens
	 */
	public void decomposeLUThis() throws Exception {
		if (!(this.isSquare())) {
			throw new Exception("Matrix must be squared in order to do LU decomposition.");
		}
		for (int i = 0; i < (rows - 1); ++i) {
			for (int j = i + 1; j < rows; ++j) {
				double divider = matrixValues.get(new Coordinate(i, i)).doubleValue();
				if ((divider - 0.0d) < threshold) {
					throw new Exception("During decomposition, element at diagonal (more precisely, (" + i + ", " + i
							+ ")) was zero. Division by zero is not allowed.");
				}

				double res = Double.valueOf(matrixValues.get(new Coordinate(j, i)).doubleValue() / divider);
				if (compare(res, 0.0d) == 0) {
					res = 0.0d;
				}
				matrixValues.put(new Coordinate(j, i), res);
				for (int k = i + 1; k < rows; ++k) {

					double res1 = Double.valueOf(matrixValues.get(new Coordinate(j, k)).doubleValue()
							- (matrixValues.get(new Coordinate(j, i)).doubleValue()
									* matrixValues.get(new Coordinate(i, k)).doubleValue()));
					if (compare(res1, 0.0d) == 0) {
						res1 = 0.0d;
					}
					matrixValues.put(new Coordinate(j, k), res1);
				}
			}
		}
	}

	/**
	 * Does the forward supstitution of the matrix.
	 *
	 * @param vectorB matrix that represents vector B. It is not changed during
	 *                calculation.
	 * @return Matrix new matrix, representation of the y vector
	 * @throws Exception if the vector B is not vector (num_of_columns > 1) or if
	 *                   this matrix is not square
	 */
	public Matrix forwardSupstitution(final Matrix vectorB) throws Exception {
		if (vectorB.getColumns() != 1) {
			throw new Exception("vector B must have only one column");
		}

		if (!(this.isSquare())) {
			throw new Exception("Matrix must be squared in order to do forward supstitution.");
		}

		Matrix result;

		if (pivotArray != null) {
			result = new Matrix(vectorB.rows, vectorB.columns);
			for (int i = 0; i < vectorB.rows; ++i) {
				for (int j = 0; j < vectorB.columns; ++j) {
					result.matrixValues.put(new Coordinate(i, j),
							vectorB.matrixValues.get(new Coordinate(pivotArray[i], j)));

				}
			}
		} else {
			result = new Matrix(vectorB);
		}

		for (int i = 0; i < (rows - 1); ++i) {
			for (int j = i + 1; j < rows; ++j) {
				double res = Double.valueOf(result.matrixValues.get(new Coordinate(j, 0)).doubleValue()
						- (matrixValues.get(new Coordinate(j, i)).doubleValue()
								* result.matrixValues.get(new Coordinate(i, 0)).doubleValue()));
				if (compare(res, 0.0d) == 0) {
					res = 0.0d;
				}
				result.matrixValues.put(new Coordinate(j, 0), res);
			}
		}

		return result;
	}

	/**
	 * Does the backward supstitution of the matrix.
	 *
	 * @param vectorY matrix that represents vector Y. It is not changed during
	 *                calculation.
	 * @return Matrix new matrix, representation of the y vector
	 * @throws Exception if the vector Y is not vector (num_of_columns > 1), if this
	 *                   matrix is not square or if division by zero happens
	 */
	public Matrix backwardSupstitution(final Matrix vectorY) throws Exception {
		if (vectorY.getColumns() != 1) {
			throw new Exception("vector Y must have only one column");
		}

		if (!(this.isSquare())) {
			throw new Exception("Matrix must be squared in order to do backward supstitution.");
		}

		Matrix result = new Matrix(vectorY);

		for (int i = rows - 1; i >= 0; --i) {
			double divider = matrixValues.get(new Coordinate(i, i)).doubleValue();
			if (compare(divider, 0.0d) == 0) {
				throw new Exception("During backward supstitution, element at diagonal (more precisely, (" + i + ", "
						+ i + ")) is zero. Division by zero is not allowed.");
			}

			double res = Double.valueOf(result.matrixValues.get(new Coordinate(i, 0)).doubleValue() / divider);
			if (compare(res, 0.0d) == 0) {
				res = 0.0d;
			}
			result.matrixValues.put(new Coordinate(i, 0), res);
			for (int j = 0; j < i; ++j) {
				double res1 = Double.valueOf(result.matrixValues.get(new Coordinate(j, 0)).doubleValue()
						- (matrixValues.get(new Coordinate(j, i)).doubleValue()
								* result.matrixValues.get(new Coordinate(i, 0)).doubleValue()));
				if (compare(res1, 0.0d) == 0) {
					res1 = 0.0d;
				}
				result.matrixValues.put(new Coordinate(j, 0), res1);
			}
		}

		return result;
	}

	/**
	 * Does the LUP decomposition of this matrix. Both L and U matrices are stored
	 * in a single matrix, where L is located in lower triangular part and U in
	 * upper triangular part. Matrix is properly permuted due to pivoting.
	 *
	 * @return new matrix that represents the requested operation.
	 * @throws Exception if the matrix is not square, or when division by zero
	 *                   happens
	 */
	public Matrix decomposeLUP() throws Exception {
		Matrix result = new Matrix(this);

		result.decomposeLUPThis();

		Matrix permutedMatrix = new Matrix(result.rows, result.columns);
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < columns; ++j) {
				permutedMatrix.matrixValues.put(new Coordinate(i, j),
						result.matrixValues.get(new Coordinate(result.pivotArray[i], j)));
			}
		}
		permutedMatrix.pivotArray = result.pivotArray;

		return permutedMatrix;
	}

	/**
	 * Does the LUP decomposition of this matrix and stores the result in this
	 * matrix. Both L and U matrices are stored in a single matrix, where L is
	 * located in lower triangular part and U in upper triangular part. Matrix is
	 * properly permuted due to pivoting.
	 *
	 * @throws Exception if the matrix is not square
	 */
	public void decomposeLUPThis() throws Exception {
		if (!(this.isSquare())) {
			throw new Exception("Matrix must be squared in order to do LUP decomposition.");
		}
		pivotArray = new int[rows];
		for (int i = 0; i < rows; ++i) {
			pivotArray[i] = i;
		}
		int pivotElement;

		for (int i = 0; i < (rows - 1); ++i) {
			pivotElement = i;
			for (int j = i + 1; j < rows; ++j) {
				if ((Math.abs(matrixValues.get(new Coordinate(pivotArray[j], i)).doubleValue()) > Math
						.abs(matrixValues.get(new Coordinate(pivotArray[pivotElement], i)).doubleValue()))) {
					pivotElement = j;
				}
			}
			if (pivotArray[i] != pivotArray[pivotElement]) {
				pivotArray[i] = pivotArray[i] ^ pivotArray[pivotElement];
				pivotArray[pivotElement] = pivotArray[i] ^ pivotArray[pivotElement];
				pivotArray[i] = pivotArray[i] ^ pivotArray[pivotElement];
			}
			for (int j = i + 1; j < rows; ++j) {
				double divider = matrixValues.get(new Coordinate(pivotArray[i], i)).doubleValue();
				if (compare(divider, 0.0d) == 0) {
					throw new Exception("Element at (" + i + ", " + i + ") is zero. Division by zero is not allowed.");
				}

				double res = Double.valueOf(matrixValues.get(new Coordinate(pivotArray[j], i)).doubleValue() / divider);
				if (compare(res, 0.0d) == 0) {
					res = 0.0d;
				}
				matrixValues.put(new Coordinate(pivotArray[j], i), res);

				for (int k = i + 1; k < rows; ++k) {
					double res1 = Double.valueOf(matrixValues.get(new Coordinate(pivotArray[j], k)).doubleValue()
							- (matrixValues.get(new Coordinate(pivotArray[j], i)).doubleValue()
									* matrixValues.get(new Coordinate(pivotArray[i], k)).doubleValue()));
					if (compare(res1, 0.0d) == 0) {
						res1 = 0.0d;
					}
					matrixValues.put(new Coordinate(pivotArray[j], k), res1);
				}

			}
		}

	}

	public Matrix inverse() throws Exception {
		Matrix matrixLUP = decomposeLUP();

		Matrix identityVectorZero = new Matrix(matrixLUP.rows, 1);
		for (int i = 0; i < matrixLUP.rows; ++i) {
			identityVectorZero.matrixValues.put(new Coordinate(i, 0), 0d);
		}

		Matrix result = new Matrix(matrixLUP.rows, matrixLUP.columns);

		for (int i = 0; i < matrixLUP.columns; ++i) {
			Matrix identityI = new Matrix(identityVectorZero);
			identityI.matrixValues.put(new Coordinate(i, 0), 1d);

			Matrix y = matrixLUP.forwardSupstitution(identityI);
			Matrix x = matrixLUP.backwardSupstitution(y);

			for (int j = 0; j < x.rows; ++j) {
				result.matrixValues.put(new Coordinate(j, i), x.getElementValue(new Coordinate(j, 0)));
			}
		}

		return result;
	}

	/**
	 * Compares two doubles the same way as {@link Double#compare(double, double)}
	 * with an addition of treating two numbers as equal if absolute value of their
	 * subtraction result is lower than {@link #threshold}
	 *
	 * @param first
	 * @param second
	 * @return 0 if equal, -1 if first < second, 1 if first > second
	 */
	private int compare(final double first, final double second) {
		double result = first - second;
		if (Math.abs(result) < threshold) {
			return 0;
		}
		return Double.compare(first, second);
	}

}
