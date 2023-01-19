package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Code showing the implementation of the ArrayIndexedCollection class
 * @author zrin
 *
 */
public class ArrayIndexedCollection<T> implements List<T> {
	
	/**
	 * Integer describing the current number of elements of the ArrayIndexedCollection 
	 */
	private int size;
	
	/**
	 * Field containing the values of elements in the Collection
	 */
	private T[] elements;
	
	/**
	 * Integer describing the default maximum size (16) of the Collection 
	 * if the size is not specified by the user
	 */
	private static final int DEFAULTSIZE = 16;
	
	private long modificationCount = 0;
	
	/**
	 * Default constructor that creates an ArrayIndexedCollection with its size being
	 * equal to the DEFAULTSIZE parameter defined above 
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection() {
		this.elements = (T[]) new Object[DEFAULTSIZE];
		this.size = 0; 
	}
	
	/**
	 * Constructor that creates and ArrayIndexedCollection with its size being 
	 * the one described by the @param 
	 * @param initialCapacity initial capacity of the collection
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) throw new IllegalArgumentException();
		else {
			this.elements = (T[]) new Object[initialCapacity];
			this.size = 0;
		}
	}
	
	/**
	 * Constructor that creates a new ArrayIndexedCollection using the elements that
	 * already exist in another collection
	 * @param other collection which elements are copied over into the new one
	 */
	public ArrayIndexedCollection(Collection<? extends T> other) {
		T[] otherElements = (T[]) other.toArray();
		
		if(other.size() > DEFAULTSIZE) {
			this.elements = (T[]) new Object[other.size()];
			for(int i = 0 ; i < other.size() ; i++) {
				this.add(otherElements[i]);
			}
			this.size = other.size();
		}else if(other == null) throw new NullPointerException();
		else {
			this.elements = (T[]) new Object[DEFAULTSIZE];
			for(int i = 0 ; i < other.size() ; i++) {
				this.add(otherElements[i]);
			}
			this.size = other.size();
		}
	}
	
	/**
	 * Constructor that creates a new ArrayIndexedCollection using the elements of an
	 * already existing collection with its own capacity seperate from the capacity
	 * of the other collection
	 * @param other collection which elements are copied over into the new one
	 * @param initialCapacity initial capacity of the collection
	 */
	public ArrayIndexedCollection(Collection<? extends T> other, int initialCapacity) {
		T[] otherElements = (T[]) other.toArray();
		
		if(other.size() > initialCapacity) {
			this.elements = (T[]) new Object[other.size()];
			for(int i = 0 ; i < other.size() ; i++) {
				this.add(otherElements[i]);
			}
		}else if(other == null) throw new NullPointerException();
		else {
			this.elements = (T[]) new Object[initialCapacity];
			for(int i = 0 ; i < other.size() ; i++) {
				this.add(otherElements[i]);
			}
		}
		
		this.size = other.size();
	}
	
