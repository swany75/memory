/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memory;
 
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
 

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 * Font d'inspiració: https://www.coderslexicon.com/playing-and-thottling-sound-clips-in-java/
 */
public class SoundManager {
 
    private static String[] paths;
    private static Clip[]   clips;
 
    public static void load(String[] sounds) {
        paths = sounds;
        clips = new Clip[sounds.length];
 
        for (int i = 0; i < sounds.length; i++) {
            try {
                AudioInputStream ais = AudioSystem.getAudioInputStream(new File(sounds[i]));
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                clips[i] = clip;
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("SoundManager: could not load \"" + sounds[i] + "\": " + e.getMessage());
            }
        }
    }
    
    
    public static void reproduce(String path) {
        if (clips == null) return;

        for (int i = 0; i < paths.length; i++) {
            if (paths[i].equals(path) && clips[i] != null) {
                if (clips[i].isRunning()) {
                    clips[i].stop();
                }
                clips[i].setFramePosition(0);
                clips[i].start();
                return;
            }
        }

        System.err.println("SoundManager: \"" + path + "\" was not found. Did you load it?");
    }
    
    /* Metode amb sense solapament
    public static void reproduce(String path) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (Exception e) {
            System.err.println("SoundManager: error reproduciendo " + path);
        }
    }
    */
}
 