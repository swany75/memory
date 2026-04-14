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
import java.util.Random;

public class StatusBar extends JPanel {
    
    private Color BG_COLOR   = new Color(180, 220, 180);
    private Color TEXT_COLOR = new Color(30, 80, 30);

    private static final String[] PHRASES = {
        "Is there anybody alive out there?",
        "Are you ready?",
        "Are you really ready?",
        "Are yoy really really ready?",
        "Are you ready to have a good time?",
        "One, two, three, four!",
        "Come on!",
        "Turn it up!",
        "Let me hear you!",
        "Tonight we ride!",
        "No retreat, no surrender!",
        "Is everybody ready?"
    };
    
    private static final Random RANDOM = new Random();
    
    private JLabel label;
    
    public StatusBar() {
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        setOpaque(true);

        label = new JLabel(getRandomText());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Monospaced", Font.BOLD, 14));
        label.setForeground(TEXT_COLOR);

        add(label, BorderLayout.CENTER);
    }
    
    public void clearText() {
        label.setText("");
    }
    
    public void setNewText(String text) {
        label.setText(text);
    }
    
    private String getRandomText() {
        return PHRASES[RANDOM.nextInt(PHRASES.length)];
    }
    
    public void setDefaultText() {
        label.setText(getRandomText());
    }
    
}
