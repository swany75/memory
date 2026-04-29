/*
PROGRAMA QUE VISUALIZA UN TABLERO CON 4 CASILLAS QUE AL PULSAR EN ELLAS SE LLEVA
A CABO EL GIRO DE LAS IMÁGENES (ANVERSO Y REVERSO) ASOCIADAS A LAS MISMAS

PROGRAMACIÓN II - CURSO 2025_2026
JUAN MONTES DE OCA
UNIVERSITAT DE LES ILLES BALEARS
 */
package TableroConGiroDeCasillas;

import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JFrame;

public class Principal {
    //método main
    public static void main(String [] argumentos) throws Exception {
        new Principal().metodoPrincipal();
    }
    
    //método metodoPrincipal
    private void metodoPrincipal() throws Exception {
////////////////////////////////////////////////////////////////////////////////
//                              DECLARACIONES                                 //
//////////////////////////////////////////////////////////////////////////////// 
////////////////////////////////////////////////////////////////////////////////
//                      DECLARACIÓN CONTENEDOR                                //
//////////////////////////////////////////////////////////////////////////////// 
/////////////            DECLARACIÓN CONTENEDOR JFrame          ////////////////
        //CREACION DEL CONTENEDOR JFrame ventana      
        JFrame ventana=new JFrame("TABLERO GIRO IMAGENES");
        
/////////////     PANEL DE CONTENIDOS DEL CONTENEDOR JFrame     ////////////////         
        //DECLARACIÓN PANEL DE CONTENIDOS JContentPane DEL CONTENEDOR JFrame 
        //ventana
        Container panelContenidos=ventana.getContentPane();
        //ASIGNACIÓN DEL GESTOR DE LAYOUT BorderLayout AL PANEL DE CONTENIDOS
        //panelContenidos
        panelContenidos.setLayout(new FlowLayout());   
        
////////////////////////////////////////////////////////////////////////////////
//                      DECLARACIÓN COMPONENTES                               //
//////////////////////////////////////////////////////////////////////////////// 
//////////DECLARACIÓN CONTENEDOR JPanel tablero
        Tablero tablero=new Tablero();

//////////INTRUDUCCIÓN EN EL CONTENEDOR panelContenidos DEL JFrame ventana EL
        //CONTENEDOR tablero
        panelContenidos.add(tablero);
         
         
////////////////////////////////////////////////////////////////////////////////
//                  ÚLTIMAS INTERVENCIONES CONTENEDOR JFrame                  //
////////////////////////////////////////////////////////////////////////////////  
        //DIMENSIONAMIENTO DEL CONTENEDOR JFrame ventana 
        ventana.pack();
        //CENTRADO DEL CONTENEDOR ventana EN EL CENTRO DE LA PANTALLA
        ventana.setLocationRelativeTo(null);
        //ACTIVACIÓN DEL CIERRE INTERACTIVO VENTANA DE WINDOWS EN EL CONTENEDOR 
        //JFrame ventana
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        //VISUALIZACIÓN CONTENEDOR JFrame ventana
        ventana.setVisible(true);
    } 
}
