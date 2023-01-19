package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ComparisonOperatorsTest {

	@Test
	public void testLessIfLess() {
		assertTrue(ComparisonOperators.LESS.satisfied("Ana","Jasna"));
	}
	
	@Test
	public void testLessIfGreater() {
		assertFalse(ComparisonOperators.LESS.satisfied("Jasna","Ana"));
	}
	
	@Test
	public void testLessIfEqual() {
		assertFalse(ComparisonOperators.LESS.satisfied("Ana","Ana"));
	}
	
	@Test
	public void testLessEqualsIfLess() {
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("Ana","Jasna"));
	}
	
	@Test
	public void testLessEqualsIfGreater() {
		assertFalse(ComparisonOperators.LESS_OR_EQUALS.satisfied("Jasna","Ana"));
	}
	
	@Test
	public void testLessEqualsIfEqual() {
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("Ana","Ana"));
	}
	
	@Test
	public void testGreaterIfGreater() {
		assertTrue(ComparisonOperators.GREATER.satisfied("Jasna","Ana"));
	}
	
	@Test
	public void testGreaterIfLess() {
		assertFalse(ComparisonOperators.GREATER.satisfied("Ana","Jasna"));
	}
	
	@Test
	public void testGreaterIfEqual() {
		assertFalse(ComparisonOperators.GREATER.satisfied("Ana","Ana"));
	}
	
	@Test
	public void testGreaterEqualsIfGreater() {
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Jasna","Ana"));
	}
	
	@Test
	public void testGreaterEqualsIfLess() {
		assertFalse(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Ana","Jasna"));
	}
	
	@Test
	public void testGreaterEqualsIfEqual() {
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Ana","Ana"));
	}
	
	@Test
	public void testEqualsIfEquals() {
		assertTrue(ComparisonOperators.EQUALS.satisfied("Ana","Ana"));
	}
	
	@Test
	public void testEqualsIfNotEquals() {
		assertFalse(ComparisonOperators.EQUALS.satisfied("Ana","Jasna"));
	}
	
	@Test
	public void testNotEqualsIfNotEquals() {
		assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("Ana","Jasna"));
	}
	
	@Test
	public void testNotEqualsIfEquals() {
		assertFalse(ComparisonOperators.NOT_EQUALS.satisfied("Ana","Ana"));
	}
	
	@Test
	public void testLikeFrontApostraphy() {
		assertTrue(ComparisonOperators.LIKE.satisfied("Anita", "*nita"));
	}
	
	@Test
	public void testLikeBackApostraphy() {
		assertTrue(ComparisonOperators.LIKE.satisfied("Anita", "An*"));
	}
	
	@Test
	public void testLikeMiddleApostraphy() {
		assertTrue(ComparisonOperators.LIKE.satisfied("Anita", "An*a"));
	}
}
