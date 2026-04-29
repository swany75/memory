/*
CLASE QUE LLEVA A CABO EL GIRO DE UNA IMAGEN VISUALIZANDO EL CAMBIO DE SU ANVERSO 
A SU REVERSO Y VICEVERSA.

PROGRAMACIÓN II - CURSO 2025_2026
JUAN MONTES DE OCA
UNIVERSITAT DE LES ILLES BALEARS
 */
package TableroConGiroAnimadoDeCasillas;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Casilla extends JPanel {
    //DECLARACIONES ATRIBUTOS
    private Image anverso;
    private Image reverso;
    //DECLARACIÓN VARIABLE BOOLEANA PARA CONTROLAR EL CAMBIO DE DIRECCIÓN DEL
    //VOLTEO DE LAS IMÁGENES ASOCIADAS A UN OBJETO Casilla
    private boolean direccionGiro = false;
    //DECLARACIÓN VARIABLE ENTERA PARA REPRESENTAR LA ESCALA A APLICAR A LAS
    //IMÁGENES CON EL OBJETIVO DE SIMULAR LOS PROCESO DE CONTRACCIÓN Y EXTENSIÓN
    //DE LAS MISMAS
    private double escala = 1.0;
    //DECLARACIÓN OBJETO Timer PARA CONTROLAR EL TIEMPO DE CADA UNO DE LOS
    //VOLTEOS
    private Timer temporizador;
    //DECLARACIÓN VARIABLE BOOLEANA PARA CONTROLAR EL CAMBIO DE LA CONTRACCIÓN
    //A LA EXTENSIÓN DEL PROCESO DE VOLTEO
    private boolean faseGiro;
    
    //MÉTODO CONSTRUCTOR
    public Casilla(String imagenAnverso, String imagenReverso) {
        //ASIGNACIÓN A LOS ATRIBUTOS anverso Y REVERSO DE LAS IMÁGENES ANVERSO
        //Y REVERSOS CORRESPONDIENTE AL OBJETO Casilla
        anverso = new ImageIcon(getClass().getResource(imagenAnverso)).getImage();
        reverso = new ImageIcon(getClass().getResource(imagenReverso)).getImage();

        //DIMENSIÓN ÓTPTIMA DEL OBJETO Casilla
        setPreferredSize(new Dimension(200, 200));

        //ASIGNACIÓN GESTOR DE EVENTOS CORRESPONDIENTE AL CLICAR CUALQUIER BOTÓN
        //DEL RATÓN SOBRE EL OBJETO Casilla
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //GIRAR LAS IMÁGENES ASOCIADAS AL OBJETO Casilla
                giro();
            }
        });
    }
    
    //MÉTODO QUE LLEVA A CABO EL TRATAMIENTO DEL VOLTEO PARA OBTENER LAS
    //DIFERENTES ESCALAS A APLICAR A LAS IMÁGENES ANVERSO Y REVERSO A TRAVÉS DEL TIEMPO 
    //CONTROLADO POR UN TEMPORIZADOR OBJETO Timer. LA SIMULACIÓN DEL GIRO SE LLEVA
    //A CABO A TRAVÉS DE UN PROCESO ITERATIVO DE CONTRACCIÓN Y EXTENSIÓN DE LAS
    //IMÁGENES ASOCIADAS AL OBJETO Casilla
    private void giro() {
        //DECLARACIONES
        //DECLARACIÓN VARIABLE BOOLEANA PARA CONTROLAR LA DIRECCIÓN DEL GIRO ENTRE
        //LAS DOS POSIBLES DIRECCIÓNES: DE LA IMAGEN ANVERSO A LA IMAGEN REVERSO O VICEVERSA
        faseGiro = true;

        //ACCIONES
        //INSTANCIACIÓN TEMPORIZADOR OBJETO Timer QUE DENINIRÁ EL TIEMPO EN EL QUE
        //SE LLEVA A CABO EL GIRO DE LAS IMÁGENES ASOCIADAS AL OBJETO Casilla
        temporizador = new Timer(15,null);
        //ASIGNACIÓN AL OBJETO 
        temporizador.addActionListener(evento -> {
            if (faseGiro) {
                //CONTRACCIÓN
                escala=escala-0.1;
                if (escala<=0.0) {
                    escala=0.0;
                    //INVERSIÓN DIRECCIÓN
                    direccionGiro=!direccionGiro;
                    //CAMBIO DE CONTRACCIÓN A EXTENSIÓN
                    faseGiro = false;
                }
            } else {
                //EXTENSIÓN
                escala=escala+0.1;
                if (escala>=1.0) {
                    escala=1.0;
                    //PARAR EL TEMPORIZADOR Y POR LO TANTO SE DA POR FINALIZADO 
                    //EL GIRO
                    temporizador.stop();
                }
            }
            //REFRESCO DIBUJANDO EL OBJETO Casilla PARA VISUALIZAR LA FASE
            //DEL GIRO
            repaint();
        });

        //INICIAR EL TEMPORIZADOR PONIENDO EN MARCHA EL PROCESO DE GIRO
        temporizador.start();
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        //DECLARACIÓN OBJETO Image
        Image image;
        //BORRADO DEL CONTENIDO ANTERIOR DEL COMPONENTE
        super.paintComponent(g);
        //INSTANCIA OBJETO Graphics2D
        Graphics2D g2 = (Graphics2D) g;

        //CÁLCULO DE LAS COORDENADAS A UTILIZAR EN LA VISUALIZACIÓN DE LA IMAGEN
        //EN FUNCIÓN DE LA ESCALA DE LA FASE ACTUAL DEL PROCESO DE GIRO
        int coord_X_Final= (int)(getWidth()*escala);
        int coord_Y_Final= (int)getHeight();
        int coord_X_Inicial=(int)(getWidth()-coord_X_Final)/2;
        int coord_Y_Inicial=0;
        
        //SELECCIONAR LA IMAGEN A VISUALIZAR EN FUNCIÓN DE LA DIRECCIÓN ACTUAL 
        //DEL GIRO
        if (direccionGiro) {
            image=anverso;
        }
        else {
            image=reverso;
        }
        //VISUALIZACIÓN DE LA MIMAGEN EN EL OBJETO Casilla EN LAS COORDENADAS 
        //CORRESPONDIENTES A LA FASE DEL GIRO
        g2.drawImage(image, coord_X_Inicial, coord_Y_Inicial, coord_X_Final, coord_Y_Final, this);
    }
}
