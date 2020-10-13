package hr.fer.apr.hw5.numIntegration;

import hr.fer.apr.hw5.Matrix;
import hr.fer.apr.hw5.MatrixUtils;

public class Trapezium extends NumericalIntegration {

	public Trapezium(final Double integrationPeriod, final Matrix A, final Matrix B, final Matrix initialX,
			final Double maxTime) {
		super(integrationPeriod, A, B, initialX, maxTime);
	}

	@Override
	protected Matrix calculateNext(final Matrix current) throws Exception {
		Matrix currentPart = current
				.add(A.multiply(current).add(B).add(B).multiply(Double.valueOf(integrationPeriod / 2)));
		Matrix inverse = (MatrixUtils.identityMatrix(A.getColumns())
				.subtract(A.multiply(Double.valueOf(integrationPeriod / 2)))).inverse();

		return inverse.multiply(currentPart);
	}
}
