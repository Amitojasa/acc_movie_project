package moviesproject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class SpellChecker {
	

	public static int byActor(CreateGraph customGraph, String nameOfActor) {
		int i = 0;		
		Set<String> uniquecasts = customGraph.getUniqueCast();
		
		Iterator<String> castIterator = uniquecasts.iterator();
		
		HashSet<String> result = new HashSet<String>();
		
		while(castIterator.hasNext()) {
			String actor = castIterator.next();
			
			String[] splitedactor = actor.trim().split(" ");
			String[] splitedInputActor = nameOfActor.trim().split(" ");
			if(splitedInputActor.length>1) {
				 if(Sequences.editDistance(actor.toLowerCase(), nameOfActor) < 4) {
		    		result.add(actor);
		    		 i++;
		    	 }
			}else {
				for(String word : splitedactor) {

						 if(Sequences.editDistance(word.toLowerCase(), nameOfActor) < 3) {
							 result.add(actor);
				    		 i++;
				    	 }

				}
			}
			
		}
		if (!result.isEmpty()) {
			System.out.println("Do you mean by....");
		}
		
		for(String s : result) {
			System.out.println(s);
		}
		return i;
	}
	
	public static int bymoviename(CreateGraph customGraph, String nameOfMovie) {
		int i = 0;
		
		List<String> allmovies = customGraph.getAllMovieNames();

		
		HashSet<String> result = new HashSet<String>();
		Iterator<String> movies = allmovies.iterator();
		
		while(movies.hasNext()) {
			String moviename = movies.next();
			
			String[] splitedmovienames = moviename.trim().split(" ");
			String[] splitedInputMovieName = nameOfMovie.trim().split(" ");
			
			
			if(splitedInputMovieName.length>1) {
				 if(Sequences.editDistance(moviename.toLowerCase(), nameOfMovie) < 4) {
		    		 result.add(moviename);
		    		 i++;
		    	 }
			}else {
				for(String word : splitedmovienames) {
						 if(Sequences.editDistance(word.toLowerCase(), nameOfMovie) < 3) {
							 result.add(moviename);
				    		 i++;
				    	 }
				}
			}
		}
		
		if (!result.isEmpty()) {
			System.out.println("Do you mean by....");
		}
		for(String s : result) {
			System.out.println(s);
		}
		return i;
	}

	
	
	public static int byGenre(CreateGraph customGraph, String nameOfGenre) {
		int i = 0;
		
		Set<String> allGenres = customGraph.getAllGenres();

		
		HashSet<String> result = new HashSet<String>();
		Iterator<String> genres = allGenres.iterator();
		
		while(genres.hasNext()) {
			String genre = genres.next();
			
			String[] splitedGenre = genre.trim().split(" ");
			String[] splitedInputGenre = nameOfGenre.trim().split(" ");
			
			
			if(splitedInputGenre.length>1) {
				 if(Sequences.editDistance(genre.toLowerCase(), nameOfGenre) < 3) {
		    		 result.add(genre);
		    		 i++;
		    	 }
			}else {
				for(String word : splitedGenre) {
						 if(Sequences.editDistance(word.toLowerCase(), nameOfGenre) < 2) {
							 result.add(genre);
				    		 i++;
				    	 }

				}
			}
			
			

		}
		if (!result.isEmpty()) {
			System.out.println("Do you mean by....");
		}
		for(String s : result) {
			
			System.out.println(s);
		}
		return i;
	}

}
