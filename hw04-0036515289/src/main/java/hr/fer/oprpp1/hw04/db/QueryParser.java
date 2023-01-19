package hr.fer.oprpp1.hw04.db;

import java.util.*;

public class QueryParser {

	List<ConditionalExpression> queries;
	QueryLexer lexer;

	public QueryParser(String line) {
		try {
			lexer = new QueryLexer(line);
			queries = initiateQueries();
		}catch(Exception e) {
			throw new ParserException(e.getMessage());
		}

	}

	private List<ConditionalExpression> initiateQueries() {
		if(!(lexer.nextToken().getValue().toString().equalsIgnoreCase("query"))) throw new IllegalArgumentException("Only query command is supported!"); //query word removed
		if(lexer.nextToken().getType() == QueryTokenType.EOQ) throw new ParserException("Missing command parameters");
		
		List<ConditionalExpression> tempList = new ArrayList<>();
		while(lexer.getToken().getType() != QueryTokenType.EOQ) {
			if(lexer.getToken().getType() == QueryTokenType.KEYWORD) {
				lexer.nextToken();
				continue;
			}
			if(lexer.getToken().getType() != QueryTokenType.FIELD) throw new IllegalArgumentException("Field can only be JMBAG, firstName, or lastName");
			tempList.add(findTheRestOfTheExpression());
			lexer.nextToken();
		}
		return tempList;
	}

	public boolean isDirectQuery() {
		if(queries.size() == 1) 
			return queries.get(0).getValueTested() == FieldValueGetters.JMBAG && queries.get(0).getOperator() == ComparisonOperators.EQUALS;
		return false;
	}

	public String getQueriedJMBAG() {
		if(!isDirectQuery()) throw new IllegalStateException();
		return queries.get(0).getLiteral();
	}

	public List<ConditionalExpression> getQueries() {
		return queries;
	}

	private ConditionalExpression findTheRestOfTheExpression() {
		if(lexer.getToken().getType() != QueryTokenType.FIELD) throw new ParserException("A field must first be stated in the query!");
		IFieldValueGetter getter = findValueGetter(lexer.getToken().getValue().toLowerCase());

		if(lexer.nextToken().getType() != QueryTokenType.OPERATOR) throw new ParserException("Invalid operator!");
		IComparisonOperator operator = findOperator(lexer.getToken().getValue().toLowerCase());

		if(lexer.nextToken().getType() != QueryTokenType.PATTERN) throw new ParserException("The search pattern is invalid!");

		return new ConditionalExpression(getter,lexer.getToken().getValue(),operator);

	}

	private IComparisonOperator findOperator(String currentValue) {
		switch (currentValue) {
		case "<":
			return ComparisonOperators.LESS;
		case "<=":
			return ComparisonOperators.LESS_OR_EQUALS;
		case ">":
			return ComparisonOperators.GREATER;
		case ">=":
			return ComparisonOperators.GREATER_OR_EQUALS;
		case "=":
			return ComparisonOperators.EQUALS;
		case "!=":
			return ComparisonOperators.NOT_EQUALS;
		case "like":
			return ComparisonOperators.LIKE;
		default:
			throw new ParserException("Illegal operator!");
		}
	}

	private IFieldValueGetter findValueGetter(String currentValue) {
		switch (currentValue) {
		case "jmbag":
			return FieldValueGetters.JMBAG;
		case "firstname":
			return FieldValueGetters.FIRST_NAME;
		case "lastname":
			return FieldValueGetters.LAST_NAME;
		default:
			throw new ParserException("Can only query on first name, last name, or jmbag of the student");
		}
	}

}
