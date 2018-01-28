// Shivangi Goel
// ITP 368, Fall 2017
// Final Project
// goelshiv@usc.edu

package model;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;

public class CountDownTimer implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -442398920774184948L;
	
	//constants that will be used for calculations below
	public static int SECONDS_IN_A_WEEK = 7 * 24 * 60 * 60;
	public static int SECONDS_IN_A_DAY = 24 * 60 * 60;
	public static int SECONDS_IN_AN_HOUR = 60 * 60;
	public static int SECONDS_IN_A_MINUTE =  60;
	
	LocalDateTime eventdate; //date till which the timer is supposed to run
	transient StringProperty weeksLeft; //string property for number of weeks left
	transient StringProperty daysLeft;  //string property for number of days left
	transient StringProperty hoursLeft;  //string property for number of hours left
	transient StringProperty minutesLeft;  //string property for number of minutes left
	transient StringProperty secondsLeft;  //string property for number of seconds left
	TimerType type; //type of timer
	transient Timeline timeline; //timeline for calling the calculate function every second
	
	EventCountdown myEvent; //refernce to the event that this count down timer belongs to
	boolean isOver; //boolean that specifies if the event is over as it has count down to the event
	
	public CountDownTimer(EventCountdown myEvent) {
		this.myEvent = myEvent;
		this.isOver = false;
		this.eventdate = myEvent.getEventdate();
		this.type = myEvent.getTimerType();
		setLeftProperties();
		setTimeline();
	}
	
	//initializing string properties
	public void setLeftProperties(){
		weeksLeft = new SimpleStringProperty("--");
		daysLeft = new SimpleStringProperty("--");
		hoursLeft = new SimpleStringProperty("--");
		minutesLeft = new SimpleStringProperty("--");
		secondsLeft = new SimpleStringProperty("--");
	}

	//setting up timeline for calling the calculate function every second
	public void setTimeline() {
		
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), e->{
			calculateTime(); //call this function every second
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
	}
	
	//figures out which version to call
	private void calculateTime() {
		if(type == TimerType.Weeks) {
			calculateTimeInWeeks();
		}else if(type == TimerType.DaysAndHours) {
			calculateTimeInDaysAndHours();
		}else {
			calculateTimeInJustDays();
		}
		
	}
	
	
	 //version for timer type 1
	private void calculateTimeInWeeks() {
		LocalDateTime currDate = LocalDateTime.now();//get the current LocalDateTime
		//diff between LocalDateTime to count down to and current LocalDateTime
		long diff = eventdate.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond() - 
				currDate.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
		//get weeks
		this.weeksLeft.set(properlyFormatted(Integer.toString((int) (diff / SECONDS_IN_A_WEEK))));
		//get the remainder to get the total number of seconds left
		diff = diff % SECONDS_IN_A_WEEK;
		//get days
		this.daysLeft.set(properlyFormatted(Integer.toString((int) (diff / SECONDS_IN_A_DAY))));
		//get the remainder to get the total number of seconds left
		diff = diff % SECONDS_IN_A_DAY;
		//get hours
		this.hoursLeft.set(properlyFormatted(Integer.toString((int) (diff/SECONDS_IN_AN_HOUR))));
		//get the remainder to get the total number of seconds left
		diff = diff % SECONDS_IN_AN_HOUR;
		//dividend gives minutes
		this.minutesLeft.set(properlyFormatted(Integer.toString((int) (diff/SECONDS_IN_A_MINUTE))));
		//remainder gives
		this.secondsLeft.set(properlyFormatted(Integer.toString((int) (diff%SECONDS_IN_A_MINUTE))));
		
		//check if the count down is finished
		if(Integer.parseInt(this.weeksLeft.get()) == 0 && 
		Integer.parseInt(this.daysLeft.get()) == 0 && 
		Integer.parseInt(this.hoursLeft.get()) == 0 && 
		Integer.parseInt(this.minutesLeft.get()) == 0 && 
		Integer.parseInt(this.secondsLeft.get()) == 0) {
			isOver = true;
			timeline.stop();
			myEvent.playAudio();
		}
		
	}
	
	
	 //version for timer type 2
	private void calculateTimeInDaysAndHours() {
		LocalDateTime currDate = LocalDateTime.now();//get the current LocalDateTime
		//diff between LocalDateTime to count down to and current LocalDateTime
		long diff = eventdate.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond() - 
				currDate.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
		//get days
		this.daysLeft.set(properlyFormatted(Integer.toString((int) (diff / SECONDS_IN_A_DAY))));
		//get the remainder to get the total number of seconds left
		diff = diff % SECONDS_IN_A_DAY;
		//get hours
		this.hoursLeft.set(properlyFormatted(Integer.toString((int) (diff/SECONDS_IN_AN_HOUR))));
		//get the remainder to get the total number of seconds left
		diff = diff % SECONDS_IN_AN_HOUR;
		//dividend gives minutes
		this.minutesLeft.set(properlyFormatted(Integer.toString((int) (diff/SECONDS_IN_A_MINUTE))));
		//remainder gives
		this.secondsLeft.set(properlyFormatted(Integer.toString((int) (diff%SECONDS_IN_A_MINUTE))));
		
		//check if the count down is finished
		if(Integer.parseInt(this.daysLeft.get()) == 0 && 
		Integer.parseInt(this.hoursLeft.get()) == 0 && 
		Integer.parseInt(this.minutesLeft.get()) == 0 && 
		Integer.parseInt(this.secondsLeft.get()) == 0) {
			isOver = true;
			timeline.stop();
			myEvent.playAudio();
		}
		
	}

	 //version for timer type 3
	private void calculateTimeInJustDays() {
		LocalDateTime currDate = LocalDateTime.now(); //get the current LocalDateTime
		//diff between LocalDateTime to count down to and current LocalDateTime
		long diff = eventdate.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond() - 
				currDate.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond(); 
		//get days
		this.daysLeft.set(properlyFormatted(Integer.toString((int) (diff / SECONDS_IN_A_DAY))));
		
		//check if the count down is finished
		if(Integer.parseInt(this.daysLeft.get()) == 0) {
			isOver = true;
			timeline.stop();
			myEvent.playAudio();
		}
	}

	
	public LocalDateTime getEventdate() {
		return eventdate;
	}

	public void setEventdate(LocalDateTime eventdate) {
		this.eventdate = eventdate;
	}

	public StringProperty getWeeksLeft() {
		return weeksLeft;
	}

	public StringProperty getDaysLeft() {
		return daysLeft;
	}

	public StringProperty getHoursLeft() {
		return hoursLeft;
	}

	public StringProperty getMinutesLeft() {
		return minutesLeft;
	}

	public StringProperty getSecondsLeft() {
		return secondsLeft;
	}

	public Timeline getTimeline() {
		return timeline;
	}
	
	public boolean isOver() {
		return isOver;
	}

	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}

	//formats single digit numbers
	private String properlyFormatted(String input) {
		StringBuilder sbBuilder = new StringBuilder(input);
		if(input.length() == 1) {
			sbBuilder.insert(0,"0");
		}
		return sbBuilder.toString();
		
	}

	public void setWeeksLeft(StringProperty weeksLeft) {
		this.weeksLeft = weeksLeft;
	}

	public void setDaysLeft(StringProperty daysLeft) {
		this.daysLeft = daysLeft;
	}

	public void setHoursLeft(StringProperty hoursLeft) {
		this.hoursLeft = hoursLeft;
	}

	public void setMinutesLeft(StringProperty minutesLeft) {
		this.minutesLeft = minutesLeft;
	}

	public void setSecondsLeft(StringProperty secondsLeft) {
		this.secondsLeft = secondsLeft;
	}

	public static void setSECONDS_IN_A_WEEK(int sECONDS_IN_A_WEEK) {
		SECONDS_IN_A_WEEK = sECONDS_IN_A_WEEK;
	}

	public static void setSECONDS_IN_A_DAY(int sECONDS_IN_A_DAY) {
		SECONDS_IN_A_DAY = sECONDS_IN_A_DAY;
	}

	public static void setSECONDS_IN_AN_HOUR(int sECONDS_IN_AN_HOUR) {
		SECONDS_IN_AN_HOUR = sECONDS_IN_AN_HOUR;
	}

	public static void setSECONDS_IN_A_MINUTE(int sECONDS_IN_A_MINUTE) {
		SECONDS_IN_A_MINUTE = sECONDS_IN_A_MINUTE;
	}

	public void setType(TimerType type) {
		this.type = type;
	}

	public void setTimeline(Timeline timeline) {
		this.timeline = timeline;
	}
}
