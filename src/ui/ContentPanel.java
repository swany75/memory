
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ui;

import javax.swing.*;
import java.awt.*;
import model.Historial;
import config.Settings;
import audio.SoundManager;
import game.Timer;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class ContentPanel extends JPanel {

    private CardLayout cardLayout;
    private String currentPanel;
    private GamePanel gamePanel;
    private Settings settings;
    private Historial historic;
    private game.Timer timer;
    private SoundManager soundManager;
   
    public static final String GAME = "GAME";
    public static final String SETTINGS = "SETTINGS";
    public static final String HISTORY = "HISTORY";
    public static final String SELECTIVE = "SELECTIVE";
    public static final String DEVTOOLS = "DEVTOOLS"; 

    private StatusBar statusBar;

    public ContentPanel(StatusBar st, Timer timer, SoundManager sm) {
        this.statusBar = st;
        this.soundManager = sm;
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        gamePanel = new GamePanel(st, timer, sm);
        settings  = new Settings();
        historic  = new Historial();
        this.add(gamePanel, GAME);
        this.add(settings,  SETTINGS);
        this.add(historic,  HISTORY);
        switchPanel(GAME);
    }
    
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

    public GamePanel getGamePanel() {
        return gamePanel;
    }
    
    public Settings getSettingsPanel() {
        return settings;
    }
    
    public Historial getHistoryPanel() {
        return historic;
    }
    
    private class RefreshHistoryTask implements Runnable {
        private final boolean selective;

        private RefreshHistoryTask(boolean selective) {
            this.selective = selective;
        }

        @Override
        public void run() {
            historic.refresh(selective);
        }
    }
}