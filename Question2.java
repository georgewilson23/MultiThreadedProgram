package homework6;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Question2 {

	int bound1;
	int bound2;
	int bound3;
	int bound4;
	public static void main(String[] args) throws Exception {
		
		//get file paths
		ArrayList<String> paths = new ArrayList<String>();
		
		Files.walk(Paths.get("C:\\Users\\Student\\Documents\\Sem 4\\Sys 2202\\sp2017sys2202_mobile_data\\PointOfInterestProximityDatum")).filter(Files::isRegularFile).map(path -> path.toString()).forEach(paths::add);
		Files.walk(Paths.get("C:\\Users\\Student\\Documents\\Sem 4\\Sys 2202\\sp2017sys2202_mobile_data\\AccelerometerDatum")).filter(Files::isRegularFile).map(path -> path.toString()).forEach(paths::add);
		Files.walk(Paths.get("C:\\Users\\Student\\Documents\\Sem 4\\Sys 2202\\sp2017sys2202_mobile_data\\BatteryDatum")).filter(Files::isRegularFile).map(path -> path.toString()).forEach(paths::add);
		Files.walk(Paths.get("C:\\Users\\Student\\Documents\\Sem 4\\Sys 2202\\sp2017sys2202_mobile_data\\LightDatum")).filter(Files::isRegularFile).map(path -> path.toString()).forEach(paths::add);
		Files.walk(Paths.get("C:\\Users\\Student\\Documents\\Sem 4\\Sys 2202\\sp2017sys2202_mobile_data\\LocationDatum")).filter(Files::isRegularFile).map(path -> path.toString()).forEach(paths::add);
		Files.walk(Paths.get("C:\\Users\\Student\\Documents\\Sem 4\\Sys 2202\\sp2017sys2202_mobile_data\\SoundDatum")).filter(Files::isRegularFile).map(path -> path.toString()).forEach(paths::add);
		Files.walk(Paths.get("C:\\Users\\Student\\Documents\\Sem 4\\Sys 2202\\sp2017sys2202_mobile_data\\AltitudeDatum")).filter(Files::isRegularFile).map(path -> path.toString()).forEach(paths::add);
		
		
		int bound1 = (int) paths.size()/4;
		int bound2 = (int) paths.size()/2;
		int bound3 = (int) paths.size()*(3/4);
		int bound4 = (int) paths.size();
		
		// work with the first set of paths
		Question2Runner runner1 = new Question2Runner(paths, 0, bound1);
		Thread thread1 = new Thread(runner1);
		thread1.start();
		
		// split up the rest of the paths
		Question2Runner runner2 = new Question2Runner(paths, bound1 + 1, bound2);
		Thread thread2 = new Thread(runner2);
		thread2.start();
		
		Question2Runner runner3 = new Question2Runner(paths, bound2 + 1, bound3);
		Thread thread3 = new Thread(runner3);
		thread3.start();
		
		Question2Runner runner4 = new Question2Runner(paths, bound3 + 1, bound4 - 1);
		Thread thread4 = new Thread(runner4);
		thread4.start();	
			
		// wait for all count runners to finish
		thread1.join();
		thread2.join();
		thread3.join();
		thread4.join();
		
		// merge results from the individual count runners into a single merged hashtable
		
		Hashtable<String, Integer> mergedDeviceCount = new Hashtable<String, Integer>();
		MergeCounts(runner1.getDevIdCount(), mergedDeviceCount);
		MergeCounts(runner2.getDevIdCount(), mergedDeviceCount);
		MergeCounts(runner3.getDevIdCount(), mergedDeviceCount);
		MergeCounts(runner4.getDevIdCount(), mergedDeviceCount);
	
		
	
		//Write to CSV	
			
		PrintWriter Question2Csv = new PrintWriter("C:\\Users\\Student\\Documents\\Sem 4\\Sys 2202\\Question2.csv");
		
		// write the header row
		Question2Csv.write("DeviceId,count\n");
		
		//add each day and count
		
		for (String s: mergedDeviceCount.keySet()){
			Question2Csv.write(s + "," + mergedDeviceCount.get(s) +"\n");
		}
		
		Question2Csv.write("\"\n");
		
		// close the file!
		Question2Csv.close();
	}
		
	private static void MergeCounts(Hashtable<String, Integer> runnerDevIdCount, Hashtable<String, Integer> mergedDeviceCount)
	{
		for(String s : runnerDevIdCount.keySet())
		{
			int count = runnerDevIdCount.get(s);
			mergedDeviceCount.put(s, count);
		}
}
}