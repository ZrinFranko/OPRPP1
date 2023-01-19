package hr.fer.oprpp1.hwo5.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UtilTest {
	
	@Test
	public void testEmptyHexToByte() {
		assertEquals(0,Util.hextobyte("").length);
	}
	
	@Test
	public void testOddStringHexToByte() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("0001A"));
	}
	
	@Test
	public void testHexToByte() {
		String temp = "01aE22";
		byte[] expected = new byte[] {1,-82,34};
		byte[] actual = Util.hextobyte(temp);
		if(expected.length != actual.length) fail();
		for(int i = 0; i < actual.length ; i++) {
			if(actual[i] != expected[i]) fail();
		}
	}
	
	@Test
	public void testEmptyByteToHex() {
		assertEquals(0,Util.bytetohex(new byte[] {}).length());
	}
	
	@Test
	public void testByteToHex() {
		byte[] temp = new byte[] {1,-82,34};
		String expected = "01ae22";
		String actual = Util.bytetohex(temp);
		assertEquals(expected,actual);
	}

}
