package studentmanagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class Management {

	private Scanner unos = new Scanner(System.in);
	// Kolekcija koja sadrzi student objekte
	private ArrayList<Student> studentCollection;

	public Management() {
		// privremena lista studenata
		Student[] students = {
				new Student("44", "Daniel", "Molnar", "30.11.1983", "43-II"),
				new Student("23", "Damir", "Mesic", "01.05.1998", "23-I"),
				new Student("122", "Meris", "Kapetanovic", "16.06.1991", "4-II"),
				new Student("54", "Ljiljana", "Petrovic", "28.02.2001", "123-III")

		};
		studentCollection = new ArrayList<>(Arrays.asList(students));
	}

	/* Kreiranje novog studenta */
	public void newStudent() {

		String iD;
		String firstName;
		String lastName;
		String indexNumber;
		String dob;

		System.out.print("Unesite ID broj studenta");
		iD = unos.next();

		if (checkStudentID(iD)) {
			System.out.println("Student vec postoji u bazi podataka!");
			return;
		}

		System.out.print("Unesite ime studenta");
		firstName = unos.next();

		System.out.print("Unesite prezime studenta");
		lastName = unos.next();

		System.out.print("Unesite dob studenta");
		dob = unos.next();

		System.out.print("Unesite broj indeksa");
		indexNumber = unos.next();

		if (checkStudentIndex(iD)) {
			System.out.println("Student sa datim indeksom vec postoji u bazi podataka!");
			return;
		}

		Student student = new Student(iD, firstName, lastName, dob, indexNumber);

		studentCollection.add(student);

	}
    /* Provjeravanje student ID broja */
	public boolean checkStudentID(String Id) {

		Iterator<Student> iter = studentCollection.iterator();

		while (iter.hasNext()) {
			if (iter.next().getStudentId().equals(Id)) {
				return true;
			}
		}
		return false;
	}
	
	/* Provjera po student broju indeksa */
	public boolean checkStudentIndex(String index) {

		for (Student st : studentCollection) {
			if (st.getIndexNumber().equals(index)) {
				return true;
			}
		}
		return false;
	}
	
	/* Printanje svih studenata */
	public void printStudents() {

		if (studentCollection.size() > 0) {
			System.out.println("Lista studenta u bazi: ");
			studentCollection.stream().forEach(stud -> System.out.println(stud));
		} else {
			System.out.println("U bazi nema podataka o studentima!");
		}
	}

	/* Printanje individualnog studenta */
	public void printStudent(String id) {

		String newId = id;

		while (newId != null) {
			if (!checkStudentID(newId)) {
				System.out.println("Student sa datim ID brojem ne postoji u bazi!\n"
						+ "Da li zelite unijeti novi ID broj? (d-DA, n-NE");
				char potv = unos.next().charAt(0);
				if (potv == 'd' || potv == 'D') {
					newId = unos.next();
					continue;
				} else if (potv == 'n' || potv == 'N') {
					return;
				}
			} else {
				break;
			}
		}
		// printanje studenta
		System.out.println(returnStudent(id));
	}

	/* Dohvatanje student objekta iz kolekcije */
	private Student returnStudent(String id) {

		for (Student st : studentCollection) {
			if (st.getStudentId().equals(id)) {
				return st; // vracamo student objekat
			}
		}
		return null; // vracamo null
	}
	
	/* Editovanje Student objekta */
	public void editStudent(String id) {

		String newId = id;
		char changeChoice;

		Student tempSt = returnStudent(id);

		while (newId != null) {
			if (!checkStudentID(newId)) {
				System.out.println("Student sa datim ID brojem ne postoji u bazi!\n"
						+ "Da li zelite unijeti novi ID broj? (d-DA, n-NE");
				char potv = unos.next().charAt(0);
				if (potv == 'd' || potv == 'D') {
					newId = unos.next();
					continue;
				} else if (potv == 'n' || potv == 'N') {
					return;
				}
			} else {
				break;
			}
		}

		printChangeChoice();

		changeChoice = unos.next().charAt(0);

		switch (changeChoice) {
		
		case 'a':
			System.out.print("Unesite novi ID:");
			newId = unos.next();
			
			if (checkStudentID(newId)) {
				System.out.println("Postoji vec jedan student sa istim ID brojem!");
				return;
			} else {
				tempSt.setStudentId(newId);
			}
			
			break;
		case 'b':
			System.out.print("Unesite novo ime:");
			tempSt.setFirstName(unos.next());
			break;
		case 'c':
			System.out.print("Unesite novo prezime:");
			tempSt.setLastName(unos.next());
			break;
		case 'd':
			System.out.print("Unesite novi datum rodjenja:");
			tempSt.setDob(unos.next());
			break;
		case 'e':
			System.out.print("Unesite novi broj indeksa:");
			tempSt.setIndexNumber(unos.next());
			break;
		}
	}

	public void printChangeChoice() {

		System.out.println("Izaberiste sta zelite editovati:\n" +
							"a - ID broj\n" +
							"b - Ime\n" +
							"c - Pezime\n" +
							"d - Datum rodjenja\n" +
							"e - Broj indeksa");
	}

	/* Brisanje studenta iz kolekcije */
	public void deleteStudent(String id) {

		printStudent(id);

		Student tempSt = returnStudent(id);
		
		if(tempSt == null) return;		
		
		System.out.print("Da li ste sigurni da zelite obrisati studenta iz baze? (d-Da, n-NE");

		char potv = unos.next().charAt(0);

		if (potv == 'd' || potv == 'D') {
			studentCollection.remove(tempSt);
		} else if (potv == 'n' || potv == 'N') {
			return;
		}
	}

}
