package hr.fer.apr.hw4.genalg.representation;

import java.util.ArrayList;
import java.util.List;

public class BinaryRepresentation {

	private final double power;
	private final double lowerLimit;
	private final double upperLimit;
//	private final double precision;

	/**
	 * @param power
	 * @param lowerLimit
	 * @param upperLimit
	 * @param precision
	 */
	public BinaryRepresentation(final Double lowerLimit, final Double upperLimit, final Double precision) {
		super();
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;

		power = Math.ceil((Math.log10(Math.floor(1d + ((upperLimit - lowerLimit) * Math.pow(10d, precision)))))
				/ (Math.log10(2d)));
//		System.out.println("power: " + power);
	}

	public int toBinary(final double value) {
		return Double.valueOf((((value - lowerLimit) * (Math.pow(2, power) - 1d)) / (upperLimit - lowerLimit)))
				.intValue();
	}

	public int[] toBinary(final double[] valueArray) {
		int[] binary = new int[valueArray.length];
		for (int i = 0; i < valueArray.length; ++i) {
			binary[i] = toBinary(valueArray[i]);
		}
		return binary;
	}

	public List<int[]> toBinary(final List<double[]> valueArrayList) {
		List<int[]> binary = new ArrayList<>();
		for (double[] valueArray : valueArrayList) {
			binary.add(toBinary(valueArray));
		}
		return binary;
	}

	public double toReal(final int value) {
		return lowerLimit
				+ ((Integer.valueOf(value).doubleValue() * (upperLimit - lowerLimit)) / (Math.pow(2, power) - 1d));
	}

	public double[] toReal(final int[] valueArray) {
		double[] real = new double[valueArray.length];
		for (int i = 0; i < valueArray.length; ++i) {
			real[i] = toReal(valueArray[i]);
		}
		return real;
	}

	public List<double[]> toReal(final List<int[]> valueArrayList) {
		List<double[]> real = new ArrayList<>();
		for (int[] valueArray : valueArrayList) {
			real.add(toReal(valueArray));
		}
		return real;
	}
}
