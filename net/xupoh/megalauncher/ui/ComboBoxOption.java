package net.xupoh.megalauncher.ui;

public class ComboBoxOption<T> {
	private String text;
	private T value;

	public ComboBoxOption() {
		
	}
	
	public ComboBoxOption(String text, T v) {
		this.text = text;
		this.value = v;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
}
