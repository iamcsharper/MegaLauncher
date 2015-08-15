/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.xupoh.megalauncher.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.xupoh.megalauncher.main.Settings;
import net.xupoh.megalauncher.main.Starter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author Админ
 */
public class WebHelper {
	static String[] allClients = new String[0];

	class Clients {
		public String[] clients;

		public String[] getClients() {
			return clients;
		}
	}

	public static String sendPost(String url, Map<String, String> urlParameters)
			throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "MegaLauncher VERSION "
				+ Settings.version);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Accept-Charset", "UTF-8");
		con.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		con.setDoOutput(true);

		DataOutputStream wr = new DataOutputStream(con.getOutputStream());

		StringBuilder params = new StringBuilder();
		int j = 0;
		for (Entry<String, String> i : urlParameters.entrySet()) {
			params.append(i.getKey() + "="
					+ URLEncoder.encode(i.getValue(), "UTF-8"));
			if (j != urlParameters.size() - 1) {
				params.append("&");
			}
			++j;
		}

		wr.writeBytes(params.toString());
		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}

	public static String[] getAllClients() throws Exception {
		if (allClients.length > 0)
			return allClients;

		Gson gson = new Gson();

		Map<String, String> params = new HashMap<String, String>();
		params.put("action", "getClientNames");
		String json = WebHelper.sendPost(Settings.site_root + "action.php",
				params);

		return gson.fromJson(json, Clients.class).getClients();
	}

	public static Client parseClient(String name) throws Exception {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Client.class, new ClientDeserializer());
		Gson gson = gsonBuilder.create();

		Map<String, String> params = new HashMap<String, String>();
		params.put("action", "getClientInfo");
		params.put("clientName", name);
		String json = WebHelper.sendPost(Settings.site_root + "action.php",
				params);

		return gson.fromJson(json, Client.class);
	}

	public static Response sendToSite(Map<String, String> params)
			throws Exception {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Response.class,
				new ResponseDeserializer());
		Gson gson = gsonBuilder.create();

		String json = WebHelper.sendPost(Settings.site_root + "action.php",
				params);
		
		try {
			return gson.fromJson(json, Response.class);
		} catch (Exception e) {
			Starter.log("Invalid data from site: " + json);
			
			Response r = new Response();
			r.setResult(false);
			r.setMessage("Invalid data from site: " + json + "\nСообщите это администрации!");
			return r;
		}
	}

	public static boolean checkFile(String client, String fileName, String md5) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("action", "checkHashsum");
		params.put("client", client);
		params.put("file", fileName);
		params.put("md5", md5);

		return sendToSite(params).isOk();
	}
}
