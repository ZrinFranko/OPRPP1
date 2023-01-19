package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

public class StudentRecord {
	private String JMBAG;
	private String firstName;
	private String lastName;
	private int grade;

	public StudentRecord(String JMBAG,String lastName,String firstName,int grade) {
		this.JMBAG = JMBAG;
		this.firstName = firstName;
		this.lastName = lastName;
		if(grade > 5 || grade < 1) throw new IllegalArgumentException("Grade can only be between 1 and 5");
		this.grade = grade;
	}

	public String getJMBAG() {
		return JMBAG;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getGrade() {
		return grade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(JMBAG);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		return Objects.equals(JMBAG, other.JMBAG);
	}
}
