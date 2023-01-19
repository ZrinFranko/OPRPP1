package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.*;

/**
 * Code showing the implementation of the Node class
 * @author zrin
 *
 */
public class Node {
	
	/**
	 * Private collection of the childNode of the current Node
	 */
	private List children;
	
	/**
	 * Function that adds the next child to the collection, on first call it creates the collection
	 * as an ArrayIndexeddCollection 
	 * @param child the Node that needs to be stored in the collection
	 */
	public void addChildNode(Node child) {
		if(children == null)
			children = new ArrayIndexedCollection();
		children.add(child);
	}
	
	/**
	 * Function that returns the current number of children nodes in the collection
	 * @return the size of the collection
	 */
	public int numberOfChildren() {
		return children.size();
	}
	
	/**
	 * Function that returns a child node from the collection at a specified index
	 * @param index the index at which the child node has been stored in the collection 
	 * @return the child node from the collection
	 */
	public Node getChild(int index) {
		if(index > numberOfChildren()) throw new IndexOutOfBoundsException();
		return (Node) children.get(index);
	}
}
