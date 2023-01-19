package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

public interface Environment {

	/**
	 * Function that reads the next lines of the commands from the user
	 * @return A string representing the users input
	 * @throws ShellIOException
	 */
	String readLine() throws ShellIOException;
	/**
	 * Function that writes the specified text back to the user
	 * @param text the text that will be printed to the user
	 * @throws ShellIOException
	 */
	void write(String text) throws ShellIOException;
	/**
	 * Function that writes the specified text in a new line back to the user
	 * @param text the text that will be printed to the user 
	 * @throws ShellIOException
	 */
	void writeln(String text) throws ShellIOException;
	/**
	 * A function that returns a sorted map of the commands
	 * @return
	 */
	SortedMap<String, ShellCommand> commands();
	/**
	 * A function that returns the current value of the MULTILINE symbol
	 * @return
	 */
	Character getMultilineSymbol();
	/**
	 * A function that set a new value of the MULTILINE symbol
	 * @param symbol the new value of the MULTILINE symbol
	 */
	void setMultilineSymbol(Character symbol);
	/**
	 * A function that returns the current value of the PROMPT symbol
	 * @return
	 */
	Character getPromptSymbol();
	/**
	 * A function that set a new value of the PROMPT symbol
	 * @param symbol the new value of the PROMPT symbol
	 */
	void setPromptSymbol(Character symbol);
	/**
	 * A function that returns the current value of the MORELINES symbol
	 * @return
	 */
	Character getMorelinesSymbol();
	/**
	 * A function that set a new value of the MORELINES symbol
	 * @param symbol the new value of the MORELINES symbol
	 */
	void setMorelinesSymbol(Character symbol);
}
