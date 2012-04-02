package main.shared;

import java.util.ArrayList;
import java.util.Collection;

import main.client.services.CourseService;
import main.client.services.CourseServiceAsync;
import main.shared.models.Course;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle;


public class AsyncSuggestOracle extends SuggestOracle
{
	private CourseServiceAsync courseService = GWT.create(CourseService.class);
	
	@Override
    public boolean isDisplayStringHTML() { return true; }

	@Override
    public void requestSuggestions(Request request, Callback callback) 
	{
		String name = request.getQuery();
		//courseService.findCoursesByName(name, new CourseSuggestCallback(request, callback));
    }

    class CourseSuggestCallback implements AsyncCallback<Collection<Course>> 
    {
        private Request request;
        private Callback callback;

        public CourseSuggestCallback(Request request, Callback callback) {
            this.request = request;
            this.callback = callback;
        }

        public void onFailure(Throwable caught) 
        {
            callback.onSuggestionsReady(request, new SuggestOracle.Response());
        }

        public void onSuccess(Collection<Course> result) 
        {
        	Collection<Suggestion> suggs = new ArrayList<Suggestion>();
        	Suggestion s = new MultiWordSuggestOracle.MultiWordSuggestion("aaa", "aaa");
        	suggs.add(s);
            callback.onSuggestionsReady(request, (SuggestOracle.Response) suggs);
        }
    }
}
