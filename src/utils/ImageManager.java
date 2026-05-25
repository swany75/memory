/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class ImageManager { // Gestió de les Imatges
    
    /**
     * Carga una imagen desde disco como {@link ImageIcon}.
     *
     * @param path ruta del archivo
     * @return icono cargado
     */
    public static ImageIcon loadIcon(String path) {
        return new ImageIcon(path);
    }
    
    /**
     * Carga una imagen y la reescala con calidad de renderizado.
     *
     * @param path  ruta del archivo
     * @param width ancho objetivo
     * @param height alto objetivo
     * @return icono reescalado
     */
    public static ImageIcon loadScaledIcon(String path, int width, int height) {

        ImageIcon icon = new ImageIcon(path);
        Image srcImg = icon.getImage();

        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resized.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        g2.drawImage(srcImg, 0, 0, width, height, null);
        g2.dispose();

        return new ImageIcon(resized);
    }
 
}
