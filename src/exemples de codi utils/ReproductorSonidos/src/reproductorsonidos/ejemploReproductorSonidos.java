/*
EJEMPLO REPRODUCTOR DE SONIDOS

PROGRAMACIÓN II
JUAN MONTES DE OCA
UNIVERSITAT DE LES ILLES BALEARS
*/

package reproductorsonidos;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

////////////////////////////////////////////////////////////////////////////////
//                                                                            //
//                         Problema1_Interface_A                              //
//                                                                            //
////////////////////////////////////////////////////////////////////////////////    

public class ejemploReproductorSonidos {
    //ATRIBUTOS
    private Container panelContenidos;
    private JFrame ventana;
    
                

    public static void main(String[] args) {
        new ejemploReproductorSonidos().metodoPrincipal();        
    }

    private void metodoPrincipal() {   
        //declaración contenedor JFrame 
        ventana=new JFrame();
        //título contenedor pruebaBotones
        ventana.setTitle("EJEMPLO DE INTERFACE NÚMERO 1");
        //asignación a panelContenidos del panel de contenidos del contenedor
        //JFrame
        panelContenidos=ventana.getContentPane();  
        inicializacion();
    }

    private void inicializacion() {
        
////////////////////////////////////////////////////////////////////////////////
//                              GESTOR DE EVENTOS                             //
////////////////////////////////////////////////////////////////////////////////

////////MANIPULADOR DE EVENTOS gestorEventos
        ActionListener gestorEventos=new ActionListener()  { 
                String sonido;
                @Override
                public void actionPerformed(ActionEvent evento)  { 
                        switch (evento.getActionCommand()) {
                            case "DO"         :sonido="do.wav";break;
                            case "RE"         :sonido="re.wav";break;
                            case "MI"         :sonido="mi.wav";break;
                            case "FA"         :sonido="fa.wav";break;
                            case "SOL"        :sonido="sol.wav";break;
                            case "LA"         :sonido="la.wav";break;
                            case "SI"         :sonido="si.wav";break;
                            case "SALIR"        ://Salir de la aplicación
                                                 System.exit(0);
                        }
                        //reproducción sonido
                        reproduccionSonido(sonido);
                }
        }; 


////////////////////////////////////////////////////////////////////////////////
//                      PANEL panelBotones y COMPONENTES                      //
////////////////////////////////////////////////////////////////////////////////
        
/////////DECLARACIÓN CONTENEDOR JPanel panelBotones 
        JPanel panelBotones = new JPanel();     
        //asignación administrador GridLayout al contenedor con 8 filas y una
        //columna
        panelBotones.setLayout(new GridLayout( 1, 7 ));

////////////////////////////////////////////////////////////////////////////////
//    MODALIDAD DE DECLARACIÓN A TRAVÉS DE UN BUCLE ITERATIVO DE TODOS LOS    //
//    JButton CORRESPONDIENTES A LAS OPCIONES DE COLOR                        //
////////////////////////////////////////////////////////////////////////////////
//////// DECLARACIÓN COMPONENTES JButton de colores
        //declaración array de objetos JButton para almacenar los JButton corresppondientes
        //a las opciones de color
        JButton [] botonesSonidos=new JButton[7];
        String [] literales={"DO","RE","MI","FA","SOL","LA","SI"};
        Color [] colores={Color.RED,Color.GREEN,Color.BLUE,Color.CYAN,Color.MAGENTA,Color.YELLOW,Color.PINK};
        //bucle iterativo de declaración de las componentes JButton
        for (int indice=0;indice<botonesSonidos.length;indice++) {
            botonesSonidos[indice] = new JButton(literales[indice]);
            //asignación tipografia a la componente JButton botonesSonidos[indice]
            botonesSonidos[indice].setFont(new Font("arial", Font.BOLD, 20));
            //asignación color de trazado componente JButton botonesSonidos[indice]
            botonesSonidos[indice].setForeground(Color.BLACK);
            //asignación color de fondo componente JButton botonesSonidos[indice]
            botonesSonidos[indice].setBackground(colores[indice]);
            //manipulador de evento asociado a la componente 
            //JButton botonesSonidos[indice]
            botonesSonidos[indice].addActionListener(gestorEventos);
            //inclusión de la componente JButton rojo en el contenedor JPanel
            //panelColores
            panelBotones.add(botonesSonidos[indice]);  
        }

      
        ////////////////////////////////////////////////////////////////////////
        //              PANEL panelSalir y COMPONENTE                         //
        //////////////////////////////////////////////////////////////////////// 
        
////////DECLARACIÓN CONTENEDOR JPanel panelSalir
        
//////////DECLARACIÓN contenedor JPanel para colocar la componente JButton salir
        JPanel panelSalir = new JPanel();

        //asignación administrador GridLayout 
        panelSalir.setLayout(new  GridLayout());

////////COMPONENTE JButton salir
        JButton salir = new JButton("SALIR");
        //asignación tipografia a la componente JButton salir
        salir.setFont(new Font("arial", Font.BOLD, 18));
        //asignación color de trazado componente JButton salir
        salir.setForeground(Color.WHITE);
        //asignación color de fondo componente JButton salir
        salir.setBackground(Color.BLACK);
        //manipulador de evento asociado a la componente 
        //JButton salir
        salir.addActionListener(gestorEventos);
        //inclusión de la componente JButton salir en el contenedor JPanel
        //panelVarios
        panelSalir.add(salir);  


 
////////////////////////////////////////////////////////////////////////////////
//   DISTRIBUCIÓN PANELES Y COMPONENTES EN EL CONTENEDOR JFrame ventana       //
//////////////////////////////////////////////////////////////////////////////// 
        
////////INCLUSIÓN DEL CONTENEDOR JPanel panelBotones EN LA ZONA OESTE DEL BorderLayout 
////////EN EL PANEL DE CONTENIDOS DEL CONTENEDOR JFrame
        panelContenidos.add(panelBotones, BorderLayout.NORTH);     

////////INCLUSIÓN DEL CONTENEDOR JPanel panelSalir EN LA ZONA SUR DEL BorderLayout 
////////EN EL PANEL DE CONTENIDOS DEL CONTENEDOR JFrame
        panelContenidos.add(panelSalir, BorderLayout.CENTER);        
        
        
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
    private void reproduccionSonido(String sonido) {
        Clip clip=null;
        AudioInputStream audioInputStream=null;
      try {
            audioInputStream = AudioSystem.getAudioInputStream(new File("sonidos/"+sonido).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start(); 
      }catch (UnsupportedAudioFileException | LineUnavailableException | IOException error) {
          System.err.println("ERROR: PROBLEMAS CON LA REPRODUCCIÓN SONIDO");
      }
    }
}
