package hr.fer.zemris.java.hw06.shell;

import java.util.*;

public class Shell implements Environment{
	
	private static Character PROMPTSYMBOL = '>';
	private static Character MORELINESSYMBOL = '\\';
	private static Character MULTILINESYMBOL = '|';
	private static SortedMap<String, ShellCommand> commands;
	
	private final Scanner sc;

	/**
	 * The public constructor of the Shell class
	 * @param commands
	 */
	public Shell(SortedMap<String, ShellCommand> commands) {
		this.commands = Collections.unmodifiableSortedMap(commands);
		sc = new Scanner(System.in);
	}
	
	@Override
	public String readLine() throws ShellIOException {
		write(PROMPTSYMBOL + " ");
		StringBuilder sb = new StringBuilder();
		String currentLine = "";
		do {
			currentLine = sc.nextLine().trim();
			if(currentLine.endsWith(MORELINESSYMBOL.toString())) {
				sb.append(currentLine.substring(0, currentLine.length()-1));
				write(MULTILINESYMBOL + " ");
			}
			else{
				sb.append(currentLine);
			}			
		}while(currentLine.endsWith(MORELINESSYMBOL.toString()));		
		return sb.toString();
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return MULTILINESYMBOL;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		MULTILINESYMBOL = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return PROMPTSYMBOL;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		PROMPTSYMBOL = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return MORELINESSYMBOL;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		MORELINESSYMBOL = symbol;
	}

}
