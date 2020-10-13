package hr.fer.apr.hw3.Main;

import java.util.ArrayList;
import java.util.List;

import hr.fer.apr.hw3.calculation.Box;
import hr.fer.apr.hw3.calculation.Calculation;
import hr.fer.apr.hw3.calculation.GradientDescent;
import hr.fer.apr.hw3.calculation.NewtonRaphson;
import hr.fer.apr.hw3.calculation.TUPBOMN;
import hr.fer.apr.hw3.function.Function;
import hr.fer.apr.hw3.function.Function1;
import hr.fer.apr.hw3.function.Function2;
import hr.fer.apr.hw3.function.Function3;
import hr.fer.apr.hw3.function.Function4;
import hr.fer.apr.hw3.limit.ImplicitLimitEquation;
import hr.fer.apr.hw3.limit.ImplicitLimitInequation;
import hr.fer.apr.hw3.limit.Limit;

public class Tasks {

	public static void main(final String[] args) {
		task1();
		task2();
		task3();
		task4();
		task5();
	}

	private static void task1() {
		Function f31 = new Function3();
		GradientDescent gd1 = new GradientDescent(f31);

		double[] minimum1 = gd1.calculate(new Double[] { Double.valueOf(0d), Double.valueOf(0d) }, true);

		System.out.println("Sa odredivanjem optimalnog iznosa:");
		System.out.println(Calculation.pointToString("Minimum", minimum1));
		System.out.println("Vrijednost u minimumu:" + f31.calculateValue(minimum1));
		System.out.println("Poziva funkcije cilja: " + f31.getEvaluationsCount());
		System.out.println("Poziva gradijenta funkcije cilja: " + f31.getGradientCount());
		System.out.println("Poziva hesseove matrice funkcije cilja: " + f31.getHesseCount());
		System.out.println();

		Function f32 = new Function3();
		GradientDescent gd2 = new GradientDescent(f32);

		double[] minimum2 = gd2.calculate(new Double[] { Double.valueOf(0d), Double.valueOf(0d) }, false);

		System.out.println("Bez odredivanja optimalnog iznosa:");
		System.out.println(Calculation.pointToString("Minimum", minimum2));
		System.out.println("Vrijednost u minimumu:" + f32.calculateValue(minimum2));
		System.out.println("Poziva funkcije cilja: " + f32.getEvaluationsCount());
		System.out.println("Poziva gradijenta funkcije cilja: " + f32.getGradientCount());
		System.out.println("Poziva hesseove matrice funkcije cilja: " + f32.getHesseCount());
		System.out.println();
	}

	private static void task2() {
		Function f1 = new Function1();
		GradientDescent gd = new GradientDescent(f1);

		double[] minimum = gd.calculate(new Double[] { Double.valueOf(-1.9d), Double.valueOf(2d) }, true);

		System.out.println("Funkcija 1, gradijentni spust s optimalnim iznosom koraka:");
		System.out.println(Calculation.pointToString("Minimum", minimum));
		System.out.println("Vrijednost u minimumu:" + f1.calculateValue(minimum));
		System.out.println("Poziva funkcije cilja: " + f1.getEvaluationsCount());
		System.out.println("Poziva gradijenta funkcije cilja: " + f1.getGradientCount());
		System.out.println("Poziva hesseove matrice funkcije cilja: " + f1.getHesseCount());
		System.out.println();

		f1 = new Function1();
		NewtonRaphson nr = new NewtonRaphson(f1);

		minimum = nr.calculate(new Double[] { Double.valueOf(-1.9d), Double.valueOf(2d) }, true);

		System.out.println("Funkcija 1, Newton-Raphson s optimalnim iznosom koraka:");
		System.out.println(Calculation.pointToString("Minimum", minimum));
		System.out.println("Vrijednost u minimumu:" + f1.calculateValue(minimum));
		System.out.println("Poziva funkcije cilja: " + f1.getEvaluationsCount());
		System.out.println("Poziva gradijenta funkcije cilja: " + f1.getGradientCount());
		System.out.println("Poziva hesseove matrice funkcije cilja: " + f1.getHesseCount());
		System.out.println();

		Function f2 = new Function2();
		gd = new GradientDescent(f2);

		minimum = gd.calculate(new Double[] { Double.valueOf(0.1d), Double.valueOf(0.3d) }, true);

		System.out.println("Funkcija 2, gradijentni spust s optimalnim iznosom koraka:");
		System.out.println(Calculation.pointToString("Minimum", minimum));
		System.out.println("Vrijednost u minimumu:" + f2.calculateValue(minimum));
		System.out.println("Poziva funkcije cilja: " + f2.getEvaluationsCount());
		System.out.println("Poziva gradijenta funkcije cilja: " + f2.getGradientCount());
		System.out.println("Poziva hesseove matrice funkcije cilja: " + f2.getHesseCount());
		System.out.println();

		f2 = new Function2();
		nr = new NewtonRaphson(f2);

		minimum = nr.calculate(new Double[] { Double.valueOf(0.1d), Double.valueOf(0.3d) }, true);

		System.out.println("Funkcija 2, Newton-Raphson s optimalnim iznosom koraka:");
		System.out.println(Calculation.pointToString("Minimum", minimum));
		System.out.println("Vrijednost u minimumu:" + f2.calculateValue(minimum));
		System.out.println("Poziva funkcije cilja: " + f2.getEvaluationsCount());
		System.out.println("Poziva gradijenta funkcije cilja: " + f2.getGradientCount());
		System.out.println("Poziva hesseove matrice funkcije cilja: " + f2.getHesseCount());
		System.out.println();

	}

