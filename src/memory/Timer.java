/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memory;

/**
 *
 * @author Juan
 */

import javax.swing.*;
import java.awt.*;
public class Timer extends JProgressBar {

    private javax.swing.Timer crono;
    private int secTotals;
    private int secRestants;

    public Timer() {
        super();
        setMinimum(0);
        setMaximum(100);
        setValue(0);
        setStringPainted(true);
        setPreferredSize(new Dimension(0, 32));
        setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        setForeground(new Color(0x3C1A7B));
        setBackground(new Color(0xDDCCFF));
    }

    // JProgressBar usa este método para saber qué texto pintar
    @Override
    public String getString() {
        if (secTotals == 0) return "--:--";
        int min = secRestants / 60;
        int seg = secRestants % 60;
        return String.format("%02d:%02d", min, seg);
    }

    public void prepararCountdown(int minuts) {
        this.secTotals   = minuts * 60;
        this.secRestants = secTotals;

        setMaximum(secTotals);
        setValue(0);
        repaint();

        crono = new javax.swing.Timer(1000, e -> {
            secRestants--;
            setValue(secTotals - secRestants);
            repaint();                        

            if (secRestants <= 0) {
                stop();
                JOptionPane.showMessageDialog(null, "GAME OVER!");
            }
        });
    }

    public void start() { 
        if (crono != null) {
            crono.start();
        } 
    }
    
    public void stop()  { 
        if (crono != null) {
            crono.stop();
        }  
    }

    public void reset() {
        stop();
        secTotals   = 0;
        secRestants = 0;
        setValue(0);
        repaint();
    }
}