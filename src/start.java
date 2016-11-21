import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hsqldb.Server;

public class start {

	public static void main(String[] args) throws IOException, InterruptedException, SQLException {
		// TODO Auto-generated method stub
		
  
		//Connect or create and connect to hsql
		//Connection c = DriverManager.getConnection(DB_URL, "admin", "");
		
		//System.out.println(c);
		
		
		controller.start();
		
	}

}
