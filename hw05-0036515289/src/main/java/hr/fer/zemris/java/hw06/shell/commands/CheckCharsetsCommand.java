package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.*;
import hr.fer.zemris.java.hw06.shell.*;

/**
 * A class representing the charsets command of MyShell application
 * @author zrin
 *
 */
public class CheckCharsetsCommand implements ShellCommand {
	
	private static final String NAME = "charsets";
	private static final List<String> DESCRIPTION = new ArrayList<>(List.of(
			"Usage: charsets",
			"Command lists all of the avaliable charsets of the Java platform."));

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.equalsIgnoreCase(""))
			return env.commands().get("help").executeCommand(env, NAME);
		for(String c : Charset.availableCharsets().keySet()) {
			env.writeln(c);
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
