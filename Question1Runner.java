package homework6;
import java.util.ArrayList;
import java.util.Date;
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


import java.util.Hashtable;

public class Question1Runner implements Runnable {

	private ArrayList<String> paths;
	private int firstPath;
	private int lastPath;
	private Hashtable<LocalDate, Integer> dateCount = new Hashtable<LocalDate, Integer>();
	
	
	
	public Question1Runner(ArrayList<String> paths, int firstPath, int lastPath)
	{
		this.paths = paths;
		this.firstPath = firstPath;
		this.lastPath = lastPath;
		
		}
	
	//get Date count -- Hashtable
	
	Hashtable<LocalDate, Integer> getDateCount(){
		return dateCount;
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
					
					//add date and runner count into Hashtable
					
					if (dateCount.containsKey(day)){
						int addedDateCount = dateCount.get(day) + 1;
						dateCount.put(day, addedDateCount);
					}
					else dateCount.put(day, 1);
						
			}
		
	}
		

	}
}


