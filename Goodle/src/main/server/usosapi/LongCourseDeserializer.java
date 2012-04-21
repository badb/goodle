package main.server.usosapi;

import java.lang.reflect.Type;

import main.shared.LongCourseDescription;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;


public class LongCourseDeserializer implements JsonDeserializer<LongCourseDescription> {
	private String getPolishValue(JsonObject json) throws JsonParseException {
		if(json.has("pl")){
			return json.get("pl").getAsString();
		} else {
			throw new JsonParseException("No member: \"pl\" in " + json.getAsString());
		}
	}
	private String getString(JsonObject json, String key){
		if(json.has(key)){
			if(json.get(key).isJsonNull()){
				return null;
			}
			return json.get(key).getAsString();
		} else {
			throw new JsonParseException("No member: \""+ key +"\" in " + json.getAsString());
		}
	}
	private String getPolishString(JsonObject json, String key){
		if(json.has(key)){
			return getPolishValue(json.get(key).getAsJsonObject());
		} else {
			throw new JsonParseException("No member: \""+ key +"\" in " + json.getAsString());
		}
	}
	private Boolean getBoolean(JsonObject json, String key){
		if(json.has(key)){
			return json.get(key).getAsBoolean();
		} else {
			throw new JsonParseException("No member: \""+ key +"\" in " + json.getAsString());
		}
	}
	@Override
	public LongCourseDescription deserialize(JsonElement arg0, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		LongCourseDescription toRet = new LongCourseDescription();
		JsonObject json = arg0.getAsJsonObject();
		
		toRet.setId(getString(json, "id"));
		toRet.setName(getPolishString(json, "name"));
		toRet.setHomepage_url(getString(json, "homepage_url"));
		toRet.setProfile_url(getString(json, "profile_url"));
		toRet.setIs_currently_conducted(getBoolean(json, "is_currently_conducted"));
		toRet.setDescription(getPolishString(json, "description"));
		toRet.setBibliography(getPolishString(json, "bibliography"));		
		return toRet;
	}

}
