package main.client.mapper;

import main.client.ClientFactory;
import main.client.activity.CourseActivity;

import main.client.activity.CourseModulesEditActivity;
import main.client.activity.FindCoursesByNameActivity;
import main.client.activity.UserMainPageActivity;
import main.client.place.CourseModulesEditPlace;

import main.client.place.CoursePlace;
import main.client.place.FindCoursesByNamePlace;
import main.client.place.UserMainPagePlace;

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
		else if (place instanceof FindCoursesByNamePlace) 
		{
			return new FindCoursesByNameActivity(clientFactory, (FindCoursesByNamePlace) place);
		}
		else if (place instanceof UserMainPagePlace)
		{
			return new UserMainPageActivity(clientFactory, (UserMainPagePlace) place);
		}
		else if (place instanceof CourseModulesEditPlace)
		{
			return new CourseModulesEditActivity(clientFactory, (CourseModulesEditPlace) place);
		}
		return null;
	}

}
