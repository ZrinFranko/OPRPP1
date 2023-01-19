package hr.fer.oprpp1.custom.collections;


public class ArrayIndexedCollection extends Collection{
	
	/**
	 * Integer describing the current number of elements of the ArrayIndexedCollection 
	 */
	private int size;
	
	/**
	 * Field containing the values of elements in the Collection
	 */
	private Object[] elements;
	
	/**
	 * Integer describing the default maximum size (16) of the Collection 
	 * if the size is not specified by the user
	 */
	private static final int DEFAULTSIZE = 16;
	
	/**
	 * Default constructor that creates an ArrayIndexedCollection with its size being
	 * equal to the DEFAULTSIZE parameter defined above 
	 */
	public ArrayIndexedCollection() {
		this.elements = new Object[DEFAULTSIZE];
		this.size = 0; 
	}
	
	/**
	 * Constructor that creates and ArrayIndexedCollection with its size being 
	 * the one described by the @param 
	 * @param initialCapacity initial capacity of the collection
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) throw new IllegalArgumentException();
		else {
			this.elements = new Object[initialCapacity];
			this.size = 0;
		}
	}
	
	/**
	 * Constructor that creates a new ArrayIndexedCollection using the elements that
	 * already exist in another collection
	 * @param other collection which elements are copied over into the new one
	 */
	public ArrayIndexedCollection(Collection other) {
		Object[] otherElements = other.toArray();
		if(other.size() > DEFAULTSIZE) {
			this.elements = new Object[other.size()];
			for(int i = 0 ; i < other.size() ; i++) {
				this.add(otherElements[i]);
			}
			this.size = other.size();
		}else if(other == null) throw new NullPointerException();
		else {
			this.elements = new Object[DEFAULTSIZE];
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
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		Object[] otherElements = other.toArray();
		if(other.size() > initialCapacity) {
			this.elements = new Object[other.size()];
			for(int i = 0 ; i < other.size() ; i++) {
				this.add(otherElements[i]);
			}
		}else if(other == null) throw new NullPointerException();
		else {
			this.elements = new Object[initialCapacity];
			for(int i = 0 ; i < other.size() ; i++) {
				this.add(otherElements[i]);
			}
		}
		this.size = other.size();
	}
	
	@Override
	public void add(Object value) {
		if(value == null) throw new NullPointerException();
		if(size() == elements.length) {
			Object[] temp = new Object[2*this.elements.length];
			System.arraycopy(this.elements, 0, temp, 0, this.elements.length);
			this.elements = temp;
		}
		this.elements[size()] = value;
		this.size++;
		
	}

	@Override
	public boolean isEmpty() {
		return size() == 0 ? true : false;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean contains(Object value) {
		if(value == null) throw new NullPointerException();
		for(int i = 0; i < size() ; i++) {
			if(this.elements[i].equals(value)) return true;
		}
		return false;
	}

	@Override
	public boolean remove(Object value) {
		if(value == null) throw new NullPointerException();
		for(int i = 0 ;i < size() ; i++) {
			if(this.elements[i].equals(value)) {
				this.elements = deleteElementAndFixArray(i);
				this.size--;
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
	private Object[] deleteElementAndFixArray(int removeAtIndex) {
		Object[] temp = new Object[elements.length];
		for(int i = 0 ; i < removeAtIndex ; i++) {
			temp[i] = this.elements[i];
		}
		for(int i = removeAtIndex+1 ; i < size() ; i++) {
			temp[i-1] = this.elements[i];
		}
		return temp;
	}

	@Override
	public Object[] toArray() {
		Object[] temp = new Object[size()];
		for(int i = 0 ; i < size() ; i ++) temp[i] = this.elements[i];
		return temp;
	}

	@Override
	public void forEach(Processor processor) {
		for(Object o : this.elements) processor.process(o); 
		
	}

	@Override
	public void addAll(Collection other) {
		class AddingProcessor extends Processor{
			@Override
			public void process(Object value) { add(value); }
		}
		other.forEach(new AddingProcessor());
		
	}

	@Override
	public void clear() {
		for(int i = 0 ; i < size() ; i++) this.elements[i] = null;
		this.size = 0;
	}
	
	/**
	 * Functions that gives the user an element at a specified index. 
	 * Average complexity of this method is O(1)
	 * @param index the value of the index that the function returns
	 * @return element at a specified index
	 */
	public Object get(int index) {
		if(index < 0 || index >= size()) throw new IndexOutOfBoundsException();
		return this.elements[index];
	}
	
	/**
	 * Inserts a new element at a specified position in the collection. Average complexity of this function is O(n/2)
	 * @param value value of the element that needs to be stored
	 * @param position position at which the elements needs to be stored
	 */
	public void insert(Object value, int position) {
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
		
		
	}
	
	/** 
	 * Function that moves the array from the first parameter index to the right in 
	 * order to make room for the storage of a specified value 
	 * @param firstMovedObject index at which a new element will be stored
	 * @param newSize modified size of the collection after the function stores an element
	 */
	private void moveArray(int firstMovedObject, int newSize) {
		if(newSize > this.elements.length) {
			Object[] temp = this.elements.clone();
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
	public int indexOf(Object value) {
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
		this.elements = deleteElementAndFixArray(index);
		this.size--;
	}
	

	

}
