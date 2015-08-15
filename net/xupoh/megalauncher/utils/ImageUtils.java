package net.xupoh.megalauncher.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import net.xupoh.megalauncher.main.Starter;

public class ImageUtils {

	public static BufferedImage getScreenShot(JComponent c) {
		int w = c.getWidth();
		int h = c.getHeight();
		BufferedImage img = new BufferedImage(w, h, 2);
		Graphics2D g = img.createGraphics();
		c.paint(g);
		g.dispose();
		
		return img;
	}

	public static String EncryptImage(BufferedImage img, String path) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(img, "png", baos);
			byte[] imageInByte = baos.toByteArray();
			baos.flush();
			baos.close();

			return new String(Encryption.encrypt(imageInByte));
		} catch (Exception e) {
			Starter.log("Caused error: " + e.getMessage());
		}

		return "";
	}

	public static BufferedImage DecryptImage(InputStream is) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			byte[] buf = new byte[1024 * 64];// try to read 1Kb at time
			int bLength;
			while ((bLength = is.read(buf)) != -1) {
				baos.write(buf, 0, bLength);
			}
			baos.flush();

			byte[] res = baos.toByteArray();

			BufferedImage result = ImageIO.read(new ByteArrayInputStream(
					Encryption.decrypt(res)));

			return result;
		} catch (Exception ex) {
			Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		return null;
	}

	public static BufferedImage createOverlayFrom(BufferedImage source, Color black) {
		for (int y = 0; y < source.getHeight(); ++y) {
			for (int x = 0; x < source.getWidth(); ++x) {
				int pixel = source.getRGB(x, y);
				
				if (pixel>>24 == 0x00) {
					continue;
				}
				
				int col = (black.getAlpha() << 24) | (black.getRed() << 16)
						| (black.getGreen() << 8) | black.getBlue();
				source.setRGB(x, y, col);
			}
		}
		
		return source;
	}

	public static BufferedImage[] splitVertical(BufferedImage d, int pieces) {
		int w = d.getWidth();
		int h = d.getHeight();
		int slice = h / pieces;
		
		BufferedImage[] frames = new BufferedImage[pieces];
		
		for (int i = 0; i < pieces; i++) {
			frames[i] = d.getSubimage(0, i*slice, w, slice);
		}
		
		return frames;
	}

	public static BufferedImage[] splitHorizontal(BufferedImage texture, int pieces) {
		int w = texture.getWidth();
		int h = texture.getHeight();
		int slice = w / pieces;
		
		BufferedImage[] frames = new BufferedImage[pieces];
		
		for (int i = 0; i < pieces; i++) {
			frames[i] = texture.getSubimage(i*slice, 0, slice, h);
		}
		
		return frames;
	}
	
	
}
