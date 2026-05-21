/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import audio.SoundManager;
import java.awt.AlphaComposite;
import ui.ImageManager;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.Timer;
import javax.swing.JPanel;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 *
 * @source Clase inspirada amb la clase Casella 
 * dels codis d'exemple: TableroConGiroAnimado
 */

public class Casella extends JPanel {

    private Card card;
    private double scale = 1.0;
    private boolean contractingPhase;
    private Timer flipTimer;
    private SoundManager sm;
    private float alpha = 0f;    
    
    // Almacenamos las imágenes en memoria para no leer el disco constantemente
    private Image frontImage;
    private Image backImage;

    public Casella(Card card) {
        this.card = card;
        setOpaque(false);
        
        // PRECARGA: Leemos el disco una única vez al instanciar la casilla
        // (Asumiendo que card.getFrontImage() y card.getBackImage() devuelven los String de las rutas)
        this.frontImage = ImageManager.loadIcon(card.getFrontImage()).getImage();
        this.backImage  = ImageManager.loadIcon(card.getBackImage()).getImage();
    }

    private void startFlip() {
        if (flipTimer != null && flipTimer.isRunning()) return;

        contractingPhase = true;
        flipTimer = new Timer(15, null);

        flipTimer.addActionListener(event -> {
            if (contractingPhase) {
                scale -= 0.1;
                if (scale <= 0.0) {
                    scale = 0.0;
                    sm.playSound("media/sounds/flip.wav");
                    card.flip();
                    contractingPhase = false;
                }
            } else {
                scale += 0.1;
                if (scale >= 1.0) {
                    scale = 1.0;
                    flipTimer.stop();
                }
            }
            repaint();
        });

        flipTimer.start();
    }

    public void flip() {
        startFlip();
    }

    public boolean isFlipping() {
        return flipTimer != null && flipTimer.isRunning();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)); // 👈

        Image currentImage = card.isFlipped() ? frontImage : backImage;
        int availableWidth  = getWidth();
        int availableHeight = getHeight();
        int squareSize  = Math.min(availableWidth, availableHeight);
        int offsetX     = (availableWidth  - squareSize) / 2;
        int offsetY     = (availableHeight - squareSize) / 2;
        int scaledWidth = (int) (squareSize * scale);
        int x = offsetX + (squareSize - scaledWidth) / 2;
        int y = offsetY;
        g2.drawImage(currentImage, x, y, scaledWidth, squareSize, this);
    }

    public Card getCard() {
        return card;
    }
    
    public void fadeIn(int delayMs) {
        alpha = 0f;
        javax.swing.Timer delay = new javax.swing.Timer(delayMs, null);
        delay.setRepeats(false);
        delay.addActionListener(e -> {
            javax.swing.Timer fadeTimer = new javax.swing.Timer(15, null);
            fadeTimer.addActionListener(ev -> {
                alpha += 0.05f;
                if (alpha >= 1f) {
                    alpha = 1f;
                    fadeTimer.stop();
                }
                repaint();
            });
            fadeTimer.start();
        });
        delay.start();
    }
    
}
