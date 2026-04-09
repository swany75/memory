           /*
FUNCIONALIDAD:
EJEMPLO DE ENTRADA DE DATOS A TRAVÉS DE LA CLASE LTGraficos

OBJETIVO: introducción programación gráfica

realización: Juan Montes de Oca
 */

package lecturaLTGraficos;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class ejemploLecturaDatosJConLTGraficos {
    JFrame ventana;
    //declaración método main
    public static void main(String[] args) { 
        new ejemploLecturaDatosJConLTGraficos().metodoPrincipal();
    }
    
    //declaración método metodoPrncipal
    public void metodoPrincipal() {  
        //DECLARACIÓN Y DEFINICIÓN CONTENEDOR JFrame
        ventana = new JFrame("ENTRADA DE DATOS");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(400, 200);

        //DECLARACIÓN PANEL JContentPane DEL CONTENEDOR JFrame ventana
        Container panelContenidos=ventana.getContentPane(); 
        panelContenidos.setLayout(new GridLayout(3,1));

        //DECLARACIÓN COMPONENTES JButton
        JButton boton1=new JButton("NOMBRE y DIRECCIÓN");
        //asignación maniuplador de eventos
        boton1.addActionListener(new manipuladorEventos());
        JButton boton2=new JButton("INTRODUCIR DOS VALORES REALES");
        boton2.addActionListener(new manipuladorEventos());
        JButton boton3=new JButton("INTRODUCIR 12 VALORES ENTEROS");
        boton3.addActionListener(new manipuladorEventos());
        
        //introducción contenedor JPanel panel en el panel de contenidos 
        //del contenedor JFrame
        panelContenidos.add(boton1);
        panelContenidos.add(boton2);
        panelContenidos.add(boton3);

        //activar el cierre interactivo del contenedor ventana para finalizar
        //ejecución
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //activar visualización contenedor ventana
        ventana.setVisible(true);
    }

////////MANIPULADOR DE EVENTOS manipuladorEventosGeneral
    private class manipuladorEventos implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evento)  { 
            switch (evento.getActionCommand()) {
                case "NOMBRE y DIRECCIÓN"      :     
                                        String [] literalesIntroduccion1={"NOMBRE","DIRECCIÓN"};
                                        literalesIntroduccion1=new LTGraficos(ventana,literalesIntroduccion1).getDatosTexto();
                                        if (literalesIntroduccion1!=null) {
                                            for (int i=0;i<literalesIntroduccion1.length;i++) {
                                                System.out.println(literalesIntroduccion1[i]);      
                                            }
                                                                                  
                                        }

                                        break;
                case "INTRODUCIR DOS VALORES REALES"   :   
                                        String [] literalesIntroduccion2={"INTRODUCE UN VALOR REAL",
                                                                          "INTRODUCE UN VALOR REAL"};
                                        literalesIntroduccion2=new LTGraficos(ventana,literalesIntroduccion2).getDatosTexto();
                                        if (literalesIntroduccion2!=null) {
                                            for (int indice=0;indice<literalesIntroduccion2.length;indice++) {
                                                if (compatibleNumero(literalesIntroduccion2[indice])){
                                                    System.out.println("VALOR REAL INTRODUCIDO:"+Double.parseDouble(literalesIntroduccion2[indice]));
                                                }  
                                                else {
                                                    System.out.println("VALOR "+(indice+1)+" INTRODUCIDO NO ES UN NÚNMERO");
                                                }
                                            }                                             
                                        }

                                        break;
                case "INTRODUCIR 12 VALORES ENTEROS"   :       
                                        String [] literalesIntroduccion3=new String[12];
                                        for (int i=0;i<12;i++) { 
                                           literalesIntroduccion3[i]="INTRODUCIR VALOR "+(i+1);
                                        }
                                                                          
                                        literalesIntroduccion3=new LTGraficos(ventana,literalesIntroduccion3,false).getDatosTexto();
                                        if (literalesIntroduccion3!=null) {
                                            for (int indice=0;indice<literalesIntroduccion3.length;indice++) {
                                                if (!literalesIntroduccion3[indice].equals("")) {
                                                    if (compatibleNumero(literalesIntroduccion3[indice])){
                                                        System.out.println("VALOR REAL INTRODUCIDO:"+Integer.parseInt(literalesIntroduccion3[indice]));
                                                    }  
                                                    else {
                                                        System.out.println("VALOR "+(indice+1)+" INTRODUCIDO NO ES UN NÚMERO");
                                                    }
                                                }  
                                            }
                                        }
                                        break;

            }        
    }};  
    
    private boolean compatibleNumero(String dato) {
        for (int indice=0;indice<dato.length();indice++) {
            if (!(((dato.charAt(indice)>='0')&&(dato.charAt(indice)<='9'))||(dato.charAt(indice)=='.'))) {
                return false;
            }
        }
        return true;
    }
}
