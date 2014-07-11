package com.goeuro.application;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.goeuro.bean.Location;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class InformationService {

	/**
	 * retrieveJsonObjects converts the JSON string retrieved from the REST API
	 * to a list of java objects
	 * 
	 * @param jsonString
	 * @return
	 */
	public List<Location> retrieveJsonObjects(String jsonString) {

		Gson gson = new Gson();
		Type type = new TypeToken<List<Location>>() {
		}.getType();
		return (gson.fromJson(jsonString, type));
	}

	/**
	 * generateCSV method writes all the objects in the list to a output.csv
	 * file
	 * 
	 * @param locationObjects
	 */
	public void generateCSV(List<Location> locationObjects) {

		try {
			FileWriter writer = new FileWriter("output.csv");

			writer.append("_type");
			writer.append(',');
			writer.append("_id");
			writer.append(',');
			writer.append("type");
			writer.append(',');
			writer.append("latitude");
			writer.append(',');
			writer.append("longitude");
			writer.append('\n');

			for (Location location : locationObjects) {

				writer.append(location.get_type());
				writer.append(',');
				writer.append(location.get_id());
				writer.append(',');
				writer.append(location.getType());
				writer.append(',');
				writer.append(location.getGeo_position().getLatitude());
				writer.append(',');
				writer.append(location.getGeo_position().getLongitude());
				writer.append('\n');
			}
			writer.flush();
			writer.close();
			System.out.println("File generated: output.csv");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
