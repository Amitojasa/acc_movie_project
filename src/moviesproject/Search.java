package moviesproject;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import graphs.Graph;
import graphs.SymbolGraph;

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
		String nameOfActor = ((String) sc.nextLine()).strip().toLowerCase();

		System.out.println();

		boolean actorIsPresent = customGraph.getSymbolGraphForMoviesWithCast().contains(nameOfActor);

		if (actorIsPresent) {
			Iterable<Integer> listOfMovies = customGraph.getGraphForMoviesWithCast()
					.adj(customGraph.getSymbolGraphForMoviesWithCast().index(nameOfActor));
			for (Integer i : listOfMovies) {
				System.out.print(
						customGraph.getCodeOfMovie(customGraph.getSymbolGraphForMoviesWithCast().name(i)) + ": ");
				System.out.println(customGraph.getSymbolGraphForMoviesWithCast().name(i));
			}

			System.out.println();

			fetchMovie(customGraph);

		} else {
			// TODO: Edit Distance call goes here.
			SpellChecker spellcheck = new SpellChecker();

			int exist = spellcheck.byActor(customGraph, nameOfActor);
//			int exist =  spellcheck.bymoviename(customGraph, nameOfActor);

			if (exist == 0) {
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
			JSONObject movieForSuggestion = customGraph.printMovieDetails(movieCode);
			System.out.println("Do you want movie suggestions based on this movie?");
			System.out.println("1: Yes");
			System.out.println("2: No");
			System.out.print("Enter your choice: ");
			int wantSuggestion = sc.nextInt();
			if (wantSuggestion == 1) {
				showSuggestionsBasedOnMovie(customGraph, movieForSuggestion);
			}
		}

	}

	private static void showSuggestionsBasedOnMovie(CreateGraph customGraph, JSONObject movieForSuggestion) {

		JSONArray jsonArray1 = (JSONArray) movieForSuggestion.get("cast");
		String[] c = new String[jsonArray1.size()];
		for (int i = 0; i < jsonArray1.size(); i++) {
			c[i] = jsonArray1.get(i).toString();
		}

		JSONArray jsonArray2 = (JSONArray) movieForSuggestion.get("genres");
		String[] c1 = new String[jsonArray2.size()];
		for (int i = 0; i < jsonArray2.size(); i++) {
			c1[i] = jsonArray2.get(i).toString();
		}
		Set<String> listBasedOnActors = findMoviesInSymbolGraphOfActor(customGraph.getSymbolGraphForMoviesWithCast(),
				customGraph.getGraphForMoviesWithCast(), c);
		Set<String> listBasedOnGenre = findMoviesInSymbolGraphOfGenre(customGraph.getSymbolGraphForMoviesWithGenre(),
				customGraph.getGraphForMoviesWithGenre(), c1);
		if (listBasedOnGenre.isEmpty() && listBasedOnActors.isEmpty()) {
			System.out.println("Sorry. No suggestions found related to this movie.");
		} else if (listBasedOnActors.isEmpty()) {
			// nothing needed
		} else if (listBasedOnGenre.isEmpty()) {
			listBasedOnGenre = listBasedOnActors;

		} else {
			listBasedOnGenre.retainAll(listBasedOnActors);
		}

		listBasedOnGenre.remove(movieForSuggestion);
		JSONArray jsonMovieArr = new JSONArray();
		for (String movieName : listBasedOnGenre) {
			jsonMovieArr.add(customGraph.arrayOfMoviesWithDetails.get(customGraph.getCodeOfMovie(movieName)));
		}
		Movie[] movieArr = customGraph.createArrayForMovies(jsonMovieArr);
		Arrays.sort(movieArr, new Comparator<Movie>() {
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
		System.out.println("=================================");
		System.out.println("Suggested Movies:");
		for (int i = 0; i < (movieArr.length >= 5 ? 5 : movieArr.length); i++) {
			System.out.println(
					"=> " + customGraph.getCodeOfMovie(movieArr[i].getMovieName()) + ": " + movieArr[i].getMovieName());
		}
		System.out.println("=================================");
		System.out.println("Do you want more suggestions?");
		System.out.println("1: Yes");
		System.out.println("2: No");
		System.out.print("Enter your choice: ");
		Scanner sc = new Scanner(System.in);
		int wantSuggestionAgain = sc.nextInt();
		if (wantSuggestionAgain == 1) {
			System.out.print("Enter the movie code as listed along side movie name: ");
			int movieCode = sc.nextInt();
			showSuggestionsBasedOnMovie(customGraph, (JSONObject) customGraph.arrayOfMoviesWithDetails.get(movieCode));
		}

	}

	private static Set<String> findMoviesInSymbolGraphOfActor(SymbolGraph sg, Graph G, String[] actors) {
		Set<String> commonMovies = new HashSet<>();
		for (String actor : actors) {
			actor = actor.toLowerCase();
			if (sg.contains(actor)) {
				for (int movie : G.adj(sg.index(actor))) {
					commonMovies.add(sg.name(movie));
				}
			}
		}

		return commonMovies;
	}

	private static Set<String> findMoviesInSymbolGraphOfGenre(SymbolGraph sg, Graph G, String[] genres) {
		Set<String> commonMovies = new HashSet<>();
		for (String genre : genres) {
			if (sg.contains(genre)) {
				for (int movie : G.adj(sg.index(genre))) {
					commonMovies.add(sg.name(movie));
				}

			}
		}

		return commonMovies;
	}

	public static void searchMovieByMovieName(CreateGraph customGraph) {
		System.out.println();
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the name of Movie or a keyword: ");
		String word = sc.nextLine().strip().toLowerCase();
		System.out.println();
		
		Set<Integer> setOfMovies = customGraph.getMovieByKeyWord(word.toLowerCase(), customGraph);
		if (setOfMovies != null && setOfMovies.size() > 0) {		

			for (int i : setOfMovies) {
				String movieName = customGraph.getMovieFromIndex(i);
				System.out.print(customGraph.getCodeOfMovie(movieName) + ": ");
				System.out.println(movieName);
			}
			System.out.println();
			fetchMovie(customGraph);

		} else {
			// TODO: Edit Distance goes here.
			SpellChecker spellcheck = new SpellChecker();
			int exist = spellcheck.bymoviename(customGraph, word);

			if (exist == 0) {
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

		Iterable<Integer> listOfMovies = customGraph.getGraphForMoviesWithGenre()
				.adj(customGraph.getSymbolGraphForMoviesWithGenre().index(chosenGenre));
		for (Integer i : listOfMovies) {
			System.out.print(customGraph.getCodeOfMovie(customGraph.getSymbolGraphForMoviesWithGenre().name(i)) + ": ");
			System.out.println(customGraph.getSymbolGraphForMoviesWithGenre().name(i));
		}

		System.out.println();

		fetchMovie(customGraph);

	}

	public void main(CreateGraph customGraph) {

		showMoviesMenu();

		Scanner sc = new Scanner(System.in);
		System.out.print("Please enter your choice: ");
		int selectedOption = sc.nextInt();

		switch (selectedOption) {
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
