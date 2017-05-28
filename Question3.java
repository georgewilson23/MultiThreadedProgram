package homework6;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

public class Question3 {

	int bound1;
	int bound2;
	int bound3;
	int bound4;
	public static void main(String[] args) throws Exception {
		
		//get file paths
		ArrayList<String> accelPaths = new ArrayList<String>();
		
		Files.walk(Paths.get("C:\\Users\\Student\\Documents\\Sem 4\\Sys 2202\\sp2017sys2202_mobile_data\\PointOfInterestProximityDatum")).filter(Files::isRegularFile).map(path -> path.toString()).forEach(accelPaths::add);
		Files.walk(Paths.get("C:\\Users\\Student\\Documents\\Sem 4\\Sys 2202\\sp2017sys2202_mobile_data\\AccelerometerDatum")).filter(Files::isRegularFile).map(path -> path.toString()).forEach(accelPaths::add);
		Files.walk(Paths.get("C:\\Users\\Student\\Documents\\Sem 4\\Sys 2202\\sp2017sys2202_mobile_data\\BatteryDatum")).filter(Files::isRegularFile).map(path -> path.toString()).forEach(accelPaths::add);
		Files.walk(Paths.get("C:\\Users\\Student\\Documents\\Sem 4\\Sys 2202\\sp2017sys2202_mobile_data\\LightDatum")).filter(Files::isRegularFile).map(path -> path.toString()).forEach(accelPaths::add);
		Files.walk(Paths.get("C:\\Users\\Student\\Documents\\Sem 4\\Sys 2202\\sp2017sys2202_mobile_data\\LocationDatum")).filter(Files::isRegularFile).map(path -> path.toString()).forEach(accelPaths::add);
		Files.walk(Paths.get("C:\\Users\\Student\\Documents\\Sem 4\\Sys 2202\\sp2017sys2202_mobile_data\\SoundDatum")).filter(Files::isRegularFile).map(path -> path.toString()).forEach(accelPaths::add);
		Files.walk(Paths.get("C:\\Users\\Student\\Documents\\Sem 4\\Sys 2202\\sp2017sys2202_mobile_data\\AltitudeDatum")).filter(Files::isRegularFile).map(path -> path.toString()).forEach(accelPaths::add);
		
		
		int bound1 = (int) accelPaths.size()/4;
		int bound2 = (int) accelPaths.size()/2;
		int bound3 = (int) accelPaths.size()*(3/4);
		int bound4 = (int) accelPaths.size();
		
		// work with the first set of paths
		Question3Runner runner1 = new Question3Runner(accelPaths, 0, bound1);
		Thread thread1 = new Thread(runner1);
		thread1.start();
		
		// split up the rest of the paths
		Question3Runner runner2 = new Question3Runner(accelPaths, bound1 + 1, bound2);
		Thread thread2 = new Thread(runner2);
		thread2.start();
		
		Question3Runner runner3 = new Question3Runner(accelPaths, bound2 + 1, bound3);
		Thread thread3 = new Thread(runner3);
		thread3.start();
		
		Question3Runner runner4 = new Question3Runner(accelPaths, bound3 + 1, bound4 - 1);
		Thread thread4 = new Thread(runner4);
		thread4.start();	
			
		// wait for all count runners to finish
		thread1.join();
		thread2.join();
		thread3.join();
		thread4.join();
		
		// merge results from the individual count runners into a single merged hashtable
		
		Hashtable<String,Integer> mergedCount = new Hashtable<String,Integer>();
		MergeCounts(runner1.getCount(), mergedCount);
		MergeCounts(runner2.getCount(), mergedCount);
		MergeCounts(runner3.getCount(), mergedCount);
		MergeCounts(runner4.getCount(), mergedCount);
	
		
	
		//Write to CSV	
			
		PrintWriter Question3Csv = new PrintWriter("C:\\Users\\Student\\Documents\\Sem 4\\Sys 2202\\Question3.csv");
		
		// write the header row
		Question3Csv.write("date,deviceId,count\n");
		
		//add each day and count
		
		for (String daydev: mergedCount.keySet()){
			 
				Question3Csv.write(daydev.substring(0,10)+ "," + daydev.substring(10) + "," + mergedCount.get(daydev) + "\n");
				
			}
			
		
		Question3Csv.write("\"\n");
		
		// close the file!
		Question3Csv.close();
	}
		
	private static void MergeCounts(Hashtable<String,Integer> runnerCount, Hashtable<String,Integer> mergedCount)
	{
		for(String daydev : runnerCount.keySet())
		{
			int count = runnerCount.get(daydev);
			mergedCount.put(daydev, count);
			
			}
			
}
}