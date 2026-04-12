/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Juan
 */


public class MainFrame extends JFrame {
 
    // Zones principals
    private JPanel panellSuperior;
    private JPanel panellCos;
    private JPanel panellLateral;
    private JPanel panellCentral;
    private JLabel labelStatus;
    private JLabel labelTemps;
 
    // Estat
    private boolean partidaEnCurs = false;

    // Rutes
    private static final String RUTA_ICONES  = "src/icons/default/";
    private static final String RUTA_IMATGES = "src/images/";
 
    // Constructor

    public MainFrame() {
        super("Memory - UIB");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Favicon
        ImageIcon icono = new ImageIcon(RUTA_IMATGES + "LogoUIB (Ben fet).png");
        setIconImage(icono.getImage());
        
        setSize(950, 680);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(4, 4));

        add(topPanel(), BorderLayout.NORTH);
 
        setVisible(true);
    }

    ////////////////////////////////////////////////////////////////////////////
    /// TOP PANEL //////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    private JPanel topPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());

        topPanel.add(desplegable(), BorderLayout.WEST);
        topPanel.add(buttonMenu(), BorderLayout.CENTER);
        topPanel.add(statusScreen(), BorderLayout.EAST);

        return topPanel;
    }
    
    
    private JMenuBar desplegable() {

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        JMenuItem itemPlay = new JMenuItem("Play");
        JMenuItem itemHistory = new JMenuItem("Historic");
        JMenuItem itemSelectiveHistory = new JMenuItem("Stats");
        JMenuItem itemSettings = new JMenuItem("Settings");
        JMenuItem itemExit = new JMenuItem("Exit");

        menu.add(itemPlay);
        menu.addSeparator();
        menu.add(itemHistory);
        menu.add(itemSelectiveHistory);
        menu.addSeparator();
        menu.add(itemSettings);
        menu.addSeparator();
        menu.add(itemExit);

        menuBar.add(menu);

        return menuBar;
    }
    
    private JToolBar buttonMenu() {

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton play = ButtonBuilder.createButton(
            RUTA_ICONES + "iconoJugar.png", 32, 32, e -> auronPlay()
        );

        JButton history = ButtonBuilder.createButton(
            RUTA_ICONES + "iconoHistoriall.png", 32, 32, e -> auronPlay()
        );

        JButton stats = ButtonBuilder.createButton(
            RUTA_ICONES + "iconoHistorialSelectivo.png", 32, 32, e -> auronPlay()
        );

        JButton settings = ButtonBuilder.createButton(
            RUTA_ICONES + "iconoAjustes.png", 32, 32, e -> auronPlay()
        );

        JButton exit = ButtonBuilder.createButton(
            RUTA_ICONES + "iconoSalir.png", 32, 32, e -> auronPlay()
        );

        toolBar.add(play);
        toolBar.add(history);
        toolBar.add(stats);
        toolBar.add(settings);
        toolBar.add(exit);

        return toolBar;
    }
    
    private void auronPlay(){};
    
    private JPanel statusScreen() {

        JPanel panel = new JPanel(new BorderLayout());

        JLabel screen = new JLabel("READY");
        screen.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(screen, BorderLayout.EAST);

        return panel;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    /// CENTER PANEL ///////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    private void setCenterPanel() {
        
    }
    
    ////////////////////////////////////////////////////////////////////////////
    /// BOTTOM PANEL ///////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    private void setBottomPanel() {
        
    }
    
}