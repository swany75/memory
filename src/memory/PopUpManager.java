/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memory;

import javax.swing.JOptionPane;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class PopUpManager {
    
    public static boolean confirmAction(String actionText) {
        String message = "Are you sure you want to " + actionText + "?";
        int response = -1;

        // Keep showing the dialog if the user tries to close it with the "X" button
        while (response == JOptionPane.CLOSED_OPTION) {
            response = JOptionPane.showConfirmDialog(
                    null, 
                    message, 
                    "Confirmation Required", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE
            );
        }

        // Returns true if YES, false if NO
        return response == JOptionPane.YES_OPTION;
    }
    
    public static void displayMessage(String message, String action) {
        int response = -1;

        while (response == JOptionPane.CLOSED_OPTION) {
            response = JOptionPane.showOptionDialog(
                    null, 
                    message, 
                    action, 
                    JOptionPane.DEFAULT_OPTION, 
                    JOptionPane.WARNING_MESSAGE, 
                    null, 
                    new Object[]{"OK"},
                    "OK"
            );
        }
    }
    
}
