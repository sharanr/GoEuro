package com.goeuro.application;

import java.util.List;

import com.goeuro.bean.Location;
import com.goeuro.utilities.RestClient;

public class LocationService {
	/**
	 * Main method which calls all the other relevant methods to read from the
	 * URL and generate file.
	 * 
	 * @param args
	 */

	public static void main(String[] args) {

		try {
			// Reads the array of JSON objects from the location url+argument
			RestClient restClient = new RestClient();
			String url = "https://www.goeuro.com/GoEuroAPI/rest/api/v2/position/suggest/en/"
					+ args[0];
			String jsonString = restClient.retrieveResource(url);

			// Converts the array of JSON objects to list of Java objects and
			// then generates a csv file
			InformationService informationService = new InformationService();
			List<Location> locationObjects = informationService
					.retrieveJsonObjects(jsonString);
			
			if(!locationObjects.isEmpty()){
				informationService.generateCSV(locationObjects);
			}
			else {
				System.out.println("No Information available about "+args[0]);
			}
			
		} catch (ArrayIndexOutOfBoundsException aIOBE) {
			System.out.println("Please enter at least one location\nUsage: java -jar GoEuroTest.jar <CityName>");
		}
	}

}
