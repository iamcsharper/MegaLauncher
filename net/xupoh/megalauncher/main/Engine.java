package net.xupoh.megalauncher.main;

import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Map;

import net.xupoh.megalauncher.ui.FrameComponent;
import net.xupoh.megalauncher.ui.Page;
import net.xupoh.megalauncher.ui.pages.AuthPage;
import net.xupoh.megalauncher.ui.pages.ConfigPage;
import net.xupoh.megalauncher.ui.pages.DownloadPage;
import net.xupoh.megalauncher.ui.pages.MainPage;
import net.xupoh.megalauncher.utils.FileUtils;
import net.xupoh.megalauncher.utils.FontLoader;
import net.xupoh.megalauncher.utils.ImageLoader;

public class Engine {

	public static Map<String, Page> pages = new HashMap<>();

	public static void init(long start) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run () {
				frame = new FrameComponent();
			}
		});
		
		if (!FileUtils.getWorkingDirectory().exists()) {
			FileUtils.getWorkingDirectory().mkdirs();
		}
		
		setStyles();
		setElements();
		
		frame.repaint();

		double dif = Math.rint(100 * (System.nanoTime() - start) / 1000000000) / 100;
		Starter.log("Initializtaion speed: " + dif + " s");
	}

	private static void setStyles() {
		imageLoader.loadLocal("background", "bg.png");
		imageLoader.loadLocal("button-auth", "button-auth.png");
		imageLoader.loadLocal("button-auth-anim", "button-auth-loading.png");
		imageLoader.loadLocal("button-config", "button-config.png");
		imageLoader.loadLocal("icon-close", "close.png");
		imageLoader.loadLocal("icon-min", "minimize.png");
		imageLoader.loadLocal("icon-config", "config.png");
		imageLoader.loadLocal("textfield", "textfield.png");
		imageLoader.loadLocal("wait", "wait.png");
		imageLoader.loadLocal("modal", "modal.png");
		imageLoader.loadLocal("logo", "logo.png");
		imageLoader.loadLocal("modal-close", "modal-close.png");
		imageLoader.loadLocal("combobox-active", "combobox-active.png");
		imageLoader.loadLocal("combobox-dropdown", "combobox-dropdown.png");
		imageLoader.loadLocal("server-status", "serverstatus.png");
		imageLoader.loadLocal("progressbar_bg", "progressbar_bg.png");
		imageLoader.loadLocal("progressbar_bar", "progressbar_bar.png");

		fontLoader.loadLocal("light", "OpenSans-Light.ttf");
		fontLoader.loadLocal("bold", "OpenSans-ExtraBold.ttf");
		fontLoader.loadLocal("icons", "FontAwesome.otf");
	}

	private static void setElements() {
		// Загружаем страницы
		pages.put("auth", new AuthPage(imageLoader, fontLoader));
		pages.put("config", new ConfigPage(imageLoader, fontLoader));
		pages.put("download", new DownloadPage());

		// Загружаем контейнер - он будет содержать все-все страницы
		container = new MainPage(imageLoader, fontLoader);
		
		frame.add(container);
		frame.repaint();

		Navigate("auth");
	}

	public static Page Navigate(String page) {
		Page p = pages.get(page);
		container.SetContent(p);
		
		return p;
	}

	public static ImageLoader imageLoader = new ImageLoader();
	public static FontLoader fontLoader = new FontLoader();
	public static FrameComponent frame;
	public static MainPage container;
}

