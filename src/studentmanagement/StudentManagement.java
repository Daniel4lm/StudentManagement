package studentmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class StudentManagement {

	private Scanner unos = new Scanner(System.in);
	// Kolekcija koja sadrzi student objekte
	private ArrayList<Student> studentCollection;

	private static final String userName = "root";
	private static final String password = "43OsLeOlRoEtXe43";
	// localhost//imeBazeNaKojuSeSpajamo
	private static final String CONN_STRING = "jdbc:mysql://localhost/StudentManagement?useSSL=false&serverTimezone=UTC";
	// error? dodati na kraj stringa ?useSSL=false&serverTimezone=UTC

	private Connection connDB = null;
	private Statement queryStatement;
	private ResultSet setResults;

	public StudentManagement() {

		openDBConnection();

		// privremena lista studenata
		Student[] students = { new Student(44, "Daniel", "Molnar", "30.11.1983", "43-II", 4),
				new Student(23, "Damir", "Mesic", "01.05.1998", "23-I", 10),
				new Student(122, "Meris", "Kapetanovic", "16.06.1991", "4-II", 5),
				new Student(54, "Ljiljana", "Petrovic", "28.02.2001", "123-III", 6)

		};
		studentCollection = new ArrayList<>(Arrays.asList(students));
	}

	/* Pokusaj spajanja sa bazom podataka za studente */
	private boolean openDBConnection() {

		try {
			connDB = DriverManager.getConnection(CONN_STRING, userName, password);
			return true;
		} catch (SQLException sqle) {
			System.err.println(sqle);
		}

		return false;
	}

	/* Zatvaranje konekcije sa bazom podataka i otpustanje resursa */
	public void closeDBConnection() {
		try {
			connDB.close();
		} catch (SQLException sqle) {
			System.err.println(sqle);
		}
	}

	/* Kreiranje novog studenta */
	public void newStudent() {
		
		int updateValue = 0;
		
		try {
			System.out.print("Unesite ID broj studenta\n> ");
			Integer iD = unos.nextInt();

			if (checkDBStudent(iD)) {
				System.out.print("Student vec postoji u bazi podataka!");
				return;
			}

			System.out.print("Unesite ime studenta:\n> ");
			String firstName = unos.next();

			System.out.print("Unesite prezime studenta:\n> ");
			String lastName = unos.next();

			System.out.print("Unesite datum rodjenja studenta:\n> ");
			String dob = unos.next();

			System.out.print("Unesite broj indeksa:\n> ");
			String indexNumber = unos.next();

			System.out.print("Unesite oznaku mjesta rodjenja:\n" + "'1', 'Banovici'\n" + "'2', 'Bihac'\n"
						+ "'3', 'Banja Luka'\n" + "'4', 'Bijeljina'\n" + "'5', 'Brcko'\n" + "'6', 'Doboj'\n"
						+ "'7', 'Gradacac'\n" + "'8', 'Gracanica'\n" + "'9', 'Jajce'\n" + "'10', 'Kladanj'\n"
						+ "'11', 'Livno'\n" + "'12', 'Lukavac'\n" + "'13', 'Mostar'\n" + "'14', 'Sarajevo'\n"
						+ "'15', 'Sanski Most'\n" + "'16', 'Travnik'\n" + "'17', 'Tuzla'\n" + "'18', 'Zenica'\n" + "> ");
			Integer ID_mjesto = unos.nextInt();
		
			/* Dodavanje zapisa o studentu u tabelu */		
			queryStatement = connDB.createStatement();
			updateValue = queryStatement.executeUpdate("INSERT INTO student VALUES (" + iD + ",'" + firstName
					+ "','" + lastName + "','" + dob + "','" + indexNumber + "'," + ID_mjesto + ")");			
			
			/* Dodavanje studenta u kolekciju podataka */
			Student student = new Student(iD, firstName, lastName, dob, indexNumber, ID_mjesto);
			studentCollection.add(student);
			
		} catch (InputMismatchException inputEx) {
			
			System.out.println("Pogresan unos informacija! ");
			
		} catch (SQLException sqlInsert) {
			/* Provjera da li je query izvrsen */
			if (updateValue == 1) {
				System.out.println("Student uspjesno upisan u tabelu!");
			} else {
				System.out.println("<< UPIS NIJE IZVRSEN >>");
				System.err.println(sqlInsert);
			}
		} finally {
			try {
				queryStatement.close();
			} catch (SQLException e) {
			}
		}
	}

	/* Provjeravanje da li zapis o studentu vec postoji u bazi podataka */
	private boolean checkDBStudent(Integer ID) {

		try {
			queryStatement = connDB.createStatement();
			setResults = queryStatement.executeQuery("SELECT ID_student FROM student WHERE ID_student = " + ID);
			setResults.next();

			if (setResults.getRow() > 0) {
				return true;
			}

		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			try {
				setResults.close();
				queryStatement.close();
			} catch (SQLException sql2) {
				System.err.println(sql2);
			}
		}

		return false;
	}

	/* Provjeravanje da li student objekat vce postoji u kolekciji prema ID broju */
	private boolean checkStudentID(Integer Id) {

		Iterator<Student> iter = studentCollection.iterator();

		while (iter.hasNext()) {
			if (iter.next().getStudentId() == Id) {
				return true;
			}
		}
		return false;
	}

	/* Provjera prema broju indeksa */
	private boolean checkStudentIndex(String index) {

		for (Student st : studentCollection) {
			if (st.getIndexNumber().equals(index)) {
				return true;
			}
		}
		return false;
	}

	/* Ispis svih zapisa o studentima iz baze podataka */
	public void printAllDBStudents() {

		try {
			queryStatement = connDB.createStatement();
			setResults = queryStatement.executeQuery("SELECT * FROM student");

			System.out.printf("| %-3s| %-15s| %-15s| %-15s| %-10s| %-3s|%n", "ID", "First name", "Last name", "DOB",
					"Inx number", "Mjesto rodjenja");

			while (setResults.next()) {
				System.out.printf("| %-3d| %-15s| %-15s| %-15s| %-10s| %-3d|%n", setResults.getInt(1),
						setResults.getString(2), setResults.getString(3), setResults.getDate(4),
						setResults.getString(5), setResults.getInt(6));
			}
		} catch (SQLException printe) {
			System.err.println(printe);
		} finally {
			try {
				queryStatement.close();
				setResults.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
	}

	/* Ispis svih zapisa o studentima iz baze podataka */
	public void printFromToDBStudents(Integer x, Integer n) {

		String rowCounter = "SELECT COUNT(*) FROM student";
		ResultSet counterResult = null;
		int rowCount;

		try {
			counterResult = connDB.createStatement().executeQuery(rowCounter);
			counterResult.next();
			rowCount = counterResult.getInt(1);
			System.out.println("Broj zapisa u tabeli je: " + rowCount + "\n");

			if (rowCount == 0 || rowCount <= x) {
				System.out.println("U bazi ne postoje zapisi u odabranom opsegu!");
				return;
			}

			queryStatement = connDB.createStatement();
			setResults = queryStatement.executeQuery("SELECT * FROM student LIMIT " + (x - 1) + ", " + (n - x + 1));

			System.out.printf("| %-3s| %-15s| %-15s| %-15s| %-10s| %-3s|%n", "ID", "First name", "Last name", "DOB",
					"Ind number", "Mjesto rodjenja");

			while (setResults.next()) {
				System.out.printf("| %-3d| %-15s| %-15s| %-15s| %-10s| %-3d|%n", setResults.getInt(1),
						setResults.getString(2), setResults.getString(3), setResults.getDate(4),
						setResults.getString(5), setResults.getInt(6));
			}

		} catch (SQLException printe) {
			System.err.println(printe);
		} finally {
			try {
				counterResult.close();
				queryStatement.close();
				setResults.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
	}

	public void printStudentByPlace(String placeName) {

		String joinQuery = "SELECT student.*, mjesto.naziv FROM student "
				+ "INNER JOIN mjesto ON mjesto.ID_mjesto = student.mjesto_rodjenja " + "WHERE mjesto.naziv = '"
				+ placeName + "'";
		try {
			queryStatement = connDB.createStatement();
			setResults = queryStatement.executeQuery(joinQuery);

			System.out.printf("| %-3s| %-15s| %-15s| %-15s| %-10s| %-3s |%n", "ID", "First name", "Last name", "DOB",
					"Ind number", "Mjesto rodjenja");

			while (setResults.next()) {
				System.out.printf("| %-3d| %-15s| %-15s| %-15s| %-10s| %-10s |%n", setResults.getInt(1),
						setResults.getString(2), setResults.getString(3), setResults.getDate(4),
						setResults.getString(5), setResults.getString(7));
			}

		} catch (SQLException joinEx) {
			System.err.println(joinEx);
		} finally {
			try {
				queryStatement.close();
				setResults.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}

	}

	/* Printanje svih studenata iz kolekcije podataka */
	public void printStudents() {

		if (studentCollection.size() > 0) {
			System.out.println("Lista studenta u bazi: ");
			studentCollection.stream().forEach(stud -> System.out.println(stud));
		} else {
			System.out.println("U bazi nema podataka o studentima!");
		}
	}

	/* Printanje individualnog studenta */
	public void printStudent(Integer Id) {

		Integer newId = Id;

		while (newId != null) {
			if (!checkDBStudent(newId)) {
				System.out.println("Student sa datim ID brojem ne postoji u bazi!\n"
						+ "Da li zelite unijeti novi ID broj? (d-DA, n-NE");
				char potv = unos.next().charAt(0);
				if (potv == 'd' || potv == 'D') {
					newId = unos.nextInt();
					continue;
				} else if (potv == 'n' || potv == 'N') {
					return;
				}
			} else {
				break;
			}
		}

		try {
			queryStatement = connDB.createStatement();
			setResults = queryStatement.executeQuery("SELECT * FROM student WHERE ID_Student = " + newId);

			System.out.printf("| %-3s| %-15s| %-15s| %-15s| %-10s| %-3s|%n", "ID", "First name", "Last name", "DOB",
					"Ind number", "Mjesto rodjenja");
			setResults.next();
			System.out.printf("| %-3d| %-15s| %-15s| %-15s| %-10s| %-3d|%n", setResults.getInt(1),
					setResults.getString(2), setResults.getString(3), setResults.getDate(4), setResults.getString(5),
					setResults.getInt(6));

		} catch (SQLException printe) {
			System.err.println(printe);
		} finally {
			try {
				queryStatement.close();
				setResults.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}

		// printanje studenta
		// System.out.println(returnStudent(id));
	}

	/* Dohvatanje student objekta iz kolekcije */
	private Student returnStudent(Integer id) {

		for (Student st : studentCollection) {
			if (st.getStudentId() == id) {
				return st; // vracamo student objekat
			}
		}
		return null; // vracamo null
	}

	/* Editovanje Student objekta */
	public void editStudent(Integer Id) {

		Integer newId = Id;
		char changeChoice;
		// Student tempSt = returnStudent(id);
		String changeQuery;
		PreparedStatement changeStatement = null;

		while (newId != null) {
			if (!checkDBStudent(newId)) {
				System.out.println("Student sa datim ID brojem ne postoji u bazi!\n"
						+ "Da li zelite unijeti novi ID broj? (d-DA, n-NE");
				char potv = unos.next().charAt(0);
				if (potv == 'd' || potv == 'D') {
					newId = unos.nextInt();
					continue;
				} else if (potv == 'n' || potv == 'N') {
					return;
				}
			} else {
				break;
			}
		}

		try {

			printChangeChoice(); // Printanje izbora za promjenu podataka o studentu

			changeChoice = unos.next().charAt(0); // Unos izbora za izmjenu

			switch (changeChoice) {

			case 'a':
				System.out.print("Unesite novo ime:");
				changeQuery = "UPDATE student SET firstName = ? WHERE ID_Student = " + Id;
				changeStatement = connDB.prepareStatement(changeQuery);
				changeStatement.setString(1, unos.next());
				// tempSt.setFirstName(unos.next());
				break;
			case 'b':
				System.out.print("Unesite novo prezime:");
				changeQuery = "UPDATE student SET lastName = ? WHERE ID_Student = " + Id;
				changeStatement = connDB.prepareStatement(changeQuery);
				changeStatement.setString(2, unos.next());
				// tempSt.setLastName(unos.next());
				break;
			case 'c':
				System.out.print("Unesite novi datum rodjenja:");
				changeQuery = "UPDATE student SET dob = ? WHERE ID_Student = " + Id;
				changeStatement = connDB.prepareStatement(changeQuery);
				changeStatement.setString(3, unos.next());
				// tempSt.setDob(unos.next());
				break;
			case 'd':
				System.out.print("Unesite novi broj indeksa:");
				changeQuery = "UPDATE student SET indexNumber = ? WHERE ID_Student = " + Id;
				changeStatement = connDB.prepareStatement(changeQuery);
				changeStatement.setString(4, unos.next());
				// tempSt.setIndexNumber(unos.next());
				break;
			}
			changeStatement.executeUpdate();
			System.out.println("Update se izvrsava ...");

		} catch (SQLException changeSQL) {
			System.err.println(changeSQL);
		} finally {
			try {
				changeStatement.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}

	}

	public void printChangeChoice() {
		System.out.println("Izaberiste sta zelite editovati:\n" + "a - Ime\n" + "b - Pezime\n" + "c - Datum rodjenja\n"
				+ "d - Broj indeksa");
	}
	
	/* Funkcija koja vraca max ID iz tabele o studentima */
	private Integer returnMaxID(Integer Id) throws SQLException  {
		
		String maxQuery = "SELECT MAX(ID_student) FROM student";				
		Statement maxStatement = connDB.createStatement();
		ResultSet max = maxStatement.executeQuery(maxQuery);
		max.next();
		Integer maxEl = max.getInt(1);
		maxStatement.close();
		max.close();
		return maxEl;	
					
	}
	
	/* Funkcija koja izvrasava reset AUTO_INCREMENT svaki put kada se izvrsi delete statement iz tabele */
	private void auto_increment_Reset(Integer Id) throws SQLException {
		
		String alterQuery = "ALTER TABLE student AUTO_INCREMENT = " + returnMaxID(Id);
		Statement alterStatement = connDB.createStatement();
		alterStatement.executeUpdate(alterQuery);		
		alterStatement.close();
	}

	/* Brisanje studenta iz kolekcije */
	public void deleteStudent(Integer Id) {

		/*
		 * Student tempSt = returnStudent(id);
		 * 
		 * if (tempSt == null) return;
		 */

		String deleteQuery = "DELETE FROM student WHERE ID_student = " + Id;
		Statement deleteStatement = null;
		Integer deleteValue = 0, maxEl = 0;
		
		try {

			deleteStatement = connDB.createStatement();

			if (checkDBStudent(Id)) {

				printStudent(Id);
				System.out.print("Da li ste sigurni da zelite obrisati studenta iz baze? (d-Da, n-NE)");
				char potv = unos.next().charAt(0);

				if (potv == 'd' || potv == 'D') {
					// studentCollection.remove(tempSt);
					deleteValue = deleteStatement.executeUpdate(deleteQuery);
					
					if (deleteValue == 1) {
							System.out.println("Student sa ID brojem " + Id + " je uspjesno obrisan iz baze podataka!");
					}
					//System.out.println("Max element u tabeli je: " + returnMaxID(Id));
					auto_increment_Reset(Id);

				} else if (potv == 'n' || potv == 'N') {
					return;
				}
				
			} else {
				
				System.out.println("Student sa ID brojem " + Id + " ne postoji u bazi podataka!");
				
			}

		} catch (SQLException deleteSQL) {
				System.out.println("<< BRISANJE IZ TABELE NIJE IZVRSENO >>");
				System.err.println(deleteSQL);
		} finally {
			try {
				deleteStatement.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
	}

}
