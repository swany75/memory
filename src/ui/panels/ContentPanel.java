
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ui.panels;

import javax.swing.*;
import java.awt.*;
import model.Historial;
import utils.SoundManager;
import ui.components.Timer;
import ui.components.StatusBar;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class ContentPanel extends JPanel {

    private CardLayout cardLayout;
    private String currentPanel;
    private GamePanel gamePanel;
    private SettingsPanel settings;
    private Historial historic;
    private ui.components.Timer timer;
    private SoundManager soundManager;
   
    public static final String GAME = "GAME";
    public static final String SETTINGS = "SETTINGS";
    public static final String HISTORY = "HISTORY";
    public static final String SELECTIVE = "SELECTIVE";
    public static final String DEVTOOLS = "DEVTOOLS"; 

    private StatusBar statusBar;

    /**
     * Crea el contenedor central y registra los paneles principales.
     *
     * @param st    barra de estado para mensajes
     * @param timer temporizador del juego
     * @param sm    gestor de sonido
     */
    public ContentPanel(StatusBar st, Timer timer, SoundManager sm) {
        this.statusBar = st;
        this.soundManager = sm;
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        gamePanel = new GamePanel(st, timer, sm);
        settings  = new SettingsPanel();
        historic  = new Historial();
        this.add(gamePanel, GAME);
        this.add(settings,  SETTINGS);
        this.add(historic,  HISTORY);
        switchPanel(GAME);
    }
    
    /**
     * Cambia la pantalla visible y actualiza el texto de estado.
     *
     * @param panelName identificador del panel a mostrar
     */
    public void switchPanel(String panelName) {
        if (panelName == null || panelName.equals(currentPanel)) {
            return;
        }
        cardLayout.show(this, panelName);
        currentPanel = panelName;
        switch (panelName) {
            case GAME:
                statusBar.setDefaultText();
                break;
            case SETTINGS:
                statusBar.setText("Settings");
                break;
            case HISTORY:
                statusBar.setText("History");
                cardLayout.show(this, HISTORY);
                SwingUtilities.invokeLater(new RefreshHistoryTask(false));
                break;
            case SELECTIVE:
                statusBar.setText("Selective History");
                cardLayout.show(this, HISTORY);
                SwingUtilities.invokeLater(new RefreshHistoryTask(true));
                break;
            default:
                statusBar.clearText();
                break;
        }
    }

    private class RefreshHistoryTask implements Runnable {
        private final boolean selective;

        /**
         * Crea la tarea que refresca el historial con o sin filtro.
         *
         * @param selective modo selectivo
         */
        private RefreshHistoryTask(boolean selective) {
            this.selective = selective;
        }

        /**
         * Ejecuta el refresco en el hilo de la interfaz.
         */
        @Override
        public void run() {
            historic.refresh(selective);
        }
    }
}