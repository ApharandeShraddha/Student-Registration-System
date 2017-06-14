import oracle.jdbc.internal.OracleTypes;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class ShowClasses {
    Connection conn = Util.getDBConnection();
    ResultSet rs;

    public ResultSet get_showStudentClasses() {


        //	System.out.printf("Here");

        try {
            // Input sid from keyboard
            BufferedReader readKeyBoard;
            String sid;
            readKeyBoard = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Please Enter B#:");

            sid = readKeyBoard.readLine();


            CallableStatement cs = conn.prepareCall("begin  PROJECT3.showStudentClasses(:1,:2); end;");
            cs.setString(1, sid);

            // register the out parameter (the first parameter)
            cs.registerOutParameter(2, OracleTypes.CURSOR);

            // execute and retrieve the result set
            cs.executeUpdate();
            rs = (ResultSet) cs.getObject(2);

            // print the results
            while (rs.next()) {
                System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4)
                        + "\t" + rs.getInt(5) + "\t" + rs.getString(6)
                        + "\t" + rs.getString(7)+ "\t" + rs.getInt(8));

            }
            conn.close();
            cs.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage().split(":")[1].split("\\.")[0]);
          //  e.printStackTrace();
        }
        return rs;
    }
}
