package net.xupoh.megalauncher.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.Timer;

import net.xupoh.megalauncher.utils.ImageUtils;

@SuppressWarnings("serial")
public abstract class Page extends Panel {

	protected List<JComponent> components;
	private Timer repainter;
	public BufferedImage fakeBg;

	public Page() {
		super();
		components = new ArrayList<>();
	}

	public Page(BufferedImage bi) {
		super(bi);
	}

	private Message message;

	public void showMessage(Message m) {
		cleanMessages();

		for (JComponent c : this.getWindowComponents()) { // Удаляем оконные
															// компоненты
			this.remove(c);
		}

		this.message = m;
		this.message.setBackgroundImage(ImageUtils.getScreenShot(this)); // Скриним
		
		this.removeAll(); // Удаляем лишнее
		for (JComponent c : this.getWindowComponents()) { // Возвращаем оконные
															// компоненты
			this.add(c);
		}

		this.add(message);
		
		repainter = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				repaint();
			}
		});
		repainter.start();
	}

	public void cleanMessages() {
		if (message != null)
			this.remove(message);
		
		this.fakeBg = null;
		repainter = null;
		this.message = null;
		this.buildPage();
	}

	@Override
	protected void paintComponent(Graphics gmain) {
		Graphics2D g = (Graphics2D) gmain;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		super.paintComponent(g);
		
		if (fakeBg != null) {
			g.drawImage(this.fakeBg, 0, 0, null);
		}

		if (message != null) {
			message.repaint();
		}
	}

	protected void buildPage() {
		for (JComponent jc : components) {
			if (jc == null)
				continue;
			
			add(jc);
		}

		repaint();
		revalidate();
	}

	protected abstract List<JComponent> getWindowComponents();
}