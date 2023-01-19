package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Code describing the implementation of the SimpleHashable class
 * @author zrin
 *
 * @param <K> type of the key element
 * @param <V> type of the value element
 */
public class SimpleHashtable<K,V> implements Iterable<SimpleHashtable.TableEntry<K, V>>{

	/**
	 * Private class describing a new object class TableEntry used in the internal storage of the SimpleHashable class
	 * @author zrin
	 *
	 * @param <K> type of the key variable
	 * @param <V> type of the value variable
	 */
	static class TableEntry<K,V> {
		/**
		 * Value of the key variable of the current TableEntry
		 */
		K key;
		/**
		 * Value of the value variable of the current TableEntry
		 */
		V value;
		/**
		 * Reference to the next TableEntry 
		 */
		TableEntry<K,V> next;

		/**
		 * Public constructor of the TableEntry class
		 * @param key value of the key variable
		 * @param value value of the value variable 
		 * @param next reference to the next TableEntry in the linked list
		 */
		public TableEntry(K key, V value, TableEntry<K,V> next) {
			if(key == null) throw new NullPointerException("Key cannot be null!");
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Public getter of the value variable
		 * @return value of the value variable
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Public setter of the value variable
		 * @param value the new value that needs to overwrite the old value
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Public getter of the key variable
		 * @return the value of the key variable
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Function that returns a readable representation of the TableEntry class
		 */
		@Override
		public String toString() {
			return getKey() + "=" + getValue();
		}

	}

	/**
	 * Internal table of all the elements currently stored in the class
	 */
	private TableEntry<K,V>[] elements;
	/**
	 * Current size of the collection
	 */
	private int size;
	/**
	 * The dafult size of the new collection if it isn't specified by the user
	 */
	private static final int DEFAULTSIZE = 16;
	
	private int modificationCount;
	
	/**
	 * Default constructor of the SimpleHashtable class
	 */
	public SimpleHashtable() {
		size = 0;
		elements = (TableEntry<K,V>[]) new TableEntry[DEFAULTSIZE];
		modificationCount = 0;
	}

	/**
	 * Constructor of the SimpleHashtable class with a user determines capacity
	 * @param initialCapacity the initial capacity of the collection defined by the user
	 */
	public SimpleHashtable(int initialCapacity) {
		if(initialCapacity < 1) throw new IllegalArgumentException("Size cannot be smaller than 1");
		size = 0;
		modificationCount = 0;
		
		int i = 0;
		while(true) {
			if(Math.pow(2, i) > initialCapacity) {
				elements = (TableEntry<K,V>[]) new TableEntry[(int)Math.pow(2, i)];
				break;
			}
			i++;
		}
	}

	/**
	 * Function that puts a new value into the collection or overwrites the old one if the key value already exists in the collection
	 * @param key the value of the key that the new value needs to be stored at
	 * @param value the value that needs to be stored under the key variable
	 * @return null if the key variable doesn't exist in the collection or the value that was stored before it was overwritten at the key variable
	 */
	public V put(K key, V value) {
		if(key == null) throw new NullPointerException("Key cannot be null!");
		if((size/elements.length) >= 0.75) doupleCapacity();
		
		if(elements[generateIndex(key)] == null) {
			elements[generateIndex(key)] = new TableEntry<K,V>(key,value,null);
			size++;
			modificationCount++;
			return null;
		}else {
			TableEntry<K,V> current = elements[generateIndex(key)];
			while(current != null) {
				if(current.getKey().equals(key)) {
					V oldValue = current.getValue();
					current.setValue(value);
					return oldValue;
				}
				current = current.next;
			}
			TableEntry<K,V> newInput = new TableEntry<K,V>(key,value,null);
			current.next = newInput;
			size++;
			modificationCount++;
			return null;			
		}
	}
	/**
	 * Function that double the capacity of the elements array if the capacity is over 75%
	 */
	private void doupleCapacity() {
		TableEntry<K,V>[] temp = toArray();
		elements = (TableEntry<K,V>[]) new TableEntry[elements.length*2];
		
		for(int i = 0; i < temp.length ; i++) {
			put(temp[i].getKey(),temp[i].getValue());
		}
		
	}

	/**
	 * Function that searches the collection and returns the value stored under a given key
	 * @param key the key under whose value needs to be returned to the user
	 * @return the value that was stored under the key
	 */
	public V get(Object key) {
		if(key == null) return null;
		
		TableEntry<K,V> current = elements[generateIndex(key)];
		
		while(current != null) {
			if(current.getKey().equals(key)) {
				return current.getValue();
			}
			current = current.next;
		}
		return null;
	}

