package hr.fer.oprpp1.custom.collections;

/**
 * Code showing methods in the Tester interface
 * @author zrin
 *
 */
public interface Tester<T> {
	
	/**
	 * Function that tests the value of the object
	 * @param obj the value that needs to be tested
	 * @return true if the value passes the test false otherwise
	 */
	boolean test(T obj);

}
