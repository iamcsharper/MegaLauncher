package net.xupoh.megalauncher.ui;

import java.awt.Color;
import java.awt.Font;

public class FontBundle
{
	public Font font;
	public Color color;
	
	public FontBundle(Font name, float size, Color color)
	{
		this.font = name.deriveFont(size);
		this.color = color;
	}
}