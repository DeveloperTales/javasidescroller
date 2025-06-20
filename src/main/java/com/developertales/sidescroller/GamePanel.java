package com.developertales.sidescroller;
// This file is part of the Developer Tales Side Scroller Game project.
// It is a simple side-scrolling game where obstacles appear and the player can jump over them
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private final Timer timer;
    private final int FPS = 60;
    private final ArrayList<Obstacle> obstacles;
    private final int OBSTACLE_INTERVAL = 90; // frames
    private final Random rand;
    private final Image backgroundImage;
    private final BackGroundLayer mountainsLayer;
    private final BackGroundLayer treesLayer;
    private final BackGroundLayer treesLayer2;
    private final BackGroundLayer groundLayer;
    private PlayerDog player;
    private int obstacleTimer = 0;
    private boolean gameOver = false;
    private int score = 0;
    private int scoreTimer = 0;
    private int speed = 5; // Speed of obstacles

    public GamePanel() {
        setPreferredSize(new Dimension(1360, 800));
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);
        player = new PlayerDog(200, 300);
        obstacles = new ArrayList<>();
        rand = new Random();
        timer = new Timer(1000 / FPS, this);
        backgroundImage = new ImageIcon("src/resources/background.png").getImage();
        mountainsLayer = new BackGroundLayer("src/resources/mountains.png", 1);
        treesLayer = new BackGroundLayer("src/resources/trees_02.png", 2);
        treesLayer2 = new BackGroundLayer("src/resources/trees.png", 3);
        groundLayer = new BackGroundLayer("src/resources/ground.png", speed);
    }

    public void startGame() {
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) return;

        // Update score
        scoreTimer++;
        if (scoreTimer >= 10) { // Increase score every 10 frames (~6 times per second)
            score++;
            scoreTimer = 0;
        }

        // Update player
        player.update();
        // Spawn obstacles
        obstacleTimer++;
        if (obstacleTimer >= OBSTACLE_INTERVAL) {
            obstacleTimer = 0;
            obstacles.add(new Obstacle(getWidth(), getHeight(), speed));
        }

        // Update backgrounds
        mountainsLayer.update(getWidth());
        treesLayer.update(getWidth());
        treesLayer2.update(getWidth());
        groundLayer.update(getWidth());

        // Update obstacles and check for collisions
        Iterator<Obstacle> iter = obstacles.iterator();
        while (iter.hasNext()) {
            Obstacle obs = iter.next();
            obs.update();
            if (obs.isOffScreen()) {
                iter.remove();
            }

            // Collision detection
            if (obs.getBounds().intersects(player.getBounds())) {
                gameOver = true;
                timer.stop();
                Sound.play("src/resources/gameover.wav");
                break;
            }
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Background
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        mountainsLayer.draw(g, getWidth(), getHeight());
        treesLayer.draw(g, getWidth(), getHeight());
        treesLayer2.draw(g, getWidth(), getHeight());
        groundLayer.draw(g, getWidth(), getHeight());
        // Draw player and obstacles
        player.draw(g);
        for (Obstacle obs : obstacles) {
            obs.draw(g);
        }

        if (gameOver) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Game Over!", 300, 180);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press 'R' to Restart", 310, 220);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 25);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameOver) {
            player.jump();
        }

        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            restartGame();
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    private void restartGame() {
        player = new PlayerDog(200, 300);
        obstacles.clear();
        obstacleTimer = 0;
        gameOver = false;
        score = 0;
        scoreTimer = 0;
        timer.start();
        
    }

}

