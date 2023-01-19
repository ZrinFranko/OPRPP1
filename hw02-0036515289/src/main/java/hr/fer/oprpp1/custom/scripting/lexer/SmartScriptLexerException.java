package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Code showing the implementation of the SmartScriptLexerException class which inherits from the RuntimeException
 * @author zrin
 *
 */
public class SmartScriptLexerException extends RuntimeException{

	/**
	 * The serialVersionUID of the exception
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor of the SmartScriptLexer class
	 */
	public SmartScriptLexerException() {
		super();
	}
	/**
	 * Constructor of the SmartScriptLexer class which can generate a message for the user
	 * @param message the message generated for the user
	 */
	public SmartScriptLexerException(String message) {
		super(message);
	}
	
}
