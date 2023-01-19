package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw06.shell.*;
/**
 * A class representing the tree command of MyShell application
 * @author zrin
 *
 */
public class TreeCommand implements ShellCommand{

	private static final String NAME = "tree";
	private static final List<String> DESCRIPTION = new ArrayList<>(List.of(
			"Usage: tree [DIRECTORY]",
			"Command prints a tree of the [DIRECTORY]",
			"[DIRECTORY] the tree of the directory that needs to be printed"));


	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.split(" ").length > 1)
			return env.commands().get("help").executeCommand(env, NAME);
		Path startingDirectory = Path.of(arguments);
		env.writeln(makeTree(startingDirectory,0));
		return ShellStatus.CONTINUE;
	}

	/**
	 * A recurssive auxiliary function that creates a tree of the user defined directory
	 * @param currentDirectory
	 * @param depth
	 * @return
	 */
	private String makeTree(Path currentDirectory,int depth) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < depth * 2 ; i ++)
			sb.append(" ");
		sb.append(currentDirectory.getFileName().toString() + "\n");
		try {
			for(Path p : Files.list(currentDirectory).collect(Collectors.toList())) {
				if(Files.isDirectory(p)) {
					sb.append(makeTree(p,depth+1));
				}else {
					for(int i = 0; i < (depth+1)*2 ; i++)
						sb.append(" ");
					sb.append(p.getFileName().toString() + "\n");
				}
			}
		} catch (IOException e) {
			return e.getMessage();
		}

		return sb.toString();
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
