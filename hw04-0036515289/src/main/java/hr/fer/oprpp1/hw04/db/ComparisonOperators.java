package hr.fer.oprpp1.hw04.db;

import java.util.regex.Pattern;

public class ComparisonOperators {

	public static final IComparisonOperator LESS = (v1,v2) -> v1.compareTo(v2) < 0;
	public static final IComparisonOperator LESS_OR_EQUALS = (v1,v2) -> v1.compareTo(v2) <= 0;
	public static final IComparisonOperator GREATER = (v1,v2) -> v1.compareTo(v2) > 0;
	public static final IComparisonOperator GREATER_OR_EQUALS = (v1,v2) -> v1.compareTo(v2) >= 0;
	public static final IComparisonOperator EQUALS = (v1,v2) -> v1.compareTo(v2) == 0;
	public static final IComparisonOperator NOT_EQUALS = (v1,v2) -> v1.compareTo(v2) != 0;
	public static final IComparisonOperator LIKE = new IComparisonOperator() {
		
		@Override
		public boolean satisfied(String value1/*stvarna npr AAAAAA*/, String value2/*patern npr AAA*AA*/) {
			String temp = value2.replaceAll("[*]", "\\\\S*");
			return Pattern.matches(temp, value1);		
		}

	};



}
