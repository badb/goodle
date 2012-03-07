package edu.goodle.prototype.db;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Link;
import com.google.appengine.api.datastore.Text;

import edu.goodle.prototype.db.exceptions.DataModificationFailedException;
import edu.goodle.prototype.db.exceptions.EntityCreationFailedException;
import edu.goodle.prototype.shared.EMF;

public class DbApi {
	
	private EntityManagerFactory emf = EMF.get();
	
	public DbApi() { }
	
	public void createUser(String firstName, String lastName, Email email) throws EntityCreationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			GoodleUser u = new GoodleUser(firstName, lastName, email);
			em.persist(u);
		}
		catch (Exception e)
		{
			String msg = "The creation of the user has failed: " + e.getMessage();
			throw new EntityCreationFailedException(msg);
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
		finally { em.close(); }
		return results;
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
		throws EntityCreationFailedException
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
			throw new EntityCreationFailedException(msg);
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
	
	public void modifyMessage(Message message, Text text)
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
	}

}
