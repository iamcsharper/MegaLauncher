package net.xupoh.megalauncher.ui;

import java.awt.Color;
import java.awt.Font;

import net.xupoh.megalauncher.utils.TextAlignment;

public class TextData {
	private Font font;
	private Color color;
	private String msg;
	private int marginLeft, marginTop;
	private TextAlignment align;
	
	public TextData(String m, Font f, Color c, int ml, int mt, TextAlignment align) {
		this.font = f;
		this.msg = m;
		this.color = c;
		this.marginLeft = ml;
		this.marginTop = mt;
		this.align = align;
	}
	

	@Override
	public String toString() {
		return "TextData [font=" + font + ", color=" + color + ", msg=" + msg
				+ ", marginLeft=" + marginLeft + ", marginTop=" + marginTop
				+ ", align=" + align + "]";
	}


	public Font getFont() {
		return font;
	}


	public void setFont(Font font) {
		this.font = font;
	}


	public Color getColor() {
		return color;
	}


	public void setColor(Color color) {
		this.color = color;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public int getMarginLeft() {
		return marginLeft;
	}


	public void setMarginLeft(int marginLeft) {
		this.marginLeft = marginLeft;
	}


	public int getMarginTop() {
		return marginTop;
	}


	public void setMarginTop(int marginTop) {
		this.marginTop = marginTop;
	}


	public TextAlignment getAlign() {
		return align;
	}


	public void setAlign(TextAlignment align) {
		this.align = align;
	}
}
