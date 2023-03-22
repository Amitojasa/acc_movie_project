package moviesproject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import graphs.Graph;
import graphs.SymbolGraph;
import graphs.TST;
public class CreateGraph {
	
	
	 List<String> movieNames = new ArrayList<String>();
	 Set<String> uniqueCast = new HashSet<String>();
	 Set<String> setOfGenres = new HashSet<String>();
	 List<String> collectionMovieAndCast = new ArrayList<String>();
	 List<List<String>> collectionCastRespectToMovie = new ArrayList();
	 
	 List<List<String>> collectionMovieRespectToGenre = new ArrayList();
	 
	 Hashtable<String, Integer> movieIndex = new Hashtable<String, Integer>();
	 JSONArray arrayOfMoviesWithDetails = new JSONArray();
	 
	 SymbolGraph sgForMovieCast, sgForMovieGenre;
	 Graph movieCastGraph, movieGenreGraph;
	 TST<Set<Integer>> ternarySearchTrie = new TST<Set<Integer>>();
	 
	 Movie[] arrayForSorting;
	 
	 
	 
	 public void putMovieInTrie(String nameOfMovie, int currentIndex) {
		 String[] wordsInMovies = nameOfMovie.toLowerCase().split(" ");
		 
		 for (String word : wordsInMovies) {
			 Set<Integer> indexesOfMovies= new HashSet<Integer>();
			 
			 if (!ternarySearchTrie.contains(word)) {
				 indexesOfMovies.add(currentIndex);
				 ternarySearchTrie.put(word, indexesOfMovies);
			 }
			 else {
				 for (int index : ternarySearchTrie.get(word)) {
					 indexesOfMovies.add(index);
				 }
				 indexesOfMovies.add(currentIndex);
				 ternarySearchTrie.put(word, indexesOfMovies);
			 }
		 }		  
	 }
	 
	 public void createGraphFromJson() throws FileNotFoundException, IOException, ParseException {
		
		
		JSONParser parserForJson = new JSONParser();
		arrayOfMoviesWithDetails = (JSONArray) parserForJson.parse(new FileReader("moviesNew.json"));
		Movie[] arrayforSorting = createArrayForMovies(arrayOfMoviesWithDetails);
		
		int currentIndexOfMovie = 0;
		 
		for (Object movieObject : arrayOfMoviesWithDetails){
			  
		    JSONObject movie = (JSONObject) movieObject;
		    
		    List<String> temp=new ArrayList<String>();
		    List<String> tempListToHandleMovieGenre = new ArrayList<String>();
		    
		    String name = (String) movie.get("movieName");
		    
		    putMovieInTrie(name, currentIndexOfMovie);
		    
		    movieNames.add(name);
		    collectionMovieAndCast.add(name);
		    temp.add(name);
		    tempListToHandleMovieGenre.add(name);
		    
		    movieIndex.put(name, currentIndexOfMovie);
		    currentIndexOfMovie += 1;
		    
		  
		    JSONArray casts = (JSONArray) movie.get("cast");
		    for (Object cast : casts) {
		    	uniqueCast.add((String)cast);
		    	collectionMovieAndCast.add((String)cast);
		    	temp.add(((String)cast).toLowerCase());
		    }
		    
		    JSONArray genres = (JSONArray) movie.get("genres");
		    for (Object genre: genres) {
		    	tempListToHandleMovieGenre.add((String)genre);
		    	setOfGenres.add((String) genre);
		    }
		    
		    collectionCastRespectToMovie.add(temp);
		    collectionMovieRespectToGenre.add(tempListToHandleMovieGenre);
		    
		  }
		 	  
		sgForMovieCast= new SymbolGraph(collectionCastRespectToMovie);
		  
		sgForMovieGenre = new SymbolGraph(collectionMovieRespectToGenre);
		    
		movieCastGraph = sgForMovieCast.G();
		movieGenreGraph = sgForMovieGenre.G();
	    
		String s = movieCastGraph.toString();
	    
		System.out.println("");
		if(sgForMovieCast.contains("Nick Preston")) {
			Iterable<Integer> t = movieCastGraph.adj(sgForMovieCast.index("Nick Preston"));
			for(Integer i : t) {
				System.out.println(sgForMovieCast.name(i));        		
			  	}
		  }
	}
	
	
	public List<String> getAllMovieNames(){
		return movieNames;
	}
	
	public String getMovieFromIndex(int indexOfMovie) {
		JSONObject movieObject = (JSONObject) arrayOfMoviesWithDetails.get(indexOfMovie);		
		return (String) movieObject.get("movieName");
	}
	
	public void printMovieNameFromIndex(int indexOfMovie) {
		System.out.println();
	}
	
	public Set<String> getUniqueCast(){
		return uniqueCast;
	}
	
	public Set<String> getAllGenres(){
		return setOfGenres;
	}
	
	public Graph getGraphForMoviesWithCast() {
		return movieCastGraph;
	}
	
	public SymbolGraph getSymbolGraphForMoviesWithCast() {
		return sgForMovieCast;
	}
	
	public Graph getGraphForMoviesWithGenre() {
		return movieGenreGraph;
	}
	
	public SymbolGraph getSymbolGraphForMoviesWithGenre() {
		return sgForMovieGenre;
	}
	
	public List<List<String>> getMoviesWithGenre(){
		return collectionMovieRespectToGenre;
	}
	
	public int getCodeOfMovie(String nameOfMovie){
		return movieIndex.get(nameOfMovie);
	}
	
	public Set<Integer> getMovieByKeyWord(String key) {
		String[] arr = key.toLowerCase().split(" ");
		if (arr.length == 1) {
			return ternarySearchTrie.get(key);
		}
		else {
			Set<Integer> newSet= new HashSet<Integer>();
			System.out.println("Boyer Moore Goes here");
//			TODO: Boyer Moore
			return (newSet);
		}
	}
	
	public void printMovieDetails(int movieCode) {
		JSONObject movieObject = (JSONObject) arrayOfMoviesWithDetails.get(movieCode);
		
		ArrayList genres = (ArrayList)movieObject.get("genres");
		
		System.out.println();
		
		System.out.println("Name 	   : " +movieObject.get("movieName"));
		System.out.println("Description: " +movieObject.get("movieDesc"));
		System.out.println("Duration   : " +movieObject.get("movieLength"));
		System.out.println("Release    : " +movieObject.get("movieYear"));
		System.out.println("Rating	   : " +movieObject.get("movieRating"));
		System.out.println("Genres	   : " +movieObject.get("genres"));
		System.out.println("Cast	   : " +movieObject.get("cast"));
	}
	

	public static Movie[] createArrayForMovies(JSONArray jsonArray) {
		Movie[] movieArray = new Movie[jsonArray.size()];
        int j = 0;
        for (Object i : jsonArray) {
       
		    JSONObject currentMovie = (JSONObject) i;
       
            Movie movie = new Movie((String)currentMovie.get("movieName"), (String)currentMovie.get("movieRating"), (String)currentMovie.get("movieDesc"), (String)currentMovie.get("movieLength"), (String)currentMovie.get("movieYear"));
            // Add other movie attributes to movie object here
            movieArray[j] = movie;
            j += 1;
        }
        return movieArray;
	}
    
}
