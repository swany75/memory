/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.Card;
import model.Casella;
import game.GameManager;
import game.Timer;
import audio.SoundManager;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class GamePanel extends JPanel {

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
    
    public GamePanel(StatusBar sb, Timer timer, SoundManager sm) {
        this.gameManager = new GameManager(sb, timer, sm);
        this.statusBar   = sb;
        this.soundManager = sm;
        this.timer       = timer;
        this.welcomeIcon = ImageManager.loadIcon("media/images/mainImage.jpg");
        this.boardBackground  = ImageManager.loadIcon("media/images/board.png").getImage();
        this.setBackground(Color.WHITE);

        this.addMouseListener(new MouseAdapter() {
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
        });
    }

    public void startGame() {
        setBackground(new Color(24, 61, 39));
        gameManager.setDifficulty(5);
        gameManager.startGame();

        inGame          = true;
        waitingForReset = false;
        firstFlipped    = null;
        secondFlipped   = null;

        
        timer.reset();
        timer.prepararCountdown(2);
        timer.setOnTimeOut(this::showGameOver); 
        timer.start();
        
        SoundManager.playMusic("media/music/gameTheme.wav");
        buildBoard();
    }

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
    
    private void addFlipListener(Casella casella) {
        // Remove the default click listener from Casella
        // and manage flipping from here so we control game logic
        for (var listener : casella.getMouseListeners()) {
            casella.removeMouseListener(listener);
        }

        casella.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleCardClick(casella);
            }
        });
    }

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

    private void checkMatch() {
        Card c1 = firstFlipped.getCard();
        Card c2 = secondFlipped.getCard();

        if (gameManager.checkMatch(c1, c2)) {
            SoundManager.playSound("media/sounds/match.wav");
            firstFlipped  = null;
            secondFlipped = null;

            if (!gameManager.isRunning()) {
                timer.stop();
                javax.swing.Timer endDelay = new javax.swing.Timer(600, e -> showGameOver());
                endDelay.setRepeats(false);
                endDelay.start();
            }
            
            statusBar.setRandomNiceMovePhrase(); 
            
        } else {
            SoundManager.playSound("media/sounds/error.wav");
            waitingForReset = true;
            Casella toFlipA = firstFlipped;
            Casella toFlipB = secondFlipped;

            javax.swing.Timer resetDelay = new javax.swing.Timer(900, e -> {
                toFlipA.flip();
                toFlipB.flip();
                firstFlipped    = null;
                secondFlipped   = null;
                waitingForReset = false;
            });
            
            resetDelay.setRepeats(false);
            resetDelay.start();
            
            statusBar.setRandomBadMovePhrase();
        }
    }

    private void showGameOver() {
        timer.stop();
        SoundManager.stopMusicWithFadeOut();
        
        if (gameManager.isWin()) {
            SoundManager.playSound("media/sounds/you_win.wav");
        } else {
            SoundManager.playSound("media/sounds/you_lose.wav");
        }
        
        statusBar.setText(gameManager.getGameStatus()); 
        PopUpManager.displayMessage(gameManager.getGameStatus());
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame && boardBackground != null) {
            g.drawImage(boardBackground, 0, 0, getWidth(), getHeight(), this);
        }
        if (!inGame) drawWelcomeScreen(g);
    }

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
    
    private void drawWelcomeScreen(Graphics g) {
        if (welcomeIcon == null) return;
        g.drawImage(welcomeIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}