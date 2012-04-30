package main.server.usosapi;

import java.lang.reflect.Type;

import main.shared.LongCourseDesc;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class LongCourseDeserializer implements JsonDeserializer<LongCourseDesc> 
{
	@Override
	public LongCourseDesc deserialize
	(
			JsonElement jsonElement, 
			Type typeOfT,
			JsonDeserializationContext context
	) 
	throws JsonParseException 
	{
		LongCourseDesc result = new LongCourseDesc();
		JsonObject json = jsonElement.getAsJsonObject();

		result.setId(getString(json, "id"));
		result.setName(getPolishString(json, "name"));
		result.setHomepageUrl(getString(json, "homepage_url"));
		result.setProfileUrl(getString(json, "profile_url"));
		result.setIsConducted(getBoolean(json, "is_currently_conducted"));
		result.setDesc(getPolishString(json, "description"));
		result.setBibliography(getPolishString(json, "bibliography"));		
		return result;
	}
	
	private String getPolishValue(JsonObject json) throws JsonParseException 
	{
		if (json.has("pl")) 
		{
			return json.get("pl").getAsString(); 
		}
		throw new JsonParseException(parseExceptionMsg("pl", json));
	}
	
	private String getString(JsonObject json, String key)
	{
		if (json.has(key))
		{
			return !json.get(key).isJsonNull() ? json.get(key).getAsString() : null;		
		} 
		throw new JsonParseException(parseExceptionMsg(key, json));	
	}
	
	private String getPolishString(JsonObject json, String key)
	{
		if (json.has(key))
		{
			return getPolishValue(json.get(key).getAsJsonObject());		 
		}
		throw new JsonParseException(parseExceptionMsg(key, json));
	}
	
	private Boolean getBoolean(JsonObject json, String key)
	{
		if (json.has(key)) 
		{
			return json.get(key).getAsBoolean();	
		}
		throw new JsonParseException(parseExceptionMsg(key, json));
	}

	private String parseExceptionMsg(String param, JsonObject json)
	{
		return "No member: \"" + param + "\" in " + json.getAsString();
	}

}
