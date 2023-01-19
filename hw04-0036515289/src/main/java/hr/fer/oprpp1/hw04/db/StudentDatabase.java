package hr.fer.oprpp1.hw04.db;

import java.util.*;

public class StudentDatabase {
	
	Map<String,StudentRecord> studentRecords;
	
	public StudentDatabase(List<String> fileLines) {
		studentRecords = new HashMap<String,StudentRecord>();
		generateRecords(fileLines);
	}
	
	private void generateRecords(List<String> lines) {
		for(String line : lines) {
			String[] student = line.split("\t");
			if(studentRecords.containsKey(student[0])) throw new IllegalArgumentException("JMBAG of the student should be unique!");
			int grade;
			try {
				grade = Integer.parseInt(student[3]);
			}catch(NumberFormatException e) {
				throw new IllegalArgumentException("The grade of the student can only be a number");
			}
			if(grade < 1 || grade > 5) throw new IllegalArgumentException("Grade of the student can only be between 1 and 5!");
			studentRecords.put(student[0], new StudentRecord(student[0],student[2],student[1],grade));
		}
	}
	
	public StudentRecord forJMBAG(String JMBAG) {
		if(studentRecords.containsKey(JMBAG)) return studentRecords.get(JMBAG);
		return null;
	}
	
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> temp = new ArrayList<>();
		for(StudentRecord sr : studentRecords.values()) {
			if(filter.accepts(sr)) temp.add(sr);
		}
		return temp;
	}

}
