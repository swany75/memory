/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 *
 * @author Juan
 */

public class MainFrame extends JFrame {
 
    ////////////////////////////////////////////////////////////////////////////
    /// Variables & Constants //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
        
    // Zones principals
    private JPanel panellCos;
    private JPanel panellLateral;
    private JPanel panellCentral;
 
    // Estats
    private boolean partidaEnCurs = false;
    private boolean menuObert = true;
    
    // Classes
    private StatusBar statusBar = new StatusBar();
    private Timer timer = new Timer();
    private Random rand = new Random();
 
    // Colors
    private Color stockSoftPurple = new Color(0x3C1A7B);
    
    // Rutes
    private static final String RUTA_ICONES  = "src/icons/default/";
    private static final String RUTA_IMATGES = "src/images/";
 
    // Icons & Images
    private ImageIcon leftMenuOpen;
    private ImageIcon leftMenuClose;
 
    // Botons 
    private JButton lateralMenuButton;
    
    ////////////////////////////////////////////////////////////////////////////
    // Constructor /////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    public MainFrame() {
        super("Memory - UIB");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // LOAD Icons & Images
        ImageIcon icono = new ImageIcon(RUTA_ICONES + ("icon" + rand.nextInt(5 - 1) + ".png"));
        setIconImage(icono.getImage());

        leftMenuOpen = ImageManager.loadScaledIcon(RUTA_ICONES + "leftMenuOpen.png", 32, 32);
        leftMenuClose = ImageManager.loadScaledIcon(RUTA_ICONES + "leftMenuClose.png", 32, 32);

        setSize(950, 680);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(4, 4));

        add(topPanel(), BorderLayout.NORTH);
        add(centerPanel(), BorderLayout.CENTER);
        
        add(timer, BorderLayout.SOUTH);
        setVisible(true);
    }

    ////////////////////////////////////////////////////////////////////////////
    /// TOP PANEL //////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    private JPanel topPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.add(buttonMenu());

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(statusBar, BorderLayout.CENTER);

        return topPanel;
    }
    
    private JToolBar buttonMenu() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        toolBar.add(new Desplegable(this));
        
        lateralMenuButton = ButtonBuilder.createButton(RUTA_ICONES + "leftMenuClose.png", 32, 32, 
            e -> toggleLateralMenu());
        
        toolBar.add(lateralMenuButton);
        
        toolBar.add(ButtonBuilder.createButton(RUTA_ICONES + "play.png", 32, 32, e -> createTEST("Play")));
        toolBar.add(ButtonBuilder.createButton(RUTA_ICONES + "user.png", 32, 32, e -> createTEST("History")));
        toolBar.add(ButtonBuilder.createButton(RUTA_ICONES + "historic.png", 32, 32, e -> createTEST("Selective History")));
        toolBar.add(ButtonBuilder.createButton(RUTA_ICONES + "settings.png", 32, 32, e -> createTEST("Settings")));
        toolBar.add(ButtonBuilder.createButton(RUTA_ICONES + "exit.png", 32, 32, e -> createTEST("Exit")));

        return toolBar;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    /// CENTER PANEL ///////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    private JPanel centerPanel() {
        panellCos = new JPanel(new BorderLayout(4, 0));

        panellLateral = new JPanel();
        panellLateral.setLayout(new BoxLayout(panellLateral, BoxLayout.Y_AXIS));
        panellLateral.setPreferredSize(new Dimension(128, 0));
        panellLateral.setLayout(new GridLayout(5, 1, 0, 4));
        panellLateral.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));

        addSideButton("PLAY");
        addSideButton("USER");
        addSideButton("HISTORIC");
        addSideButton("SETTINGS");
        addSideButton("EXIT");

        panellCentral = new JPanel(new BorderLayout());
        panellCentral.setBorder(BorderFactory.createLineBorder(
                UIManager.getColor("Separator.foreground"), 0, true));

        panellCos.add(panellLateral, BorderLayout.WEST);
        panellCos.add(panellCentral, BorderLayout.CENTER);

        return panellCos;
    }
    
    private void toggleLateralMenu() {
        menuObert = !menuObert;
        panellLateral.setVisible(menuObert);

        if (menuObert) {
            lateralMenuButton.setIcon(leftMenuClose);
        } else {
            lateralMenuButton.setIcon(leftMenuOpen);
        }

        panellCos.revalidate();
        panellCos.repaint();
    }

    private void addSideButton(String baseName) {
        JButton button = ButtonBuilder.createPulsador(
            baseName, 
            stockSoftPurple, 
            new Font("Montserrat", Font.BOLD, 16)
        );
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        panellLateral.add(button);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
    }
 
    private void GamePanel() {
        panellCentral = new JPanel(new BorderLayout());
        panellCentral.setBorder(BorderFactory.createLineBorder(
                UIManager.getColor("Separator.foreground"), 1, true));
        panellCos.add(panellCentral, BorderLayout.CENTER);
    }
    
    ////////////////////////////////////////////////////////////////////////////
    /// Trash //////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    private void createTEST(String text) {
        JFrame newWindow = new JFrame(text);
        newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newWindow.setSize(450, 200);
        newWindow.setLocationRelativeTo(null);
        newWindow.setVisible(true);
    }
}