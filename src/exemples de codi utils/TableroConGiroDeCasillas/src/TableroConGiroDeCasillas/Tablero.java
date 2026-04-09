/*
CLASE Tablero de objetos Casilla

PROGRAMACIÓN II - CURSO 2025_2026
JUAN MONTES DE OCA
UNIVERSITAT DE LES ILLES BALEARS
 */
package TableroConGiroDeCasillas;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Tablero extends JPanel {
    //DECLARACIONES ATRIBUTOS
    private static final int NUMERO_PAREJAS=2;
    private Casilla [][] casillas;
    private int numeroFilas,numeroColumnas;
    private String directorioImagenes="imagenes",raizNombreImagenes="imagen";
    
    public Tablero() {
        numeroFilas=2;
        numeroColumnas=2;
        inicializacion();
    }
    
    public Tablero(String directorio, String nombre) {
        numeroFilas=2;
        numeroColumnas=2;
        directorioImagenes=directorio;
        raizNombreImagenes=nombre;
        inicializacion();
    }
    
    private void inicializacion() {
        //ASIGNACIÓN AL CONTENEDOR tablero DEL ADMINISTRADOR 
        //DE LAYOUT GridLayout CON 2 FILAS Y 2 COLUMNAS CON ESPACIADO HORIZONTAL
        //Y VERTICAL DE 10
        setLayout((new GridLayout(2, 2,5,5))); 
        //ASIGNACIÓN COLOR DE FONDO CYAN
        setBackground(Color.orange);
        //ASIGNACIÓN BORDE DE DIMENSIÓN 30
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        casillas=new Casilla[numeroFilas][numeroFilas];
        int numero=1;
        for (int fila=0; fila<numeroFilas;fila++) {
            for (int columna=0;columna<numeroColumnas;columna++,numero++) {
                casillas[fila][columna]=new Casilla("/"+directorioImagenes+"/"+raizNombreImagenes+(numero)+".png", "/"+directorioImagenes+"/reverso.png");   
//                casillas[fila][columna]=new Casilla("/resources/img"+numero+".png", "/resources/back.png");
                add(casillas[fila][columna]);
            }
        }
    }
}
