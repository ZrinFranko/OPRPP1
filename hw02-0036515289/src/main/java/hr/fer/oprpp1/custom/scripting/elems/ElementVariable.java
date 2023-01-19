package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Code showing the implementation of the ElementVariable class that inherits Element
 * @author zrin
 *
 */
public class ElementVariable extends Element{
	
	/**
	 * Private variable that stores the value of the Element
	 */
	private String name;
	
	/**
	 * Constructor of the ElementVariable class
	 * @param value value that gets stored in the Element
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	
	/**
	 * Function that returns the value of the element value
	 * @return value of the element type
	 */
	public String getName() {
		return this.name;
	}
	
	@Override
	public String asText() {
		return getName();
	}

}
