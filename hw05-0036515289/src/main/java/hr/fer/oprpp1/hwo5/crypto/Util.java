package hr.fer.oprpp1.hwo5.crypto;

public class Util {
	
	/**
	 * Function that transforms a hexcoded string into a byte array. Throws Illegal argumentException if the input string is odd.
	 * @param keyText hexcoded string that needs to be transformed into a byte array
	 * @return a byte array created from the input string
	 */
	
	public static byte[] hextobyte(String keyText) {
		if(keyText.length() % 2 != 0) throw new IllegalArgumentException();
		if(keyText.length() == 0) return new byte[0];
		byte[] temp = new byte[keyText.length()/2];
		for(int i = 0; i < temp.length ; i++) {
			int currentIndex = i * 2;
			temp[i] = (byte)Integer.parseInt(keyText.substring(currentIndex, currentIndex+2), 16);
		}
		return temp;
	}
	
	/**
	 * Function that generates a hexcoded string from a given byte array
	 * @param bytearray a byte array that is used to generate a hexcoded string
	 * @return a string generated from the input byte array
	 */
	public static String bytetohex(byte[] bytearray) {
		if(bytearray.length == 0) return "";
		String temp = "";
		for(byte chunk : bytearray) {
			temp+= String.format("%02x", chunk);
		}
		return temp;
	}

}
