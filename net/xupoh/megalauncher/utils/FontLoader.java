package net.xupoh.megalauncher.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

public class FontLoader extends AbstractStyleLoader<String, Font> {

    public static final float fontDerivedSize = 14f;

    public FontLoader() {
        elements = new HashMap<>();
    }

    @Override
    public void load(String name, URL path) {
        Font font = null;

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, path.openStream());
            font = font.deriveFont(fontDerivedSize);
        } catch (IOException | FontFormatException e) {
        }
        
        try (InputStream is = path.openStream()) {
			font = Font.createFont(Font.TRUETYPE_FONT, is);
            font = font.deriveFont(Font.PLAIN, fontDerivedSize);
        } catch (IOException | FontFormatException e1) {
			e1.printStackTrace();
		}

        elements.put(name, font);
    }

    @Override
    public void loadLocal(String name, String filename) {
        load(name, getStylePath("fonts/" + filename));
    }
}
