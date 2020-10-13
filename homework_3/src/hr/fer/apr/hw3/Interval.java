package hr.fer.apr.hw3;

public class Interval {

	private Double start, end;

	/**
	 * @param start
	 * @param end
	 */
	public Interval(final Double start, final Double end) {
		super();
		this.start = start;
		this.end = end;
	}

	public Double getStart() {
		return start;
	}

	public void setStart(final Double start) {
		this.start = start;
	}

	public Double getEnd() {
		return end;
	}

	public void setEnd(final Double end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "[" + start + ", " + end + "]";
	}

}
