package edu.goodle.prototype;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/*
 * To execute this script you have to run selenium server first:
 * 
 * java -jar war/WEB-INF/lib/selenium-server-standalone-2.16.1.jar -firefoxProfileTemplate ~/.mozilla/firefox/mp41h6ve.default
 * where ~/.mozilla/firefox/mp41h6ve.default is your real firefox profile directory
 */
public class TestLogin extends TestCase {
	private Selenium selenium;

	@Before
	private void runSelenium() {
		String seleniumCommand = "java -jar war/WEB-INF/lib/selenium-server-standalone-2.16.1.jar -firefoxProfileTemplate ";
		String firefoxProfileDir = "/home/students/bioinf/j/jh280468/.mozilla/firefox";

		File dir = new File(firefoxProfileDir);
		System.out.println(firefoxProfileDir);
		if (dir == null || dir.list() == null) {
			System.out.println("Firefox profile file not found. "
					+ "Check paths in TestLogin.java");
		} else {
			String firefoxFileName = dir.listFiles()[0].getPath();
			
			try {
				Runtime.getRuntime().exec(seleniumCommand + firefoxFileName);
				System.out.println(seleniumCommand + firefoxFileName);
				Thread.sleep(10000);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setUp() throws Exception {
		runSelenium(); //TODO
				
		selenium = new DefaultSelenium("localhost", 4444, "*chrome",
				"http://goodleprototype.appspot.com/");
		selenium.start();
	}

	@Test
	public void testLogin() throws Exception {
		selenium.open("/");
		for (int second = 0;; second++) {
			if (second >= 10)
				fail("timeout");
			try {
				if (selenium.isTextPresent("LoginHasłoOK"))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		selenium.type("css=input.gwt-TextBox", "test");
		selenium.type("css=input.gwt-PasswordTextBox", "test");
		selenium.click("id=loginButton");
		for (int second = 0;; second++) {
			if (second >= 10)
				fail("timeout");
			try {
				if (selenium.isTextPresent("Wyloguj"))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}