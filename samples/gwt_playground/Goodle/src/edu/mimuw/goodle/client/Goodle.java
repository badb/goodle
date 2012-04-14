package edu.mimuw.goodle.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.logging.client.ConsoleLogHandler;
import com.google.gwt.logging.client.FirebugLogHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Goodle implements EntryPoint {

	private static final String JSON_URL =
		"http://apps.usos.edu.pl/services/courses/classtypes_index?format=json";
	private VerticalPanel mainPanel = new VerticalPanel();
	private VerticalPanel coursesPanel = new VerticalPanel();
	private HorizontalPanel searchPanel = new HorizontalPanel();
	private TextBox searchTextBox = new TextBox();
	private Button searchButton = new Button("search");
	private ArrayList<Course> courses = new ArrayList<Course>();
	private Label errorMsgLabel = new Label();
	
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	private String sessionID = null;
	private Button authButton = new Button("pincode auth button");
	private Button getCoursesButton = new Button("getCourses Button");
	private Button loginButton = new Button("login Button");
	private Button logoutButton = new Button("logout Button");
	private TextBox userNameBox = new TextBox();
	private PasswordTextBox passwdBox = new PasswordTextBox();
	private Button getAllUsers = new Button("list users");
	private final GoodleServiceAsync goodleService = GWT.create(GoodleService.class);
	private Logger logger = Logger.getLogger("");
	

	

	public void onModuleLoad() {
		logger.addHandler(new FirebugLogHandler());
		mainPanel.add(authButton);
		mainPanel.add(getCoursesButton);
		mainPanel.add(loginButton);
		mainPanel.add(logoutButton);
		mainPanel.add(userNameBox);
		mainPanel.add(passwdBox);
		mainPanel.add(getAllUsers);
		RootPanel.get("courseList").add(mainPanel);
		
		authButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				goodleService.authorisePinCode(sessionID, "pinpin", new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						logger.severe("PinCodeRequest failed.");
					}
					public void onSuccess(Boolean result) {
						logger.info("PinCodeAuth returned:" + result);
					}
				});
			}
		});
		
		loginButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				goodleService.loginUser(userNameBox.getText(), 
						passwdBox.getText(), new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						logger.severe("Login command failed.");
					}
					public void onSuccess(String result) {
						logger.info("SessionID: "+result);
					}
				});
			}
		});
		

		/* TODO: pobranie listy przedmiotow. Do zastapienia Z1*/
		Course c = new Course("P1", "pp", "pe");
		courses.add(c);
		Course c2 = new Course("P2", "pp2", "pe2");
		courses.add(c2);
		/* END: Z1 */

		getCourses();
		for (Iterator <Course> i = courses.iterator(); i.hasNext(); ) {
			Course course = i.next();
			Hyperlink l = new Hyperlink(course.getNamePL(), course.getShortcut());
			coursesPanel.add(l);
		}

		mainPanel.add(errorMsgLabel);
		mainPanel.add(searchPanel);
		mainPanel.add(coursesPanel);

		

		searchButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				searchForCourse();
			}
		});

		searchTextBox.addKeyPressHandler(new KeyPressHandler () {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					searchForCourse();
				}
			}
		});
	}

	/* TODO: tresc do zmiany */
	private void searchForCourse () {
		final String symbol = searchTextBox.getText().toUpperCase().trim();
		searchTextBox.setFocus(true);

		if (!symbol.matches("^[0-9A-Z\\.]{1,10}$")) {
			Window.alert("'" + symbol + "' is not a valid symbol.");
			searchTextBox.selectAll();
			return;
		}

		searchTextBox.setText("");
	}

// 	private final native JsArray<CourseData> asArrayOfCourseData (String json) {
// 		return eval(json);
// 	}

	private void displayError(String error) {
		errorMsgLabel.setText("Error: " + error);
		errorMsgLabel.setVisible(true);
	}

	private void getCourses () {
		greetingService.greetServer("aaaaa", new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				displayError("" + caught.getMessage());
				
			}

			public void onSuccess(String result) {
				displayError(result);
			}
		});
		/*
		RequestBuilder builder =
			new RequestBuilder(RequestBuilder.GET, JSON_URL);

		try {
			Request request = builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					displayError("Couldn't retrive JSON (onError)");
				}

				public void onResponseReceived(Request request, Response response) {
					 if (200 == response.getStatusCode()) {
						displayError("" + response.getStatusCode());
					} else {
						displayError(response.getStatusCode() + "ERROR" );
					}
// 					updateCourseList(asArrayOfCourseData(response.getText()));
				}
			});
		} catch (RequestException e) {
			displayError("Coudn't retrive JSON (exception)");
		}*/
	}
}