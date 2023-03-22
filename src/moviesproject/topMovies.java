package moviesproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class topMovies {
	
	public static int showMenu() {
		System.out.println();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter number of movies that you want to get between 1 to 50:");
		int numberOfMovies = sc.nextInt();
//		System.out.println("1: Get top " +numberOfMovies+ " based on Actor?");
//		System.out.println("2: Get top " +numberOfMovies+ " based on Genre?");
//		System.out.println("3: Get top " +numberOfMovies+ " based on overall Rating?");
		
		return numberOfMovies;
	}
	
	
	
	public static void main(CreateGraph customGraph) {
		
		int numberOfMovies = showMenu();
		Movie[] arr = customGraph.createArrayForMovies(customGraph.arrayOfMoviesWithDetails);
		System.out.println(arr.length);
		
	}

}