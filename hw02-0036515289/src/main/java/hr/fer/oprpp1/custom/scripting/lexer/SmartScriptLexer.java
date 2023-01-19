package hr.fer.oprpp1.custom.scripting.lexer;


/**
 * Code describing the implemenetation of the SmartScriptLexer class
 * @author zrin
 *
 */
public class SmartScriptLexer {
	
	/**
	 * Private array containing all the input chracters
	 */
	private char[] data;
	/**
	 * reference to the current token the lexer has processed
	 */
	private SmartScriptLexerToken token;
	/**
	 * Index of the first unprocessed character in the array
	 */
	private int currentIndex;
	/**
	 * Current state of the lexer
	 */
	private SmartScriptLexerState currentState;

	/**
	 * Constructor of the SmartScriptLexer class
	 * @param text input text that needs to be processed
	 */
	public SmartScriptLexer(String text) {
		if(text==null) throw new SmartScriptLexerException("Cannot initialize lexer with null text value");

		this.currentIndex = 0;
		this.currentState = SmartScriptLexerState.BASIC;
		this.data = text.toCharArray();
	}

	/**
	 * Function that generates a new token from the input data array
	 * @return reference to the new token that has been processed
	 */
	public SmartScriptLexerToken nextToken() {
		if(this.token != null && this.token.getType() == SmartScriptLexerTokenType.EOF) throw new SmartScriptLexerException("No more tokens left to give");

		int length = 0;
		SmartScriptLexerTokenType thisType = SmartScriptLexerTokenType.WORD;
		Object value = null;
		while(currentIndex < data.length && Character.isWhitespace(data[currentIndex])) currentIndex++;

		if(currentIndex >= data.length) {
			thisType = SmartScriptLexerTokenType.EOF;
		}

		else if(currentState.equals(SmartScriptLexerState.BASIC)) {		
			if(Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
				thisType = SmartScriptLexerTokenType.WORD;
				value = findTheRestOfTheWord();

			}else if(Character.isDigit(data[currentIndex])) {
				length = findTheRestOfTheNumber();
				thisType = SmartScriptLexerTokenType.NUMBER;
				try {
					value = Long.valueOf(String.copyValueOf(data,currentIndex,length));
				}catch(NumberFormatException nfe) {
					throw new SmartScriptLexerException();
				}
			}else {
				length = 1;
				thisType = SmartScriptLexerTokenType.SYMBOL;
				value = Character.valueOf(data[currentIndex]);

			}

		}else {
			if(data[currentIndex] == '$') {
				length = 1;
				thisType = SmartScriptLexerTokenType.SYMBOL;
				value = Character.valueOf(data[currentIndex]);

			}else if(data[currentIndex] == '"') {
				while((currentIndex+length) < data.length && data[currentIndex+length] != '"') {
					length++;
				}
				value = String.copyValueOf(data, currentIndex, length);
			}else {
				while((currentIndex+length) < data.length && !Character.isWhitespace(data[currentIndex+length])) {
					if(data[currentIndex+length] == '$') break;
					length++;
				}
				value = String.copyValueOf(data, currentIndex, length);
			}
		}
		this.token = new SmartScriptLexerToken(thisType,value);
		currentIndex+=length;
		return this.token;
	} 

	/**
	 * Private function that finds the last index of the word before the whitespace in the input array 
	 * @return string value of the word type token
	 */
	private String findTheRestOfTheWord() {
		StringBuilder sb = new StringBuilder();
		int i = 0;

		while(data.length > (currentIndex+i) && !Character.isWhitespace(data[currentIndex + i])) {
			if(Character.isLetter(data[currentIndex+i])) {
				sb.append(data[currentIndex+i]);
				i++;
			}else if(data[currentIndex] == '\\' && data.length > (currentIndex+i+1)){
				if(data.length > (currentIndex+i+1)) {
					if(data[currentIndex+i+1] == '\\' || data[currentIndex+i+1] == '$') {
						sb.append(data[currentIndex]).append(data[currentIndex+i+1]);
						i+=2;
					}else throw new SmartScriptLexerException("You can only escape \\ and $ signs");
				}else throw new SmartScriptLexerException("Escape sign must have a trailling symbol");
			}else break;
		}
		currentIndex +=i;
		return sb.toString();
	}

	/**
	 * Function that finds the last index at which the number type token can be created
	 * @return the last index in the array that contains a number
	 */
	private int findTheRestOfTheNumber() {
		int i = 0;
		while(data.length > (currentIndex+i) && !Character.isWhitespace(data[currentIndex + i])) {
			if(Character.isDigit(data[currentIndex+i])) i++;
			else break;
		}
		return i;
	}

	/**
	 * Function that switches the current state of the lexer
	 * @param state the next state of the lexer class
	 */
	public void setState(SmartScriptLexerState state) {
		if(state == null) throw new NullPointerException("State can only be TAG or BASIC");
		this.currentState = state;

	}

	/**
	 * Function that returns the value of the last processed token
	 * @return value of the last processed token
	 */
	public SmartScriptLexerToken getToken() {
		return this.token;
	}
}
