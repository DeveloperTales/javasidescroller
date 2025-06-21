package com.developertales.sidescroller;
// This file is part of the Developer Tales Side Scroller Game project.
// It is a simple side-scrolling game where obstacles appear and the player can jump over them

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class BackGroundLayer{
    private final Image backgroundImage;
    private int x = 0;

    public BackGroundLayer(String image) {
        backgroundImage = new ImageIcon(image).getImage();
    }

    public void draw(Graphics g, int screenWidth, int screenHeight) {
        g.drawImage(backgroundImage, x, 0, screenWidth, screenHeight, null);
        g.drawImage(backgroundImage, x + screenWidth, 0, screenWidth, screenHeight, null);
    }

    public void update(int screenWidth, int speed) {
        // Loop backgrounds
        x -= speed;
        if (x <= -screenWidth){
            x = 0;
        }
    }

}