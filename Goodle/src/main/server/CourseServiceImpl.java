package main.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import main.client.services.CourseService;
import main.client.utils.CourseShortDesc;
import main.shared.EMF;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


public class CourseServiceImpl extends RemoteServiceServlet implements CourseService 
{
	private static final long serialVersionUID = 1L;
	
	private static EntityManagerFactory emf = EMF.get();

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
				Text t = (Text) o[2];
				results.add(new CourseShortDesc((Key) o[0], (String) o[1], t.toString()));
			}
		}
		catch (NoResultException e) { results = null; }
		finally { em.close(); }
		return results;
	}

}
