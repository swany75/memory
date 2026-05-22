/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memory;

import ui.PopUpManager;
import game.Timer;
import game.GameManager;
import ui.ButtonBuilder;
import ui.ContentPanel;
import ui.CustomColors;
import ui.StatusBar;
import ui.Desplegable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import audio.SoundManager;

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
    private SoundManager sm = new SoundManager();
    
    // Rutes
    private static final String RUTA_ICONES  = "media/icons/default/";
    
    ////////////////////////////////////////////////////////////////////////////
    // Constructor /////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    public MainFrame() {
        super("Memory - UIB");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new FrameCloseListener());

        // LOAD Icons & Images
        ImageIcon icono = new ImageIcon(RUTA_ICONES + ("icon" + (rand.nextInt(4) + 1) + ".png"));
        setIconImage(icono.getImage());
        
        setSize(1280, 720);
        setMinimumSize(new Dimension(1280, 720));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(4, 4));

        contentPanel = new ContentPanel(statusBar, timer, sm);
        
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
        
        toolBar.add(ButtonBuilder.createButton(RUTA_ICONES + "play.png", 32, 32, new SwitchPanelActionListener(ContentPanel.GAME)));
        toolBar.add(ButtonBuilder.createButton(RUTA_ICONES + "selectiveHistoric.png", 32, 32, new SwitchPanelActionListener(ContentPanel.SELECTIVE)));
        toolBar.add(ButtonBuilder.createButton(RUTA_ICONES + "historic.png", 32, 32, new SwitchPanelActionListener(ContentPanel.HISTORY)));
        toolBar.add(ButtonBuilder.createButton(RUTA_ICONES + "settings.png", 32, 32, new SwitchPanelActionListener(ContentPanel.SETTINGS)));
        toolBar.add(ButtonBuilder.createButton(RUTA_ICONES + "exit.png", 32, 32, new SecureExitActionListener()));

        return toolBar;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    /// CENTER PANEL ///////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    private JPanel centerPanel() {
        panellCos = new JPanel(new BorderLayout(4, 0));

        panellLateral = new JPanel();
        panellLateral.setPreferredSize(new Dimension(192, 0));
        panellLateral.setLayout(new GridLayout(5, 1, 0, 4));
        panellLateral.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));

        JPanel separadorEsquerra = new JPanel();
        separadorEsquerra.setPreferredSize(new Dimension(1, 0));
        separadorEsquerra.setBackground(panellCos.getBackground());
        
        addSideButton("GAME", new SwitchPanelActionListener(ContentPanel.GAME));
        addSideButton("SELECTIVE", new SwitchPanelActionListener(ContentPanel.SELECTIVE));
        addSideButton("HISTORY", new SwitchPanelActionListener(ContentPanel.HISTORY));
        addSideButton("SETTINGS", new SwitchPanelActionListener(ContentPanel.SETTINGS));
        addSideButton("EXIT", new SecureExitActionListener());
        
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
            System.exit(0);
        }
    }

    private class FrameCloseListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            secureExit();
        }
    }

    private class SwitchPanelActionListener implements ActionListener {
        private final String panel;

        private SwitchPanelActionListener(String panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            contentPanel.switchPanel(panel);
        }
    }

    private class SecureExitActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            secureExit();
        }
    }
}