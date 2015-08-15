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

/**
 *
 * @author Админ
 */
public class IconedButton extends Button implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Animation icon;
    private String lastText;
    private boolean showIcon;

    public IconedButton(int x, int y, String text, BufferedImage bg, Animation icon) {
        super(x, y, text, bg);

        this.icon = icon;
        this.icon.getAnimator().addActionListener(this);
    }

    public void SetShowIcon(boolean show) {

        if (show) {
            lastText = this.getText();
            setText("");
            this.icon.Start();
        } else {
            setText(lastText);
            this.icon.Pause();
        }

        this.showIcon = show;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (showIcon && icon != null) {
            int w = this.getWidth();
            int h = this.getHeight();

            int iw = this.icon.getCurrentFrame().getWidth();
            int ih = this.icon.getCurrentFrame().getHeight();

            g.drawImage(icon.getCurrentFrame(), (w - iw) / 2, (h - ih) / 2, this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
