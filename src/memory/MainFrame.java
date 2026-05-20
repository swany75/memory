/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memory;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class MainFrame extends JFrame {
 
    ////////////////////////////////////////////////////////////////////////////
    /// Variables & Constants //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
        
    // Zones principals
    private JPanel panellCos;
    private JPanel panellLateral;

    // Estats
    private boolean menuObert = true;
    
    // Classes
    private StatusBar statusBar = new StatusBar();
    private Timer timer = new Timer();
    private Random rand = new Random();
    private CustomColors CC = new CustomColors();
    private ContentPanel contentPanel;
    private PopUpManager popup;
    
    // Rutes
    private static final String RUTA_ICONES  = "media/icons/default/";
 
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

        setSize(670, 720);
        setMinimumSize(new Dimension(1280, 720));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(4, 4));

        contentPanel = new ContentPanel(statusBar);
        
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

        toolBar.add(new Desplegable(this, contentPanel));
        
        lateralMenuButton = ButtonBuilder.createButton(RUTA_ICONES + "leftMenuClose.png", 32, 32, 
            e -> toggleLateralMenu());
        
        toolBar.add(lateralMenuButton);
        
        toolBar.add(ButtonBuilder.createButton(RUTA_ICONES + "play.png", 32, 32, e -> contentPanel.switchPanel(ContentPanel.GAME)));
        toolBar.add(ButtonBuilder.createButton(RUTA_ICONES + "selectiveHistoric.png", 32, 32, e -> contentPanel.switchPanel(ContentPanel.SELECTIVE)));
        toolBar.add(ButtonBuilder.createButton(RUTA_ICONES + "historic.png", 32, 32, e -> contentPanel.switchPanel(ContentPanel.HISTORY)));
        toolBar.add(ButtonBuilder.createButton(RUTA_ICONES + "settings.png", 32, 32, e -> contentPanel.switchPanel(ContentPanel.SETTINGS)));
        toolBar.add(ButtonBuilder.createButton(RUTA_ICONES + "exit.png", 32, 32, e -> secureExit()));

        return toolBar;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    /// CENTER PANEL ///////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    private JPanel centerPanel() {
        panellCos = new JPanel(new BorderLayout(4, 0));

        panellLateral = new JPanel();
        panellLateral.setLayout(new BoxLayout(panellLateral, BoxLayout.Y_AXIS));
        panellLateral.setPreferredSize(new Dimension(192, 0));
        panellLateral.setLayout(new GridLayout(5, 1, 0, 4));
        panellLateral.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));

        JPanel separadorEsquerra = new JPanel();
        separadorEsquerra.setPreferredSize(new Dimension(1, 0));
        separadorEsquerra.setBackground(panellCos.getBackground());
        
        addSideButton("GAME", e -> contentPanel.switchPanel(ContentPanel.GAME));
        addSideButton("SELECTIVE", e -> contentPanel.switchPanel(ContentPanel.SELECTIVE));
        addSideButton("HISTORY", e -> contentPanel.switchPanel(ContentPanel.HISTORY));
        addSideButton("SETTINGS", e -> contentPanel.switchPanel(ContentPanel.SETTINGS));
        addSideButton("EXIT", e -> secureExit());
        
        panellCos.add(panellLateral, BorderLayout.WEST);
        panellCos.add(contentPanel, BorderLayout.CENTER);
        return panellCos;
    }
   
    private void addSideButton(String baseName, java.awt.event.ActionListener action) {
        JButton button = ButtonBuilder.createPulsador(
            baseName, 
            CC.AMSTRAD_BLUE, 
            new Font("Montserrat", Font.BOLD, 16)
        );
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(action);
        panellLateral.add(button);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
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
    
    // METHODS
    public void secureExit() {
        if (GameManager.isRunning()) {
            statusBar.setText("A game is currently in progress");
            PopUpManager.displayMessage(
                "You must finish the current game before exiting!",
                "The game is still in progress"
            );
            return;
        }   

        boolean confirmExit = PopUpManager.confirmAction("Exit the application");

        if (confirmExit) {
            statusBar.setText("[+] Exit simulation");
            // System.exit(0);
        }
    }
}