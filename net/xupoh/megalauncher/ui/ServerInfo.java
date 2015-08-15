package net.xupoh.megalauncher.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import net.xupoh.megalauncher.main.Engine;
import net.xupoh.megalauncher.utils.ImageUtils;

public class ServerInfo extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2542286882691173922L;

	public BufferedImage icon;
	private String info;
	private String ip;
	private int port;
	
	public ServerInfo () {
		icon = ImageUtils.splitHorizontal(Engine.imageLoader.getRef("server-status"), 2)[1];
	}
	
	public ServerInfo(String ip, int port) {
		super();
		
		this.ip = ip;
		this.port = port;
	}
	
	public void setIcon(BufferedImage icon) {
		this.icon = icon;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(icon != null) {
			g2d.drawImage(icon, 0, 0, icon.getWidth(), icon.getHeight(), null);
		}
		
		if (info != null) {
			g2d.drawString(info, 0, 20);
		}

		g2d.dispose();
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
}
