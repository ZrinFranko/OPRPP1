package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {
	
	@Test
	public void testDefaultContructor() {
		Collection collection = new LinkedListIndexedCollection();
		assertEquals(0, collection.size());
	}
	
	@Test 
	public void testOtherCollectionConstructor() {
		Collection other = new LinkedListIndexedCollection();
		other.add("ASP");
		other.add("UTR");
		other.add("ProGi");
		other.add("OOP");
		other.add("MatAn");
		Object[] correct = new Object[] {"ASP","UTR","ProGi","OOP","MatAn"};
		Collection tested = new LinkedListIndexedCollection(other);
		assertArrayEquals(correct,tested.toArray());
	}
	
	@Test
	public void testOtherCollectionNullErrorConstructor() {
		assertThrows(NullPointerException.class, () ->{ new LinkedListIndexedCollection(null);});
	}
	
	@Test
	public void testIsEmptyWhenEmpty() {
		Collection collection = new LinkedListIndexedCollection();
		assertTrue(collection.isEmpty());
	}
	
	@Test
	public void testIsEmptyWhenNotEmpty() {
		Collection collection = new LinkedListIndexedCollection();
		collection.add("ASP");
		assertFalse(collection.isEmpty());
	}
	
	@Test
	public void testSizeCorrect() {
		Collection collection = new LinkedListIndexedCollection();
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertEquals(5,collection.size());
	}
	
	@Test
	public void testAddFunction() {
		Collection collection = new LinkedListIndexedCollection();
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		Object[] correct = new Object[] {"ASP","UTR","ProGi","OOP","MatAn"};
		assertArrayEquals(correct,collection.toArray());
	}
	
	@Test
	public void testAddingNullError() {
		Collection collection = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () ->{ collection.add(null);});
	}
	
	@Test
	public void testContainsStoredElement() {
		Collection collection = new LinkedListIndexedCollection();
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertTrue(collection.contains("ASP"));
	}
	
	@Test
	public void testContainsRandomElement() {
		Collection collection = new LinkedListIndexedCollection();
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertFalse(collection.contains("cipele"));
	}
	
	@Test
	public void testContainsNullError() {
		Collection collection = new LinkedListIndexedCollection();
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertThrows(NullPointerException.class, () ->{ collection.contains(null);});
	}
	
	@Test
	public void testRemoveStoredElementBool() {
		Collection collection = new LinkedListIndexedCollection();
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertTrue(collection.remove("ASP"));
	}
	
	@Test
	public void testRemoveStoredElement() {
		Collection collection = new LinkedListIndexedCollection();
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		boolean removed = collection.remove("UTR");
		Object[] correct = new Object[] {"ASP","ProGi","OOP","MatAn"};
		assertArrayEquals(correct,collection.toArray());
	}
	
	@Test
	public void testRemoveNullError() {
		Collection collection = new LinkedListIndexedCollection();
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertThrows(NullPointerException.class, () ->{ collection.remove(null);});
	}
	
	@Test
	public void testRemoveRandomElement() {
		Collection collection = new LinkedListIndexedCollection();
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertFalse(collection.remove("cipele"));
	}
	
	@Test
	public void testToArrayFunction() {
		Collection other = new LinkedListIndexedCollection();
		other.add("ASP");
		other.add("UTR");
		other.add("ProGi");
		other.add("OOP");
		other.add("MatAn");
		Object[] correct = new Object[] {"ASP","UTR","ProGi","OOP","MatAn"};
		Collection tested = new LinkedListIndexedCollection(other);
		assertArrayEquals(correct,tested.toArray());
	}
	
	@Test
	public void testClearFunction() {
		Collection collection = new LinkedListIndexedCollection();
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		collection.clear();
		assertTrue(collection.isEmpty());		
	}
	
	@Test
	public void testGetFunctionProperIndexFirstHalfOfCollection() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertEquals("UTR",collection.get(1));
	}
	
	@Test
	public void testGetFunctionProperIndexSecondHalfOfCollection() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertEquals("OOP",collection.get(3));
	}
	
	@Test
	public void testGetFunctionInvalidError() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertThrows(IndexOutOfBoundsException.class, () ->{ collection.get(7);});
	}
	
	@Test
	public void testInsertFunction() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		collection.insert("cipele", 2);
		Object[] correct = new Object[] {"ASP","UTR","cipele","ProGi","OOP","MatAn"};
		assertArrayEquals(correct,collection.toArray());
	}
	
	@Test
	public void testInsertFunctionInvalidIndexError() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertThrows(IndexOutOfBoundsException.class, () ->{ collection.insert("cipele",7);});
	}
	
	@Test
	public void testIndexOfFunctionValidObject() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertEquals(3,collection.indexOf("OOP"));
	}
	
	@Test
	public void testIndexOfFunctionInvalidObject() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertEquals(-1,collection.indexOf("cipele"));
	}
	
	@Test
	public void testRemoveByIndexFunction() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		collection.remove(1);
		Object[] correct = new Object[] {"ASP","ProGi","OOP","MatAn"};
		assertArrayEquals(correct,collection.toArray());
	}
	
	@Test
	public void testRemoveByIndexInvalidIndexError() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertThrows(IndexOutOfBoundsException.class, () ->{ collection.remove(7);});
	}
}
