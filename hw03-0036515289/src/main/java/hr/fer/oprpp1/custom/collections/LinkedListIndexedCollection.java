package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Code showing the implementation of the LinkedListIndexedCollection
 * @author zrin
 *
 */
public class LinkedListIndexedCollection<T> implements List<T> {
	/**
	 * Private class referencing a single element of the LinkedListIndexedCollection
	 * @author zrin
	 *
	 */
	private static class ListNode<T>{
		/**
		 * Value stored in the ListNode element
		 */
		private T value;
		/**
		 * Reference to the ListNode element that was stored before the current one 
		 */
		private ListNode<T> previous;
		/**
		 * Reference to the ListNode element that was stored after the current one
		 */
		private ListNode<T> next;
		
		
		/**
		 * Default constructor of the ListNode class that sets all value to null
		 */
		public ListNode() {
			this.value = null;
			this.previous = null;
			this.next = null;
		}

		/**
		 * Constructor of the ListNode class
		 * @param value value of the node
		 * @param previous reference to the previous node 
		 * @param next reference to the next node
		 */
		public ListNode(T value, ListNode<T> previous, ListNode<T> next) {
			this.value = value;
			this.previous = previous;
			this.next = next;
		}
		
		/**
		 * Function that returns the value of the parameter value
		 * @return value of the ListNode
		 */
		public T getValue() {
			return value;
		}
		/**
		 * Function that sets the value of the ListNode
		 * @param value what value is given into the parameter
		 */
		public void setValue(T value) {
			this.value = value;
		}
		/**
		 * Function that returns the reference to the previous ListNode in the chain
		 * @return reference to the previous ListNode
		 */
		public ListNode<T> getPrevious() {
			return previous;
		}
		/**
		 * Function that sets the new value of the previous ListNode in the chain
		 * @param previous reference to the new ListNode element
		 */
		public void setPrevious(ListNode<T> previous) {
			this.previous = previous;
		}
		/**
		 * Function that returns the reference to the next ListNode element in the chain
		 * @return reference to the next ListNode in the chain
		 */
		public ListNode<T> getNext() {
			return next;
		}
		/**
		 * Function that sets the new value of the reference for the next ListNode in 
		 * the chain
		 * @param next new reference value of the ListNode element
		 */
		public void setNext(ListNode<T> next) {
			this.next = next;
		}
	}
	
	/**
	 * Variable showing the current number of elements in the collection
	 */
	private int size;
	/**
	 * Reference to the first element of the collection
	 */
	private ListNode<T> first;
	/**
	 * Reference to the last element of the collection
	 */
	private ListNode<T> last;
	
	private long modificationCount = 0;
	
	/**
	 * Default contructor of the LinkedListIndexedCollection that setp up the default 
	 * values of the parameters
	 */
	public LinkedListIndexedCollection() {
		this.first = null;
		this.last = null;
		this.size = 0;
	}
	
