package edu.google.prototype.db;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Key;

@Entity
public class GUser {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
    public Key getKey() { return key; }
    
    private String accessTokenKey;
    public String getAccessTokenKey() { return accessTokenKey; }
    public void setAccessTokenKey(String token) { accessTokenKey = token; }
    
    private String accessTokenSecret;
    public String getAccessTokenSecret() { return accessTokenSecret; }
    public void setAccessTokenSecret(String token) { accessTokenSecret = token; }
    
    private String requestKey;
    public String getRequestKey() { return requestKey; }
    public void setRequestKey(String key) { requestKey = key; }
    
	private String firstName;
	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	
	private String lastName;
	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	
	private Email email;
	public Email getEmail() { return email; }
	public void setEmail(Email email) { this.email = email; }
	
	// Avatar
	
	private Set<Course> coursesLed = new HashSet<Course>();
	public Set<Course> getCoursesLed() { return coursesLed; }
	
	private Set<Course> courses = new HashSet<Course>();
	public Set<Course> getCourses() { return courses; }
	
    public List<Message> messages = new ArrayList<Message>();
    public List<Message> getMessages() { return messages; }
	
    public GUser(String firstName, String lastName, Email email)
    {
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.email = email;
    }

}
