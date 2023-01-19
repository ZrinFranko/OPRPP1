package hr.fer.oprpp1.custom.collections;

public class ObjectStack {
	
	private ArrayIndexedCollection<Object> hiddenGoods;
	
	/**
	 * Constructor that creates an Object of a type ObjectStack with the default
	 * ArrayIndexedCollection class to handle the operations
	 */
	public ObjectStack() {
		this.hiddenGoods = new ArrayIndexedCollection<>();
	}
	
	/**
	 * Function that calls on the ArrayIndexedCollection function isEmpty()
	 * to check if the stack is empty
	 * @return true if the stack is empty and false otherwise
	 */
	public boolean isEmpty() {
		return this.hiddenGoods.isEmpty();
	}
	
	/**
	 * Function that calls on the ArrayIndexedCollection function size()
	 * to check the current number of elements in the stack
	 * @return size of the stack
	 */
	public int size() {
		return this.hiddenGoods.size();
	}

	/**
	 * Function that add a new element to the stack by calling on the function
	 * add(Object value) in the ArrayIndexedCollection class
	 * @param value what value is stored on the stack
	 */
	public void push(Object value) {
		this.hiddenGoods.add(value);
	}
	
	/**
	 * Function that returns the last element added to the stack while 
	 * simultaneously removing it from the stack
	 * @return the value of the last stored element in the stack
	 */
	public Object pop() {
		if(hiddenGoods.size() == 0) throw new EmptyStackException("Stack is currently empty");
		Object temp = this.hiddenGoods.get(this.hiddenGoods.size()-1);
		this.hiddenGoods.remove(temp);
		return temp;
	}
	
	/**
	 * Function that return the value of the last element add to the stack
	 * while keeping it in the stack
	 * @return the value of the last element stored in the stack
	 */
	public Object peek() {
		if(hiddenGoods.size() == 0) throw new EmptyStackException("Stack is currently empty");
		return this.hiddenGoods.get(this.hiddenGoods.size()-1);
		
	}
	
	/**
	 * Function that removes all elements from the stack
	 */
	public void clear() {
		this.hiddenGoods.clear();
	}
}
