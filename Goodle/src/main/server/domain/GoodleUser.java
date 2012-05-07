package main.server.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Version;

import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.users.UserService;

@Entity
@NamedQueries
({
	@NamedQuery
	(
		name = "findUsersByName",
		query = "SELECT u FROM GoodleUser u WHERE u.firstName LIKE :name OR u.lastName LIKE :name"      
	),
	@NamedQuery
	(
		name = "findUserByLogin",
		query = "SELECT u FROM GoodleUser u WHERE u.login = :login"
	),
	@NamedQuery
	(
		name = "findUserByEMail",
		query = "SELECT u FROM GoodleUser u WHERE u.email = :email"
	)
})
public class GoodleUser implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	@Column(name="version")
	private Integer version;

	private UsosInfo usosInfo;

	private String login;

	private String password;

	private String firstName;

	private String lastName;

	private Email email;

	private Set<Long> coursesLed = new HashSet<Long>();

	private Set<Long> coursesAttended = new HashSet<Long>();

	@OneToMany(cascade=CascadeType.ALL)
	private List<Message> messages = new ArrayList<Message>();

	public static final EntityManager entityManager() { return EMF.get().createEntityManager(); }

	public Long getId() { return id; }

	public Integer getVersion() { return version; }

	public UsosInfo getUsosInfo() { return usosInfo; }

	public String getLogin() { return login; }

	public String getPassword() { return password; }

	public String getFirstName() { return firstName; }

	public String getLastName() { return lastName; }

	public Email getEmail() { return email; }

	public Set<Long> getCoursesLed() { return Collections.unmodifiableSet(coursesLed); }

	public Set<Long> getCoursesAttended() { return Collections.unmodifiableSet(coursesAttended); }

	public List<Message> getMessages() { return Collections.unmodifiableList(messages); }

	public void setLogin(String login) { this.login = login; }

	public void setPassword(String password) { this.password = password; }

	public void setFirstName(String firstName) { this.firstName = firstName; }

	public void setLastName(String lastName) { this.lastName = lastName; }

	public void setEmail(Email email) { this.email = email; }

	/* Collections methods */

	public void addCourseLed(Course course) { coursesLed.add(course.getId()); }

	public void removeCourseLed (Course course) { coursesLed.remove(course.getId()); }

	public void addCourseAttended(Course course) { coursesAttended.add(course.getId()); }

	public void removeCourseAttended(Course course) { coursesAttended.remove(course.getId()); }

	public void addMessage(Message message) { messages.add(message); }

	public void removeMessage(Message message) { messages.remove(message); }

	/* Db methods */

	public void persist() 
	{
		EntityManager em = entityManager();
		try { em.persist(this); }
		finally { em.close(); }
	}

	public void remove()
	{
		EntityManager em = entityManager();
		try 
		{
			Course attached = em.find(Course.class, this.id);
			em.remove(attached); 
		}
		finally { em.close(); }
	}

	public static GoodleUser findGoodleUser(Long id) 
	{
		if (id == null) { return null; }
		EntityManager em = entityManager();
		try 
		{
			GoodleUser u = em.find(GoodleUser.class, id);
			return u;
		}
		finally { em.close(); }
	}

	@SuppressWarnings("unchecked")
	public static List<GoodleUser> findGoodleUsersByName(String name)
	{
		EntityManager em = entityManager();
		try
		{               

			Query q = em.createNamedQuery("findUserByName");
			q.setParameter("name", name + "%");
			List<GoodleUser> list = q.getResultList();
			list.size(); /* force it to materialize */ 
			return list;
		}
		catch (NoResultException e) { return null; }
		finally { em.close(); }
	}
	
	public static GoodleUser getCurrentUser(){
		Logger logger = Logger.getLogger("goodle");
		logger.log(Level.FINE, "getGoodleUser()");
		UserService service = UserServiceFactory.getUserService();
		if(service.isUserLoggedIn()){
			logger.log(Level.FINE, "User is logged in Google");
			User user = service.getCurrentUser();
			EntityManager em = entityManager();
			GoodleUser toRet = null;
			try
			{
				Query q = em.createNamedQuery("findUserByEMail");
				q.setParameter("email", user.getEmail());
				q.setMaxResults(1);
				toRet = (GoodleUser) q.getSingleResult();
				return toRet;
			}
			catch (NoResultException e) {				
			    logger.log(Level.WARNING, "User is logged In, but doesn't exist in our database, Creating New User");
			    GoodleUser goodleUser = new GoodleUser();
			    Email email = new Email(user.getEmail());
			    goodleUser.setEmail(email);
			    goodleUser.setLogin(user.getEmail());
			    goodleUser.persist();			    
			    return goodleUser;
			}
			finally { em.close(); }			
		} else {
			return null;
		}
	}
	
	public static String getLoginUrl(String destination){
		UserService service = UserServiceFactory.getUserService();
		if(service.isUserLoggedIn()){
			return null;
		}else {
			return service.createLoginURL(destination);
		}
	}
	
	public static String getLogoutUrl(String destination){
		UserService service = UserServiceFactory.getUserService();
		if(service.isUserLoggedIn()){
			return service.createLogoutURL(destination);
		}else {
			return null;
		}
	}
}