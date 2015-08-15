package net.xupoh.megalauncher.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.xupoh.megalauncher.utils.DownloadFileInfo;

public class Unpacker extends AbstractProccess {
	protected List<DownloadFileInfo> filesPool = new ArrayList<>();
	String client;

	public Unpacker(String cl, List<DownloadFileInfo> filesPool) {
		super(10);

		this.client = cl;
		this.filesPool = filesPool;
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
				for (DownloadFileInfo url : filesPool) {
					UnpackTask task = new UnpackTask(url);

					for (ProccessActionListener dul : listeners) {
						task.addActionListener(dul);
					}

					pool.add(task);
					service.submit(task);
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
