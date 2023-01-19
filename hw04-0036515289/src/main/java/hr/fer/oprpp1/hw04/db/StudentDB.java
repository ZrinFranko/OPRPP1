package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class StudentDB {
	
	private static StudentDatabase database;
	
	public static void main(String[] args) {
		database = new StudentDatabase(readFileFromFolder());
		Scanner scanner = new Scanner(System.in);
		String line = null;
		System.out.print("> ");
		
		while(scanner.hasNextLine()) {
			line = scanner.nextLine();
			
			if(line.equalsIgnoreCase("exit")) {
				System.out.println("Goodbye!");
				scanner.close();
				return;
			}
			
			QueryParser queryParser = new QueryParser(line);
			
			if(queryParser != null) {
				List<StudentRecord> srList;
				
				if(queryParser.isDirectQuery()) {
					System.out.println("Using index for record retrieval.");
					srList = new ArrayList<>();
					srList.add(database.forJMBAG(queryParser.getQueriedJMBAG()));
				}else {
					srList = database.filter(new QueryFilter(queryParser.getQueries()));
				}
				
				List<String> output = RecordFormatter.format(srList);
				for(String outputLine : output) System.out.println(outputLine);
			}
			System.out.print("> ");
		}
	}

	private static List<String> readFileFromFolder() {
		try {
			return Files.readAllLines(Path.of("/home/zrin/Documents/OPRPP1/hw04-0036515289/src/main/java/hr/fer/oprpp1/hw04/db/database.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
