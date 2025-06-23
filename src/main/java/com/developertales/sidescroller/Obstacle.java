package com.developertales.sidescroller;
// This file is part of the Developer Tales Side Scroller Game project.
// It is a simple side-scrolling game where obstacles appear and the player can jump over them
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * Represents an obstacle in the side-scrolling game.
 * Obstacles can be of different types and are randomly generated.
 */
public class Obstacle {
    private int x, y, width, height;
    private final Random rand = new Random();
    private Image sprite;

    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
        createObstacle();
    }

    public void update(int speed) {
        x -= speed;
    }

    public boolean isOffScreen() {
        return x + width < 0;
    }

    public void draw(Graphics g) {
        g.drawImage(sprite, x, y, width, height, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    private void createObstacle() {        
        switch (rand.nextInt(5)) {
            case 1 -> {
                sprite = new ImageIcon("src/resources/stump.png").getImage();
            }
            case 2 -> {
                sprite = new ImageIcon("src/resources/bush.png").getImage();
                height = 50;
                width = 50;
            }
            case 3 -> {
                sprite = new ImageIcon("src/resources/tree.png").getImage();
                height = 50;
                width = 50;
            }
            case 4 -> {
                sprite = new ImageIcon("src/resources/torch.gif").getImage();
            }
            default -> {
                sprite = new ImageIcon("src/resources/spikes.png").getImage();
                height = 50;
                width = 50;
            }
        }

        if (height == 0 || width == 0) {
            // If the sprite dimensions are not set, use image values
            height = sprite.getHeight(null);
            width = sprite.getWidth(null);
        }

        y -= 30;
        y -= height;
    }
}
