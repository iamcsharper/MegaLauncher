/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.xupoh.megalauncher.ui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Админ
 */
public class Animation extends JPanel implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int delay = 50, currentFrame = 0;
    
    private Timer animator;
    private List<BufferedImage> frames;

    public Animation(List<BufferedImage> frameList, int delay) {
        this.frames = frameList;
        this.delay = delay;

        animator = new Timer(delay, this);
    }
    
    public Animation(BufferedImage track, int framesCount, int fps, boolean horizontal) {

        List<BufferedImage> frms = new ArrayList<>();

        if (horizontal) {
            int frameSize = track.getWidth() / framesCount;

            for (int x = 0; x < framesCount; x++) {
                frms.add(track.getSubimage(frameSize * x, 0, frameSize, track.getHeight()));
            }
        } else {
            int frameSize = track.getHeight() / framesCount;

            for (int y = 0; y < framesCount; y++) {
                frms.add(track.getSubimage(0, frameSize * y, track.getWidth(), frameSize));
            }
        }
        
        this.frames = frms;
        this.delay = fps;
        
        animator = new Timer(delay, this);
    }
    
    public BufferedImage getCurrentFrame() {
        return frames.get(currentFrame);
    }
    
    public BufferedImage[] getFrames() {
        BufferedImage[] arr = new BufferedImage[frames.size()];
        return frames.toArray(arr);
    }
    
    public Timer getAnimator() {
        return animator;
    }
    
    public int getDelay() {
        return this.delay;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currentFrame++;

        if (currentFrame >= frames.size()) {
        	animator.stop();
            currentFrame = 0;
            animator.start();
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(frames.get(currentFrame), 0, 0, getWidth(), getHeight(), null);
    }

    public void Start() {
        this.animator.start();
    }

    public void Pause() {
        this.animator.stop();
    }
}
