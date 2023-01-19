package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.List;
import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;

/**
 * Code showing the implementation of the SmartScriptParser class
 * @author zrin
 *
 */
public class SmartScriptParser {

	/**
	 * Private lexer of the class
	 */
	private SmartScriptLexer lexer;
	/**
	 * Private node of the DocumentNode type 
	 */
	private DocumentNode docNode;
	
	/**
	 * Public constructor of the SmartScriptParser class
	 * @param documentBody the text of the whole document
	 */
	public SmartScriptParser(String documentBody) {
		if(documentBody == null) throw new SmartScriptParserException("The value of the document body cannot be null");
		lexer = new SmartScriptLexer(documentBody);
		docNode = new DocumentNode();
		generateParserNodes();

	}

	/**
	 * Function that returns the value of the Node stored inside the object class
	 * @return
	 */
	public DocumentNode getNode() {
		return this.docNode;
	}
	
	/**
	 * Function that parses the whole text of the document through the lexer and creates a tree of nodes connected to the DocumentNode
	 */
	private void generateParserNodes() {
		try {
			lexer.setState(SmartScriptLexerState.BASIC);
			SmartScriptLexerToken firstToken = lexer.nextToken();
			SmartScriptLexerToken current = firstToken;
			

			while(lexer.getToken().getType() != SmartScriptLexerTokenType.EOF) {
				if(current.getValue().equals('{')) {
					current = lexer.nextToken();
					if(current.getValue().equals('$')) {
						int whichTag = findNextTag();
						if(whichTag == 0) {
							docNode.addChildNode(generateForLoopNode());
						}else if(whichTag == 1){
							docNode.addChildNode(generateEchoNode());
							lexer.nextToken();
						}else {
							throw new SmartScriptParserException("Invalid tag starter!");
						}
					}else {
						throw new SmartScriptParserException("Tag not opened correctly!");
					}
					if(lexer.getToken().getType() == SmartScriptLexerTokenType.EOF) break;
					current = lexer.nextToken();					
				}else {
					docNode.addChildNode(generateTextNode());
					current = lexer.getToken();
				}
				
			}
		}catch(Exception e) {
			throw new SmartScriptParserException("Something went wrong");
		}

	}

	/**
	 * Mini function that helps understand which tag is used in the text
	 * @return the index of the node defined 0 = ForLoopNode 1 = EchoNode and -1 if the tag is invalid
	 */
	private int findNextTag() {
		String firstThing = (String)lexer.nextToken().getValue();
		
		if(firstThing.equalsIgnoreCase("for")) return 0;
		if (firstThing.equalsIgnoreCase("=")) return 1;
		return -1;
	}

	/**
	 * Function that generates a new EchoNode using the lexer of the class
	 * @return new node of type EchoNode
	 */
	private Node generateEchoNode() {
		List elements = new ArrayIndexedCollection();
		while(!lexer.getToken().getValue().equals('$')) {
			elements.add((ElementString)lexer.getToken().getValue());
			lexer.nextToken();
		}
		return new EchoNode((ElementString[]) elements.toArray());
	}

	/**
	 * Function that genrates a new ForLoopNode using the lexer of the class
	 * @return a new node of the ForLoopNode type
	 */
	private Node generateForLoopNode() {
		Element start,end,step;
		lexer.setState(SmartScriptLexerState.TAG);
		SmartScriptLexerToken current = lexer.nextToken();
		ElementVariable elVar;
		ForLoopNode node;
		
		if(checkVarName(String.valueOf(current.getValue())))
			elVar = new ElementVariable(String.valueOf(current.getValue()));
		else throw new SmartScriptParserException("Invalid varible name in for loop");
		
		current = lexer.nextToken();
		if(current.getType() != SmartScriptLexerTokenType.SYMBOL) {
			if(current.getType() == SmartScriptLexerTokenType.NUMBER) start = new ElementConstantInteger((int) current.getValue());
			else start = new ElementString(String.valueOf(current.getValue()));
		}else throw new SmartScriptParserException("Invalid start expression in for loop");
		
		current = lexer.nextToken();
		if(current.getType() != SmartScriptLexerTokenType.SYMBOL) {
			if(current.getType() == SmartScriptLexerTokenType.NUMBER) end = new ElementConstantInteger((int) current.getValue());
			else end = new ElementString(String.valueOf(current.getValue()));
		}else throw new SmartScriptParserException("Invalid end expression in for loop");
		
		current = lexer.nextToken();
		if(current.getType() == SmartScriptLexerTokenType.SYMBOL) step = null;
		else {
			if(current.getType() == SmartScriptLexerTokenType.NUMBER) step = new ElementConstantInteger((int) current.getValue());
			else step = new ElementString(String.valueOf(current.getValue()));
			lexer.nextToken();
		}
		lexer.nextToken();
		lexer.setState(SmartScriptLexerState.BASIC);
		node = new ForLoopNode(elVar,start,end,step);
		node.addChildNode(generateTextNode());
		while(!lexer.getToken().getValue().equals('}')) lexer.nextToken();
		return node;
	}

	/**
	 * Function that check if the supplied variable name is valid
	 * @param string the name of the variable
	 * @return true if the name is valid and false otherwise
	 */
	private boolean checkVarName(String string) {
		char[] characters = string.toCharArray();
		if(Character.isLetter(characters[0])) return true;
		return false;
	}

	/**
	 * Function that generates a new TextNode using the lexer of the class
	 * @return a new node of the TextNode type
	 */
	private Node generateTextNode() {
		StringBuilder sb = new StringBuilder();
		while(lexer.getToken().getType() != SmartScriptLexerTokenType.EOF && !lexer.getToken().getValue().equals('{')) {
			switch(lexer.getToken().getType()) {
			case WORD:
				sb.append(String.valueOf(lexer.getToken().getValue())).append(" ");
				break;
			case SYMBOL:
				sb.append(String.valueOf(lexer.getToken().getValue()));
				break;
			default:
				sb.append(String.valueOf(lexer.getToken().getValue())).append(" ");
				break;								
			}
			lexer.nextToken();						
		}
		return new TextNode(sb.toString());
	}

}
