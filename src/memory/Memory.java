/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package memory;

import ui.panels.MainFrame;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class Memory { // Codi Principal

    /**
     * Punto de entrada de la aplicación. Arranca la interfaz principal del juego.
     *
     * @param args argumentos de línea de comandos (no usados)
     */
    public static void main(String[] args) {
        // TODO code application logic here
        (new Memory()).run();
    }
    
    /**
     * Inicializa y muestra la ventana principal del juego.
     */
    private void run() {
        new MainFrame();
    }
    
}
