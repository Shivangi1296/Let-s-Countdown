// Shivangi Goel
// ITP 368, Fall 2017
// Final Project
// goelshiv@usc.edu

package controller;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.User;
import view.ViewEventsScene;
import view.CreateEventsScene;
import view.StartScene;

public class Controller {
	
	private final Image CLIPART_BIRTHDAY  = new Image("/cliparts/birthday-cake.png");
    private final Image CLIPART_THANKSGIVING  = new Image("/cliparts/thanksgiving-turkey.png");
    private final Image CLIPART_GRADUATION  = new Image("/cliparts/graduation-owl.png");
    private final Image CLIPART_WEDDING  = new Image("/cliparts/wedding.png");
    private final Image CLIPART_FOOTBALL = new Image("/cliparts/football-png.png");
    private final Image CLIPART_CHRISTMAS = new Image("/cliparts/santa-claus.png");
    private final Image CLIPART_HEART = new Image("/cliparts/hearts.png");
    private final Image CLIPART_PLANE = new Image("/cliparts/aircraft.png");
    private final Image CLIPART_EXAM = new Image("/cliparts/exam.png");
    private final Image CLIPART_CELEBRATION = new Image("/cliparts/celebration.png");
    private final Image CLIPART_VACATION = new Image("/cliparts/vacation.png");
    
    public final int USER_FOUND = 2;
    public final int INCORRECT_PASSWORD = 1;
    public final int NO_USER_FOUND = 0;

    private Map<String, Image> mapOfCliparts = new HashMap<String, Image>();
    private Map<String, String> mapOfAudios = new HashMap<String, String>();

	// reference to window (used for switching between scenes)
	private Stage primaryStage;
	// reference to start scene
	private StartScene startScene;
	// reference to game scene
	private CreateEventsScene createEventsScene;
	// reference to end scene
	private ViewEventsScene viewEventsScene;
	
	private Map<String, User> userMap;
	private User currUser = new User("", "");
	private String typeBg;
		
	public Controller(Stage primaryStage, Map<String, User> userMap) {
		typeBg = "Plain";
		this.primaryStage = primaryStage;
		createMapCliparts();
		createMapAudios();
		this.userMap = userMap;
		startScene = new StartScene(this);
		createEventsScene = new CreateEventsScene(this);
		viewEventsScene = new ViewEventsScene(this);
		loadStartScene();
	}

	private void createMapAudios() {
		mapOfAudios.put("Applause Sound", "/audios/applause3.mp3");
		mapOfAudios.put("Cheering Sound", "/audios/cheer_8k.mp3");
		mapOfAudios.put("Beep Sound", "/audios/beep-04.mp3");
		mapOfAudios.put("Ocean Sound", "/audios/wavebig.mp3");
		mapOfAudios.put("Sleigh Sound", "/audios/SleighBells1.mp3");
	}

	private void createMapCliparts() {
		mapOfCliparts.put("Birthday", CLIPART_BIRTHDAY);
		mapOfCliparts.put("Thanksgiving", CLIPART_THANKSGIVING);
		mapOfCliparts.put("Graduation", CLIPART_GRADUATION);
		mapOfCliparts.put("Wedding", CLIPART_WEDDING);
		mapOfCliparts.put("Christmas", CLIPART_CHRISTMAS);
		mapOfCliparts.put("Heart", CLIPART_HEART);
		mapOfCliparts.put("Travel", CLIPART_PLANE);
		mapOfCliparts.put("Exams", CLIPART_EXAM);
		mapOfCliparts.put("Celebration",CLIPART_CELEBRATION );
		mapOfCliparts.put("Vacation", CLIPART_VACATION );
		mapOfCliparts.put("Football", CLIPART_FOOTBALL );
	}

	
	public Map<String, Image> getMapOfCliparts() {
		return mapOfCliparts;
	}
	
	public Map<String, String> getMapOfAudios() {
		return mapOfAudios;
	}

	// get reference to start scene
	public void setStartScene(StartScene startScene) {
		this.startScene = startScene;
	}

	// get reference to createEventsScene scene
	public void setCreateEventsScene(CreateEventsScene createEventsScene) {
		this.createEventsScene = createEventsScene;
	}

	// get reference to viewEventsScene scene
	public void setViewEventsScene(ViewEventsScene viewEventsScene) {
		this.viewEventsScene = viewEventsScene;
	}
	
	// get reference to viewEventsScene scene
	public void setCurrUser(User u) {
		this.currUser = u;
	}
	
	public User getCurrUser() {
		return currUser;
	}

	// show start scene in window
	public void loadStartScene() {
		startScene.resetComponents();
		primaryStage.setScene(startScene);
	}
	
	// show createEvents scene in window
	public void loadCreateEventsScene() {
		createEventsScene.resetComponents();
		primaryStage.setScene(createEventsScene);
	}
	
	// show viewEvents scene in window
	public void loadViewEventsScene() {
		//viewEventsScene.printUser();
		System.out.println(currUser);
		viewEventsScene.printCurrEvents();
		primaryStage.setScene(viewEventsScene);
	}
	
	
	public Map<String, User> getUserMap() {
		return userMap;
	}
	
	public boolean addToUserMap(User u) {
		if(!userMap.containsKey(u.getUsername())) {
			userMap.put(u.getUsername(), u);
			return true;
		}
		return false;
	}
	
	public int isUserValid(User u) {
		if(userMap.containsKey(u.getUsername())) {
			if(userMap.get(u.getUsername()).getPassword().equals(u.getPassword())) {
				return USER_FOUND;
			}else {
				return INCORRECT_PASSWORD;
			}
		}else {
			return NO_USER_FOUND;
		}
	}

	public void setUserMap(Map<String, User> readUsers) {
		this.userMap = readUsers;
		
	}

	public void changeBackground(String t1) {
		startScene.changeBackground(t1);
		viewEventsScene.changeBackground(t1);
		createEventsScene.changeBackground(t1);
		
		
	}
}
