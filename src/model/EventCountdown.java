// Shivangi Goel
// ITP 368, Fall 2017
// Final Project
// goelshiv@usc.edu

package model;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

//this class would entail all the information related to an event
public class EventCountdown implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -379199424238872174L;
	
	String eventname; //name of the event
	LocalDateTime eventdate; //date of the event
	String backgroundColor; //background color chosen by the user for the timer
	String textColor; //text color chosen by the user for the timer
	String imageString; //string address of the clipart chosen by the user
	String audioString;//string address of the audio chosen by the user
	TimerType timerType; //enum representing timer type(chosen by the user)
	CountDownTimer countDownTimer; //the countdown timer which will have corresponding class 
								// based on the enum.
	transient Media sound; // media for sound to be played at the end  of the countdown
	transient MediaPlayer mediaPlayer; //media player for the above media
	
	//constructor
	public EventCountdown(String eventname, 
		LocalDateTime eventdate,
		Color backgroundColor, 
		Color textColor,
		String imageString,
		String audioString,
		TimerType timerType) throws URISyntaxException 
	{
		
		this.eventname = eventname;
		this.eventdate = eventdate;
		this.backgroundColor = backgroundColor.toString();
		this.textColor = textColor.toString();
		this.imageString = imageString;
		this.audioString = audioString;
		this.timerType = timerType;
		this.countDownTimer = new CountDownTimer(this);
		//set up the media player and media
		setupAudio();
		
		
	}
	
	//set up the media player and media
	public void setupAudio() throws URISyntaxException {
		sound = new Media(getClass().getResource(audioString).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
	}
	
	
	//plays the mediaplayer
	public void playAudio() {
		
		mediaPlayer.play();
	
	}
	
	public String getEventname() {
		return eventname;
	}
	
	
	public LocalDateTime getEventdate() {
		return eventdate;
	}
	
	public String getBackgroundColor() {
		return backgroundColor;
	}
	
	public String getTextColor() {
		return textColor;
	}
	
	public String getImageString() {
		return imageString;
	}
	
	public String getAudioString() {
		return audioString;
	}
	
	public TimerType getTimerType() {
		return timerType;
	}
	
	public CountDownTimer getCountDownTimer() {
		return countDownTimer;
	}
	
	public void setEventname(String eventname) {
		this.eventname = eventname;
	}
	
	public void setEventdate(LocalDateTime eventdate) {
		this.eventdate = eventdate;
	}
	
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
	
	public void setImageString(String imageString) {
		this.imageString = imageString;
	}
	
	public void setAudioString(String audioString) {
		this.audioString = audioString;
	}
	
	public void setTimerType(TimerType timerType) {
		this.timerType = timerType;
	}
	
	public void setCountDownTimer(CountDownTimer countDownTimer) {
		this.countDownTimer = countDownTimer;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EventCountdown [eventname=" + eventname + ", eventdate=" + eventdate + ", backgroundColor="
				+ backgroundColor.toString() + ", textColor=" + textColor.toString() + ", imageString=" + imageString + ", audioString="
				+ audioString + ", timerType=" + timerType + "]";
	}
	
}
