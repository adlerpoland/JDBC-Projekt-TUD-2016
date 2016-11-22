
public class view {
	static void print_out(String s){
		System.out.print(s);
	}
	
	static void clear_console(){
		for (int i = 0; i < 50; ++i) 
			System.out.println();
	}
	
	static void print_menu(){
		print_out("Stra¿ Po¿arna 2016\n");
		print_out("__________________\n");
		
		print_out("1. Wyswietl tabele\n");
		print_out("2. Dodaj dane\n");
		print_out("3. Zmodyfikuj dane\n");
		print_out("4. Usun dane\n");
		print_out(" \n");
	}
	
	static void print_tables(){
		clear_console();
		print_out("Dostêpne tabele:\n");
		print_out("__________________\n");
		
		print_out("1. Interwencje\n");
		print_out("2. Pracownicy\n");
		print_out("3. Pojazdy\n");
		print_out("4. Wróæ\n");
		print_out(" \n");

	}
}
