/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memory;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
    private boolean flipDirection = false;
    private boolean contractingPhase;
    private Timer flipTimer;

    public Casella(Card card) {
        this.card = card;
        setPreferredSize(new Dimension(100, 100));
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startFlip();
            }
        });
    }

    private void startFlip() {
        if (flipTimer != null && flipTimer.isRunning()) return;

        contractingPhase = true;
        flipTimer = new Timer(15, null);

        flipTimer.addActionListener(event -> {
            if (contractingPhase) {
                // Contracting
                scale -= 0.1;
                if (scale <= 0.0) {
                    scale = 0.0;
                    // Midpoint: flip the card model
                    card.flip();
                    flipDirection = !flipDirection;
                    contractingPhase = false;
                }
            } else {
                // Expanding
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

        Image image = ImageManager.loadScaledIcon(
            card.getCurrentImage(), getWidth(), getHeight()
        ).getImage();

        int scaledWidth  = (int)(getWidth() * scale);
        int height       = getHeight();
        int x            = (getWidth() - scaledWidth) / 2;
        int y            = 0;

        g2.drawImage(image, x, y, scaledWidth, height, this);
    }

    public Card getCard() {
        return card;
    }
}
