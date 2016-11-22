import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class model {
	static java.sql.Statement stmt = null;
	
	public static boolean connect_database(){
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
	
}
