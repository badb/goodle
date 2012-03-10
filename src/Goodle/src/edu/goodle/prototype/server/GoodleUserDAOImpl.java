package edu.goodle.prototype.server;

import java.util.List;

import javax.persistence.EntityManager;
import edu.goodle.prototype.shared.EMF;


public class GoodleUserDAOImpl implements GoodleUserDAO 
{
	static String goodleUser = GoodleUser.class.getName();

	@Override
	public void addGoodleUser(GoodleUser user) 
	{
		EntityManager em = EMF.get().createEntityManager();
		try { em.persist(user); } 
		finally { em.close(); }
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodleUser> listGoodleUser() 
	{
		EntityManager em = EMF.get().createEntityManager();
		String query = "select from " + GoodleUser.class.getName();
		return (List<GoodleUser>) em.createQuery(query).getResultList();
	}

	@Override
	public void removeGoodleUser(GoodleUser user) 
	{
		EntityManager em = EMF.get().createEntityManager();
		try 
		{
			em.getTransaction().begin();
			user = em.find(GoodleUser.class, user.getKey());
			em.remove(user);
			em.getTransaction().commit();
		} 
		catch (Exception ex) 
		{
			em.getTransaction().rollback();
			throw new RuntimeException(ex);
		} 
		finally { em.close(); }
	}

	@Override
	public void updateGoodleUser(GoodleUser user) 
	{
		EntityManager em = EMF.get().createEntityManager();
		try 
		{
			em.getTransaction().begin();
			GoodleUser dbuser = em.find(GoodleUser.class, user.getKey());
			dbuser.setLogin(user.getLogin());
			dbuser.setPassword(user.getPassword());
			dbuser.setAccess_token_key(user.getAccess_token_key());
			dbuser.setAccess_token_secret(user.getAccess_token_secret());
			em.persist(dbuser);
			em.getTransaction().commit();
		} 
		catch (Exception ex) 
		{
			em.getTransaction().rollback();
			throw new RuntimeException(ex);
		} 
		finally { em.close(); }
	}
	
	@SuppressWarnings("unchecked")
	public GoodleUser getUserByLogin(String login) 
	{
		EntityManager em = EMF.get().createEntityManager();
		String query = "SELECT u FROM " + goodleUser + " u WHERE u.login = '" + login + "'";
		List<GoodleUser> list =  (List<GoodleUser>) em.createQuery(query).getResultList();
		if (list != null) { return list.get(0); }
		return null;
	}

	@Override
	public GoodleUser getUserByID(Long Id) 
	{
		EntityManager em = EMF.get().createEntityManager();
		return em.find(GoodleUser.class, Id);
	}
}
