import oracle.jdbc.internal.OracleTypes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

/**
 * 
 */
public class DependentCourses {

    Connection conn = Util.getDBConnection();
    ResultSet rs;

    public void get_dependentCourses() {


        //	System.out.printf("Here");

        try {
            
            Scanner sc = new Scanner(System.in);
           
            System.out.print("Please Enter CourseID:");
            int cid = sc.nextInt();
            System.out.print("Please Enter Dept Name:");
            String deptName=sc.next();
       
            CallableStatement cs = conn.prepareCall("begin  PROJECT3.GET_DEPENDENTCOURSES(:1,:2,:3); end;");
            cs.setString(1, deptName);
            cs.setInt(2, cid);



            // register the out parameter (the first parameter)
            cs.registerOutParameter(3, OracleTypes.CURSOR);

            // execute and retrieve the result set
            cs.execute();
            rs = (ResultSet) cs.getObject(3);

            if(rs==null)
                System.out.println("rs is null");

            
            // print the results
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            conn.close();
            cs.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage().split(":")[1].split("\\.")[0]);
             // e.printStackTrace();
        }
       
    }

}
