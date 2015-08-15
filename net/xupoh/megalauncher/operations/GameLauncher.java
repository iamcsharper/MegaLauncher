package net.xupoh.megalauncher.operations;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.xupoh.megalauncher.main.Engine;
import net.xupoh.megalauncher.main.Settings;
import net.xupoh.megalauncher.main.Starter;
import net.xupoh.megalauncher.utils.DownloadFileInfo;
import net.xupoh.megalauncher.utils.FileUtils;

public class GameLauncher extends AbstractProccess {

	String client;
	URLClassLoader cl;
	List<String> params = new ArrayList<String>();
	String className;

	public GameLauncher(String clName) {
		super(10);

		this.client = clName;
	}

	@Override
	public void start() throws Exception {
		super.start();

		try {
			launchGame();
		} catch (Exception e) {
			Starter.log(e.getMessage());
		}
	}

	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException {
		String jarPath = FileUtils.getWorkingDirectory().getAbsolutePath() + File.separator + "clients/" + client + "\\";

		System.err.println("Game is launching! Game home located in: " + jarPath);
		
		System.setProperty("fml.ignoreInvalidMinecraftCertificates", "true");
		System.setProperty("fml.ignorePatchDiscrepancies", "true");
		System.setProperty("org.lwjgl.librarypath", jarPath + "bin\\natives");
		System.setProperty("net.java.games.input.librarypath", jarPath+ "bin/natives");
		System.setProperty("java.library.path", jarPath + "bin/natives");

		File bin = new File(jarPath + "bin/");

		List<URL> urls = new ArrayList<URL>();

		for (File f : FileUtils.listFileTree(bin)) {
			try {
				urls.add(f.toURI().toURL());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		cl = new URLClassLoader(urls.toArray(new URL[urls.size()]));

		try {
			cl.loadClass("com.mojang.authlib.Agent");
			params.add("--accessToken");
			params.add(LogInWorker.userSession);
			params.add("--uuid");
			params.add(LogInWorker.uuid);
			params.add("--userProperties");
			params.add("{}");
			params.add("--assetIndex");
			params.add("1.7.10");
		} catch (ClassNotFoundException e2) {
			params.add("--session");
			params.add(LogInWorker.userSession);
		}
		params.add("--username");
		params.add(LogInWorker.login);
		params.add("--version");
		params.add("1.7.10");
		params.add("--gameDir");
		params.add(jarPath);
		params.add("--assetsDir");
		params.add(FileUtils.getWorkingDirectory().getAbsolutePath() + File.separator + "clients/assets/");

		boolean tweakClass = false;
		
		try {
			cl.loadClass("cpw.mods.fml.common.launcher.FMLTweaker");
			params.add("--tweakClass");
			params.add("cpw.mods.fml.common.launcher.FMLTweaker");
			tweakClass = true;
		} catch (ClassNotFoundException e) {
		}
		try {
			cl.loadClass("net.minecraftforge.fml.common.launcher.FMLTweaker");
			params.add("--tweakClass");
			params.add("net.minecraftforge.fml.common.launcher.FMLTweaker");
			tweakClass = true;
		} catch (ClassNotFoundException e) {
		}
		try {
			cl.loadClass("com.mumfrey.liteloader.launch.LiteLoaderTweaker");
			params.add("--tweakClass");
			params.add("com.mumfrey.liteloader.launch.LiteLoaderTweaker");
			tweakClass = true;
		} catch (ClassNotFoundException e) {
		}

		if (tweakClass) {
			className = "net.minecraft.launchwrapper.Launch";
		} else {
			className = "net.minecraft.client.main.Main";
		}
		
		params.add("-Xmx2048m");
		params.add("-Xms2048m");
		
		Engine.frame.setVisible(false);
		
		final Class<?> start = cl.loadClass(className);
		final Method main = start.getMethod("main", String[].class);

		Thread th = new Thread(new Runnable() {
		    @Override
		    public void run() {
		        try {
					main.invoke(null, new Object[] { params.toArray(new String[params.size()]) });
					
					Engine.frame.setVisible(false);
		        } catch (Throwable th) {
		            th.printStackTrace();
		        }
		    }
		});

		th.setContextClassLoader(cl);
		th.start();
		
		System.gc();
	}

	public void checkForUpdates() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				fireStart();

				FilesChecker fc = new FilesChecker(client);
				fc.addActionListener(new ProccessActionListener() {
					@Override
					public void onProccessStart() {
					}

					@Override
					public void onProccessEnd() {
					}

					@Override
					public void onProcessing(DownloadEvent ev) {
					}

					@Override
					public void onProccessEnd(List result) {
						@SuppressWarnings("unchecked")
						List<String> files = (List<String>) result;
						List<DownloadFileInfo> data = new ArrayList<>();

						if (files.size() > 0) {
							for (String f : files) {
								f = f.replace('\\', '/');
								int firstIndex = f.indexOf("/");

								System.err.println("Redownload archive "
										+ f.substring(0, firstIndex));

								DownloadFileInfo info = new DownloadFileInfo();
								info.setPathOnClient(new File(FileUtils
										.getWorkingDirectory()
										.getAbsolutePath()
										+ "/" + f));
								try {
									info.setPathOnServer(new URL(
											Settings.site_root
													+ "action.php?action=redownload&client="
													+ client + "&file=" + f));
								} catch (MalformedURLException e) {
									e.printStackTrace();
								}

								data.add(info);
							}

							Downloader downloader = new Downloader(client, data);
							try {
								downloader.start();

								downloader
										.addActionListener(new ProccessActionListener() {

											@Override
											public void onProccessStart() {
												// TODO Auto-generated method
												// stub

											}

											@Override
											public void onProccessEnd() {
												// TODO Auto-generated method
												// stub

											}

											@Override
											public void onProcessing(
													DownloadEvent ev) {
												// TODO Auto-generated method
												// stub

											}

											@Override
											public void onProccessEnd(
													List result) {
												try {
													launchGame();
												} catch (Exception e) {
													// TODO Auto-generated catch
													// block
													e.printStackTrace();
												}
											}
										});
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							try {
								launchGame();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					}

				});

				pool.add(fc);
				service.submit(fc);

				service.shutdown();
				try {
					service.awaitTermination(Long.MAX_VALUE, TimeUnit.HOURS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
