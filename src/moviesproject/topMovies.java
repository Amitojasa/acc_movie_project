package moviesproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class topMovies {
	
	public static Hashtable<Movie, Double> movieHash = new Hashtable<Movie, Double>();
	public static List<Movie> sortedMovies = new ArrayList<>();
	
	public static int[] showMenu() {
		
		System.out.println();
		Scanner sc = new Scanner(System.in);
		System.out.println("\nEnter number of movies that you want to get between 1 to 50:");
		int numberOfMovies = sc.nextInt();
		System.out.println("1: Get top " +numberOfMovies+ " based on Actor?");
		System.out.println("2: Get top " +numberOfMovies+ " based on overall Rating?");
		
		System.out.println("\nEnter your choice: ");
		int choice = sc.nextInt();
		
		int choices[] = new int[2];
		choices[0] = numberOfMovies;
		choices[1] = choice;
		
		return choices;
	}
	
	public static Hashtable<Movie, Double> createHashTableOfMOvies(int numberOfMovies, Movie[] arr) {
		
		Hashtable<Movie, Double> movieHash = new Hashtable<Movie, Double>();
			
		for (int i = 0; i < arr.length; i++) {
			movieHash.put(arr[i], arr[i].getRating());
		}
		return movieHash;
	}
	
	public static void sortingBasedOnRatings(Hashtable<Movie, Double> movieIndex, int numberOfMovies) {
		List<Movie> sortedBasedOnRatings = new ArrayList<>();
		
		for (Map.Entry<Movie, Double> entry : movieIndex.entrySet()) {
		    if (entry.getValue() > 7.0) {
		        sortedBasedOnRatings.add(entry.getKey());
		    }
		}
		
		Collections.sort(sortedBasedOnRatings, new Comparator<Movie>() {
			@Override
			public int compare(Movie movie1, Movie movie2) {
				return Double.compare(movieIndex.get(movie2), movieIndex.get(movie1));
			}
		});
		
		System.out.println();
		for (int displayMovies = 0; displayMovies < numberOfMovies; displayMovies++) {
			
			Movie movie = sortedBasedOnRatings.get(displayMovies);
			System.out.println(displayMovies+1 + " : "+movie.getMovieName() + " : " +movie.getRating());
		}
		fetchMovies(sortedBasedOnRatings);
	}
	
	public static void sortingBasedOnCast(List<Movie> sortedMovies, int numberOfMovies, CreateGraph customGraph, Movie[] arr) {
		System.out.println();
		System.out.println("Plese enter first and last name of the actor: ");
		
		Scanner sc = new Scanner(System.in);
		String nameOfActor = sc.nextLine().strip().toLowerCase();
		
		boolean actorIsPresent = customGraph.getSymbolGraphForMoviesWithCast().contains(nameOfActor);
		
		if(actorIsPresent) {
		
			List<Movie> sortedMoviesOfActor = new ArrayList<>();
			Iterable<Integer> listOfMovies = customGraph.getGraphForMoviesWithCast().adj(customGraph.getSymbolGraphForMoviesWithCast().index(nameOfActor));
			
			for(Integer j : listOfMovies) {
	
				for(Movie i : arr) {
					if (i.getMovieName().equals(customGraph.getSymbolGraphForMoviesWithCast().name(j))) {
						sortedMoviesOfActor.add(i);
					}
				}
			}
			
			
			Collections.sort(sortedMoviesOfActor, new Comparator<Movie>(){
				@Override
				public int compare(Movie movie1, Movie movie2) {
					return Double.compare(movieHash.get(movie2), movieHash.get(movie1));
				}
			});
		

			System.out.println();
			
			if (numberOfMovies > sortedMoviesOfActor.size()) {
				numberOfMovies = sortedMoviesOfActor.size();
			}
			
			for (int displayMovies = 0; displayMovies < numberOfMovies; displayMovies++) {
				
				Movie movie = sortedMoviesOfActor.get(displayMovies);
				System.out.println(displayMovies+1 + " : "+movie.getMovieName() + " : " +movie.getRating());
			}
			
			fetchMovies(sortedMoviesOfActor);
			
		}
		else {
			// TODO: Edit Distance call goes here.
						SpellCheker spellcheck = new SpellCheker();
						
			int exist = spellcheck.main(customGraph, nameOfActor);
//						int exist =  spellcheck.bymoviename(customGraph, nameOfActor);
			
			if(exist== 0) {
				System.out.println("Could not find actor.");
			}
		}
	
		
	}
	
	
	public static void fetchMovies(List<Movie> movies) {

		Scanner sc = new Scanner(System.in);
		System.out.println("\nDo you want to fetch details of particular movie");
		System.out.println("1: Yes");
		System.out.println("2: NO");
		System.out.println("Enter your choice:");
		int fetchMoiveDetails = sc.nextInt();
		
		switch (fetchMoiveDetails){
			case 1:
				System.out.println();
				System.out.print("Enter the movie code: ");
				int movieCode = (sc.nextInt()) - 1;
				System.out.println();
				System.out.println("********** Details of Movie **********");
				
				Movie movie = movies.get(movieCode);
				
				System.out.println();
				
				System.out.println("Name 	   : " +movie.getMovieName());
				System.out.println("Description: " +movie.getMovieDesc());
				System.out.println("Duration   : " +movie.getMovieDuration());
				System.out.println("Release    : " +movie.getMovieRelease());
				System.out.println("Rating	   : " +movie.getRating());
				System.out.println("Genres	   : " +movie.getMovieGenres());
				System.out.println("Cast	   : " +movie.getMovieCast());
				break;
			
			case 2:
				break;
				
			default:
				System.out.println("Please enter a valid choice");
				break;
		}

	}
	
	
	public static void main(CreateGraph customGraph) {
		
		int choices[] = showMenu();
		int numberOfMovies = choices[0];
		int getMovieBasedOn = choices[1];
		
		Movie[] arr = customGraph.createArrayForMovies(customGraph.arrayOfMoviesWithDetails);
		
		movieHash = createHashTableOfMOvies(numberOfMovies, arr);
		
		for (Map.Entry<Movie, Double> entry : movieHash.entrySet()) {
		    if (entry.getValue() > 5.0) {
		        sortedMovies.add(entry.getKey());
		    }
		}
		
		Collections.sort(sortedMovies, new Comparator<Movie>() {
			@Override
			public int compare(Movie movie1, Movie movie2) {
				try {					
					return Double.compare(movieHash.get(movie2), movieHash.get(movie1));
				} catch (Exception e) {
					return 0;
				}
			}
		});
		
		switch(getMovieBasedOn) {
		
		case 1:
			sortingBasedOnCast(sortedMovies, numberOfMovies, customGraph, arr);
			break;
			
		case 2:
			sortingBasedOnRatings(movieHash, numberOfMovies);
			break;
		
		default:
			break;
		}

	
	}

}