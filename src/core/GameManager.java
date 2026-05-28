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

    /**
     * Crea el gestor lógico del juego y enlaza los componentes de interfaz.
     *
     * @param sb    barra de estado para mensajes del juego
     * @param timer temporizador asociado a la partida
     * @param sm    gestor de sonido global
     */
    public GameManager(StatusBar sb, Timer timer, SoundManager sm) {
        this.soundManager = sm;
        this.statusBar = sb;
        this.timer = timer;
        this.setDifficulty(12);
        
    }

    /**
     * Mapea un nivel de dificultad a su número de filas y columnas.
     *
     * @param difficulty nivel de dificultad (0-12)
     * @return array con filas y columnas
     */
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

    /**
     * Asigna el tamaño del tablero según el nivel de dificultad.
     *
     * @param difficulty nivel de dificultad
     */
    public void setDifficulty(int difficulty) {
        int[] dims = getDimsForDifficulty(difficulty);
        numRows = dims[0];
        numCols = dims[1];
    }

    /**
     * Inicializa la partida cargando recursos y generando el tablero lógico.
     *
     * @throws IllegalStateException si no hay suficientes imágenes para el nivel actual
     */
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

    /**
     * Genera el tablero lógico barajando imágenes y creando parejas de cartas.
     *
     * @return matriz de cartas lista para renderizar
     */
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
    /**
     * Baraja un array de cartas usando el algoritmo Fisher-Yates.
     *
     * @param arr array de cartas a mezclar
     */
    private void shuffleCardArray(Card[] arr) {
        java.util.Random rand = new java.util.Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            Card tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    /**
     * Lista las imágenes válidas del directorio de cartas, excluyendo el reverso.
     *
     * @return rutas absolutas de imágenes .png disponibles
     */
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

    /**
     * Baraja un array de cadenas usando el algoritmo Fisher-Yates.
     *
     * @param arr array de rutas a mezclar
     */
    private void shuffleStringArray(String[] arr) {
        java.util.Random rand = new java.util.Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            String tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    /**
     * Comprueba si dos cartas forman pareja y actualiza el estado de la partida.
     *
     * @param c1 primera carta seleccionada
     * @param c2 segunda carta seleccionada
     * @return {@code true} si coinciden; {@code false} en caso contrario
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

    /**
     * Devuelve el número de parejas encontradas.
     *
     * @return parejas acertadas
     */
    public int getMatchesFound() { return matchesFound; }
    
    /**
     * Devuelve el tablero lógico actual.
     *
     * @return matriz de cartas
     */
    public Card[][]      getBoard()       { return board;   }
    
    /**
     * Indica si hay una partida en curso.
     *
     * @return {@code true} si el juego está activo
     */
    public static boolean isRunning()     { return running; }
    
    /**
     * Indica si la partida terminó en victoria.
     *
     * @return {@code true} si se han encontrado todas las parejas
     */
    public boolean        isWin()         { return win;     }
    
    /**
     * Devuelve el total de parejas que debe encontrar el jugador.
     *
     * @return número total de parejas
     */
    public int            getTotalPairs() { return totalPairs; }
    

    /**
     * Devuelve el estado final de la partida en formato legible.
     *
     * @return "You Win!" si se gana; "You Lose..." en caso contrario
     */
    public static String getGameStatus() {
        String res = "";
        if (win) {
            res = "You Win!";
        } else {
            res = "You Lose...";
        }
        return res;
    }
    
    /**
     * Fuerza la finalización de la partida por tiempo agotado.
     */
    public static void timeOut() {
        win     = false;
        running = false;
    }

}