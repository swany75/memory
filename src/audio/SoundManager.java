/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class SoundManager {
 
    private static Clip musicClip;
    private static float soundVolume = 1.0f;
    private static float musicVolume = 0.8f;
    private static boolean muted = false;
 
    public static void playSound(String path) {
        if (muted) return;
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            applyVolume(clip, soundVolume);
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) clip.close();
            });
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("SoundManager: error reproduciendo \"" + path + "\": " + e.getMessage());
        }
    }
 
    public static void playMusic(String path) {
        if (muted) return;
        if (musicClip != null) {
            musicClip.stop();
            musicClip.close();
        }
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
            musicClip = AudioSystem.getClip();
            musicClip.open(ais);
            applyVolume(musicClip, musicVolume);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            musicClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("SoundManager: error reproduciendo \"" + path + "\": " + e.getMessage());
        }
    }
 
    public static void setSoundVolume(float volume) {
        soundVolume = volume;
    }
 
    public static void setMusicVolume(float volume) {
        musicVolume = volume;
        if (musicClip != null) applyVolume(musicClip, musicVolume);
    }
 
    public static void setMuted(boolean value) {
        muted = value;
    }
 
    private static void applyVolume(Clip clip, float volume) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = volume <= 0f ? gain.getMinimum() : (float) (20.0 * Math.log10(volume));
            gain.setValue(Math.max(gain.getMinimum(), Math.min(gain.getMaximum(), dB)));
        }
    }
}