package net.xupoh.megalauncher.utils;

import java.util.ArrayList;
import java.util.List;

public class Client {
	protected String name;
	protected String ip;	
	protected List<DownloadFileInfo> extraction = new ArrayList<>();
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public List<DownloadFileInfo> getExtraction() {
		return extraction;
	}
	public void setExtraction(List<DownloadFileInfo> extraction) {
		this.extraction = extraction;
	}
}
