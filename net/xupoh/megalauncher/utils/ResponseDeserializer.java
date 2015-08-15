package net.xupoh.megalauncher.utils;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ResponseDeserializer implements JsonDeserializer<Response> {

	@Override
	public Response deserialize(final JsonElement json, final Type typeOfT,
			final JsonDeserializationContext context) throws JsonParseException {

		final JsonObject jsonObject = json.getAsJsonObject();
		final boolean result = jsonObject.get("result").getAsBoolean();
		String message = "";
		try {
			message = jsonObject.get("message").getAsString();
		} catch (Exception e) {}
		
		String session = "";
		try {
			session = jsonObject.get("session").getAsString();
		} catch(Exception e) {}

		final Response response = new Response();
		response.setResult(result);
		response.setMessage(message);
		response.setSession(session);
		
		return response;
	}
}