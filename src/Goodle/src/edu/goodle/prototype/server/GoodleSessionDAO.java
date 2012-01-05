package edu.goodle.prototype.server;

import java.util.List;

import com.google.appengine.api.datastore.Key;

public interface GoodleSessionDAO {
    void addGoodleSession(GoodleSession session);
    void removeGoodleSession(GoodleSession session);
    void updateGoodleSession(GoodleSession session);
    List<GoodleSession> listGoodleSession();
    Key getSessionUser(Long id);
}