/*
 * package net.xupoh.megalauncher.main;
 * 
 * import java.awt.Color; import java.awt.Frame; import java.awt.Insets; import
 * java.awt.event.ActionEvent; import java.awt.event.ActionListener;
 * 
 * import javax.swing.Timer; import javax.swing.border.EmptyBorder;
 * 
 * import net.xupoh.megalauncher.operations.LogInWorker; import
 * net.xupoh.megalauncher.operations.ServerStatus; import
 * net.xupoh.megalauncher.ui.Animation; import net.xupoh.megalauncher.ui.Button;
 * import net.xupoh.megalauncher.ui.FontBundle; import
 * net.xupoh.megalauncher.ui.FrameComponent; import
 * net.xupoh.megalauncher.ui.GUIState; import
 * net.xupoh.megalauncher.ui.IconedButton; import
 * net.xupoh.megalauncher.ui.PanelComponent; import
 * net.xupoh.megalauncher.ui.PasswordField; import
 * net.xupoh.megalauncher.ui.ServerInfo; import
 * net.xupoh.megalauncher.ui.TextField; import net.xupoh.megalauncher.ui.Title;
 * import net.xupoh.megalauncher.utils.FontLoader; import
 * net.xupoh.megalauncher.utils.ImageLoader;
 * 
 * public class Engine {
 * 
 * public FontBundle mainFont; public FontBundle authFont; public FontBundle
 * confFont; public FontBundle inputsFont; public FontBundle titleFont;
 * 
 * public Engine(long start) { frame.setBackground(new Color(0, 0, 0, 0));
 * 
 * setStyles(); setElements();
 * 
 * frame.setVisible(true);
 * 
 * ServerStatus ss = new ServerStatus(serverinfo); ss.execute(); new Timer(5000,
 * new ActionListener() {
 * 
 * @Override public void actionPerformed(ActionEvent arg0) { ServerStatus ss =
 * new ServerStatus(serverinfo); ss.execute(); }
 * 
 * }).start();
 * 
 * // try { // System.err.println(WebHelper.sendPost(Settings.site_root, //
 * "action=config")); // } catch (Exception ex) { //
 * Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex); // }
 * 
 * double dif = Math.rint(100 * (System.nanoTime() - start) / 1000000000) / 100;
 * Starter.log("Initializtaion speed: " + dif + " s"); }
 * 
 * private void setStyles() { imageLoader.loadLocal("background", "kek.png");
 * imageLoader.loadLocal("button-auth", "button-auth.png");
 * imageLoader.loadLocal("button-config", "button-config.png");
 * imageLoader.loadLocal("icon-close", "close.png");
 * imageLoader.loadLocal("icon-min", "minimize.png");
 * imageLoader.loadLocal("textfield", "textfield.png");
 * imageLoader.loadLocal("wait", "wait.png");
 * 
 * fontLoader.loadLocal("main", "Helvetica.otf"); // Только TRUE-TYPE // FORMAT.
 * ttf/otf. fontLoader.loadLocal("icons", "FontAwesome.otf");
 * 
 * titleFont = new FontBundle(fontLoader.getRef("main"), 15f,
 * Color.decode("#f6f6f6")); mainFont = new
 * FontBundle(fontLoader.getRef("main"), 14f, Color.decode("#f6f6f6")); authFont
 * = new FontBundle(fontLoader.getRef("main"), 16f, Color.decode("#ebebeb"));
 * confFont = new FontBundle(fontLoader.getRef("icons"), 16f,
 * Color.decode("#ebebeb")); inputsFont = new
 * FontBundle(fontLoader.getRef("main"), 15.5f, Color.white); }
 * 
 * private void setElements() {
 * 
 * // / Title title = new Title(Settings.title, true); title.setBounds(10, 45,
 * 383, 50); title.setBorder(new EmptyBorder(0, 40, 0, 0));
 * title.label.setForeground(titleFont.color);
 * title.label.setFont(titleFont.font);
 * 
 * // / Button close = new Button(320, 6, "", imageLoader.getRef("icon-close"));
 * 
 * // / Button minimize = new Button(280, 7, "",
 * imageLoader.getRef("icon-min"));
 * 
 * // / Button auth = new IconedButton(49, 258, "Войти",
 * imageLoader.getRef("button-auth"), new Animation( imageLoader.getRef("wait"),
 * 12, 50, true)); auth.setFont(authFont.font);
 * auth.setForeground(authFont.color);
 * 
 * // / Button conf = new Button(257, 258, "\uf085",
 * imageLoader.getRef("button-config")); conf.setFont(confFont.font);
 * conf.setForeground(authFont.color); conf.setMargin(new Insets(10, 0, 0, 0));
 * 
 * // / TextField login = new TextField();
 * login.applyTexture(imageLoader.getRef("textfield"));
 * login.setPlaceholder("Логин..."); login.setBounds(49, 149, -1, -1);
 * login.setSelectionColor(new Color(0.3f, 0.3f, 0.3f, .4f));
 * login.setFont(inputsFont.font); login.setColors(Color.decode("#939494"),
 * Color.decode("#b4b4b4"), Color.decode("#818181"), Color.gray);
 * login.setPaddings(6, 15, 4, 15);
 * 
 * // / PasswordField password = new PasswordField();
 * password.applyTexture(imageLoader.getRef("textfield"));
 * password.setBounds(49, 200, -1, -1); password.setPaddings(6, 15, 4, 15);
 * password.setFont(inputsFont.font); password.setEchoChar('*');
 * password.setColors(Color.decode("#939494"), Color.decode("#b4b4b4"),
 * Color.decode("#818181"), Color.gray); password.setPlaceholder("Пароль...");
 * password.setSelectionColor(new Color(0.3f, 0.3f, 0.3f, .4f));
 * 
 * serverinfo = new ServerInfo("37.59.15.218", 25861); serverinfo.setBounds(20,
 * 20, 100, 100);
 * 
 * close.addActionListener(new java.awt.event.ActionListener() {
 * 
 * @Override public void actionPerformed(java.awt.event.ActionEvent evt) {
 * closeActionPerformed(evt); } }); minimize.addActionListener(new
 * java.awt.event.ActionListener() {
 * 
 * @Override public void actionPerformed(java.awt.event.ActionEvent evt) {
 * minimizeActionPerformed(evt); } }); auth.addActionListener(new
 * java.awt.event.ActionListener() {
 * 
 * @Override public void actionPerformed(java.awt.event.ActionEvent evt) {
 * authActionPerformed(evt); } }); conf.addActionListener(new
 * java.awt.event.ActionListener() {
 * 
 * @Override public void actionPerformed(java.awt.event.ActionEvent evt) {
 * confActionPerformed(evt); } });
 * 
 * // / Panel panel = new PanelComponent();
 * panel.setBackgroundImage(imageLoader.getRef("background")); panel.add(auth);
 * panel.add(login); panel.add(password); panel.add(conf);
 * panel.add(serverinfo); panel.add(title); panel.add(close);
 * panel.add(minimize);
 * 
 * // / Frame frame.getContentPane().add(panel); }
 * 
 * public void closeActionPerformed(java.awt.event.ActionEvent evt) {
 * System.exit(0); }
 * 
 * public void minimizeActionPerformed(java.awt.event.ActionEvent evt) {
 * frame.setState(Frame.ICONIFIED); }
 * 
 * public void authActionPerformed(java.awt.event.ActionEvent evt) {
 * auth.setEnabled(false); auth.state = GUIState.Locked;
 * 
 * auth.SetShowIcon(true);
 * 
 * new LogInWorker(login, password, auth).execute(); }
 * 
 * public void confActionPerformed(java.awt.event.ActionEvent evt) { }
 * 
 * // <editor-fold defaultstate="collapsed" desc="List of elements"> public
 * ImageLoader imageLoader = new ImageLoader(); public FontLoader fontLoader =
 * new FontLoader(); public FrameComponent frame = new FrameComponent(); public
 * PanelComponent panel; public Title title; public Button close; public Button
 * minimize; public IconedButton auth; public Button conf; public TextField
 * login; public PasswordField password; public ServerInfo serverinfo;//
 * </editor-fold> }
 */
