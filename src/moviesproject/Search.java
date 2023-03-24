package moviesproject;

import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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
		System.out.print("Enter the name of actor (full name): ");
		String nameOfActor = ((String) sc.nextLine()).strip().toLowerCase() ;
		
		System.out.println();
		
		boolean actorIsPresent = customGraph.getSymbolGraphForMoviesWithCast().contains(nameOfActor); 
		
		if (actorIsPresent) {
			Iterable<Integer> listOfMovies = customGraph.getGraphForMoviesWithCast().adj(customGraph.getSymbolGraphForMoviesWithCast().index(nameOfActor));
			for(Integer i : listOfMovies) {
				System.out.print(customGraph.getCodeOfMovie(customGraph.getSymbolGraphForMoviesWithCast().name(i))+ ": ");
				System.out.println(customGraph.getSymbolGraphForMoviesWithCast().name(i));     
			  	}
			
			System.out.println();
			
			fetchMovie(customGraph);
			
		}
		else {
			// TODO: Edit Distance call goes here.
			SpellChecker spellcheck = new SpellChecker();
			
			int exist = spellcheck.byActor(customGraph, nameOfActor);
//			int exist =  spellcheck.bymoviename(customGraph, nameOfActor);
			
			if( exist== 0) {
				System.out.println("Could not find actor.");
			}
		}
	}
	
	public static void fetchMovie(CreateGraph customGraph) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Do you want to fetch the details of a particular movie?");
		System.out.println("1: Yes");
		System.out.println("2: No");
		System.out.print("Enter your choice: ");
		int fetchDetails = sc.nextInt();
		if (fetchDetails == 1) {			
			System.out.println();
			System.out.print("Enter the movie code as listed along side movie name: ");
			int movieCode = sc.nextInt();
			System.out.println();
			System.out.println("=====+=====+===== Details of Movie =====+=====+=====");
			customGraph.printMovieDetails(movieCode);
		}
		
		
		
	}
	
	
	public static void searchMovieByMovieName(CreateGraph customGraph) {
		System.out.println();
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the name of Movie or a keyword: ");
		String word = sc.nextLine().strip().toLowerCase();
		System.out.println();
		
		Set<Integer> setOfMovies = customGraph.getMovieByKeyWord(word.toLowerCase());
		if (setOfMovies != null && setOfMovies.size() > 0) {		
			for (int i : setOfMovies) {
				String movieName = customGraph.getMovieFromIndex(i);
				System.out.print(customGraph.getCodeOfMovie(movieName) + ": ");
				System.out.println(movieName);  
			}
			System.out.println();
			fetchMovie(customGraph);		
				
		}
		else {
			//TODO: Edit Distance goes here.
			SpellChecker spellcheck = new SpellChecker();
			int exist = spellcheck.bymoviename(customGraph, word);
			
			if(exist == 0) {
				System.out.println("No movies found.");
			}
		}	
	}
	
	public static void searchMovieByGenre(CreateGraph customGraph) {
		System.out.println();
		System.out.println("============== List of Genre ==============");
		Set<String> setOfGenres = customGraph.getAllGenres();
		
		Hashtable<Integer, String> genreIndex = new Hashtable<Integer, String>();
		int currentItr = 1;
		for (String i : setOfGenres) {
			System.out.println(currentItr + " : " + i);
			genreIndex.put(currentItr, i);
			currentItr += 1;
		}
		System.out.println();
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Please choose a genre: ");
		String chosenGenre = genreIndex.get(sc.nextInt());
		
		System.out.println();
		
		Iterable<Integer> listOfMovies = customGraph.getGraphForMoviesWithGenre().adj(customGraph.getSymbolGraphForMoviesWithGenre().index(chosenGenre));
		for(Integer i : listOfMovies) {
			System.out.print(customGraph.getCodeOfMovie(customGraph.getSymbolGraphForMoviesWithGenre().name(i))+ ": ");
			System.out.println(customGraph.getSymbolGraphForMoviesWithGenre().name(i));     
		  	}
		
		System.out.println();
		
		fetchMovie(customGraph);

		
	}
		
	public static void main(CreateGraph customGraph) {

		showMoviesMenu();
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Please enter your choice: ");
		int selectedOption = sc.nextInt();
		
		switch(selectedOption) {
		case 1:
			searchMovieByActorName(customGraph);
			break;
		case 2:
			searchMovieByMovieName(customGraph);
			break;
		case 3:
			searchMovieByGenre(customGraph);
			break;
		default:
			System.out.println();
			System.out.println("Please select from the provided options");
			main(customGraph);
			break;
		}
	}

}
