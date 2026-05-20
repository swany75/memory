/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import ui.ImageManager;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
                    card.flip(); // Actualiza el estado lógico (flipped = !flipped)
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

        // Filtros de alta calidad
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        Image currentImage = card.isFlipped() ? frontImage : backImage;

        // 1. FORZAR PROPORCIÓN CUADRADA: Buscamos el lado del cuadrado más grande que cabe
        int availableWidth = getWidth();
        int availableHeight = getHeight();
        int squareSize = Math.min(availableWidth, availableHeight);

        // 2. CENTRADO: Calculamos los márgenes para que el cuadrado quede en el centro de la celda
        int offsetX = (availableWidth - squareSize) / 2;
        int offsetY = (availableHeight - squareSize) / 2;

        // 3. ANIMACIÓN 3D: Aplicamos tu escala horizontal sobre el tamaño del cuadrado
        int scaledWidth = (int) (squareSize * scale);
        int x = offsetX + (squareSize - scaledWidth) / 2;
        int y = offsetY;

        // 4. DIBUJADO: Pintamos la carta perfectamente cuadrada y centrada
        g2.drawImage(currentImage, x, y, scaledWidth, squareSize, this);
    }

    public Card getCard() {
        return card;
    }
}
