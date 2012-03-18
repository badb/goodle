package edu.goodle.prototype.db;

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

import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;
import com.google.appengine.api.datastore.Text;

import edu.goodle.prototype.shared.EMF;

public class DbApi {
	
	private EntityManagerFactory emf;
	
	public DbApi() { emf = EMF.get(); }
	
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
			GoodleUser user, 
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
			user = em.merge(user);
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
	
	public GoodleUser findUserByKey(Key key)
	{
		GoodleUser result;
		EntityManager em = emf.createEntityManager();
		try
		{
			Query q = em.createNamedQuery("findUserByKey");
			q.setParameter("key", key);
			result = (GoodleUser) q.getSingleResult();
		}
		catch (NoResultException e) { result = null; }
		finally { em.close(); }
		return result;
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
			Course course,
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
			course = em.merge(course);
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
	
	public List<Course> findUserCourses(GoodleUser user)
	{
		List<Course> results = new ArrayList<Course>();
		EntityManager em = emf.createEntityManager();
		try
		{
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
	
	public List<Course> findUserCoursesLed(GoodleUser user)
	{
		List<Course> results = new ArrayList<Course>();
		EntityManager em = emf.createEntityManager();
		try
		{
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
	
	public void addCommentToCourse(GoodleUser author, Text text, Course course)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			course = em.merge(course);
			Message comment = new Message(author, text);
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
	
	public void removeCommentFromCourse(Message comment, Course course)
			throws DataModificationFailedException
		{
			EntityManager em = emf.createEntityManager();
			try
			{
				em.getTransaction().begin();
				course = em.merge(course);
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
	
	public void addMemberToCourse(GoodleUser member, Course course)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			member = em.merge(member);
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
	
	public void removeMemberFromCourse(GoodleUser member, Course course)
			throws DataModificationFailedException
		{
			EntityManager em = emf.createEntityManager();
			try
			{
				em.getTransaction().begin();
				member = em.merge(member);
				course = em.merge(course);
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
	
	public void addMessageToCourse(GoodleUser author, Text text, Course course)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			course = em.merge(course);
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
	
	public void removeMessageFromCourse(Message message, Course course)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			course = em.merge(course);
			message = em.merge(message);
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
	
	public void addTeacherToCourse(GoodleUser teacher, Course course)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			teacher = em.merge(teacher);
			course = em.merge(course);
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
	
	public void removeTeacherFromCourse(GoodleUser teacher, Course course)
			throws DataModificationFailedException
		{
			EntityManager em = emf.createEntityManager();
			try
			{
				em.getTransaction().begin();
				teacher = em.merge(teacher);
				course = em.merge(course);
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
	
	public void createModule(Course course, Collection<Material> materials, boolean isVisible)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			course = em.merge(course);
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
	
	public void copyModule(Course course, Module module, boolean isVisible)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			course = em.merge(course);
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
	
	public void removeModule(Course course, Module module)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			course = em.merge(course);
			module = em.merge(module);
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
	
	public void addCommentToModule(GoodleUser author, Text text, Module module)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			module = em.merge(module);
			Message comment = new Message(author, text);
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
	
	public void removeCommentFromModule(Message comment, Module module)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			module = em.merge(module);
			comment = em.merge(comment);
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
	
	public void addHomeworkToModule(GoodleUser author, String name, Text desc, Date deadline, Module module)
			throws DataModificationFailedException
		{
			EntityManager em = emf.createEntityManager();
			try
			{
				em.getTransaction().begin();
				module = em.merge(module);
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

	public void removeMaterialFromModule(Material material, Module module)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			module = em.merge(module);
			material = em.merge(material);
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

	public void addCommentToMaterial(GoodleUser author, Text text, Material material)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			material = em.merge(material);
			Message comment = new Message(author, text);
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
		
	public void removeCommentFromMaterial(Message comment, Material material)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			material = em.merge(material);
			comment = em.merge(comment);
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
	
	public void addFileToHomework(GoodleUser author, Link file, Homework homework)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			homework = em.merge(homework);
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
			
	public void removeFileFromHomework(HomeworkFile file, Homework homework)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			homework = em.merge(homework);
			file = em.merge(file);
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
	
	public void modifyMessage(Message message, Text text)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			message = em.merge(message);
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
	
	public void modifyMark(Mark mark, int val, Text comment)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			mark = em.merge(mark);
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
		/* Tymczasowo */
		try
		{
			GoodleUser user = findUserByLogin("llama");
			if (user == null) createUser("llama", "llama", null, null, null);
		}
		catch (DataModificationFailedException e)
		{
			Logger.getLogger("").severe("Unable to create llama: " + e.getMessage());
		}
		
		/* Tymczasowo-end */
		String sessionKey = null;
		EntityManager em = emf.createEntityManager();
		try
		{
			Query q = em.createNamedQuery("loginUser");
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
	}

}
