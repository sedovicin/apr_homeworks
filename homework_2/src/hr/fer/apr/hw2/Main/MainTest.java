package hr.fer.apr.hw2.Main;

import hr.fer.apr.hw2.calculation.Calculation;
import hr.fer.apr.hw2.calculation.HookeJeeves;
import hr.fer.apr.hw2.function.Function;

public class MainTest {

	public static void main(final String[] args) {
		Function f = new Function() {

			@Override
			public Double calculateValue(final double... x) {
				// TODO Auto-generated method stub
				return (x[0] * x[0]) + (x[1] * x[1]) + (x[2] * x[2]);
			}
		};

		HookeJeeves hj = new HookeJeeves(f, 1d, 0.25d);
		System.out.println(Calculation.pointToString("nultocka", hj.calculate(new Double[] { 2d, 3d, 4d })));

	}

}
