package hr.fer.zemris.java.gui.charts;

import java.util.Objects;

public class XYValue {
	
	private int x;
	private int y;
	
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XYValue other = (XYValue) obj;
		return x == other.x && y == other.y;
	}
	

}
