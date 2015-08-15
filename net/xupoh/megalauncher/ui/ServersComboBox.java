package net.xupoh.megalauncher.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import net.xupoh.megalauncher.main.Engine;
import net.xupoh.megalauncher.operations.ServerStatus;
import net.xupoh.megalauncher.utils.ImageUtils;

public class ServersComboBox extends ComboBox implements ActionListener {
	private static final long serialVersionUID = 14989028087735178L;
	
	private String selectedIp;
	private int selectedPort;
	private BufferedImage serverOffline;
	private BufferedImage serverOnline;
	private BufferedImage serverStatusIcon;
	private Animation loader;

	public ServersComboBox(int x, int y, int w, int h, BufferedImage t1,
			BufferedImage t2) {
		super(x, y, w, h, t1, t2);
		
		BufferedImage[] imgs = ImageUtils.splitHorizontal(Engine.imageLoader.getRef("server-status"), 2);
		this.serverOffline = imgs[1];
		this.serverOnline = imgs[0];
		
		Timer timer = new Timer(5000, null);
		timer.setInitialDelay(20);
		timer.setDelay(5000);
		timer.addActionListener(this);
		timer.start();

		loader = new Animation(Engine.imageLoader.getRef("wait"), 18, 60, false);
		loader.getAnimator().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
	}
	
	@Override
	public void addOption(ComboBoxOption<String> option) {
		super.addOption(option);
		
		if (this.activeId == 0) {
			this.onOptionSelected(getActiveOption());
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		ServerStatus ss = new ServerStatus(this);
		ss.execute();
	}
	
	@Override
	protected int calculateHeight(BufferedImage b) {
		return super.calculateHeight(b) + 4;
	}

	@Override
	public void onOptionSelected(ComboBoxOption<?> var) {
		if (var == null)
			return;
		
		this.setLoading();
		
		String[] info = var.getValue().toString().split(":");
		
		if (info.length < 2)
			return;
		
		selectedIp = info[0];
		selectedPort = Integer.parseInt(info[1]);
		
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		try {
			ImageIO.write(this.serverStatusIcon, "png", new File("test.png"));
			}catch(Exception e) {}
		
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
		ComboBoxOption<String> current = null;
		
		try {
			current = this.options.get(0);
		} catch(Exception e) {}

		try {
			current = this.options.get(activeId);
		} catch (IndexOutOfBoundsException e) {
			if (this.options.size() > 0)
				current = this.options.get(0);
		}

		g.drawImage(curTex, 0, y, curTex.getWidth(), curTex.getHeight(), null);
		g.drawString(current.getText(), 5, y + g.getFontMetrics().getHeight() - g.getFontMetrics().getDescent());
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(this.serverStatusIcon != null) {
			g2d.drawImage(this.serverStatusIcon, this.getWidth()
					- this.serverStatusIcon.getWidth(), this.getHeight()
					- this.serverStatusIcon.getHeight(),
					this.serverStatusIcon.getWidth(),
					this.serverStatusIcon.getHeight(), null);
		} else {
			loader.getAnimator().start();
			
			int w = this.getWidth();
            int h = this.getHeight();

            int iw = this.loader.getCurrentFrame().getWidth();
            int ih = this.loader.getCurrentFrame().getHeight();
            
            y += (h - ih) / 2;

            g2d.drawImage(loader.getCurrentFrame(), w - iw, y, iw, ih, null);
		}
		
		g2d.dispose();
	}
	
	public void setLoading() {
		this.serverStatusIcon = null;

		loader.Start();
		
		repaint();
	}

	public void setOnline() {
		this.serverStatusIcon = this.serverOnline;
		
		loader.Pause();
		
		repaint();
	}

	public void setOffline() {
		this.serverStatusIcon = this.serverOffline;
		repaint();
	}

	public String getIp() {
		return this.selectedIp;
	}

	public int getPort() {
		return this.selectedPort;
	}
}
