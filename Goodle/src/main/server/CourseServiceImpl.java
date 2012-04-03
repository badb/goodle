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

import main.client.services.CourseService;
import main.client.utils.CourseShortDesc;
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

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;
import com.google.appengine.api.datastore.Text;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


public class CourseServiceImpl extends RemoteServiceServlet implements CourseService 
{
	private static final long serialVersionUID = 1L;
	
	private static EntityManagerFactory emf = EMF.get();
	
	public void createCourse
	(
			String name,
			String term,
			String desc, 
			Collection<GoodleUser> teachers, 
			Collection<GoodleUser> members, 
			Link calendar
	)
		throws DataModificationFailedException
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			Course c = new Course(name, term, desc, teachers, members, calendar);
			em.persist(c);
		}
		catch (Exception e)
		{
			String msg = "The creation of the course has failed: " + e.getMessage();
			throw new DataModificationFailedException(msg);
		}
		finally { em.close(); }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<CourseShortDesc> findCoursesDescByName(String name) 
	{
		ArrayList<CourseShortDesc> results = new ArrayList<CourseShortDesc>();
		EntityManager em = emf.createEntityManager();
		try
		{		
			Query q = em.createNamedQuery("findCoursesDescByName");
			q.setParameter("name", name);
			List<Object[]> objects = (List<Object[]>) q.getResultList();
			for (Object[] o : objects)
			{
				Key key = (Key) o[0];
				String courseName = (String) o[1];
				String desc = (String) o[2];
				results.add(new CourseShortDesc(key, courseName, desc));
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
