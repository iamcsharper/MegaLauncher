package net.xupoh.megalauncher.operations;

import java.util.List;

public interface ProccessActionListener {
	void onProccessStart();
	void onProccessEnd();
	void onProcessing(DownloadEvent ev);
	void onProccessEnd(List result);
}
