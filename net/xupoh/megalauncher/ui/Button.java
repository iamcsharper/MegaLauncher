package net.xupoh.megalauncher.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Button extends JButton implements MouseListener,
		MouseMotionListener {
	public BufferedImage texture;
	public GUIState state = GUIState.Default;

	public Button(int x, int y, String text, BufferedImage bg) {
		setText(text);
		setBorderPainted(false);
		setContentAreaFilled(false);
		setFocusPainted(false);
		setOpaque(false);
		setFocusable(false);
		setCursor(Cursor.getPredefinedCursor(12));
		setForeground(Color.decode("#ebebeb"));

		this.texture = bg;
		setBounds(x, y, bg.getWidth(), bg.getHeight() / 4);

		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		BufferedImage img = GUIState.generateTexture(state, texture);
		
		// Mode 0 - ScaleToFit
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
		// Mode 1 - ScaleAndCut
		//g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
		
		super.paintComponent(g);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if (arg0.getButton() != 1 || !this.isEnabled())
			return;

		state = GUIState.Active;
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if (!this.isEnabled())
			return;

		state = GUIState.Default;
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		if (!this.isEnabled())
			return;
		

		state = GUIState.Hover;
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (!this.isEnabled())
			return;

		state = GUIState.Hover;
	}
}