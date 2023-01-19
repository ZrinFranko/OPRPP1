package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {	
	
	@Test
	public void testDefaultContructor() {
		Collection collection = new ArrayIndexedCollection();
		assertEquals(0, collection.size());
	}
	
	@Test 
	public void testInitialCapacityContructor() {
		Collection collection = new ArrayIndexedCollection(5);
		assertEquals(0, collection.size());
	}
	
	@Test 
	public void testInitialCapacityConstructorError() {
		assertThrows(IllegalArgumentException.class, () ->{ new ArrayIndexedCollection(0);});
	}
	
	@Test 
	public void testOtherCollectionConstructor() {
		Collection other = new ArrayIndexedCollection();
		other.add("ASP");
		other.add("UTR");
		other.add("ProGi");
		other.add("OOP");
		other.add("MatAn");
		Object[] correct = new Object[] {"ASP","UTR","ProGi","OOP","MatAn"};
		Collection tested = new ArrayIndexedCollection(other);
		assertArrayEquals(correct,tested.toArray());
	}
	
	@Test
	public void testOtherCollectionHigherCapacityConstructor() {
		Collection other = new ArrayIndexedCollection();
		other.add("ASP");
		other.add("UTR");
		other.add("ProGi");
		other.add("OOP");
		other.add("MatAn");
		other.add("ASP");
		other.add("UTR");
		other.add("ProGi");
		other.add("OOP");
		other.add("MatAn");
		other.add("ASP");
		other.add("UTR");
		other.add("ProGi");
		other.add("OOP");
		other.add("MatAn");
		other.add("ASP");
		other.add("UTR");
		other.add("ProGi");
		other.add("OOP");
		other.add("MatAn");
		Object[] correct = new Object[] {"ASP","UTR","ProGi","OOP","MatAn","ASP","UTR","ProGi","OOP","MatAn","ASP","UTR","ProGi","OOP","MatAn","ASP","UTR","ProGi","OOP","MatAn"};
		Collection tested = new ArrayIndexedCollection(other);
		assertArrayEquals(correct,tested.toArray());
	}
	
	@Test
	public void testOtherCollectionNullErrorConstructor() {
		assertThrows(NullPointerException.class, () ->{ new ArrayIndexedCollection(null);});
	}
	
	@Test
	public void testOtherCollectionInitialCapacityConstructorCopiedCorrectly() {
		Collection other = new ArrayIndexedCollection();
		other.add("ASP");
		other.add("UTR");
		other.add("ProGi");
		other.add("OOP");
		other.add("MatAn");
		Object[] correct = new Object[] {"ASP","UTR","ProGi","OOP","MatAn"};
		Collection tested = new ArrayIndexedCollection(other,10);
		assertArrayEquals(correct,tested.toArray());
	}
	
	@Test
	public void testOtherCollectionInitialCapacityNullErrorConstructor() {
		assertThrows(NullPointerException.class, () ->{ new ArrayIndexedCollection(null,5);});
	}
	
	@Test
	public void testAddFunction() {
		Collection collection = new ArrayIndexedCollection();
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		Object[] correct = new Object[] {"ASP","UTR","ProGi","OOP","MatAn"};
		assertArrayEquals(correct,collection.toArray());
	}
	
	@Test
	public void testAddingFunctionWithInitialCapacity() {
		Collection collection = new ArrayIndexedCollection(6);
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
		Collection collection = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () ->{ collection.add(null);});
	}
	
	@Test 
	public void testIsEmptyIfEmpty() {
		Collection collection = new ArrayIndexedCollection();
		assertTrue(collection.isEmpty());
		
	}
	
	@Test 
	public void testIsEmptyIfNotEmpty() {
		Collection collection = new ArrayIndexedCollection();
		collection.add("ASP");
		assertFalse(collection.isEmpty());
		
	}
	
	@Test
	public void testSizeCorrect() {
		Collection collection = new ArrayIndexedCollection(6);
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertEquals(5,collection.size());
	}
	
	@Test
	public void testContainsStoredElement() {
		Collection collection = new ArrayIndexedCollection(6);
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertTrue(collection.contains("ASP"));
	}
	
	@Test
	public void testContainsRandomElement() {
		Collection collection = new ArrayIndexedCollection(6);
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertFalse(collection.contains("cipele"));
	}
	
	@Test
	public void testContainsNullError() {
		Collection collection = new ArrayIndexedCollection(6);
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertThrows(NullPointerException.class, () ->{ collection.contains(null);});
	}
	
	@Test
	public void testRemoveStoredElementBool() {
		Collection collection = new ArrayIndexedCollection(6);
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertTrue(collection.remove("ASP"));
	}
	
	@Test
	public void testRemoveStoredElement() {
		Collection collection = new ArrayIndexedCollection(6);
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
		Collection collection = new ArrayIndexedCollection(6);
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertThrows(NullPointerException.class, () ->{ collection.remove(null);});
	}
	
	@Test
	public void testRemoveRandomElement() {
		Collection collection = new ArrayIndexedCollection(6);
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertFalse(collection.remove("cipele"));
	}
	
	@Test
	public void testToArrayFunction() {
		Collection other = new ArrayIndexedCollection();
		other.add("ASP");
		other.add("UTR");
		other.add("ProGi");
		other.add("OOP");
		other.add("MatAn");
		Object[] correct = new Object[] {"ASP","UTR","ProGi","OOP","MatAn"};
		Collection tested = new ArrayIndexedCollection(other,10);
		assertArrayEquals(correct,tested.toArray());
	}
	
	@Test
	public void testClearFunction() {
		Collection collection = new ArrayIndexedCollection(6);
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		collection.clear();
		assertTrue(collection.isEmpty());		
	}
	
	@Test
	public void testGetFunctionProperIndex() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(6);
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertEquals("UTR",collection.get(1));
	}
	
	@Test
	public void testGetFunctionInvalidError() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(6);
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertThrows(IndexOutOfBoundsException.class, () ->{ collection.get(7);});
	}
	
	@Test
	public void testInsertFunction() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(6);
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
		ArrayIndexedCollection collection = new ArrayIndexedCollection(6);
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertThrows(IndexOutOfBoundsException.class, () ->{ collection.insert("cipele",7);});
	}
	
	@Test
	public void testIndexOfFunctionValidObject() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(6);
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertEquals(3,collection.indexOf("OOP"));
	}
	
	@Test
	public void testIndexOfFunctionInvalidObject() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(6);
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertEquals(-1,collection.indexOf("cipele"));
	}
	
	@Test
	public void testRemoveByIndexFunction() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(6);
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
		ArrayIndexedCollection collection = new ArrayIndexedCollection(6);
		collection.add("ASP");
		collection.add("UTR");
		collection.add("ProGi");
		collection.add("OOP");
		collection.add("MatAn");
		assertThrows(IndexOutOfBoundsException.class, () ->{ collection.remove(7);});
	}
}
