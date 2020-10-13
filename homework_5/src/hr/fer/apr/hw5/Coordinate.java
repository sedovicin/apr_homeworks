package hr.fer.apr.hw5;

import java.util.Objects;

public class Coordinate {
	private Integer columnIndex;
	private Integer rowIndex;

	public Coordinate(final Integer rowIndex, final Integer columnIndex) {
		if ((rowIndex < 0) || (columnIndex < 0)) {
			throw new IllegalArgumentException("Indexes must be greater than 0!");
		}
		this.columnIndex = columnIndex;
		this.rowIndex = rowIndex;
	}

	public Integer getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(final Integer columnIndex) {
		this.columnIndex = columnIndex;
	}

	public Integer getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(final Integer rowIndex) {
		this.rowIndex = rowIndex;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Coordinate)) {
			return false;
		}
		Coordinate second = (Coordinate) obj;

		if (this.getColumnIndex().equals(second.getColumnIndex()) && this.getRowIndex().equals(second.getRowIndex())) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getColumnIndex(), this.getRowIndex());
	}
}