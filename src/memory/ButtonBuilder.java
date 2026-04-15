/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memory;

import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author Juan
 */
public class ButtonBuilder {

    // Boto simple per la barra superior sense text i amb opcio d'ESCALAT!
    public static JButton createButton(String iconPath, int w, int h, ActionListener action) {

        JButton button = new JButton();

        button.setIcon(ImageManager.loadScaledIcon(iconPath, w, h));

        button.addActionListener(action);

        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);

        return button;
    }   
    
    // Polsador per la barra lateral estil fusta
    public static JButton createPulsador(String normalPath, String pressedPath, String hoverPath) {

        JButton button = new JButton();

        button.setIcon(ImageManager.loadIcon(normalPath));
        button.setPressedIcon(ImageManager.loadIcon(pressedPath));
        button.setRolloverIcon(ImageManager.loadIcon(hoverPath));

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusable(false);

        return button;
    }
}
