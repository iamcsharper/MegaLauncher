package net.xupoh.megalauncher.utils;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import net.xupoh.megalauncher.main.Settings;
import net.xupoh.megalauncher.main.Starter;

public class FileUtils {
	public enum PlatformType {
		Windows, Linux
	}

	/** TODO **/

	public static URL getResourceURL(String p) {
		return Starter.class.getResource("../" + p);
	}

	public static List<File> listFileTree(File dir) {
		List<File> fileTree = new ArrayList<File>();

		if (dir.listFiles() == null) {
			fileTree.add(dir);
		}

		for (File entry : dir.listFiles()) {
			if (entry.isFile())
				fileTree.add(entry);
			else
				fileTree.addAll(listFileTree(entry));
		}
		
		return fileTree;
	}

	public static String getMD5(File file) throws Exception {
		if (!file.exists()) {
			return "d41d8cd98f00b204e9800998ecf8427e";
		}

		MessageDigest digest = MessageDigest.getInstance("MD5");
		FileInputStream fis = new FileInputStream(file);

		byte[] byteArray = new byte[1024];
		int bytesCount = 0;

		while ((bytesCount = fis.read(byteArray)) != -1) {
			digest.update(byteArray, 0, bytesCount);
		}

		fis.close();

		byte[] bytes = digest.digest();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		return sb.toString();
	}

	public static String getRelativePath(File file, File folder) {
		String filePath = file.getAbsolutePath();
		String folderPath = folder.getAbsolutePath();

		if (filePath.startsWith(folderPath)) {
			return filePath.substring(folderPath.length() + 1);
		} else {
			return null;
		}
	}

	public static PlatformType getPlatformType() {
		String os = System.getProperty("os.name").toLowerCase();

		if (os.contains("win")) {
			return PlatformType.Windows;
		} else {
			return PlatformType.Linux;
		}
	}

	public static File getWorkingDirectory() {
		if (FileUtils.getPlatformType() == PlatformType.Windows) {
			return new File(System.getenv("AppData") + "/."
					+ Settings.title.toLowerCase() + '/');
		} else {
			return new File(System.getProperty("user.home")
					+ "/Library/Application Support" + "/."
					+ Settings.title.toLowerCase() + '/');
		}
	}
}
