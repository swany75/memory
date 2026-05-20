/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class GamePanel extends JPanel { 
    
    private boolean inGame = false;
    private ImageIcon welcomeIcon; 

    public GamePanel() {
        this.setBackground(Color.WHITE);
        this.welcomeIcon = ImageManager.loadIcon("media/images/LogoUIB (Ben fet).png");
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!inGame) {
                    boolean start = PopUpManager.confirmAction("start a new Game");
                    if (start) {
                        startGame();
                    } else {
                        repaint();
                    }
                }
            }
        });
    }

    public void startGame() {
        this.inGame = true;
        this.removeAll();
        
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!inGame) {
            drawWelcomeScreen(g);
        }
    }

    private void drawWelcomeScreen(Graphics g) {
        if (welcomeIcon != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            int margin = 100;
            int maxWidth = getWidth() - margin;
            int maxHeight = getHeight() - margin - 50;

            int imgWidth = welcomeIcon.getIconWidth();
            int imgHeight = welcomeIcon.getIconHeight();
            double ratio = (double) imgWidth / imgHeight;

            int finalWidth = maxWidth;
            int finalHeight = (int) (finalWidth / ratio);

            if (finalHeight > maxHeight) {
                finalHeight = maxHeight;
                finalWidth = (int) (finalHeight * ratio);
            }

            int x = (getWidth() - finalWidth) / 2;
            int y = (getHeight() - finalHeight) / 2 - 20;

            g2d.drawImage(welcomeIcon.getImage(), x, y, finalWidth, finalHeight, null);

            g2d.setColor(Color.GRAY);
            g2d.setFont(new Font("Times New Roman", Font.BOLD, 24));
            String subtitle = "Marti Figuls Nolla & Juan Dalmau Santandreu";
            FontMetrics fm = g2d.getFontMetrics();
            int txtX = (getWidth() - fm.stringWidth(subtitle)) / 2;
            int txtY = y + finalHeight + 40; 

            g2d.drawString(subtitle, txtX, txtY);
        }
    }
}