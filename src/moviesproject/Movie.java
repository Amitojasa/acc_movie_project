package moviesproject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Movie {

	private String movieName;
    private String movieRating;
    private String movieDesc, movieLength, movieyear;
    
    // Add other movie attributes here
    
    public Movie(String title, String rating, String desc, String length, String year) {
        this.movieName = title;
        this.movieRating = rating;
        this.movieDesc = movieDesc;
        this.movieLength = length;
        this.movieyear = year;
        // Initialize other movie attributes here
    }
    
    public String getMovieName() {
        return movieName;
    }
    
    public String getRating() {
        return movieRating;
    }
}
