package net.xupoh.megalauncher.operations;

import javax.swing.SwingWorker;

import net.xupoh.megalauncher.server.MinecraftStatus;
import net.xupoh.megalauncher.server.QueryResponse;
import net.xupoh.megalauncher.ui.ServersComboBox;

public class ServerStatus extends SwingWorker<Void, Void> {
	public QueryResponse response;
	public ServersComboBox scb;

	public ServerStatus(ServersComboBox scb) {
		this.scb = scb;
	}

	@Override
	protected Void doInBackground() throws Exception {
		MinecraftStatus mcQuery = new MinecraftStatus(scb.getIp(), scb.getPort());
		response = mcQuery.fullStat();
		scb.setLoading();

		return null;
	}

	@Override
	protected void done() {
		if (response != null) {
			scb.setOnline();
		} else {
			scb.setOffline();
		}
	}
}
