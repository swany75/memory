
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package memory;

import javax.swing.*;
import java.awt.*;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
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
    
    private StatusBar statusBar;
    
    public ContentPanel(StatusBar st) {
        
        this.statusBar = st;
        
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        
        gamePanel = new GamePanel();
        settings = new Settings();
        historic = new Historic();
        selectiveHistoric = new SelectiveHistoric();
        
        this.add(gamePanel, GAME);
        this.add(settings, SETTINGS);
        this.add(historic, HISTORY);
        this.add(selectiveHistoric, USER);

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
                break;

            case USER:
                statusBar.setText("User History");
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
    
    public Historic getHistoryPanel() {
        return historic;
    }
    
    public SelectiveHistoric getUserPanel() {
        return selectiveHistoric;
    }
}
