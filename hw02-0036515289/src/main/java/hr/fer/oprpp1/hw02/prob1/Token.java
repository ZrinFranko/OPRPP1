package hr.fer.oprpp1.hw02.prob1;

/**
 * Code showing the implementation of the Token class
 * @author zrin
 *
 */
public class Token {
	
	/**
	 * type of the token
	 */
	private final TokenType type;
	/**
	 * value of the token
	 */
	private final Object value;
	
	/**
	 * Constructor of the Token class
	 * @param type type of the new token
	 * @param value value of the new token
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	/**
	 * Getter of the value of the token
	 * @return the value of the token 
	 */
	public Object getValue() {return this.value;}
	/**
	 * Getter of the token type
	 * @return type of the token
	 */
	public TokenType getType() {return this.type;}
}
