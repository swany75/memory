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
        
        // Inicialitzem botoAlternarMenu. 
        // Si el menú comença OBERT, l'icona que ha de mostrar el botó és la de TANCAR.
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
        panellLateral.setPreferredSize(new Dimension(70, 0));

        addSideButton("play");
        addSideButton("user");
        addSideButton("historic");
        addSideButton("settings");
        addSideButton("exit");

        panellCentral = new JPanel(new BorderLayout());
        panellCentral.setBorder(BorderFactory.createLineBorder(
                UIManager.getColor("Separator.foreground"), 1, true));

        panellCos.add(panellLateral, BorderLayout.WEST);
        panellCos.add(panellCentral, BorderLayout.CENTER);

        return panellCos;
    }
    
    private void toggleLateralMenu() {
        menuObert = !menuObert;
        panellLateral.setVisible(menuObert);

        // Usem exactament les teves variables d'icona
        if (menuObert) {
            lateralMenuButton.setIcon(leftMenuClose); // Si s'obre, el botó permet tancar
        } else {
            lateralMenuButton.setIcon(leftMenuOpen);  // Si es tanca, el botó permet obrir
        }

        panellCos.revalidate();
        panellCos.repaint();
    }

    private void addSideButton(String baseName) {
        JButton button = ButtonBuilder.createPulsador(
            RUTA_ICONES + baseName + "_normal.png",
            RUTA_ICONES + baseName + "_pressed.png",
            RUTA_ICONES + baseName + "_hover.png"
        );
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        panellLateral.add(Box.createVerticalStrut(8));
        panellLateral.add(button);
    }
 
    private void setGamePanel() {
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