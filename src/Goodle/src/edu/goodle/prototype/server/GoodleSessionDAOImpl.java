package edu.goodle.prototype.server;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.google.appengine.api.datastore.Key;

import edu.goodle.prototype.server.GoodleSessionDAO;
import edu.goodle.prototype.shared.PMF;

public class GoodleSessionDAOImpl implements GoodleSessionDAO {
	private static final PersistenceManagerFactory pmfInstance = PMF.get();

	public static PersistenceManagerFactory getPersistenceManagerFactory() {
		return pmfInstance;
	}

	@Override
	public void addGoodleSession(GoodleSession session) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		try {
			pm.makePersistent(session);
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodleSession> listGoodleSession() {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String query = "select from " + GoodleSession.class.getName();
		return (List<GoodleSession>) pm.newQuery(query).execute();
	}

	@Override
	public void removeGoodleSession(GoodleSession session) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		try {
			pm.currentTransaction().begin();

			session = pm.getObjectById(GoodleSession.class, session.getId());
			pm.deletePersistent(session);

			pm.currentTransaction().commit();
		} catch (Exception ex) {
			pm.currentTransaction().rollback();
			throw new RuntimeException(ex);
		} finally {
			pm.close();
		}
	}

	@Override
	public void updateGoodleSession(GoodleSession session) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();

		try {
			pm.currentTransaction().begin();
			GoodleSession dbSession = pm.getObjectById(GoodleSession.class,
					session.getId());
			dbSession.setUser(session.getUser());
			pm.makePersistent(dbSession);
			pm.currentTransaction().commit();
		} catch (Exception ex) {
			pm.currentTransaction().rollback();
			throw new RuntimeException(ex);
		} finally {
			pm.close();
		}

	}

	public GoodleSession getSessionByLogin(String login) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String query = "select from " + GoodleSession.class.getName()
				+ "where login == " + login;
		List<GoodleSession> list = (List<GoodleSession>) pm.newQuery(query)
				.execute();
		if (list != null) {
			return list.get(0);
		} else
			return null;
	}

	public Key getSessionUser(Long id) {
		PersistenceManager pm = getPersistenceManagerFactory()
		.getPersistenceManager();
String query = "select from " + GoodleSession.class.getName()
		+ "where id == " + id;
List<GoodleSession> list = (List<GoodleSession>) pm.newQuery(query)
		.execute();
if (list != null) {
	return list.get(0).getUser();
} else
	return null;
	}

}
