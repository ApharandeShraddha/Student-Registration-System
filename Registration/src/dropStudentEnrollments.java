import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.OracleResultSet;

public class dropStudentEnrollments {
	
	Connection conn = Util.getDBConnection();
	ResultSet rs;

	Statement selectStmt =null;
	Statement selectStmt1 =null;
	int class_size;
	String enroll;

	public String doOperation() {

		try {
			// Get input from user
			BufferedReader readKeyBoard;
			readKeyBoard = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Please Enter B#: ");
			String student_id = readKeyBoard.readLine();
			System.out.println("Please enter class Id : ");
			String class_id = readKeyBoard.readLine();

			CallableStatement cs = conn.prepareCall("begin PROJECT3.dropStudentEnrollment(:1,:2); end;");

			// set the in parameter (the first parameter)
			cs.setString(1, student_id);

			// register the out parameter (the second parameter)
			cs.setString(2, class_id);

			// execute the stored procedure
			ResultSet s = cs.executeQuery();

			// get the out parameter result.

			System.out.println("Enrollment Deleted Successfully");
			
			// to check enrollment after deletion
			
			selectStmt1= conn.createStatement (); 
            
            String enrollmentCount="select classid  from enrollments where enrollments.B# ='"+student_id+"'"+ "and enrollments.classid ='"+class_id+"'";
            ResultSet rset1 = selectStmt1.executeQuery (enrollmentCount);
            if(rset1 == null){
            	
            	System.out.println("This student is not enrolled in any classes");
            }
            while (rset1.next ()) {
            	
                enroll=rset1.getString (1);
               
              
             }
            
            
            
            //to check class size after deletion
            
			selectStmt=conn.createStatement (); 
            
            String queryString="SELECT class_size from classes  where classid='"+class_id+"'";
            ResultSet rset = selectStmt.executeQuery (queryString);
            while (rset.next ()) {
                 class_size=Integer.parseInt(rset.getString (1));
                
               
              }
            if(class_size == 0){
            	
            	System.out.println("The class now has no students");
            }
            

			
          
            
			
			// close the result set, statement, and the connection
			cs.close();
			conn.close();

		} catch (Exception e) {
			e.getMessage();

			System.out.println(e.getMessage().split(":")[1].split("\\.")[0]);

		}

		return null;

	}

}
