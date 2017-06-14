import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import oracle.jdbc.OracleTypes;

public class ListClassStudents {
	Connection conn = Util.getDBConnection();
	ResultSet rs;
	public void doOperation(){
		
		
		try {
			BufferedReader readKeyBoard;
			readKeyBoard = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Please Enter Classid: ");
			String class_id = readKeyBoard.readLine();
			
			
			CallableStatement cs = conn.prepareCall("begin PROJECT3.list_classstudents(?, ?, ?); end;");
			
			// set the in parameter (the first parameter)
			
			cs.setString(1, class_id);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			cs.registerOutParameter(3, OracleTypes.CURSOR);
			cs.executeQuery();
			
			System.out.println("Classes :: ");
			ResultSet rsTitle = (ResultSet) cs.getObject(2);
			while (rsTitle.next()) {
				System.out.println(rsTitle.getString(1));
			}
			
			ResultSet rsBfirstname = (ResultSet) cs.getObject(3);
			System.out.println("Student :: ");
			while (rsBfirstname.next()) {
				System.out.println( rsBfirstname .getString(1)+"   "+ rsBfirstname .getString(2));
			}

			// close the result set, statement, and the connection
			cs.close();
			conn.close();

		} catch (Exception e) {
			e.getMessage();
			System.out.println(e.getMessage().split(":")[1].split("\\.")[0]);
		}
		
	}

}
