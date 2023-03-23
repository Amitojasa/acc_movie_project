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
	
	public static int[] showMenu() {
		
		System.out.println();
		Scanner sc = new Scanner(System.in);
		System.out.println("\nEnter number of movies that you want to get between 1 to 50:");
		int numberOfMovies = sc.nextInt();
		System.out.println("1: Get top " +numberOfMovies+ " based on Actor?");
		System.out.println("2: Get top " +numberOfMovies+ " based on Genre?");
		System.out.println("3: Get top " +numberOfMovies+ " based on overall Rating?");
		
		System.out.println("\nEnter your choice: ");
		int choice = sc.nextInt();
		
		int choices[] = new int[2];
		choices[0] = numberOfMovies;
		choices[1] = choice;
		
		return choices;
	}
	
	public static void basedOnRating(int numberOfMovies, Movie[] arr) {
		
		Double[] movieRatings = new Double[arr.length];
		
		Hashtable<Movie, Double> movieIndex = new Hashtable<Movie, Double>();
			
		for (int i = 0; i < movieRatings.length; i++) {
			movieRatings[i] = arr[i].getRating();
			movieIndex.put(arr[i], arr[i].getRating());
		}

		List<Movie> aboveSevenMovies = new ArrayList<>();
		
		for (Map.Entry<Movie, Double> entry : movieIndex.entrySet()) {
		    if (entry.getValue() > 7.0) {
		        aboveSevenMovies.add(entry.getKey());
		    }
		}
		
		Collections.sort(aboveSevenMovies, new Comparator<Movie>() {
			@Override
			public int compare(Movie movie1, Movie movie2) {
				return Double.compare(movieIndex.get(movie2), movieIndex.get(movie1));
			}
		});
		
		System.out.println();
		for (int displayMovies = 0; displayMovies < numberOfMovies; displayMovies++) {
			
			Movie movie = aboveSevenMovies.get(displayMovies);
			System.out.println(movie.getMovieName() + " : " +movie.getRating());
			
		}
		
	}
	
	
	
	public static void main(CreateGraph customGraph) {
		
		int choices[] = showMenu();
		int numberOfMovies = choices[0];
		int getMovieBasedOn = choices[1];
		
		Movie[] arr = customGraph.createArrayForMovies(customGraph.arrayOfMoviesWithDetails);
		
		switch(getMovieBasedOn) {
		case 3:
			basedOnRating(numberOfMovies, arr);
			break;
		default:
			break;
		}

	
	}

}