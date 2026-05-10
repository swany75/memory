/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */
public class Historial extends JPanel {

    boolean selective = false;

    // Panel superior: barra de búsqueda + botones
    private JPanel searchPanel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton clearButton;

    // Panel inferior: área de historial con scroll
    private JPanel historialPanel;
    private JTextArea historialArea;
    private JScrollPane scrollPane;

    // Datos cargados del fichero
    private List<String> allLines = new ArrayList<>();

    private static final String FILENAME = "media/files/historial";

    Historial() {
        setLayout(new BorderLayout(0, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setPanels();
        setListeners();
    }

    // ── Refresh: llamado desde ContentPanel al cambiar de pestaña ────────
    public void refresh(boolean selective) {
        this.selective = selective;
        searchField.setText("");
        historialArea.setText("");

        if (selective) {
            searchField.requestFocusInWindow();
        } else {
            loadAll();
        }
    }

    // ── Construcción de paneles ───────────────────────────────────────────
    private void setPanels() {
        setTopPanel();
        setHistorialPanel();
        add(searchPanel,    BorderLayout.NORTH);
        add(historialPanel, BorderLayout.CENTER);
    }

    private void setTopPanel() {
        searchPanel = new JPanel(new BorderLayout(6, 0));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        searchField = new JTextField();
        searchField.setToolTipText("Buscar en el historial...");

        // Botones tamaño fijo e igual
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 4, 0));
        searchButton = new JButton("Buscar");
        clearButton  = new JButton("Clear");
        buttonsPanel.add(searchButton);
        buttonsPanel.add(clearButton);

        searchPanel.add(searchField,  BorderLayout.CENTER); // se estira todo lo posible
        searchPanel.add(buttonsPanel, BorderLayout.EAST);   // tamaño fijo
    }

    private void setHistorialPanel() {
        historialArea = new JTextArea();
        historialArea.setEditable(false);
        historialArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        historialArea.setLineWrap(true);
        historialArea.setWrapStyleWord(true);
        historialArea.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        scrollPane = new JScrollPane(historialArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        historialPanel = new JPanel(new BorderLayout());
        historialPanel.add(scrollPane, BorderLayout.CENTER);
    }

    // ── Listeners ─────────────────────────────────────────────────────────
    private void setListeners() {
        searchButton.addActionListener(e -> search());
        searchField.addActionListener(e -> search()); // Enter también busca

        clearButton.addActionListener(e -> {
            searchField.setText("");
            if (selective) {
                historialArea.setText("");
            } else {
                showLines(allLines); // restaura el historial completo
            }
        });
    }

    // ── Lógica de datos ───────────────────────────────────────────────────
    private void loadAll() {
        allLines = readFile();
        showLines(allLines);
    }

    private List<String> readFile() {
        List<String> lines = new ArrayList<>();
        FileRead fr = new FileRead(FILENAME);
        fr.open();
        String line;
        while ((line = fr.readLine()) != null) {
            lines.add(line);
        }
        fr.close();
        return lines;
    }

    private void showLines(List<String> lines) {
        StringBuilder sb = new StringBuilder();
        for (String l : lines) {
            sb.append("- ").append(l).append("\n");
        }
        historialArea.setText(sb.toString());
        historialArea.setCaretPosition(0); // volver arriba
    }

    private void search() {
        String query = searchField.getText().trim().toLowerCase();

        if (query.isEmpty()) {
            if (!selective) showLines(allLines);
            return;
        }

        List<String> source = selective ? readFile() : allLines;
        List<String> results = new ArrayList<>();

        for (String line : source) {
            if (line.toLowerCase().contains(query)) {
                results.add(line);
            }
        }

        if (results.isEmpty()) {
            historialArea.setText("No se encontraron resultados para: \"" + query + "\"");
        } else {
            showLines(results);
        }
    }
}