	private static void task3() {
		List<Limit> limits = new ArrayList<>();
		limits.add(Limits.getTask3ExplicitLimit());
		limits.add(Limits.getTask3ImplicitLimitIneq1());
		limits.add(Limits.getTask3ImplicitLimitIneq2());

		Function f1 = new Function1();
		Box box = new Box(f1);
		box.setLimits(limits);

		double[] minimum = box.calculate(new Double[] { Double.valueOf(-1.9d), Double.valueOf(2d) });

		System.out.println("Funkcija 1, Box:");
		System.out.println(Calculation.pointToString("Minimum", minimum));
		System.out.println("Vrijednost u minimumu:" + f1.calculateValue(minimum));
		System.out.println("Poziva funkcije cilja: " + f1.getEvaluationsCount());
		System.out.println("Poziva gradijenta funkcije cilja: " + f1.getGradientCount());
		System.out.println("Poziva hesseove matrice funkcije cilja: " + f1.getHesseCount());
		System.out.println();

		Function f2 = new Function2();
		box = new Box(f2);
		box.setLimits(limits);

		minimum = box.calculate(new Double[] { Double.valueOf(0.1d), Double.valueOf(0.3d) });

		System.out.println("Funkcija 2, Box:");
		System.out.println(Calculation.pointToString("Minimum", minimum));
		System.out.println("Vrijednost u minimumu:" + f1.calculateValue(minimum));
		System.out.println("Poziva funkcije cilja: " + f2.getEvaluationsCount());
		System.out.println("Poziva gradijenta funkcije cilja: " + f2.getGradientCount());
		System.out.println("Poziva hesseove matrice funkcije cilja: " + f2.getHesseCount());
		System.out.println();

	}

	private static void task4() {
		List<ImplicitLimitInequation> limits = new ArrayList<>();
		limits.add(Limits.getTask3ImplicitLimitIneq1());
		limits.add(Limits.getTask3ImplicitLimitIneq2());

		Function f1 = new Function1();
		TUPBOMN tup = new TUPBOMN(f1);
		tup.setInequationLimits(limits);

		double[] minimum = tup.calculate(new Double[] { Double.valueOf(-1.9d), Double.valueOf(2d) });

		System.out.println("Funkcija 1, TUPBOMN:");
		System.out.println(Calculation.pointToString("Minimum", minimum));
		System.out.println("Vrijednost u minimumu:" + f1.calculateValue(minimum));
		System.out.println("Poziva funkcije cilja: " + f1.getEvaluationsCount());
		System.out.println("Poziva gradijenta funkcije cilja: " + f1.getGradientCount());
		System.out.println("Poziva hesseove matrice funkcije cilja: " + f1.getHesseCount());
		System.out.println();

		Function f2 = new Function2();
		tup = new TUPBOMN(f2);
		tup.setInequationLimits(limits);

		minimum = tup.calculate(new Double[] { Double.valueOf(0.1d), Double.valueOf(0.3d) });

		System.out.println("Funkcija 2, TUPBOMN:");
		System.out.println(Calculation.pointToString("Minimum", minimum));
		System.out.println("Vrijednost u minimumu:" + f2.calculateValue(minimum));
		System.out.println("Poziva funkcije cilja: " + f2.getEvaluationsCount());
		System.out.println("Poziva gradijenta funkcije cilja: " + f2.getGradientCount());
		System.out.println("Poziva hesseove matrice funkcije cilja: " + f2.getHesseCount());
		System.out.println();
	}

	private static void task5() {
		List<ImplicitLimitInequation> limitsIneq = new ArrayList<>();
		limitsIneq.add(Limits.getTask5ImplicitLimitIneq1());
		limitsIneq.add(Limits.getTask5ImplicitLimitIneq2());

		List<ImplicitLimitEquation> limitsEq = new ArrayList<>();
		limitsEq.add(Limits.getTask5ImplicitLimitEq1());

		Function f4 = new Function4();
		TUPBOMN tup = new TUPBOMN(f4);
		tup.setInequationLimits(limitsIneq);
		tup.setEquationLimits(limitsEq);

		double[] minimum = tup.calculate(new Double[] { Double.valueOf(0d), Double.valueOf(0d) });

		System.out.println("Funkcija 4, TUPBOMN, tocka 0,0:");
		System.out.println(Calculation.pointToString("Minimum", minimum));
		System.out.println("Vrijednost u minimumu:" + f4.calculateValue(minimum));
		System.out.println("Poziva funkcije cilja: " + f4.getEvaluationsCount());
		System.out.println("Poziva gradijenta funkcije cilja: " + f4.getGradientCount());
		System.out.println("Poziva hesseove matrice funkcije cilja: " + f4.getHesseCount());
		System.out.println();

		f4 = new Function4();
		tup = new TUPBOMN(f4);
		tup.setInequationLimits(limitsIneq);
		tup.setEquationLimits(limitsEq);

		minimum = tup.calculate(new Double[] { Double.valueOf(5d), Double.valueOf(5d) });

		System.out.println("Funkcija 4, TUPBOMN, tocka 5,5:");
		System.out.println(Calculation.pointToString("Minimum", minimum));
		System.out.println("Vrijednost u minimumu:" + f4.calculateValue(minimum));
		System.out.println("Poziva funkcije cilja: " + f4.getEvaluationsCount());
		System.out.println("Poziva gradijenta funkcije cilja: " + f4.getGradientCount());
		System.out.println("Poziva hesseove matrice funkcije cilja: " + f4.getHesseCount());
		System.out.println();
	}
}
