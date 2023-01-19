package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw06.shell.*;

/**
 * A class representing the cat command of MyShell application
 * @author zrin
 *
 */
public class CopyCommand implements ShellCommand{
	
	private static final String NAME = "copy";
	private static final List<String> DESCRIPTION = new ArrayList<>(List.of(
			"Usage: copy [SOURCE_FILE] [DESTINATION_FILE]",
			"Command copies the [SOURCE_FILE] into the [DESTINATION_FILE]",
			"[SOURCE_FILE] the name and path of the file that needs to be copied",
			"[DESTINATION_FILE] the name and path to the file where the [SOURCE_FILE] will be copied\n\t If the [DESTINATION_FILE] is a directory the [SOURCE_FILE] will just be copied into it using the original file name."));
	

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] commandArgs = arguments.split(" ");
		Path sourceFile = Path.of(commandArgs[0]);
		Path destinationFile = Path.of(commandArgs[1]);
		if(Files.isDirectory(destinationFile))
			destinationFile = Path.of(destinationFile.toString() + "/" + sourceFile.getFileName().toString());
		if(checkAlreadyExists(destinationFile)) {
			env.writeln("File already exists. Do you wish to overwrite it? [y/n]");
			String answer = env.readLine();
			if(answer.equals("n"))
				return ShellStatus.CONTINUE;
		}
		try(InputStream sourceInput = new BufferedInputStream(Files.newInputStream(sourceFile),1024); OutputStream destinationOutput = new BufferedOutputStream(Files.newOutputStream(destinationFile),1024)){
			byte[] currentRead = new byte[1024];
			for(int i = sourceInput.read(currentRead) ; i > -1 ; i = sourceInput.read(currentRead)) {
				destinationOutput.write(currentRead);
			}
		} catch (IOException e) {
			env.writeln("Error copying " + sourceFile.getFileName().toString() + " to " + destinationFile.toString());
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}
		env.writeln("File " + sourceFile.getFileName().toString() + " succesfully copied into " + destinationFile.toString());
		return ShellStatus.CONTINUE;
	}

	/**
	 * Auxiliary function that check is the file with the ”[DESTINATION_NAME] already exists
	 * @param checkFile
	 * @return
	 */
	private boolean checkAlreadyExists(Path checkFile) {
		String fileName = checkFile.getFileName().toString();
		int positionOfLastSlash = checkFile.toString().lastIndexOf("/");
		String directory = checkFile.toString().substring(0, positionOfLastSlash);
		try {
			List<Path> paths = Files.list(checkFile).collect(Collectors.toList());
			for(Path p : paths) {
				if(p.getFileName().equals(fileName))
					return true;
			}
		}catch(IOException e) {
			return false;
		}
		return false;
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
