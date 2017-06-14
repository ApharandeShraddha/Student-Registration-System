import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;

public class Entry {
	
	
	
	
	static int choice;
	

	private static void displayOptions() {
		
		System.out.println("\n");
		System.out.println("------------------------------------------------------------------------------------");
		System.out.println("1: Display table names");
		System.out.println("2: Show classes where Student is enrolled");
		System.out.println("3: Dependent courses");
		System.out.println("4: List students enroll for classid(display classid and course title");
		System.out.println("5: Enroll Student in to Class ");
		System.out.println("6: Drop Student enrollment");
		System.out.println("7: Delete Student ");
		System.out.println("Enter your choice:");
	}

	public static void main(String[] args) {
		
		
		
		
		try {
		do {
			displayOptions();
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
				choice = Integer.parseInt(reader.readLine());
			
			switch (choice) {
			case 1:
				DisplayTableContent dtc = new DisplayTableContent();
				dtc.display();
				break;
			case 2:
				ShowClasses sc = new ShowClasses();
				sc.get_showStudentClasses();
				
				break;
			case 3:
				
				DependentCourses dc = new DependentCourses();
				dc.get_dependentCourses();				
				break;
			case 4:
				ListClassStudents ls = new ListClassStudents();
				ls.doOperation();
				
				break;
			case 5:
				EnrollStudent e = new EnrollStudent();
				e.doOperation();
				
				break;
			case 6:
				dropStudentEnrollments dse = new dropStudentEnrollments();
				dse.doOperation();
				break;
			case 7:
				dropStudent ds = new dropStudent();
				ds.doOperation();
				break;
			default:
					System.out.println("Invalid choice");
	                
					break;
			}
		} while (choice < 7 || choice > 0);
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
	
	
	

	
}


