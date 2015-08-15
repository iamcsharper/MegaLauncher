package net.xupoh.megalauncher.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractProccess {
	protected List<AbstractTask> pool = new ArrayList<>();
	protected ExecutorService service;
	protected List<ProccessActionListener> listeners = new ArrayList<>();
	protected int maxOperationsPerCore;
	
	public AbstractProccess (int mopc) {
		this.maxOperationsPerCore = mopc;
	}
	
	public void addActionListener(ProccessActionListener pal) {
		this.listeners.add(pal);
	}
	
	public void start() throws Exception {
		service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 5);
	}
	
	protected void fireStart() {
		for (ProccessActionListener dul : listeners) {
			dul.onProccessStart();
		}
	}

	protected void fireShutdown() {
		for (ProccessActionListener dul : listeners) {
			dul.onProccessEnd();
		}
	}
	
	public float getGlobalEstimated() {
		float total = 0;

		for (int i = 0; i < pool.size(); i++) {
			total += (int) pool.get(i).getEstimated();
		}

		return total;
	}

	public float getGlobalPercent() {
		if (pool.size() == 0) {
			return 100;
		}
		
		float totalSize = 0;
		float totalDownloaded = 0;

		for (int i = 0; i < pool.size(); i++) {
			totalSize += pool.get(i).getTotalSize();
			totalDownloaded += pool.get(i).getDone();
		}

		return totalDownloaded / totalSize * 100;
	}
}
