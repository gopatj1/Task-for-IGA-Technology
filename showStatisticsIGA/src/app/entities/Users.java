package app.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Users {
	
	public static List<String> users = new ArrayList<>();
	private static BufferedReader reader; //for line by line reading
    public static File file = new File("C:/Users/Envy/Рабочий стол/IGA Technologies/users.txt"); //find file users.txt
	
	//collect users from user.txt
	public static void addUsers() {
		try {
	        FileReader fr = new FileReader(file); //create object FileReader for object File
	        reader = new BufferedReader(fr);
	        String line = reader.readLine(); //read first line
			
	        while (line != null) {
	        	users.add(line); //add new userName
	            line = reader.readLine(); //read other lines
	        }
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	//get user from array
	public static String getUser(int index) {
		return users.get(index);
	}
	
	//get array size
	public static int getSize() {
		return users.size();
	}
	
	//clear user array
	public static void clear() {
		users.clear();
	}
}