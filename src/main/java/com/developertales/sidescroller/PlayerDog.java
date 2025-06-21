package com.developertales.sidescroller;
// This file is part of the Developer Tales Side Scroller Game project.
// It is a simple side-scrolling game where obstacles appear and the player can jump over them
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

public class PlayerDog {

    private final int x;
    private final int frameWidth;
    private final int frameHeight;
    private final int gravity = 1;
    private final int jumpStrength = -20;
    private final Image spriteSheet;
    private final long frameDelay = 100; // milliseconds between frames
    private final int totalFrames = 5; // Assuming 5 frames in the sprite sheet
    private final int reducedY = 10; // Adjust the height for better collision detection
    private final int reduceX = 10; // Adjust the width for better collision detection 
    private int y;
    private int groundLevel = 600 - 30;
    private int currentFrame = 4; // Start with the last frame since this sprite is in reverse order
    private long lastFrameTime;
    private int yVelocity = 0;

    public PlayerDog(int x, int y) {
        this.x = x;
        this.y = y;
        spriteSheet = new ImageIcon("src/resources/wolf_run.png").getImage();
        frameWidth = spriteSheet.getWidth(null) / totalFrames;
        frameHeight = spriteSheet.getHeight(null);
        groundLevel -= frameHeight; // Adjust ground level based on player height
        lastFrameTime = System.currentTimeMillis();
    }

    public void update() {
        yVelocity += gravity;
        y += yVelocity;
        if (y > groundLevel) {
            y = groundLevel;
            yVelocity = 0;
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastFrameTime > frameDelay) {
                // Update the current frame for animation in reverse order
                currentFrame--;
                if (currentFrame < 0) {
                    currentFrame = totalFrames - 1;
                }

                lastFrameTime = currentTime;
            }
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
        int sx = currentFrame * frameWidth;
        g.drawImage(spriteSheet, x, y, x + frameWidth, y + frameHeight, 
            sx, 0, sx + frameWidth, frameHeight, null);
        g.setColor(Color.GREEN);
        g.drawRect(x + reduceX, y + reducedY, frameWidth - (2 * reduceX), frameHeight - (2 * reducedY));
    }

    public Rectangle getBounds() {
        return new Rectangle(x + reduceX, y + reducedY, frameWidth - (2 * reduceX), frameHeight - (2 * reducedY));
    }
}
