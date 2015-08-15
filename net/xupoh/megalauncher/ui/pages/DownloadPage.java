package net.xupoh.megalauncher.ui.pages;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JComponent;

import net.xupoh.megalauncher.main.Engine;
import net.xupoh.megalauncher.ui.Page;
import net.xupoh.megalauncher.ui.ProgressBar;
import net.xupoh.megalauncher.utils.ImageUtils;

public class DownloadPage extends Page {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ProgressBar pb;

	public DownloadPage() {
		super();

		pb = new ProgressBar(20, 50, Engine.imageLoader.getRef("progressbar_bg"), Engine.imageLoader.getRef("progressbar_bar"));
		
		pb.setPercents(30);
		
		this.components.add(pb);
		this.buildPage();
	}
	
	public void setScreen(BufferedImage screen) {
		this.backgroundImage = screen;		
	}

	@Override
	protected List<JComponent> getWindowComponents() {
		return null;
	}

	@Override
	public void paintComponent(Graphics g) {
		Engine.container.fakeBg = ImageUtils.createOverlayFrom(backgroundImage,
				new Color(1, 1, 1, 150));

		super.paintComponent(g);
	}
}
