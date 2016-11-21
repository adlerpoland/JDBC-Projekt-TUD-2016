import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class start {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		view.print_out("Stra¿ Po¿arna 2016\n");
		view.print_out("__________________\n");
		
		view.print_out("1. Dodaj dane\n");
		view.print_out("2. Wyswietl tabele\n");
		view.print_out("3. Zmodyfikuj dane\n");
		view.print_out("4. Usun dane\n");
		view.print_out(" \n");
		
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		view.print_out("Wybierz dzialanie: ");
        try{
            int i = Integer.parseInt(br.readLine());
        }catch(NumberFormatException nfe){
            System.err.println("Nieprawidlowy wybor!");
        }
	}

}
