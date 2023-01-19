package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import hr.fer.zemris.java.hw06.shell.*;

/**
 * A class representing the cat command of MyShell application
 * @author zrin
 *
 */
public class CatCommand implements ShellCommand{
	
	private static final String NAME = "cat";
	private static final List<String> DESCRIPTION = new ArrayList<>(List.of(
			"Usage: cat [PATH] [CHARSET]",
			"Command opens the [PATH] file, reads it using [CHARSET] and writes it on the console.",
			"[PATH]\t the path to the file that the user wants to read. MANDATORY!",
			"[CHARSET]\t the charset used to interpret the bytes in the file.\n\tIf no [CHARSET] is given the console will use the default Java platform charset."));

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] commandArgs = arguments.split(" ");
		Path filePath = Path.of(commandArgs[0]);
		Charset charset;
		if(commandArgs.length == 2)
			charset = Charset.forName(commandArgs[1]);
		else
			charset = Charset.defaultCharset();
		try {
			List<String> fileLines = Files.readAllLines(filePath, charset);
			fileLines.forEach(l -> env.writeln(l));
		}catch(IOException e) {
			env.writeln(e.getMessage());
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
