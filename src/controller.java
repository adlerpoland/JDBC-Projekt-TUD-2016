import java.io.IOException;

public class controller {
	static void start() throws IOException, InterruptedException{
		boolean con = model.connect_database();
		
		if(con){
			if(model.init_database())
				view.clear_console();
				Thread.sleep(1000);
				view.print_out("Wczytano baze danych\n");
		}
		menu();
	}
	
	static void menu() throws IOException, InterruptedException{
		view.print_menu();
		int i = model.read_int();
		if(choice_check(i)){
			view.clear_console();
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
	
	static void execute_choice(int i) throws IOException, InterruptedException{
		/*
		1. Wyswietl tabele
		2. Dodaj dane
		3. Zmodyfikuj dane
		4. Usun dane
		 */
		if(i==1){
			view.print_tables();
			int x = model.read_int(1,4);
			if(x==4){	
				menu();
			}
			else
			{	
				model.print_Table(x);
			}
		}
		else if(i==2){
			view.print_tables();
			int x = model.read_int(1,4);
			if(x==4){
				menu();
			}
			else
			{	
				model.insert(x);
			}
		}
		else if(i==3){
			view.print_tables();
		}
		else if(i==4){
			view.print_tables();
		}
	}
}
