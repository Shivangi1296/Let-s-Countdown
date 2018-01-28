// Shivangi Goel
// ITP 368, Fall 2017
// Final Project
// goelshiv@usc.edu

package model;
import java.io.Serializable;
// Shivangi Goel
// ITP 368, Fall 2017
// Final Project
// goelshiv@usc.edu
import java.util.ArrayList;

public class User implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2066847351999800665L;
	
	String username; //name of the user
	String password; //password of the user
	ArrayList<EventCountdown> events; //list of events that the user has created
	
	public User(String username, String password) 
	{
		this.username = username;
		this.password = password;
		this.events = new ArrayList<EventCountdown>();
		//System.out.println("User created. Username : "+username+", password : "+password);
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @return the events
	 */
	public ArrayList<EventCountdown> getEvents() {
		return events;
	}
	
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @param events the events to set
	 */
	public void setEvents(ArrayList<EventCountdown> events) {
		this.events = events;
	}
	
	public  void addEvent(EventCountdown event) {
		
		this.events.add(event);

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", "
				+ "events=" + events + "]";
	}
	
}
