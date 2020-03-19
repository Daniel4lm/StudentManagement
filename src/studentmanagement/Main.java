package studentmanagement;

import java.util.Scanner;

public class Main {
	
	Scanner unos = new Scanner(System.in);
	
	private void printMenu() {
		
		System.out.println("\n Odaberite izbor:\n"
				+ "1 - Unos novog studenta u bazu\n"
				+ "2 - Ispisati listu studenata u bazi\n"
				+ "3 - Ispisivanje podataka individualnog studenta\n"
				+ "4 - Editovanje postojeceg studenta\n"
				+ "5 - Brisanje studenta iz baze podataka\n"
				+ "0 - kraj programa");	
	}
	
	public void printChoice() {
		
		int choice = 0;
		Integer idStud;
		
		StudentManagement baza = new StudentManagement();
				
		do {
			
			printMenu();
			
			choice = unos.nextInt();
			
			switch (choice) {
			
			case 1:		
				
				baza.newStudent();				
				break;
				
			case 2:	
				
				System.out.println("Odaberite opciju:\n"
									+ "  a - Lista svih studenata\n"
									+ "  b - Lista od - do studenata\n"
									+ "  c - Lista po mjestu rodjenja\n");
				
				char opc = unos.next().charAt(0);
				if(opc == 'a') {
					baza.printAllDBStudents();				
				} else if(opc == 'b') {
					System.out.println("Odaberite opseg redova za ispis iz tabele: od(X) do(N)");
					int x = unos.nextInt(), n = unos.nextInt();
					baza.printFromToDBStudents(x, n);
				} else if(opc == 'c') {
					System.out.print("Unesite naziv mjesta stanovanja studenta: ");
					baza.printStudentByPlace(unos.next());
				}
				break;
				
			case 3:
				
				System.out.print("Unesite ID studenta:");
				idStud = unos.nextInt();								
				baza.printStudent(idStud);				
				break;
				
			case 4:
				
				System.out.print("Unesite ID studenta:");
				idStud = unos.nextInt();				
				baza.editStudent(idStud);				
				break;
				
			case 5:
				
				System.out.print("Unesite ID studenta:");
				idStud = unos.nextInt();				
				baza.deleteStudent(idStud);				
				break;
				
			case 0:
				
				System.out.println("Zatvaram bazu posataka ...");
				baza.closeDBConnection();
				System.out.println("Izlaz iz programa ...");
				break;
			}
						
		} while (choice != 0);
		
	}

	public static void main(String[] args) {
		
		new Main().printChoice(); // kreiranje objekta i printanje glavnog izbornika
		
	}

}
