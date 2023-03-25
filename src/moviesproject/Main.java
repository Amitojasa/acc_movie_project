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
		
		//creating Objects
		FetchMovies fetchMoviesObj = new FetchMovies();
		CreateGraph customGraph = new CreateGraph();
		Search searchMovies = new Search();
		Scanner scannerObj = new Scanner(System.in);
		
		// create Graph from JSON file
		customGraph.createGraphFromJson();
		
		
		System.out.println("Welcome to the Movie Search Engine\n");

		
		//loop until 4 is selected
		while (true) {
			
			//show the menu
			showMenu();
			
			try {
				
				//take input from user
				int option = scannerObj.nextInt();

				if (option == 1) {
					
					//sync/fetch movie details
					fetchMoviesObj.sync();
					
				} else if (option == 2) {
					
					// search function
					searchMovies.main(customGraph);
					
				} else if (option == 3) {
					
					// get top movies
					topMovies.main(customGraph);
					
				} else if (option == 4) {
					
					//exit from the loop
					System.out.println("Thankyou, have a nice day.");
					return;
					
				} else {
					// in case wrong input is selected
					System.out.println("Wrong option, Please select again.");
				}
			} catch (Exception e) {
				
				//if person enters string in place of integer input
				
				System.out.println("\nPlease provide valid inputs.....");
				System.out.println(e);
				scannerObj.nextLine();
			}
		}

	}
}
