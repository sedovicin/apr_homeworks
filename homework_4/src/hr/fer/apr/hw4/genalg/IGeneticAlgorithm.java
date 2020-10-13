package hr.fer.apr.hw4.genalg;

public interface IGeneticAlgorithm {

	public void init();

	public void evaluate();

	public void select();

	public void cross();

	public void mutate();
}
