/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.settings;

import utils.SoundManager;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */
public final class GameSettings {

    private static final int MIN_DIFFICULTY = 0;
    private static final int MAX_DIFFICULTY = 12;
    private static final int MIN_TIMER_MINUTES = 1;
    private static final int MAX_TIMER_MINUTES = 10;
    private static final int MIN_VOLUME = 0;
    private static final int MAX_VOLUME = 100;
    private static final int MAX_PLAYER_NAME_LENGTH = 12;

    private static final int DEFAULT_DIFFICULTY = 5;
    private static final int DEFAULT_TIMER_MINUTES = 2;
    private static final int DEFAULT_SOUND_VOLUME = 100;
    private static final int DEFAULT_MUSIC_VOLUME = 80;
    private static final String DEFAULT_CARDS_DIR = "media/images/cards";
    private static final String DEFAULT_BACK_IMAGE_PATH = "media/images/backImage1.png";
    private static final String[] DEFAULT_PLAYER_NAMES = {"Juan", "Marti"};

    private static int difficulty = DEFAULT_DIFFICULTY;
    private static int timerMinutes = DEFAULT_TIMER_MINUTES;
    private static int soundVolume = DEFAULT_SOUND_VOLUME;
    private static int musicVolume = DEFAULT_MUSIC_VOLUME;
    private static boolean muted = false;
    private static String cardsDir = DEFAULT_CARDS_DIR;
    private static String backImagePath = DEFAULT_BACK_IMAGE_PATH;
    private static String playerName = randomDefaultPlayerName();

    private GameSettings() {
    }

    /**
     * Devuelve la dificultad actual del juego.
     *
     * @return nivel de dificultad
     */
    public static int getDifficulty() {
        return difficulty;
    }

    /**
     * Actualiza la dificultad, limitándola al rango permitido.
     *
     * @param value nuevo nivel de dificultad
     */
    public static void setDifficulty(int value) {
        difficulty = clamp(value, MIN_DIFFICULTY, MAX_DIFFICULTY);
    }

    /**
     * Devuelve la etiqueta descriptiva de un nivel de dificultad.
     *
     * @param value nivel numérico de dificultad
     * @return etiqueta legible ("Fácil", "Normal" o "Difícil")
     */
    public static String getDifficultyLabel(int value) {
        if (value <= 4) return "Easy";
        if (value <= 8) return "Normal";
        return "Hard";
    }

    /**
     * Devuelve la etiqueta normalizada de un nivel de dificultad.
     *
     * @param value nivel numérico de dificultad
     * @return etiqueta lógica ("EASY", "NORMAL" o "HARD")
     */
    public static String getDifficultyTag(int value) {
        if (value <= 4) return "EASY";
        if (value <= 8) return "NORMAL";
        return "HARD";
    }

    /**
     * Devuelve el tiempo límite de la partida en minutos.
     *
     * @return minutos configurados
     */
    public static int getTimerMinutes() {
        return timerMinutes;
    }

    /**
     * Actualiza el tiempo límite de la partida.
     *
     * @param minutes minutos de juego
     */
    public static void setTimerMinutes(int minutes) {
        timerMinutes = clamp(minutes, MIN_TIMER_MINUTES, MAX_TIMER_MINUTES);
    }

    /**
     * Devuelve el volumen de efectos en porcentaje.
     *
     * @return volumen de sonido (0-100)
     */
    public static int getSoundVolume() {
        return soundVolume;
    }

    /**
     * Ajusta el volumen de efectos y lo propaga al gestor de sonido.
     *
     * @param value volumen en porcentaje (0-100)
     */
    public static void setSoundVolume(int value) {
        soundVolume = clamp(value, MIN_VOLUME, MAX_VOLUME);
        SoundManager.setSoundVolume(soundVolume / 100f);
    }

    /**
     * Devuelve el volumen de la música en porcentaje.
     *
     * @return volumen de música (0-100)
     */
    public static int getMusicVolume() {
        return musicVolume;
    }

    /**
     * Ajusta el volumen de música y lo propaga al gestor de sonido.
     *
     * @param value volumen en porcentaje (0-100)
     */
    public static void setMusicVolume(int value) {
        musicVolume = clamp(value, MIN_VOLUME, MAX_VOLUME);
        SoundManager.setMusicVolume(musicVolume / 100f);
    }

    /**
     * Devuelve el directorio de cartas configurado.
     *
     * @return ruta del directorio de cartas
     */
    public static String getCardsDir() {
        return cardsDir;
    }

    /**
     * Actualiza el directorio de cartas si el valor es válido.
     *
     * @param directory nueva ruta del directorio de imágenes
     */
    public static void setCardsDir(String directory) {
        if (directory == null || directory.trim().isEmpty()) return;
        cardsDir = directory;
    }

    /**
     * Devuelve la ruta de la imagen de reverso.
     *
     * @return ruta del reverso
     */
    public static String getBackImagePath() {
        return backImagePath;
    }

    /**
     * Actualiza la ruta de la imagen de reverso si es válida.
     *
     * @param path ruta del reverso
     */
    public static void setBackImagePath(String path) {
        if (path == null || path.trim().isEmpty()) return;
        backImagePath = path;
    }

    /**
     * Devuelve el nombre actual del jugador.
     *
     * @return nombre del jugador
     */
    public static String getPlayerName() {
        return playerName;
    }

    /**
     * Actualiza el nombre del jugador si es válido.
     *
     * @param name nombre del jugador
     */
    public static void setPlayerName(String name) {
        if (name == null) return;
        String trimmed = name.trim();
        if (trimmed.isEmpty()) return;
        if (trimmed.length() > MAX_PLAYER_NAME_LENGTH) {
            trimmed = trimmed.substring(0, MAX_PLAYER_NAME_LENGTH);
        }
        playerName = trimmed;
    }

    /**
     * Indica si el sonido está silenciado.
     *
     * @return {@code true} si está muteado
     */
    public static boolean isMuted() {
        return muted;
    }

    /**
     * Activa o desactiva el modo silencio global.
     *
     * @param value estado de silencio
     */
    public static void setMuted(boolean value) {
        muted = value;
        SoundManager.setMuted(value);
    }

    /**
     * Restaura todos los valores por defecto del juego.
     */
    public static void resetDefaults() {
        setDifficulty(DEFAULT_DIFFICULTY);
        setTimerMinutes(DEFAULT_TIMER_MINUTES);
        setSoundVolume(DEFAULT_SOUND_VOLUME);
        setMusicVolume(DEFAULT_MUSIC_VOLUME);
        setMuted(false);
        setCardsDir(DEFAULT_CARDS_DIR);
        setBackImagePath(DEFAULT_BACK_IMAGE_PATH);
        setPlayerName(randomDefaultPlayerName());
    }

    /**
     * Restringe un valor dentro de un rango.
     *
     * @param value valor a limitar
     * @param min   mínimo permitido
     * @param max   máximo permitido
     * @return valor acotado
     */
    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Selecciona un nombre de jugador por defecto de forma aleatoria.
     *
     * @return nombre por defecto
     */
    private static String randomDefaultPlayerName() {
        int idx = (int) (Math.random() * DEFAULT_PLAYER_NAMES.length);
        return DEFAULT_PLAYER_NAMES[idx];
    }
}
