/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memory;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */
public class GameManager { // Gestio Partida
    
    private static boolean running = false;
    private static boolean win = false;
    
    int NUM_ROWS;
    int NUM_COLS;
    int[][] MATRIX;


    private GameManager() {
        generateInitialMatrix();
    }

    private void generateInitialMatrix() {
        // Implementación para generar la matriz inicial
    }


    public static boolean isRunning() {
        return running;
    }

    public void setDificulty(int dificulty) {
        switch (dificulty) {
            case 0: NUM_ROWS = 2; NUM_COLS = 0; break;
            case 1:  NUM_ROWS = 2; NUM_COLS = 4;   break;
            case 2:  NUM_ROWS = 3; NUM_COLS = 4;   break;
            case 3:  NUM_ROWS = 4; NUM_COLS = 4;   break;
            case 4:  NUM_ROWS = 4; NUM_COLS = 5;   break;
            case 5:  NUM_ROWS = 4; NUM_COLS = 6;   break;
            case 6:  NUM_ROWS = 5; NUM_COLS = 6;   break;
            case 7:  NUM_ROWS = 6; NUM_COLS = 6;   break;
            case 8:  NUM_ROWS = 6; NUM_COLS = 7;   break;
            case 9:  NUM_ROWS = 7; NUM_COLS = 8;   break;
            case 10: NUM_ROWS = 8; NUM_COLS = 8;   break;
            case 11: NUM_ROWS = 8; NUM_COLS = 10;  break;
            case 12: NUM_ROWS = 10; NUM_COLS = 10; break;
            default: NUM_ROWS = 4; NUM_COLS = 6;   break;
        }
        MATRIX = new int[NUM_ROWS][NUM_COLS];
    }

    public static String getGameStatus() {
        String res = "";
        if (win) {
            res = "You Win!";
        } else {
            res = "You Lose!";
        }
        return res;
    }
    
}
