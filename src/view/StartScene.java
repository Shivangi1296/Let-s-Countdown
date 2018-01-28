// Shivangi Goel
// ITP 368, Fall 2017
// Final Project
// goelshiv@usc.edu

package view;

import controller.Controller;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
import model.User;

public class StartScene extends Scene{

	// scene height
	private static int sceneHeight = 630;
	// scene width
	private static int sceneWidth = 400;
	
	private static final String USER_ALREADY_EXISTS_WARNING = 
			Messages.getString("StartScene.usernameWarning"); //$NON-NLS-1$
	private static final String INVALID_PASSWORD_WARNING = 
			Messages.getString("StartScene.passwordWarning"); //$NON-NLS-1$
	private static final String NO_SUCH_USER_EXISTS_WARNING = 
			Messages.getString("StartScene.userWarning"); //$NON-NLS-1$
	
	// reference to controller
	private Controller controller;
	// main layout pane
	private static BorderPane mainPane = new BorderPane();
	//label for title
	private Label appTitleLabel;
	//label for username
	private Label usernameLabel;
	//label for password
	private Label passwordLabel;
	//label for username
	private TextField usernameField;
	//label for password
	private PasswordField passwordField;
	//button for login
	private Button loginButton;
	
	private Label existingUserLabel;
	private Hyperlink signupLabel, loginLabel;
	
	private Label warningLabel = new Label("warning"); //$NON-NLS-1$
	
	private ComboBox<String> bgBox;
	
	BorderPane outerCenterPane;
	
	//button for sign up
	private Button signupButton;
		
	public StartScene(Controller controller) {
		super(mainPane, sceneWidth, sceneHeight);
		this.controller = controller;
		mainPane.setStyle("-fx-background-color: ANTIQUEWHITE;" );  //$NON-NLS-1$
		createComoponents();
	}

