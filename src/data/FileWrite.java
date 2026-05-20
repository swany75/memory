/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

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
    
    public FileWrite(String fname) {
        if (fname != null && !fname.toLowerCase().endsWith(".dat")) {
            this.filename = fname + ".dat";
        } else {
            this.filename = fname;
        }
    }
    
    public void open() {
        try {
            fileStream = new FileOutputStream(filename);
            buffer = new BufferedOutputStream(fileStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
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