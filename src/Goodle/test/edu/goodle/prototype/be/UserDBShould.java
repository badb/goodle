package edu.goodle.prototype.be;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import main.server.domain.DbApi;
import main.server.domain.GoodleUser;

import org.junit.Test;


public class UserDBShould {
	private DbApi dbApi;
	
	/*
	private void setUp() {
		uDAO = mock(GoodleUserDAO.class);
		sDAO = mock(GoodleSessionDAO.class);
		db = new UserDB(uDAO, sDAO);
	}
	
	private void tearDown() { dbApi = null; }
	
	@Test
	public void shouldNotLoginNotExistingUser() {
		setUp();
		when(uDAO.getUserByLogin("test")).thenReturn(null);
		
		String session = dbApi.loginUser("test","test");
		
		assertNull(session);
		tearDown();
	}

	
	@Test
	public void shouldLoginExistingUser() {
		setUp();
		GoodleUser testUser = new GoodleUser();
		testUser.setLogin("test");
		testUser.setPassword("test");
		when(uDAO.getUserByLogin("test")).thenReturn(testUser);
		
		String session = dbApi.loginUser("test","test");
		
		assertNotNull(session);
		tearDown();
	}
	
	@Test
	public void shouldNotLoginWithWrongPassword() {
		setUp();
		GoodleUser testUser = new GoodleUser();
		testUser.setLogin("test");
		testUser.setPassword("notTest");
		when(uDAO.getUserByLogin("test")).thenReturn(testUser);
		
		String session = db.loginUser("test","test");
		
		assertNull(session);
		tearDown();
	}	
	
	*/
}
