package hr.fer.oprpp1.hw04.db;

public class QueryLexer {

	/**
	 * Array that contains all the characters from the input string
	 */
	private char[] data;
	/**
	 * The reference to the last processed token
	 */
	private QueryToken token; 

	/**
	 * The index of the first unprocessed character
	 */
	private int currentIndex;

	public QueryLexer(String text) {
		if(text == null) throw new NullPointerException("Cannot tokenize null string");

		this.currentIndex = 0;
		this.data = text.toCharArray();
	}

	public QueryToken nextToken() {
		if(this.token != null && this.token.getType() == QueryTokenType.EOQ) throw new QueryLexerException("No more tokens left to give");

		int length = 0;
		QueryTokenType thisType = QueryTokenType.FIELD;
		String value = null;
		if(currentIndex >= data.length) {
			this.token = new QueryToken(QueryTokenType.EOQ,null);
			return this.token;
		}
		while(currentIndex < data.length && Character.isWhitespace(data[currentIndex])) currentIndex++;

		
		if(Character.isLetter(data[currentIndex])) {
			value = findTheWord();
			if(value.equalsIgnoreCase("and")) thisType = QueryTokenType.KEYWORD;
			else if(value.equalsIgnoreCase("like")) thisType = QueryTokenType.OPERATOR;
			else thisType = QueryTokenType.FIELD;
		}else if(data[currentIndex] == '"') {
			value = findThePattern();
			thisType = QueryTokenType.PATTERN;
		}else {

			value = findTheOperator();
			thisType = QueryTokenType.OPERATOR;

		}
		this.token = new QueryToken(thisType, value);
		return token;
	}



	private String findTheWord() {
		StringBuilder sb = new StringBuilder();
		int i = 0;

		while(data.length > (currentIndex+i) && !Character.isWhitespace(data[currentIndex + i])) {
			if(isOperatorCandidate(data[currentIndex+i])) break;
			else if(Character.isLetter(data[currentIndex+i])) {
				sb.append(data[currentIndex+i]);
				i++;
			}else throw new QueryLexerException("Invalid query!");
		}
		currentIndex +=i;
		return sb.toString();
	}


	private String findThePattern() {
		StringBuilder sb = new StringBuilder();
		int i = 1;
		while(data.length > (currentIndex+i) && data[currentIndex+i] != '"') {
			sb.append(data[currentIndex+i]);
			i++;
		}
		currentIndex+=(i+1);
		return sb.toString();
	}

	private String findTheOperator() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while(data.length > (currentIndex+i)) {
			if(isOperatorCandidate(data[currentIndex+i])) {
				sb.append(data[currentIndex+i]);
				i++;
			}else break;
		}
		if(i > 1) throw new QueryLexerException("Invalid query!");
		currentIndex+=i;
		return sb.toString();
	}

	private boolean isOperatorCandidate(char symbol) {
		if(symbol == '<') return true;
		if(symbol == '>') return true;
		if(symbol == '=') return true;
		if(symbol == '!') return true;
		return false;

	}
	
	public QueryToken getToken() {
		return token;
	}

}
