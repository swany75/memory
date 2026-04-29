/*
PROGRAMA QUE ILUSTRA LA CREACIÓN DE UN BOTÓN PULSADOR CON LA UTILIZACIÓN DE
3 IMÁGENES QUE DEFINEN LOS ESTADOS DE NORMAL, PULSADO Y FOCO DE USUARIO (cuándo
el cursor del rat´ón pasa por encima del botón)

PROGRAMACIÓN II - CURSO 2025_2026
JUAN MONTES DE OCA
UNIVERSITAT DE LES ILLES BALEARS
 */
package pulsadorConEstadosVisibles;
import javax.swing.*;
import java.awt.*;

public class BotonPulsadorInteractivoConEstadosVisibles {
    public static void main(String[] args) {
////////////////////////////////////////////////////////////////////////////////
//                      DECLARACIÓN CONTENEDOR                                //
//////////////////////////////////////////////////////////////////////////////// 
/////////////            DECLARACIÓN CONTENEDOR JFrame          ////////////////
        //CREACION DEL CONTENEDOR JFrame ventana
        JFrame ventana = new JFrame("PULSADOR");
        
/////////////     PANEL DE CONTENIDOS DEL CONTENEDOR JFrame     ////////////////         
        //DECLARACIÓN PANEL DE CONTENIDOS JContentPane DEL CONTENEDOR JFrame 
        //ventana
        Container panelContenidos=ventana.getContentPane();
        //ASIGNACIÓN DEL GESTOR DE LAYOUT FlowLayout AL PANEL DE CONTENIDOS
        //panelContenidos
        panelContenidos.setLayout(new FlowLayout());

/////////////                  IMÁGENES BOTÓN PULSADOR          ////////////////
        ///IMAGEN ESTADO BOTÓN NORMAL
        ///instanciación del objeto ImageIcon IconoNormal con la imagen correspondiente
        ///al estado normal del botón
        ImageIcon iconoNormal = new ImageIcon("imagenesBotones/NUEVA_PARTIDA_SIN.png");
        ///escalado de la imagen
        Image imgEscaladaNormal = iconoNormal.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
        ///asignación al objeto ImageIcon normal de la imagen final resultante del
        ///escalado
        ImageIcon normal = new ImageIcon(imgEscaladaNormal);

        ///IMAGEN ESTADO BOTÓN PULSADO
        ///instanciación del objeto ImageIcon iconoPulsado con la imagen correspondiente
        ///al estado pulsado del botón
        ImageIcon iconoPulsado = new ImageIcon("imagenesBotones/NUEVA_PARTIDA_PULSADO.png");
        ///escalado de la imagen
        Image imgEscaladaPulsado = iconoPulsado.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
        ///asignación al objeto ImageIcon pulsado de la imagen final resultante del
        ///escalado
        ImageIcon pulsado = new ImageIcon(imgEscaladaPulsado);

        ///IMAGEN ESTADO BOTÓN FOCO USUARIO
        ///instanciación del objeto ImageIcon iconoFocalizado con la imagen correspondiente
        ///al estado foco usuario del botón
        ImageIcon iconoFocalizado = new ImageIcon("imagenesBotones/NUEVA_PARTIDA_HOVER.png");
        ///escalado de la imagen
        Image imgEscaladaFocalizado = iconoFocalizado.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
        ///asignación al objeto ImageIcon focalizado de la imagen final resultante del
        ///escalado
        ImageIcon focalizado = new ImageIcon(imgEscaladaFocalizado);

        
////////////////////////////////////////////////////////////////////////////////
//                      DECLARACIÓN COMPONENTES                               //
////////////////////////////////////////////////////////////////////////////////
//////////DECLARACIÓN componente JButton boton ASIGNÁNDOLE EL OBJETO imageIcon normal
        JButton boton = new JButton(normal);
        ///ACTIVACIÓN DEL OBJETO ImageIcon pulsado CUANDO EL USUARIO PULSE EL
        ///BOTÓN
        boton.setPressedIcon(pulsado);
        ///ACTIVACIÓN DEL OBJETO ImageIcon focalizado CUANDO EL CURSOR DEL RATÓN
        ///PASE POR ENCIMA DEL BOTÓN
        boton.setRolloverIcon(focalizado);

        ///DEFINICIÓN ESTILO RATÓN
        ///NO VISUALIZACIÓN DEL BORDE DEL BOTÓN
        boton.setBorderPainted(false);
        ///NO INTERACCIÓN CON EL AREA DEL BOTÓN NO OCUPADA POR EL OBJETO ImageIcon
        boton.setContentAreaFilled(false);

        ///INCLUSIÓN DEL BOTÓN EN EL PANEL DE CONTENIDOS DEL JFrame ventana
        panelContenidos.add(boton);
////////////////////////////////////////////////////////////////////////////////
//                  ÚLTIMAS INTERVENCIONES CONTENEDOR JFrame                  //
////////////////////////////////////////////////////////////////////////////////  
        //DIMENSIONAMIENTO AUTOMÁTICO DEL CONTENEDOR JFrame ventana EN FUNCIÓN
        //DE LAS COMPONENTES INTRODUCIDAS EN ÉL
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