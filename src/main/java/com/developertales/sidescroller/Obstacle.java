package com.developertales.sidescroller;
// This file is part of the Developer Tales Side Scroller Game project.
// It is a simple side-scrolling game where obstacles appear and the player can jump over them
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

public class Obstacle {
    private int x, y, width, height;
    private final int speed;
    private final Random rand = new Random();
    private Image sprite;

    public Obstacle(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        createObstacle();
    }

    public void update() {
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
        switch (rand.nextInt(3)) {
            case 1 -> {
                sprite = new ImageIcon("src/resources/spikes.png").getImage();
                height = sprite.getHeight(null);
                width = sprite.getWidth(null);
            }
            default -> {
                sprite = new ImageIcon("src/resources/spikes.png").getImage();
                height = sprite.getHeight(null);
                width = sprite.getWidth(null);
            }
        }
        y -= 50;
        y -= height;
    }
}
