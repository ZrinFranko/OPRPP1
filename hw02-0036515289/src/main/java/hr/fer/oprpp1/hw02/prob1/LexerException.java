package hr.fer.oprpp1.hw02.prob1;

/**
 * Code showing implementation of the LexerException class that inherits from the RuntimeException
 * @author zrin
 *
 */
public class LexerException extends RuntimeException{

	/**
	 * Value of the serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The default constructor of the LExerException class
	 */
	public LexerException() {
		super();
	}
	
	/**
	 * Constructor of the class which can return the error message to the user
	 * @param message
	 */
	public LexerException(String message) {
		super(message);
	}

}
