package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import hr.fer.oprpp1.custom.scripting.lexer.*;

public class SmartScriptParserTest {
	
	@Test
	public void testNullInput() {
		assertThrows(SmartScriptLexerException.class, () -> new SmartScriptLexer(null));
	}
	
	@Test
	public void testSingleWordDocumentBody() {
		String docBody = "JAsmina";
		SmartScriptParser test = new SmartScriptParser(docBody);
		assertEquals(1,test.getNode().numberOfChildren());
		
	}
	
	@Test
	public void testMultilineDocumentBody() {
		String docBody = "Evo ja sad tu nesto \\$ radim";
		SmartScriptParser test = new SmartScriptParser(docBody);
		assertEquals(1,test.getNode().numberOfChildren());
	}
	
	@Test
	public void testTagDocumentBody() {
		String docBody = "Imam jedan tag {$ for i 1 10 1$} i to je to {$ end $}";
		SmartScriptParser test = new SmartScriptParser(docBody);
		assertEquals(2,test.getNode().numberOfChildren());
	}
	

}
