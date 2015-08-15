package net.xupoh.megalauncher.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.xupoh.megalauncher.main.Engine;
import net.xupoh.megalauncher.main.Settings;
import net.xupoh.megalauncher.utils.ImageUtils;
import net.xupoh.megalauncher.utils.TextAlignment;
import net.xupoh.megalauncher.utils.TextRenderer;

@SuppressWarnings("serial")
public class Message extends Panel {
	private TextData header, content;
	public Button close;
	private int marginLeftReal;
	private Rectangle contentRect;

	private boolean drew;

	public Button getCloseButton() {
		return close;
	}

	public Message(String header, String content, String button) {
		Font hf = Engine.fontLoader.getRef("bold").deriveFont(14f);
		Font cf = Engine.fontLoader.getRef("light").deriveFont(13f);

		TextData hdr = new TextData(header, hf, Color.decode("#000000"), 0, 17,
				TextAlignment.MIDDLE);
		TextData cnt = new TextData(content, cf, Color.decode("#6b6b6b"), 46,
				110, TextAlignment.TOP_LEFT);

		setBounds(0, 105, Settings.width, Settings.height);

		this.header = hdr;
		this.content = cnt;

		BufferedImage bg = Engine.imageLoader.getRef("modal");
		BufferedImage img = Engine.imageLoader.getRef("modal-close");
		int x = (this.getWidth() - img.getWidth()) / 2;
		this.close = new Button(x, bg.getHeight() - img.getHeight() / 4 - 5,
				button, img);
		this.add(close);
	}

	public TextData getHeader() {
		return header;
	}

	public TextData getContent() {
		return content;
	}

	public void setHeader(String text) {
		this.header.setMsg(text);
	}

	public void setContent(String text) {
		this.content.setMsg(text);
	}

	private Rectangle drawString(Graphics gm, TextData str, int width) {
		Graphics2D g = (Graphics2D) gm.create();

		g.setFont(str.getFont());
		g.setColor(str.getColor());

		int x = str.getMarginLeft();
		int y = str.getMarginTop();

		Rectangle bounds = new Rectangle(x, y, width, 100);
		
		return TextRenderer.drawString(g, str.getMsg(), str.getFont(),
				str.getColor(), bounds, str.getAlign());
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (!drew) {
			Engine.container.fakeBg = ImageUtils.createOverlayFrom(backgroundImage, new Color(1, 1, 1, 150));

			Graphics2D reloaded = (Graphics2D) Engine.container.fakeBg.getGraphics();
			BufferedImage bg = Engine.imageLoader.getRef("modal");
			marginLeftReal = (this.getWidth() - bg.getWidth()) / 2;
			reloaded.drawImage(bg, marginLeftReal, this.getY(), bg.getWidth(), bg.getHeight(), null);
			reloaded.dispose();

			drew = true;
		}

		drawString(g, header, this.getWidth());
		contentRect = drawString(g, content, this.getWindowWidth());
	}
	
	public int getWindowWidth() {
		return (int) Math.floor(getWidth() * 0.65f);
	}

	public int getContentY() {
		// TODO Auto-generated method stub
		return content.getMarginTop();
	}
	
	public Rectangle getContentRect () {
		return contentRect;
	}
}
