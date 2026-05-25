/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.components;

import java.awt.Color;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class CustomColors {
    
    // Persona
    public static final Color P3_MAIN = new Color(0x0104DB);
    public static final Color P4_MAIN = new Color(0xFDF500);
    public static final Color P5_MAIN = new Color(0xED1D24);
    public static final Color TARTARUS = new Color(0x25BC0A);
    
    // Persona 3
    public static final Color P3_BLUE = new Color(0, 98, 255);
    public static final Color P3_CYAN = new Color(0, 210, 255);
    public static final Color P3_DARK_NAVY = new Color(11, 14, 17);
    public static final Color P3_WHITE = new Color(240, 240, 240);
    public static final Color P3_SKY = new Color(128, 223, 255);
    public static final Color P3_STEEL_BLUE = new Color(109, 154, 199);
    public static final Color P3_DEEP_BLUE = new Color(18, 105, 204);
    public static final Color P3_BRIGHT_CYAN = new Color(81, 238, 252);
    public static final Color P3_DARK_GRAY = new Color(48, 48, 48);
    public static final Color P3_RED_ALERT = new Color(255, 75, 75);
    
    // Amstrad CPC 464
    public static final Color AMSTRAD_DARK_GREY = new Color(0x373A36);
    public static final Color AMSTRAD_LIGHT_GREY = new Color(0x53565A);
    public static final Color AMSTRAD_RED = new Color(0xE10600);
    public static final Color AMSTRAD_BLUE = new Color(0x0061A0);
    public static final Color AMSTRAD_GREEN = new Color(0x48A23F);
    public static final Color HACKER_GREEN = new Color(0x00FF00);
    public static final Color TERMINAL = new Color(0x1C262C);
    public static final Color BACKGROUND = new Color(0x263238);
    public static final Color CYAN_KHAN = new Color(0x16B290);
    
    // Other Colors
    public static final Color EGG_White = new Color(0xF7EFE0);
    public static final Color PASTEL_YELLOW = new Color(0xFEEE8D);
    public static final Color UIB_BLUE = new Color(1, 93, 185);
        
    // Metodos para los colores
    /**
     * Genera un color de sombra a partir de un color base.
     *
     * @param base color base
     * @return color de sombra
     */
    public static Color shadowColor(Color base) {
        return scaleColor(base, 0.68f);
    }

    /**
     * Genera un color de hover ligeramente más brillante.
     *
     * @param base color base
     * @return color para estado hover
     */
    public static Color hoverColor(Color base) {
        int r = base.getRed();
        int g = base.getGreen();
        int b = base.getBlue();
        int max = Math.max(r, Math.max(g, b));
        return new Color(
            clamp((int)(r * 1.08f) + (r == max ? 12 : 0)),
            clamp((int)(g * 1.08f) + (g == max ? 12 : 0)),
            clamp((int)(b * 1.08f) + (b == max ? 12 : 0))
        );
    }

    /**
     * Escala los canales RGB de un color por un factor.
     *
     * @param c      color original
     * @param factor factor de escala
     * @return color escalado
     */
    private static Color scaleColor(Color c, float factor) {
        return new Color(
            clamp((int)(c.getRed()   * factor)),
            clamp((int)(c.getGreen() * factor)),
            clamp((int)(c.getBlue()  * factor))
        );
    }

    /**
     * Limita un canal RGB al rango 0-255.
     *
     * @param v valor a limitar
     * @return valor acotado
     */
    private static int clamp(int v) {
        return Math.min(255, Math.max(0, v));
    }
       
}
