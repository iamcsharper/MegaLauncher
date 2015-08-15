package net.xupoh.megalauncher.ui;

import java.awt.image.BufferedImage;

import net.xupoh.megalauncher.utils.ImageUtils;

public enum GUIState {
	Default, Hover, Active, Locked;
	
	public static BufferedImage generateTexture(GUIState cur, BufferedImage source) {
		BufferedImage[] states = ImageUtils.splitVertical(source, 4);
		
		switch (cur) {
		case Default:
			return states[0];
		case Active:
			return states[1];
		case Hover:
			return states[2];
		case Locked:
			return states[3];
		default:
			return null;
		}
	}
}
