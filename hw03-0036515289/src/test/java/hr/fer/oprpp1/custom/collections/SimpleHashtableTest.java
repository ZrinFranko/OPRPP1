package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimpleHashtableTest {
	
	@Test
	public void testDefaultContructor() {
		SimpleHashtable<String,Integer> collection = new SimpleHashtable<>();
		assertEquals(0,collection.size());
	}
	
	@Test
	public void testInitialCapacityConstructor() {
		SimpleHashtable<String,Integer> collection = new SimpleHashtable<String,Integer>(5);
		assertEquals(0,collection.size());
	}
	
	@Test
	public void testInitialCapacityConstructorExceptionHandle() {
		assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<>(0));
	}
	
	@Test
	public void testPutNewValue() {
		SimpleHashtable<String,Integer> collection = new SimpleHashtable<String,Integer>(5);
		collection.put("cipele", 2);
		assertEquals(1,collection.size());
	}
	
	@Test
	public void testPutOldValue() {
		SimpleHashtable<String,Integer> collection = new SimpleHashtable<String,Integer>(5);
		collection.put("cipele", 2);
		collection.put("novine", 6);
		assertEquals(6,collection.put("novine", 3));
	}
	
	@Test
	public void testPutNullPointer() {
		SimpleHashtable<String,Integer> collection = new SimpleHashtable<String,Integer>(5);
		collection.put("cipele", 2);
		collection.put("novine", 6);
		assertThrows(NullPointerException.class , () -> collection.put(null, null));
	}
	
	@Test
	public void testGetNullValue() {
		SimpleHashtable<String,Integer> collection = new SimpleHashtable<String,Integer>(5);
		collection.put("cipele", 2);
		collection.put("novine", 6);
		assertEquals(null,collection.get(null));
	}
	
	@Test
	public void testGetExistingValue() {
		SimpleHashtable<String,Integer> collection = new SimpleHashtable<String,Integer>(5);
		collection.put("cipele", 2);
		collection.put("novine", 6);
		assertEquals(2,collection.get("cipele"));
	}
	
	@Test
	public void testGetNonExistingValue() {
		SimpleHashtable<String,Integer> collection = new SimpleHashtable<String,Integer>(5);
		collection.put("cipele", 2);
		collection.put("novine", 6);
		assertEquals(null,collection.get("papiga"));
	}
	
	@Test
	public void testSize() {
		SimpleHashtable<String,Integer> collection = new SimpleHashtable<String,Integer>(5);
		collection.put("cipele", 2);
		collection.put("novine", 6);
		assertEquals(2,collection.size());
	}
	
	@Test
	public void testContainsKeyExistingKey() {
		SimpleHashtable<String,Integer> collection = new SimpleHashtable<String,Integer>(5);
		collection.put("cipele", 2);
		collection.put("novine", 6);
		assertTrue(collection.containsKey("novine"));
	}
	
	@Test
	public void testContainsKeyNullHandle() {
		SimpleHashtable<String,Integer> collection = new SimpleHashtable<String,Integer>(5);
		collection.put("cipele", 2);
		collection.put("novine", 6);
		assertThrows(NullPointerException.class , () -> collection.containsKey(null));
	}
	
	@Test
	public void testContainsKeyNonExistingKey() {
		SimpleHashtable<String,Integer> collection = new SimpleHashtable<String,Integer>(5);
		collection.put("cipele", 2);
		collection.put("novine", 6);
		assertFalse(collection.containsKey("papiga"));
	}
	
	@Test
	public void testContainsValueExistingValue() {
		SimpleHashtable<String,Integer> collection = new SimpleHashtable<String,Integer>(5);
		collection.put("cipele", 2);
		collection.put("novine", 6);
		assertTrue(collection.containsValue(2));
	}
	
	@Test
	public void testContainsValueNonExistingValue() {
		SimpleHashtable<String,Integer> collection = new SimpleHashtable<String,Integer>(5);
		collection.put("cipele", 2);
		collection.put("novine", 6);
		assertFalse(collection.containsValue(4));
	}
	
	@Test
	public void testRemoveExistingEntry() {
		SimpleHashtable<String,Integer> collection = new SimpleHashtable<String,Integer>(5);
		collection.put("cipele", 2);
		collection.put("novine", 6);
		assertEquals(6,collection.remove("novine"));
	}

	@Test
	public void testRemoveNonExistingEntry() {
		SimpleHashtable<String,Integer> collection = new SimpleHashtable<String,Integer>(5);
		collection.put("cipele", 2);
		collection.put("novine", 6);
		assertEquals(null,collection.remove("papiga"));
	}
	
	@Test
	public void testIsEmpty() {
		SimpleHashtable<String,Integer> collection = new SimpleHashtable<String,Integer>(5);
		assertTrue(collection.isEmpty());
	}
	
	@Test
	public void testToString() {
		SimpleHashtable<String,Integer> collection = new SimpleHashtable<String,Integer>(5);
		collection.put("cipele", 2);
		collection.put("novine", 6);
		String expected = "[cipele=2, novine=6]";
		assertEquals(expected,collection.toString());
	}
	
	@Test
	public void testClear() {
		SimpleHashtable<String,Integer> collection = new SimpleHashtable<String,Integer>(5);
		collection.put("cipele", 2);
		collection.put("novine", 6);
		collection.clear();
		assertTrue(collection.isEmpty());
	}
}
