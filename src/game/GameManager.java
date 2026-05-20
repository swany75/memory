/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import model.Couple;
import model.Card;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class GameManager {

    private static final String IMAGE_PATH = "media/images/cards/imagen";
    private static final String IMAGE_EXT  = ".png";

    public int numRows;
    public int numCols;
    private Card[][] board;

    private static boolean running = false;
    private static boolean win     = false;

    private int matchesFound = 0;
    private int totalPairs;

    public GameManager() {}

    public void setDifficulty(int difficulty) {
        switch (difficulty) {
            case 0:  numRows = 2; numCols = 2;  break;
            case 1:  numRows = 2; numCols = 4;  break;
            case 2:  numRows = 3; numCols = 4;  break;
            case 3:  numRows = 4; numCols = 4;  break;
            case 4:  numRows = 4; numCols = 5;  break;
            case 5:  numRows = 4; numCols = 6;  break;
            case 6:  numRows = 5; numCols = 6;  break;
            case 7:  numRows = 6; numCols = 6;  break;
            case 8:  numRows = 6; numCols = 7;  break;
            case 9:  numRows = 7; numCols = 8;  break;
            case 10: numRows = 8; numCols = 8;  break;
            case 11: numRows = 8; numCols = 10; break;
            case 12: numRows = 10; numCols = 10; break;
            default: numRows = 4; numCols = 6;  break;
        }
    }

    public void startGame() {
        totalPairs   = (numRows * numCols) / 2;
        matchesFound = 0;
        win          = false;
        running      = true;
        board        = generateBoard();
    }

    private Card[][] generateBoard() {
        // Build a flat list with two Cards per pair (one Couple per pair)
        List<Card> cards = new ArrayList<>();
        for (int i = 1; i <= totalPairs; i++) {
            String imagePath = IMAGE_PATH + i + IMAGE_EXT;
            Couple couple = new Couple(imagePath);
            cards.add(couple.getCardA());
            cards.add(couple.getCardB());
        }

        // Shuffle
        Collections.shuffle(cards);

        // Fill the board matrix
        Card[][] result = new Card[numRows][numCols];
        int index = 0;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                result[row][col] = cards.get(index++);
            }
        }
        return result;
    }

    /**
     * Called by GamePanel when two cards are flipped.
     * Returns true if they match.
     */
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

    public Card[][] getBoard()    { return board;   }
    public static boolean isRunning()    { return running; }
    public boolean isWin()        { return win;     }

    public static String getGameStatus() {
        return win ? "You Win!" : "You Lose!";
    }
}