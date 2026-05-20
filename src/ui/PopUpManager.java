/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import javax.swing.JOptionPane;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class PopUpManager {
    
    public static boolean confirmAction(String actionText) {
        String message = "Are you sure you want to " + actionText + "?";
        int response = -1;
        while (response == JOptionPane.CLOSED_OPTION) {
            response = JOptionPane.showConfirmDialog(
                    null,
                    message,
                    "Confirmation Required",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
        }
        return response == JOptionPane.YES_OPTION;
    }

    
    public static void displayMessage(String message, String title) {
        int response = -1;
        while (response == JOptionPane.CLOSED_OPTION) {
            response = JOptionPane.showOptionDialog(
                    null,
                    message,
                    title,
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    new Object[]{"OK"},
                    "OK"
            );
        }
    }

    
    public static void displayMessage(String message) {
        displayMessage(message, "Game Over");
    }
}
