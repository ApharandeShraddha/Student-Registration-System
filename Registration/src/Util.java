import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;

public class Util {

	//static OracleDataSource ds ;
    static Connection conn ;

    
    //Returning JDBC connection instance
    //Only one instance is created per application and same instance is returned
    static public Connection getDBConnection() {
    	OracleDataSource ds;
		try {
			ds = new oracle.jdbc.pool.OracleDataSource();
			ds.setURL("jdbc:oracle:thin:@localhost:1521:xe");
			 conn = ds.getConnection("system", "Shraddha");
			//System.out.println("connected......");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

        return conn;
    }
	
	
	
}
