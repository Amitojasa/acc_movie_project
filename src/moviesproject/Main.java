package moviesproject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

public class Main {
	public static void showMenu() {
		System.out.println("\n1: Sync (Re-fetch the movie data, this will take some time)");
		System.out.println("2: Search a movie");
		System.out.println("3: Get Top Movie");
		System.out.println("4: Exit");
		System.out.print("\nPlease select one of the above options (Enter the digit associated): ");
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		FetchMovies fm= new FetchMovies();
		CreateGraph cg= new CreateGraph();
		
		Scanner scannerObj = new Scanner(System.in);  
		cg.createGraphFromJson();
		System.out.println("Welcome to the Movie Search Engine\n");
		
		
		while(true) {
			
			// show menu
			showMenu();
			
			int option = scannerObj.nextInt(); 
			
			if(option==1) {
				fm.sync();
			}else if(option==2) {
				
				//search related function
			}else if(option==3) {
				//get top movies
			}else if(option ==4) {
				System.out.println("Thankyou, have a nice day.");
				return;
			}else {
				System.out.println("Wrong option, Please select again.");
			}
		}
		
	}
}
