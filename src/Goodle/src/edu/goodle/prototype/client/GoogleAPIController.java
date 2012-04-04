package edu.goodle.prototype.client;

import java.util.Date;
import java.util.logging.Logger;

import com.google.api.gwt.client.impl.ClientGoogleApiRequestTransport;
import com.google.api.gwt.client.impl.ClientOAuth2Login;
import com.google.api.gwt.oauth2.client.Auth;
import com.google.api.gwt.oauth2.client.AuthRequest;
import com.google.api.gwt.oauth2.client.Callback;
import com.google.api.gwt.services.calendar.shared.Calendar;
import com.google.api.gwt.services.calendar.shared.Calendar.CalendarAuthScope;
import com.google.api.gwt.services.calendar.shared.model.Event;
import com.google.api.gwt.services.calendar.shared.model.Events;
import com.google.api.gwt.shared.GoogleApiRequestTransport;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

@SuppressWarnings("deprecation")
public class GoogleAPIController {
	private Goodle goodle;
	private static Logger logger = Logger.getLogger("");
	private final Calendar calendar = GWT.create(Calendar.class);
	
	private static final String AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
	private static final String CLIENT_ID = "625550383194.apps.googleusercontent.com"; // available from the APIs console
	private static final String CALENDAR_READONLY_SCOPE = "https://www.googleapis.com/auth/calendar.readonly";
	private static final String API_KEY = "AIzaSyChon5JQ11ztVR4NjYWyYWnm6cVD1xmQM4";
	private static final String APPLICATION_NAME = "goodleprototype";
	
	
	public GoogleAPIController(Goodle goodle) { this.goodle = goodle;}
	
	AuthRequest req = new AuthRequest(AUTH_URL, CLIENT_ID)
	    .withScopes(CALENDAR_READONLY_SCOPE); // Can specify multiple scopes here
	
	
	/*public void login() {
		Auth.get().login(req, new Callback<String, Throwable>() {
			  @Override
			  public void onSuccess(String token) {
				  logger.info("login ok, token: " + token);
				  initialize(token);
			  }
			  @Override
			  public void onFailure(Throwable caught) {
				  logger.severe("login failed, caught: " + caught);
			  }
		});
	} */
	
	 public void login() {
		    final Button button = new Button("Log in to get started");
		    button.addClickHandler(new ClickHandler() {
		      @Override
		      public void onClick(ClickEvent event) {
		        new ClientOAuth2Login(CLIENT_ID)
		            .withScopes(CalendarAuthScope.CALENDAR_READONLY)
		            .login(new Receiver<String>() {
		              @Override
		              public void onSuccess(String accessToken) {
		                initialize(accessToken);
		              }
		            });
		        button.setVisible(false);
		      }
		    });
		    RootPanel.get().add(button);
		  }
	
	  public void initialize(final String accessToken) {
		    new ClientGoogleApiRequestTransport()
		        .setApiAccessKey(API_KEY)
		        .setApplicationName(APPLICATION_NAME)
		        .setAccessToken(accessToken)
		        .create(new Receiver<GoogleApiRequestTransport>() {
		          @Override
		          public void onSuccess(GoogleApiRequestTransport transport) {
		            SimpleEventBus eventBus = new SimpleEventBus();
		            calendar.initialize(eventBus, transport);
		            logger.info("DZIALA, przed calendarID");
		            String calendarId = Window.prompt("Provide a Calendar ID (try your email address)", "");
		            listEvents(calendarId);
		            logger.info("DZIALA, po calendarID");
		          }

		          @Override
		          public void onFailure(ServerFailure failure) {
		            Window.alert("Failed to initialize transport");
		          }
		        });
		  }
	  
	  private String today() {
		    String today = DateTimeFormat.getFormat("yyyy-MM-dd'T'00:00:00-00:00").format(new Date());
		    println("Today is " + today);
		    return today;
		  }

	  
	  public void listEvents(String calendarId) {
		  logger.info("listEvents start");
		    calendar.events().list(calendarId)
		        .setTimeMin(today())
		        .to(new Receiver<Events>() {
		      @Override
		      public void onSuccess(Events events) {
		    	  logger.info("listEvents ok");
		        println("=== UPCOMING EVENTS ===");
		        for (Event event : events.getItems()) {
		          printLink(event.getSummary(), event.getHtmlLink());
		        }
		      }
		      public void onFailure(Throwable error) {
		    	 logger.severe("listEvents failed" + error);
		      }
		    }).fire();
		    logger.info("listEvents end");
		  }
	  
	  private void println(String msg) {
		    RootPanel.get().add(new Label(msg));
		  }

		  private void printLink(String text, String url) {
		    RootPanel.get().add(new Anchor(text, url));
		  }
}

