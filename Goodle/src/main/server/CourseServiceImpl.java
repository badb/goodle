package main.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import main.client.services.CourseService;
import main.shared.EMF;
import main.shared.models.Course;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


public class CourseServiceImpl extends RemoteServiceServlet implements CourseService 
{
	private static final long serialVersionUID = 1L;
	
	private static EntityManagerFactory emf = EMF.get();

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Course> findCoursesByName(String name) 
	{
		Collection<Course> results;
		EntityManager em = emf.createEntityManager();
		try
		{
			Query q = em.createNamedQuery("findCoursesByName");
			q.setParameter("name", name);
			results = new ArrayList<Course> (q.getResultList());
		}
		catch (NoResultException e) { results = null; }
		finally { em.close(); }
		return results;
	}

}
