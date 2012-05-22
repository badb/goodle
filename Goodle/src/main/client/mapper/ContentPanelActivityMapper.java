package main.client.mapper;

import main.client.ClientFactory;
import main.client.activity.CourseActivity;
import main.client.activity.CreateCourseActivity;
import main.client.activity.CreateCourseImportActivity;
import main.client.activity.FindCoursesByNameActivity;
import main.client.activity.UserMainPageActivity;
import main.client.activity.UserProfileActivity;
import main.client.place.CoursePlace;
import main.client.place.CreateCourseImportPlace;
import main.client.place.CreateCoursePlace;
import main.client.place.FindCoursesByNamePlace;
import main.client.place.UserMainPagePlace;
import main.client.place.UserProfilePlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class ContentPanelActivityMapper implements ActivityMapper
{
	private ClientFactory clientFactory;
	
	public ContentPanelActivityMapper(ClientFactory clientFactory)
	{
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) 
	{
		if (place instanceof CoursePlace) 
		{
			return new CourseActivity(clientFactory, (CoursePlace) place);
		}
		else if (place instanceof CreateCoursePlace) 
		{
			return new CreateCourseActivity(clientFactory);
		}
		else if (place instanceof CreateCourseImportPlace)
		{
			return new CreateCourseImportActivity(clientFactory);
		}
		else if (place instanceof FindCoursesByNamePlace) 
		{
			return new FindCoursesByNameActivity(clientFactory, (FindCoursesByNamePlace) place);
		}
		else if (place instanceof UserMainPagePlace)
		{
			return new UserMainPageActivity(clientFactory, (UserMainPagePlace) place);
		}
		else if (place instanceof UserProfilePlace)
		{
			return new UserProfileActivity(clientFactory, (UserProfilePlace) place);
		}
		return null;
	}

}
