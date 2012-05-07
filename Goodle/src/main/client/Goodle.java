package main.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import main.client.mapper.AppPlaceHistoryMapper;
import main.client.mapper.ContentPanelActivityMapper;
import main.client.place.CreateCoursePlace;
import main.client.ui.TopView;
import main.client.ui.UserCoursesView;
import main.client.ui.CalendarView;
import main.shared.proxy.CourseProxy;
import main.shared.proxy.GoodleUserProxy;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


public class Goodle implements EntryPoint, ValueChangeHandler<String> {
	
	interface Binder extends UiBinder<DockLayoutPanel, Goodle> { }
    
    private static final Binder binder = GWT.create(Binder.class);
    
    @UiField TopView topPanel;
    @UiField UserCoursesView leftPanel;
    @UiField SimpleLayoutPanel contentPanel;
    @UiField CalendarView rightPanel;
    
    private Place defaultPlace = new CreateCoursePlace();
    private ClientFactory clientFactory = null;

	public void onModuleLoad() 
	{		
		clientFactory = GWT.create(ClientFactory.class);		
		clientFactory.initializeRequestFactory();		
		clientFactory.getRequestFactory().goodleUserRequest().getCurrentUser().fire(
				new Receiver<GoodleUserProxy>()
				{
					@Override
					public void onSuccess(GoodleUserProxy response)
					{
						if (response != null){
							clientFactory.setCurrentUser(response);
							whenUserLogged();
						} else {
							clientFactory.getRequestFactory().goodleUserRequest().getLoginUrl(Window.Location.getHref()).fire(
									new Receiver <String>(){
										@Override
										public void onSuccess(String response)
											{Window.Location.assign(response);}
									}
							);							
						}						
					}
					@Override
					public void onFailure(ServerFailure error){
						Logger logger = Logger.getLogger("Goodle.Log");
					    logger.log(Level.SEVERE, error.getMessage());
					    logger.log(Level.SEVERE, error.getStackTraceString());
					    logger.log(Level.SEVERE, error.getExceptionType());
					}
				});		
	}
	
	public void whenUserLogged(){
		DockLayoutPanel outer = binder.createAndBindUi(this);
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();
		
		ActivityMapper contentPanelActivityMapper = new ContentPanelActivityMapper(clientFactory);
		ActivityManager contentPanelActivityManager = new ActivityManager(contentPanelActivityMapper, eventBus);
		contentPanelActivityManager.setDisplay(contentPanel);

        AppPlaceHistoryMapper historyMapper= GWT.create(AppPlaceHistoryMapper.class);
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController, eventBus, defaultPlace);
        
        topPanel.setClientFactory(clientFactory);
        topPanel.setUserName(clientFactory.getCurrrentUser().getFirstName() + " " + clientFactory.getCurrrentUser().getLastName());
        leftPanel.setClientFactory(clientFactory);
        rightPanel.setClientFactory(clientFactory);
        
        RootLayoutPanel.get().add(outer);
        
        historyHandler.handleCurrentHistory();
	}
	
	/*
	
	public boolean checkSessionID(String sessionID) {
		return true;
	}
	
	public void clearPage() {
		RootPanel.get("tabs").clear();
		RootPanel.get("page").clear();
		np.clear();
		RootPanel.get("navpath").clear();
		RootPanel.get("info").clear();
		RootPanel.get("name").clear();
	}
	
	public void clearAll() {
		clearPage();
		RootPanel.get("search").clear();
		RootPanel.get("user_nav").clear();		
	}
	
	public void displayLoginBox() {
		clearAll();

		RootPanel.get("goodleLogin").add(lp.getPanel());
	    DOM.setElementAttribute(
	                DOM.getElementById("goodleLogin"), "style", "visibility:visible");
	}
	
	public void rememberLogin(String sessionID) {
		final long DURATION = 1000 * 60 * 30;
		Date expires = new Date(System.currentTimeMillis() + DURATION);
		Cookies.setCookie("sessionID", sessionID, expires, null, "/", false);
	}
	
	public void afterLogin(String result) {
		lp.afterLogin();
		rememberLogin(result);
        DOM.setElementAttribute(
                DOM.getElementById("goodleLogin"), "style", "visibility:hidden");
        sp.clearTextBox();
        showNavBar();
		showCourseList();
	}
	
	public void showNavBar() {
		RootPanel.get("search").add(sp.getPanel());
		RootPanel.get("user_nav").add(up.getPanel());
		RootPanel.get("navpath").add(np.getPanel());
	}
	
	public void logout () {
		Cookies.removeCookie("sessionID");
		displayLoginBox();
	}
	
	public void showCourseList() {
		RootPanel.get("tabs").add(mp.getPanel());
	}

	public String getSession() {
		String sessionID = Cookies.getCookie("sessionID");
		return sessionID;
	}
	
	public void changeToCourse(String course) {
		logger.info("chagneToCourseList");
		clearPage();
		cp = new CourseViewPanel(manager, this, course);
		manager.getCourseInfo(course);
		np.addNext(course);
		RootPanel.get("navpath").add(np.getPanel());
		RootPanel.get("name").add(new Label(course));
		RootPanel.get("tabs").add(mcp.getPanel());
		RootPanel.get("info").add(cip.getPanel());
	}
	
	public void addCoursePanels(String data) {
		logger.info("addCoursePanels");
		loadDataToCoursePanel(data);
	
	}
	
	public void addRegisterPanel() {
	   	RootPanel.get("page").add(rp.getPanel());
	}
	
	public void changeToCourseList(String text) {
		clearPage();
		showNavBar();
		RootPanel.get("tabs").add(mp.getPanel());
		mp.setNone();
		RootPanel.get("page").add(clp.getPanel(text));
	}

	public void searchForCourse(final String text) {
		manager.searchCourse(text);
	}
	*/

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		/*logger.info("History changed: "+ event.getValue());
		changeToCourse(event.getValue()); */
	}
	
    /*
	public void loadDataToCoursePanel(String data){
		cp.load(data);
		RootPanel.get("page").add(cp.getPanel());
	}
	
	public void loginFail() {
		lp.loginFail();
	} */
}
