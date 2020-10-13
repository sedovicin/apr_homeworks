/**
 *
 */
package hr.fer.apr.hw3.calculation;

import hr.fer.apr.hw3.Interval;
import hr.fer.apr.hw3.function.Function;

/**
 * @author Sebastian
 *
 */
public class GoldenRatio extends Calculation {

	private static final double k = 0.5d * (Math.sqrt(5d) - 1d);
	private Double precision;

	public GoldenRatio(final Function f) {
		super(f);
		precision = Double.valueOf(1e-6);
	}

	public GoldenRatio(final Function f, final Double precision) {
		this(f);
		this.precision = precision;
	}

	public Double calculateMean(final Interval interval) {
		Interval grInterval = calculateInterval(interval, precision);
		Double mean = (grInterval.getStart() + grInterval.getEnd()) / 2;
//		System.out.println("Mean: " + mean);
		return mean;
	}

	public Interval calculateInterval(final Interval interval, final double e) {
		double a = interval.getStart();
		double b = interval.getEnd();
		double c, d;
		double fc, fd;

		c = b - (k * (b - a));
		d = a + (k * (b - a));

		fc = f.calculate(c);
		fd = f.calculate(d);

		while ((b - a) > e) {
//			System.out.println("a: " + a + "; c: " + c + "; d: " + d + "; b: " + b);
//			System.out.println("fc: " + fc + "; fd: " + fd);
//			System.out.println();
			if (fc < fd) {
				b = d;
				d = c;
				c = b - (k * (b - a));
				fd = fc;
				fc = f.calculate(c);
			} else {
				a = c;
				c = d;
				d = a + (k * (b - a));
				fc = fd;
				fd = f.calculate(d);
			}
		}

//		System.out.println("a: " + a + "; c: " + c + "; d: " + d + "; b: " + b);
//		System.out.println("fc: " + fc + "; fd: " + fd);
//		System.out.println("Interval: [" + a + ", " + b + "]");
		return new Interval(a, b);
	}

	public Double calculateMean(final Double point, final Double step) {
		Interval interval = calculateUnimodal(step, point);
//		System.out.println("Unimodal:" + interval);

		return calculateMean(interval);
	}

	public Interval calculateInterval(final Double point, final Double step) {
		Interval interval = calculateUnimodal(step, point);
//		System.out.println("Unimodal:" + interval);

		return calculateInterval(interval, precision);
	}

	public Interval calculateUnimodal(final double h, final double point) {
//		System.out.println("Unimodal:");
		double l = point - h;
		double r = point + h;
		double m = point;
		double fl, fm, fr;
		int step = 1;

		fm = f.calculate(point);
		fl = f.calculate(l);
		fr = f.calculate(r);

		if ((fm < fr) && (fm < fl)) {
//			System.out.println("l: " + l + "; m: " + m + "; r: " + r);
//			System.out.println("fl: " + fl + "; fm: " + fm + "; fr: " + fr);
			return new Interval(l, r);
		} else if (fm > fr) {
			do {
//				System.out.println("l: " + l + "; m: " + m + "; r: " + r);
//				System.out.println("fl: " + fl + "; fm: " + fm + "; fr: " + fr);
				l = m;
				m = r;
				fm = fr;
				r = point + (h * (step *= 2));
				fr = f.calculate(r);
			} while (fm > fr);
		} else {
			do {
//				System.out.println("l: " + l + "; m: " + m + "; r: " + r);
//				System.out.println("fl: " + fl + "; fm: " + fm + "; fr: " + fr);
				r = m;
				m = l;
				fm = fl;
				l = point - (h * (step *= 2));
				fl = f.calculate(l);
			} while (fm > fl);
		}
//		System.out.println("l: " + l + "; m: " + m + "; r: " + r);
//		System.out.println("fl: " + fl + "; fm: " + fm + "; fr: " + fr);
		return new Interval(l, r);
	}
}
