// Shivangi Goel
// ITP 368, Fall 2017
// Final Project
// goelshiv@usc.edu

package view;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import messages.Messages;
import model.EventCountdown;
import model.User;

public class MainWindow extends Application {
	
	private Controller controller;

	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// create controller object which also sets up scenes
		controller = new Controller(primaryStage, ReadUsers());
		//controller.setUserMap(ReadUsers());
		
		
		// set window title
		primaryStage.setTitle(Messages.getString("MainWindow.windowTitle")); //$NON-NLS-1$
		// show window
		primaryStage.show();
	}
	
	public Map<String, User> ReadUsers() throws URISyntaxException {
		FileInputStream fis;
		Map<String, User> userMap = new HashMap<String, User>();
		try {
			fis = new FileInputStream("src/users.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			while (true) {
				try{
					//read user object from file
					User user = (User) ois.readObject();
					ArrayList<EventCountdown> eventlist = user.getEvents();
					//set the transient members
					for(int i = 0; i < eventlist.size(); i++) {
						eventlist.get(i).getCountDownTimer().setLeftProperties();
						eventlist.get(i).getCountDownTimer().setTimeline();
						eventlist.get(i).setupAudio();
					}
					System.out.println(user);
					//add to the user map that will be passed to controller
					userMap.put(user.getUsername(), user);
			        
			    } 
				catch (EOFException e){
					ois.close();
					return userMap;
			    } 
				catch (ClassNotFoundException e) {
					
					e.printStackTrace();
				}
				//ois.close();
				
			}
		} catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
		} catch (IOException e1) {
			
			return userMap;
		}
		
		
		return userMap;
		
	}
	
	@Override
	public void stop(){
	    System.out.println("Stage is closing");
	    
	    try {
	    		// write object to file
	    		
			FileOutputStream fos = new FileOutputStream("src/users.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			//get the map of users from the controller
			Iterator<Entry<String, User>> it = controller.getUserMap().entrySet().iterator();
	        while (it.hasNext()) {
	            Map.Entry<String, User> pair = (Map.Entry<String, User>)it.next();
	            System.out.println(pair.getKey() + " = " + pair.getValue());
	            oos.writeObject(pair.getValue());
	            it.remove(); // avoids a ConcurrentModificationException
	        }
			oos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}


}