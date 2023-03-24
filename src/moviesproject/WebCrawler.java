package moviesproject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler {

	// Set of URLs already visited
	private Set<String> visitedUrls;

	// Queue of URLs to be visited
	private LinkedList<String> urlsToVisit;

	// Base URL for the movie search engine
	private static final String BASE_URL = "https://www.imdb.com/";

	// Constructor
	public WebCrawler() {
		visitedUrls = new HashSet<String>();
		urlsToVisit = new LinkedList<String>();
		urlsToVisit.add(BASE_URL + "/chart/top");
	}

	// Start the crawler
	public void startCrawler() {
		// Initialize JSON array to store movie information
		JSONArray moviesArray = new JSONArray();

		while (!urlsToVisit.isEmpty()) {
			String url = urlsToVisit.remove();
			if (!visitedUrls.contains(url)) {
				try {
					visitedUrls.add(url);
					Document htmlDocument = Jsoup.connect(url).get();
					Elements movieElements = htmlDocument.select(".lister-list tr");

					// Process each movie element
					for (Element movieElement : movieElements) {
						JSONObject movieObject = new JSONObject();

						// Extract movie information from the HTML element and add to JSON object
						String title = movieElement.select(".titleColumn a").text();
						String year = movieElement.select(".titleColumn span").text().replaceAll("[()]", "");
						String rating = movieElement.select(".imdbRating strong").text();

						movieObject.put("title", title);
						movieObject.put("year", year);
						movieObject.put("rating", rating);

						// Add JSON object to array
						moviesArray.add(moviesArray);
					}
//					System.out.println(moviesArray.size());

					// Extract links to other pages to be crawled
					Element nextLink = htmlDocument.select(".next a").first();
					if (nextLink != null) {
						String nextUrl = BASE_URL + nextLink.attr("href");
						urlsToVisit.add(nextUrl);
					}
				} catch (Exception e) {
					System.err.println("Error crawling " + url + ": " + e.getMessage());
				}
			}
		}

		// Write movie information to JSON file
		try {
			FileWriter writer = new FileWriter("movies-pheni.json");
			writer.write(moviesArray.toJSONString());
			writer.close();
		} catch (IOException e) {
			System.err.println("Error writing to file: " + e.getMessage());
		}
	}

	// Main method to start the program
	public static void main(String[] args) {
		WebCrawler crawler = new WebCrawler();
		crawler.startCrawler();
	}

}