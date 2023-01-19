package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

public class RCPosition {
	
	private int row;
	private int column;
	
	public RCPosition(int row,int column) {
		this.row = row;
		this.column = column;
	}
	
	public static RCPosition parse(String line) {
		if(line == null) throw new NullPointerException("Input cannot be null!");
		line = line.replace(" ", "");
		String[] el = line.split(",");
		if(el.length != 2) throw new IllegalArgumentException("Input must define row and column separated by ',' !");
		if(line.contains("-")) throw new CalcLayoutException("Coordinates of rows and columns can only be positive!");
		
		int row = 0;
		int column = 0;
		
		try {
			row = Integer.parseInt(el[0]);
			column = Integer.parseInt(el[1]);
		}catch(NumberFormatException e) {
			System.out.println("Position of a component can only be a number!");
		}
		
		if(row < 1 || row > 5)
			throw new CalcLayoutException("Invalid row coordinate (can only be between 1 and 5)!");
		if(row == 1) {
			if(column > 1 && column < 6)
				throw new CalcLayoutException("For the first row only positions 1,6, and 7 are avaliable!");
		}
		if(column < 1 || column > 7)
			throw new CalcLayoutException("Invalid column coordinate (can only be between 1 and 7)!");
		
		return new RCPosition(Integer.parseInt(el[0]),Integer.parseInt(el[1]));
	}

	@Override
	public int hashCode() {
		return Objects.hash(column, row);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		return column == other.column && row == other.row;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

}
