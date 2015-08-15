package net.xupoh.megalauncher.operations;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTask implements Runnable {
	private List<ProccessActionListener> listeners = new ArrayList<>();

	// Каков процент работы
	public abstract float getPercents();

	// Сколько выполнено (скачано байт или разархивировано и пр)
	public abstract float getDone();

	// Сколько осталось
	public abstract float getEstimated();

	// Сколько всего нужно выполнить
	public abstract float getTotalSize();

	public void addActionListener(ProccessActionListener pal) {
		this.listeners.add(pal);
	}

	public void removeActionListener(ProccessActionListener pal) {
		this.listeners.remove(pal);
	}
	
	protected List<ProccessActionListener> getListeners() {
		return this.listeners;
	}
}