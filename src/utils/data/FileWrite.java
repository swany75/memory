/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils.data;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileWrite {
    
    private String filename;
    private FileOutputStream fileStream;
    private BufferedOutputStream buffer;
    private boolean append;
    
    /**
     * Crea un escritor que sobrescribe el fichero destino.
     *
     * @param fname nombre base o ruta del fichero
     */
    public FileWrite(String fname) {
        this(fname, false);
    }

    /**
     * Crea un escritor con opción de añadir al final del fichero.
     *
     * @param fname  nombre base o ruta del fichero
     * @param append {@code true} para añadir al final
     */
    public FileWrite(String fname, boolean append) {
        if (fname != null && !fname.toLowerCase().endsWith(".dat")) {
            this.filename = fname + ".dat";
        } else {
            this.filename = fname;
        }
        this.append = append;
    }
    
    /**
     * Abre el flujo de escritura.
     */
    public void open() {
        try {
            fileStream = new FileOutputStream(filename, append);
            buffer = new BufferedOutputStream(fileStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Escribe una línea en el fichero.
     *
     * @param line línea a escribir
     */
    public void writeLine(String line) {
        try {
            if (buffer != null && line != null) {
                byte[] dades = line.getBytes(StandardCharsets.UTF_8);
                buffer.write(dades);
                buffer.write('\n');
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Cierra el flujo de escritura.
     */
    public void close() {
        try {
            if (buffer != null) {
                buffer.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}