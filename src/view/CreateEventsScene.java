// Shivangi Goel
// ITP 368, Fall 2017
// Final Project
// goelshiv@usc.edu

package view;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import controller.Controller;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import messages.Messages;
import model.EventCountdown;
import model.TimerType;

public class CreateEventsScene extends Scene {

	// scene height
	private static int sceneHeight = 650;
	// scene width
	private static int sceneWidth = 795;
	
	// reference to controller
	private Controller controller;
	// main layout pane
	private static BorderPane createPane = new BorderPane();
	//set up header
	HBox headerBox = new HBox();
	// center layout pane
	private static BorderPane centerCreatePane = new BorderPane();
	Label createEventsLabel, eventNameLabel, eventDateLabel,eventTimeLabel,
	eventAudioLabel, dummyEventNameLabel, dummyEventBackgroundLabel, 
	dummyEventTextLabel, eventClipartLabel;
	TextField eventNameField;
	DatePicker eventDatePicker;
	HBox timeBox;
	ComboBox<String> hourPicker;
	ComboBox<String> minutePicker;
	ComboBox<String> ampmPicker;
	ComboBox<String> audioPicker;
	ToggleGroup radioGroup;
	RadioButton weeksRadio;
	RadioButton daysAndHoursRadio;
	RadioButton justDaysRadio;
	ColorPicker backgroundColorPicker, textColorPicker;
	ListView<String> clipartList;
	Button createEventButton, backButton;
	BorderPane dummyEventPane;
	
	public CreateEventsScene(Controller controller) {
		super(createPane, sceneWidth, sceneHeight);
		this.controller = controller;
		createComoponents();
		createPane.setStyle("-fx-background-color: ANTIQUEWHITE;" );  //$NON-NLS-1$
		createComoponents();
	}
	
