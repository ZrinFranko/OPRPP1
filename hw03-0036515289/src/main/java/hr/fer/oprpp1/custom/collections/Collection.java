package hr.fer.oprpp1.custom.collections;

/**
 * Interface describing the methods in the Collection interface
 * @author zrin
 *
 */
public interface Collection<T> {
	
	
	/**
	 * Function that check if the collection is empty
	 * @return return true if it is empty and false if it isn't
	 */
	default boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Function that returns the current size of the collection
	 * @return current size of the collection
	 */
	int size();
	
	/**
	 * Function that adds and element at the first free space in the collection
	 * Average complexity of this method is O(1)
	 * @param value value that needs to be stored
	 */
	void add(T value);
	
	/**
	 * Function that checks if the current collection contains a specified element
	 * @param value value of the element we are looking for
	 * @return return true if the element is found in the collection and false otherwise
	 */
	boolean contains(T value);
	
	/**
	 * Function that removes the first found element of a specific value
	 * @param value element value that needs to be removed
	 * @return returns true if the removal was successful and false otherwise
	 */
	boolean remove(T value);
	
	/**
	 * Function that creates an array and fills it with the values of elements 
	 * currently in the collection
	 * @return returns an array created from the values of the elements in the collection
	 */
	Object[] toArray();
	
	/**
	 * Function that calls on a processors function for each element in the collection 
	 * @param processor processor which the function calls on to make an operation
	 */
	default void forEach(Processor<? super T> processor) {
		ElementsGetter<T> getter = this.createElementsGetter();
		while(getter.hasNextElement()) 
			processor.process(getter.getNextElement());
		
	}
	
	/**
	 * Function that adds all of the elements in a preexisting collection 
	 * to the new collection
	 * @param other collection which element values are stored inside the new collection
	 */
	default void addAll(Collection<? extends T> other) {
		
		T[] otherArray = (T[]) other.toArray();
		
		for(int i = 0; i < otherArray.length ; i++) {
			this.add(otherArray[i]);
		}
	}
	
	/**
	 * Function which clears all values of the elements in the collection
	 */
	void clear();
	
	/**
	 * Function that creates an instance of ElementsGetter imterface to make iterating over the collection easier
	 * @return a new instance of ElementsGetter interface
	 */
	ElementsGetter<T> createElementsGetter();
	
	default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		ElementsGetter<T> getter = (ElementsGetter<T>) col.createElementsGetter();
		T temp;
		while(getter.hasNextElement()) {
			temp = (T) getter.getNextElement();
			if(tester.test(temp)) this.add(temp);
		}
	}
	
	default void copyTransformedIntoIfAllowed(Collection<T> other,Processor<? super T> processor, Tester<? super T> tester) {
		ElementsGetter<T> getter = this.createElementsGetter();
		while(getter.hasNextElement()) {
			T temp = getter.getNextElement();
			processor.process(temp);
			if(tester.test(temp)) 
				other.add(temp);
		}
	}

}
