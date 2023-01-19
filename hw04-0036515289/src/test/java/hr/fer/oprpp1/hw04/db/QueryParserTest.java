package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class QueryParserTest {
	
	@Test
	public void testEmptyParserQuery() {
		assertThrows(ParserException.class, () -> new QueryParser(""));
	}
	
	@Test
	public void testCommandWithoutParameters() {
		assertThrows(ParserException.class, () -> new QueryParser("query"));
	}
	
	@Test
	public void testIsDirectQueryIfDirectQuery() {
		QueryParser testParser = new QueryParser("query jmbag = \"0000000000\"");
		assertTrue(testParser.isDirectQuery());
	}
	
	@Test
	public void testIsDirectQueryIfNotDirectQuery() {
		QueryParser testParser = new QueryParser("query jmbag = \"0000000000\" and firstName = \"Ana\"");
		assertFalse(testParser.isDirectQuery());
	}
	
	@Test
	public void testGetQueriedJmbagIfDirectQuery() {
		QueryParser testParser = new QueryParser("query jmbag = \"0000000000\"");
		assertEquals("0000000000",testParser.getQueriedJMBAG());
	}
	
	@Test
	public void testGetQueriedJmbagIfNotDirectQuery() {
		QueryParser testParser = new QueryParser("query jmbag = \"0000000000\" and firstName = \"Ana\"");
		assertThrows(IllegalStateException.class, () -> testParser.getQueriedJMBAG());
	}
}
