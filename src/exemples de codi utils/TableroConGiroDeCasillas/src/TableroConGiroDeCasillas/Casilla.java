/*
CLASE QUE LLEVA A CABO EL GIRO DE UNA IMAGEN VISUALIZANDO EL CAMBIO DE SU ANVERSO 
A SU REVERSO Y VICEVERSA.

PROGRAMACIÓN II - CURSO 2025_2026
JUAN MONTES DE OCA
UNIVERSITAT DE LES ILLES BALEARS
 */
package TableroConGiroDeCasillas;

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
    private boolean casillaGirada=false;
    
    //MÉTODO CONSTRUCTOR
    public Casilla(String imagenAnverso, String imagenReverso) {
        //ASIGNACIÓN A LOS ATRIBUTOS anverso Y REVERSO DE LAS IMÁGENES ANVERSO
        //Y REVERSOS CORRESPONDIENTE AL OBJETO Casilla
        System.out.println(imagenAnverso+"   "+imagenReverso);
        anverso = new ImageIcon(getClass().getResource(imagenAnverso)).getImage();
        reverso = new ImageIcon(getClass().getResource(imagenReverso)).getImage();

        //DIMENSIÓN ÓTPTIMA DEL OBJETO Casilla
        setPreferredSize(new Dimension(200, 200));

        //ASIGNACIÓN GESTOR DE EVENTOS CORRESPONDIENTE AL CLICAR CUALQUIER BOTÓN
        //DEL RATÓN SOBRE EL OBJETO Casilla
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //VOLTEAR LAS IMÁGENES ASOCIADAS AL OBJETO Casilla
                giro();
            }
        });
    }
    
    //MÉTODO QUE LLEVA A CABO EL TRATAMIENTO DEL VOLTEO DE LAS IMÁGENES ASOCIADAS
    //AL OBJETO Casilla
    public void giro() {
        casillaGirada=!casillaGirada;
        repaint();
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        //BORRADO DEL CONTENIDO ANTERIOR DEL COMPONENTE
        super.paintComponent(g);
        //INSTANCIA OBJETO Graphics2D
        Graphics2D g2 = (Graphics2D) g;

        //VERFICACIÓN DEL ESTADO (CASILLA YA ACERTADA O CASILLA GIRADA) DE LA 
        //CASILLA CUYA IMAGEN VA A SER VISUALIZADA
        if (casillaGirada) {
            //VISUALIZACIÓN DE LA IMAGEN ANVERSO CORRESPONDIENTE A LA CASILLA
            g2.drawImage(anverso, 0, 0, getWidth(), getHeight(), this);
        }
        else {
            //VISUALIZACIÓN DE LA IMAGEN REVERSO CORERSPONDIENTE A LA CASILLA
            g2.drawImage(reverso, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
