package net.xupoh.megalauncher.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import net.xupoh.megalauncher.main.Settings;

@SuppressWarnings("serial")
public class FrameComponent extends JFrame {

	public FrameComponent() {
		super(Settings.title);

		setSize(Settings.width, Settings.height);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				Runtime.getRuntime().exit(0);
			}
		});

		setLayout(new BorderLayout());
		setVisible(true);
	}
}