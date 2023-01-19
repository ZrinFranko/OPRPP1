package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw06.shell.*;

/**
 * A class representing the ls command of MyShell application
 * @author zrin
 *
 */
public class LSCommand implements ShellCommand{
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final String NAME = "ls";
	private static final List<String> DESCRIPTION = new ArrayList<>(List.of(
			"Usage: ls [DIRECTORY_PATH]",
			"Command writes on the console the directory listing",
			"[DIRECTORY_PATH] the path to the directory whose contents will be listed"));

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Path directoryPath = Path.of(arguments);
		if(Files.isDirectory(directoryPath)) {
			try {
				List<String> output = processPaths(Files.list(directoryPath).collect(Collectors.toList()));
				for(String s : output) {
					env.writeln(s);
				}
			}catch(IOException e) {
				env.writeln(e.getMessage());
			}
		}else 
			return env.commands().get("help").executeCommand(env, NAME);
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * An auxiliary function that uses the Directory listing of the directory path and processes each file separately 
	 * @param directoryList the listing of the directory
	 * @return a list of strings representing the attributes of all the files in the directory listing
	 */
	private List<String> processPaths(List<Path> directoryList) {
		List<String> outputLines = new ArrayList<>();
		for(Path p : directoryList) {
			try {
				BasicFileAttributeView faView = Files.getFileAttributeView(p, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
				BasicFileAttributes attributes = faView.readAttributes();
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				String fileInfo = getFileInfo(p) + " ";
				fileInfo += String.format("%10s", attributes.size());
				fileInfo += " " + formattedDateTime + " " + p.getFileName();
				outputLines.add(fileInfo);
			} catch (IOException e) {
				return new ArrayList<>(List.of(e.getMessage()));
			}
		}
		return outputLines;
	}
	
	/**
	 * Auxiliary function that gives a small string of attributes of the selected file
	 * @param p The path to the file whose info we needs to aquire
	 * @return a String of the attribute values
	 */
	private String getFileInfo(Path p) {
		String line = "";
		if(Files.isDirectory(p)) line += "d";
		else line += "-";
		if(Files.isReadable(p)) line += "r";
		else line += "-";
		if(Files.isWritable(p)) line += "w";
		else line += "-";
		if(Files.isExecutable(p)) line += "x";
		else line += "-";
		return line;
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
