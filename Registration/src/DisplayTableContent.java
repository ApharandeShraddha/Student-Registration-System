import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

public class DisplayTableContent {

	Connection conn = Util.getDBConnection();
	ResultSet rs;
	static int choice;

	// Return all tuples from students table
	public ResultSet get_students() {

		try {
			conn = Util.getDBConnection();

			CallableStatement cs = conn.prepareCall("begin ? := PROJECT3.get_students(); end;");

			// register the out parameter (the first parameter)
			cs.registerOutParameter(1, OracleTypes.CURSOR);

			// execute and retrieve the result set
			cs.execute();
			rs = (ResultSet) cs.getObject(1);

			// print the results
			while (rs.next()) {
				System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + rs.getString(4)
						+ "\t" + rs.getDouble(5) + "\t" + rs.getString(6)+ "\t" + rs.getDate(7)+ "\t" + rs.getString(8));
			}

			conn.close();
			cs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return rs;

	}

	public ResultSet get_prerequisites() {

		try {
			conn = Util.getDBConnection();

			CallableStatement cs = conn.prepareCall("begin ? := PROJECT3.get_prerequisites(); end;");

			// register the out parameter (the first parameter)
			cs.registerOutParameter(1, OracleTypes.CURSOR);

			// execute and retrieve the result set
			cs.execute();
			rs = (ResultSet) cs.getObject(1);

			// print the results
			while (rs.next()) {
				System.out.println(rs.getString(1) + "\t" + rs.getInt(2) + "\t" + rs.getString(3) + rs.getInt(4));
			}

			conn.close();
			cs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return rs;

	}

	public ResultSet get_logs() {

		try {

			conn = Util.getDBConnection();
			CallableStatement cs = conn.prepareCall("begin ? := PROJECT3.get_logs(); end;");

			// register the out parameter (the first parameter)
			cs.registerOutParameter(1, OracleTypes.CURSOR);

			// execute and retrieve the result set
			cs.execute();
			rs = (ResultSet) cs.getObject(1);

			// print the results
			while (rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getDate(3) + "\t" + rs.getString(4)
						+ "\t" + rs.getString(5) + "\t" + rs.getString(6));
			}

			conn.close();
			cs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return rs;

	}

	public ResultSet get_grades() {

		try {

			conn = Util.getDBConnection();
			CallableStatement cs = conn.prepareCall("begin ? := PROJECT3.get_grades(); end;");

			// register the out parameter (the first parameter)
			cs.registerOutParameter(1, OracleTypes.CURSOR);

			// execute and retrieve the result set
			cs.execute();
			rs = (ResultSet) cs.getObject(1);

			// print the results
			while (rs.next()) {
				System.out.println(rs.getString(1) + "\t" + rs.getDouble(2));

			}
			conn.close();
			cs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return rs;

	}

	public ResultSet get_enrollments() {

		try {

			conn = Util.getDBConnection();
			CallableStatement cs = conn.prepareCall("begin ? := PROJECT3.get_enrollments(); end;");

			// register the out parameter (the first parameter)
			cs.registerOutParameter(1, OracleTypes.CURSOR);

			// execute and retrieve the result set
			cs.execute();
			rs = (ResultSet) cs.getObject(1);

			// print the results
			while (rs.next()) {
				System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3));

			}
			conn.close();
			cs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return rs;

	}

	public ResultSet get_courses() {

		try {

			conn = Util.getDBConnection();
			CallableStatement cs = conn.prepareCall("begin ? := PROJECT3.get_courses(); end;");

			// register the out parameter (the first parameter)
			cs.registerOutParameter(1, OracleTypes.CURSOR);

			// execute and retrieve the result set
			cs.execute();
			rs = (ResultSet) cs.getObject(1);

			// print the results
			while (rs.next()) {
				System.out.println(rs.getString(1) + "\t" + rs.getInt(2) + "\t" + rs.getString(3));

			}
			conn.close();
			cs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return rs;

	}

	public ResultSet get_courseCredit() {

		try {
			conn = Util.getDBConnection();

			CallableStatement cs = conn.prepareCall("begin ? := PROJECT3.get_courseCredit(); end;");

			// register the out parameter (the first parameter)
			cs.registerOutParameter(1, OracleTypes.CURSOR);

			// execute and retrieve the result set
			cs.execute();
			rs = (ResultSet) cs.getObject(1);

			// print the results
			while (rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getInt(2));

			}
			conn.close();
			cs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return rs;

	}

	public ResultSet get_classes() {

		try {
			conn = Util.getDBConnection();

			CallableStatement cs = conn.prepareCall("begin ? := PROJECT3.get_classes(); end;");

			// register the out parameter (the first parameter)
			cs.registerOutParameter(1, OracleTypes.CURSOR);

			// execute and retrieve the result set
			cs.execute();
			rs = (ResultSet) cs.getObject(1);

			// print the results
			while (rs.next()) {
				System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4)
						+ "\t" + rs.getInt(5) + "\t" + rs.getString(6) + "\t" + rs.getInt(7) + "\t" + rs.getInt(8));

			}
			conn.close();
			cs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return rs;

	}
	
	
	public void display(){
		
		try {
			System.out.println("\n");
			System.out.println("--------------------------------------------------");
			System.out.println("1:Show students");
			System.out.println("2:show Prerequisites");
			System.out.println("3:Show Logs");
			System.out.println("4:show grades");
			System.out.println("5:show enrollments");
			System.out.println("6:show courses");
			System.out.println("7:show classes");
			System.out.println("8:show course_credit");
			System.out.println("9:Go to main menu..");
			
			
			System.out.println("Enter your choice:");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			choice = Integer.parseInt(reader.readLine());

switch (choice) {

   case 1: 
        get_students();
        display();
        break;
   case 2:
	    get_prerequisites();
	    display();
	    break;
   case 3:
	      get_logs();
	      display();
	      break;
   case 4:
	     get_grades();
	     display();
	     break;
   case 5:
	     get_enrollments();
	     display();
	     break;
   case 6:
	      get_courses();
	      display();
	      break;
   case 7:
	       get_classes();
	       display();
	       break;
   case 8:
	     get_courseCredit();
	     display();
	     break;
   case 9:
	     
	     break;
   default:
	   System.out.println("Invalid choice.");
       return;


}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
	}

}
