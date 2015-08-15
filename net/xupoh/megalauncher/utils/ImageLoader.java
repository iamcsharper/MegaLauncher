package net.xupoh.megalauncher.utils;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

import net.xupoh.megalauncher.main.Settings;
import net.xupoh.megalauncher.main.Starter;

public class ImageLoader extends AbstractStyleLoader<String, BufferedImage> {

    public ImageLoader() {
        elements = new HashMap<>();
    }

    @Override
    public void load(String name, URL path) {
        BufferedImage img = null;

        try {
            if (Settings.encryptedStyles) {
                InputStream is = path.openStream();
                img = ImageUtils.DecryptImage(is);
            } else {
                img = ImageIO.read(path);
                Starter.log("[ImageLoader] Image \"" + name + "\" loaded from path " + path);
            }
        } catch (Exception e) {
            Starter.log("[ImageLoader] Can't load image from (local) path: " + path + "\n" + e.getMessage());
        }

        elements.put(name, img);
    }

    @Override
    public void loadLocal(String name, String file) {
        load(name, this.getStylePath(file));
    }
}
