package net.xupoh.megalauncher.operations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.xupoh.megalauncher.utils.DownloadFileInfo;
import net.xupoh.megalauncher.utils.FileUtils;
import net.xupoh.megalauncher.utils.WebHelper;

public class Downloader extends AbstractProccess {
	protected List<DownloadFileInfo> filesPool;
	private String client;

	public static File currentFile;

	private boolean end;

	public boolean isEnd() {
		return end;
	}

	public Downloader(String client, List<DownloadFileInfo> files) {
		super(10); // Сколько поток на 1 ядро

		this.filesPool = files;
		this.client = client;
	}

	public List<DownloadFileInfo> getBadArchives() {
		List<DownloadFileInfo> files = new ArrayList<>();

		for (DownloadFileInfo f : this.filesPool) { // В пуле сидят .zip
			try {
				if (!WebHelper.checkFile(this.client, f.getPathOnClient().getName(), FileUtils.getMD5(f.getPathOnClient()))) {
					System.err.println("Найден плохой ZIP-файл: " + f.getPathOnClient().getName() + " MD5 = " + FileUtils.getMD5(f.getPathOnClient()) + " client = " + client);

					files.add(f);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return files;
	}

	@Override
	public void start() throws Exception {
		super.start();

		if (filesPool.size() == 0) {
			fireShutdown();

			return;
		}

		fireStart();

		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (this) {
					for (DownloadFileInfo url : getBadArchives()) {
						DownloadTask task = new DownloadTask(url);

						for (ProccessActionListener dul : listeners) {
							task.addActionListener(dul);
						}

						pool.add(task);
						service.submit(task);
					}
				}

				service.shutdown();
				try {
					service.awaitTermination(Long.MAX_VALUE, TimeUnit.HOURS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				fireShutdown();
			}
		}).start();
	}
}
