package hr.fer.apr.hw5.numIntegration;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;

import hr.fer.apr.hw5.Coordinate;
import hr.fer.apr.hw5.Matrix;

public abstract class NumericalIntegration {

	private final Double epsilon = 1e-6;

	protected Double integrationPeriod;
	protected Matrix A, B;
	protected Matrix initialX;
	protected Double maxTime;

	private int iterationsCountOutput = 1;

	public NumericalIntegration(final Double integrationPeriod, final Matrix A, final Matrix B, final Matrix initialX,
			final Double maxTime) {
		this.integrationPeriod = integrationPeriod;
		this.A = A;
		this.B = B;
		this.initialX = initialX;
		this.maxTime = maxTime;
	}

	public void calculate() throws Exception {
		File file = new File(this.getClass().getSimpleName() + new Date().getTime() + ".csv");
		file.createNewFile();
		PrintWriter writer = new PrintWriter(file);
		writer.println("t;x1;x2");

		System.out.println(this.getClass().getSimpleName() + " numerical integration with integration period "
				+ integrationPeriod + ":");
		Matrix current = initialX;
		System.out.println("Initial (t = 0):");
		System.out.println(current);
		String line = Double.valueOf(0).toString() + ";" + current.getElementValue(new Coordinate(0, 0)) + ";"
				+ current.getElementValue(new Coordinate(1, 0));
		line = line.replaceAll("\\.", ",");
		writer.println(line);

		int count = 0;

		for (double i = 0d + integrationPeriod; i <= (maxTime + epsilon); i += integrationPeriod) {
			Matrix next = calculateNext(current);

			line = Double.valueOf(i).toString() + ";" + next.getElementValue(new Coordinate(0, 0)) + ";"
					+ next.getElementValue(new Coordinate(1, 0));
			line = line.replaceAll("\\.", ",");
			writer.println(line);

			if (++count >= iterationsCountOutput) {
				System.out.println("t = " + i);
				System.out.println(next);
				System.out.println();
				count = 0;
			}

			current = next;
		}

		writer.flush();
		writer.close();

	}

	protected abstract Matrix calculateNext(Matrix current) throws Exception;

	public void setIterationsCountOutput(final int count) {
		iterationsCountOutput = count;
	}

}
