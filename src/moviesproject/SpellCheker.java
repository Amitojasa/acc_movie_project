package moviesproject;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class SpellCheker {
	

	public static int main(CreateGraph customGraph, String nameOfActor) {
		int i = 0;		
		Set<String> uniquecasts = customGraph.getUniqueCast();
		
		Iterator<String> castIterator = uniquecasts.iterator();
		
		System.out.println("Do you mean by....");
		while(castIterator.hasNext()) {
			String actor = castIterator.next();
			 if(Sequences.editDistance(actor, nameOfActor) < 3) {
	    		 System.out.println(actor);
	    		 i++;
	    	 }
		}
		return i;
	}
	
	public static int bymoviename(CreateGraph customGraph, String nameOfMovie) {
		int i = 0;
		
		List<String> allmovies = customGraph.getAllMovieNames();
		System.out.println(allmovies);
		
		Iterator<String> movies = allmovies.iterator();
		
		System.out.println("Do you mean by....");
		while(movies.hasNext()) {
			String moviename = movies.next();
			 if(Sequences.editDistance(moviename, nameOfMovie) < 3) {
	    		 System.out.println(moviename);
	    		 i++;
	    	 }
		}
		return i;
	}


}
