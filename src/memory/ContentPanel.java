
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package memory;

import javax.swing.*;
import java.awt.*;

/**
 * Manages multiple content panels using CardLayout for seamless switching
 * @author Juan
 */
public class ContentPanel extends JPanel {
    
    private CardLayout cardLayout;
    private String currentPanel;
    private GamePanel gamePanel;
    private Settings settings;
    private Historic historic;
    private SelectiveHistoric selectiveHistoric;
    
    public static final String GAME = "GAME";
    public static final String SETTINGS = "SETTINGS";
    public static final String HISTORY = "HISTORY";
    public static final String USER = "USER";
    
    public ContentPanel() { 
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
        
        gamePanel = new GamePanel();
        settings = new Settings();
        historic = new Historic();
        selectiveHistoric = new SelectiveHistoric();
        
        this.add(gamePanel, GAME);
        this.add(settings, SETTINGS);
        this.add(historic, HISTORY);
        this.add(selectiveHistoric, USER);

        // Show GamePanel by default
        cardLayout.show(this, GAME);
        currentPanel = GAME;
    }
    
    public void switchPanel(String panelName) {
        if (panelName == null || panelName.equals(currentPanel)) {
            return;
        }

        cardLayout.show(this, panelName);
        currentPanel = panelName;
    }
    
    public GamePanel getGamePanel() {
        return gamePanel;
    }
    
    public Settings getSettingsPanel() {
        return settings;
    }
    
    public Historic getHistoryPanel() {
        return historic;
    }
    
    public SelectiveHistoric getUserPanel() {
        return selectiveHistoric;
    }
}
