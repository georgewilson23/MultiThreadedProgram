package homework6;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.util.Hashtable;;

public class Question3Runner implements Runnable {

	private ArrayList<String> paths;
	private int firstPath;
	private int lastPath;
	
	//private Hashtable<LocalDate,Hashtable<String,Integer>> bigHashtable = new Hashtable<LocalDate,Hashtable<String,Integer>>();
	private Hashtable<String, Integer> comboHashtable = new Hashtable<String,Integer>();
	
	public Question3Runner(ArrayList<String> paths, int firstPath, int lastPath){
		this.paths = paths;
		this.firstPath = firstPath;
		this.lastPath = lastPath;
		
	}
	
	//get count
	
	Hashtable<String, Integer> getCount(){
		return comboHashtable;
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
				
					// get date
					JsonElement dateJsonElement = pathJsonObject.get("Timestamp");
					DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
					String timestampStr = dateJsonElement.getAsString();
					LocalDateTime timestamp = LocalDateTime.parse(timestampStr, formatter);
					LocalDate day = timestamp.toLocalDate();
					
					//get device Id
					JsonElement devIdJsonElement = pathJsonObject.get("DeviceId");
					String deviceIdStr = devIdJsonElement.getAsString();
					
					//add date + device Id  and runner count into Hashtable
					
					if (comboHashtable.containsKey(day.toString()+deviceIdStr)){
						int addedDateCount = comboHashtable.get(day.toString()+deviceIdStr) + 1;
						comboHashtable.put(day.toString()+deviceIdStr, addedDateCount);
					}
					else comboHashtable.put(day.toString()+deviceIdStr, 1);
		

	}
		}
	}
}



