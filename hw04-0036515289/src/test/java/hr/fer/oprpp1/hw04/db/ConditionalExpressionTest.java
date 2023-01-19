package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ConditionalExpressionTest {
	
	ConditionalExpression testExpression = new ConditionalExpression(FieldValueGetters.LAST_NAME,
			"Hib*",
			ComparisonOperators.LIKE);
	StudentRecord testRecord = new StudentRecord("0000000020","Hibner","Sonja",5);

	@Test
	public void testExpression() {
		assertTrue(testExpression.getOperator().satisfied(testExpression.getValueTested().get(testRecord), testExpression.getLiteral()));
	}
}