	/**
	 * Contructor that creates a LinkedListIndexedCollection using a preexisting collection
	 * by copying its element values into the current collection
	 * @param other collection whose elements are copied over into the current one 
	 */
	public LinkedListIndexedCollection(Collection<? extends T> other) {
		if(other == null) throw new NullPointerException();
		
		other.forEach((value) -> add(value));	
		this.size = other.size();
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
	public void add(T value) {
		if(value == null) throw new NullPointerException();
		
		ListNode<T> temp = new ListNode<T>(value,this.last,null);
		
		if(size() == 0) {
			this.first = temp;
		}
		else{
			this.last.next = temp;
		}
		
		this.last = temp;
		this.size++;
		modificationCount++;
		
	}

	@Override
	public boolean contains(T value) {
		if(value == null) throw new NullPointerException();
		
		ListNode<T> current = this.first;
		int i = 0;
		
		while(i < size()) {
			if(current.getValue().equals(value)) return true;
			current = current.getNext();
			i++;
		}
		
		return false;
	}

	@Override
	public boolean remove(T value) {
		if(value == null) throw new NullPointerException();
		
		ListNode<T> current = this.first;
		int i = 0;
		modificationCount++;
		
		while(i < size()) {
			if(current.getValue().equals(value)) {
				ListNode<T> temp = current;
				if(current.getNext() == null) {
					this.last = temp.getPrevious();
					current = null;
					this.size--;
					return true;
				}else if(current.getPrevious() == null) {
					this.first = temp.getNext();
					current = null;
					this.size--;
					return true;
				}else {
					current.getPrevious().setNext(temp.getNext());
					current.getNext().setPrevious(temp.getPrevious());
					current = null;
					this.size--;
					return true;
				}
			}
			current = current.getNext();
			i++;
		}
		
		return false;
	}

	@Override
	public Object[] toArray() {
		Object[] temp = new Object[size()];
		int i = 0;
		ListNode<T> current = this.first;
		
		while(i < size()) {
			temp[i] = current.getValue();
			current = current.getNext();
			i++;
		}
		
		return temp;
	}

	@Override
	public void addAll(Collection<? extends T> other) {
		class AddingProcessor implements Processor<T>{
			@Override
			public void process(T value) { add(value); }
		}
		
		other.forEach(new AddingProcessor());
		modificationCount++;
		
	}
	
	@Override
	public void clear() {
		int i = 0;
		ListNode<T> current = this.first;
		
		while(i < size()) {
			ListNode<T> next = current.getNext();
			current = null;
			current = next;
			i++;
		}
		this.first = null;
		this.last = null;
		size = 0;
		modificationCount++;
	}
	
	/**
	 * Function that returns the value of the element at the specified index
	 * @param index target element that the function needs to give to the user
	 * @return the value of the element at the specified index
	 */
	public T get(int index) {
		if(index >= size()) throw new IndexOutOfBoundsException();
		
		ListNode current = new ListNode();
		
		if(index == 0) return first.getValue();
		else if(index == (size()-1)) return last.getValue();
		else{
			if(index > (size()/2)) {
				current = this.last;
				for(int i = size()-1 ; i >= index ; i --) {
					current = current.getPrevious();
				}
				return (T) current.getNext().getValue();
			}
			else {
				current = this.first;
				for(int i = 0 ; i < index ; i++) {
					current = current.getNext();
				}
				return (T) current.getValue();
			}
		}
	}
	
	/**
	 * Function that puts a new element into the collection at a specified index. Average complexity of this function
	 * is O(n/2+1)
	 * @param value value that needs to be put into the collection
	 * @param position position at which the value needs to be put to
	 */
	public void insert(T value, int position) {
		if(position < 0 || position > size()) throw new IndexOutOfBoundsException();
		
		ListNode current = this.first;
		int i = 0;
		
		while(i < (size()+1)) {
			if(i == position) {
				ListNode newNode = new ListNode(value,current.getPrevious(),current);
				current.getPrevious().setNext(newNode);
				current = newNode;
			}
			current = current.getNext();
			i++;
		}
		
		this.size++;
		modificationCount++;
		
	}
	
	/**
	 * Function that returns the index of a first occurence of a specified value. Average complexity of this method is
	 * O(n/2+1)
	 * @param value value that we are looking for
	 * @return the index of the first found occurence of the value or -1 if it isn't found
	 */
	public int indexOf(T value) {
		int i = 0;
		ListNode current = this.first;
		
		while(i < size()) {
			if(current.getValue().equals(value)) return i;
			current = current.getNext();
			i++;
		}
		return -1;
	}
	
	/**
	 * Function removes an element from the collection at a specified index
	 * @param index index at which the function needs to remove the element
	 */
	public void remove(int index) {
		if(index < 0 || index >= size()) throw new IndexOutOfBoundsException();
		
		int i = 0;
		ListNode current = this.first;
		
		while(i < index) {
			current = current.getNext();
			i++;
		}
		
		ListNode temp = current;
		modificationCount++;
		
		if(current.getNext() == null) {
			this.last = temp.getPrevious();
			current = null;
			this.size--;
		}else if(current.getPrevious() == null) {
			this.first = temp.getNext();
			current = null;
			this.size--;
		}else {
			current.getPrevious().setNext(temp.getNext());
			current.getNext().setPrevious(temp.getPrevious());
			current = null;
			this.size--;
		}
		
	}

	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ElementsGetterImpl(this,modificationCount);
	}
	
	/**
	 * Code showing the private implementation of the ElementsGetter interface
	 * @author zrin
	 *
	 */
	private static class ElementsGetterImpl<T> implements ElementsGetter<T> {
		
		/**
		 * Last processed node
		 */
		private ListNode currentNode;
		/**
		 * The reference to the collection that needs to be processed
		 */
		private LinkedListIndexedCollection<T> collection;
		/**
		 * The current number of modifications to the collection
		 */
		private long savedModificationCount;

		/**
		 * Constructor of the ElementsGetterImpl class
		 * @param collection the reference to the collection that needs to be processed
		 * @param modificationCount the number of modifications made to the collection
		 */
		public ElementsGetterImpl(Collection<T> collection,long modificationCount) {
			this.collection = (LinkedListIndexedCollection<T>) collection;
			this.currentNode = this.collection.first;
			this.savedModificationCount = modificationCount;
		}
		
		@Override
		public boolean hasNextElement() {
			if(savedModificationCount != collection.modificationCount) throw new ConcurrentModificationException("Dogodila se izmjena strukture nad kojom iteriram!");

			return currentNode != null;
		}

		@Override
		public T getNextElement() {
			if(savedModificationCount != collection.modificationCount) throw new ConcurrentModificationException("Dogodila se izmjena strukture nad kojom iteriram!");
			if(currentNode == null) throw new NoSuchElementException("Kolekcija nema vise elemenata za isporuciti!");
				
			ListNode temp = currentNode;
			currentNode = temp.getNext();
			return (T) temp.getValue();
		}
		
	}

}
