package net.xupoh.megalauncher.operations;

import java.io.File;

public class DownloadEvent {
	File pathOnClient;
	float percents;

	public DownloadEvent(File pathOnClient, float percents) {
		this.pathOnClient = pathOnClient;
		this.percents = percents;
	}

	public File getCurrentFile() {
		return pathOnClient;
	}

	public void getCurrentFile(File pathOnClient) {
		this.pathOnClient = pathOnClient;
	}

	public float getPercents() {
		return percents;
	}

	public void setPercents(float percents) {
		this.percents = percents;
	}
}
