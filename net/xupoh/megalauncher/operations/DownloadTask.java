package net.xupoh.megalauncher.operations;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import net.xupoh.megalauncher.main.Starter;
import net.xupoh.megalauncher.utils.DownloadFileInfo;

public class DownloadTask extends AbstractTask {
	protected DownloadFileInfo target;
	protected int totalRead = 0;
	protected int totalSize = 0;

	public DownloadTask(DownloadFileInfo tg) {
		this.target = tg;
	}

	@Override
	public void run() {
		String p = target.getPathOnClient().getAbsolutePath();
		p = p.replaceAll(target.getPathOnClient().getName(), "");
		new File(p).mkdirs();

		URLConnection conn = null;
		InputStream is = null;
		FileOutputStream fos = null;

		try {
			is = new BufferedInputStream(target.getPathOnServer().openStream());
			conn = target.getPathOnServer().openConnection();
			conn.connect();
			totalSize = conn.getContentLength();

			fos = new FileOutputStream(target.getPathOnClient());
		} catch (IOException e) {
			Starter.log("Невозможно сохранить файл по адресу "
					+ target.getPathOnClient().getAbsolutePath());
			e.printStackTrace();
		}

		byte[] buffer = new byte[1024];

		int read;

		try {
			while ((read = is.read(buffer, 0, buffer.length)) != -1) {
				fos.write(buffer, 0, read);
				totalRead += read;

				Downloader.currentFile = target.getPathOnClient();

				for (ProccessActionListener list : this.getListeners()) {
					list.onProcessing(new DownloadEvent(target.getPathOnClient(), this.getPercents()));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				fos.flush();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public float getPercents() {
		return totalRead / totalSize * 100;
	}

	@Override
	public float getDone() {
		return totalRead;
	}

	@Override
	public float getEstimated() {
		return totalSize - totalRead;
	}

	@Override
	public float getTotalSize() {
		return totalSize;
	}
}
