package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Code showing the implementation of the ElementConstantDouble class that inherits Element
 * @author zrin
 *
 */
public class ElementConstantDouble extends Element{
	
	/**
	 * Private variable that stores the value of the Element
	 */
	private double value;
	
	/**
	 * Constructor of the ElementConstantDouble class
	 * @param value value that gets stored in the Element
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Function that returns the value of the element value
	 * @return value of the element type double
	 */
	public double getValue() {
		return this.value;
	}
	
	@Override
	public String asText() {
		return String.valueOf(getValue());
	}
}
