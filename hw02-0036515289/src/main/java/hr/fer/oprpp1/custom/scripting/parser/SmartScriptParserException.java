package hr.fer.oprpp1.custom.scripting.parser;

/**
 * Code showing the implementation of the SmartScriptParserException class which inherits from the RuntimeException class
 * @author zrin
 *
 */
public class SmartScriptParserException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor of the class
	 */
	public SmartScriptParserException() {
		super();
	}
	/**
	 * Constructor which generates an error message for the user 
	 * @param message
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

}
