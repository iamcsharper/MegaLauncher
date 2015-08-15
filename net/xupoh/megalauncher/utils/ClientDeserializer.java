package net.xupoh.megalauncher.utils;

import java.lang.reflect.Type;
import java.net.MalformedURLException;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ClientDeserializer implements JsonDeserializer<Client> {

	@Override
	public Client deserialize(final JsonElement json, final Type typeOfT,
			final JsonDeserializationContext context) throws JsonParseException {

		final JsonObject jsonObject = json.getAsJsonObject();
		final String name = jsonObject.get("name").getAsString();
		final String ip = jsonObject.get("ip").getAsString();
		final JsonArray files = jsonObject.getAsJsonArray("files");

		final Client client = new Client();

		client.setName(name);
		client.setIp(ip);
		
		for (JsonElement el : files) {
			//client.extraction.add(new DownloadFileInfo(el.));
			String serverUrl = el.getAsJsonObject().get("serverUrl").getAsString();
			String clientFile = el.getAsJsonObject().get("clientFile").getAsString();
			String clientExtractionDir = el.getAsJsonObject().get("clientExtractionDir").getAsString();
			
			try {
				client.getExtraction().add(new DownloadFileInfo(serverUrl, clientFile, clientExtractionDir));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		
		return client;
	}
}