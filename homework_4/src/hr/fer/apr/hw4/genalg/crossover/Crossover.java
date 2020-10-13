package hr.fer.apr.hw4.genalg.crossover;

public interface Crossover {

	public double createChild(final double parent1, final double parent2);

	public double[] createChildUnit(final double[] parent1, final double[] parent2);
}
