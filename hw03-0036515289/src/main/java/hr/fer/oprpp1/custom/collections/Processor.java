package hr.fer.oprpp1.custom.collections;

/**
 * COde showing the methods in the Process interface
 * @author zrin
 *
 */
public interface Processor<T> {
	
	/**
	 * Method that processes the object value in a specified way
	 * @param value the value that needs to be processed
	 */
	void process(T value);

}
