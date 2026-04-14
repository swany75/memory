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

public class Desplegable extends JMenuBar {

    // Ruta Icones
    private String RUTA_ICONES = "src/icons/default/";

    // Colors
    private Color TOOLBAR_BG = new Color(255, 255, 255);  // gris fosc blavós
    private Color MENU_BG    = new Color(45, 45, 55);
    private Color MENU_HOVER = new Color(70, 70, 85);
    private Color MENU_FG    = new Color(220, 220, 220); // text clar
    
    // Font
    private Font  MENU_FONT  = new Font("Segoe UI", Font.PLAIN, 13);

    public Desplegable(MainFrame frame) {
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder());

        JMenu menu = new JMenu();
        menu.setIcon(ImageManager.loadScaledIcon(RUTA_ICONES + "desplegable.png", 32, 32));
        menu.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        JMenuItem itemPlay = new JMenuItem("Play");
        JMenuItem itemHistory = new JMenuItem("Historic");
        JMenuItem itemSelectiveHistory = new JMenuItem("Stats");
        JMenuItem itemSettings = new JMenuItem("Settings");
        JMenuItem itemExit = new JMenuItem("Exit");

        // Afegir al desplegable  
        menu.add(itemPlay);
        menu.addSeparator();
        menu.add(itemHistory);
        menu.add(itemSelectiveHistory);
        menu.addSeparator();
        menu.add(itemSettings);
        menu.addSeparator();
        menu.add(itemExit);

        add(menu);
    }
}