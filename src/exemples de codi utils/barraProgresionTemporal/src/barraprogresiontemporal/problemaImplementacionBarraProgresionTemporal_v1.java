/*
EJEMPLO IMPLEMENTACIÓN BARRA DE PROGRESIÓN JProgressBar
 */
package barraprogresiontemporal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.Timer;

public class problemaImplementacionBarraProgresionTemporal_v1 {
    //DECLARACIÓN ATRIBUTOS
    private JProgressBar barraTemporal;
    private Timer cronometro;
    private JButton boton;
    private int velocidad=200;
    
    //declaración método main
    public static void main(String[] args) {
        new problemaImplementacionBarraProgresionTemporal_v1().metodoPrincipal();
    }
    
    //declaración método metodoPrincipal
    public  void metodoPrincipal() {
        //DECLARACIONES
        //DECLARACIONES VARIABLES ENTERAS PARA ALMACENAR EL VALOR MÍNIMO Y MÁXIMO
        //DE LA BARRA DE PROGRESIÓN
        int valorMinimo=0;
        int valorMaximo=100;
////////////////////////////////////////////////////////////////////////////////
//                      DECLARACIÓN CONTENEDOR                                //
//////////////////////////////////////////////////////////////////////////////// 
/////////////            DECLARACIÓN CONTENEDOR JFrame          ////////////////
        //CREACION DEL CONTENEDOR JFrame ventana
        JFrame ventana=new JFrame("BARRA DESPLAZAMIENTO EN PROGRESIÓN");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
/////////////     PANEL DE CONTENIDOS DEL CONTENEDOR JFrame     ////////////////         
        //DECLARACIÓN PANEL DE CONTENIDOS JContentPane DEL CONTENEDOR JFrame 
        //ventana
        Container panelContenidos=ventana.getContentPane();
 
////////////////////////////////////////////////////////////////////////////////
//                     DECLARACIÓN GESTORES DE EVENTOS                        //
//////////////////////////////////////////////////////////////////////////////// 
/////////////    DECLARACIÓN GESTOR DE EVENTOS ActionListener   ////////////////
        ActionListener gestorEvento = new ActionListener() { 
            //TRATAMIENTO EVENTO
            @Override
            public void actionPerformed(ActionEvent evento) {
                //VERIFICAR SI LA COMPONENTE EN LA QUE HA SUCEDIDO EL EVENTO
                //ES LA COMPONENTE JButton boton
                if(evento.getSource()==boton) {
                    //INICIAR EL TEMPORIZADOR cronometro
                    cronometro.start();
                }

                //VERIFICAR SI LA COMPONENTE EN LA QUE HA SUCEDIDO EL EVENTO
                //ES EL TEMPORIZADOR Timer cronometro
                if(evento.getSource()==cronometro)
                {
                    //VERIFICACIÓN SI EL VALOR ACTUAL DE LA JProgressBar barraTemporal
                    //HA LLEGADO AL VALOR MÁXIMO ESTIPULADO
                    int valor=barraTemporal.getValue();
                    if(valor<valorMaximo)
                    {
                        //ASIGNAR A JProgressBar barraTemporal SU VALOR INCREMENTADO
                        //EN UNA UNIDAD
                        barraTemporal.setValue(++valor);
                    }
                    else
                    {
                        //DETENER EL TEMPORIZADOR cronometro
                        cronometro.stop();
                        //CERRAR JFrame ventana LIBERANDO TODOS LOS RECURSOS
                        //UTILIZADOS
                        ventana.dispose();
                    }
                }
            }
        };
        
////////////////////////////////////////////////////////////////////////////////
//                      DECLARACIÓN COMPONENTES                               //
////////////////////////////////////////////////////////////////////////////////
//////////        DECLARACIONES Y DEFINICIONES JProgressBar        /////////////
        barraTemporal=new JProgressBar();    
        //ASIGNACIÓN VALORES MÍNIMO Y MÁXIMO A LA JProgressBar barraTemporal
        barraTemporal.setMinimum(valorMinimo);
        barraTemporal.setMaximum(valorMaximo);
        //ASIGNACIÓN VALOR INICIAL A LA JProgressBar barraTemporal
        barraTemporal.setValue(0);
        //ACTIVACIÓN VISUALIZACIÓN VALOR EN LA JProgressBar barraTemporal
        barraTemporal.setStringPainted(true);
        //DIMENSIONAMIENTO JProgressBar barraTemporal
        barraTemporal.setPreferredSize(new Dimension(500,25));
        //ASIGNACIÓN COLORES DE FONDO Y TRAZADO JProgressBar barraTemporal
        barraTemporal.setForeground(Color.RED);
        barraTemporal.setBackground(Color.YELLOW);
        //INTRODUCCIÓN JProgressBar DENTRO DEL PANEL DE CONTENIDOS panelContenidos
        //EN EL AREA CENTRO DEL ADMINISTRADOR BorderLayout
        panelContenidos.add(barraTemporal,BorderLayout.CENTER);
        
        
//////////        DECLARACIONES Y DEFINICIONES JButton boton       /////////////
        boton=new JButton("ACTIVAR PROGRESIÓN");
        //ASIGNACIÓN GESTOR DE EVENTOS A LA COMPONENTE JButton boton
        boton.addActionListener(gestorEvento);
        //INTRODUCCIÓN COMPONENTE JButton DENTRO DEL PANEL DE CONTENIDOS panelContenidos
        //EN EL AREA NORTE DEL ADMINISTRADOR BorderLayout
        panelContenidos.add(boton,BorderLayout.NORTH);


////////////////////////////////////////////////////////////////////////////////
//                      DECLARACIÓN TEMPORIZADOR                              //
////////////////////////////////////////////////////////////////////////////////
//////////DECLARACIÓN TEMPORIZADOR cronometro CON UN INTERVALO DE 100 MILISEGUNDOS 
        //Y ASOCIADO AL ActionListener gestorEvento
        cronometro=new Timer(velocidad,gestorEvento);  
        
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


