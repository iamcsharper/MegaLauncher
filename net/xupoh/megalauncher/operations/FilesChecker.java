package net.xupoh.megalauncher.operations;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.xupoh.megalauncher.main.Settings;
import net.xupoh.megalauncher.utils.FileUtils;
import net.xupoh.megalauncher.utils.WebHelper;

public class FilesChecker extends AbstractTask {

	String client;

	public FilesChecker(String cl) {
		this.client = cl;
	}

	@Override
	public void run() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("action", "getFilesNeedToCheck");
		List<String> badFiles = new ArrayList<>();

		try {
			String[] files = WebHelper.sendPost(Settings.site_root + "action.php", params).split(",");

			for (String f : files) {
				File folder = new File(FileUtils.getWorkingDirectory().getAbsolutePath() + "/clients/" + client + "/");
				File file = new File(folder.getAbsolutePath() + "/" + f);

				// Если mods/
				if (file.isDirectory()) {
					for (File localFile : FileUtils.listFileTree(file)) {
						String md5 = FileUtils.getMD5(localFile);
						
						if (!WebHelper.checkFile(client, FileUtils.getRelativePath(localFile, folder), md5)) {
							badFiles.add( FileUtils.getRelativePath(localFile, folder) );
							
							System.err.println(FileUtils.getRelativePath(localFile, folder) + " is bad");
						} else {
							System.out.println(FileUtils.getRelativePath(localFile, folder) + " is okay");
						}
					}
				} else {
					String md5 = FileUtils.getMD5(file);
					
					if (!WebHelper.checkFile(client, FileUtils.getRelativePath(file, folder), md5)) {
						badFiles.add( FileUtils.getRelativePath(file, folder) );
						
						System.err.println(FileUtils.getRelativePath(file, folder) + " is bad");
					} else {
						System.out.println(FileUtils.getRelativePath(file, folder) + " is okay");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (ProccessActionListener pal : this.getListeners()) {
			pal.onProccessEnd(badFiles);
		}
	}

	@Override
	public float getPercents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getDone() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getEstimated() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getTotalSize() {
		// TODO Auto-generated method stub
		return 0;
	}

}
