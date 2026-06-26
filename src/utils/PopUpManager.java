/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import core.settings.GameSettings;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class PopUpManager {
    
    /**
     * Muestra un cuadro de confirmación y obliga a elegir una opción.
     *
     * @param actionText texto de la acción a confirmar
     * @return {@code true} si el usuario confirma
     */
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

    
    /**
     * Muestra un mensaje modal con título personalizado.
     *
     * @param message texto del mensaje
     * @param title   título de la ventana
     */
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

    
    /**
     * Muestra un mensaje modal con el título por defecto.
     *
     * @param message texto del mensaje
     */
    public static void displayMessage(String message) {
        displayMessage(message, "Game Over");
    }

    /**
     * Muestra el diálogo inicial de configuración de partida.
     *
     * @return {@code true} si el usuario acepta; {@code false} si cancela o cierra
     */
    public static boolean showStartupConfigurationDialog() {
        JTextField playerNameField = new JTextField("", 16);
        JSlider difficultySlider = createSlider(0, 12, GameSettings.getDifficulty(), 1);
        JSlider timerSlider = createSlider(1, 10, GameSettings.getTimerMinutes(), 1);

        JLabel difficultyValue = new JLabel();
        JLabel timerValue = new JLabel();
        updateDifficultyLabel(difficultyValue, difficultySlider.getValue());
        updateTimerLabel(timerValue, timerSlider.getValue());

        difficultySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateDifficultyLabel(difficultyValue, difficultySlider.getValue());
            }
        });

        timerSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateTimerLabel(timerValue, timerSlider.getValue());
            }
        });

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addRow(panel, gbc, 0, "Player name", playerNameField);
        addSliderRow(panel, gbc, 1, "Difficulty", difficultySlider, difficultyValue);
        addSliderRow(panel, gbc, 2, "Time limit", timerSlider, timerValue);

        int result = JOptionPane.showOptionDialog(
            null,
            panel,
            "Game setup",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            new Object[]{"Accept", "Cancel"},
            "Accept"
        );

        if (result != JOptionPane.OK_OPTION) {
            return false;
        }

        String playerName = playerNameField.getText().trim();
        if (playerName.isEmpty()) {
            GameSettings.setPlayerName(GameSettings.getRandomDefaultPlayerName());
        } else {
            GameSettings.setPlayerName(playerName);
        }
        GameSettings.setDifficulty(difficultySlider.getValue());
        GameSettings.setTimerMinutes(timerSlider.getValue());
        return true;
    }

    private static JSlider createSlider(int min, int max, int value, int majorTick) {
        JSlider slider = new JSlider(min, max, value);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(majorTick);
        slider.setSnapToTicks(true);
        return slider;
    }

    private static void addRow(JPanel form, GridBagConstraints gbc, int row, String labelText, java.awt.Component content) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        form.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        form.add(content, gbc);
    }

    private static void addSliderRow(JPanel form, GridBagConstraints gbc, int row, String labelText, JSlider slider, JLabel valueLabel) {
        JPanel rowPanel = new JPanel(new BorderLayout(8, 0));
        rowPanel.add(slider, BorderLayout.CENTER);
        rowPanel.add(valueLabel, BorderLayout.EAST);
        addRow(form, gbc, row, labelText, rowPanel);
    }

    private static void updateDifficultyLabel(JLabel label, int value) {
        label.setText("Level " + value + " (" + GameSettings.getDifficultyLabel(value) + ")");
    }

    private static void updateTimerLabel(JLabel label, int value) {
        label.setText(value + " min");
    }
}
