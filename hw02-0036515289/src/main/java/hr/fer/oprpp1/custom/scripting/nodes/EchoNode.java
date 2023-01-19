package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;

/**
 * Code showing the implementation of the EchoNode class 
 * @author zrin
 *
 */
public class EchoNode extends Node{
	
	/**
	 * An array of elements that the node contains
	 */
	private Element[] elements;
	
	/**
	 * Constructor of the echo node class
	 * @param elements the array of elements that the node contains
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}
	
	/**
	 * Function that returns all of the elements stored in the node
	 * @return an array of elements stored
	 */
	public Element[] getElements() {
		return this.elements;
	}
	
}
