package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * A class representing the hexdump command of MyShell application
 * @author zrin
 *
 */
public class HexdumpCommand implements ShellCommand{
	
	private static final String NAME = "hexdump";
	private static final List<String> DESCRIPTION = new ArrayList<>(List.of(
			"Usage: hexdump [FILE_NAME]",
			"Command produces a hex-output of the specified [FILE_NAME]",
			"[FILE_NAME] the name and path of the file whose hex-output the user wishes to see."));

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.split(" ").length != 1 || Files.isDirectory(Path.of(arguments)))
			return env.commands().get("help").executeCommand(env, NAME);
		Path filePath = Path.of(arguments);
		List<String> output = generateHexdump(filePath);
		for(String o : output)
			env.writeln(o);
		return null;
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return DESCRIPTION;
	}
	
	/**
	 * Auxiliary function that generates a hexdump of the user specified file
	 * @param file the file whose hexdump the program needs to generate
	 * @return a list of strings representing values of the hexdump command
	 */
	private List<String> generateHexdump(Path file){
		List<String> temp = new ArrayList<>();
		try(InputStream fileData = new BufferedInputStream(Files.newInputStream(file))){
			byte[] currentRead = new byte[16];
			for(int j = 0, i = fileData.read(currentRead) ; i > -1 ; i = fileData.read(currentRead),j++) {
				temp.add(formatString(currentRead,j));
			}
			fileData.close();
		} catch (IOException e) {
			return new ArrayList<String>(List.of(e.getMessage()));
		}
		return temp;
	}

	/**
	 * Auxiliary function that formats the string in a certain way that is easier to the read to the user
	 * @param currentRead A byte array of currently read bytes from the file
	 * @param nRead the number of times a read has happened
	 * @return a string representing the hexdump operation result
	 */
	private String formatString(byte[] currentRead, int nRead) {
		String offset = String.format("%08x", currentRead.length*nRead);
		String limitter = "|";
		String byteOutput = "";
		String actualValues = "";
		for(int i = 0; i < currentRead.length ; i++) {
			if(i == 7)
				byteOutput += limitter;
			byteOutput += String.format(" %02X", currentRead[i]);
			if(currentRead[i] >= 0x20 && currentRead[i] <= 0x7F) {
				actualValues += String.format("%c", currentRead[i]);
			}else
				actualValues += ".";
				
		}
		
		return offset + ":" + byteOutput + " " +  limitter + " " + actualValues;
	}

}
