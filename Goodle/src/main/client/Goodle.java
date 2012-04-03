package main.client;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import main.client.panels.CoursePanel;
import main.client.panels.CreateCoursePanel;
import main.client.panels.EmptyPanel;
import main.client.panels.GoodlePanel;
import main.client.panels.LeftPanel;
import main.client.panels.ResultListPanel;
import main.client.panels.RightPanel;
import main.client.panels.TopPanel;
import main.client.services.ServicesManager;
import main.client.utils.CourseShortDesc;
import main.server.DbApi;
import main.shared.models.Course;
import main.shared.models.DataModificationFailedException;
import main.shared.models.GoodleUser;

import com.google.appengine.api.datastore.Text;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class Goodle implements EntryPoint, ValueChangeHandler<String> {
	private ServicesManager manager = new ServicesManager(this);
	private VerticalPanel mainPanel = new VerticalPanel();
	private TopPanel topPanel = new TopPanel(manager);
	private HorizontalPanel contentPanel = new HorizontalPanel();
	private LeftPanel leftPanel = new LeftPanel(manager);
	private GoodlePanel middlePanel;
	private RightPanel rightPanel = new RightPanel(manager);
	
	private Label actionLabel = new Label("");
	
/*	private LoginPanel lp = new LoginPanel(manager);
	private MainStudentPanel mp = new MainStudentPanel(manager, this);
	private SearchPanel sp = new SearchPanel(manager, this);
	private UserNavPanel up = new UserNavPanel(manager, this); 
	private NavPathPanel np = new NavPathPanel(manager, this);
	private CourseInfoPanel cip = new CourseInfoPanel(manager, this);
	private CoursePanel cp;
	private CourseListPanel clp = new CourseListPanel(manager, this);
	private MainCoursePanel mcp = new MainCoursePanel(manager, this);
	private RegisterPanel rp = new RegisterPanel(manager, this); */

	

	private static Logger logger = Logger.getLogger("");
	//private String initToken = History.getToken();
	
	public void onModuleLoad() 
	{
		mainPanel.getElement().setId("mainPanel");
		topPanel.getElement().setId("topPanel");
		leftPanel.getElement().setId("leftPanel");
		middlePanel.getElement().setId("middlePanel");
		rightPanel.getElement().setId("rightPanel");
		
		
		middlePanel = new EmptyPanel(manager);
		
		contentPanel.add(leftPanel);
		contentPanel.add(middlePanel);
		contentPanel.add(rightPanel);
		
		mainPanel.add(topPanel);
		mainPanel.add(contentPanel);
		
		mainPanel.add(actionLabel);
		
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
	
	public void createCourse() { loadContent(new CreateCoursePanel(manager)); }
	
	public void showCoursesFound(Collection<CourseShortDesc> courses)
	{
		actionLabel.setText("Courses found");
		List<CourseShortDesc> list = (List<CourseShortDesc>) courses;
		loadContent(new ResultListPanel(manager, list));
		//Logger.getLogger("").severe("Courses found.");
		//middlePanel = new ResultListPanel(manager);
	}
	
	public void actionFailed() { actionLabel.setText("Operacja nie powiodła się"); }
	
	public void loadContent(Widget w)
	{
		contentPanel.remove(1);
		contentPanel.insert(w, 1);
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
		cp = new CoursePanel(manager, this, course);
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
