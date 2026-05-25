/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.components;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

import core.GameManager;
import ui.components.CustomColors;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import utils.PopUpManager;

public class Timer extends JProgressBar {

    private Runnable onTimeOut;
    private javax.swing.Timer crono;
    private int secTotals;
    private int secRestants;
    private int gameDuration;
    private CustomColors CC = new CustomColors();

    /**
     * Crea el temporizador visual con los estilos por defecto.
     */
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

    /**
     * Devuelve el tiempo restante en formato MM:SS.
     *
     * @return cadena con el tiempo restante
     */
    @Override
    public String getString() {
        if (secTotals == 0) return "--:--";
        int min = secRestants / 60;
        int seg = secRestants % 60;
        return String.format("%02d:%02d", min, seg);
    }

    /**
     * Prepara la cuenta atrás en segundos a partir de minutos.
     *
     * @param minuts minutos de juego
     */
    public final void prepararCountdown(int minuts) {
        this.secTotals   = minuts * 60;
        this.secRestants = secTotals;

        setMaximum(secTotals);
        setValue(0);
        repaint();

        crono = new javax.swing.Timer(1000, new CronoTickListener());
    }

    /**
     * Inicia el temporizador si está configurado.
     */
    public void start() { 
        if (crono != null) {
            crono.start();
        } 
    }

    /**
     * Indica si el temporizador está en ejecución.
     *
     * @return {@code true} si está activo
     */
    public boolean isRunning() {
        return crono != null && crono.isRunning();
    }
    
    /**
     * Detiene el temporizador y calcula la duración real de la partida.
     */
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

    /**
     * Reinicia completamente el estado del temporizador.
     */
    public final void reset() {
        stop();
        secTotals = 0;
        secRestants = 0;
        setValue(0);
        repaint();
    }
    
    /**
     * Devuelve la duración registrada de la partida en segundos.
     *
     * @return duración en segundos
     */
    public int getGameDuration() {
        return gameDuration;
    }

    /**
     * Define la acción que se ejecuta al agotar el tiempo.
     *
     * @param callback tarea a ejecutar
     */
    public void setOnTimeOut(Runnable callback) {
        this.onTimeOut = callback;
    }
    
    /**
     * Renderiza la barra de progreso con el texto centrado.
     *
     * @param g contexto gráfico
     */
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

    private class CronoTickListener implements ActionListener {
        /**
         * Actualiza el contador cada segundo y dispara el fin de partida al llegar a cero.
         *
         * @param e evento de acción
         */
        @Override
        public void actionPerformed(ActionEvent e) {
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
                if (onTimeOut != null) {
                    onTimeOut.run();
                }
            }
        }
    }
    
}