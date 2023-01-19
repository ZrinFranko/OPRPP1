package hr.fer.oprpp1.hw04.db;

public class ConditionalExpression {
	
	private IFieldValueGetter valueTested;
	private String literal;
	private IComparisonOperator operator;
	
	public ConditionalExpression(IFieldValueGetter valueTested, String literal, IComparisonOperator operator) {
		super();
		this.valueTested = valueTested;
		this.literal = literal;
		this.operator = operator;
	}
	
	public IFieldValueGetter getValueTested() {
		return valueTested;
	}
	public String getLiteral() {
		return literal;
	}
	public IComparisonOperator getOperator() {
		return operator;
	}	

}
