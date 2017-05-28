package homework6;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import homework6.Question4Runner.Coords;

public class Question4 {

	int bound1;
	int bound2;
	int bound3;
	int bound4;
	
	public static void main(String[] args) throws Exception {
		
		//get file paths
		ArrayList<String> locPaths = new ArrayList<String>();
		
		
		Files.walk(Paths.get("C:\\Users\\Student\\Documents\\Sem 4\\Sys 2202\\sp2017sys2202_mobile_data\\LocationDatum")).filter(Files::isRegularFile).map(path -> path.toString()).forEach(locPaths::add);
		
		int bound1 = (int) locPaths.size()/4;
		int bound2 = (int) locPaths.size()/2;
		int bound3 = (int) locPaths.size()*(3/4);
		int bound4 = (int) locPaths.size();
		
		// work with the first set of paths
		Question4Runner runner1 = new Question4Runner(locPaths, 0, bound1);
		Thread thread1 = new Thread(runner1);
		thread1.start();
		
		// split up the rest of the paths
		Question4Runner runner2 = new Question4Runner(locPaths, bound1 + 1, bound2);
		Thread thread2 = new Thread(runner2);
		thread2.start();
		
		Question4Runner runner3 = new Question4Runner(locPaths, bound2 + 1, bound3);
		Thread thread3 = new Thread(runner3);
		thread3.start();
		
		Question4Runner runner4 = new Question4Runner(locPaths, bound3 + 1, bound4 - 1);
		Thread thread4 = new Thread(runner4);
		thread4.start();	
			
		// wait for all count runners to finish
		thread1.join();
		thread2.join();
		thread3.join();
		thread4.join();
		
		// merge results from the individual count runners into a single merged hashtable
		
		ArrayList<Coords> mergedDataFinal = new ArrayList<Coords>();
		for(Coords cord : runner1.getLatLong())
			{
				mergedDataFinal.add(cord);
			}
		for(Coords cord : runner2.getLatLong())
		{
			mergedDataFinal.add(cord);
		}
		for(Coords cord : runner3.getLatLong())
		{
			mergedDataFinal.add(cord);
		}
		for(Coords cord : runner4.getLatLong())
		{
			mergedDataFinal.add(cord);
		}
	
		//Write to CSV	
			
		PrintWriter Question4Csv = new PrintWriter("C:\\Users\\Student\\Documents\\Sem 4\\Sys 2202\\Question4.csv");
		
		// write the header row
		Question4Csv.write("deviceId,latitude,longitude\n");
		
		//add each day and count
		
		for (Coords coord: mergedDataFinal){	
			Question4Csv.write(coord.getDevId()+ "," + coord.getLat() + "," + coord.getLongitude() +"\n");
		}
		
		Question4Csv.write("\"\n");
		
		// close the file!
		Question4Csv.close();
	}
		
	
}