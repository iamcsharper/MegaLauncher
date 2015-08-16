package net.xupoh.megalauncher.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import net.xupoh.megalauncher.utils.FileUtils;
import net.xupoh.megalauncher.utils.ProcessUtils;

/**
 *
 * @author Илья
 */
public class Starter {
	public static void log(String text) {
		System.out.println(String.format("[STDINFO]: %s", text));
	}

	public static void main(String[] args) {
		try {
			long start = System.nanoTime();
			
			String jarpath = Starter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			// TODO !!!!!!!
			int memory = 2048;

			ArrayList<String> params = new ArrayList<String>();
			params.add(System.getProperty("java.home") + "/bin/java");

			if (System.getProperty("sun.arch.data.model").equals("32") && (memory > 1024)) {
				memory = 1024;
			}

			params.add("-Xmx" + memory + "m");
			params.add("-Xms" + memory + "m");
			params.add("-XX:MaxPermSize=1024m");

			if (System.getProperty("os.name").toLowerCase().startsWith("mac")) {
				params.add("-Xdock:name=Minecraft");
			}

			params.add("-classpath");
			params.add(jarpath);
			params.add(Main.class.getCanonicalName());
			params.add(start+"");

			ProcessBuilder pb = new ProcessBuilder(params);
			pb.directory(FileUtils.getWorkingDirectory());
			Process process = pb.start();
			if (process == null)
				throw new Exception("Launcher can't be started!");
			new ProcessUtils(process).print();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка запуска (Starter) ", javax.swing.JOptionPane.ERROR_MESSAGE, null);
			try {
				Class<?> af = Class.forName("java.lang.Shutdown");
				Method m = af.getDeclaredMethod("halt0", int.class);
				m.setAccessible(true);
				m.invoke(null, 1);
			} catch (Exception x) {}
		}
	}
}
