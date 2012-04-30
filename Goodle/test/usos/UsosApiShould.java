package usos;

import static org.junit.Assert.assertEquals;
import main.server.domain.GoodleUser;
import main.server.usosapi.UsosApiService;
import main.shared.usos.UsosResponseStatus;
import main.shared.usos.UsosSearchCourseResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class UsosApiShould {
	UsosApiService service = null;
	GoodleUser userInDB = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		service = new UsosApiService();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		service = null;
	}

	@Test
 	public void searchCourseByID() { 		
 		UsosSearchCourseResponse r = service.getCourseByID("1000-212bMD");
 		
 		assertEquals(UsosResponseStatus.OK, r.getStatus());
 			
 	}

}
