package com.developertales.sidescroller;
// This file is part of the Developer Tales Side Scroller Game project.
// It is a simple side-scrolling game where obstacles appear and the player can jump over them
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Obstacle {
    private int x, y, width, height;
    private final int speed;
    private final Random rand = new Random();

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
        g.setColor(Color.GREEN.darker());
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    private void createObstacle() {
        height = 50 + rand.nextInt(50);
        width = 30; // Fixed width for simplicity
        y = -50 - height;
    }
}
