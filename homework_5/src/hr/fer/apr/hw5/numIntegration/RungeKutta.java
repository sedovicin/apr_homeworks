package hr.fer.apr.hw5.numIntegration;

import hr.fer.apr.hw5.Matrix;

public class RungeKutta extends NumericalIntegration {

	public RungeKutta(final Double integrationPeriod, final Matrix A, final Matrix B, final Matrix initialX,
			final Double maxTime) {
		super(integrationPeriod, A, B, initialX, maxTime);
	}

	@Override
	protected Matrix calculateNext(final Matrix current) throws Exception {
		Matrix m1 = calculateGeneralFunction(current, null);
		Matrix m2 = calculateGeneralFunction(current.add(m1.multiply(integrationPeriod / 2)), null);
		Matrix m3 = calculateGeneralFunction(current.add(m2.multiply(integrationPeriod / 2)), null);
		Matrix m4 = calculateGeneralFunction(current.add(m3.multiply(integrationPeriod)), null);

		return current.add(m1.add(m2.multiply(2d)).add(m3.multiply(2d)).add(m4).multiply(integrationPeriod / 6));
	}

	private Matrix calculateGeneralFunction(final Matrix x, final Double time) {
		return A.multiply(x).add(B);
	}
}
