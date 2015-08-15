package net.xupoh.megalauncher.operations;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.xupoh.megalauncher.utils.DownloadFileInfo;

public class UnpackTask extends AbstractTask {

	private DownloadFileInfo target;
	private float totalSize = 0;
	private float totalRead = 0;

	public UnpackTask(DownloadFileInfo t) {
		this.target = t;
	}

	@Override
	public void run() {
		byte[] buffer = new byte[1024];

		try {
			File folder = new File(target.getExtractionPath().getAbsolutePath()
					+ File.separator);

			if (!folder.exists()) {
				folder.mkdirs();
			}

			ZipInputStream zis = new ZipInputStream(new FileInputStream(target.getPathOnClient()));
			ZipEntry ze;

			totalSize = target.getPathOnClient().length() * 2.5f;
			
			System.out.println("ZipFile " + target.getPathOnClient().getAbsolutePath() + " is proccess.");

			while ((ze = zis.getNextEntry()) != null) {

				String fileName = ze.getName();
				
				File newFile = new File(folder.getAbsoluteFile() + File.separator + fileName);

				if (ze.isDirectory()) {
					newFile.mkdirs();
				} else {
					BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(newFile));

					int len;
					while ((len = zis.read(buffer)) > 0) {
						totalRead += len;

						fos.write(buffer, 0, len);

						for (ProccessActionListener pal : this.getListeners()) {
							pal.onProcessing(new DownloadEvent(newFile, this.getDone() / this.getTotalSize()));
						}
					}

					fos.flush();
					fos.close();
				}
			}
			
			zis.closeEntry();
			zis.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public float getEstimated() {
		return totalSize - totalRead;
	}

	@Override
	public float getTotalSize() {
		return totalSize;
	}

	@Override
	public float getDone() {
		return totalRead;
	}

	@Override
	public float getPercents() {
		return getDone() / getTotalSize();
	}
}
