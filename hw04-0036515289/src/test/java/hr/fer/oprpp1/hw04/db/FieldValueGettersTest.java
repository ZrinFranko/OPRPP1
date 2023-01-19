package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FieldValueGettersTest {
	
	StudentRecord testRecord = new StudentRecord("0000000020","Hibner","Sonja",5);
	
	@Test
	public void testJmbagGetter() {
		assertEquals("0000000020",FieldValueGetters.JMBAG.get(testRecord));
	}
	
	@Test
	public void testFirstNameGetter() {
		assertEquals("Sonja",FieldValueGetters.FIRST_NAME.get(testRecord));
	}
	
	@Test
	public void testLastNameGetter() {
		assertEquals("Hibner",FieldValueGetters.LAST_NAME.get(testRecord));
	}
	

}
