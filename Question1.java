package homework6;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

public class Question1 {

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
		
		//set bounds
		int bound1 = (int) accelPaths.size()/4;
		int bound2 = (int) accelPaths.size()/2;
		int bound3 = (int) accelPaths.size()*(3/4);
		int bound4 = (int) accelPaths.size();
		
		// work with the first set of paths
		Question1Runner runner1 = new Question1Runner(accelPaths, 0, bound1);
		Thread thread1 = new Thread(runner1);
		thread1.start();
		
		// split up the rest of the paths
		Question1Runner runner2 = new Question1Runner(accelPaths, bound1 + 1, bound2);
		Thread thread2 = new Thread(runner2);
		thread2.start();
		
		Question1Runner runner3 = new Question1Runner(accelPaths, bound2 + 1, bound3);
		Thread thread3 = new Thread(runner3);
		thread3.start();
		
		Question1Runner runner4 = new Question1Runner(accelPaths, bound3 + 1, bound4 - 1);
		Thread thread4 = new Thread(runner4);
		thread4.start();	
			
		// wait for all count runners to finish
		thread1.join();
		thread2.join();
		thread3.join();
		thread4.join();
		
		// merge results from the individual count runners into a single merged hashtable
		
		Hashtable<LocalDate, Integer> mergedDateCount = new Hashtable<LocalDate, Integer>();
		MergeCounts(runner1.getDateCount(), mergedDateCount);
		MergeCounts(runner2.getDateCount(), mergedDateCount);
		MergeCounts(runner3.getDateCount(), mergedDateCount);
		MergeCounts(runner4.getDateCount(), mergedDateCount);
	
		
	
		//Write to CSV	
			
		PrintWriter question1Csv = new PrintWriter("C:\\Users\\Student\\Documents\\Sem 4\\Sys 2202\\Question1.csv");
		
		// write the header row
		question1Csv.write("date,count\n");
		
		//add each day and count
		
		for (LocalDate day: mergedDateCount.keySet()){
			question1Csv.write(day.toString()+ "," + mergedDateCount.get(day) +"\n");
		}
		
		question1Csv.write("\"\n");
		
		// close the file!
		question1Csv.close();
	}
		
	private static void MergeCounts(Hashtable<LocalDate, Integer> runnerDateCount, Hashtable<LocalDate, Integer> mergedDateCount)
	{
		for(LocalDate day : runnerDateCount.keySet())
		{
			int count = runnerDateCount.get(day);
			mergedDateCount.put(day, count);
		}
}
}