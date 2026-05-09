/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memory;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class ButtonBuilder {

    private static final Color TEXT_COLOR = Color.WHITE;
    
    
    // Botón simple para la barra de botones superior 
    // Sin texto y con escalado
    public static JButton createButton(String iconPath, int w, int h, ActionListener action) {

        JButton button = new JButton();

        button.setIcon(ImageManager.loadScaledIcon(iconPath, w, h));

        button.addActionListener(action);

        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);

        return button;
    }   
       
    /**
     * Interfaz de usuario personalizada para botones que simula un botón físico (pulsador).
     * <p>
     * Dibuja un efecto de sombra en la parte inferior para dar sensación de relieve (3D). 
     * Cuando el botón es presionado, el contenido y el fondo se desplazan verticalmente 
     * hacia abajo para simular la pulsación física.
     * </p>
     * * <b>Créditos:</b>
     * Adaptado a partir de una solución de diseño de botones tipo CSS en Stack Overflow.
     * * @see <a href="https://stackoverflow.com/questions/23698092/design-button-in-java-like-in-css">Stack Overflow - Design Button in Java like in CSS</a>
     */
    
    public static JButton createPulsador(String texto, Color color, Font fuente) {
        JButton button = new JButton(texto);
        button.setFont(fuente);
        button.setForeground(TEXT_COLOR);
        button.setBackground(color);
        button.setFocusable(false);
        button.setUI(new PulsadorUI());
        return button;
    }

    static class PulsadorUI extends BasicButtonUI {

        /**
         * Inicializa la interfaz de usuario del botón.
         * Configura el botón como no opaco para permitir el renderizado de bordes redondeados
         * y le asigna un margen interno por defecto.
         * * @param c El componente {@link JComponent} (botón) donde se instala esta UI.
         */
        
        @Override
        public void installUI(JComponent c) {
            super.installUI(c);
            AbstractButton b = (AbstractButton) c;
            b.setOpaque(false);
            b.setBorder(new EmptyBorder(8, 20, 8, 20));
        }

        /**
         * Dibuja el contenido y gestiona el desplazamiento de la interfaz del botón.
         * Si el botón está presionado, traslada temporalmente el lienzo de dibujo 
         * hacia abajo en el eje Y para simular la pulsación antes de renderizar el texto o icono.
         * * @param g El contexto gráfico {@link Graphics} usado para pintar.
         * @param c El componente que se está pintando.
         */
        
        @Override
        public void paint(Graphics g, JComponent c) {
            AbstractButton b = (AbstractButton) c;
            boolean pressed = b.getModel().isPressed();
            paintBackground(g, b, pressed);
            if (pressed) g.translate(0, 3);
            super.paint(g, c);
            if (pressed) g.translate(0, -3);
        }

        /**
         * Pinta el fondo del botón con bordes redondeados y un efecto de sombra.
         * * <ul>
         * <li><b>Estado normal:</b> Dibuja la base tridimensional más oscura debajo y el botón en relieve arriba.</li>
         * <li><b>Estado presionado:</b> Dibuja el botón con un tono más oscuro y aplanado para simular hundimiento.</li>
         * </ul>
         * * @param g El contexto gráfico {@link Graphics} usado para pintar.
         * @param c El componente del cual se extraen las dimensiones y colores.
         * @param pressed {@code true} si el botón está activado/presionado actualmente; {@code false} en caso contrario.
         */
        
        private void paintBackground(Graphics g, JComponent c, boolean pressed) {
            AbstractButton b = (AbstractButton) c;
            Dimension size = c.getSize();
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color base   = c.getBackground();
            Color shadow = CustomColors.shadowColor(base);  // ← se deriva del color base
            Color hover  = CustomColors.hoverColor(base);   // ← se deriva del color base
            boolean rollover = b.getModel().isRollover();
            int sombra = 6;

            if (!pressed) {
                g2.setColor(shadow);
                g2.fillRoundRect(0, 0, size.width, size.height, 15, 15);
                g2.setColor(shadow);
                g2.fillRoundRect(0, sombra, size.width, size.height - sombra, 15, 15);

                g2.setColor(rollover ? hover : base);
                g2.fillRoundRect(0, 0, size.width, size.height - sombra, 15, 15);
            } else {
                g2.setColor(shadow);
                g2.fillRoundRect(0, 0, size.width, size.height, 15, 15);
                g2.setColor(shadow);
                g2.fillRoundRect(0, sombra, size.width, size.height - sombra, 15, 15);
            }
        }
        
        /* Diferencias entre el código de Stack Overflow y el nuestro:
         * * El botón original de Stack Overflow traslada y deforma visualmente todo 
         * el componente al hacer clic. En cambio, nosotros hemos modificado la lógica 
         * de renderizado en 'paintBackground' para que la base tridimensional (la sombra 
         * inferior de 6 píxeles) permanezca estática en su posición original, mientras 
         * que solo la cara superior del botón (el cuerpo principal) se desplaza hacia 
         * abajo en el eje Y. 
         * * Esto genera una sensación de "compresión" o "pulsación real", donde el botón 
         * parece hundirse físicamente dentro de su propio marco, en lugar de desplazarse 
         * por completo en la pantalla.
         */
    }
}
