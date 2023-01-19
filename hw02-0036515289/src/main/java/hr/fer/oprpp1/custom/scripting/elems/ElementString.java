package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Code showing the implementation of the ElementString class that inherits Element
 * @author zrin
 *
 */
public class ElementString extends Element{
	
	/**
	 * Private variable that stores the value of the Element
	 */
	private String value;
	
	/**
	 * Constructor of the ElementString class
	 * @param value value that gets stored in the Element
	 */
	public ElementString(String value) {
		this.value = value;
	}
	
	/**
	 * Function that returns the value of the element value
	 * @return value of the element type
	 */
	public String getValue() {
		return this.value;
	}
	
	@Override
	public String asText() {
		return getValue();
	}
}
