package hr.fer.oprpp1.hw04.db;

import java.util.*;

public class QueryFilter implements IFilter{
	
	List<ConditionalExpression> filteringExpressions;

	public QueryFilter(List<ConditionalExpression> expressions) {
		filteringExpressions = expressions;
	}
	@Override
	public boolean accepts(StudentRecord record) {
		for(ConditionalExpression condiExp : filteringExpressions) {
			if(!(condiExp.getOperator().satisfied(condiExp.getValueTested().get(record), condiExp.getLiteral()))) return false;
		}
		return true;
	}

}
