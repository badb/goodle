package edu.google.prototype.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import edu.goodle.prototype.shared.EMF;

public class DbApi {
	
	private static EntityManagerFactory emf = EMF.get();
	
	public static void AddMemberToCourse(GUser student, Course course)
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			
		}
		catch (Exception e) { }
		finally { em.close(); }
	}

}