	/**
	 * Function that returns the current size of the collection to the user
	 * @return the current size of the collection
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Function that searches the collection for a certain key value
	 * @param key the value that needs to be found in the collection
	 * @return true if the key value exists in the collection, false otherwise
	 */
	public boolean containsKey(Object key) {
		if(key == null) throw new NullPointerException("Key cannot be null");
		
		TableEntry<K,V> current = elements[generateIndex(key)];
		
		while(current != null) {
			if(current.getKey().equals(key)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	/**
	 * Function that searches the collection for a certain value 
	 * @param value the value that needs to be found
	 * @return true if the value if found in the collection, false otherwise
	 */
	public boolean containsValue(Object value) {
		TableEntry<K,V> current = null;
		
		for(int i = 0; i < elements.length ; i++) {
			current = elements[i];
			while(current != null) {
				if(current.getValue().equals(value)) {
					return true;
				}
				current = current.next;
			}
		}
		return false;
	}

	/**
	 * Function that removes the TableEntry of a certain key from the collection
	 * @param key the key whose TableEntry needs to be removed from the collection
	 * @return the value that has been stored under the key if it existed in the collection, null otherwise
	 */
	public V remove(Object key) {
		if(containsKey(key)) {
			modificationCount++;
			
			TableEntry<K,V> current = elements[generateIndex(key)];
			if(current.getKey().equals(key)) {
				V temp = current.getValue();
				current = current.next;
				size--;
				return temp;
			}
			while(current != null) {
				if(current.next.getKey().equals(key)) {
					V temp = current.next.getValue();
					TableEntry<K,V> tempTE = current.next;
					current.next = tempTE.next;
					size--;
					return temp;
				}
				current = current.next;
			}
		}
		return null;
	}

	/**
	 * Function that checks if the collection is empty
	 * @return true if it is empt and false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}
		
	/**
	 * Function that returns a string representation of the collection
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		TableEntry<K,V>[] temp = toArray();
		
		sb.append("[");
		for(int i = 0; i < temp.length ; i++) sb.append(temp[i].toString()).append(", ");
		sb.deleteCharAt(sb.length()-1).deleteCharAt(sb.length()-1).append("]");
		return sb.toString();
	}

	/**
	 * Function that creates an array made up of the TableEntry elements currently in the collection
	 * @return
	 */
	public TableEntry<K,V>[] toArray() {
		TableEntry<K,V>[] temp = (TableEntry<K,V>[]) new TableEntry[size()];
		int k = 0;
		TableEntry<K,V> current;	
		
		for(int i = 0; i < elements.length ; i++) {
			current = elements[i];
			while(current != null) {
				temp[k] = current;
				k++;
				current = current.next;
			}
		}
		return temp;
	}

	/**
	 * Function that generates at what index a certain TableEntry needs to be stored using the hashcode of the key
	 * @param key the key whose hashcode value if used to determine what index the key should go at
	 * @return index at which the key points using an equation of the hashcode
	 */
	private int generateIndex(Object key) {
		return Math.abs(key.hashCode() % elements.length);
	}
	
	/**
	 * Function that removes all the elements currently stored in the collection
	 */
	public void clear() {
		for(int i = 0 ; i < elements.length ; i++) {
			elements[i] = null;
		}
		this.size = 0;
		this.modificationCount++;
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		
		private int index;
		
		private int savedModificationCount;
		
		private TableEntry<K,V> current;
		
		public IteratorImpl() {
			this.index = 0;
			this.savedModificationCount = modificationCount;
			current = findNextElement();
		}

		private TableEntry<K, V> findNextElement() {
			while(index < elements.length) {
				if(elements[index] != null) {
					return elements[index];
				}
				index++;
			}
			return null;
		}

		@Override
		public boolean hasNext() {
			if(savedModificationCount != modificationCount) throw new ConcurrentModificationException("The collection has been updated!");
			return current != null;
		}

		@Override
		public SimpleHashtable.TableEntry<K, V> next() {
			if(savedModificationCount != modificationCount) throw new ConcurrentModificationException("The collection has been changed!");
			if(current.next == null) {
				findNextElement();
				return current;
			}
			TableEntry<K,V> temp = current;
			current = temp.next;
			return temp;
		}
		
		public void remove() {
			if(savedModificationCount != modificationCount) throw new ConcurrentModificationException("The collection has been changed!");
			TableEntry<K,V> temp = current;
			SimpleHashtable.this.remove(current);
			modificationCount++;
			current = temp.next;
		}
	}


}
