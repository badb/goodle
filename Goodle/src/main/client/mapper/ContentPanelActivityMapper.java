package main.client.mapper;

import main.client.ClientFactory;
import main.client.activity.CourseActivity;
import main.client.activity.CreateCourseActivity;
import main.client.activity.FindCoursesByNameActivity;
import main.client.place.CoursePlace;
import main.client.place.CreateCoursePlace;
import main.client.place.FindCoursesByNamePlace;

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
		else if (place instanceof FindCoursesByNamePlace) 
		{
			return new FindCoursesByNameActivity(clientFactory, (FindCoursesByNamePlace) place);
		}
		return null;
	}
	

}
