package net.xupoh.megalauncher.utils;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class AnimatedImage extends JComponent implements ActionListener {

    private List<BufferedImage> frames = new ArrayList<>();
    private int delay;
    private int curIndex;
    private Timer timer;

    public AnimatedImage(List<BufferedImage> frames, int delay) {
        this.frames = frames;
        this.delay = delay;

        timer = new Timer(delay, this);
    }

    public void Start() {
        timer.start();
    }

    public void Pause() {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        curIndex++;

        if (curIndex >= frames.size()) {
            curIndex = 0;
        }

        repaint();
    }

    public static AnimatedImage FromOne(URL path) {
        BufferedImage one;
        try {
            one = ImageIO.read(path);
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }

        List<BufferedImage> anim = new ArrayList<>();
        anim.add(one);

        return new AnimatedImage(anim, 0);
    }

    public static AnimatedImage FromPack(URL path, int frames, int fps,
            boolean horizontal) {
        BufferedImage track;
        try {
            track = ImageIO.read(path);
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }

        List<BufferedImage> frms = new ArrayList<>();

        if (horizontal) {
            int frameSize = track.getWidth() / frames;

            for (int x = 0; x < frames; x++) {
                frms.add(track.getSubimage(frameSize * x, 0, frameSize, track.getHeight()));
            }
        } else {
            int frameSize = track.getHeight() / frames;

            for (int y = 0; y < frames; y++) {
                frms.add(track.getSubimage(0, frameSize * y, track.getWidth(), frameSize));
            }
        }

        return new AnimatedImage(frms, fps);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(frames.get(curIndex), 0, 0, getWidth(), getHeight(), null);
    }

    public List<BufferedImage> getFrames() {
        return frames;
    }

    public void setFrames(List<BufferedImage> frames) {
        this.frames = frames;
    }

    public int getCurIndex() {
        return curIndex;
    }

    public void setCurIndex(int curIndex) {
        this.curIndex = curIndex;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
    
    public Timer getTimer() {
        return timer;
    }
}
