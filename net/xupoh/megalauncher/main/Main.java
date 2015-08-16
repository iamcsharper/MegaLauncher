package net.xupoh.megalauncher.main;

import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		try {
			boolean test = true;
			long started = 0;

			if (args.length < 1) {
				Starter.main(args);
				
				return;
			} else {
				started = Long.parseLong(args[0]);
			}

			if (started == 0) { 
				test = false;
			}

			if (test) {
				Engine.init(started);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "Ошибка запуска (вы что-то химичите)", javax.swing.JOptionPane.ERROR_MESSAGE, null);
		}
	}
}
