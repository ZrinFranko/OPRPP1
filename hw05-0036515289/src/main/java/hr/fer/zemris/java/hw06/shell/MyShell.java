package hr.fer.zemris.java.hw06.shell;

import java.util.*;

import hr.fer.zemris.java.hw06.shell.commands.*;

public class MyShell {
	
	private static String PROMPTSYMBOL = ">";
	private static String MORELINESSYMBOL = "\\";
	private static String MULTILINESYMBOL = "|";
	
	private static SortedMap<String,ShellCommand> listOfCommands = new TreeMap<>(Map.of(
			"symbol",new ChangeSymbolCommand(),
			"charsets", new CheckCharsetsCommand(),
			"cat" ,new CatCommand(),
			"ls" ,new LSCommand(),
			"tree" ,new TreeCommand(),
			"copy", new CopyCommand(),
			"mkdir" ,new MkdirCommand(),
			"hexdump" ,new HexdumpCommand(),
			"help", new HelpCommand()));

	
	public static void main(String[] args) {
		
		Environment env = new Shell(listOfCommands);
		env.writeln("Welcome to MyShell v 1.0");
		ShellStatus currentStatus = ShellStatus.CONTINUE;
		do {
			String commandLine = env.readLine();
			int endOfCommandWord = commandLine.indexOf(" ");
			String userCommand = "";
			String commandArguments = "";
			if(endOfCommandWord != -1) {
				userCommand = commandLine.substring(0,endOfCommandWord);
				commandArguments = commandLine.substring(endOfCommandWord + 1).trim();
			}else {
				userCommand += commandLine;
			}
			if(userCommand.equalsIgnoreCase("exit")) {
				env.writeln("Goodbye");
				currentStatus = ShellStatus.TERMINATE;
			}else
				currentStatus = listOfCommands.getOrDefault(userCommand, new HelpCommand()).executeCommand(env, commandArguments);
		}while(currentStatus != ShellStatus.TERMINATE);
		
		
	}
	
}
