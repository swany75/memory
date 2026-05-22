/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import data.FileWrite;
import ui.PopUpManager;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class Settings extends JPanel { // Classe dels Settings

    private static final String HISTORY_FILE = "media/files/historial";

    private JTextField playerNameField;
    private JLabel difficultyValue;
    private JSlider difficultySlider;

    private JLabel timerValue;
    private JSlider timerSlider;

    private JLabel soundValue;
    private JSlider soundSlider;

    private JLabel musicValue;
    private JSlider musicSlider;

    private JCheckBox muteCheckBox;
    private JTextField cardsFolderField;
    private JButton clearHistoryButton;
    private JButton resetButton;

    public Settings() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        addRow(form, gbc, row++, "Nombre jugador", buildPlayerRow());
        addRow(form, gbc, row++, "Carpeta de cartas", buildCardsFolderRow());
        addRow(form, gbc, row++, "Dificultad", buildDifficultyRow());
        addRow(form, gbc, row++, "Tiempo máximo", buildTimerRow());
        addRow(form, gbc, row++, "Volumen sonidos", buildSoundRow());
        addRow(form, gbc, row++, "Volumen música", buildMusicRow());
        addRow(form, gbc, row++, "Mute rápido", buildMuteRow());

        add(form, BorderLayout.NORTH);
        add(buildActionsPanel(), BorderLayout.SOUTH);
    }

    private JPanel buildPlayerRow() {
        JPanel panel = new JPanel(new BorderLayout(8, 0));
        playerNameField = new JTextField(GameSettings.getPlayerName());
        playerNameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyPlayerName();
            }
        });
        playerNameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                applyPlayerName();
            }
        });
        panel.add(playerNameField, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildCardsFolderRow() {
        JPanel panel = new JPanel(new BorderLayout(8, 0));
        cardsFolderField = new JTextField(GameSettings.getCardsDir());
        cardsFolderField.setEditable(false);

        JButton browseButton = new JButton("Explorar...");
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFolderChooser();
            }
        });

        panel.add(cardsFolderField, BorderLayout.CENTER);
        panel.add(browseButton, BorderLayout.EAST);
        return panel;
    }

    private JPanel buildDifficultyRow() {
        difficultySlider = createSlider(0, 12, GameSettings.getDifficulty(), 1);
        difficultyValue = new JLabel();
        updateDifficultyLabel(difficultySlider.getValue());

        difficultySlider.addChangeListener(new DifficultyChangeListener());
        return buildSliderRow(difficultySlider, difficultyValue);
    }

    private JPanel buildTimerRow() {
        timerSlider = createSlider(1, 10, GameSettings.getTimerMinutes(), 1);
        timerValue = new JLabel();
        updateTimerLabel(timerSlider.getValue());

        timerSlider.addChangeListener(new TimerChangeListener());
        return buildSliderRow(timerSlider, timerValue);
    }

    private JPanel buildSoundRow() {
        soundSlider = createSlider(0, 100, GameSettings.getSoundVolume(), 10);
        soundValue = new JLabel();
        updateVolumeLabel(soundValue, soundSlider.getValue());

        soundSlider.addChangeListener(new SoundVolumeChangeListener());
        return buildSliderRow(soundSlider, soundValue);
    }

    private JPanel buildMusicRow() {
        musicSlider = createSlider(0, 100, GameSettings.getMusicVolume(), 10);
        musicValue = new JLabel();
        updateVolumeLabel(musicValue, musicSlider.getValue());

        musicSlider.addChangeListener(new MusicVolumeChangeListener());
        return buildSliderRow(musicSlider, musicValue);
    }

    private JPanel buildMuteRow() {
        JPanel panel = new JPanel(new BorderLayout(8, 0));
        muteCheckBox = new JCheckBox("Silenciar todo");
        muteCheckBox.setSelected(GameSettings.isMuted());
        muteCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameSettings.setMuted(muteCheckBox.isSelected());
            }
        });
        panel.add(muteCheckBox, BorderLayout.WEST);
        return panel;
    }

    private JPanel buildActionsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        clearHistoryButton = new JButton("Vaciar historial");
        clearHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearHistory();
            }
        });
        panel.add(clearHistoryButton);

        resetButton = new JButton("Restaurar ajustes");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restoreDefaults();
            }
        });
        panel.add(resetButton);
        return panel;
    }

    private JSlider createSlider(int min, int max, int value, int majorTick) {
        JSlider slider = new JSlider(min, max, value);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(majorTick);
        slider.setSnapToTicks(true);
        return slider;
    }

    private JPanel buildSliderRow(JSlider slider, JLabel valueLabel) {
        JPanel panel = new JPanel(new BorderLayout(8, 0));
        panel.add(slider, BorderLayout.CENTER);
        panel.add(valueLabel, BorderLayout.EAST);
        return panel;
    }

    private void addRow(JPanel form, GridBagConstraints gbc, int row, String labelText, JPanel content) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        form.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        form.add(content, gbc);
    }

    private void openFolderChooser() {
        JFileChooser chooser = new JFileChooser(new File(GameSettings.getCardsDir()));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Seleccionar carpeta de cartas");

        int result = chooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) return;

        File selected = chooser.getSelectedFile();
        if (selected == null) return;

        String dir = selected.getPath();
        GameSettings.setCardsDir(dir);
        cardsFolderField.setText(dir);

        String backImage = GameSettings.findBackImagePath(dir);
        if (backImage == null) {
            PopUpManager.displayMessage(
                "No se encontró un archivo backImage en la carpeta seleccionada.",
                "BackImage no encontrado"
            );
            return;
        }
        GameSettings.setBackImagePath(backImage);
    }

    private void updateDifficultyLabel(int value) {
        difficultyValue.setText("Nivel " + value + " (" + GameSettings.getDifficultyLabel(value) + ")");
    }

    private void updateTimerLabel(int value) {
        timerValue.setText(value + " min");
    }

    private void updateVolumeLabel(JLabel label, int value) {
        label.setText(value + "%");
    }

    private void restoreDefaults() {
        GameSettings.resetDefaults();
        cardsFolderField.setText(GameSettings.getCardsDir());
        playerNameField.setText(GameSettings.getPlayerName());
        muteCheckBox.setSelected(GameSettings.isMuted());

        difficultySlider.setValue(GameSettings.getDifficulty());
        timerSlider.setValue(GameSettings.getTimerMinutes());
        soundSlider.setValue(GameSettings.getSoundVolume());
        musicSlider.setValue(GameSettings.getMusicVolume());

        updateDifficultyLabel(GameSettings.getDifficulty());
        updateTimerLabel(GameSettings.getTimerMinutes());
        updateVolumeLabel(soundValue, GameSettings.getSoundVolume());
        updateVolumeLabel(musicValue, GameSettings.getMusicVolume());
    }

    private void applyPlayerName() {
        String value = playerNameField.getText().trim();
        if (value.isEmpty()) {
            playerNameField.setText(GameSettings.getPlayerName());
            return;
        }
        GameSettings.setPlayerName(value);
        playerNameField.setText(GameSettings.getPlayerName());
    }

    private void clearHistory() {
        boolean confirmed = PopUpManager.confirmAction("vaciar el historial");
        if (!confirmed) return;

        FileWrite writer = new FileWrite(HISTORY_FILE);
        writer.open();
        writer.close();
        PopUpManager.displayMessage("Historial vaciado.", "Historial");
    }

    private class DifficultyChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            int value = difficultySlider.getValue();
            GameSettings.setDifficulty(value);
            updateDifficultyLabel(value);
        }
    }

    private class TimerChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            int value = timerSlider.getValue();
            GameSettings.setTimerMinutes(value);
            updateTimerLabel(value);
        }
    }

    private class SoundVolumeChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            int value = soundSlider.getValue();
            GameSettings.setSoundVolume(value);
            updateVolumeLabel(soundValue, value);
        }
    }

    private class MusicVolumeChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            int value = musicSlider.getValue();
            GameSettings.setMusicVolume(value);
            updateVolumeLabel(musicValue, value);
        }
    }

}
