import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class EnrollStudent {
	Connection conn = Util.getDBConnection();
	ResultSet rs;
	String year;
	String semester;
	int counter;
	public void doOperation(){
		
		
		try {
			BufferedReader readKeyBoard;
			readKeyBoard = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Please Enter B#: ");
			String student_id = readKeyBoard.readLine();
			System.out.println("Please Enter Classid: ");
			String class_id = readKeyBoard.readLine();
			
			
			Statement selectStmt=conn.createStatement (); 
			
			String query ="SELECT year, semester FROM Classes WHERE classid = '"+class_id+"'";
			ResultSet rset = selectStmt.executeQuery (query);
            while (rset.next ()) {
                  year=rset.getString (1);
                  semester = rset.getString (2);
                
               }
            
            String queryString="SELECT count(*) FROM Enrollments WHERE B# ='"+student_id+"'"+" and classid IN (SELECT classid FROM Classes WHERE year='"+year+"' and semester ='"+semester+"')" ;
            ResultSet rset1 = selectStmt.executeQuery (queryString);
            while (rset1.next ()) {
            	counter=Integer.parseInt(rset1.getString (1));
                
               
              }
            if(counter == 3){
            	
            	System.out.println("You are overloaded");
            }
            

			
			
			CallableStatement cs = conn.prepareCall("begin PROJECT3.enroll_student(:1,:2); end;");
			
			// set the in parameter (the first parameter)
			cs.setString(1, student_id);
			cs.setString(2, class_id);
		

			

			// execute the stored procedure
			cs.executeQuery();
			//System.out.println("HERE");
			
			// get the out parameter result.

			System.out.println("Enrolled Successfully");

			// close the result set, statement, and the connection
			cs.close();
			conn.close();

		} catch (Exception e) {
			e.getMessage();
			//System.out.println(e.getMessage());
			System.out.println(e.getMessage().split(":")[1].split("\\.")[0]);
		}
		
	}	

}
