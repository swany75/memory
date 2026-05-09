/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memory;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileRead {
    private String filename;
    private FileInputStream fileInputStream;
    private BufferedInputStream reader;

    public FileRead(String fname) {
        if (fname != null && !fname.toLowerCase().endsWith(".dat")) {
            this.filename = fname + ".dat";
        } else {
            this.filename = fname;
        }
    }

    public void open() {
        try {
            fileInputStream = new FileInputStream(filename);
            reader = new BufferedInputStream(fileInputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String readLine() {
        if (reader == null) {
            return null;
        }
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int b;
        try {
            while ((b = reader.read()) != -1) {
                if (b == '\n') {
                    break;
                }
                buffer.write(b);
            }
            if (b == -1 && buffer.size() == 0) {
                return null;
            }
            return buffer.toString(StandardCharsets.UTF_8.name());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void close() {
        try {
            if (reader != null) {
                reader.close();
            } else if (fileInputStream != null) {
                fileInputStream.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
