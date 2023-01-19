package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.*;

/**
 * A class representing the mkdir command of MyShell application
 * @author zrin
 *
 */
public class MkdirCommand implements ShellCommand{
	
	private static final String NAME = "mkdir";
	private static final List<String> DESCRIPTION = new ArrayList<>(List.of(
			"Usage: mkdir [DIRECTORY_NAME]",
			"Command creates a new directory with the [DIRECTORY_NAME] name and path",
			"[DIRECTORY_NAME] the name of the new directory and the path where it will be created"));

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.split(" ").length != 1)
			return env.commands().get("help").executeCommand(env, NAME);
		File newDirectory = new File(arguments);
		if(newDirectory.mkdir()) {
			env.writeln("Directory created succesfully!");
		}else
			env.writeln("Did not succed in creating the directory!");
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
