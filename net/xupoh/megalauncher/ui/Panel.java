package net.xupoh.megalauncher.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Panel extends JPanel {
	protected BufferedImage backgroundImage;
	
	public Panel() {
		setOpaque(false);
		setLayout(null);
		setBorder(null);
		setBackground(new Color(0, 0, 0, 0));
	}

	public Panel (BufferedImage bi) {
		super();
		this.backgroundImage = bi;
	}


	public BufferedImage getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(BufferedImage backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if (backgroundImage != null) {
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
		}
	}
}
