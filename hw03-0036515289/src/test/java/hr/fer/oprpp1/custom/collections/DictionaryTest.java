package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DictionaryTest {
	
	@Test
	public void testEmptyIfEmpty() {
		Dictionary<String,Integer> dictionary = new Dictionary<>();
		assertTrue(dictionary.isEmpty());
	}
	
	@Test
	public void testEmptyIfNotEmpty() {
		Dictionary<String,Integer> dictionary = new Dictionary<>();
		dictionary.put("cipele", 1);
		assertFalse(dictionary.isEmpty());
	}
	
	@Test
	public void testSize() {
		Dictionary<String,Integer> dictionary = new Dictionary<>();
		dictionary.put("cipele", 1);
		dictionary.put("torte", 6);
		assertEquals(2,dictionary.size());
	}
	
	@Test
	public void testClear() {
		Dictionary<String,Integer> dictionary = new Dictionary<>();
		dictionary.put("cipele", 1);
		dictionary.put("torte", 6);
		dictionary.clear();
		assertTrue(dictionary.isEmpty());
	}
	
	@Test
	public void testPutNullKey() {
		Dictionary<String,Integer> dictionary = new Dictionary<>();
		assertThrows(NullPointerException.class, () -> dictionary.put(null, 3));
	}
	
	@Test
	public void testPutWithNewValue() {
		Dictionary<String,Integer> dictionary = new Dictionary<>();
		if(!dictionary.isEmpty()) fail();
		assertEquals(null,dictionary.put("cipele", 1));
	}
	
	@Test
	public void testPutWithOldKey() {
		Dictionary<String,Integer> dictionary = new Dictionary<>();
		if(!dictionary.isEmpty()) fail();
		dictionary.put("cipele", 1);
		assertEquals(1,dictionary.put("cipele", 5));
	}
	
	@Test
	public void testGetNullKey() {
		Dictionary<String,Integer> dictionary = new Dictionary<>();
		assertThrows(NullPointerException.class, () -> dictionary.get(null));
	}
	
	@Test
	public void testGetExistingValue() {
		Dictionary<String,Integer> dictionary = new Dictionary<>();
		dictionary.put("cipele", 1);
		dictionary.put("torte", 6);
		assertEquals(6,dictionary.get("torte"));
	}
	
	@Test
	public void testGetNewValue() {
		Dictionary<String,Integer> dictionary = new Dictionary<>();
		dictionary.put("cipele", 1);
		dictionary.put("torte", 6);
		assertEquals(null,dictionary.get("igracke"));
	}
	
	@Test
	public void testRemoveNullKey() {
		Dictionary<String,Integer> dictionary = new Dictionary<>();
		assertThrows(NullPointerException.class, () -> dictionary.remove(null));
	}
	
	@Test
	public void testRemoveExistingValue() {
		Dictionary<String,Integer> dictionary = new Dictionary<>();
		dictionary.put("cipele", 1);
		dictionary.put("torte", 6);
		assertEquals(6,dictionary.remove("torte"));
	}
	
	@Test
	public void testRemoveNewValue() {
		Dictionary<String,Integer> dictionary = new Dictionary<>();
		dictionary.put("cipele", 1);
		dictionary.put("torte", 6);
		assertEquals(null,dictionary.remove("igracke"));
	}

}
