package hr.fer.oprpp1.custom.scripting.lexer;


/**
 * Code showing the implementation of the SmartScriptLexerToken class
 * @author zrin
 *
 */
public class SmartScriptLexerToken {
	
	/**
	 * Type of the token
	 */
	private final SmartScriptLexerTokenType type;
	/**
	 * Value of the token
	 */
	private final Object value;
	
	/**
	 * Constructor of the SmartScriptLexerToken class
	 * @param type type of the token
	 * @param value value of the token
	 */
	public SmartScriptLexerToken(SmartScriptLexerTokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	/**
	 * Function that returns the value of the token
	 * @return value of the token
	 */
	public Object getValue() {return this.value;}
	/**
	 * Function that returns the type of the token
	 * @return type of the token
	 */
	public SmartScriptLexerTokenType getType() {return this.type;}
}