	private void createComoponents() {
		//set up header image
		Image headerImage = new Image("/images/countdown-hands.jpg"); //$NON-NLS-1$
		ImageView header = new ImageView(headerImage);
		header.setFitWidth(400);
		header.setFitHeight(150);
		header.setSmooth(true); //use a better quality filtering algorithm (false is less quality, but faster)
		header.setCache(true); //cache to improve performance
		mainPane.setTop(header);
		
		outerCenterPane = new BorderPane();
		outerCenterPane.setPadding(new Insets(20,0,0,0));
		
		//setup Title Label
		appTitleLabel = new Label(Messages.getString("StartScene.appTitle")); //$NON-NLS-1$
		appTitleLabel.setAccessibleText(Messages.getString("StartScene.appTitleACC")); //$NON-NLS-1$
		appTitleLabel.setFont(Font.font ("Verdana",FontWeight.BOLD, 40)); //$NON-NLS-1$
		appTitleLabel.setWrapText(true);
		appTitleLabel.setTextAlignment(TextAlignment.CENTER);
		appTitleLabel.setTextFill(Color.DARKMAGENTA);
		
		warningLabel.setFont(Font.font ("Verdana", 10)); //$NON-NLS-1$
		warningLabel.setWrapText(true);
		warningLabel.setTextAlignment(TextAlignment.CENTER);
		warningLabel.setTextFill(Color.CRIMSON);
		warningLabel.setVisible(false);
		
		outerCenterPane.setTop(appTitleLabel);
		outerCenterPane.setCenter(setUpLoginPane());
		
		//setting center pane to the center of border pane
		mainPane.setCenter(outerCenterPane);
		
		BorderPane bottomPane = new BorderPane();
		bottomPane.setPadding(new Insets(20,10,10,10));
		
		bgBox = new ComboBox<String>();
		bgBox.setAccessibleHelp(Messages.getString("StartScene.switchBackgroundsACC"));  //$NON-NLS-1$
		bgBox.getItems().addAll(Messages.getString("StartScene.plainBackGround"), Messages.getString("StartScene.floralBackground")); //$NON-NLS-1$ //$NON-NLS-2$
		bgBox.setValue(Messages.getString("StartScene.plainBackGround")); //$NON-NLS-1$
		bottomPane.setCenter(bgBox);
		
		bgBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String t, String t1) {
		            System.out.println(t); //old value
		            System.out.println(t1); //new value
		            controller.changeBackground(t1);
		            
		        }
		});
		
		mainPane.setBottom(bottomPane);
	}
	
	public void changeBackground(String typeBG) {
		if(typeBG.equals(Messages.getString("StartScene.plainBackGround"))) { //$NON-NLS-1$
	    		mainPane.setStyle("-fx-background-color: ANTIQUEWHITE;" ); //$NON-NLS-1$
	    }else {
	
	        	mainPane.setStyle("-fx-background-image: url("+"/images/floral.jpg"+");"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	        
	    }
	}
	
	Node setUpLoginPane(){
		//center pane for holding all the components except header image
		VBox centerPane = new VBox();
		centerPane.setSpacing(30);
		centerPane.setAlignment(Pos.CENTER);
		
		//setup a vbox for username and password labels and fields
		VBox loginBox = new VBox(10);
		loginBox.setAlignment(Pos.CENTER);
		
		//setup username label
		usernameLabel = new Label(Messages.getString("StartScene.username")); //$NON-NLS-1$
		usernameLabel.setAccessibleText(Messages.getString("StartScene.usernameACC")); //$NON-NLS-1$
		usernameLabel.setFont(Font.font ("Verdana", 15)); //$NON-NLS-1$
		usernameLabel.setWrapText(true);
		usernameLabel.setTextAlignment(TextAlignment.CENTER);
		usernameLabel.setTextFill(Color.DARKBLUE);
		ImageView usernameIcon = new ImageView(new Image("/images/username-icon.png")); //$NON-NLS-1$
		usernameIcon.setFitWidth(25);
		usernameIcon.setFitHeight(25);
		usernameIcon.setSmooth(true); //use a better quality filtering algorithm (false is less quality, but faster)
		usernameIcon.setCache(true); //cache to improve performance
		usernameLabel.setGraphic(usernameIcon);
		
		//setup username field
		usernameField = new TextField("");
		usernameField.setAccessibleHelp(Messages.getString("StartScene.usernameFieldACC")); //$NON-NLS-1$
		usernameField.setMaxWidth(300);
		usernameField.setMinHeight(35);
		
		//connecting label to node
		usernameLabel.setLabelFor(usernameField);
		
		//setup password label
		passwordLabel = new Label(Messages.getString("StartScene.password")); //$NON-NLS-1$
		passwordLabel.setAccessibleText(Messages.getString("StartScene.passwordACC")); //$NON-NLS-1$
		passwordLabel.setFont(Font.font ("Verdana", 15)); //$NON-NLS-1$
		passwordLabel.setWrapText(true);
		passwordLabel.setTextAlignment(TextAlignment.CENTER);
		passwordLabel.setTextFill(Color.DARKBLUE);
		ImageView passwordIcon = new ImageView(new Image("/images/password-icon.png")); //$NON-NLS-1$
		passwordIcon.setFitWidth(25);
		passwordIcon.setFitHeight(25);
		passwordIcon.setSmooth(true); //use a better quality filtering algorithm (false is less quality, but faster)
		passwordIcon.setCache(true); //cache to improve performance
		passwordLabel.setGraphic(passwordIcon);
		
		//setup password field
		passwordField = new PasswordField();
		passwordField.setAccessibleHelp(Messages.getString("StartScene.passwordFieldACC")); //$NON-NLS-1$
		passwordField.setFont(Font.font(10));
		passwordField.setMaxWidth(300);
		passwordField.setMinHeight(35);
		
		//connecting label to node
		passwordLabel.setLabelFor(passwordField);
				
		loginButton = new Button(Messages.getString("StartScene.login")); //$NON-NLS-1$
		loginButton.setAccessibleHelp(Messages.getString("StartScene.loginACC")); //$NON-NLS-1$
		loginButton.setPrefHeight(40);
		loginButton.setPrefWidth(300);
		loginButton.setStyle("-fx-font: 18 Verdana; -fx-base : DARKMAGENTA"); //$NON-NLS-1$
		loginButton.setOnAction(event->{
			User u = new User(usernameField.getText(), passwordField.getText());
			int result = controller.isUserValid(u);
			if( result == controller.NO_USER_FOUND) { //no user found
				warningLabel.setText(NO_SUCH_USER_EXISTS_WARNING);
				warningLabel.setVisible(true);
			}else if(result == controller.INCORRECT_PASSWORD){
				warningLabel.setText(INVALID_PASSWORD_WARNING);
				warningLabel.setVisible(true);
			}else {
				//user found
				controller.setCurrUser(controller.getUserMap().get(u.getUsername()));
				controller.loadViewEventsScene();
			}
		});
		
		//login button will be disabled until both username field and password field have some text
		//referenced from https://stackoverflow.com/questions/23040531/how-to-disable-button-when-textfield-is-empty
		loginButton.disableProperty().bind(
				Bindings.isEmpty(usernameField.textProperty())
			    .or(Bindings.isEmpty(passwordField.textProperty()))
		);
		
		//setup a hbox for labels below
		HBox labelBox = new HBox(5);
		labelBox.setAlignment(Pos.CENTER);
		
		existingUserLabel = new Label(Messages.getString("StartScene.newUserLabel")); //$NON-NLS-1$
		existingUserLabel.setAccessibleText(Messages.getString("StartScene.newUserLabelACC")); //$NON-NLS-1$
		existingUserLabel.setFont(Font.font ("Verdana", 15)); //$NON-NLS-1$
		existingUserLabel.setWrapText(true);
		existingUserLabel.setTextAlignment(TextAlignment.CENTER);
		existingUserLabel.setTextFill(Color.DARKBLUE);
		
		
		signupLabel = new Hyperlink(Messages.getString("StartScene.signUpLabel")); //$NON-NLS-1$
		signupLabel.setAccessibleHelp(Messages.getString("StartScene.signUpLabelACC")); //$NON-NLS-1$
		signupLabel.setFont(Font.font ("Verdana", 15)); //$NON-NLS-1$
		signupLabel.setAccessibleRole(AccessibleRole.HYPERLINK);
		signupLabel.setUnderline(true);
		signupLabel.setWrapText(true);
		signupLabel.setTextAlignment(TextAlignment.CENTER);
		signupLabel.setTextFill(Color.DARKMAGENTA);
		
		signupLabel.setOnAction(event->{
			warningLabel.setVisible(false);
			outerCenterPane.setCenter(setUpSignUpPane());
		});
		
		labelBox.getChildren().addAll(existingUserLabel, signupLabel);

		//adding login elements to loginBox
		loginBox.getChildren().addAll(warningLabel, usernameLabel, usernameField , passwordLabel, passwordField);
		
		//adding all the components to center pane
		centerPane.getChildren().addAll(loginBox, loginButton, labelBox );
		
		return centerPane;
	}
	
	Node setUpSignUpPane() {
		//center pane for holding all the components except header image
		VBox centerPane = new VBox();
		centerPane.setSpacing(30);
		centerPane.setAlignment(Pos.CENTER);
		
		//setup a vbox for username and password labels and fields
		VBox loginBox = new VBox(10);
		loginBox.setAlignment(Pos.CENTER);
		
		//setup username label
		usernameLabel = new Label("Create Username"); //$NON-NLS-1$
		usernameLabel.setAccessibleText("Create Username"); //$NON-NLS-1$
		usernameLabel.setFont(Font.font ("Verdana", 15)); //$NON-NLS-1$
		usernameLabel.setWrapText(true);
		usernameLabel.setTextAlignment(TextAlignment.CENTER);
		usernameLabel.setTextFill(Color.DARKBLUE);
		ImageView usernameIcon = new ImageView(new Image("/images/username-icon.png")); //$NON-NLS-1$
		usernameIcon.setFitWidth(25);
		usernameIcon.setFitHeight(25);
		usernameIcon.setSmooth(true); //use a better quality filtering algorithm (false is less quality, but faster)
		usernameIcon.setCache(true); //cache to improve performance
		usernameLabel.setGraphic(usernameIcon);
		
		//setup username field
		usernameField = new TextField();
		usernameField.setAccessibleHelp(Messages.getString("StartScene.usernameFieldACC")); //$NON-NLS-1$
		usernameField.setMaxWidth(300);
		usernameField.setMinHeight(35);
		
		//connecting label to node
		usernameLabel.setLabelFor(usernameField);
		
		//setup password label
		passwordLabel = new Label("Create Password"); //$NON-NLS-1$
		passwordLabel.setAccessibleText("Create Password"); //$NON-NLS-1$
		passwordLabel.setFont(Font.font ("Verdana", 15)); //$NON-NLS-1$
		passwordLabel.setWrapText(true);
		passwordLabel.setTextAlignment(TextAlignment.CENTER);
		passwordLabel.setTextFill(Color.DARKBLUE);
		ImageView passwordIcon = new ImageView(new Image("/images/password-icon.png")); //$NON-NLS-1$
		passwordIcon.setFitWidth(25);
		passwordIcon.setFitHeight(25);
		passwordIcon.setSmooth(true); //use a better quality filtering algorithm (false is less quality, but faster)
		passwordIcon.setCache(true); //cache to improve performance
		passwordLabel.setGraphic(passwordIcon);
		
		//setup password field
		passwordField = new PasswordField();
		passwordField.setAccessibleHelp(Messages.getString("StartScene.passwordFieldACC")); //$NON-NLS-1$
		passwordField.setFont(Font.font(10));
		passwordField.setMaxWidth(300);
		passwordField.setMinHeight(35);
		
		//connecting label to node
		passwordLabel.setLabelFor(passwordField);
				
		signupButton = new Button(Messages.getString("StartScene.signUpButton"));  //$NON-NLS-1$
		signupButton.setAccessibleHelp(Messages.getString("StartScene.18")); //$NON-NLS-1$
		signupButton.setPrefHeight(40);
		signupButton.setPrefWidth(300);
		signupButton.setStyle("-fx-font: 18 Verdana; -fx-base : DARKMAGENTA"); //$NON-NLS-1$
		signupButton.setOnAction(event->{
			User u = new User(usernameField.getText(), passwordField.getText());
			
			if(controller.addToUserMap(u)) {
				controller.setCurrUser(u);
				controller.loadViewEventsScene();
			}
			else {
				warningLabel.setText(USER_ALREADY_EXISTS_WARNING);
				warningLabel.setVisible(true);
			}
		});

		//login button will be disabled until both username field and password field have some text
		//referenced from https://stackoverflow.com/questions/23040531/how-to-disable-button-when-textfield-is-empty
		signupButton.disableProperty().bind(
				Bindings.isEmpty(usernameField.textProperty())
			    .or(Bindings.isEmpty(passwordField.textProperty()))
		);
				
		//setup a hbox for labels below
		HBox labelBox = new HBox(5);
		labelBox.setAlignment(Pos.CENTER);
		
		existingUserLabel = new Label(Messages.getString("StartScene.existingUserLabel")); //$NON-NLS-1$
		existingUserLabel.setAccessibleText(Messages.getString("StartScene.existingUserLabelACC")); //$NON-NLS-1$
		existingUserLabel.setFont(Font.font ("Verdana", 15)); //$NON-NLS-1$
		existingUserLabel.setWrapText(true);
		existingUserLabel.setTextAlignment(TextAlignment.CENTER);
		existingUserLabel.setTextFill(Color.DARKBLUE);
		
		
		loginLabel = new Hyperlink(Messages.getString("StartScene.loginLabel")); //$NON-NLS-1$
		loginLabel.setAccessibleHelp(Messages.getString("StartScene.loginLabelACC")); //$NON-NLS-1$
		loginLabel.setFont(Font.font ("Verdana", 15)); //$NON-NLS-1$
		loginLabel.setAccessibleRole(AccessibleRole.HYPERLINK);
		loginLabel.setUnderline(true);
		loginLabel.setWrapText(true);
		loginLabel.setTextAlignment(TextAlignment.CENTER);
		loginLabel.setTextFill(Color.DARKMAGENTA);
		
		loginLabel.setOnAction(event->{
			warningLabel.setVisible(false);
			outerCenterPane.setCenter(setUpLoginPane());
		});
		
		labelBox.getChildren().addAll(existingUserLabel, loginLabel);

		//adding login elements to loginBox
		loginBox.getChildren().addAll(warningLabel, usernameLabel, usernameField , passwordLabel, passwordField);
				
		
		
		//adding all the components to center pane
		centerPane.getChildren().addAll(loginBox, signupButton, labelBox );
		
		return centerPane;
	}

	public void resetComponents() {
		outerCenterPane.setCenter(setUpLoginPane());
		warningLabel.setVisible(false);
		usernameField.setText(""); //$NON-NLS-1$
		passwordField.setText(""); //$NON-NLS-1$
	}

}
