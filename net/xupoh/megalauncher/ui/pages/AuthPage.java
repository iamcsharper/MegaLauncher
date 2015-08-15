package net.xupoh.megalauncher.ui.pages;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import net.xupoh.megalauncher.operations.LogInWorker;
import net.xupoh.megalauncher.ui.Animation;
import net.xupoh.megalauncher.ui.ComboBox;
import net.xupoh.megalauncher.ui.ComboBoxOption;
import net.xupoh.megalauncher.ui.GUIState;
import net.xupoh.megalauncher.ui.IconedButton;
import net.xupoh.megalauncher.ui.Page;
import net.xupoh.megalauncher.ui.PasswordField;
import net.xupoh.megalauncher.ui.ServersComboBox;
import net.xupoh.megalauncher.ui.TextField;
import net.xupoh.megalauncher.utils.FontLoader;
import net.xupoh.megalauncher.utils.ImageLoader;
import net.xupoh.megalauncher.utils.WebHelper;

public class AuthPage extends Page {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TextField login;
	public IconedButton auth;
	public PasswordField password;
	public ComboBox test;

	public AuthPage(ImageLoader imgLoader, FontLoader fntLoader) {
		super();
		
		auth = new IconedButton(0, 110, "АВТОРИЗАЦИЯ", imgLoader.getRef("button-auth"), new Animation(imgLoader.getRef("button-auth-anim"), 12, 55, false));
		auth.setFont(fntLoader.getRef("bold").deriveFont(14f));
		auth.setForeground(Color.decode("#ebebeb"));
		
		login = new TextField();
		login.setTexture(imgLoader.getRef("textfield"));
		login.setPlaceholder("Логин...");
		login.setBounds(0, 0, -1, -1);
		login.setSelectionColor(new Color(0.76f, 0.58f, 0.321f, .3f));
		login.setFont(fntLoader.getRef("light").deriveFont(14f));
		login.setColors(Color.decode("#ffffff"), Color.decode("#ffffff"), Color.decode("#ffffff"), Color.decode("#ffffff"));
		login.setPaddings(6, 15, 4, 15);

		// / PasswordField
		password = new PasswordField();
		password.setTexture(imgLoader.getRef("textfield"));
		password.setBounds(0, 55, -1, -1);
		password.setPaddings(6, 15, 4, 15);
		password.setFont(fntLoader.getRef("light").deriveFont(14f));
		password.setEchoChar('*');
		password.setColors(Color.decode("#e9e9e9"), Color.decode("#ffffff"), Color.decode("#ffffff"), Color.decode("#e9e9e9"));
		password.setPlaceholder("Пароль...");
		password.setSelectionColor(new Color(0.3f, 0.3f, 0.3f, .4f));

		test = new ServersComboBox(0, 168, 257, 22, imgLoader.getRef("combobox-active"), imgLoader.getRef("combobox-dropdown"));
		test.setFont(fntLoader.getRef("light").deriveFont(14f));
		test.setForeground(Color.white);
		//test.setBorder(BorderFactory.createLineBorder(Color.red, 1));
		
		try {
			for (String clientName : WebHelper.getAllClients()) {
				test.addOption(new ComboBoxOption<String>(clientName, WebHelper
						.parseClient(clientName).getIp()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		auth.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				authActionPerformed(evt);
			}
		});

		this.components.add(test);
		this.components.add(auth);
		this.components.add(login);
		this.components.add(password);
		
		this.buildPage();
	}

	public void authActionPerformed(java.awt.event.ActionEvent evt) {
		auth.setEnabled(false);
		auth.state = GUIState.Locked;
		auth.SetShowIcon(true);

		new LogInWorker(this).execute();
	}

	@Override
	protected List<JComponent> getWindowComponents() {
		List<JComponent> result = new ArrayList<>();
		return result;
	}
}
