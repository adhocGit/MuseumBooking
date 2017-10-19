package com.museumbooking.volleyservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.museumbooking.model.MuseumModel;
import com.museumbooking.model.RegisterModel;

import static com.museumbooking.volleyservice.ParserException.HTTP_ERROR_503;
import static com.museumbooking.volleyservice.ParserException.SERVER_UNDER_MAINTENANCE;
public class JsonParser {
	
	
	public static <T extends MuseumModel> T mapObjectFromJson(
			String jsonResponseString, Class<T> clazz,
			String additionalRequestInformation) throws ParserException {

		T t = null;
		/*ResponseMessageBean responseMessageBean = null;
		FaultMessageBean faultMessageBean = null;*/

		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		try {
		
				if (jsonResponseString != null) {
					t = gson.fromJson(jsonResponseString, clazz);
				}
			
		} catch (JsonSyntaxException jsonSyntaxException) {
			try{
			jsonResponseString="{\"array\":"+jsonResponseString+"}";
			t = gson.fromJson(jsonResponseString, clazz);
			}
			catch(JsonSyntaxException jsonSyntaxExcep)
			{
				throw new ParserException("JSE", "JsonSyntaxException");
			}
		} 
		if (t == null) {
			throw new ParserException(HTTP_ERROR_503,SERVER_UNDER_MAINTENANCE);
		}
		return t;
	}
}
