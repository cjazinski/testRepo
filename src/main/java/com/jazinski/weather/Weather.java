package com.jazinski.weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import com.jayway.jsonpath.JsonPath;

public class Weather {
	private static String APIKey;
	/**
	 * Returns a Map that has the longitude and latitude for a given zip or address
	 * Uses the google geocode API to get the resolution of the address
	 * 1,000 calls a day free
	 * @return Map with keys lng, lat and location
	 */
	public Weather(String APIKey) {
		this.setAPIKey(APIKey);
	}
	
	public Map<String, String> getWeather(String zipCode) throws Exception {
		Map<String, String> latLong = getLatLong(zipCode);
		URL APIUrl = new URL("https://api.darksky.net/forecast/abf2672d3299b8822b57541bc46288cb/" + latLong.get("lat") + "," + latLong.get("lng"));
		
		HttpsURLConnection connection = (HttpsURLConnection) APIUrl.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		String output = "";
		String outputCache = "";
		while ((outputCache = br.readLine()) != null) {
			output += outputCache;
		}
		br.close();
		// Parse the JSON return and check status value
		List<Double> tmp = JsonPath.parse(output.toString()).read("$..currently.temperature");
		List<String> summary = JsonPath.parse(output.toString()).read("$..currently.summary");
		Map<String, String> weather = new HashMap<String, String>();
		
		weather.put("summary", summary.get(0));
		weather.put("temp", tmp.get(0).toString() + " F");
		weather.put("location", latLong.get("loc"));
		
		return weather;
	}
		
	private static Map<String, String> getLatLong(String zip) throws Exception {
		URL APIUrl = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + zip + "&key=" + getAPIKey());
		
		HttpsURLConnection connection = (HttpsURLConnection) APIUrl.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		String output = "";
		String outputCache = "";
		while ((outputCache = br.readLine()) != null) {
			output += outputCache;
		}
		br.close();
		// Parse the JSON return and check status value
		List<String> latList = JsonPath.parse(output.toString()).read("$..lat");
		List<String> lngList = JsonPath.parse(output.toString()).read("$..lng");
		List<String> location = JsonPath.parse(output.toString()).read("$..formatted_address");
		Double lat = Double.parseDouble(String.valueOf(latList.get(2)));
		Double lng = Double.parseDouble(String.valueOf(lngList.get(2)));
		Map<String, String> lngLat = new HashMap<String, String>();
		
		lngLat.put("lat", lat.toString());
		lngLat.put("lng", lng.toString());
		lngLat.put("loc", location.get(0));
		
		return lngLat;
	}
	
	private static String getAPIKey() {
		return APIKey;
	}
	
	private void setAPIKey(String aPIKey) {
		APIKey = aPIKey;
	}
}