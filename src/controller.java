import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hsqldb.Statement;

public class controller {
	static void start() throws IOException, InterruptedException{
		boolean con = connect_database();
		
		view.print_menu();
		int i = read_choice();
		if(choice_check(i)){
			execute_choice(i);
		}
		else
		{
			Thread.sleep(1000);
			start();
		}
	}
	
	static boolean choice_check(int i){
		if(i==0) return false;
		else return true;
	}
	
	static void execute_choice(int i){
		
	}
	
	static int read_choice() throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		view.print_out("Wybierz dzialanie: ");
        try{
            int i = Integer.parseInt(br.readLine());
            return i;
        }catch(NumberFormatException nfe){
            System.err.println("Nieprawidlowy wybor!");
            return 0;
        }
	}
	
	public static boolean connect_database(){
		final String DRIVER = "org.hsqldb.jdbcDriver";
	    final String DB_URL = "jdbc:hsqldb:file:mydb;ifexists=false;hsqldb.lock_file=false";
		
	    Connection conn;
	    java.sql.Statement stmt = null;
	    
	    //Check JDBC
	    try 
	    {
			Class.forName(DRIVER);
		} 
	    catch (ClassNotFoundException e) 
	    {
			System.err.println("Brak sterownika JDBC");
			e.printStackTrace();
		}
	    
	    //Connect
	    try {
			   conn = DriverManager.getConnection(DB_URL,"admin","");   
			   stmt = conn.createStatement();
		} catch (SQLException e) {
					System.err.println("Problem z otwarciem po³¹czenia");
					e.printStackTrace();
		}
	    
	  //ADRES
	  		String createDataAdres="CREATE TABLE IF NOT EXISTS Adres(id INTEGER PRIMARY KEY IDENTITY, ulica VARCHAR(30), numer INTEGER, miejscowosc VARCHAR(50),  kod VARCHAR(6), kraj VARCHAR(40))";
	  		try {
	  			stmt.execute(createDataAdres);
	  		} catch (SQLException e) {
	  			System.err.println("Blad przy tworzeniu tabeli Adres");
	  			e.printStackTrace();
	  			return false;
	  		}
	    
	    return true;
	}
}
