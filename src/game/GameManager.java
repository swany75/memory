/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import audio.SoundManager;
import java.io.File;
import model.Couple;
import model.Card;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ui.StatusBar;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class GameManager {

    private static final String IMAGE_DIR  = "media/images/cards/";
    private static final String IMAGE_PATH = IMAGE_DIR + "imagen";
    private static final String IMAGE_EXT  = ".png";

    private int totalAvailableImages = 0;

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
        totalAvailableImages = countAvailableImages();
        totalPairs           = (numRows * numCols) / 2;
        matchesFound         = 0;
        win                  = false;
        running              = true;

        if (totalPairs > totalAvailableImages) {
            throw new IllegalStateException(
                "Not enough images: need " + totalPairs + ", have " + totalAvailableImages
            );
        }

        board = generateBoard();
    }

    private Card[][] generateBoard() {
        // 1. Lista de todos los índices disponibles
        List<Integer> allIndices = new ArrayList<>();
        for (int i = 1; i <= totalAvailableImages; i++) {
            allIndices.add(i);
        }

        // 2. Shuffle de parejas → coger las primeras totalPairs
        Collections.shuffle(allIndices);
        List<Integer> selectedIndices = allIndices.subList(0, totalPairs);

        // 3. Crear las cartas con las parejas seleccionadas
        List<Card> cards = new ArrayList<>();
        for (int index : selectedIndices) {
            String imagePath = IMAGE_PATH + index + IMAGE_EXT;
            Couple couple = new Couple(imagePath);
            cards.add(couple.getCardA());
            cards.add(couple.getCardB());
        }

        // 4. Shuffle de cartas
        Collections.shuffle(cards);

        // 5. Colocar en el tablero
        Card[][] result = new Card[numRows][numCols];
        int idx = 0;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                result[row][col] = cards.get(idx++);
            }
        }
        return result;
    }

    private int countAvailableImages() {
        int count = 0;
        File folder = new File(IMAGE_DIR);
        if (!folder.exists() || !folder.isDirectory()) return 0;

        File[] files = folder.listFiles();
        if (files == null) return 0;

        for (File f : files) {
            String name = f.getName();
            if (name.startsWith("imagen") && name.endsWith(IMAGE_EXT)) {
                count++;
            }
        }
        return count;
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