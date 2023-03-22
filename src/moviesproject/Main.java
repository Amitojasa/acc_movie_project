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
	public static void topMovies() {
		System.out.println();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter between 1 to 50:");
		int numberOfMovies = sc.nextInt();
		System.out.println("Do you want top" + numberOfMovies + "movies based on Actor?");
		Search.searchMovieByActorName(customGraph);
		System.out.println("Do you want top" + numberOfMovies + "movies based on Genre?");
		Search.searchMovieByGenre();
	}
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		FetchMovies fetchMoviesObj = new FetchMovies();
		CreateGraph customGraph = new CreateGraph();
		Search searchMovies = new Search();
		
		Scanner scannerObj = new Scanner(System.in);  
		customGraph.createGraphFromJson();
		System.out.println("Welcome to the Movie Search Engine\n");
		
		
		while(true) {
			
			// show menu
			showMenu();
			
			int option = scannerObj.nextInt(); 
			
			if(option==1) {
				fetchMoviesObj.sync();
			}else if(option==2) {
				searchMovies.main(customGraph);
				//search related function
			}else if(option==3) {
				topMovies.main(customGraph);
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
