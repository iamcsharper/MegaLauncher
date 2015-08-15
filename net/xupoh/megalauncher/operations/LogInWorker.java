package net.xupoh.megalauncher.operations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingWorker;

import net.xupoh.megalauncher.main.Engine;
import net.xupoh.megalauncher.main.Settings;
import net.xupoh.megalauncher.main.Starter;
import net.xupoh.megalauncher.ui.GUIState;
import net.xupoh.megalauncher.ui.Message;
import net.xupoh.megalauncher.ui.ProgressBar;
import net.xupoh.megalauncher.ui.pages.AuthPage;
import net.xupoh.megalauncher.utils.DownloadFileInfo;
import net.xupoh.megalauncher.utils.Response;
import net.xupoh.megalauncher.utils.WebHelper;

public class LogInWorker extends SwingWorker<Void, Void> {
	AuthPage page;
	Response r = null;

	public static String userSession;
	public static String uuid;
	public static String login;

	public LogInWorker(AuthPage page) {
		this.page = page;
	}

	@Override
	protected Void doInBackground() throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		data.put("action", "login");
		data.put("login", this.page.login.getText());
		data.put("server", this.page.test.getActiveOption().getText());
		data.put("password", this.page.password.getText());
		r = WebHelper.sendToSite(data);
		data.clear();

		System.gc();

		return null;
	}

	// После авторизации и ответа от сервера, вне зависимости от удачности.
	@Override
	protected void done() {
		this.page.auth.setEnabled(true);
		this.page.auth.state = GUIState.Default;
		this.page.auth.SetShowIcon(false);

		String msg = r.getMessage();

		// Список файлов
		List<DownloadFileInfo> files = new ArrayList<DownloadFileInfo>();
		try {
			files = WebHelper.parseClient(
					this.page.test.getActiveOption().getText()).getExtraction();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		final Downloader loader = new Downloader(page.test.getActiveOption()
				.getText(), files);

		final Message modal = new Message("Результат авторизации", msg,
				"ЗАКРЫТЬ");

		final ActionListener closeListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Engine.container.cleanMessages();
			}
		};

		modal.getCloseButton().addActionListener(closeListener);
		Engine.container.showMessage(modal);

		if (r.isOk()) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("action", "genUUID");
			params.put("login", this.page.login.getText());

			try {
				uuid = WebHelper.sendPost(Settings.site_root + "action.php",
						params);
			} catch (Exception e) {
			}

			login = this.page.login.getText();
			userSession = r.getSession();

			modal.getCloseButton().setVisible(false);
			// При удачной авторизации запускаем скачку.

			BufferedImage bg = Engine.imageLoader.getRef("progressbar_bg");

			final ProgressBar pb = new ProgressBar(
					(Settings.width - bg.getWidth()) / 2, modal.close.getY()
							- 4
							- Engine.imageLoader.getRef("progressbar_bg")
									.getHeight(),
					Engine.imageLoader.getRef("progressbar_bg"),
					Engine.imageLoader.getRef("progressbar_bar"));
			loader.addActionListener(new ProccessActionListener() {
				// При скачке будет очистка сообщения и запуск окна DownloadPage
				@Override
				public void onProccessStart() {
					Starter.log("Starting downloading...");

					modal.setHeader("Скачивание");

					modal.add(pb);
				}

				// В конце будет запускаться деархиватор
				@Override
				public void onProccessEnd() {
					modal.setHeader("Распаковка");

					final Unpacker unpacker = new Unpacker(page.test
							.getActiveOption().getText(), loader.filesPool);

					unpacker.addActionListener(new ProccessActionListener() {
						@Override
						public void onProccessStart() {
						}

						@Override
						public void onProccessEnd() {
							modal.setHeader("Запуск");

							pb.setPercents(100);

							modal.setContent("Все файлы в норме. Нажмите кнопку \"Запустить игру\"");

							modal.getCloseButton().removeActionListener(
									closeListener);

							modal.getCloseButton().addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									GameLauncher gl = new GameLauncher(page.test.getActiveOption().getText());
									try {
										gl.start();
									} catch (Exception e1) {
										e1.printStackTrace();
									}
									
									modal.close.state = GUIState.Locked;
									modal.close.setEnabled(false);
								}
							});

							modal.getCloseButton().setText("Запустить игру");
							modal.getCloseButton().setVisible(true);
						}

						@Override
						public void onProcessing(DownloadEvent evt) {
							int pc = (int) Math.floor(unpacker
									.getGlobalPercent());
							pb.setPercents(pc);

							String name = "undefined";
							try {
								name = evt.getCurrentFile().getName();
							} catch (Exception e) {
							}

							modal.setContent("Осталось: ~"
									+ Math.ceil(unpacker.getGlobalEstimated() / 1024)
									+ "КБ" + "\nТекущий файл: " + name + "\n~"
									+ pc + "%");
						}

						@Override
						public void onProccessEnd(List result) {
							// TODO Auto-generated method stub

						}
					});

					try {
						unpacker.start();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				// При скачивании на экране DownloadPage будет отрисовываться
				// ProgerssBar, мы этой странице
				// Будем передавать ивент DownloadEvent.
				@Override
				public void onProcessing(DownloadEvent evt) {
					int pc = (int) Math.floor(loader.getGlobalPercent());
					pb.setPercents(pc);
					modal.setContent("Осталось: "
							+ Math.ceil(loader.getGlobalEstimated() / 1024)
							+ "КБ" + "\nТекущий файл: "
							+ evt.getCurrentFile().getName() + "\n" + pc + "%");
				}

				@Override
				public void onProccessEnd(List result) {
					// TODO Auto-generated method stub

				}
			});

			try {
				loader.start();
			} catch (Exception e1) {
				Starter.log(e1.getMessage());
			}
		}
	}
}