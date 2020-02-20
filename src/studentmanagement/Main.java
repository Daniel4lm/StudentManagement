package studentmanagement;

import java.util.Scanner;

public class Main {
	
	Scanner unos = new Scanner(System.in);
	
	private void printMenu() {
		
		System.out.println("\n Odaberite izbor:\n"
				+ "1 - Unos novog studenta u bazu\n"
				+ "2 - Ispisati listu svih studenata u bazi\n"
				+ "3 - Ispisivanje podataka individualnog student\n"
				+ "4 - Editovanje postojeceg studenta\n"
				+ "5 - Brisanje studenta iz baze podataka\n"
				+ "0 - kraj programa");		
	}
	
	public void printChoice() {
		
		int choice = 0;
		String idStud;
		
		StudentManagement baza = new StudentManagement();
				
		do {
			
			printMenu();
			
			choice = unos.nextInt();
			
			switch (choice) {
			
			case 1:				
				baza.newStudent();				
				break;
			case 2:				
				baza.printStudents();				
				break;
			case 3:
				
				System.out.print("Unesite ID studenta:");
				idStud = unos.next();
								
				baza.printStudent(idStud);
				
				break;
			case 4:
				
				System.out.print("Unesite ID studenta:");
				idStud = unos.next();
				
				baza.editStudent(idStud);
				
				break;
			case 5:
				
				System.out.print("Unesite ID studenta:");
				idStud = unos.next();
				
				baza.deleteStudent(idStud);
				
				break;
			case 0:
				
				System.out.println("Izlaz iz programa ...");
				break;
			}
			
			//printMenu();			
			
		} while (choice != 0);
		
	}

	public static void main(String[] args) {
		
		new Main().printChoice(); // kreiranje objekta i printanje glavnog izbornika
		
	}

}
