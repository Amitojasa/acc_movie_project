package moviesproject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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
public class CreateGraph {
	
	
	 List<String> movieNames=new ArrayList<String>();
	 Set<String> uniqueCast = new HashSet<String>();
	 List<String> collectionMovieAndCast=new ArrayList<String>();
	 List<List<String>> collectionCastRespectToMovie=new ArrayList();
	 
	 List<List<String>> collectionMovieRespectToGenre = new ArrayList();
	 
	 SymbolGraph sgForMovieCast, sgForMovieGenre;
	 Graph movieCastGraph, movieGenreGraph;
	 
	 public  void createGraphFromJson() throws FileNotFoundException, IOException, ParseException {
		
		
		JSONParser parserForJson = new JSONParser();
		JSONArray arrayOfMoviesWithDetails = (JSONArray) parserForJson.parse(new FileReader("moviesNew.json"));
		 
		for (Object movieObject : arrayOfMoviesWithDetails){
			  
		    JSONObject movie = (JSONObject) movieObject;
		    
		    List<String> temp=new ArrayList<String>();
		    List<String> tempListToHandleMovieGenre = new ArrayList<String>();
		    
		    String name = (String) movie.get("movieName");
		    
		    movieNames.add(name);
		    collectionMovieAndCast.add(name);
		    temp.add(name);
		    tempListToHandleMovieGenre.add(name);
		    
		  
		    JSONArray casts = (JSONArray) movie.get("cast");
		    for (Object cast : casts) {
		    	uniqueCast.add((String)cast);
		    	collectionMovieAndCast.add((String)cast);
		    	temp.add((String)cast);
		    }
		    
		    JSONArray genres = (JSONArray) movie.get("genres");
		    for (Object genre: genres) {
		    	tempListToHandleMovieGenre.add((String)genre);
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
	
	public List<String> getUniqueCast(){
		return (List<String>) uniqueCast;
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
	
	
}
