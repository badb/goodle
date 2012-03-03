package edu.goodle.prototype.server;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import edu.goodle.prototype.shared.PMF;


public class GoodleUserDAOImpl implements GoodleUserDAO {

	public static PersistenceManagerFactory getPersistenceManagerFactory() {
		return PMF.get();
	}

	@Override
	public void addGoodleUser(GoodleUser user) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(user);
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodleUser> listGoodleUser() {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String query = "select from " + GoodleUser.class.getName();
		return (List<GoodleUser>) pm.newQuery(query).execute();
	}

	@Override
	public void removeGoodleUser(GoodleUser user) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		try {
			pm.currentTransaction().begin();

			user = pm.getObjectById(GoodleUser.class, user.getKey().getId());
			pm.deletePersistent(user);

			pm.currentTransaction().commit();
		} catch (Exception ex) {
			pm.currentTransaction().rollback();
			throw new RuntimeException(ex);
		} finally {
			pm.close();
		}

	}

	@Override
	public void updateGoodleUser(GoodleUser user) {
		PersistenceManager pm = getPersistenceManagerFactory()
		.getPersistenceManager();

		try {
			pm.currentTransaction().begin();
			GoodleUser dbuser = pm.getObjectById(GoodleUser.class, user.getKey().getId());
			dbuser.setLogin(user.getLogin());
			dbuser.setPassword(user.getPassword());
			dbuser.setAccess_token_key(user.getAccess_token_key());
			dbuser.setAccess_token_secret(user.getAccess_token_secret());
			pm.makePersistent(dbuser);
			pm.currentTransaction().commit();
		} catch (Exception ex) {
			pm.currentTransaction().rollback();
			throw new RuntimeException(ex);
		} finally {
			pm.close();
		}

	}
	
	
	public GoodleUser getUserByLogin(String login) {
		PersistenceManager pm = getPersistenceManagerFactory()
		.getPersistenceManager();
		String query = "select from " + GoodleUser.class.getName() + " where login == '" + login + "'";

		List<GoodleUser> list =  listGoodleUser();//(List<GoodleUser>) pm.newQuery(query).execute();
		if (list != null) {
			for (GoodleUser goodleUser : list) {
				if(goodleUser.getLogin().equals(login))
					return goodleUser;
			}
			return null;
		}
		else return null;
	}

	@Override
	public GoodleUser getUserByID(Long Id) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		return pm.getObjectById(GoodleUser.class, Id);
	}
}
