/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

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

    /**
     * Reproduce un efecto de sonido puntual si no está silenciado.
     *
     * @param path ruta del archivo de audio
     */
    public static void playSound(String path) {
        if (muted) return;
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            applyVolume(clip, soundVolume);
            clip.addLineListener(new ClipStopListener(clip));
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("SoundManager: error reproduciendo \"" + path + "\": " + e.getMessage());
        }
    }

    /**
     * Reproduce música de fondo en bucle, reemplazando la pista anterior.
     *
     * @param path ruta del archivo de audio
     */
    public static void playMusic(String path) {
        // Detener siempre la música anterior, independientemente del mute
        if (musicClip != null) {
            musicClip.stop();
            musicClip.close();
            musicClip = null;
        }
        if (muted) return;
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

    /**
     * Detiene la música de fondo y libera recursos.
     */
    public static void stopMusic() {
        if (musicClip != null) {
            musicClip.stop();
            musicClip.close();
            musicClip = null;
        }
    }

    /**
     * Ajusta el volumen de efectos de sonido.
     *
     * @param volume volumen lineal entre 0.0 y 1.0
     */
    public static void setSoundVolume(float volume) {
        soundVolume = Math.max(0f, Math.min(1f, volume));
    }

    /**
     * Ajusta el volumen de la música y actualiza el clip activo.
     *
     * @param volume volumen lineal entre 0.0 y 1.0
     */
    public static void setMusicVolume(float volume) {
        musicVolume = Math.max(0f, Math.min(1f, volume));
        if (musicClip != null) applyVolume(musicClip, musicVolume);
    }

    /**
     * Activa o desactiva el silencio global.
     *
     * @param value estado de silencio
     */
    public static void setMuted(boolean value) {
        muted = value;
        if (musicClip != null) {
            if (muted) musicClip.stop();
            else musicClip.start();
        }
    }

    /**
     * Aplica el volumen al clip usando escala logarítmica en dB.
     *
     * @param clip   clip al que aplicar el volumen
     * @param volume volumen lineal entre 0.0 y 1.0
     */
    private static void applyVolume(Clip clip, float volume) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = volume <= 0f ? gain.getMinimum() : (float) (20.0 * Math.log10(volume));
            gain.setValue(Math.max(gain.getMinimum(), Math.min(gain.getMaximum(), dB)));
        }
    }

    private static class ClipStopListener implements LineListener {
        private final Clip clip;

        /**
         * Crea un listener que cierra el clip al finalizar.
         *
         * @param clip clip asociado
         */
        private ClipStopListener(Clip clip) {
            this.clip = clip;
        }

        /**
         * Cierra el clip cuando la reproducción se detiene.
         *
         * @param event evento de línea
         */
        @Override
        public void update(LineEvent event) {
            if (event.getType() == LineEvent.Type.STOP) {
                clip.close();
            }
        }
    }

}