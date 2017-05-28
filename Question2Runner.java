package homework6;
import java.util.ArrayList;
import java.util.Date;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.util.Hashtable;

public class Question2Runner implements Runnable {

	private ArrayList<String> paths;
	private int firstPath;
	private int lastPath;
	private Hashtable<String, Integer> devIdCount = new Hashtable<String, Integer>();
	
	
	
	public Question2Runner(ArrayList<String> paths, int firstPath, int lastPath)
	{
		this.paths = paths;
		this.firstPath = firstPath;
		this.lastPath = lastPath;
		
		}
	
	//get deviceId count -- Hashtable
	
	Hashtable<String, Integer> getDevIdCount(){
		return devIdCount;
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
				
					// get device Id
					JsonElement devIdJsonElement = pathJsonObject.get("DeviceId");
					String deviceIdStr = devIdJsonElement.getAsString();
					
					//add devId and runner count into Hashtable
					
					if (devIdCount.containsKey(deviceIdStr)){
						int addedCount = devIdCount.get(deviceIdStr) + 1;
						devIdCount.put(deviceIdStr, addedCount);
					}
					else devIdCount.put(deviceIdStr, 1);
						
			}
		
	}
		

	}
}


