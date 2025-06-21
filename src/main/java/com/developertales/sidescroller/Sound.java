package com.developertales.sidescroller;
// This file is part of the Developer Tales Side Scroller Game project.
// It is a simple side-scrolling game where obstacles appear and the player can jump over them
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/* 
 * Sound class to handle playing sound effects and background music.
 * It uses Java's built-in sound API to play audio files.
 */
public class Sound {
    private static Clip musicClip;

    public static void play(String filePath) {
        try {
            File soundFile = new File(filePath);
            if (!soundFile.exists()) {
                System.err.println("Sound file not found: " + filePath);
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }
    }

    public static void playLoop(String filePath) {
        try {
            File soundFile = new File(filePath);
            if (!soundFile.exists()) {
                System.err.println("Music file not found: " + filePath);
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            musicClip = AudioSystem.getClip();
            musicClip.open(audioIn);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.err.println("Error playing music: " + e.getMessage());
        }
    }

    public static void stopLoop() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
        }
    }
}

