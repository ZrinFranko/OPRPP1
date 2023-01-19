package hr.fer.oprpp1.custom.collections;

/**
 * Code showing the methods in the List interface
 * @author zrin
 *
 */
public interface List extends Collection {
	
	/**
	 * Function that returns the value of the object at a specified index
	 * @param index the index at which the object value is stored in the collection
	 * @return the value of the object
	 */
	Object get(int index);
	/**
	 * Function that inserts a new object value into the array
	 * @param value value that needs to be inserted
	 * @param position index at which the value needs to be inserted
	 */
	void insert(Object value, int position);
	/**
	 * Function that returns the index at which the object value has been stored
	 * @param value the value that needs to be found in the collection
	 * @return the index at which the value is stored
	 */
	int indexOf(Object value);
	/**
	 * Function that removes the object value at a specified index
	 * @param index the index at which the object value needs to be removed
	 */
	void remove(int index);

}
