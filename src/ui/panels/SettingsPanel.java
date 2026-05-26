/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.panels;

import core.settings.GameSettings;
import utils.data.FileWrite;
import utils.PopUpManager;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.event.*;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class SettingsPanel extends JPanel { // Classe dels Settings

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
    private JCheckBox fullScreenCheckBox;
    private JTextField cardsFolderField;
    private JButton clearHistoryButton;
    private JButton resetButton;

    /**
     * Construye el panel de ajustes y sus componentes.
     */
    public SettingsPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        addRow(form, gbc, row++, "Player name", buildPlayerRow());
        addRow(form, gbc, row++, "Cards folder", buildCardsFolderRow());
        addRow(form, gbc, row++, "Difficulty", buildDifficultyRow());
        addRow(form, gbc, row++, "Time limit", buildTimerRow());
        addRow(form, gbc, row++, "Sound volume", buildSoundRow());
        addRow(form, gbc, row++, "Music volume", buildMusicRow());
        addRow(form, gbc, row++, "Quick mute", buildMuteRow());
        addRow(form, gbc, row++, "Full screen", buildFullScreenRow());

        add(form, BorderLayout.NORTH);
        add(buildActionsPanel(), BorderLayout.SOUTH);
    }

    /**
     * Sincroniza el estado del checkbox de pantalla completa al añadirse al árbol.
     */
    @Override
    public void addNotify() {
        super.addNotify();
        syncFullScreenState();
    }

    /**
     * Construye la fila del nombre del jugador con validación automática.
     *
     * @return panel de la fila
     */
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

    /**
     * Construye la fila de selección de carpeta de cartas.
     *
     * @return panel de la fila
     */
    private JPanel buildCardsFolderRow() {
        JPanel panel = new JPanel(new BorderLayout(8, 0));
        cardsFolderField = new JTextField(GameSettings.getCardsDir());
        cardsFolderField.setEditable(false);

        JButton browseButton = new JButton("Browse...");
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

    /**
     * Construye la fila del selector de dificultad.
     *
     * @return panel de la fila
     */
    private JPanel buildDifficultyRow() {
        difficultySlider = createSlider(0, 12, GameSettings.getDifficulty(), 1);
        difficultyValue = new JLabel();
        updateDifficultyLabel(difficultySlider.getValue());

        difficultySlider.addChangeListener(new DifficultyChangeListener());
        return buildSliderRow(difficultySlider, difficultyValue);
    }

    /**
     * Construye la fila del selector de tiempo máximo.
     *
     * @return panel de la fila
     */
    private JPanel buildTimerRow() {
        timerSlider = createSlider(1, 10, GameSettings.getTimerMinutes(), 1);
        timerValue = new JLabel();
        updateTimerLabel(timerSlider.getValue());

        timerSlider.addChangeListener(new TimerChangeListener());
        return buildSliderRow(timerSlider, timerValue);
    }

    /**
     * Construye la fila del selector de volumen de efectos.
     *
     * @return panel de la fila
     */
    private JPanel buildSoundRow() {
        soundSlider = createSlider(0, 100, GameSettings.getSoundVolume(), 10);
        soundValue = new JLabel();
        updateVolumeLabel(soundValue, soundSlider.getValue());

        soundSlider.addChangeListener(new SoundVolumeChangeListener());
        return buildSliderRow(soundSlider, soundValue);
    }

    /**
     * Construye la fila del selector de volumen de música.
     *
     * @return panel de la fila
     */
    private JPanel buildMusicRow() {
        musicSlider = createSlider(0, 100, GameSettings.getMusicVolume(), 10);
        musicValue = new JLabel();
        updateVolumeLabel(musicValue, musicSlider.getValue());

        musicSlider.addChangeListener(new MusicVolumeChangeListener());
        return buildSliderRow(musicSlider, musicValue);
    }

    /**
     * Construye la fila de silenciamiento rápido.
     *
     * @return panel de la fila
     */
    private JPanel buildMuteRow() {
        JPanel panel = new JPanel(new BorderLayout(8, 0));
        muteCheckBox = new JCheckBox("Mute all");
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

    /**
     * Construye la fila del selector de pantalla completa.
     *
     * @return panel de la fila
     */
    private JPanel buildFullScreenRow() {
        JPanel panel = new JPanel(new BorderLayout(8, 0));
        fullScreenCheckBox = new JCheckBox("Enable");
        fullScreenCheckBox.setSelected(false);
        fullScreenCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyFullScreen(fullScreenCheckBox.isSelected());
            }
        });
        panel.add(fullScreenCheckBox, BorderLayout.WEST);
        return panel;
    }

    /**
     * Construye el panel inferior con acciones globales.
     *
     * @return panel de acciones
     */
    private JPanel buildActionsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        clearHistoryButton = new JButton("Clear history");
        clearHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearHistory();
            }
        });
        panel.add(clearHistoryButton);

        resetButton = new JButton("Restore defaults");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restoreDefaults();
            }
        });
        panel.add(resetButton);
        return panel;
    }

    /**
     * Crea un slider con marcas y ajuste a pasos fijos.
     *
     * @param min       mínimo permitido
     * @param max       máximo permitido
     * @param value     valor inicial
     * @param majorTick separación entre marcas principales
     * @return slider configurado
     */
    private JSlider createSlider(int min, int max, int value, int majorTick) {
        JSlider slider = new JSlider(min, max, value);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(majorTick);
        slider.setSnapToTicks(true);
        return slider;
    }

    /**
     * Monta una fila estándar con slider y etiqueta a la derecha.
     *
     * @param slider     barra de control
     * @param valueLabel etiqueta del valor
     * @return panel de la fila
     */
    private JPanel buildSliderRow(JSlider slider, JLabel valueLabel) {
        JPanel panel = new JPanel(new BorderLayout(8, 0));
        panel.add(slider, BorderLayout.CENTER);
        panel.add(valueLabel, BorderLayout.EAST);
        return panel;
    }

    /**
     * Añade una fila con etiqueta a la izquierda y contenido a la derecha.
     *
     * @param form     panel contenedor
     * @param gbc      constraints reutilizables
     * @param row      índice de fila
     * @param labelText texto de la etiqueta
     * @param content  panel de contenido
     */
    private void addRow(JPanel form, GridBagConstraints gbc, int row, String labelText, JPanel content) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        form.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        form.add(content, gbc);
    }

    /**
     * Abre el selector de carpetas y actualiza la ruta de cartas.
     */
    private void openFolderChooser() {
        JFileChooser chooser = new JFileChooser(new File(GameSettings.getCardsDir()));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Select cards folder");

        int result = chooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) return;

        File selected = chooser.getSelectedFile();
        if (selected == null) return;

        String dir = selected.getPath();
        GameSettings.setCardsDir(dir);
        cardsFolderField.setText(dir);
    }

    /**
     * Actualiza la etiqueta de dificultad según el valor del slider.
     *
     * @param value nivel de dificultad
     */
    private void updateDifficultyLabel(int value) {
        difficultyValue.setText("Level " + value + " (" + GameSettings.getDifficultyLabel(value) + ")");
    }

    /**
     * Actualiza la etiqueta del tiempo máximo.
     *
     * @param value minutos configurados
     */
    private void updateTimerLabel(int value) {
        timerValue.setText(value + " min");
    }

    /**
     * Actualiza una etiqueta de porcentaje de volumen.
     *
     * @param label etiqueta a actualizar
     * @param value porcentaje
     */
    private void updateVolumeLabel(JLabel label, int value) {
        label.setText(value + "%");
    }

    /**
     * Restaura todos los valores a los ajustes por defecto.
     */
    private void restoreDefaults() {
        GameSettings.resetDefaults();
        cardsFolderField.setText(GameSettings.getCardsDir());
        playerNameField.setText(GameSettings.getPlayerName());
        muteCheckBox.setSelected(GameSettings.isMuted());
        fullScreenCheckBox.setSelected(false);
        applyFullScreen(false);

        difficultySlider.setValue(GameSettings.getDifficulty());
        timerSlider.setValue(GameSettings.getTimerMinutes());
        soundSlider.setValue(GameSettings.getSoundVolume());
        musicSlider.setValue(GameSettings.getMusicVolume());

        updateDifficultyLabel(GameSettings.getDifficulty());
        updateTimerLabel(GameSettings.getTimerMinutes());
        updateVolumeLabel(soundValue, GameSettings.getSoundVolume());
        updateVolumeLabel(musicValue, GameSettings.getMusicVolume());
    }

    /**
     * Valida y aplica el nombre del jugador desde el campo de texto.
     */
    private void applyPlayerName() {
        String value = playerNameField.getText().trim();
        if (value.isEmpty()) {
            playerNameField.setText(GameSettings.getPlayerName());
            return;
        }
        GameSettings.setPlayerName(value);
        playerNameField.setText(GameSettings.getPlayerName());
    }

    /**
     * Aplica el cambio de pantalla completa sobre la ventana principal.
     *
     * @param enabled {@code true} para activar pantalla completa
     */
    private void applyFullScreen(boolean enabled) {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window instanceof MainFrame) {
            ((MainFrame) window).setFullScreen(enabled);
        }
    }

    /**
     * Actualiza el checkbox según el estado actual de la ventana.
     */
    private void syncFullScreenState() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window instanceof MainFrame) {
            fullScreenCheckBox.setSelected(((MainFrame) window).isFullScreen());
        }
    }

    /**
     * Borra el historial almacenado tras confirmación.
     */
    private void clearHistory() {
        boolean confirmed = PopUpManager.confirmAction("clear the history");
        if (!confirmed) return;

        FileWrite writer = new FileWrite(HISTORY_FILE);
        writer.open();
        writer.close();
        PopUpManager.displayMessage("History cleared.", "History");
    }

    private class DifficultyChangeListener implements ChangeListener {
        /**
         * Actualiza la dificultad al mover el slider.
         *
         * @param e evento de cambio
         */
        @Override
        public void stateChanged(ChangeEvent e) {
            int value = difficultySlider.getValue();
            GameSettings.setDifficulty(value);
            updateDifficultyLabel(value);
        }
    }

    private class TimerChangeListener implements ChangeListener {
        /**
         * Actualiza el tiempo máximo al mover el slider.
         *
         * @param e evento de cambio
         */
        @Override
        public void stateChanged(ChangeEvent e) {
            int value = timerSlider.getValue();
            GameSettings.setTimerMinutes(value);
            updateTimerLabel(value);
        }
    }

    private class SoundVolumeChangeListener implements ChangeListener {
        /**
         * Actualiza el volumen de efectos al mover el slider.
         *
         * @param e evento de cambio
         */
        @Override
        public void stateChanged(ChangeEvent e) {
            int value = soundSlider.getValue();
            GameSettings.setSoundVolume(value);
            updateVolumeLabel(soundValue, value);
        }
    }

    private class MusicVolumeChangeListener implements ChangeListener {
        /**
         * Actualiza el volumen de música al mover el slider.
         *
         * @param e evento de cambio
         */
        @Override
        public void stateChanged(ChangeEvent e) {
            int value = musicSlider.getValue();
            GameSettings.setMusicVolume(value);
            updateVolumeLabel(musicValue, value);
        }
    }

}