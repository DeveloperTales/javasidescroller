package com.developertales.sidescroller;
// This file is part of the Developer Tales Side Scroller Game project.
// It is a simple side-scrolling game where obstacles appear and the player can jump over them
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Player {
    private int x, y, width, height;
    private int yVelocity = 0;
    private final int gravity = 1;
    private final int jumpStrength = -20;
    private int groundLevel = 800 - 50;

    public Player(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        groundLevel -= height; // Adjust ground level based on player height
    }

    public void update() {
        yVelocity += gravity;
        y += yVelocity;

        if (y > groundLevel) {
            y = groundLevel;
            yVelocity = 0;
        }
    }

    public void jump() {
        if (y == groundLevel) {
            yVelocity = jumpStrength;
            Random jumpSound = new Random();
            // Play a random jump sound
            if (jumpSound.nextBoolean()) {
                Sound.play("src/resources/jump_06.wav");
            } else {    
                Sound.play("src/resources/jump_07.wav");
            }
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
