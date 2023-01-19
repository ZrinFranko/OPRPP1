package hr.fer.oprpp1.hw02.prob1;

/**
 * Code showing implementation of the Lexer class
 * @author zrin
 *
 */
public class Lexer {

	/**
	 * Array that contains all the characters from the input string
	 */
	private char[] data;
	/**
	 * The reference to the last processed token
	 */
	private Token token; 

	/**
	 * The index of the first unprocessed character
	 */
	private int currentIndex;
	/**
	 * The current state of the lexer
	 */
	private LexerState currentState;

	/**
	 * Constructor of the Lexer class
	 * @param text the text that needs to be processed
	 */
	public Lexer(String text) {
		if(text == null) throw new NullPointerException("Cannot tokenize null string");

		this.currentIndex = 0;
		this.data = text.toCharArray();
		this.currentState = LexerState.BASIC;
	}
	
	/**
	 * The function that generates the next token from the data array
	 * @return the reference to the new token
	 */
	public Token nextToken() { 		
		if(this.token != null && this.token.getType() == TokenType.EOF) throw new LexerException("No more tokens left to give");

		int length = 0;
		TokenType thisType = TokenType.WORD;
		Object value = null;
		while(currentIndex < data.length && Character.isWhitespace(data[currentIndex])) currentIndex++;

		if(currentIndex >= data.length) {
			thisType = TokenType.EOF;
		}

		else if(currentState.equals(LexerState.BASIC)) {		
			if(Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
				thisType = TokenType.WORD;
				value = findTheRestOfTheWord();

			}else if(Character.isDigit(data[currentIndex])) {
				length = findTheRestOfTheNumber();
				thisType = TokenType.NUMBER;
				try {
					value = Long.valueOf(String.copyValueOf(data,currentIndex,length));
				}catch(NumberFormatException nfe) {
					throw new LexerException();
				}
			}else {
				length = 1;
				thisType = TokenType.SYMBOL;
				value = Character.valueOf(data[currentIndex]);
				if(value.equals('#')) setState(LexerState.EXTENDED);

			}

		}else {
			if(data[currentIndex] == '#') {
				length = 1;
				thisType = TokenType.SYMBOL;
				value = Character.valueOf(data[currentIndex]);
				setState(LexerState.BASIC);

			}else {
				while((currentIndex+length) < data.length && !Character.isWhitespace(data[currentIndex+length])) {
					if(data[currentIndex+length] == '#') break;
					length++;
				}
				value = String.copyValueOf(data, currentIndex, length);
			}
		}
		this.token = new Token(thisType,value);
		currentIndex+=length;
		return this.token;

	}

	/**
	 * Function that finds the last index of the valid character in the array
	 * @return string value of the word token type
	 */
	private String findTheRestOfTheWord() {
		StringBuilder sb = new StringBuilder();
		int i = 0;

		while(data.length > (currentIndex+i) && !Character.isWhitespace(data[currentIndex + i])) {
			if(Character.isLetter(data[currentIndex+i])) {
				sb.append(data[currentIndex+i]);
				i++;
			}
			else if(data[currentIndex+i] == '\\' && data.length > (currentIndex+i+1)) {
				if(Character.isDigit(data[currentIndex+i+1]) || data[currentIndex+i+1] == '\\') {
					sb.append(data[currentIndex+i+1]);
					i+=2;
				}else throw new LexerException();
			}
			else if(data[currentIndex+i] == '\\' && data.length <= (currentIndex+i+1) ) {
				throw new LexerException();
			}else break;
		}
		currentIndex +=i;
		return sb.toString();
	}

	/**
	 * Function that finds the last index of the number character in the array 
	 * @return the last index that contains a number
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
	 * Function that returns the value of the last processed token
	 * @return the value of the last processed token
	 */
	public Token getToken() { 
		return this.token;
	}

	/**
	 * Function that sets the state of the lexer
	 * @param state the next state of the lexer
	 */
	public void setState(LexerState state) {
		if(state == null) throw new NullPointerException("State cannot be null. It can only be BASIC or EXTENDED");
		this.currentState = state;
	}

}
