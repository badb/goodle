package edu.goodle.prototype.server;

import java.util.List;

<<<<<<< HEAD
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
=======
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
>>>>>>> db

import com.google.appengine.api.datastore.Key;

import edu.goodle.prototype.server.GoodleSessionDAO;
import edu.goodle.prototype.shared.EMF;

public class GoodleSessionDAOImpl implements GoodleSessionDAO {
        private static final EntityManagerFactory emfInstance = EMF.get();

        public static EntityManagerFactory createEntityManagerFactory() {
                return emfInstance;
        }

        @Override
        public void addGoodleSession(GoodleSession session) {
                EntityManager em = createEntityManagerFactory()
                                .createEntityManager();
                try {
                        em.persist(session);
                } finally {
                        em.close();
                }
        }

        @SuppressWarnings("unchecked")
        @Override
        public List<GoodleSession> listGoodleSession() {
                EntityManager em = createEntityManagerFactory()
                                .createEntityManager();
                String query = "select from " + GoodleSession.class.getName();
                return (List<GoodleSession>) em.createQuery(query).getResultList();
        }

        @Override
        public void removeGoodleSession(GoodleSession session) {
                EntityManager em = createEntityManagerFactory()
                                .createEntityManager();
                try {
                        em.getTransaction().begin();

                        session = em.find(GoodleSession.class, session.getId());
                        em.remove(session);

                        em.getTransaction().commit();
                } catch (Exception ex) {
                        em.getTransaction().rollback();
                        throw new RuntimeException(ex);
                } finally {
                        em.close();
                }
        }

        @Override
        public void updateGoodleSession(GoodleSession session) {
                EntityManager em = createEntityManagerFactory()
                                .createEntityManager();

                try {
                        em.getTransaction().begin();
                        GoodleSession dbSession = em.find(GoodleSession.class, session.getId());
                        dbSession.setUser(session.getUser());
                        em.persist(dbSession);
                        em.getTransaction().commit();
                } catch (Exception ex) {
                        em.getTransaction().rollback();
                        throw new RuntimeException(ex);
                } finally {
                        em.close();
                }

        }

        public GoodleSession getSessionByLogin(String login) {
                EntityManager em = createEntityManagerFactory()
                                .createEntityManager();
                String query = "select from " + GoodleSession.class.getName()
                                + "where login == " + login;
                List<GoodleSession> list = (List<GoodleSession>) em.createQuery(query)
                                .getResultList();
                if (list != null) {
                        return list.get(0);
                } else
                        return null;
        }

        public Key getSessionUser(Long id) {
                EntityManager em = createEntityManagerFactory()
                .createEntityManager();
String query = "select from " + GoodleSession.class.getName()
                + "where id == " + id;
List<GoodleSession> list = (List<GoodleSession>) em.createQuery(query).getResultList();
if (list != null) {
        return list.get(0).getUser();
} else
        return null;
        }

}
