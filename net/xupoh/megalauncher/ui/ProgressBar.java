package net.xupoh.megalauncher.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class ProgressBar extends JComponent {
	private static final long serialVersionUID = 1L;
	
	protected int percents;
	protected BufferedImage background, bar;
	
	public void setPercents(int p) {
		this.percents = p;
		repaint();
		revalidate();
	}

	public ProgressBar(int x, int y, BufferedImage t1, BufferedImage t2) {
		this.background = t1;
		this.bar = t2;
		
		int w = t1.getWidth();
		int h = t1.getHeight();
		
		if (w < t2.getWidth())
			w = t2.getWidth();
		
		if (h < t2.getHeight())
			h = t2.getHeight();
		
		setBounds(x, y, w, h);
		setLayout(null);
	}

	@Override
	public void paintComponent(Graphics g) {
		percents = Math.max(1, percents);
		percents = Math.min(100, percents);
		
		int w = percents * bar.getWidth() / 100;
		
		if (w <= 0) {
			w = 1;
		}
		
		BufferedImage resizedBar = bar.getSubimage(0, 0, w, bar.getHeight());
		
		g.drawImage(background, 0, 0, null);
		g.drawImage(resizedBar, 0, 0, null);
		
		super.paintComponent(g);
	}
}
