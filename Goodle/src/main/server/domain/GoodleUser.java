package main.server.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Basic;
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

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

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
	public Long getId() { return id; }

	@Version
	@Column(name="version")
	private Integer version;
	public Integer getVersion() { return version; }

	private UsosInfo usosInfo;
	public UsosInfo getUsosInfo() { return usosInfo; }

	@NotBlank
	private String login;
	public String getLogin() { return login; }
	public void setLogin(String login) { this.login = login; }

	@NotBlank
	private String firstName;
	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }

	@NotBlank
	private String lastName;
	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }

	@Email
	private String email;
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	@Basic
	private Set<Long> coursesLed = new HashSet<Long>();
	public Set<Long> getCoursesLed() { return Collections.unmodifiableSet(coursesLed); }
	public void addCourseLed(Course course) { coursesLed.add(course.getId()); }
	public void removeCourseLed (Course course) { coursesLed.remove(course.getId()); }

	@Basic
	private Set<Long> coursesAttended = new HashSet<Long>();
	public Set<Long> getCoursesAttended() { return Collections.unmodifiableSet(coursesAttended); }
	public void addCourseAttended(Course course) { coursesAttended.add(course.getId()); }
	public void removeCourseAttended(Course course) { coursesAttended.remove(course.getId()); }

	@OneToMany(cascade=CascadeType.ALL)
	private List<Message> messages = new ArrayList<Message>();
	public List<Message> getMessages() { return Collections.unmodifiableList(messages); }
	public void addMessage(Message message) { messages.add(message); }
	public void removeMessage(Message message) { messages.remove(message); }
	
	@Basic
	private Set<String> flags = new HashSet<String>();
	public Set<String> getFlags() { return Collections.unmodifiableSet(flags); }
	public void addFlag(String flag) { flags.add(flag); }
	public boolean hasFlag(String flag) { return flags.contains(flag); }
	public void removeFlag(String flag) { flags.remove(flag); }

	public static final EntityManager entityManager() { return EMF.get().createEntityManager(); }

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
	
	public static Set<GoodleUser> findGoodleUsers(Collection<Long> ids)
	{
		Set<GoodleUser> users = new HashSet<GoodleUser>();
		
		EntityManager em = entityManager();
		try
		{
			for(Long id : ids) users.add(em.find(GoodleUser.class, id));
			return users;
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
	
	public static GoodleUser getCurrentUser()
	{
		Logger logger = Logger.getLogger("goodle");
		logger.log(Level.FINE, "getGoodleUser()");
		
		UserService service = UserServiceFactory.getUserService();
		if (service.isUserLoggedIn())
		{
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
			catch (NoResultException e) 
			{				
			    logger.log(Level.WARNING, "User is logged In, but doesn't exist in our database, Creating New User");
			    
			    GoodleUser goodleUser = new GoodleUser();
			    goodleUser.setEmail(user.getEmail());
			    goodleUser.setLogin(user.getEmail());
			    goodleUser.persist();			    
			    return goodleUser;
			}
			finally { em.close(); }			
		}
		return null;
	}
	
	public static String getLoginUrl(String destination)
	{
		UserService service = UserServiceFactory.getUserService();
		if (!service.isUserLoggedIn()) return service.createLoginURL(destination);
		return null;
	}
	
	public static String getLogoutUrl(String destination)
	{
		UserService service = UserServiceFactory.getUserService();
		if (service.isUserLoggedIn()) return service.createLogoutURL(destination);
		return null;
	}
}