package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Code showing the implementation of the ElementConstantInteger class that inherits Element
 * @author zrin
 *
 */
public class ElementConstantInteger extends Element{
	
	/**
	 * Private variable that stores the value of the Element
	 */
	private int value;
	
	/**
	 * Constructor of the ElementConstantInteger class
	 * @param value value that gets stored in the Element
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * Function that returns the value of the element value
	 * @return value of the element type integer
	 */
	public int getValue() {
		return this.value;
	}
	
	@Override
	public String asText() {
		return String.valueOf(getValue());
	}
}
