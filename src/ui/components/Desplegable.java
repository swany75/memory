/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.components;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

import ui.panels.ContentPanel;
import ui.components.CustomColors;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import utils.ImageManager;
import ui.panels.MainFrame;
import utils.SoundManager;

public class Desplegable extends JMenuBar {

    // Ruta Icones
    private String RUTA_ICONES = "media/icons/default/";
    
    // Font & Colors
    private Font MENU_FONT = new Font("Jet Brains Mono", Font.PLAIN, 12);
    private CustomColors CC = new CustomColors();

    /**
     * Crea el menú desplegable superior y configura sus acciones.
     *
     * @param frame ventana principal para gestionar la salida segura
     * @param cp    panel central para cambiar de pantalla
     */
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
        JMenuItem itemHistory = createStyledMenuItem("History");
        JMenuItem itemSettings = createStyledMenuItem("Settings");
        JMenuItem itemExit = createStyledMenuItem("Exit");

        itemGame.addActionListener(new SwitchPanelActionListener(cp, ContentPanel.GAME));
        itemSelective.addActionListener(new SwitchPanelActionListener(cp, ContentPanel.SELECTIVE));
        itemHistory.addActionListener(new SwitchPanelActionListener(cp, ContentPanel.HISTORY));
        itemSettings.addActionListener(new SwitchPanelActionListener(cp, ContentPanel.SETTINGS));
        itemExit.addActionListener(new SecureExitActionListener(frame));
        
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
    
    /**
     * Fabrica un item del menú con estilo y sonido al pulsar.
     *
     * @param text texto a mostrar
     * @return item configurado
     */
    private JMenuItem createStyledMenuItem(String text) {
        JMenuItem item = new JMenuItem(text);

        item.setFont(MENU_FONT);
        item.setForeground(Color.BLACK);
        item.setOpaque(false);
        item.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Sonido al clicar
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundManager.playSound("media/sounds/click.wav");
            }
        });

        return item;
    }

    private class SwitchPanelActionListener implements ActionListener {
        private final ContentPanel contentPanel;
        private final String panel;

        /**
         * Crea el listener que cambia el panel visible.
         *
         * @param contentPanel panel contenedor
         * @param panel        identificador de la vista
         */
        private SwitchPanelActionListener(ContentPanel contentPanel, String panel) {
            this.contentPanel = contentPanel;
            this.panel = panel;
        }

        /**
         * Activa el cambio de panel.
         *
         * @param e evento de acción
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            contentPanel.switchPanel(panel);
        }
    }

    private class SecureExitActionListener implements ActionListener {
        private final MainFrame frame;

        /**
         * Crea el listener que ejecuta la salida segura.
         *
         * @param frame ventana principal
         */
        private SecureExitActionListener(MainFrame frame) {
            this.frame = frame;
        }

        /**
         * Ejecuta la salida segura desde el menú.
         *
         * @param e evento de acción
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.secureExit();
        }
    }
}