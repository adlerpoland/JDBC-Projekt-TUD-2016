import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hsqldb.Statement;

public class controller {
	static void start() throws IOException, InterruptedException{
		boolean con = model.connect_database();
		
		if(con){
			if(model.init_database())	
				Thread.sleep(1000);
				view.print_out("Wczytano baze danych\n");		
		}
		
		view.print_menu();
		int i = model.read_choice();
		if(choice_check(i)){
			execute_choice(i);
		}
		else
		{
			Thread.sleep(1000);
			menu();
		}
	}
	
	static void menu() throws IOException, InterruptedException{
		view.print_menu();
		int i = model.read_choice();
		if(choice_check(i)){
			execute_choice(i);
		}
		else
		{
			Thread.sleep(1000);
			menu();
		}
	}
	
	static boolean choice_check(int i){
		if(i==0) return false;
		else return true;
	}
	
	static void execute_choice(int i){
		
	}
}
