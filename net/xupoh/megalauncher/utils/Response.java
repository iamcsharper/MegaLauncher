package net.xupoh.megalauncher.utils;

public class Response {
	protected boolean result;
	protected String message, client, session;
	
	public boolean isOk() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getClientName() {
		return client;
	}
	
	public String getSession() {
		return this.session;
	}
	
	public void setSession(String s) {
		this.session = s;
	}
}
