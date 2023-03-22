package moviesproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class topMovies {
  FileReader f = new FileReader("moviesNew");
  BufferedReader br = new BufferedReader(f);
  String currentJSONString = "";
  List<String> jsonObjectArray = new ArrayList<String>();
  
  while ((currentJSONString = br.readline()) != null) {
    JSONObject currentObject = new JSONObject();
    jsonObjectArray.add(currentObject);
  }
  JSONArray jsonArray = new JSONArray();
  for (int i = 0; i < jsonObjectArray.size(); i++) {
    JSONObject jsonObject = (JSONObject) jsonObjectArray.get(i);

  }

}