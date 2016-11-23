import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class model {
	static java.sql.Statement stmt = null;
	
	static boolean connect_database(){
		final String DRIVER = "org.hsqldb.jdbcDriver";
	    final String DB_URL = "jdbc:hsqldb:file:mydb;ifexists=false;hsqldb.lock_file=false";
		
	    Connection conn;
	    
	    view.print_out("Nawi¹zywanie po³¹czenia z baz¹ danych\n");	
	    
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
	    return true;
	}
	
	static boolean init_database(){
		view.print_out("Inicjacja danych...\n");
		
		//ADRES
				String createDataAdres="CREATE TABLE IF NOT EXISTS Adres(id INTEGER PRIMARY KEY IDENTITY, ulica VARCHAR(30), numer INTEGER, miejscowosc VARCHAR(50),  kod VARCHAR(6), kraj VARCHAR(40))";
				try {
					stmt.execute(createDataAdres);
				} catch (SQLException e) {
					System.err.println("Blad przy tworzeniu tabeli Adres");
					e.printStackTrace();
					return false;
				}
					
				//INTERWENCJA
				String createDataInterwencja="CREATE TABLE IF NOT EXISTS Interwencja(id INTEGER PRIMARY KEY IDENTITY,Adres_id INTEGER REFERENCES Adres(id), data_interwencji DATE, rozpoczecie_interwencji TIME, zakonczenie_interwencji TIME, typ_interwencji VARCHAR(50), opis_zdarzenia VARCHAR(1000))";
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
			    //Stanowisko
			    String createDataStanowisko="CREATE TABLE IF NOT EXISTS Stanowisko(id INTEGER PRIMARY KEY IDENTITY,rodzaj VARCHAR(5),nazwa VARCHAR(100), maksymalny_stopien VARCHAR(50), staz INTEGER, pensja INTEGER)";
			    try {
			        stmt.execute(createDataStanowisko);
			
			    } catch (SQLException e) {
			        System.err.println("Blad przy tworzeniu tabeli Stanowisko");
			        e.printStackTrace();
			        return false;
			    }
			    //Pracownik
			    String createDataPracownik="CREATE TABLE IF NOT EXISTS Pracownik(id INTEGER PRIMARY KEY IDENTITY,Stanowisko_id INTEGER REFERENCES Stanowisko(id),Adres_id INTEGER REFERENCES Adres(id),Pojazd_id INTEGER REFERENCES Pojazd(id),imie VARCHAR(50), nazwisko VARCHAR(50), data_urodzenia DATE, numer_telefonu VARCHAR(9),data_zatrudnienia DATE)";
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
	
	static void insert(int i){
		/*
		1. Interwencje
		2. Pracownicy
		3. Pojazdy
		*/
		if(i==1){
			view.print_out("Data: ");
			
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
            	return read_int(min,max);
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
            	 return read_string(min,max);
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
				//System.out.println();
				i++;			
			}
	    } catch (SQLException e){
				 System.err.println("Blad");
				e.printStackTrace();
		}
	}
	
}
