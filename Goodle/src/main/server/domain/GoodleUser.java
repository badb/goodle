package main.server.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Key;

@Entity
@NamedQueries
({
	@NamedQuery
	(
			name = "findUsersByName",
			query = "SELECT u FROM GoodleUser u WHERE u.firstName = :name OR u.lastName = :name"	
	),
	@NamedQuery
	(
			name = "findUserByLogin",
			query = "SELECT u FROM GoodleUser u WHERE u.login = :login"
	),
	@NamedQuery
	(
			name = "findUserByLoginAndPassword",
			query = "SELECT u FROM GoodleUser u WHERE u.login = :login AND u.password = :password"
	)
})
public class GoodleUser implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key Id;
    public Key getId() { return Id; }
    
    private String accessTokenId;
    public String getAccessTokenId() { return accessTokenId; }
    public void setAccessTokenId(String token) { accessTokenId = token; }
    
    private String accessTokenSecret;
    public String getAccessTokenSecret() { return accessTokenSecret; }
    public void setAccessTokenSecret(String token) { accessTokenSecret = token; }
    
    private String requestId;
    public String getRequestId() { return requestId; }
    public void setRequestId(String Id) { requestId = Id; }
    
	private String login;
	public String getLogin() { return login; }
	public void setLogin(String login) { this.login = login; }
	
	private String password;
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
    
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
	
	private Set<Long> coursesLed = new HashSet<Long>();
	public Set<Long> getCoursesLed() 
	{ 
		return Collections.unmodifiableSet(coursesLed); 
	}
	public void addCourseLed(Course course) 
	{ 
		coursesLed.add(course.getId()); 
	}
	public void removeCourseLed (Course course) 
	{ 
		coursesLed.remove(course.getId()); 
	}
	
	private Set<Long> courses = new HashSet<Long>();
	public Set<Long> getCourses() { return Collections.unmodifiableSet(courses); }
	public void addCourse(Course course) { courses.add(course.getId()); }
	public void removeCourse (Course course) { courses.remove(course.getId()); }
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Message> messages = new ArrayList<Message>();
    public List<Message> getMessages() { return Collections.unmodifiableList(messages); }
    public void addMessage(Message message) { messages.add(message); }
    public void removeMessage(Message message) { messages.remove(message); }
    
    public GoodleUser() { }
	
    public GoodleUser(String login, String password, String firstName, String lastName, Email email)
    {
    	this.login = login;
    	this.password = password;
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.email = email;
    }

}
