package main.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import main.client.mapper.AppPlaceHistoryMapper;
import main.client.mapper.ContentPanelActivityMapper;
import main.client.place.CreateCoursePlace;
import main.client.ui.CalendarView;
import main.client.ui.FooterView;
import main.client.ui.TopView;
import main.client.ui.UserCoursesView;
import main.shared.proxy.GoodleUserProxy;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


public class Goodle implements EntryPoint 
{
	
	interface Binder extends UiBinder<ScrollPanel, Goodle> { }
    
    private static final Binder binder = GWT.create(Binder.class);
    
    @UiField TopView topPanel;
    @UiField UserCoursesView leftPanel;
    @UiField SimplePanel contentPanel;
    @UiField CalendarView rightPanel;
            
    private Place defaultPlace = new CreateCoursePlace();
    private ClientFactory clientFactory = null;

	public void onModuleLoad() 
	{		
		clientFactory = GWT.create(ClientFactory.class);		
		clientFactory.initializeRequestFactory();		
		clientFactory.getRequestFactory().goodleUserRequest().getCurrentUser().fire
		(
			new Receiver<GoodleUserProxy>()
			{
				@Override
				public void onSuccess(GoodleUserProxy response)
				{
					clientFactory.setCurrentUser(response);
					whenUserLogged();
				}
				@Override
				public void onFailure(ServerFailure error)
				{
					Logger logger = Logger.getLogger("Goodle.Log");
					logger.log(Level.SEVERE, error.getMessage());
					logger.log(Level.SEVERE, error.getStackTraceString());
					logger.log(Level.SEVERE, error.getExceptionType());
				}
		});		
	}
	
	public void whenUserLogged()
	{
		ScrollPanel outer = binder.createAndBindUi(this);
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();
		
		ActivityMapper contentPanelActivityMapper = new ContentPanelActivityMapper(clientFactory);
		ActivityManager contentPanelActivityManager = new ActivityManager(contentPanelActivityMapper, eventBus);
		contentPanelActivityManager.setDisplay(contentPanel);

        AppPlaceHistoryMapper historyMapper= GWT.create(AppPlaceHistoryMapper.class);
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController, eventBus, defaultPlace);
        
        topPanel.setClientFactory(clientFactory);
        topPanel.setUserName(clientFactory.getCurrentUser().getLogin());
        leftPanel.setClientFactory(clientFactory);
        rightPanel.setClientFactory(clientFactory);
        
        RootLayoutPanel.get().add(outer);
        
        historyHandler.handleCurrentHistory();
	}
}
