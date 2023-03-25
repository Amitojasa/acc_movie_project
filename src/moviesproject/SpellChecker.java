package moviesproject;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import common.Sequences;

public class SpellChecker {
	

	public static int byActor(CreateGraph customGraph, String nameOfActor) {
		int i = 0;	
		
		// Create set based on unique cast name from custom graph 
		Set<String> uniquecasts = customGraph.getUniqueCast();
		
		// To iterate through set of cast
		Iterator<String> castIterator = uniquecasts.iterator();
		
		// Create hashset to store result of spell checking
		HashSet<String> result = new HashSet<String>();
		
		// Iterate through castIterator set till it reach to last value
		while(castIterator.hasNext()) {
			String actor = castIterator.next();
			
			// Create string array of actor name splited by space 
			String[] splitedactor = actor.trim().split(" ");
			//Create string array of user input splited by space 
			String[] splitedInputActor = nameOfActor.trim().split(" ");
			
			// If splited array of user input length is grater than 1
			if(splitedInputActor.length>1) {
				// call the edit distance method, and if the returned edit distance is less than 4
				 if(Sequences.editDistance(actor.toLowerCase(), nameOfActor) < 4) {
					 // add actor name into result hashset
		    		result.add(actor);
		    		 i++;
		    		 // To show first 5 results of edit distance
		    		 if(i==4) {
		    			 break;
		    		 }
		    	 }
			}else {
//				for(String word : splitedactor) {
//
//						 if(Sequences.editDistance(word.toLowerCase(), nameOfActor) < 3) {
//							 result.add(actor);
//				    		 i++;
//				    	 }
//
//				}
			}
			
		}
		// if result hashset has values
		if (!result.isEmpty()) {
			System.out.println("Do you mean by....");
		}
		
		// Iterate through result hashset and print all suggestion based on spell checking
		for(String s : result) {
			System.out.println(s);
		}
		return i;
	}
	
	public static int bymoviename(CreateGraph customGraph, String nameOfMovie) {
		int i = 0;
		
		// Create list based on allmovienames from custom graph 
		List<String> allmovies = customGraph.getAllMovieNames();

		// Create hashset to store result of spell checking
		HashSet<String> result = new HashSet<String>();
		Iterator<String> movies = allmovies.iterator();
		
		// Iterate through movies list till it reach to last value
		while(movies.hasNext()) {
			String moviename = movies.next();
			
			// Create string array of movie name splited by space 
			String[] splitedmovienames = moviename.trim().split(" ");
			//Create string array of user input splited by space 
			String[] splitedInputMovieName = nameOfMovie.trim().split(" ");
			
			// If splited array of user input length is grater than 1
			if(splitedInputMovieName.length>1) {
				// call the edit distance method, and if the returned edit distance is less than 4
				 if(Sequences.editDistance(moviename.toLowerCase(), nameOfMovie) < 4) {
		    		 result.add(moviename);
		    		 i++;
		    		 if(i==4) {
		    			 break;
		    		 }
		    	 }
			}else {
				// Iterate through splited movie names array
				for(String word : splitedmovienames) {
					// If length of user input is grater than 5 and edit distance is less than 3 then
						 if(nameOfMovie.length()>5 && Sequences.editDistance(word.toLowerCase(), nameOfMovie) < 3) {
							 // add actor name into result hashset
							 result.add(moviename);
				    		 i++;
				    		 if(i==4) {
				    			 break;
				    		 }
				    	 }
						// If length of user input is grater than 3 and edit distance is less than 2 then
						 else if(nameOfMovie.length()>3 && Sequences.editDistance(word.toLowerCase(), nameOfMovie) < 2) {
							 result.add(moviename);
				    		 i++;
				    		 if(i==4) {
				    			 break;
				    		 }
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
		
		// Create set based on unique genres name from custom graph
		Set<String> allGenres = customGraph.getAllGenres();

		
		HashSet<String> result = new HashSet<String>();
		Iterator<String> genres = allGenres.iterator();
		
		// Iterate through genre set till it reach to last value
		while(genres.hasNext()) {
			String genre = genres.next();
			
			// Create string array of genre name splited by space 
			String[] splitedGenre = genre.trim().split(" ");
			//Create string array of user input splited by space 
			String[] splitedInputGenre = nameOfGenre.trim().split(" ");
			
			// If splited array of user input length is grater than 1
			if(splitedInputGenre.length>1) {
				 if(Sequences.editDistance(genre.toLowerCase(), nameOfGenre) < 3) {
		    		 result.add(genre);
		    		 i++;
		    		 if(i==4) {
		    			 break;
		    		 }
		    	 }
			}else {
//				for(String word : splitedGenre) {
//						 if(Sequences.editDistance(word.toLowerCase(), nameOfGenre) < 2) {
//							 result.add(genre);
//				    		 i++;
//				    	 }
//
//				}
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
