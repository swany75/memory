/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import audio.SoundManager;
import java.io.File;

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

    public static int getDifficulty() {
        return difficulty;
    }

    public static void setDifficulty(int value) {
        difficulty = clamp(value, MIN_DIFFICULTY, MAX_DIFFICULTY);
    }

    public static String getDifficultyLabel(int value) {
        if (value <= 4) return "Fácil";
        if (value <= 8) return "Normal";
        return "Difícil";
    }

    public static String getDifficultyTag(int value) {
        if (value <= 4) return "EASY";
        if (value <= 8) return "NORMAL";
        return "HARD";
    }

    public static int getTimerMinutes() {
        return timerMinutes;
    }

    public static void setTimerMinutes(int minutes) {
        timerMinutes = clamp(minutes, MIN_TIMER_MINUTES, MAX_TIMER_MINUTES);
    }

    public static int getSoundVolume() {
        return soundVolume;
    }

    public static void setSoundVolume(int value) {
        soundVolume = clamp(value, MIN_VOLUME, MAX_VOLUME);
        SoundManager.setSoundVolume(soundVolume / 100f);
    }

    public static int getMusicVolume() {
        return musicVolume;
    }

    public static void setMusicVolume(int value) {
        musicVolume = clamp(value, MIN_VOLUME, MAX_VOLUME);
        SoundManager.setMusicVolume(musicVolume / 100f);
    }

    public static String getCardsDir() {
        return cardsDir;
    }

    public static void setCardsDir(String directory) {
        if (directory == null || directory.trim().isEmpty()) return;
        cardsDir = directory;
    }

    public static String getBackImagePath() {
        return backImagePath;
    }

    public static void setBackImagePath(String path) {
        if (path == null || path.trim().isEmpty()) return;
        backImagePath = path;
    }

    public static String findBackImagePath(String directory) {
        if (directory == null) return null;
        File folder = new File(directory);
        if (!folder.exists() || !folder.isDirectory()) return null;
        File[] files = folder.listFiles();
        if (files == null) return null;

        for (File f : files) {
            if (!f.isFile()) continue;
            String name = f.getName().toLowerCase();
            if (name.startsWith("backimage") && name.endsWith(".png")) {
                return f.getPath();
            }
        }
        return null;
    }

    public static String getPlayerName() {
        return playerName;
    }

    public static void setPlayerName(String name) {
        if (name == null) return;
        String trimmed = name.trim();
        if (trimmed.isEmpty()) return;
        playerName = trimmed;
    }

    public static boolean isMuted() {
        return muted;
    }

    public static void setMuted(boolean value) {
        muted = value;
        SoundManager.setMuted(value);
    }

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

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private static String randomDefaultPlayerName() {
        int idx = (int) (Math.random() * DEFAULT_PLAYER_NAMES.length);
        return DEFAULT_PLAYER_NAMES[idx];
    }
}