	public void changeBackground(String typeBG){
		if(typeBG.equals("Plain")) { //$NON-NLS-1$
			createPane.setStyle("-fx-background-color: ANTIQUEWHITE;" ); //$NON-NLS-1$
	    }else {
	
	    		createPane.setStyle("-fx-background-image: url("+"/images/floral.jpg"+");"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	        
	    }
	}

	private void createComoponents() {
		setupHeader();
		createPane.setTop(headerBox);
		setupCenterPane();
		createPane.setCenter(centerCreatePane);
		createPane.setBottom(setupBottomPane());	
	}

	private void setupHeader() {
		//set up header image 1
		Image headerImage = new Image("/images/countdown-hands.jpg"); //$NON-NLS-1$
		ImageView header1 = new ImageView(headerImage);
		header1.setFitWidth(199);
		header1.setFitHeight(75);
		header1.setSmooth(true); //use a better quality filtering algorithm (false is less quality, but faster)
		header1.setCache(true); //cache to improve performance
		
		//set up header image 2
		ImageView header2 = new ImageView(headerImage);
		header2.setFitWidth(199);
		header2.setFitHeight(75);
		header2.setSmooth(true); //use a better quality filtering algorithm (false is less quality, but faster)
		header2.setCache(true); //cache to improve performance
		
		//set up header
		headerBox.getChildren().addAll(header1, header2);
	}
	
	private void setupCenterPane() {
		centerCreatePane.setPadding(new Insets(20,10,10,10));
		
		BorderPane titlePane = new BorderPane(); 
		//create title label for this window
		createEventsLabel = new Label(Messages.getString("CreateEventsScene.createEventTitle")); //$NON-NLS-1$
		createEventsLabel.setAccessibleText(Messages.getString("CreateEventsScene.createEventTitleACC")); //$NON-NLS-1$
		createEventsLabel.setFont(Font.font ("Verdana",FontWeight.BOLD, 25)); //$NON-NLS-1$
		createEventsLabel.setWrapText(true);
		createEventsLabel.setTextAlignment(TextAlignment.CENTER);
		createEventsLabel.setTextFill(Color.DARKMAGENTA);
		
		backButton = new Button(Messages.getString("CreateEventsScene.backButton"));  //$NON-NLS-1$
		backButton.setAccessibleHelp(Messages.getString("CreateEventsScene.backButtonACC")); //$NON-NLS-1$
		backButton.setPrefHeight(35);
		backButton.setStyle("-fx-font: 16 Verdana; -fx-base : DARKMAGENTA"); //$NON-NLS-1$
		backButton.setOnAction(event->{
			controller.loadViewEventsScene();
		});
		
		titlePane.setLeft(backButton);
		titlePane.setCenter(createEventsLabel);
		
		centerCreatePane.setTop(titlePane);
		
		//horizontal box with 3 vboxes that holds all controls
		//to set up a countdown
		centerCreatePane.setCenter(setupHBox());
	}

	private Node setupHBox() {
		HBox hpane = new HBox(20);
		//the first column of hbox
		VBox infoBox = setUpInfoBox();
		//the second column of hbox
		VBox colorBox = setUpColorBox();
		//the third column of hbox
		VBox clipartBox = setUpClipartBox();
		hpane.getChildren().addAll(infoBox, colorBox, clipartBox);
		return hpane;
	}

	private VBox setUpInfoBox() {
		VBox infoBox = new VBox(25);
		infoBox.setPadding(new Insets(20,20,0,10));
		
		VBox nameBox = new VBox(10);
		//setup event name label
		eventNameLabel = new Label(Messages.getString("CreateEventsScene.eventName")); //$NON-NLS-1$
		eventNameLabel.setAccessibleText(Messages.getString("CreateEventsScene.eventNameACC")); //$NON-NLS-1$
		eventNameLabel.setFont(Font.font ("Verdana",FontWeight.BOLD, 15)); //$NON-NLS-1$
		eventNameLabel.setWrapText(true);
		eventNameLabel.setTextAlignment(TextAlignment.CENTER);
		eventNameLabel.setTextFill(Color.DARKBLUE);
		//setup event name field
		eventNameField = new TextField();
		eventNameField.setAccessibleHelp(Messages.getString("CreateEventsScene.eventNameFieldACC")); //$NON-NLS-1$
		eventNameField.setPromptText("My Event"); //$NON-NLS-1$
		//add these to name box
		nameBox.getChildren().addAll(eventNameLabel, eventNameField);
		//connect label to node
		eventNameLabel.setLabelFor(eventNameField);
		
		VBox dateBox = new VBox(10);
		//setup event date label
		eventDateLabel = new Label(Messages.getString("CreateEventsScene.eventDate")); //$NON-NLS-1$
		eventDateLabel.setAccessibleText(Messages.getString("CreateEventsScene.eventDateACC")); //$NON-NLS-1$
		eventDateLabel.setFont(Font.font ("Verdana", FontWeight.BOLD,15)); //$NON-NLS-1$
		eventDateLabel.setWrapText(true);
		eventDateLabel.setTextAlignment(TextAlignment.CENTER);
		eventDateLabel.setTextFill(Color.DARKBLUE);
		//setup event date picker
		eventDatePicker = new DatePicker();
		eventDatePicker.setAccessibleHelp(Messages.getString("CreateEventsScene.datePickerACC")); //$NON-NLS-1$
		eventDatePicker.setValue(LocalDate.now().plusDays(1));
		
		//make the date picker unable to select dates that have passed
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (item.isBefore(LocalDate.now())) {
							setDisable(true);
							setStyle("-fx-background-color: #DDDDDD;"); //$NON-NLS-1$
						}
					}
				};
			}
		};
		eventDatePicker.setDayCellFactory(dayCellFactory);    
		
		//add these to date box
		dateBox.getChildren().addAll(eventDateLabel, eventDatePicker);
		//connect label to node
		eventDateLabel.setLabelFor(eventDatePicker);
		
		VBox timeOverallBox = new VBox(10);
		//setup event time label
		eventTimeLabel = new Label(Messages.getString("CreateEventsScene.eventTime")); //$NON-NLS-1$
		eventTimeLabel.setAccessibleText(Messages.getString("CreateEventsScene.eventTimeACC")); //$NON-NLS-1$
		eventTimeLabel.setFont(Font.font ("Verdana",FontWeight.BOLD, 15)); //$NON-NLS-1$
		eventTimeLabel.setWrapText(true);
		eventTimeLabel.setTextAlignment(TextAlignment.CENTER);
		eventTimeLabel.setTextFill(Color.DARKBLUE);
		//setup event time picker
		setUpTimePicker();
		//add these to timeOverall box
		timeOverallBox.getChildren().addAll(eventTimeLabel, timeBox);
		//connect label to nodes
		eventTimeLabel.setLabelFor(hourPicker);
		eventTimeLabel.setLabelFor(minutePicker);
		eventTimeLabel.setLabelFor(ampmPicker);
		
		VBox radioBox = new VBox(10);
		//setup radio buttons picker
		setUpRadioButtons();
		//add these to radio box
		radioBox.getChildren().addAll(weeksRadio, daysAndHoursRadio, justDaysRadio);
		
		VBox audioBox = new VBox(10);
		//setup event audio label
		eventAudioLabel = new Label(Messages.getString("CreateEventsScene.eventAudio")); //$NON-NLS-1$
		eventAudioLabel.setAccessibleText(Messages.getString("CreateEventsScene.eventAudioACC")); //$NON-NLS-1$
		eventAudioLabel.setFont(Font.font ("Verdana", FontWeight.BOLD,15)); //$NON-NLS-1$
		eventAudioLabel.setWrapText(true);
		eventAudioLabel.setTextAlignment(TextAlignment.CENTER);
		eventAudioLabel.setTextFill(Color.DARKBLUE);
		//setup audio picker
		setUpAudioPicker();
		//add these to radio box
		audioBox.getChildren().addAll(eventAudioLabel, audioPicker);
		//connect label to node
		eventAudioLabel.setLabelFor(audioPicker);
		
		infoBox.getChildren().addAll(nameBox, dateBox, timeOverallBox, 
				radioBox, audioBox);
	
		return infoBox;
	}

	private void setUpTimePicker() {
		timeBox = new HBox(5);
		
		//setup event hour picker
		setUpHourPicker();
		
		//set up label for Colon separating hours and minutes
		Label colonLabel = new Label(" : "); //$NON-NLS-1$
		colonLabel.setAccessibleText(Messages.getString("CreateEventsScene.colonACC")); //$NON-NLS-1$
		colonLabel.setFont(Font.font ("Verdana", FontWeight.BOLD, 15)); //$NON-NLS-1$
		colonLabel.setWrapText(true);
		colonLabel.setTextAlignment(TextAlignment.CENTER);
		colonLabel.setTextFill(Color.DARKBLUE);
		
		//setup event minute picker
		setUpMinutePicker();
		
		//setup event am/pm picker
		setUpAmPmPicker();
		
		timeBox.getChildren().addAll(hourPicker, colonLabel,
				minutePicker, ampmPicker);
	}

	private void setUpHourPicker() {
		hourPicker = new ComboBox<>();
		hourPicker.setAccessibleHelp(Messages.getString("CreateEventsScene.hourPickerACC")); //$NON-NLS-1$
		hourPicker.getItems().addAll("01","02","03","04","05","06","07","08","09","10","11","12"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
		hourPicker.setValue("12"); //$NON-NLS-1$
	}

	private void setUpMinutePicker() {
		minutePicker = new ComboBox<>();
		minutePicker.setAccessibleHelp(Messages.getString("CreateEventsScene.minutePickerACC")); //$NON-NLS-1$
		minutePicker.getItems().addAll("00","01","02","03","04","05","06","07","08","09", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$
				"10","11","12","13","14","15","16","17","18","19","20","21","22","23","24", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$ //$NON-NLS-13$ //$NON-NLS-14$ //$NON-NLS-15$
				"25","26","27","28","29","30","31","32","33","34","35","36", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
				"37","38","39","40","41","42","43","44","45","46","47","48", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
				"49","50","51","52","53","54","55","56","57","58","59"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$
		minutePicker.setValue("00"); //$NON-NLS-1$
	}
	
	private void setUpAmPmPicker() {
		ampmPicker = new ComboBox<>();
		ampmPicker.setAccessibleHelp(Messages.getString("CreateEventsScene.ampmPickerACC")); //$NON-NLS-1$
		ampmPicker.getItems().addAll("AM","PM"); //$NON-NLS-1$ //$NON-NLS-2$
		ampmPicker.setValue("AM"); //$NON-NLS-1$
	}

	private void setUpRadioButtons() {
		radioGroup = new ToggleGroup();
		//This option will run the countdown in 
		//terms of Weeks, Days, Hours, Minutes and Seconds
		weeksRadio = new RadioButton(Messages.getString("CreateEventsScene.weeksRadio")); //$NON-NLS-1$
		weeksRadio.setTextFill(Color.DARKBLUE);
		weeksRadio.setFont(Font.font ("Verdana", 15)); //$NON-NLS-1$
		weeksRadio.setWrapText(true);
		weeksRadio.setAccessibleHelp(Messages.getString("CreateEventsScene.weeksRadioACC")); //$NON-NLS-1$
		weeksRadio.setSelected(true);
		
		//This option will run the countdown in 
		//terms of Days, Hours, Minutes and Seconds
		daysAndHoursRadio = new RadioButton(Messages.getString("CreateEventsScene.daysAndHoursRadio")); //$NON-NLS-1$
		daysAndHoursRadio.setTextFill(Color.DARKBLUE);
		daysAndHoursRadio.setFont(Font.font ("Verdana", 15)); //$NON-NLS-1$
		daysAndHoursRadio.setWrapText(true);
		daysAndHoursRadio.setAccessibleHelp(Messages.getString("CreateEventsScene.daysAndHoursRadioACC")); //$NON-NLS-1$
		
		//This option will run the countdown in 
		//terms of just Days
		justDaysRadio = new RadioButton(Messages.getString("CreateEventsScene.justDaysRadio")); //$NON-NLS-1$
		justDaysRadio.setTextFill(Color.DARKBLUE);
		justDaysRadio.setFont(Font.font ("Verdana", 15)); //$NON-NLS-1$
		justDaysRadio.setWrapText(true);
		justDaysRadio.setAccessibleHelp(Messages.getString("CreateEventsScene.justDaysRadioACC")); //$NON-NLS-1$
		
		radioGroup.getToggles().addAll(weeksRadio, daysAndHoursRadio, justDaysRadio);
		
	}
	
	private void setUpAudioPicker() {
		audioPicker = new ComboBox<>();
		audioPicker.setAccessibleHelp(Messages.getString("CreateEventsScene.audioPickerACC")); //$NON-NLS-1$
		audioPicker.getItems().addAll(controller.getMapOfAudios().keySet());
		audioPicker.setValue("Cheering Sound"); //$NON-NLS-1$
	}
	
	private VBox setUpColorBox() {
		VBox colorBox = new VBox(30);
		colorBox.setPadding(new Insets(20,20,0,10));
		colorBox.setAlignment(Pos.CENTER);
		
		dummyEventPane = new BorderPane();
		dummyEventPane.setPrefHeight(150);
		dummyEventPane.setPrefWidth(250);
		dummyEventPane.setStyle("-fx-background-color: PINK"); //$NON-NLS-1$
		
		//setup dummy event name label
		dummyEventNameLabel = new Label(Messages.getString("CreateEventsScene.dummyEventName")); //$NON-NLS-1$
		dummyEventNameLabel.setAccessibleText(Messages.getString("CreateEventsScene.dummyEventNameACC")); //$NON-NLS-1$
		dummyEventNameLabel.setFont(Font.font ("Verdana", FontWeight.BOLD, 20)); //$NON-NLS-1$
		dummyEventNameLabel.setWrapText(true);
		dummyEventNameLabel.setTextAlignment(TextAlignment.CENTER);
		dummyEventNameLabel.setTextFill(Color.DARKMAGENTA);
		dummyEventNameLabel.setPadding(new Insets(10,5,10,5));
		dummyEventNameLabel.textProperty().bind(eventNameField.textProperty());
		
		dummyEventPane.setTop(dummyEventNameLabel);
		dummyEventPane.setAlignment(dummyEventNameLabel, Pos.CENTER);
		
		VBox backgroundBox = new VBox(15);
		backgroundBox.setAlignment(Pos.CENTER);
		//setup background color picker label
		dummyEventBackgroundLabel = new Label(Messages.getString("CreateEventsScene.eventBackgroundColor")); //$NON-NLS-1$
		dummyEventBackgroundLabel.setAccessibleText(Messages.getString("CreateEventsScene.eventBackgroundColorACC")); //$NON-NLS-1$
		dummyEventBackgroundLabel.setFont(Font.font ("Verdana",FontWeight.BOLD, 15)); //$NON-NLS-1$
		dummyEventBackgroundLabel.setWrapText(true);
		dummyEventBackgroundLabel.setTextAlignment(TextAlignment.CENTER);
		dummyEventBackgroundLabel.setTextFill(Color.DARKBLUE);
		//set up color picker for background of the countdown
		backgroundColorPicker = new ColorPicker(Color.PINK);
		backgroundColorPicker.setAccessibleHelp(Messages.getString("CreateEventsScene.backgroundColorPickerACC")); //$NON-NLS-1$
		backgroundBox.getChildren().addAll(dummyEventBackgroundLabel, backgroundColorPicker);
		//connect label to node
		dummyEventBackgroundLabel.setLabelFor(backgroundColorPicker);
		//making the color of the dummy event change as a different color is picked
		backgroundColorPicker.setOnAction(event->{
			dummyEventPane.setStyle("-fx-background-color: \"" +  //$NON-NLS-1$
			backgroundColorPicker.getValue()+"\""); //$NON-NLS-1$
		});
		
		VBox textBox = new VBox(15);
		textBox.setAlignment(Pos.CENTER);
		//setup text color picker label
		dummyEventTextLabel = new Label(Messages.getString("CreateEventsScene.eventTextColor")); //$NON-NLS-1$
		dummyEventTextLabel.setAccessibleText(Messages.getString("CreateEventsScene.eventTextColorACC")); //$NON-NLS-1$
		dummyEventTextLabel.setFont(Font.font ("Verdana", FontWeight.BOLD,15)); //$NON-NLS-1$
		dummyEventTextLabel.setWrapText(true);
		dummyEventTextLabel.setTextAlignment(TextAlignment.CENTER);
		dummyEventTextLabel.setTextFill(Color.DARKBLUE);
		//set up color picker for text of the countdown
		textColorPicker = new ColorPicker(Color.DARKMAGENTA);
		textColorPicker.setAccessibleHelp(Messages.getString("CreateEventsScene.textColorPickerACC")); //$NON-NLS-1$
		textBox.getChildren().addAll(dummyEventTextLabel, textColorPicker);
		//connect label to node
		dummyEventTextLabel.setLabelFor(textColorPicker);
		//binding label's color to color picker's value
		dummyEventNameLabel.textFillProperty().bind(textColorPicker.valueProperty());
				
		colorBox.getChildren().addAll(dummyEventPane, backgroundBox, textBox);
		return colorBox;
	}
	
	private VBox setUpClipartBox() {
		VBox clipartBox = new VBox(5);
		clipartBox.setPadding(new Insets(20,5,0,10));
		
		//setup event clipart label
		eventClipartLabel = new Label(Messages.getString("CreateEventsScene.eventClipart")); //$NON-NLS-1$
		eventClipartLabel.setWrapText(true);
		eventClipartLabel.setAccessibleText(Messages.getString("CreateEventsScene.eventClipartACC")); //$NON-NLS-1$
		eventClipartLabel.setFont(Font.font ("Verdana", FontWeight.BOLD, 15)); //$NON-NLS-1$
		eventClipartLabel.setWrapText(true);
		eventClipartLabel.setTextAlignment(TextAlignment.CENTER);
		eventClipartLabel.setTextFill(Color.DARKBLUE);
		
		//setup list of cliparts
		setUpClipartList();
		clipartList.setPrefWidth(150);
		clipartList.setMinWidth(150);
		clipartList.setPrefHeight(380);
		
		//connect label to node
		eventClipartLabel.setLabelFor(clipartList);
		
		clipartBox.getChildren().addAll(eventClipartLabel, clipartList);
		return clipartBox;
	}

	private void setUpClipartList() {
		clipartList = new ListView<String>();
		clipartList.setAccessibleHelp(Messages.getString("CreateEventsScene.clipartListACC")); //$NON-NLS-1$
        ObservableList<String> items = FXCollections.observableArrayList (controller.getMapOfCliparts().keySet());
        clipartList.setItems(items);
        
        //Referenced from https://stackoverflow.com/questions/33592308/javafx-how-to-put-imageview-inside-listview
        clipartList.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    //setText(null);
                    setGraphic(null);
                } 
                else {
                		imageView.setImage(controller.getMapOfCliparts().get(name));;
                		imageView.setFitWidth(120);
                		imageView.setFitHeight(100);
                		imageView.setSmooth(true); //use a better quality filtering algorithm (false is less quality, but faster)
                		imageView.setCache(true); //cache to improve performance
                		setGraphic(imageView);
                }
            }
        });
        clipartList.getSelectionModel().select(0);
        
	}
	
	private Node setupBottomPane() {
		BorderPane bPane = new BorderPane();
		bPane.setPadding(new Insets(0,0,20,0));
		
		createEventButton = new Button(Messages.getString("CreateEventsScene.createCountDownButton")); //$NON-NLS-1$
		createEventButton.setAccessibleHelp(Messages.getString("CreateEventsScene.createCountDownButtonACC")); //$NON-NLS-1$
		createEventButton.setPrefHeight(40);
		createEventButton.setPrefWidth(300);
		createEventButton.setStyle("-fx-font: 18 Verdana; -fx-base : DARKMAGENTA"); //$NON-NLS-1$
		
		//createEvent button will be disabled until event name field has some text
		//referenced from https://stackoverflow.com/questions/23040531/how-to-disable-button-when-textfield-is-empty
		createEventButton.disableProperty().bind(
				Bindings.isEmpty(eventNameField.textProperty())
		);
		
		createEventButton.setOnAction(event->{
			try {
				createEvent();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			controller.loadViewEventsScene();
		});
		
		bPane.setCenter(createEventButton);
		return bPane;
	}

	private void createEvent() throws URISyntaxException {
		TimerType timerType;
		if(weeksRadio.isSelected()) {
			timerType = TimerType.Weeks; 
		}else if(daysAndHoursRadio.isSelected()) {
			timerType = TimerType.DaysAndHours; 
		}else {
			timerType = TimerType.JustDays; 
		}
		
		//create a date time formatter 
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault()); //$NON-NLS-1$
		//get value of time by combining values from various controls
		String timeIs = hourPicker.getValue()+":"+minutePicker.getValue()+" "+ampmPicker.getValue(); //$NON-NLS-1$ //$NON-NLS-2$
	
		EventCountdown event= new EventCountdown(eventNameField.getText(), 
				eventDatePicker.getValue().atTime(LocalTime.parse(timeIs, dtf)), 
				backgroundColorPicker.getValue(), textColorPicker.getValue(), 
				clipartList.getSelectionModel().getSelectedItem(), 
				controller.getMapOfAudios().get(audioPicker.getValue()), timerType);
		
		controller.getCurrUser().addEvent(event);
		
		
	}
	
	public void resetComponents() {
		
		eventNameField.setText(""); //$NON-NLS-1$
		eventDatePicker.setValue(LocalDate.now().plusDays(1));
		weeksRadio.setSelected(true);
		daysAndHoursRadio.setSelected(false);
		justDaysRadio.setSelected(false);
		hourPicker.setValue("12"); //$NON-NLS-1$
		minutePicker.setValue("00"); //$NON-NLS-1$
		ampmPicker.setValue("AM"); //$NON-NLS-1$
		backgroundColorPicker.setValue(Color.PINK);
		textColorPicker.setValue(Color.DARKMAGENTA);
		dummyEventPane.setStyle("-fx-background-color: PINK"); //$NON-NLS-1$
		audioPicker.setValue("Cheering Sound"); //$NON-NLS-1$
		
	}

}
