package net.xupoh.megalauncher.ui.pages;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;

import net.xupoh.megalauncher.main.Engine;
import net.xupoh.megalauncher.main.Settings;
import net.xupoh.megalauncher.ui.Button;
import net.xupoh.megalauncher.ui.Page;
import net.xupoh.megalauncher.ui.Panel;
import net.xupoh.megalauncher.ui.Title;
import net.xupoh.megalauncher.utils.FontLoader;
import net.xupoh.megalauncher.utils.ImageLoader;

public class MainPage extends Page {
	private static final long serialVersionUID = -5166960979546138559L;

	Title title;
	Button close, minimize, conf;
	Panel logo;
	Page content;

	public MainPage(ImageLoader imgLoader, FontLoader fntLoader) {
		super();

		setBackgroundImage(imgLoader.getRef("background"));

		title = new Title("", true);
		title.setBounds(0, 0, Settings.width, Settings.height);
		title.setBorder(new EmptyBorder(0, 40, 0, 0));

		close = new Button(68, 170, "", imgLoader.getRef("icon-close"));
		minimize = new Button(30, 170, "", imgLoader.getRef("icon-min"));
		conf = new Button(105, 170, "", imgLoader.getRef("icon-config"));

		logo = new Panel(imgLoader.getRef("logo"));
		int lw = imgLoader.getRef("logo").getWidth();
		int lh = imgLoader.getRef("logo").getHeight();
		int center = (int) (Settings.width - lw) / 2;
		logo.setBounds(center, 20, lw, lh);

		close.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				closeActionPerformed(evt);
			}
		});
		minimize.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				minimizeActionPerformed(evt);
			}
		});

		conf.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				confActionPerformed(evt);
			}
		});

		build(null);
	}

	private void build(Page cont) {
		this.components.add(logo); // Up the all
		this.components.add(close);
		this.components.add(minimize);
		this.components.add(conf);

		if (cont != null)
			this.components.add(cont);

		this.components.add(title); // Under all

		this.buildPage();
	}

	public void SetContent(Page page) {
		this.removeAll();
		this.components.clear();

		this.content = page;
		this.content.setBounds(30, 200, 257, 238);

		build(this.content);
	}

	public Page getContent() {
		return this.content;
	}

	public void closeActionPerformed(java.awt.event.ActionEvent evt) {
		System.exit(0);
	}

	public void minimizeActionPerformed(java.awt.event.ActionEvent evt) {
		Engine.frame.setState(Frame.ICONIFIED);
	}

	public void confActionPerformed(java.awt.event.ActionEvent evt) {
		Engine.Navigate("config");
	}

	@Override
	protected List<JComponent> getWindowComponents() {
		List<JComponent> result = new ArrayList<>();
		result.add(logo);
		return result;
	}
}
