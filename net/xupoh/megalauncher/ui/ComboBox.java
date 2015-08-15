package net.xupoh.megalauncher.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import net.xupoh.megalauncher.utils.ImageUtils;

public class ComboBox extends JComponent implements MouseListener,
		MouseMotionListener, ComboBoxListener {
	private static final long serialVersionUID = 1L;

	public GUIState activeState = GUIState.Default;
	public List<GUIState> dropdownStates = new ArrayList<GUIState>();

	protected List<ComboBoxOption<String>> options = new ArrayList<>();
	protected int activeId = 0;
	protected BufferedImage texture, dropTexture;
	protected boolean dropped;
	protected int initialY;
	protected int currentYPos;
	protected int prevHover;

	public ComboBox(int x, int y, int w, int h, BufferedImage t1,
			BufferedImage t2) {
		this.texture = t1;
		this.dropTexture = t2;
		this.initialY = y;

		setBounds(x, y, w, h);
		setLayout(null);

		addMouseListener(this);
		addMouseMotionListener(this);
		
		this.dropdownStates.add(0, GUIState.Active);
	}

	public void addOption(ComboBoxOption<String> option) {
		this.options.add(option);
		this.dropdownStates.add(GUIState.Default);
		repaint();
	}
	
	public void activateOption (int id) {
		this.activeId = id;
		this.dropdownStates.set(id, GUIState.Active);
		repaint();
	}
	
	public void removeOption(int id) {
		this.options.remove(id);
		this.dropdownStates.remove(id);
		repaint();
	}
	
	public void removeOption(ComboBoxOption<String> opt) {
		this.dropdownStates.remove(this.options.indexOf(opt));
		this.options.remove(opt);
		repaint();
	}

	protected int calculateHeight(BufferedImage b) {
		int def = b.getHeight();
		def += (this.dropTexture.getHeight() / 4) * (this.options.size());
		return  def;
	}

	@Override
	public void paintComponent(Graphics g) {
		BufferedImage[] imgs = ImageUtils.splitHorizontal(this.texture, 2);

		// First - default
		// Second - dropdowned

		BufferedImage curTex = GUIState.generateTexture(activeState, imgs[0]);

		if (dropped) {
			for (int i = 0; i < options.size(); i++) {
				BufferedImage tex = GUIState.generateTexture(dropdownStates.get(i), this.dropTexture);
				
				g.drawImage(tex, 0, tex.getHeight() * i, null);
				g.drawString(options.get(i).getText().toUpperCase(), 5, tex.getHeight() * (i+1) - (g.getFontMetrics().getHeight() / 3));
			}

			curTex = GUIState.generateTexture(activeState, imgs[1]);
			
			setSize(getWidth(), this.calculateHeight(curTex));
			setLocation(getX(), initialY + curTex.getHeight() - getHeight());
		} else {
			setSize(getWidth(), curTex.getHeight());
			setLocation(getX(), initialY);
		}

		int y = getHeight() - curTex.getHeight();
		ComboBoxOption<String> current = this.options.get(0);

		try {
			current = this.options.get(activeId);
		} catch (IndexOutOfBoundsException e) {
			if (this.options.size() > 0)
				current = this.options.get(0);
		}

		g.drawImage(curTex, 0, y, null);
		g.drawString(current.getText(), 5, y + g.getFontMetrics().getHeight() - g.getFontMetrics().getDescent());

		super.paintComponent(g);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if (arg0.getButton() != 1 || !this.isEnabled())
			return;

		activeState = GUIState.Active;
		repaint();
	}
	
	public void setActive (int id) {
		this.activeId = id;
		repaint();
	}
	
	public int getActiveID() {
		return this.activeId;
	}
	
	public ComboBoxOption<String> getActiveOption() {
		return this.options.get(this.activeId);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		this.currentYPos = arg0.getY();
		
		int pick = currentYPos / (this.dropTexture.getHeight() / 4);
		
		if (pick < options.size()) {
			int id = currentYPos / (this.dropTexture.getHeight() / 4);
			
			this.dropdownStates.set(prevHover, GUIState.Default);
			prevHover = id;
			this.dropdownStates.set(id, GUIState.Hover);
			
			this.dropdownStates.set(this.activeId, GUIState.Active);

			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getButton() != 1 || !this.isEnabled())
			return;
		
		int pick = currentYPos / (this.dropTexture.getHeight() / 4);
		
		if (dropped && pick < options.size()) {
			activeId = currentYPos / (this.dropTexture.getHeight() / 4);
			
			onOptionSelected(this.getActiveOption());

			for (int i = 0; i < this.dropdownStates.size(); ++i) {
				this.dropdownStates.set(i, GUIState.Default);
			}
			
			this.dropdownStates.set(activeId, GUIState.Active);
		}
		
		dropped = !dropped;	
		
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void onOptionSelected(ComboBoxOption<?> var) {}
}
