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

    public GamePanel(StatusBar sb, Timer timer, SoundManager sm) {
        this.gameManager = new GameManager(sb, timer, sm);
        this.statusBar   = sb;
        this.soundManager = sm;
        this.timer       = timer;
        this.welcomeIcon = ImageManager.loadIcon("media/images/LogoUIB (Ben fet).png");
        this.setBackground(Color.WHITE);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!inGame) {
                    boolean start = PopUpManager.confirmAction("start a new Game");
                    if (start) startGame();
                    else repaint();
                }
            }
        });
    }

    public void startGame() {
        setBackground(new Color(24, 61, 39));
        gameManager.setDifficulty(1);
        gameManager.startGame();

        inGame          = true;
        waitingForReset = false;
        firstFlipped    = null;
        secondFlipped   = null;

        timer.reset();
        timer.prepararCountdown(2);
        timer.start();

        buildBoard();
    }

    private void buildBoard() {
        this.removeAll();
        Card[][] board = gameManager.getBoard();

        setLayout(new GridLayout(gameManager.numRows, gameManager.numCols, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        casellas = new Casella[gameManager.numRows][gameManager.numCols];

        for (int row = 0; row < gameManager.numRows; row++) {
            for (int col = 0; col < gameManager.numCols; col++) {
                Casella casella = new Casella(board[row][col]);
                casellas[row][col] = casella;
                addFlipListener(casella);
                add(casella);
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
            firstFlipped  = null;
            secondFlipped = null;

            if (!gameManager.isRunning()) {
                timer.stop();               // ← detener timer al ganar
                javax.swing.Timer endDelay = new javax.swing.Timer(600, e -> showGameOver());
                endDelay.setRepeats(false);
                endDelay.start();
            }
        } else {
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
        }
    }

    private void showGameOver() {
        timer.stop();
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
        if (!inGame) drawWelcomeScreen(g);
    }

    private void drawWelcomeScreen(Graphics g) {
        if (welcomeIcon == null) return;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,    RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,   RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        int margin    = 100;
        int maxWidth  = getWidth()  - margin;
        int maxHeight = getHeight() - margin - 50;
        int imgWidth  = welcomeIcon.getIconWidth();
        int imgHeight = welcomeIcon.getIconHeight();
        double ratio  = (double) imgWidth / imgHeight;

        int finalWidth  = maxWidth;
        int finalHeight = (int)(finalWidth / ratio);
        if (finalHeight > maxHeight) {
            finalHeight = maxHeight;
            finalWidth  = (int)(finalHeight * ratio);
        }

        int x = (getWidth()  - finalWidth)  / 2;
        int y = (getHeight() - finalHeight) / 2 - 20;

        g2d.drawImage(welcomeIcon.getImage(), x, y, finalWidth, finalHeight, null);
        g2d.setColor(Color.GRAY);
        g2d.setFont(new Font("Times New Roman", Font.BOLD, 24));
        String subtitle = "Marti Figuls Nolla & Juan Dalmau Santandreu";
        FontMetrics fm  = g2d.getFontMetrics();
        g2d.drawString(subtitle, (getWidth() - fm.stringWidth(subtitle)) / 2, y + finalHeight + 40);
    }
}