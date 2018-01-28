// Shivangi Goel
// ITP 368, Fall 2017
// Final Project
// goelshiv@usc.edu

package view;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import messages.Messages;
import model.EventCountdown;
import model.TimerType;

public class ViewEventsScene extends Scene {

	// scene height
	private static int sceneHeight = 600;
	// scene width
	private static int sceneWidth = 700;
	
	// reference to controller
	private Controller controller;
	// main layout pane
	private static BorderPane viewPane = new BorderPane();
	// inner layout pane
	private static BorderPane centerPane = new BorderPane();
	//scroll pane for center 
	private static ScrollPane scrollPane = new ScrollPane();
	//pane to hold buttons and title
	private static BorderPane titlePane = new BorderPane();
	//title label for this window
	Label viewEventsLabel;
	//box that will hold all the countdown events and title
	VBox eventsBox = new VBox(50);
	//button for creating new events
	Button createNewEventsButton;
	//button for logging out
	Button logoutButton;
		
	public ViewEventsScene(Controller controller) {
		super(viewPane, sceneWidth, sceneHeight);
		this.controller = controller;
		viewPane.setStyle("-fx-background-color: ANTIQUEWHITE;" );  //$NON-NLS-1$
		createComoponents();
	}
	
	public void changeBackground(String typeBG){
		if(typeBG.equals("Plain")) { //$NON-NLS-1$
			viewPane.setStyle("-fx-background-color: ANTIQUEWHITE;" ); //$NON-NLS-1$
	    }else {
	
	    		viewPane.setStyle("-fx-background-image: url("+"/images/floral.jpg"+");"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	        
	    }
	}

	private void createComoponents() {
		//set up header image
		setupHeader();
		setupTitleBar();
		setupScrollPane();
	}
	
	public void printCurrEvents() {
		if(controller.getCurrUser() != null) {
			ArrayList<EventCountdown> list = controller.getCurrUser().getEvents();
			eventsBox.getChildren().clear();
			for(int i = 0; i < list.size(); i++) {
				eventsBox.getChildren().add(createEventWidget(list.get(i)));
				if(!list.get(i).getCountDownTimer().isOver()) {
					list.get(i).getCountDownTimer().getTimeline().playFromStart();
				}
			}
		}
	}
	
	public void pauseTimers() {
		if(controller.getCurrUser() != null) {
			ArrayList<EventCountdown> list = controller.getCurrUser().getEvents();
			for(int i = 0; i < list.size(); i++) {
				list.get(i).getCountDownTimer().getTimeline().stop();
			}
		}
	}

	private void setupHeader() {
		//set up header image 1
		Image headerImage = new Image("/images/countdown-hands.jpg"); //$NON-NLS-1$
		ImageView header1 = new ImageView(headerImage);
		header1.setFitWidth(350);
		header1.setFitHeight(130);
		header1.setSmooth(true); //use a better quality filtering algorithm (false is less quality, but faster)
		header1.setCache(true); //cache to improve performance
		
		//set up header image 2
		ImageView header2 = new ImageView(headerImage);
		header2.setFitWidth(350);
		header2.setFitHeight(130);
		header2.setSmooth(true); //use a better quality filtering algorithm (false is less quality, but faster)
		header2.setCache(true); //cache to improve performance
		
		//set up header
		HBox headerBox = new HBox();
		headerBox.getChildren().addAll(header1, header2);
		viewPane.setTop(headerBox);
	}
	
	private void setupTitleBar() {
		//create title label for this window
		viewEventsLabel = new Label(Messages.getString("ViewEventsScene.viewEventsTitle")); //$NON-NLS-1$
		viewEventsLabel.setAccessibleText(Messages.getString("ViewEventsScene.viewEventsTitleACC")); //$NON-NLS-1$
		viewEventsLabel.setFont(Font.font ("Verdana",FontWeight.BOLD, 30)); //$NON-NLS-1$
		viewEventsLabel.setWrapText(true);
		viewEventsLabel.setTextAlignment(TextAlignment.CENTER);
		viewEventsLabel.setTextFill(Color.DARKMAGENTA);
		viewEventsLabel.setPadding(new Insets(0,20,0,20));
		
		//create button for creating new events
		createNewEventsButton = new Button(Messages.getString("ViewEventsScene.createNewButton")); //$NON-NLS-1$
		createNewEventsButton.setAccessibleHelp(Messages.getString("ViewEventsScene.createNewButtonACC")); //$NON-NLS-1$
		createNewEventsButton.setTextAlignment(TextAlignment.CENTER);
		createNewEventsButton.setPrefHeight(60);
		createNewEventsButton.setStyle("-fx-font: 15 Verdana; -fx-base : DARKMAGENTA"); //$NON-NLS-1$
		createNewEventsButton.setOnAction(event->{
			pauseTimers();
			controller.loadCreateEventsScene();
		});
		
		//create button for logging out
		logoutButton = new Button(Messages.getString("ViewEventsScene.logoutButton")); //$NON-NLS-1$
		logoutButton.setAccessibleHelp(Messages.getString("ViewEventsScene.logoutButtonACC")); //$NON-NLS-1$
		logoutButton.setTextAlignment(TextAlignment.CENTER);
		logoutButton.setPrefHeight(60);
		logoutButton.setStyle("-fx-font: 15 Verdana; -fx-base : DARKMAGENTA"); //$NON-NLS-1$
		logoutButton.setOnAction(event->{
			pauseTimers();
			controller.setCurrUser(null);	
			controller.loadStartScene();
		});
		
		//pane to hold buttons and title
		titlePane = new BorderPane();
		titlePane.setLeft(createNewEventsButton);
		titlePane.setRight(logoutButton);
		titlePane.setCenter(viewEventsLabel);
		//titlePane.setPadding(new Insets(0,10,10,10));
		
	}
	
	private void setupScrollPane() {
		//set scroll pane to the center of view pane
		//this scroll pane will be used to scroll through all the count
		//down events created by the user
		viewPane.setCenter(scrollPane);
		viewPane.setAlignment(scrollPane, Pos.CENTER);
		
		//set scroll pane's scrolling policy
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
		//set scroll pane's color
		scrollPane.setStyle("-fx-background-color: ANTIQUEWHITE;" );  //$NON-NLS-1$
		
		eventsBox.setPadding(new Insets(40,10,20,10));
		eventsBox.setStyle("-fx-background-color: ANTIQUEWHITE;" ); //$NON-NLS-1$
		eventsBox.setPrefHeight(470);
		eventsBox.setPrefWidth(682);
		eventsBox.setAlignment(Pos.CENTER);
		
		centerPane.setPadding(new Insets(20,10,20,10));
		centerPane.setStyle("-fx-background-color: ANTIQUEWHITE;" ); //$NON-NLS-1$
		centerPane.setPrefHeight(470);
		centerPane.setPrefWidth(682);
		
		centerPane.setTop(titlePane);
		centerPane.setCenter(eventsBox);
		
		
		//add the eventsbox to scrollpane to make
		//the events scrollable
		scrollPane.setContent(centerPane);
	}

	public void printUser() {
		System.out.println(controller.getCurrUser().getUsername());
		System.out.println(controller.getCurrUser().getPassword());
	}
	
	private BorderPane createEventWidget(EventCountdown event) {
		BorderPane widgetPane = new BorderPane();
		widgetPane.setPrefHeight(300);
		widgetPane.setMaxWidth(500);
		widgetPane.setPadding(new Insets(0,10,0,0));
		
		widgetPane.setStyle("-fx-background-color: \""+event.getBackgroundColor()+"\""); //$NON-NLS-1$ //$NON-NLS-2$
		
		Label widgetTitle = new Label(event.getEventname());
		widgetTitle.setAccessibleText(Messages.getString("ViewEventsScene.eventTitleACC")+event.getEventname());  //$NON-NLS-1$
		widgetTitle.setFont(Font.font ("Verdana",FontWeight.BOLD, 25)); //$NON-NLS-1$
		widgetTitle.setWrapText(true);
		widgetTitle.setTextAlignment(TextAlignment.CENTER);
		widgetTitle.setTextFill(Color.web(event.getTextColor()));
		widgetTitle.setPadding(new Insets(10,10,10,10));
		
		widgetPane.setTop(widgetTitle);
		widgetPane.setAlignment(widgetTitle, Pos.CENTER);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM d, yyyy  hh:mm a", Locale.getDefault()); //$NON-NLS-1$
		Label widgetDate = new Label(event.getEventdate().format(dtf));
		widgetDate.setFont(Font.font ("Verdana",FontWeight.BOLD, 15)); //$NON-NLS-1$
		widgetDate.setWrapText(true);
		widgetDate.setTextAlignment(TextAlignment.CENTER);
		widgetDate.setTextFill(Color.web(event.getTextColor()));
		//widgetDate.setPadding(new Insets(10,10,10,10));
		
		Label widgetMessage = new Label(Messages.getString("ViewEventsScene.timeUntilLabel")); //$NON-NLS-1$
		widgetMessage.setFont(Font.font ("Verdana", 12)); //$NON-NLS-1$
		widgetMessage.setWrapText(true);
		widgetMessage.setTextAlignment(TextAlignment.CENTER);
		widgetMessage.setTextFill(Color.web(event.getTextColor()));
		//widgetDate.setPadding(new Insets(10,10,10,10));
		
		HBox bottomPane = new HBox(5);
		bottomPane.setPadding(new Insets(10,10,10,10));
		bottomPane.setStyle("-fx-border-style: solid none none none; -fx-border-width: 1; -fx-border-color: ANTIQUEWHITE;"); //$NON-NLS-1$
		bottomPane.getChildren().addAll(widgetMessage, widgetDate);
		bottomPane.setAlignment(Pos.CENTER);
		
		widgetPane.setBottom(bottomPane);
		widgetPane.setAlignment(bottomPane, Pos.CENTER);
		
		ImageView widgetImage = new ImageView(controller.getMapOfCliparts().get(event.getImageString()));
		widgetImage.setFitWidth(200);
		widgetImage.setFitHeight(200);
		widgetImage.setSmooth(true); //use a better quality filtering algorithm (false is less quality, but faster)
		widgetImage.setCache(true); //cache to improve performance
		
		
		widgetPane.setRight(widgetImage);
		widgetPane.setAlignment(widgetImage, Pos.CENTER);
		
		widgetPane.setCenter(createTimeBox( event));
		
		return widgetPane;
	}

	
	private Node createTimeBox(EventCountdown event) {
		if(event.getTimerType() == TimerType.Weeks) {
			return createTimeBoxType1(event);
		}else if(event.getTimerType() == TimerType.DaysAndHours) {
			return createTimeBoxType2(event);
		}else {
			return createTimeBoxType3(event);
		}
	}
	
	private Node createTimeBoxType1(EventCountdown event) {
		VBox timeDisplayBox = new VBox(20);
		
		HBox daysAndWeeksBox = new HBox(20);
		HBox hoursAndMinutesBox = new HBox(15);
		
		VBox weeksBox = new VBox(5);
		VBox daysBox = new VBox(5);
		VBox hoursBox = new VBox(5);
		VBox minutesBox = new VBox(5);
		VBox secondsBox = new VBox(5);
		
		Label weeksLabel = new Label(Messages.getString("ViewEventsScene.weeksLabel")); //$NON-NLS-1$
		weeksLabel.setAccessibleText(Messages.getString("ViewEventsScene.weeksLabelACC")); //$NON-NLS-1$
		weeksLabel.setFont(Font.font ("Verdana", 12)); //$NON-NLS-1$
		weeksLabel.setTextFill(Color.web(event.getTextColor()));
		
		Label numWeeks = new Label("00"); //$NON-NLS-1$
		numWeeks.setFont(Font.font ("Verdana", 40)); //$NON-NLS-1$
		numWeeks.setTextFill(Color.web(event.getTextColor()));
		
		Label daysLabel = new Label(Messages.getString("ViewEventsScene.daysLabel")); //$NON-NLS-1$
		daysLabel.setAccessibleText(Messages.getString("ViewEventsScene.daysLabelACC")); //$NON-NLS-1$
		daysLabel.setFont(Font.font ("Verdana", 12)); //$NON-NLS-1$
		daysLabel.setTextFill(Color.web(event.getTextColor()));
		
		Label numDays = new Label("01"); //$NON-NLS-1$
		numDays.setFont(Font.font ("Verdana", 40)); //$NON-NLS-1$
		numDays.setTextFill(Color.web(event.getTextColor()));
		
		Label hoursLabel = new Label(Messages.getString("ViewEventsScene.hoursLabel")); //$NON-NLS-1$
		hoursLabel.setAccessibleText(Messages.getString("ViewEventsScene.hoursLabelACC")); //$NON-NLS-1$
		hoursLabel.setFont(Font.font ("Verdana", 12)); //$NON-NLS-1$
		hoursLabel.setTextFill(Color.web(event.getTextColor()));

		Label numHours = new Label("02"); //$NON-NLS-1$
		numHours.setFont(Font.font ("Verdana", 40)); //$NON-NLS-1$
		numHours.setTextFill(Color.web(event.getTextColor()));
		
		Label minutesLabel = new Label(Messages.getString("ViewEventsScene.minutesLabel")); //$NON-NLS-1$
		minutesLabel.setAccessibleText(Messages.getString("ViewEventsScene.minutesLabelACC")); //$NON-NLS-1$
		minutesLabel.setFont(Font.font ("Verdana", 12)); //$NON-NLS-1$
		minutesLabel.setTextFill(Color.web(event.getTextColor()));
		
		Label numMinutes = new Label("03"); //$NON-NLS-1$
		numMinutes.setFont(Font.font ("Verdana", 40)); //$NON-NLS-1$
		numMinutes.setTextFill(Color.web(event.getTextColor()));
		
		Label secondsLabel = new Label(Messages.getString("ViewEventsScene.secondsLabel")); //$NON-NLS-1$
		secondsLabel.setAccessibleText(Messages.getString("ViewEventsScene.secondsLabelACC")); //$NON-NLS-1$
		secondsLabel.setFont(Font.font ("Verdana", 12)); //$NON-NLS-1$
		secondsLabel.setTextFill(Color.web(event.getTextColor()));
		
		Label numSeconds = new Label("04"); //$NON-NLS-1$
		numSeconds.setFont(Font.font ("Verdana", 40)); //$NON-NLS-1$
		numSeconds.setTextFill(Color.web(event.getTextColor()));
		
		numWeeks.textProperty().bind(event.getCountDownTimer().getWeeksLeft());
		numDays.textProperty().bind(event.getCountDownTimer().getDaysLeft());
		numHours.textProperty().bind(event.getCountDownTimer().getHoursLeft());
		numMinutes.textProperty().bind(event.getCountDownTimer().getMinutesLeft());
		numSeconds.textProperty().bind(event.getCountDownTimer().getSecondsLeft());
		
		weeksBox.getChildren().addAll(numWeeks, weeksLabel);
		weeksBox.setAlignment(Pos.CENTER);
		daysBox.getChildren().addAll(numDays, daysLabel);
		daysBox.setAlignment(Pos.CENTER);
		hoursBox.getChildren().addAll(numHours, hoursLabel);
		hoursBox.setAlignment(Pos.CENTER);
		minutesBox.getChildren().addAll(numMinutes,minutesLabel);
		minutesBox.setAlignment(Pos.CENTER);
		secondsBox.getChildren().addAll(numSeconds, secondsLabel);
		secondsBox.setAlignment(Pos.CENTER);
		
		daysAndWeeksBox.getChildren().addAll(weeksBox, daysBox);
		daysAndWeeksBox.setAlignment(Pos.CENTER);
		hoursAndMinutesBox.getChildren().addAll(hoursBox, minutesBox, secondsBox);
		hoursAndMinutesBox.setAlignment(Pos.CENTER);
		
		timeDisplayBox.getChildren().addAll(daysAndWeeksBox, hoursAndMinutesBox);
		timeDisplayBox.setAlignment(Pos.CENTER);
		return timeDisplayBox;
	}
	
	

	private Node createTimeBoxType2(EventCountdown event) {
		VBox timeDisplayBox = new VBox(20);
		
		HBox daysAndWeeksBox = new HBox(20);
		HBox hoursAndMinutesBox = new HBox(15);
		
		VBox daysBox = new VBox(5);
		VBox hoursBox = new VBox(5);
		VBox minutesBox = new VBox(5);
		VBox secondsBox = new VBox(5);
		
		Label daysLabel = new Label(Messages.getString("ViewEventsScene.daysLabel")); //$NON-NLS-1$
		daysLabel.setAccessibleText(Messages.getString("ViewEventsScene.daysLabelACC")); //$NON-NLS-1$
		daysLabel.setFont(Font.font ("Verdana", 12)); //$NON-NLS-1$
		daysLabel.setTextFill(Color.web(event.getTextColor()));
		
		Label numDays = new Label("01"); //$NON-NLS-1$
		numDays.setFont(Font.font ("Verdana", 40)); //$NON-NLS-1$
		numDays.setTextFill(Color.web(event.getTextColor()));
		
		Label hoursLabel = new Label(Messages.getString("ViewEventsScene.hoursLabel")); //$NON-NLS-1$
		hoursLabel.setAccessibleText(Messages.getString("ViewEventsScene.hoursLabelACC")); //$NON-NLS-1$
		hoursLabel.setFont(Font.font ("Verdana", 12)); //$NON-NLS-1$
		hoursLabel.setTextFill(Color.web(event.getTextColor()));

		Label numHours = new Label("02"); //$NON-NLS-1$
		numHours.setFont(Font.font ("Verdana", 40)); //$NON-NLS-1$
		numHours.setTextFill(Color.web(event.getTextColor()));
		
		Label minutesLabel = new Label(Messages.getString("ViewEventsScene.minutesLabel")); //$NON-NLS-1$
		minutesLabel.setAccessibleText(Messages.getString("ViewEventsScene.minutesLabelACC")); //$NON-NLS-1$
		minutesLabel.setFont(Font.font ("Verdana", 12)); //$NON-NLS-1$
		minutesLabel.setTextFill(Color.web(event.getTextColor()));
		
		Label numMinutes = new Label("03"); //$NON-NLS-1$
		numMinutes.setFont(Font.font ("Verdana", 40)); //$NON-NLS-1$
		numMinutes.setTextFill(Color.web(event.getTextColor()));
		
		Label secondsLabel = new Label(Messages.getString("ViewEventsScene.secondsLabel")); //$NON-NLS-1$
		secondsLabel.setAccessibleText(Messages.getString("ViewEventsScene.secondsLabelACC")); //$NON-NLS-1$
		secondsLabel.setFont(Font.font ("Verdana", 12)); //$NON-NLS-1$
		secondsLabel.setTextFill(Color.web(event.getTextColor()));
		
		Label numSeconds = new Label("04"); //$NON-NLS-1$
		numSeconds.setFont(Font.font ("Verdana", 40)); //$NON-NLS-1$
		numSeconds.setTextFill(Color.web(event.getTextColor()));
		
		numDays.textProperty().bind(event.getCountDownTimer().getDaysLeft());
		numHours.textProperty().bind(event.getCountDownTimer().getHoursLeft());
		numMinutes.textProperty().bind(event.getCountDownTimer().getMinutesLeft());
		numSeconds.textProperty().bind(event.getCountDownTimer().getSecondsLeft());
		
		daysBox.getChildren().addAll(numDays, daysLabel);
		daysBox.setAlignment(Pos.CENTER);
		hoursBox.getChildren().addAll(numHours, hoursLabel);
		hoursBox.setAlignment(Pos.CENTER);
		minutesBox.getChildren().addAll(numMinutes,minutesLabel);
		minutesBox.setAlignment(Pos.CENTER);
		secondsBox.getChildren().addAll(numSeconds, secondsLabel);
		secondsBox.setAlignment(Pos.CENTER);
		
		daysAndWeeksBox.getChildren().add(daysBox);
		daysAndWeeksBox.setAlignment(Pos.CENTER);
		hoursAndMinutesBox.getChildren().addAll(hoursBox, minutesBox, secondsBox);
		hoursAndMinutesBox.setAlignment(Pos.CENTER);
		
		timeDisplayBox.getChildren().addAll(daysAndWeeksBox, hoursAndMinutesBox);
		timeDisplayBox.setAlignment(Pos.CENTER);
		return timeDisplayBox;
	}
	
	private Node createTimeBoxType3(EventCountdown event) {
		VBox timeDisplayBox = new VBox(20);
		
		VBox daysBox = new VBox(5);
		
		Label daysLabel = new Label(Messages.getString("ViewEventsScene.daysLabel")); //$NON-NLS-1$
		daysLabel.setAccessibleText(Messages.getString("ViewEventsScene.daysLabelACC")); //$NON-NLS-1$
		daysLabel.setFont(Font.font ("Verdana", 20)); //$NON-NLS-1$
		daysLabel.setTextFill(Color.web(event.getTextColor()));
		
		Label numDays = new Label("01"); //$NON-NLS-1$
		numDays.setFont(Font.font ("Verdana", 60)); //$NON-NLS-1$
		numDays.setTextFill(Color.web(event.getTextColor()));
		
		numDays.textProperty().bind(event.getCountDownTimer().getDaysLeft());
		
		daysBox.getChildren().addAll(numDays, daysLabel);
		daysBox.setAlignment(Pos.CENTER);

		timeDisplayBox.getChildren().add(daysBox);
		timeDisplayBox.setAlignment(Pos.CENTER);
		return timeDisplayBox;
	}

}
