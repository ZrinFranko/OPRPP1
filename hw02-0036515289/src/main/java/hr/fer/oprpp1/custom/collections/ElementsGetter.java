package hr.fer.oprpp1.custom.collections;

/**
 * Interface showing the default classes of the ElementsGetter 
 * @author zrin
 *
 */
public interface ElementsGetter {
	
	/**
	 * Function that checks if the next element exists in the array
	 * @return true if there is a next elemenet false otherwise
	 */
	boolean hasNextElement();
	
	/**
	 * Function that returns the value of the next element in the collection
	 * @return value of the next element
	 */
	Object getNextElement();
	
	/**
	 * Default function that invokes the process function of the Processor class on all the remaining elements in the collection
	 * @param p Processor whose function we are calling on all the remaining elements in the collection
	 */
	default void processRemaining(Processor p) {
		while(hasNextElement()) p.process(getNextElement());
	}

}
