package com.developertales.sidescroller;
import javax.swing.JFrame;

/**
 * Main class to run the side-scrolling game.
 * It initializes the game window and starts the game loop.
 */
public class JavaSideScroller {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Side Scroller Game");
        GamePanel gamePanel = new GamePanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Sound.playLoop("src/resources/music.wav");
        gamePanel.startGame();
    }
}
