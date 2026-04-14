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

public class Timer extends JPanel {
    
    private JLabel labelTemps;

    public Timer() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, 32));

        labelTemps = new JLabel("--:--", SwingConstants.CENTER);
        labelTemps.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

        add(labelTemps, BorderLayout.CENTER);
    }

    public void setTime(String time) {
        labelTemps.setText(time);
    }

    public void reset() {
        labelTemps.setText("--:--");
    }
    
}
