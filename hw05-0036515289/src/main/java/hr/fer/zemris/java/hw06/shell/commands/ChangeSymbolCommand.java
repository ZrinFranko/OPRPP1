package hr.fer.zemris.java.hw06.shell.commands;

import java.util.*;
import hr.fer.zemris.java.hw06.shell.*;

/**
 * A class representing the symbol command of MyShell application
 * @author zrin
 *
 */
public class ChangeSymbolCommand implements ShellCommand{

	private Environment env;
	private static final String NAME = "symbol";
	private static final List<String> DESCRIPTION = new ArrayList<>(List.of(
			"Usage: symbol [PROGRAM_SYMBOL] [NEW_VALUE]",
			"Command switches the default value of the [PROGRAM_SYMBOL] into the [NEW_VALUE]",
			"If no [NEW_VALUE] is specified the command shows the current value of [PROGRAM_SYMBOL]",
			"[PROGRAM_SYMBOL]:",
			"\tPROMPT for the prompt symbol",
			"\tMULTILINE for the multiline symbol",
			"\tMORELINES for the more lines symbol",
			"[NEW_VALUE] a character which will be used for the specified symbol instead of the current one."));

	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		this.env = env;
		String[] commandArgs = arguments.split(" ");
		if(commandArgs.length == 1) {
			printCurrentSymbol(commandArgs[0]);
			return ShellStatus.CONTINUE;
		}
		changeSymbolValue(commandArgs[0],commandArgs[1].charAt(0));
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return DESCRIPTION;
	}

	private void printCurrentSymbol(String symbol) {
		env.writeln(getSymbolValue(symbol));
	}

	private String getSymbolValue(String symbol) {
		switch(symbol.toLowerCase()){
		case "prompt":
			return "Symbol for " + symbol.toUpperCase() + " is "  + env.getPromptSymbol().toString();	
		case "morelines":
			return "Symbol for " + symbol.toUpperCase() + " is "  + env.getMorelinesSymbol().toString();
		case "multiline":
			return "Symbol for " + symbol.toUpperCase() + " is "  + env.getMultilineSymbol().toString();
		default:
			return "Invalid symbol!";
		}
	}

	private void changeSymbolValue(String symbol,Character newValue) {
		switch(symbol.toLowerCase()){
		case "prompt":
			env.setPromptSymbol(newValue);
			break;
		case "morelines":
			env.setMorelinesSymbol(newValue);
			break;
		case "multiline":
			env.setMultilineSymbol(newValue);
			break;
		default:
			throw new ShellIOException();
		}
	}



}
