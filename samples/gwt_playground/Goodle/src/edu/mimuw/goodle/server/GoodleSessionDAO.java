package edu.mimuw.goodle.server;

import java.util.List;

import edu.mimuw.goodle.client.GoodleSession;
import edu.mimuw.goodle.client.GoodleUser;

public interface GoodleSessionDAO {
	void addGoodleSession(GoodleSession session);
	void removeGoodleSession(GoodleSession session);
	void updateGoodleSession(GoodleSession session);
	List<GoodleSession> listGoodleSession();
	GoodleUser getSessionUser(Long id);
}
