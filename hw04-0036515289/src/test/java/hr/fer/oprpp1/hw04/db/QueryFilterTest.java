package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

public class QueryFilterTest {
	
	@Test
	public void testFilter() {
		//0000000003	Bosnić	Andrea	4
		List<ConditionalExpression> testList = List.of(new ConditionalExpression(FieldValueGetters.JMBAG, "0000000003", ComparisonOperators.EQUALS),new ConditionalExpression(FieldValueGetters.LAST_NAME,"Bos*",ComparisonOperators.LIKE));
		QueryFilter testFilter = new QueryFilter(testList);
		assertTrue(testFilter.accepts(new StudentRecord("0000000003","Bosnić","Andrea",4)));
	}

}
