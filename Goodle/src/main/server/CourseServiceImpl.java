package main.server;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import main.client.services.CourseService;
import main.client.utils.CourseShortDesc;
import main.shared.EMF;
import main.shared.models.Course;
import main.shared.models.DataModificationFailedException;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


public class CourseServiceImpl extends RemoteServiceServlet implements CourseService 
{
	private static final long serialVersionUID = 1L;
	
	private static EntityManagerFactory emf = EMF.get();
	
	public Course createCourse
	(
			String name,
			String term,
			String desc, 
			Link calendar
	)
		throws DataModificationFailedException
	{
		Course c = new Course(name, term, desc, calendar);
		EntityManager em = emf.createEntityManager();
		try
		{
			em.persist(c);
		}
		catch (Exception e)
		{
			String msg = "The creation of the course has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }
		return c;
	}
	
	public Course findCourseByKey(Key key)
	{
		Course c = null;
		EntityManager em = emf.createEntityManager();
		try
		{
			c = em.find(Course.class, key);
		}
		catch (NoResultException e) { }
		finally { em.close(); }
		return c;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<CourseShortDesc> findCoursesDescByName(String name) 
	{
		ArrayList<CourseShortDesc> results = new ArrayList<CourseShortDesc>();
		EntityManager em = emf.createEntityManager();
		try
		{		
			Query q = em.createNamedQuery("findCoursesDescByName");
			q.setParameter("name", name);
			Collection<Object[]> objects = (Collection<Object[]>) q.getResultList();
			for (Object[] o : objects)
			{
				Key key = (Key) o[0];
				String courseName = (String) o[1];
				String term = (String) o[2];
				String desc = (String) o[3];
				results.add(new CourseShortDesc(key, courseName, term, desc));
			}
		}
		catch (NoResultException e) { results = null; }
		finally { em.close(); }
		return results;
	}
	
	public void modifyCourse
	(
			Key courseKey,
			String desc, 
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
}