	@Override
	public void add(T value) {
		if(value == null) throw new NullPointerException();
		
		if(size() == elements.length) {
			T[] temp = (T[]) new Object[2*this.elements.length];
			System.arraycopy(this.elements, 0, temp, 0, this.elements.length);
			this.elements = temp;
		}
		
		this.elements[size()] = value;
		this.size++;
		modificationCount++;
		
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean contains(T value) {
		if(value == null) throw new NullPointerException();
		
		for(int i = 0; i < size() ; i++) {
			if(this.elements[i].equals(value)) return true;
		}
		
		return false;
	}

	@Override
	public boolean remove(T value) {
		if(value == null) throw new NullPointerException();
		
		for(int i = 0 ;i < size() ; i++) {
			if(this.elements[i].equals(value)) {
				this.elements = deleteElementAndFixArray(i,size()-1);
				this.size--;
				modificationCount++;
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Function that helps speed up the process of removal of element from the collection
	 * it uses the first parameter to know on what index element needs to be removed and
	 * moves the rest of the array one step to the left so the array doesn't have duplicate
	 * of the last element in the original collection
	 * @param removeAtIndex the index of the element that needs to be removed from the collection
	 * @param newSize modified size of the collection without the removed element
	 * @return returns a new array without the removed element 
	 */
	private T[] deleteElementAndFixArray(int removeAtIndex, int newSize) {
		T[] temp = (T[]) new Object[newSize];
		for(int i = 0 ; i < removeAtIndex ; i++) {
			temp[i] = this.elements[i];
		}
		for(int i = removeAtIndex+1 ; i < newSize+1 ; i++) {
			temp[i-1] = this.elements[i];
		}
		return temp;
	}

	@Override
	public T[] toArray() {
		T[] temp = null;
		System.arraycopy(elements, 0, temp, 0, size());
		return temp;
	}

	@Override
	public void addAll(Collection<? extends T> other) {
		class AddingProcessor implements Processor<T>{
			@Override
			public void process(T value) { add(value); }
		}
		other.forEach(new AddingProcessor());
		
	}

	@Override
	public void clear() {
		for(int i = 0 ; i < size() ; i++) this.elements[i] = null;
		this.size = 0;
		modificationCount++;
	}
	
	/**
	 * Functions that gives the user an element at a specified index. 
	 * Average complexity of this method is O(1)
	 * @param index the value of the index that the function returns
	 * @return element at a specified index
	 */
	public T get(int index) {
		if(index < 0 || index >= size()) throw new IndexOutOfBoundsException();
		return this.elements[index];
	}
	
	/**
	 * Inserts a new element at a specified position in the collection. Average complexity of this function is O(n/2)
	 * @param value value of the element that needs to be stored
	 * @param position position at which the elements needs to be stored
	 */
	public void insert(T value, int position) {
		if(position < 0 || position > size()) throw new IndexOutOfBoundsException();
		else if(position == size()) add(value);
		else {
			for(int i = 0 ; i < size() ; i++) {
				if(i == position) {
					moveArray(i,size()+1);
					this.elements[i] = value;
					this.size++;
				}
			}
		}
		
		modificationCount++;
	}
	
	/** 
	 * Function that moves the array from the first parameter index to the right in 
	 * order to make room for the storage of a specified value 
	 * @param firstMovedObject index at which a new element will be stored
	 * @param newSize modified size of the collection after the function stores an element
	 */
	private void moveArray(int firstMovedObject, int newSize) {
		if(newSize > this.elements.length) {
			T[] temp = this.elements.clone();
			System.arraycopy(temp, 0, this.elements, 0, 2*temp.length);
		}
		for(int i = newSize-1 ; i > firstMovedObject ; i--) {
			this.elements[i] = this.elements[i-1];
		}	
		
	}
	
	/**
	 * Function that return the index value of an element in the field. Average complexity of this method is O(n/2)
	 * @param value element value which we are searching an index of 
	 * @return index of the specified element value
	 */
	public int indexOf(T value) {
		for(int i = 0 ; i < size() ; i++) {
			if(this.elements[i].equals(value)) return i;
		}
		return -1;
	}
	
	/**
	 * Function that removes an element from a specified index in the collection
	 * @param index index at which the removal of the element happens
	 */
	public void remove(int index) {
		if(index < 0 || index >= size()) throw new IndexOutOfBoundsException();
		
		this.elements = deleteElementAndFixArray(index, size()-1);
		this.size--;
		modificationCount++;
	}

	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ElementsGetterImpl(this);
	}
	
	/**
	 * Code showing the implementation of the ElementsGetter interface for the ArrayIndexedCollection class
	 * @author zrin
	 *
	 */
	private static class ElementsGetterImpl<T> implements ElementsGetter<T> {
		
		/**
		 * Value of the last processed index
		 */
		private int current;
		/**
		 * The reference to the collection
		 */
		private final ArrayIndexedCollection<T> collection;
		/**
		 * The number of modifications to the collection
		 */
		private long savedModificationCount;
		
		/**
		 * Constructor of the ElementsGetterImpl class
		 * @param collection
		 * @param modificationCount
		 */
		public ElementsGetterImpl(ArrayIndexedCollection<T> collection) {
			this.current = 0;
			this.collection = (ArrayIndexedCollection<T>) collection;
			this.savedModificationCount = collection.modificationCount;
		}
		
		@Override
		public boolean hasNextElement() {
			if(savedModificationCount != collection.modificationCount) throw new ConcurrentModificationException("Dogodila se izmjena strukture nad kojom iteriram!");
			return current < collection.size();
		}
		
		@Override
		public T getNextElement() {
			if(savedModificationCount != collection.modificationCount) throw new ConcurrentModificationException("Dogodila se izmjena strukture nad kojom iteriram!");
			if(current >= collection.size()) throw new NoSuchElementException("Kolekcija nema vise elemenata za vratiti!");
			
			return collection.elements[current++];
		}
		
	}
	

	

}
