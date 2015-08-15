package net.xupoh.megalauncher.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadFileInfo {
	private URL pathOnServer;
	private File pathOnClient;
	private File extractionPath;

	public DownloadFileInfo(String s, String c, String e)
			throws MalformedURLException {
		this.pathOnServer = new URL(s);
		this.setRelativeOnClient(c);
		this.extractionPath = new File(FileUtils.getWorkingDirectory().getAbsolutePath() + "/" + e);
	}
	
	public DownloadFileInfo () {}

	public URL getPathOnServer() {
		return pathOnServer;
	}

	public void setPathOnServer(URL pathOnServer) {
		this.pathOnServer = pathOnServer;
	}

	public File getPathOnClient() {
		return pathOnClient;
	}

	public void setRelativeOnClient(String path) {
		this.pathOnClient = new File(FileUtils.getWorkingDirectory()
				.getAbsolutePath() + "/" + path);
	}

	public void setPathOnClient(File pathOnClient) {
		this.pathOnClient = pathOnClient;
	}

	public File getExtractionPath() {
		return extractionPath;
	}

	public void setExtractionPath(File extractionPath) {
		this.extractionPath = extractionPath;
	}

}
