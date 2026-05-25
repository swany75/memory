/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import utils.SoundManager;
import java.awt.AlphaComposite;
import utils.ImageManager;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    /**
     * Crea una casilla visual enlazada a una carta lógica y precarga sus imágenes.
     *
     * @param card carta asociada a la casilla
     */
    public Casella(Card card) {
        this.card = card;
        setOpaque(false);
        
        // PRECARGA: Leemos el disco una única vez al instanciar la casilla
        // (Asumiendo que card.getFrontImage() y card.getBackImage() devuelven los String de las rutas)
        this.frontImage = ImageManager.loadIcon(card.getFrontImage()).getImage();
        this.backImage  = ImageManager.loadIcon(card.getBackImage()).getImage();
    }

    /**
     * Inicia la animación de volteo de la carta.
     */
    public void flip() {
        startFlip();
    }

    /**
     * Indica si la casilla está ejecutando la animación de volteo.
     *
     * @return {@code true} si el volteo está en curso
     */
    public boolean isFlipping() {
        return flipTimer != null && flipTimer.isRunning();
    }

    /**
     * Renderiza la carta aplicando escala y transparencia para las animaciones.
     *
     * @param g contexto gráfico
     */
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

    /**
     * Devuelve la carta lógica asociada a la casilla.
     *
     * @return carta vinculada
     */
    public Card getCard() {
        return card;
    }
    
    /**
     * Dispara la animación de aparición con un retardo configurado.
     *
     * @param delayMs retardo en milisegundos antes de iniciar el fade in
     */
    public void fadeIn(int delayMs) {
        alpha = 0f;
        javax.swing.Timer delay = new javax.swing.Timer(delayMs, null);
        delay.setRepeats(false);
        delay.addActionListener(new FadeInDelayListener());
        delay.start();
    }

    /**
     * Inicializa el temporizador de la animación de volteo.
     */
    private void startFlip() {
        if (flipTimer != null && flipTimer.isRunning()) return;

        contractingPhase = true;
        flipTimer = new Timer(15, null);
        flipTimer.addActionListener(new FlipTimerListener());
        flipTimer.start();
    }

    private class FlipTimerListener implements ActionListener {
        /**
         * Ejecuta los pasos de contracción y expansión del efecto de volteo.
         *
         * @param event evento del temporizador
         */
        @Override
        public void actionPerformed(ActionEvent event) {
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
        }
    }

    private class FadeInDelayListener implements ActionListener {
        /**
         * Inicia el temporizador de incremento gradual de opacidad.
         *
         * @param e evento del temporizador
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            javax.swing.Timer fadeTimer = new javax.swing.Timer(15, null);
            fadeTimer.addActionListener(new FadeInStepListener(fadeTimer));
            fadeTimer.start();
        }
    }

    private class FadeInStepListener implements ActionListener {
        private final javax.swing.Timer fadeTimer;

        /**
         * Crea el listener asociado al temporizador de fade in.
         *
         * @param fadeTimer temporizador de la animación
         */
        private FadeInStepListener(javax.swing.Timer fadeTimer) {
            this.fadeTimer = fadeTimer;
        }

        /**
         * Incrementa la opacidad y detiene el temporizador al llegar al 100%.
         *
         * @param ev evento del temporizador
         */
        @Override
        public void actionPerformed(ActionEvent ev) {
            alpha += 0.05f;
            if (alpha >= 1f) {
                alpha = 1f;
                fadeTimer.stop();
            }
            repaint();
        }
    }
}
