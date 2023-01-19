package hr.fer.oprpp1.hw04.db;

public class QueryToken {
	
	private QueryTokenType type;
	private String value;
	
	public QueryToken(QueryTokenType type, String value) {
		this.type = type;
		this.value = value;
	}

	public QueryTokenType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}
}
