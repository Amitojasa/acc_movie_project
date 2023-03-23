package moviesproject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Movie {

	private String movieName;
    private String movieRating;
    private String movieDesc, movieLength, movieyear;
    private JSONArray genres;
    private JSONArray cast;
    
    
    // Add other movie attributes here
    
    public Movie(String title, String rating, String desc, String length, String year, JSONArray genres, JSONArray cast) {
        this.movieName = title;
        this.movieRating = rating;
        this.movieDesc = movieDesc;
        this.movieLength = length;
        this.movieyear = year;
        this.genres = genres;
        this.cast = cast;
        // Initialize other movie attributes here
    }
    
    public String getMovieName() {
        return movieName;
    }
    
    public String getMovieDesc() {
    	return movieDesc;
    }
    
    public String getMovieDuration() {
    	return movieLength;
    }
    
    public String getMovieRelease() {
    	return movieyear;
    }
    
    public JSONArray getMovieGenres() {
    	return genres;
    }
    
    public JSONArray getMovieCast() {
    	return cast;
    }
    
    public double getRating() {
    	try {
    		return Double.parseDouble(movieRating.strip());			
		} catch (NumberFormatException e) {
			return (double) 0;
		}
    }
}
