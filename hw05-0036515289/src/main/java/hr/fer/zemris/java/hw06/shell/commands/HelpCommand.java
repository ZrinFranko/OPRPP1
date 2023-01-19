package hr.fer.zemris.java.hw06.shell.commands;

import java.util.*;

import hr.fer.zemris.java.hw06.shell.*;

/**
 * A class representing the help command of MyShell application
 * @author zrin
 *
 */
public class HelpCommand implements ShellCommand{

	private static final String NAME = "help";
	private static final List<String> DESCRIPTION = new ArrayList<>(List.of(
			"Usage: help [COMMAND_NAME]",
			"Command prints the [COMMAND_NAME] and it's description to the user.",
			"If no [COMMAND_NAME] is given a list of all possible command will be listed.",
			"[COMMAND_NAME] the name of the command whose description the user want to be listed"));

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.equals("")) {
			env.writeln("List of MyShell commands: ");
			for(String command : env.commands().keySet()) {
				env.writeln(command);
			}
		}
		else {
			if(env.commands().containsKey(arguments)) {
				for(String descriptionText : env.commands().get(arguments).getCommandDescription()) 
					env.writeln(env.getMultilineSymbol() +  descriptionText);
			}
			if(!env.commands().containsKey(arguments)) {
				env.writeln("Illegal command name!");
			}
		}
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

}
