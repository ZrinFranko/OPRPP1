package hr.fer.oprpp1.custom.collections;

public class Collection {
	
	protected Collection() {}
	
	/**
	 * Function that check if the collection is empty
	 * @return return true if it is empty and false if it isn't
	 */
	public boolean isEmpty() {
		return size() == 0 ? true : false;
	}
	
	/**
	 * Function that returns the current size of the collection
	 * @return current size of the collection
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Function that adds and element at the first free space in the collection
	 * Average complexity of this method is O(1)
	 * @param value value that needs to be stored
	 */
	public void add(Object value) {}
	
	/**
	 * Function that checks if the current collection contains a specified element
	 * @param value value of the element we are looking for
	 * @return return true if the element is found in the collection and false otherwise
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Function that removes the first found element of a specific value
	 * @param value element value that needs to be removed
	 * @return returns true if the removal was successful and false otherwise
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Function that creates an array and fills it with the values of elements 
	 * currently in the collection
	 * @return returns an array created from the values of the elements in the collection
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Function that calls on a processors function for each element in the collection 
	 * @param processor processor which the function calls on to make an operation
	 */
	public void forEach(Processor processor) {}
	
	/**
	 * Function that adds all of the elements in a preexisting collection 
	 * to the new collection
	 * @param other collection which element values are stored inside the new collection
	 */
	public void addAll(Collection other) {}
	
	/**
	 * Function which clears all values of the elements in the collection
	 */
	public void clear() {}

}
