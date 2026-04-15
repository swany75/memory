/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memory;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 *
 * @author Juan
 */
public class SoundManager { // Gestió dels sons

    /**
     * Reproduces a sound from the given file path.
     *
     * @param routeSound the path to the sound file (e.g. "sounds/click.wav")
     */
    
    public void reproduce(String routeSound) throws IOException {
        try {
            File soundFile = new File(routeSound);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Unsupported audio format: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.err.println("Audio line unavailable: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading sound file: " + e.getMessage());
        }
    }

}
