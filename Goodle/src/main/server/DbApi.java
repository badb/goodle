package main.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import main.shared.EMF;
import main.shared.models.Comment;
import main.shared.models.Course;
import main.shared.models.DataModificationFailedException;
import main.shared.models.GoodleSession;
import main.shared.models.GoodleUser;
import main.shared.models.Homework;
import main.shared.models.HomeworkFile;
import main.shared.models.Mark;
import main.shared.models.Material;
import main.shared.models.Message;
import main.shared.models.Module;

import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;
import com.google.appengine.api.datastore.Text;


public class DbApi {
	
	private EntityManagerFactory emf;
	
	public DbApi() { emf = EMF.get(); }
	/*
	public void createUser
	(
			String login, 
			String password, 
			String firstName, 
			String lastName, 
			Email email
	) 
			throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			GoodleUser u = new GoodleUser(login, password, firstName, lastName, email);
			em.persist(u);
		}
		catch (Exception e)
		{
			String msg = "The creation of the user has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }
	}
	
	public void modifyUser
	(
			Key userKey, 
			String firstName, 
			String lastName, 
			Email email
	) 
			throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			GoodleUser user = em.find(GoodleUser.class, userKey);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			em.getTransaction().commit();
		}
		catch (Exception e) 
		{ 
			String msg = "The modification of the user has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }
	}
	
	@SuppressWarnings("unchecked")
	public List<GoodleUser> findUsersByName(String name)
	{
		List<GoodleUser> results;
		EntityManager em = emf.createEntityManager();
		try
		{
			Query q = em.createNamedQuery("findUsersByName");
			q.setParameter("name", name);
			results = (List<GoodleUser>) q.getResultList();
		}
		catch (NoResultException e) { results = null; }
		finally { em.close(); }
		return results;
	}
	
	public GoodleUser findUserByLogin(String login)
	{
		GoodleUser result;
		EntityManager em = emf.createEntityManager();
		try
		{
			Query q = em.createNamedQuery("findUserByLogin");
			q.setParameter("login", login);
			result = (GoodleUser) q.getSingleResult();
		}
		catch (NoResultException e) { result = null; }
		finally { em.close(); }
		return result;
	}
	
	public void createCourse
	(
			String name,
			String term,
			Text desc, 
			Link site, 
			Collection<GoodleUser> teachers, 
			Collection<GoodleUser> members, 
			Link calendar
	)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			Course c = new Course(name, term, desc, site, teachers, members, calendar);
			em.persist(c);
		}
		catch (Exception e)
		{
			String msg = "The creation of the course has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }
	}
	
	public void modifyCourse
	(
			Key courseKey,
			Text desc, 
			Link site, 
			String term
	)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			Course course = em.find(Course.class, courseKey);
			course.setDesc(desc);
			course.setSite(site);
			course.setTerm(term);
			em.getTransaction().commit();
		}
		catch (Exception e)
		{
			String msg = "The modification of the course has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }
	}
	
	public List<Course> findUserCourses(Key userKey)
	{
		List<Course> results = new ArrayList<Course>();
		EntityManager em = emf.createEntityManager();
		try
		{
			GoodleUser user = em.find(GoodleUser.class, userKey);
			Set<Key> courses = user.getCourses();
			for (Key k : courses)
			{
				Query q = em.createNamedQuery("findCoursesByKey");
				q.setParameter("key", k);
				results.add((Course) q.getSingleResult());
			}
		}
		finally { em.close(); }
		return results;
	}
	
	public List<Course> findUserCoursesLed(Key userKey)
	{
		List<Course> results = new ArrayList<Course>();
		EntityManager em = emf.createEntityManager();
		try
		{
			GoodleUser user = em.find(GoodleUser.class, userKey);
			Set<Key> courses = user.getCoursesLed();
			for (Key k : courses)
			{
				Query q = em.createNamedQuery("findCoursesByKey");
				q.setParameter("key", k);
				results.add((Course) q.getSingleResult());
			}
		}
		finally { em.close(); }
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public List<Course> findCoursesByName(String name)
	{
		List<Course> results;
		EntityManager em = emf.createEntityManager();
		try
		{
			Query q = em.createNamedQuery("findCoursesByName");
			q.setParameter("name", name);
			results = (List<Course>) q.getResultList();
		}
		catch (NoResultException e) { results = null; }
		finally { em.close(); }
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public List<Course> findCoursesByTerm(String term)
	{
		List<Course> results;
		EntityManager em = emf.createEntityManager();
		try
		{
			Query q = em.createNamedQuery("findCoursesByTerm");
			q.setParameter("term", term);
			results = (List<Course>) q.getResultList();
		}
		catch (NoResultException e) { results = null; }
		finally { em.close(); }
		return results;
	}
	
	public void addCommentToCourse(Key authorKey, Text text, Key courseKey)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			GoodleUser author = em.find(GoodleUser.class, authorKey);
			Course course = em.find(Course.class, courseKey);
			course = em.merge(course);
			Comment comment = new Comment(author, text);
			course.addComment(comment);
			em.getTransaction().commit();
		}
		catch (Exception e)
		{
			String msg = "Adding comment to the course has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }		
	}
	
	public void removeCommentFromCourse(Comment comment, Key courseKey)
			throws DataModificationFailedException
		{
			EntityManager em = emf.createEntityManager();
			try
			{
				em.getTransaction().begin();
				Course course = em.find(Course.class, courseKey);
				comment = em.merge(comment);
				course.removeComment(comment);
				em.getTransaction().commit();
			}
			catch (Exception e)
			{
				String msg = "Removing comment from the course has failed: " + e.getMessage();
				throw new DataModificationFailedException(msg);
			}
			finally { em.close(); }		
		}
	
	public void addMemberToCourse(Key memberKey, Key courseKey)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			GoodleUser member = em.find(GoodleUser.class, memberKey);
			Course course = em.find(Course.class, courseKey);
			course = em.merge(course);
			member.addCourse(course);
			course.addMember(member);
			em.getTransaction().commit();
		}
		catch (Exception e) 
		{
			String msg = "Adding the member to the course has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }
	}
	
	public void removeMemberFromCourse(Key memberKey, Key courseKey)
			throws DataModificationFailedException
		{
			EntityManager em = emf.createEntityManager();
			try
			{
				em.getTransaction().begin();
				GoodleUser member = em.find(GoodleUser.class, memberKey);
				Course course = em.find(Course.class, courseKey);
				member.removeCourse(course);
				course.removeMember(member);
				em.getTransaction().commit();
			}
			catch (Exception e) 
			{
				String msg = "Removing the member from the course has failed: " + e.getMessage();
				throw new DataModificationFailedException(msg);
			}
			finally { em.close(); }
		} 
	
	public void addMessageToCourse(Key authorKey, Text text, Key courseKey)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			GoodleUser author = em.find(GoodleUser.class, authorKey);
			Course course = em.find(Course.class, courseKey);
			Message msg = new Message(author, text);
			course.addMessage(msg);
			em.getTransaction().commit();
		}
		catch (Exception e)
		{
			String msg = "Adding the message to the course has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }		
	}
	
	public void removeMessageFromCourse(Key messageKey, Key courseKey)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			Course course = em.find(Course.class, courseKey);
			Message message = em.find(Message.class, messageKey);
			course.removeMessage(message);
			em.getTransaction().commit();
		}
		catch (Exception e)
		{
			String msg = "Removing the message from the course has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }		
	}
	
	public void addTeacherToCourse(Key teacherKey, Key courseKey)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			GoodleUser teacher = em.find(GoodleUser.class, teacherKey);
			Course course = em.find(Course.class, courseKey);
			teacher.addCourseLed(course);
			course.addTeacher(teacher);
			em.getTransaction().commit();
		}
		catch (Exception e)
		{
			String msg = "Adding the teacher to the course has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }
	}
	
	public void removeTeacherFromCourse(Key teacherKey, Key courseKey)
			throws DataModificationFailedException
		{
			EntityManager em = emf.createEntityManager();
			try
			{
				em.getTransaction().begin();
				GoodleUser teacher = em.find(GoodleUser.class, teacherKey);
				Course course = em.find(Course.class, courseKey);
				teacher.removeCourseLed(course);
				course.removeTeacher(teacher);
				em.getTransaction().commit();
			}
			catch (Exception e)
			{
				String msg = "Removing the teacher from the course has failed: " + e.getMessage();
				throw new DataModificationFailedException(msg);
			}
			finally { em.close(); }
		}
	
	public void createModule(Key courseKey, Collection<Material> materials, boolean isVisible)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			Course course = em.find(Course.class, courseKey);
			Module module = new Module(materials, isVisible);
			course.addModule(module);
			em.getTransaction().commit();
		}
		catch (Exception e)
		{
			String msg = "Adding the module to the course has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }
	}
	
	public void copyModule(Key courseKey, Module module, boolean isVisible)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			Course course = em.find(Course.class, courseKey);
			Module m = new Module(module, isVisible);
			course.addModule(m);
			em.getTransaction().commit();
		}
		catch (Exception e)
		{
			String msg = "Adding the module to the course has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }
	}
	
	public void removeModule(Key courseKey, Key moduleKey)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			Course course = em.find(Course.class, courseKey);
			Module module = em.find(Module.class, moduleKey);
			course.removeModule(module);
			em.getTransaction().commit();
		}
		catch (Exception e)
		{
			String msg = "Removing the module from the course has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }
	}
	
	public void addCommentToModule(Key authorKey, Text text, Key moduleKey)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			GoodleUser author = em.find(GoodleUser.class, authorKey);
			Module module = em.find(Module.class, moduleKey);
			Comment comment = new Comment(author, text);
			module.addComment(comment);
			em.getTransaction().commit();
		}
		catch (Exception e)
		{
			String msg = "Adding comment to the module has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }		
	}
	
	public void removeCommentFromModule(Key commentKey, Key moduleKey)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			Module module = em.find(Module.class, moduleKey);
			Comment comment = em.find(Comment.class, commentKey);
			module.removeComment(comment);
			em.getTransaction().commit();
		}
		catch (Exception e)
		{
			String msg = "Removing comment from the module has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }		
	}
	
	public void addHomeworkToModule(Key authorKey, String name, Text desc, Date deadline, Key moduleKey)
			throws DataModificationFailedException
		{
			EntityManager em = emf.createEntityManager();
			try
			{
				em.getTransaction().begin();
				GoodleUser author = em.find(GoodleUser.class, authorKey);
				Module module = em.find(Module.class, moduleKey);
				Homework homework = new Homework(author, name, desc, deadline);
				module.addMaterial(homework);
				em.getTransaction().commit();
			}
			catch (Exception e)
			{
				String msg = "Adding the homework to the module has failed: " + e.getMessage();
				throw new DataModificationFailedException(msg);
			}
			finally { em.close(); }		
		}	

	public void removeMaterialFromModule(Key materialKey, Key moduleKey)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			Module module = em.find(Module.class, moduleKey);
			Material material = em.find(Material.class, materialKey);
			module.removeMaterial(material);
			em.getTransaction().commit();
		}
		catch (Exception e)
		{
			String msg = "Removing the material from the module has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }		
	}	

	public void addCommentToMaterial(Key authorKey, Text text, Key materialKey)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			GoodleUser author = em.find(GoodleUser.class, authorKey);
			Material material = em.find(Material.class, materialKey);
			Comment comment = new Comment(author, text);
			material.addComment(comment);
			em.getTransaction().commit();
		}
		catch (Exception e)
		{
			String msg = "Adding comment to the material has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }		
	}
		
	public void removeCommentFromMaterial(Key commentKey, Key materialKey)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			Material material = em.find(Material.class, materialKey);
			Comment comment = em.find(Comment.class, commentKey);
			material.removeComment(comment);
			em.getTransaction().commit();
		}
		catch (Exception e)
		{
			String msg = "Removing comment from the material has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }		
	}
	
	public void addFileToHomework(Key authorKey, Link file, Key homeworkKey)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			GoodleUser author = em.find(GoodleUser.class, authorKey);
			Homework homework = em.find(Homework.class, homeworkKey);
			HomeworkFile homeworkFile = new HomeworkFile(author, file);
			homework.addProvided(homeworkFile);
			em.getTransaction().commit();
		}
		catch (Exception e)
		{
			String msg = "Adding the file to the material has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }		
	}
			
	public void removeFileFromHomework(Key fileKey, Key homeworkKey)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			Homework homework = em.find(Homework.class, homeworkKey);
			HomeworkFile file = em.find(HomeworkFile.class, fileKey);
			homework.removeProvided(file);
			em.getTransaction().commit();
		}
		catch (Exception e)
		{
			String msg = "Removing comment from the material has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }		
	}	
	
	public void modifyComment(Key commentKey, Text text)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			Comment comment = em.find(Comment.class, commentKey);
			comment.setText(text);
			em.getTransaction().commit();
		}
		catch (Exception e)
		{
			String msg = "The modification of the message has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }	
	}
	
	public void modifyMessage(Key messageKey, Text text)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			Message message = em.find(Message.class, messageKey);
			message.setText(text);
			em.getTransaction().commit();
		}
		catch (Exception e)
		{
			String msg = "The modification of the message has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }	
	}
	
	public void modifyMark(Key markKey, int val, Text comment)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			Mark mark = em.find(Mark.class, markKey);
			mark.setVal(val);
			mark.setComment(comment);
			em.getTransaction().commit();
		}
		catch (Exception e)
		{
			String msg = "The modification of the mark has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }	
	}
	
	// Tymczasowo:
	
	public void modifyUser(GoodleUser user) throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try { user = em.merge(user); }
		catch (Exception e) 
		{ 
			String msg = "The modification of the user has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }
	}
	
	public void createSession(GoodleUser user) throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			GoodleSession session = new GoodleSession(user);
			em.persist(session);
		}
		catch (Exception e)
		{
			String msg = "The creation of the session has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }
	}
	
	public String loginUser(String login, String password) 
	{
		Tymczasowo
		try
		{
			GoodleUser user = findUserByLogin("llama");
			if (user == null) 
			{
				createUser("llama", "llama", null, null, null);
				Logger.getLogger("").severe("Llama created.");
			}
			else  Logger.getLogger("").severe("Llama found.");
			//Link l = new Link("www.pizzaro.es");
			//Text t = new Text("");
			//createCourse("Historia Inków", "2012L", t, l, new ArrayList<GoodleUser>(), new ArrayList<GoodleUser>(), null);
			//Course c = findCoursesByName("Historia Inków").get(0);
			//GoodleUser u = findUserByLogin("llama");
			//addMemberToCourse(u, c);
		}
		catch (DataModificationFailedException e)
		{
			Logger.getLogger("").severe("Unable to create llama: " + e.getMessage());
		}
		
		String sessionKey = null;
		EntityManager em = emf.createEntityManager();
		try
		{
			Query q = em.createNamedQuery("findUserByLoginAndPassword");
			q.setParameter("login", login);
			q.setParameter("password", password);
			GoodleUser user = (GoodleUser) q.getSingleResult();
			GoodleSession session = new GoodleSession(user);
			em.persist(session);
			session = em.merge(session);
			sessionKey = session.getKey().toString();
		}
		catch (NoResultException e)
		{
			Logger.getLogger("").severe("Unable to log in: wrong credentials.");
		}
		catch (Exception e)
		{
			Logger.getLogger("").severe("Unable to log in: " + e.getMessage());
		}
		finally { em.close(); }
		Logger.getLogger("").severe("Session key returned: " + sessionKey);
		return sessionKey;
	} */

}
