/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memory;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class ButtonBuilder {

    // private static final Color TEXT_COLOR = new Color(0xFECD01);
    private static final Color TEXT_COLOR = Color.WHITE;
    
    
    // Boto simple per la barra superior sense text i amb opcio d'ESCALAT!
    public static JButton createButton(String iconPath, int w, int h, ActionListener action) {

        JButton button = new JButton();

        button.setIcon(ImageManager.loadScaledIcon(iconPath, w, h));

        button.addActionListener(action);

        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);

        return button;
    }   
    
    // Pulsador per la barra lateral
    public static JButton createPulsador(String texto, Color color, Font fuente) {
        JButton button = new JButton(texto);
        button.setFont(fuente);
        button.setForeground(TEXT_COLOR);
        button.setBackground(color);
        button.setFocusable(false);
        button.setUI(new PulsadorUI());
        return button;
    }

    /*
    * @font Hem obtingut el codi per fer pulsadors de Stack Overflow
    * Ho hem adaptat per al nostre cas en concret
    * https://stackoverflow.com/questions/23698092/design-button-in-java-like-in-css
    */

    static class PulsadorUI extends BasicButtonUI {

        @Override
        public void installUI(JComponent c) {
            super.installUI(c);
            AbstractButton b = (AbstractButton) c;
            b.setOpaque(false);
            b.setBorder(new EmptyBorder(8, 20, 8, 20));
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            AbstractButton b = (AbstractButton) c;
            boolean pressed = b.getModel().isPressed();
            paintBackground(g, b, pressed);
            if (pressed) {
                g.translate(0, 3);
            }
            super.paint(g, c);
            if (pressed) {
                g.translate(0, -3);
            }
        }

        private void paintBackground(Graphics g, JComponent c, boolean pressed) {
            Dimension size = c.getSize();
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color base = c.getBackground();
            int sombra = 6;

            if (!pressed) {
                g2.setColor(base.darker().darker());
                g2.fillRoundRect(0, 0, size.width, size.height, 15, 15);
                g2.setColor(base.darker().darker());
                g2.fillRoundRect(0, sombra, size.width, size.height - sombra, 15, 15);
                g2.setColor(base);
                g2.fillRoundRect(0, 0, size.width, size.height - sombra, 15, 15);
            } else {
                g2.setColor(base.darker());
                g2.fillRoundRect(0, 0, size.width, size.height, 15, 15);
                g2.setColor(base.darker());
                g2.fillRoundRect(0, sombra, size.width, size.height - sombra, 15, 15);
            }
        }
    }
}
