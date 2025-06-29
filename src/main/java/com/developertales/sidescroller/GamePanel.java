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

/* 
 * Represents the main game panel for the side-scrolling game.
 * It handles the game loop, player input, obstacle generation, and rendering.
 */
public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private final Timer timer;
    private final int FPS = 60;
    private final ArrayList<Obstacle> obstacles;    
    private final Random rand;
    private final Image backgroundImage;
    private final BackGroundLayer mountainsLayer;
    private final BackGroundLayer treesLayer;
    private final BackGroundLayer treesLayer2;
    private final BackGroundLayer groundLayer;
    private final int obstacle_min_interval = 40; // frames
    private final int obstacle_max_interval = 90; // frames
    private PlayerDog player;
    private int obstacleTimer = 0;
    private boolean gameOver = false;
    private int score = 0;
    private int scoreTimer = 0;
    private int speed = 5; // Speed of obstacles
    private int obstacle_interval = 90; // frames
    private boolean canUpdateSpeed = true;

    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.GRAY);
        setFocusable(true);
        rand = new Random();
        timer = new Timer(1000 / FPS, this);
        // Initialize player,obstacles and background layers
        player = new PlayerDog(100);
        obstacles = new ArrayList<>();
        backgroundImage = new ImageIcon("src/resources/background.png").getImage();
        mountainsLayer = new BackGroundLayer("src/resources/mountains.png");
        treesLayer = new BackGroundLayer("src/resources/trees_02.png");
        treesLayer2 = new BackGroundLayer("src/resources/trees.png");
        groundLayer = new BackGroundLayer("src/resources/ground.png");
    }

    public void startGame() {
        addKeyListener(this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) return;

        // Update score
        scoreTimer++;
        // Increase score every 10 frames (~6 times per second)
        if (scoreTimer >= 10) { 
            score++;
            scoreTimer = 0;
            canUpdateSpeed = true;
        }
        // Increase speed every 100 points
        if (canUpdateSpeed && score % 100 == 0 && score > 0) { 
            speed++;
            canUpdateSpeed = false;
        }
        // Update player
        player.update();
        // Spawn obstacles
        obstacleTimer++;
        if (obstacleTimer >= obstacle_interval) {
            obstacleTimer = 0;
            obstacles.add(new Obstacle(getWidth(), getHeight()));
            obstacle_interval = rand.nextInt(obstacle_min_interval , obstacle_max_interval);
        }
        // Update obstacles and check for collisions
        Iterator<Obstacle> iter = obstacles.iterator();
        while (iter.hasNext()) {
            Obstacle obs = iter.next();
            obs.update(speed);
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
        // Update backgrounds
        mountainsLayer.update(getWidth(), speed - 3);
        treesLayer.update(getWidth(), speed - 2);
        treesLayer2.update(getWidth(), speed - 1);
        groundLayer.update(getWidth(), speed);

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

        // Draw score
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

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    private void restartGame() {
        player = new PlayerDog(100);
        obstacles.clear();
        obstacleTimer = 0;
        gameOver = false;
        score = 0;
        scoreTimer = 0;
        speed = 5;
        obstacle_interval = 90;
        timer.start();
    }

}

