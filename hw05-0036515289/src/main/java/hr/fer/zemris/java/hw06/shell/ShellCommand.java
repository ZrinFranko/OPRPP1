package hr.fer.zemris.java.hw06.shell;

import java.util.*;
public interface ShellCommand {

	/**
	 * Function that starts a command and does its  function
	 * @param env environement in which the program is working
	 * @param arguments arguments of the command
	 * @return a ShellStatus depending on how the command is done
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	/**
	 * Function that returns the name of the command
	 * @return the name of the command
	 */
	String getCommandName();
	/**
	 * Function that returns the description and how-to-use text of the command
	 * @return
	 */
	List<String> getCommandDescription();
}
