package main.client;

import main.client.services.ServicesManager;
import main.client.widgets.ContentPanel;
import main.client.widgets.LeftPanel;
import main.client.widgets.TopPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class Goodle implements EntryPoint, ValueChangeHandler<String> {
	private SimpleEventBus eventBus = new SimpleEventBus();
	private ServicesManager manager = new ServicesManager(eventBus);
	private VerticalPanel mainPanel = new VerticalPanel();
	private TopPanel topPanel = new TopPanel(manager, eventBus);
	private HorizontalPanel bodyPanel = new HorizontalPanel();
	private LeftPanel leftPanel = new LeftPanel(manager, eventBus);
	private ContentPanel contentPanel = new ContentPanel(manager, eventBus);
	
	
/*	private LoginPanel lp = new LoginPanel(manager);
	private MainStudentPanel mp = new MainStudentPanel(manager, this);
	private SearchPanel sp = new SearchPanel(manager, this);
	private UserNavPanel up = new UserNavPanel(manager, this); 
	private NavPathPanel np = new NavPathPanel(manager, this);
	private CourseInfoPanel cip = new CourseInfoPanel(manager, this);
	private CourseViewPanel cp;
	private CourseListPanel clp = new CourseListPanel(manager, this);
	private MainCoursePanel mcp = new MainCoursePanel(manager, this);
	private RegisterPanel rp = new RegisterPanel(manager, this); */


	//private String initToken = History.getToken();
	
	public void onModuleLoad() 
	{				
		bodyPanel.add(leftPanel);
		bodyPanel.add(contentPanel);
		mainPanel.add(topPanel);
		mainPanel.add(bodyPanel);		
		RootPanel.get().add(mainPanel);
		
	/*	
	(	if (initToken.length() == 0) {
			History.newItem("main");
		}
		History.addValueChangeHandler(this);

        DOM.setElementAttribute(
                DOM.getElementById("goodleLogin"), "style", "visibility:hidden");
	    String sessionID = Cookies.getCookie("sessionID");
	    if ( sessionID != null && checkSessionID(sessionID)) {
	    	showNavBar();
	    	showCourseList();	   
	    	} else {
	    	displayLoginBox();
	    } */
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
