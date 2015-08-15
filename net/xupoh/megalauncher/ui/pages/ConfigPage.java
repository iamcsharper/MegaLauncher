package net.xupoh.megalauncher.ui.pages;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import net.xupoh.megalauncher.main.Engine;
import net.xupoh.megalauncher.ui.Button;
import net.xupoh.megalauncher.ui.Page;
import net.xupoh.megalauncher.ui.PasswordField;
import net.xupoh.megalauncher.ui.TextField;
import net.xupoh.megalauncher.utils.FontLoader;
import net.xupoh.megalauncher.utils.ImageLoader;

public class ConfigPage extends Page {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	TextField login;
	Button back;
	PasswordField password;

	public ConfigPage(ImageLoader imgLoader, FontLoader fntLoader) {
		super();

		setBackgroundImage(imgLoader.getRef("background"));

		back = new Button(0, 0, "BACK", imgLoader.getRef("button-config"));
		back.setFont(fntLoader.getRef("light").deriveFont(7f));
		back.setForeground(Color.decode("#ebebeb"));
		back.setBounds(0, 0, 80, 40);

		back.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backActionPerformed(e);
			}
		});

		this.components.add(back);

		this.buildPage();
	}

	public void backActionPerformed(java.awt.event.ActionEvent evt) {
		Engine.Navigate("auth");
	}

	@Override
	protected List<JComponent> getWindowComponents() {
		List<JComponent> result = new ArrayList<>();
		return result;
	}
}
