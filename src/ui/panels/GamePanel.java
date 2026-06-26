/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.panels;

import utils.PopUpManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.Card;
import model.Casella;
import core.GameManager;
import ui.components.Timer;
import utils.SoundManager;
import core.settings.GameSettings;
import utils.data.FileWrite;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import utils.ImageManager;
import ui.components.StatusBar;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class GamePanel extends JPanel {

    private static final String HISTORY_FILE = "media/files/historial";

    private GameManager gameManager;
    private Timer timer;
    private Casella[][] casellas;
    private boolean inGame = false;
    private ImageIcon welcomeIcon;
    private StatusBar statusBar;
    private SoundManager soundManager;
    
    private Casella firstFlipped  = null;
    private Casella secondFlipped = null;
    private boolean waitingForReset = false;
    private Image boardBackground;
    private int currentDifficulty = GameSettings.getDifficulty();
    
    /**
     * Crea el panel principal de juego y precarga recursos visuales.
     *
     * @param sb    barra de estado
     * @param timer temporizador del juego
     * @param sm    gestor de sonido
     */
    public GamePanel(StatusBar sb, Timer timer, SoundManager sm) {
        this.gameManager = new GameManager(sb, timer, sm);
        this.statusBar   = sb;
        this.soundManager = sm;
        this.timer       = timer;
        this.welcomeIcon = ImageManager.loadIcon("media/images/mainImage.jpg");
        this.boardBackground  = ImageManager.loadIcon("media/images/board.png").getImage();
        this.setBackground(Color.WHITE);

        this.addMouseListener(new StartGameClickListener());
    }

    /**
     * Inicia una nueva partida, configura el temporizador y construye el tablero.
     */
    public void startGame() {
        currentDifficulty = GameSettings.getDifficulty();
        gameManager.setDifficulty(currentDifficulty);
        try {
            gameManager.startGame();
        } catch (IllegalStateException ex) {
            statusBar.setText("Not enough cards");
            PopUpManager.displayMessage(
                "Not enough cards for the selected difficulty.",
                "Not enough cards"
            );
            inGame = false;
            setBackground(Color.WHITE);
            removeAll();
            revalidate();
            repaint();
            return;
        }

        setBackground(new Color(24, 61, 39));

        inGame          = true;
        waitingForReset = false;
        firstFlipped    = null;
        secondFlipped   = null;

        
        timer.reset();
        timer.prepararCountdown(GameSettings.getTimerMinutes());
        timer.setOnTimeOut(new TimeOutHandler());
        timer.start();
        
        SoundManager.playMusic("media/music/gameTheme.wav");
        buildBoard();
    }

    /**
     * Genera la matriz visual de casillas y las añade al panel.
     */
    private void buildBoard() {
        this.removeAll();
        Card[][] board = gameManager.getBoard();

        setLayout(null);
        setBorder(null);

        casellas = new Casella[gameManager.numRows][gameManager.numCols];

        for (int row = 0; row < gameManager.numRows; row++) {
            for (int col = 0; col < gameManager.numCols; col++) {
                Casella casella = new Casella(board[row][col]);
                casellas[row][col] = casella;
                addFlipListener(casella);
                add(casella);
                int delay = (row * gameManager.numCols + col) * 40; // 👈 40ms entre carta y carta
                casella.fadeIn(delay);
            }
        }

        revalidate();
        repaint();
    }
    
    /**
     * Sustituye el listener de la casilla por el controlado desde el panel.
     *
     * @param casella casilla a la que añadir el listener
     */
    private void addFlipListener(Casella casella) {
        // Remove the default click listener from Casella
        // and manage flipping from here so we control game logic
        for (MouseListener listener : casella.getMouseListeners()) {
            casella.removeMouseListener(listener);
        }

        casella.addMouseListener(new CardFlipListener(casella));
    }

    /**
     * Gestiona un clic sobre una casilla y dispara la comparación si procede.
     *
     * @param casella casilla seleccionada
     */
    private void handleCardClick(Casella casella) {
        // Ignore clicks during reset delay or if already flipped/matched
        if (waitingForReset)              return;
        if (casella.isFlipping())         return;
        if (casella.getCard().isFlipped()) return;

        casella.flip();

        if (firstFlipped == null) {
            firstFlipped = casella;
        } else {
            secondFlipped = casella;
            checkMatch();
        }
    }

    /**
     * Comprueba si las dos cartas seleccionadas forman pareja.
     */
    private void checkMatch() {
        Card c1 = firstFlipped.getCard();
        Card c2 = secondFlipped.getCard();

        if (gameManager.checkMatch(c1, c2)) {
            SoundManager.playSound("media/sounds/match.wav");
            firstFlipped  = null;
            secondFlipped = null;

            if (!gameManager.isRunning()) {
                timer.stop();
                javax.swing.Timer endDelay = new javax.swing.Timer(600, new EndGameDelayListener());
                endDelay.setRepeats(false);
                endDelay.start();
            }
            
            statusBar.setRandomNiceMovePhrase(); 
            
        } else {
            SoundManager.playSound("media/sounds/error.wav");
            waitingForReset = true;
            Casella toFlipA = firstFlipped;
            Casella toFlipB = secondFlipped;

            javax.swing.Timer resetDelay = new javax.swing.Timer(900, new ResetDelayListener(toFlipA, toFlipB));
            
            resetDelay.setRepeats(false);
            resetDelay.start();
            
            statusBar.setRandomBadMovePhrase();
        }
    }

    /**
     * Detiene la partida y muestra la pantalla de final con guardado en historial.
     */
    private void showGameOver() {
        if (timer.isRunning()) {
            timer.stop();
        }
        SoundManager.stopMusic();
        
        if (gameManager.isWin()) {
            SoundManager.playSound("media/sounds/you_win.wav");
        } else {
            SoundManager.playSound("media/sounds/you_lose.wav");
        }
        
        statusBar.setText(gameManager.getGameStatus()); 
        appendHistoryEntry();
        int points = gameManager.getMatchesFound() * 100;
        String finalMessage = gameManager.getGameStatus() + "    Score: " + points + " pts";
        PopUpManager.displayMessage(finalMessage);
        boolean playAgain = PopUpManager.confirmAction("play again");
        if (playAgain) {
            startGame();
        } else {
            inGame = false;
            setBackground(Color.WHITE);
            removeAll();
            revalidate();
            repaint();
        }
    }

    /**
     * Añade la partida actual al fichero de historial.
     */
    private void appendHistoryEntry() {
        String player = GameSettings.getPlayerName();
        String timestamp = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        String difficultyTag = GameSettings.getDifficultyTag(currentDifficulty);
        int matches = gameManager.getMatchesFound();
        int totalPairs = gameManager.getTotalPairs();
        String result = gameManager.isWin() ? "WIN" : "LOSE";
        int points = matches * 100;
        int duration = timer.getGameDuration();

        String line = player + "\t" + timestamp + "\t" + difficultyTag + "\t" +
            matches + "/" + totalPairs + "\t" + result + "\t" +
            points + "pts" + "\t" + duration + "s";

        FileWrite writer = new FileWrite(HISTORY_FILE, true);
        writer.open();
        writer.writeLine(line);
        writer.close();
    }

    /**
     * Dibuja el fondo del tablero o la pantalla de bienvenida.
     *
     * @param g contexto gráfico
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame && boardBackground != null) {
            g.drawImage(boardBackground, 0, 0, getWidth(), getHeight(), this);
        }
        if (!inGame) drawWelcomeScreen(g);
    }

    /**
     * Recalcula el layout para mantener cartas cuadradas y centradas.
     */
    @Override
    public void doLayout() {
        if (!inGame || casellas == null) {
            super.doLayout();
            return;
        }

        int rows   = gameManager.numRows;
        int cols   = gameManager.numCols;
        int gap    = 4;
        int margin = 15;

        int availW = getWidth()  - 2 * margin;
        int availH = getHeight() - 2 * margin;

        // Tamaño máximo que puede tener cada carta (cuadrada) sin pasarse en ningún eje
        int cardByW  = (availW - gap * (cols - 1)) / cols;
        int cardByH  = (availH - gap * (rows - 1)) / rows;
        int cardSize = Math.min(cardByW, cardByH); // cuadrada, pero lo más grande posible

        // Tablero real
        int boardW = cardSize * cols + gap * (cols - 1);
        int boardH = cardSize * rows + gap * (rows - 1);

        // Centrado
        int startX = (getWidth()  - boardW) / 2;
        int startY = (getHeight() - boardH) / 2;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = startX + col * (cardSize + gap);
                int y = startY + row * (cardSize + gap);
                casellas[row][col].setBounds(x, y, cardSize, cardSize);
            }
        }
    }
    
    /**
     * Dibuja la pantalla de bienvenida cuando no hay partida activa.
     *
     * @param g contexto gráfico
     */
    private void drawWelcomeScreen(Graphics g) {
        if (welcomeIcon == null) return;
        g.drawImage(welcomeIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    private class StartGameClickListener extends MouseAdapter {
        /**
         * Lanza el diálogo de inicio al hacer clic en la pantalla.
         *
         * @param e evento del ratón
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            if (!inGame) {
                boolean start = PopUpManager.confirmAction("start a new Game");
                if (start) {
                    SoundManager.playSound("media/sounds/shuffle.wav");
                    startGame();
                }
                else repaint();
            }
        }
    }

    private class CardFlipListener extends MouseAdapter {
        private final Casella casella;

        /**
         * Crea el listener asociado a una casilla concreta.
         *
         * @param casella casilla asociada
         */
        private CardFlipListener(Casella casella) {
            this.casella = casella;
        }

        /**
         * Gestiona el clic de la casilla.
         *
         * @param e evento del ratón
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            handleCardClick(casella);
        }
    }

    private class EndGameDelayListener implements ActionListener {
        /**
         * Dispara el fin de partida tras el retardo configurado.
         *
         * @param e evento del temporizador
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            showGameOver();
        }
    }

    private class ResetDelayListener implements ActionListener {
        private final Casella toFlipA;
        private final Casella toFlipB;

        /**
         * Crea el listener que voltea las dos cartas tras fallo.
         *
         * @param toFlipA primera casilla
         * @param toFlipB segunda casilla
         */
        private ResetDelayListener(Casella toFlipA, Casella toFlipB) {
            this.toFlipA = toFlipA;
            this.toFlipB = toFlipB;
        }

        /**
         * Restablece el turno tras el retardo de error.
         *
         * @param e evento del temporizador
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            toFlipA.flip();
            toFlipB.flip();
            firstFlipped    = null;
            secondFlipped   = null;
            waitingForReset = false;
        }
    }

    private class TimeOutHandler implements Runnable {
        /**
         * Ejecuta el fin de partida cuando el tiempo se agota.
         */
        @Override
        public void run() {
            showGameOver();
        }
    }
}