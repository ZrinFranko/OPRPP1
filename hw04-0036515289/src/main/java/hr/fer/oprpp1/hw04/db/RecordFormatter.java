package hr.fer.oprpp1.hw04.db;

import java.util.*;

public class RecordFormatter {

	public static List<String> format(List<StudentRecord> studentRecords) {
		List<String> temp = new ArrayList<>();
		if(studentRecords.size() < 1) {
			temp.add("Records selected: 0");
			return temp;
		}
		int[] border = findLongestEntries(studentRecords);
		temp.add(makeBorder(border));
		for(StudentRecord sr : studentRecords) {
			temp.add("| " + sr.getJMBAG() + " | " + sr.getLastName() + " | " + sr.getFirstName() + " | " + sr.getGrade() + " |");
		}
		temp.add(makeBorder(border));
		temp.add("Records selected: " + studentRecords.size());
		return temp;
	}

	private static String makeBorder(int[] border) {
		StringBuilder sb = new StringBuilder();
		for(int i : border) {
			sb.append("+");
			for(int j = 0; j < i ; j++) sb.append("=");
		}
		sb.append("+");
		return sb.toString();
	}

	private static int[] findLongestEntries(List<StudentRecord> studentRecords) {
		int[] temp = new int[] {12,0,0,3};
		for(StudentRecord sr : studentRecords) {
			if(sr.getLastName().length() > temp[1]) temp[1] = sr.getLastName().length();
			if(sr.getFirstName().length() > temp[2]) temp[2] = sr.getFirstName().length();
		}
		temp[1] +=2;
		temp[2] +=2;
		return temp;
	}

}
