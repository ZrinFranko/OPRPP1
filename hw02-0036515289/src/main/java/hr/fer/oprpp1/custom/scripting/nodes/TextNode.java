package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Code showing the implementation of the TextNode class 
 * @author zrin
 *
 */
public class TextNode extends Node{
	
	/**
	 * Private variable of the value of the TextNode
	 */
	private String text;
	
	/**
	 * Constructor of the TextNode class
	 * @param text the text that is stored into the node
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	/**
	 * Function that returns the variable value as a String
	 * @return the value of the text variable 
	 */
	public String getText() {
		return this.text;
	}

}
