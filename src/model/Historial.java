/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import utils.data.FileRead;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class Historial extends JPanel {

    boolean selective = false;

    private JPanel searchPanel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton clearButton;

    private JPanel historialPanel;
    private JTextArea historialArea;
    private JScrollPane scrollPane;

    private String[] allLines;
    private int numLines;

    private static final String FILENAME = "media/files/historial";
    private static final int INCREMENT = 10;

    public Historial() {
        allLines = new String[10];
        numLines = 0;
        setLayout(new BorderLayout(0, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setPanels();
        setListeners();
    }

    public void refresh(boolean selective) {
        this.selective = selective;
        searchField.setText("");
        historialArea.setText("");

        if (selective) {
            searchField.requestFocusInWindow();
        } else {
            loadAll();
            SwingUtilities.invokeLater(new RequestFocusTask());
        }
    }

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

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 4, 0));
        searchButton = new JButton("Buscar");
        clearButton  = new JButton("Clear");
        buttonsPanel.add(searchButton);
        buttonsPanel.add(clearButton);

        searchPanel.add(searchField,  BorderLayout.CENTER);
        searchPanel.add(buttonsPanel, BorderLayout.EAST);
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

    private void loadAll() {
        int[] count = {0};
        allLines = readFileInto(count);
        numLines = count[0];
        showLines(allLines, numLines);
    }

    private String[] readFileInto(int[] count) {
        String[] lines = new String[10];
        int n = 0;
        FileRead fr = new FileRead(FILENAME);
        fr.open();
        String line;
        while ((line = fr.readLine()) != null) {
            if (n == lines.length) {
                String[] bigger = new String[lines.length + INCREMENT];
                for (int i = 0; i < n; i++) bigger[i] = lines[i];
                lines = bigger;
            }
            lines[n++] = line;
        }
        fr.close();
        count[0] = n;
        return lines;
    }

    private void showLines(String[] lines, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append("- ").append(lines[i]).append("\n");
        }
        historialArea.setText(sb.toString());
        historialArea.setCaretPosition(0);
    }

    private void search() {
        String query = searchField.getText().trim().toLowerCase();

        if (query.isEmpty()) {
            if (!selective) showLines(allLines, numLines);
            return;
        }

        String[] source;
        int sourceCount;
        if (selective) {
            int[] count = {0};
            source = readFileInto(count);
            sourceCount = count[0];
        } else {
            source = allLines;
            sourceCount = numLines;
        }

        String[] results = new String[sourceCount];
        int numResults = 0;
        for (int i = 0; i < sourceCount; i++) {
            if (source[i].toLowerCase().contains(query)) {
                results[numResults++] = source[i];
            }
        }

        if (numResults == 0) {
            historialArea.setText("No se encontraron resultados para: \"" + query + "\"");
        } else {
            showLines(results, numResults);
        }
    }
    
    private void setListeners() {
        searchButton.addActionListener(new SearchActionListener());
        searchField.addActionListener(new SearchActionListener());
        clearButton.addActionListener(new ClearActionListener());
    }

    private class SearchActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            search();
        }
    }

    private class ClearActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            searchField.setText("");
            historialArea.setText("");
            historialArea.setCaretPosition(0);
        }
    }



    private class RequestFocusTask implements Runnable {
        @Override
        public void run() {
            scrollPane.requestFocusInWindow();
        }
    }
} 