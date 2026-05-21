/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

import ui.CustomColors;
import javax.swing.*;
import java.awt.*;
import ui.PopUpManager;

public class Timer extends JProgressBar {

    private Runnable onTimeOut;
    private javax.swing.Timer crono;
    private int secTotals;
    private int secRestants;
    private int gameDuration;
    private CustomColors CC = new CustomColors();

    public Timer() {
        super();
        setMinimum(0);
        setMaximum(100);
        setValue(0);
        setStringPainted(false);
        setPreferredSize(new Dimension(0, 32));
        setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        setBackground(CC.PASTEL_YELLOW);
    }

    @Override
    public String getString() {
        if (secTotals == 0) return "--:--";
        int min = secRestants / 60;
        int seg = secRestants % 60;
        return String.format("%02d:%02d", min, seg);
    }

    public final void prepararCountdown(int minuts) {
        this.secTotals   = minuts * 60;
        this.secRestants = secTotals;

        setMaximum(secTotals);
        setValue(0);
        repaint();

        crono = new javax.swing.Timer(1000, e -> {
            secRestants--;
            setValue(secTotals - secRestants);
            
            if (secRestants <= secTotals * 0.05) {
                setForeground(CC.P3_RED_ALERT);
            } else {
                setForeground(Color.WHITE);
            }
            
            repaint();                        

            if (secRestants <= 0) {
                GameManager.timeOut();
                stop();
                PopUpManager.displayMessage("Game Over!", GameManager.getGameStatus());
                if (onTimeOut != null)  { 
                    onTimeOut.run();
                }  
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
        
        this.gameDuration = this.secTotals - this.secRestants;
        this.secTotals = 0;
        this.secRestants = 0;
        setValue(0);
        repaint();
        
    }

    public final void reset() {
        stop();
        secTotals = 0;
        secRestants = 0;
        setValue(0);
        repaint();
    }
    
    public int getGameDuration() {
        return gameDuration;
    }

    public void setOnTimeOut(Runnable callback) {
        this.onTimeOut = callback;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.setFont(getFont());
        String text = getString();
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g.drawString(text, x, y);
        setBorderPainted(false);
    }
    
}