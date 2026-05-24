/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core;

import utils.SoundManager;
import core.settings.GameSettings;
import ui.components.Timer;
import java.io.File;
import model.Couple;
import model.Card;
import ui.components.StatusBar;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class GameManager {

    private int totalAvailableImages = 0;
    private String[] availableImages = new String[0];

    public int numRows;
    public int numCols;
    private Card[][] board;

    private Timer timer;     
    private StatusBar statusBar;
    private SoundManager soundManager;
    
    
    private static boolean running = false;
    private static boolean win     = false;

    private int matchesFound = 0;
    private int totalPairs;

    public GameManager(StatusBar sb, Timer timer, SoundManager sm) {
        this.soundManager = sm;
        this.statusBar = sb;
        this.timer = timer;
        this.setDifficulty(12);
        
    }

    public int getMaxDifficulty() {
        int available = countAvailableImages();
        for (int d = 12; d >= 0; d--) {
            int[] dims = getDimsForDifficulty(d);
            int pairs = (dims[0] * dims[1]) / 2;
            if (pairs <= available) {
                return d;
            }
        }
        return 0;
    }

    private int[] getDimsForDifficulty(int difficulty) {
        switch (difficulty) {
            case 0:  return new int[]{2,  2};
            case 1:  return new int[]{2,  4};
            case 2:  return new int[]{3,  4};
            case 3:  return new int[]{4,  4};
            case 4:  return new int[]{4,  5};
            case 5:  return new int[]{4,  6};
            case 6:  return new int[]{5,  6};
            case 7:  return new int[]{6,  8};
            case 8:  return new int[]{6,  8};
            case 9:  return new int[]{6, 10};
            case 10: return new int[]{8, 10};
            case 11: return new int[]{8, 10};
            case 12: return new int[]{8, 12};
            default: return new int[]{2,  4};
        }
    }

    public void setDifficulty(int difficulty) {
        int[] dims = getDimsForDifficulty(difficulty);
        numRows = dims[0];
        numCols = dims[1];
    }

    public void startGame() {
        availableImages = listAvailableImages();
        totalAvailableImages = availableImages.length;
        totalPairs           = (numRows * numCols) / 2;
        matchesFound         = 0;
        win                  = false;

        if (totalPairs > totalAvailableImages) {
            running = false;
            throw new IllegalStateException(
                "Not enough images: need " + totalPairs + ", have " + totalAvailableImages
            );
        }

        running = true;
        board = generateBoard();
    }

    private Card[][] generateBoard() {
        // 1. Shuffle de las imágenes disponibles
        String[] pool = new String[availableImages.length];
        System.arraycopy(availableImages, 0, pool, 0, availableImages.length);
        shuffleStringArray(pool);

        // 2. Crear las cartas con las primeras totalPairs parejas
        Card[] cards = new Card[totalPairs * 2];
        int cardIdx = 0;
        for (int i = 0; i < totalPairs; i++) {
            String imagePath = pool[i];
            Couple couple = new Couple(imagePath);
            cards[cardIdx++] = couple.getCardA();
            cards[cardIdx++] = couple.getCardB();
        }

        // 3. Shuffle de cartas
        shuffleCardArray(cards);

        // 4. Colocar en el tablero
        Card[][] result = new Card[numRows][numCols];
        int idx = 0;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                result[row][col] = cards[idx++];
            }
        }
        return result;
    }

    // Fisher-Yates shuffle para Card[]
    private void shuffleCardArray(Card[] arr) {
        java.util.Random rand = new java.util.Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            Card tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    private int countAvailableImages() {
        return listAvailableImages().length;
    }

    private String[] listAvailableImages() {
        File folder = new File(GameSettings.getCardsDir());
        if (!folder.exists() || !folder.isDirectory()) return new String[0];

        File[] files = folder.listFiles();
        if (files == null) return new String[0];

        int count = 0;
        for (File f : files) {
            if (!f.isFile()) continue;
            String name = f.getName().toLowerCase();
            if (name.endsWith(".png") && !name.startsWith("backimage")) {
                count++;
            }
        }
        if (count == 0) return new String[0];

        String[] images = new String[count];
        int idx = 0;
        for (File f : files) {
            if (!f.isFile()) continue;
            String name = f.getName().toLowerCase();
            if (name.endsWith(".png") && !name.startsWith("backimage")) {
                images[idx++] = f.getPath();
            }
        }
        return images;
    }

    private void shuffleStringArray(String[] arr) {
        java.util.Random rand = new java.util.Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            String tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    public boolean checkMatch(Card c1, Card c2) {
        if (c1.getFrontImage().equals(c2.getFrontImage())) {
            matchesFound++;
            if (matchesFound == totalPairs) {
                win     = true;
                running = false;
            }
            return true;
        }
        return false;
    }

    public int getMatchesFound() { return matchesFound; }
    public Card[][]      getBoard()       { return board;   }
    public static boolean isRunning()     { return running; }
    public boolean        isWin()         { return win;     }
    public int            getTotalPairs() { return totalPairs; }

    public static String getGameStatus() {
        String res = "";
        if (win) {
            res = "You Win!";
        } else {
            res = "You Lose...";
        }
        return res;
    }
    
    public static void timeOut() {
        win     = false;
        running = false;
    }

}