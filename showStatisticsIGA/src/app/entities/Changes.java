package app.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections; 
import java.util.Date;
import java.util.List;
 
public class Changes {
	
	static SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yyyy HH:mm:ss a"); //date format for parsing
	static Date parsingDate; //variable for date parsing

	static List<List<Float>> countOfChanges = new ArrayList<>(); //array of count of changes, using it for graphic
	static List<List<Long>> persTime = new ArrayList<>(); //personal time
	static List<Long> z = new ArrayList<>(); //array for paste 0 elements to array persTime
	static List<Long> commonTime = new ArrayList<>(); //array of common time
	public static List<Float> commonChanges = new ArrayList<>(); //common changes
	public static List<String> checkedUsers = new ArrayList<>(); //array with user names
	private static BufferedReader reader;
	public static File file = new File("C:/Users/Envy/Рабочий стол/IGA Technologies/history.txt");
	
	public static long stepX; //step of X-axis
	public static long leftDate; //left date
	public static long rightDate; //right date
	public static String changeType; //type of change (connect or create)

    public static void findCountChanges() {
        
    	//add 0 elements to arrays for correct work
    	commonChanges.add((float) 0);
    	countOfChanges.add(commonChanges);
    	z.add((long) 0);
    	for (int i = 0; i < checkedUsers.size(); i++)
    		persTime.add(z);
        
        try {
            FileReader fr = new FileReader(file); //create object FileReader for object File
            reader = new BufferedReader(fr);
            String line = reader.readLine(); //read first line
    		
            while (line != null) {
            	if (line.contains("history = " + changeType)) { //find type of change (connect or create)
            		for (String userName: checkedUsers) {
		        		if (line.contains("user: " + userName)) { //find checked users which do changes
		        			String[] linesT = line.split("  "); //find string "time: ..:..:.." as linesT[3]
		        			String[] lines = linesT[3].split(" ", 2); //delete word "time:", dateTime as lines[1]
		        			
		        			//try to parse our dateTime
		        			try {
	        		           parsingDate = ft.parse(lines[1]);
	        		        } catch (ParseException e) {
	        		           System.out.println("Can not to parse by " + ft); 
	        		        }
		        	        
		        			//check is time(milliseconds) between leftDate and rigthDate from calendar
	        				if ((parsingDate.getTime() > leftDate) && (parsingDate.getTime() < rightDate)) {	        					
	        					commonTime.add(parsingDate.getTime()); //array for collect common time of changes
	        					
	        					List<Long> newPersTime = new ArrayList<>(); //new array for collect personal time of changes
	        					newPersTime.add(parsingDate.getTime());
	        					if (persTime.get(checkedUsers.indexOf(userName)).get(0) == 0) { //check is first element of default array == 0
	        						persTime.set(checkedUsers.indexOf(userName), newPersTime); //replace defaultArray element to newPersTime
	        					} else {
	        						newPersTime = persTime.get(checkedUsers.indexOf(userName)); //get existing array element
	        						newPersTime.add(parsingDate.getTime()); //add new element to array
			        	    		persTime.set(checkedUsers.indexOf(userName), newPersTime);  //replace newPersTime element to new newPersTime
	        					}
		        			}
		        		}
            		}
	            }
                line = reader.readLine(); //read other lines
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //find count of changes in each x-axis step
		for (int i = 0; i < persTime.size(); i++) {
			List<Float> personalChanges = new ArrayList<>(); //array for collect personal changes
			long stepDate = leftDate; //date + stepX
	    	personalChanges.add((float) 0); //no changes
			Collections.sort(persTime.get(i)); //sort personal array of time
	    	
	    	for (int j = 0, k = 0; j < persTime.get(i).size(); j++) { //all elements of i-array of persTime 
	        	if ((stepDate + stepX >= persTime.get(i).get(j))) { //if stepX include this time of change
	        		if (persTime.get(i).get(j) != 0) { //haven't personal time (will not have personal changes) 
		        		personalChanges.set(k, personalChanges.get(k) + 1); //increment count of personal changes at this stepX
		        		commonChanges.set(k, commonChanges.get(k) + 1); //increment count of common changes at this stepX
	        		}
	        	} else {
	        		stepDate += stepX; //increase leftDate to stepX number
	        		personalChanges.add((float) 0); //new element 0 (don't change still)
	        		commonChanges.add((float) 0); //new element 0 (don't change still)
	        		k++; //work with this new element of arrays with count of changes
	        		j--; //check element of i-array of indTime again
	        	}
			}
	        //if time of changes is over and leftDate < rightDate, so create 0 elements
	        while (stepDate < rightDate) {
	        	stepDate += stepX;
	    		personalChanges.add((float) 0);
        		commonChanges.add((float) 0); //new element 0 (don't change still)
	        }
    		countOfChanges.add(i, personalChanges); //add new personal array to array of count changes
    		while (commonChanges.size() > personalChanges.size()) //delete extra 0 elements from common array
    			commonChanges.remove(commonChanges.size() - 1);
		}		
		countOfChanges.set(countOfChanges.size() - 1, commonChanges); //replace array [0] to array commonChanges
        
        for (int i = 0; i < checkedUsers.size(); i++) {
			System.out.println(countOfChanges.get(i) + " - " + checkedUsers.get(i));
        }
        System.out.println(countOfChanges.get(countOfChanges.size() - 1) + " - TOTAL");
		System.out.println("Change type: " + changeType);
		System.out.println("Left date: " + leftDate + " ms");
		System.out.println("Right date: " + rightDate + " ms");
		System.out.println("StepX: " + stepX + " ms");
    }
	
	//get personal array with count of changes
	public static List<Float> getChanges(int index) {
		return countOfChanges.get(index);
	}
	
	//get array size
	public static int getSize() {
		return countOfChanges.size();
	}
	
	//clear all arrays
	public static void clear() {
		countOfChanges.clear();
		commonChanges.clear();
		commonTime.clear();
		persTime.clear();
		z.clear();
	}
}
