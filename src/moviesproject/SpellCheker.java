package moviesproject;

import java.util.Arrays;
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
			
			String[] splitedactor = actor.trim().split(" ");
			
			for(String word : splitedactor) {
				
				if (nameOfActor.length() <= 7) {
					 if(Sequences.editDistance(word, nameOfActor) < 3) {
			    		 System.out.println(actor);
			    		 i++;
			    	 }
				}
				else if(nameOfActor.length() > 7 && nameOfActor.length()<= 15) {
					if(Sequences.editDistance(word, nameOfActor) < 6) {
			    		 System.out.println(actor);
			    		 i++;
			    	 }
				}
				else {
					if(Sequences.editDistance(word, nameOfActor) < 10) {
			    		 System.out.println(actor);
			    		 i++;
			    	 }
				}
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
			
			String[] splitedmovienames = moviename.trim().split(" ");
			
			for(String word : splitedmovienames) {
//				System.out.println(word);
				if (nameOfMovie.length() <= 7) {
					 if(Sequences.editDistance(word, nameOfMovie) < 3) {
			    		 System.out.println(moviename);
			    		 i++;
			    	 }
				}
				else if(nameOfMovie.length() > 7 && nameOfMovie.length()<= 15) {
					if(Sequences.editDistance(word, nameOfMovie) < 6) {
			    		 System.out.println(moviename);
			    		 i++;
			    	 }
				}
				else {
					if(Sequences.editDistance(word, nameOfMovie) < 10) {
			    		 System.out.println(moviename);
			    		 i++;
			    	 }
				}
			}

		}
		return i;
	}


}
