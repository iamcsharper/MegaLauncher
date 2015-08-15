package net.xupoh.megalauncher.ui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.xupoh.megalauncher.main.Engine;

public class Title extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8778026802351646211L;

	private int x, y = 0;

	public JLabel label = new JLabel();

	public Title(String tit, boolean canDrag) {
		setOpaque(false);
		setLayout(new BorderLayout());
		label.setText(tit);
		add(label, BorderLayout.CENTER);

		if (!canDrag)
			return;

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Engine.frame.setLocation(
						e.getX() + Engine.frame.getX() - x, 
						e.getY() + Engine.frame.getY() - y);
			}
		});
		
		addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) {
				x = e.getX();
				y = e.getY();
			}

			public void mouseClicked(MouseEvent event) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {

	    Graphics2D g2 = (Graphics2D)g;
	    RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
	    g2.setRenderingHints(rh);
	    
	    super.paintComponent(g);
	}
}
