package moviesproject;

import java.util.Scanner;

public class Search {
	
	public static void showMoviesMenu() {
		System.out.println();
		System.out.println("=====+=====+===== Search Menu =====+=====+=====");
		System.out.println();
		
		System.out.println("1: Search Movies by Actor Name");
		System.out.println("2: Search Movies by Movie name or keyword");
		System.out.println("3: Search Movies by Genre");
		System.out.println();
	}
	
	public static void searchMovieByActorName(CreateGraph customGraph) {
		System.out.println();
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the name of actor: ");
		System.out.println();
		String nameOfActor = sc.nextLine();
		
		boolean actorIsPresent = customGraph.getSymbolGraphForMoviesWithCast().contains(nameOfActor); 
		
		if (actorIsPresent) {
			Iterable<Integer> listOfMovies = customGraph.getGraphForMoviesWithCast().adj(customGraph.getSymbolGraphForMoviesWithCast().index(nameOfActor));
			for(Integer i : listOfMovies) {
				System.out.print(customGraph.getCodeOfMovie(customGraph.getSymbolGraphForMoviesWithCast().name(i))+ ": ");
				System.out.println(customGraph.getSymbolGraphForMoviesWithCast().name(i));     
			  	}
			
			System.out.println();
			System.out.println("Do you want to fetch the details of a particular movie?");
			System.out.println("1: Yes");
			System.out.println("2: No");
			System.out.print("Enter your choice: ");
			int fetchDetails = sc.nextInt();
			if (fetchDetails == 1) {				
				fetchMovie(customGraph);
			}
		}
		else {
			// TODO: Edit Distance call goes here.
			SpellCheker spellcheck = new SpellCheker();
			
			int exist = spellcheck.main(customGraph, nameOfActor);
//			int exist =  spellcheck.bymoviename(customGraph, nameOfActor);
			
			if(exist== 0) {
				System.out.println("Could not find actor.");
			}
		}
	}
	
	public static void fetchMovie(CreateGraph customGraph) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the movie code as listed along side movie name: ");
		int movieCode = sc.nextInt();
		System.out.println();
		System.out.println("=====+=====+===== Details of Movie =====+=====+=====");
		customGraph.printMovieDetails(movieCode);
	}
		
		
	

	public static void main(CreateGraph customGraph) {
		
//		SpellCheker spellcheck = new SpellCheker();
//		spellcheck.main(customGraph, nameOfActor);
		
		showMoviesMenu();
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Please enter your choice: ");
		int selectedOption = sc.nextInt();
		
		switch(selectedOption) {
		case 1:
			searchMovieByActorName(customGraph);
			break;
		case 2:
			System.out.println("1");
			break;
		case 3:
			System.out.println("1");
			break;
		default:
			System.out.println();
			System.out.println("Please select from the provided options");
			main(customGraph);
			break;
		}
	}

}
