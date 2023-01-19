package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class StudentDatabaseTest {

	//List<String> testBase = null;
	StudentDatabase sb = new StudentDatabase(List.of("0000000001	Akšamović	Marin	2"
			,"0000000002	Bakamović	Petra	3","0000000015	Glavinić Pecotić	Kristijan	4"
			,"0000000029	Kos-Grabar	Ivo	2","0000000031	Krušelj Posavec	Bojan	4"));
	/*@BeforeEach
	public void makeInput() {
		
		try {
			testBase = Files.readAllLines(Paths.get("./database.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sb = new StudentDatabase(testBase);
	}*/
	@Test
	public void testForJmbag() {
		assertEquals("Kos-Grabar",sb.forJMBAG("0000000029").getLastName());
	}
	
	@Test
	public void testFilterAlwaysTrue() {
		assertEquals(5,sb.filter(r -> true).size());
	}
	
	@Test
	public void testFilterAlwaysFalse() {
		assertEquals(0, sb.filter(r -> false).size());
	}
}
