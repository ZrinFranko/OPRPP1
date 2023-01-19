package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Code showing the implementation of the ElementOperator class that inherits Element
 * @author zrin
 *
 */
public class ElementOperator extends Element{
	
	/**
	 * Private variable that stores the value of the Element
	 */
	private String symbol;
	
	/**
	 * Constructor of the ElementOperator class
	 * @param value value that gets stored in the Element
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Function that returns the value of the element value
	 * @return value of the element type
	 */
	public String getSymbol() {
		return this.symbol;
	}
	
	@Override
	public String asText() {
		return getSymbol();
	}
}
