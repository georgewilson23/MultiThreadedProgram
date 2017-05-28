package homework6;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Question4Runner implements Runnable {

	private ArrayList<String> paths;
	private int firstPath;
	private int lastPath;
	private  ArrayList<Coords> coords = new ArrayList<Coords>();
	
	
	
	public Question4Runner(ArrayList<String> paths, int firstPath, int lastPath)
	{
		this.paths = paths;
		this.firstPath = firstPath;
		this.lastPath = lastPath;
		
		}
	
	//get lat,long
	
	ArrayList<Coords> getLatLong(){
		return coords;
	}
	public class Coords {
		String devId;
		double lat;
		double longitude;
		//constructor
		public Coords(String devId, double lat, double longitude) {
			this.devId = devId;
			this.lat = lat;
			this.longitude = longitude;
		}
		public String getDevId(){
			return devId;
		}
		public double getLat(){
			return lat;
		}
		public double getLongitude(){
			return longitude;
		}
	}
	@Override
	public void run() {
		
		
		for (int i =  firstPath; i <= lastPath; ++i){
			String path = paths.get(i);
			FileReader jsonFile = null;
			try {
				jsonFile = new FileReader(path);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JsonParser jsonParser = new JsonParser(); 
			JsonArray parsedJSON = jsonParser.parse(jsonFile).getAsJsonArray();	
			for (int j = 0; j < parsedJSON.size(); ++j) {

				JsonObject pathJsonObject = parsedJSON.get(j).getAsJsonObject();
				
					// get lat
					JsonElement latJsonElement = pathJsonObject.get("Latitude");
					Double latDouble = latJsonElement.getAsDouble();
					
					//get long
					JsonElement longJsonElement = pathJsonObject.get("Longitude");
					Double longDouble = longJsonElement.getAsDouble();
					
					//get device Id
					
					JsonElement devIdJsonElement = pathJsonObject.get("DeviceId");
					String devIdStr = devIdJsonElement.getAsString();
					
					
					
					//add devId, lat and long into ArrayList
					
					coords.add(new Coords(devIdStr, latDouble, longDouble));
						
			}
		
	}
		

	}
}


