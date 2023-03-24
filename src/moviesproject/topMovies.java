package moviesproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class topMovies {

	public static Hashtable<Movie, Double> movieHash = new Hashtable<Movie, Double>();
	public static List<Movie> sortedMovies = new ArrayList<>();

	public static int showMenu() {

		System.out.println();
		Scanner sc = new Scanner(System.in);
		System.out.println("\nEnter number of movies that you want to get between 1 to 50:");
		int numberOfMovies = sc.nextInt();
		return numberOfMovies;
	}

	public static void fetchMovies(Movie[] arr) {

		Scanner sc = new Scanner(System.in);
		System.out.println("\nDo you want to fetch details of particular movie");
		System.out.println("1: Yes");
		System.out.println("2: NO");
		System.out.println("Enter your choice:");
		int fetchMoiveDetails = sc.nextInt();

		switch (fetchMoiveDetails) {
		case 1:
			System.out.println();
			System.out.print("Enter the movie code: ");
			int movieCode = (sc.nextInt()) - 1;
			System.out.println();
			System.out.println("********** Details of Movie **********");

			Movie movie = arr[movieCode];

			System.out.println();

			System.out.println("Name 	   : " + movie.getMovieName());
			System.out.println("Description: " + movie.getMovieDesc());
			System.out.println("Duration   : " + movie.getMovieDuration());
			System.out.println("Release    : " + movie.getMovieRelease());
			System.out.println("Rating	   : " + movie.getRating());
			System.out.println("Genres	   : " + movie.getMovieGenres());
			System.out.println("Cast	   : " + movie.getMovieCast());
			break;

		case 2:
			break;

		default:
			System.out.println("Please enter a valid choice");
			break;
		}

	}

	public static void printSortedMovies(int x, Movie[] arr) {
		for (int i = 0; i < x; i++) {
			System.out.println((i + 1) + " : " + arr[i].getMovieName() + " : Rating: " + arr[i].getRating());
		}
		fetchMovies(arr);
	}

	public static void main(CreateGraph customGraph) {

		int numberOfMovies = showMenu();

		Movie[] arr = customGraph.createArrayForMovies(customGraph.arrayOfMoviesWithDetails);

		/// important sorting done in main
		Arrays.sort(arr, new Comparator<Movie>() {
			@Override
			public int compare(Movie movie1, Movie movie2) {
				try {
					if (movie1.getRating() == movie2.getRating()) {
						if (movie1.getMovieGenres().size() < movie2.getMovieGenres().size()) {
							return 1;

						} else if ((movie1.getMovieGenres().size() == movie2.getMovieGenres().size())) {

							if (movie1.getMovieCast().size() < movie2.getMovieCast().size()) {
								return 1;

							} else {
								return -1;
							}
						} else {
							return -1;
						}
					} else if (movie1.getRating() < movie2.getRating()) {
						return 1;
					} else {
						return -1;
					}
				} catch (Exception e) {
					return 0;
				}
			}
		});

		printSortedMovies(numberOfMovies, arr);

	}

}