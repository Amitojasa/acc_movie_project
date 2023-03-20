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
	 SymbolGraph sg;
	 Graph g;
	public  void createGraphFromJson() throws FileNotFoundException, IOException, ParseException {
		
		JSONParser parser=new JSONParser();
		  JSONArray a = (JSONArray) parser.parse(new FileReader("moviesNew.json"));
		 
		  for (Object o : a)
		  {
		    JSONObject movie = (JSONObject) o;
		    List<String> temp=new ArrayList<String>();
		    String name = (String) movie.get("movieName");
		    movieNames.add(name);
		    collectionMovieAndCast.add(name);
		    temp.add(name);
		    
		  
		    JSONArray casts = (JSONArray) movie.get("cast");
		    for (Object cast : casts) {
		    	uniqueCast.add((String)cast);
		    	collectionMovieAndCast.add((String)cast);
		    	temp.add((String)cast);
		    }
		    
		    collectionCastRespectToMovie.add(temp);
		    	
		   
		    
		    

		    
		  }
		  
		  sg= new SymbolGraph(collectionCastRespectToMovie);
		    
		    g=sg.G();
		    
		    String s=g.toString();
		    
		    System.out.println("");
	        if(sg.contains("Nick Preston")) {
	        	Iterable<Integer> t=g.adj(sg.index("Nick Preston"));
	        	for(Integer i : t) {
	        		System.out.println(sg.name(i));        		
	        	}
	        }
		    
		
	}
	
	
	public List<String> getAllMovieNames(){
		return movieNames;
	}
	
	public List<String> getUniqueCast(){
		return (List<String>) uniqueCast;
	}
	
	
	public Graph getGraph() {
		return g;
	}
	
	public SymbolGraph getSymbolGraph() {
		return sg;
	}
	
	
	
}
