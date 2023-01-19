package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class RCPositionTest {

	
	@Test
	public void rcPositionRowRestrictionTest() {
		assertThrows(CalcLayoutException.class, () -> RCPosition.parse("70,3"));
	}
	
	@Test
	public void rcPositionColumnRestrictionTest() {
		assertThrows(CalcLayoutException.class, () -> RCPosition.parse("2,90"));
	}
	
	@Test
	public void rcPositionNegativeRowRestrictionTest() {
		assertThrows(CalcLayoutException.class, () -> RCPosition.parse("-1,3"));
	}
	
	@Test
	public void rcPositionNegativeColumnRestrictionTest() {
		assertThrows(CalcLayoutException.class, () -> RCPosition.parse("1,-3"));
	}
	
	@Test
	public void firstRowColumnTest() {
		assertThrows(CalcLayoutException.class, () -> RCPosition.parse("1,4"));
	}
	
}
