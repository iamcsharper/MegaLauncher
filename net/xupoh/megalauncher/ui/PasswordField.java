package net.xupoh.megalauncher.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;

public class PasswordField extends JPasswordField implements FocusListener,
		KeyListener, MouseListener, MouseMotionListener {

	/**
     *
     */
	private static final long serialVersionUID = 1L;

	public GUIState state = GUIState.Default;

	public String placeholder;
	public Color textColor, placeholderColor, hoverColor, focusColor;

	public BufferedImage texture;

	public PasswordField() {
		setOpaque(false);
		addKeyListener(this);
		setOpaque(false);
		setCursor(Cursor.getPredefinedCursor(2));
		setBorder(javax.swing.BorderFactory.createEmptyBorder());
		addMouseMotionListener(this);
		addMouseListener(this);
		addFocusListener(this);
		setEchoChar('•');
	}

	public String getText() {
		String res = String.copyValueOf(this.getPassword());

		if (res.equals(placeholder))
			return "";

		return res;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(GUIState.generateTexture(state, texture), 0, 0, null);

		if (!this.getPassword().toString().equals("")
				&& placeholderColor != null && !placeholder.equals("")) {
			setForeground(placeholderColor);
		}

		super.paintComponent(g);

		if (placeholder.length() == 0 || getPassword().length > 0) {
			return;
		}

		final Graphics2D gP = (Graphics2D) g;
		gP.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		gP.setColor(this.placeholderColor);

		FontMetrics fm = g.getFontMetrics();
		gP.drawString(placeholder, getInsets().left,
				(getHeight() + fm.getHeight()) / 2 - fm.getDescent());
	}
	
	public void setPaddings(int top, int left, int bottom, int right) {
		setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
	}

	public void setColors(Color state, Color hover, Color focus,
			Color placeholder) {
		this.textColor = state;
		this.hoverColor = hover;
		this.focusColor = focus;
		this.placeholderColor = placeholder;
	}

	public void setPlaceholder(String ph) {
		this.placeholder = ph;
	}

	@Override
	public void setBounds(int x, int y, int w, int h) {
		if (this.texture != null) {
			if (w == -1) {
				w = this.texture.getWidth();
			}
			if (h == -1) {
				h = this.texture.getHeight() / 4;
			}
		}

		super.setBounds(x, y, w, h);
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (this.isEnabled()) {
			this.state = GUIState.Active;
			setForeground(this.focusColor);
			repaint();
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (this.isEnabled()) {
			setForeground(this.textColor);
			this.state = GUIState.Default;
			repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if (!this.isEnabled() || state == GUIState.Active) {
			return;
		}

		state = GUIState.Default;
		setForeground(textColor);
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if (!this.isEnabled() || state == GUIState.Active) {
			return;
		}

		state = GUIState.Hover;
		setForeground(this.hoverColor);
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public void setTexture(BufferedImage ref) {
		this.texture = ref;
	}
}
