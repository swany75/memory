/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memory;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

import javax.swing.*;
import java.awt.*;

public class Desplegable extends JMenuBar {

    // Ruta Icones
    private String RUTA_ICONES = "media/icons/default/";
    
    // Font & Colors
    private Font MENU_FONT = new Font("Jet Brains Mono", Font.PLAIN, 12);
    private CustomColors CC = new CustomColors();

    public Desplegable(MainFrame frame, ContentPanel cp) {
        setOpaque(true);
        setBorder(BorderFactory.createEmptyBorder());

        JMenu menu = new JMenu();
        menu.setIcon(ImageManager.loadScaledIcon(RUTA_ICONES + "desplegable.png", 32, 32));
        menu.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        menu.setFont(MENU_FONT);
        menu.setForeground(CC.P3_BRIGHT_CYAN);
        
        JMenuItem itemGame = createStyledMenuItem("Game");
        JMenuItem itemSelective = createStyledMenuItem("Selective");
        JMenuItem itemHistory = createStyledMenuItem("Historic");
        JMenuItem itemSettings = createStyledMenuItem("Settings");
        JMenuItem itemExit = createStyledMenuItem("Exit");

        itemGame.addActionListener(e -> {
            cp.switchPanel(ContentPanel.GAME);
        });

        itemSelective.addActionListener(e -> {
            cp.switchPanel(ContentPanel.SELECTIVE);
        });

        itemHistory.addActionListener(e -> {
            cp.switchPanel(ContentPanel.HISTORY);
        });

        itemSettings.addActionListener(e -> {
            cp.switchPanel(ContentPanel.SETTINGS);
        });

        itemExit.addActionListener(e -> {
            frame.secureExit();
        });
        
        menu.add(itemGame);
        menu.addSeparator();
        menu.add(itemSelective);
        menu.add(itemHistory);
        menu.addSeparator();
        menu.add(itemSettings);
        menu.addSeparator();
        menu.add(itemExit);

        add(menu);
    }
    
    private JMenuItem createStyledMenuItem(String text) {
        JMenuItem item = new JMenuItem(text);
        
        item.setFont(MENU_FONT);
        item.setForeground(Color.BLACK);
        item.setOpaque(false);
        item.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        return item;
    }
}