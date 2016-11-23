import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class model {
	static java.sql.Statement stmt = null;
	
	static boolean connect_database(){
		final String DRIVER = "org.hsqldb.jdbcDriver";
	    final String DB_URL = "jdbc:hsqldb:file:mydb;ifexists=false;hsqldb.lock_file=false";
		
	    Connection conn;
	    
	    view.print_out("Nawiπzywanie po≥πczenia z bazπ danych\n");	
	    
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
					System.err.println("Problem z otwarciem po≥πczenia");
					e.printStackTrace();
		}      
	    return true;
	}
	
	static boolean init_database(){
		view.print_out("Inicjacja danych...\n");
		
				//INTERWENCJA
				String createDataInterwencja="CREATE TABLE IF NOT EXISTS Interwencja(id INTEGER PRIMARY KEY IDENTITY, data_interwencji DATE, miejscowosc VARCHAR(30), ulica VARCHAR(30), numer INTEGER, opis_zdarzenia VARCHAR(1000))";
				try {
					stmt.execute(createDataInterwencja);
				} catch (SQLException e) {
					System.err.println("Blad przy tworzeniu tabeli Interwencja");
					e.printStackTrace();
					return false;
				}
				//POJAZD
			    String createDataPojazd="CREATE TABLE IF NOT EXISTS Pojazd(id INTEGER PRIMARY KEY IDENTITY, marka VARCHAR(30),model VARCHAR(30), data_produkcji DATE, przebieg REAL, wartosc INTEGER)";
			    try {
			        stmt.execute(createDataPojazd);
			
			    } catch (SQLException e) {
			        System.err.println("Blad przy tworzeniu tabeli Pojazd");
			        e.printStackTrace();
			        return false;
			    }
			    //Pracownik
			    String createDataPracownik="CREATE TABLE IF NOT EXISTS Pracownik(id INTEGER PRIMARY KEY IDENTITY,Pojazd_id INTEGER REFERENCES Pojazd(id),imie VARCHAR(50), nazwisko VARCHAR(50), data_urodzenia DATE, numer_telefonu VARCHAR(9),data_zatrudnienia DATE)";
			    try {
			        stmt.execute(createDataPracownik);
			
			    } catch (SQLException e) {
			        System.err.println("Blad przy tworzeniu tabeli Pracownik");
			        e.printStackTrace();
			        return false;
			    }
			    //INTERWENCJA HAS PRACOWNIK
			    String createDataHas="CREATE TABLE IF NOT EXISTS Interwencja_has_Pracownik(Interwencja_id INTEGER REFERENCES Interwencja(id), Pracownik_id INTEGER REFERENCES Pracownik(id))";
			    try {
			        stmt.execute(createDataHas);
			
			    } catch (SQLException e) {
			        System.err.println("Blad przy tworzeniu tabeli Interwencja has Pracownik");
			        e.printStackTrace();
			        return false;
			    }
			    return true;
	}
	
	static boolean db_execute(String cmd){
		try {
	        stmt.execute(cmd);
	        //view.print_out("\n"+cmd);
	        return true;
	    } catch (SQLException e) {
	        System.err.println("BLAD BAZY DANYCH");
	        e.printStackTrace();
	        return false;
	    }
	}
	
	static void database_insert(int table,String data[]) throws IOException, InterruptedException
	{
		if(table==1){
			//Interwencja
			String cmd = String.format("INSERT INTO Interwencja (data_interwencji,miejscowosc,ulica,numer,opis_zdarzenia) Values ('%s','%s','%s','%s','%s')",data[1],data[2],data[3],data[4],data[5]);

			if(model.db_execute(cmd)){
				view.print_out("Poprawnie dodano interwencje\n");
			}
			else
			{
				view.print_out("Blad bazy danych!\n");
			}
		}
		
	}
	
	static void insert(int i) throws IOException, InterruptedException{
		/*
		1. Interwencje
		2. Pracownicy
		3. Pojazdy
		*/
		if(i==1){
			//DATA, MIEJSCOWOSC, ULICA, NUMER, OPIS, DLUGOSC_INTERWENCJI
			view.print_out("DATA\n");
			String data[] = new String[10];
			data[1] = read_string();
			while(data[1].matches("^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")==false)
			{
				view.print_out("Niepoprawna data! YYYY-MM-DD\n");
				data[1] = read_string();
			}
			
			view.print_out("MIEJSCOWOSC\n");
			data[2] = read_string(2,50);
			while(data[2].matches("^([A-Z-Øè∆•å £”—][A-Za-zøüÊÒÛ≥ÍπúØè∆•å £”—]*(([ ][A-Za-zøüÊÒÛ≥ÍπúØè∆•å £”—]*)?)*)$")==false)
			{
				view.print_out("Niepoprawna miejscowoúÊ (Piszemy z duøej)\n");
				data[2] = read_string(2,50);
			}
			
			view.print_out("ULICA\n");
			data[3] = read_string(3,30);
			while(data[3].matches("^([A-Z-Øè∆•å £”—][A-Za-zøüÊÒÛ≥ÍπúØè∆•å £”—]*(([ ][A-Za-zøüÊÒÛ≥ÍπúØè∆•å £”—]*)?)*)$")==false)
			{
				view.print_out("Niepoprawna ulica (Piszemy z duøej)\n");
				data[3] = read_string(2,50);
			}
			
			view.print_out("NUMER\n");
			data[4] = Integer.toString(read_int(0,99999));
			
			view.print_out("OPIS\n");
			data[5] = read_string(10,255);
			
			database_insert(i,data);
		}
		else if(i==2){
			
		}
		else if(i==3){
			
		}
	}
	
	static int read_int() throws IOException{
		view.print_out("Twoj wybor: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            int i = Integer.parseInt(br.readLine());
            return i; 	
        }catch(NumberFormatException nfe){
            return read_int();
        }
	}
	
	static int read_int(int min,int max) throws IOException{
		view.print_out("Twoj wybor: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            int i = Integer.parseInt(br.readLine());
            if(i<min || i>max)
            {
            	view.print_out("\nBlad! Dane sa z≥ej wielkoúci. MIN: "+min+" MAX: "+max+"\n");
            	return read_int(min,max);
            }
            return i; 	
        }catch(NumberFormatException nfe){
            return read_int(min,max);
        }
	}
	
	static String read_string(int min,int max) throws IOException{
		view.print_out("Wprowadz dane: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            String i = br.readLine();
            int l = i.length();
            if(l<min || l>max)
            {
            	view.print_out("\nBlad! Dane sa z≥ej d≥ugoúci. MIN: "+min+" MAX: "+max+"\n");
            	return read_string(min,max);
            }
            else
            	return i; 	
        }catch(NumberFormatException nfe){
            return read_string(min,max);
        }
	}
	
	static String read_string() throws IOException{
		view.print_out("Wprowadz dane: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            String i = br.readLine();
            return i; 	
        }catch(NumberFormatException nfe){
            return read_string();
        }
	}
	
	static void ok_continue(){
		@SuppressWarnings("resource")
		Scanner s=new Scanner(System.in);
        System.out.println("Kliknij enter by kontynuowac.....");
        s.nextLine();
        //s.close();
	}
	
	public static int get_table_rows(String name){
		String cmd = "SELECT Count(*) FROM "+name;
		try{
			ResultSet result = stmt.executeQuery(cmd);
			result.next();
			return result.getInt(1);
	    } catch (SQLException e){
	    	System.err.println("Blad: "+cmd);
			e.printStackTrace();
			return 0;
		}
	}
	
	static void print_Table(int x) throws IOException, InterruptedException{
		String c = "";
		if(x==1){
			c="Interwencja";
		}
		else if(x==2){
			c="Pracownik";
		}
		else if(x==3){
			c="Pojazd";
		}
		else if(x==4){
			view.clear_console();
			controller.menu();
		}
		
		
		String cmd = "SELECT * FROM "+c;
		int i = 1;
		int rows = get_table_rows(c);
		try{
			ResultSet result = stmt.executeQuery(cmd);
			ResultSetMetaData rsmd = result.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			//System.out.println("ROWS: "+rows+" Columns: "+numberOfColumns);
			String[][] data = new String[rows+2][numberOfColumns+1];
			data[0][0] = Integer.toString(rows);
			data[0][1] = Integer.toString(numberOfColumns);			
			while(result.next())
			{
				if(result.isClosed())
				{
					break;
				}			
				//System.out.print("Dane: ");
				for(int j=1;j<=numberOfColumns;j++){
					data[i][j] = result.getString(j);
					System.out.print(data[i][j]+ ", ");
				}
				System.out.print("\n");
				//System.out.println();
				i++;			
			}
	    } catch (SQLException e){
				 System.err.println("Blad");
				e.printStackTrace();
		}
	}
	
}
