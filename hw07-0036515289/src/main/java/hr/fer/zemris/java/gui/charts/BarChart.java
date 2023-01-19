package hr.fer.zemris.java.gui.charts;

import java.util.*;

public class BarChart {
	
	private List<XYValue> values;
	private String xOpis;
	private String yOpis;
	private int maxY;
	private int minY;
	private int diffY;
	
	public BarChart(List<XYValue> values, String xOpis, String yOpis, int minY, int maxY, int diffY) {
		values.forEach(v -> {
			if(!(v.getY() >= minY)) throw new IllegalArgumentException("Value of y must be higher than minimum y value");
		});
		this.values = values;
		this.xOpis = xOpis;
		this.yOpis = yOpis;
		if(!(maxY > minY)) throw new IllegalArgumentException("Maximum y value must be hight than minimum y value!");
		this.maxY = maxY;
		if(minY < 0) throw new IllegalArgumentException("Minimum y value must be positive");
		this.minY = minY;
		this.diffY = diffY;
	}

	public List<XYValue> getValues() {
		return values;
	}

	public String getxOpis() {
		return xOpis;
	}

	public String getyOpis() {
		return yOpis;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getMinY() {
		return minY;
	}

	public int getDiffY() {
		return diffY;
	}

}
