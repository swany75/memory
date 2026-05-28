/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.components;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class StatusBar extends JPanel {
    
    private Color BG_COLOR   = new Color(180, 220, 180);
    private Color TEXT_COLOR = new Color(30, 80, 30);
    
    private static final Random RANDOM = new Random();
    
    private JLabel label;

    private static final String[] PHRASES = {
        "Are you ready?",
        "Are you really ready?",
        "Are you really really ready?",
        "One, two, three, four!",
        "Come on!",
        "Turn it up!",
        "Let me hear you!",
        "Tonight we ride!",
        "No retreat, no surrender!",
        "Is everybody ready?",
        "Take me down to the paradise city",
        "For those about to rock, we salute you!",
        "I can't hear you!",
        "Rock and roll will never die!",
        "Let's see some hands in the air!",
        "Welcome to the jungle!",
        "Alright partner, keep on rollin' baby!",
        "Ladies and gentlemen... introducing!",
        "Get up, come on, get down with the sickness!",
        "Cut my life into pieces, this is my last resort!",
        "Wake up! Grab a brush and put a little makeup!",
        "Let the bodies hit the... FLOOOOOOR!",
        "Bring me to life! (Wake me up inside)",
        "Du... Du hast... Du hast mich!",
        "We will, we will, rock you!",
        "Oh sh*t, here we go again",
        "Star Platinum: Za Warudo!",
        "Do you think you can survive the top?",
        "This is ten percent luck. Twenty percent skill...",
        "Here I am! Rock you like a hurricane!",
        "Disturbing the peace. Look into my eyes",
    };
    
    // Nice Moves
    private static final String[] NICE_MOVE_PHRASES = {
        "Boom! Match found!",
        "Excellent move!",
        "You're on a roll!",
        "Masterfully done!",
        "What an eye you have!",
        "Perfect match!",
        "That's the one! Keep it up.",
        "Spot on!",
        "Pure magic!",
        "Nothing escapes you!",
        "Impressive work!",
        "Great find!",
        "You're killing it!",
        "Let him cook!",
        "I said, LET. HIM. COOK.",
        "Sheesh! That memory is built different!",
        "Bro is playing in 4K",
        "Big brain move right there!",
        "Outstanding move!",
        "We are so back!",
        "Is it possible to learn this power?",
        "Simple history, absolute cinema.",
        "W memory. No cap, pure rizz.",
        "They're beginning to believe...",
        "Calculated. Just like you planned."
    };

    // Bad Moves
    private static final String[] BAD_MOVE_PHRASES = {
        "Almost... your memory just missed it!",
        "Uf, I could've sworn it was there...",
        "No worries, the next one is yours!",
        "On the bright side, now you know where they AREN'T!",
        "WASTED",
        "It's over. We're cooked.",
        "Emotional damage!",
        "Bruh. That wasn't it.",
        "Mission failed, we'll get 'em next time.",
        "My disappointment is immeasurable, and my day is ruined.",
        "Wait, that's illegal.",
        "Instructions unclear, matched the wrong cards.",
        "Press F to pay respects to that attempt.",
        "Not like this... NOT LIKE THIS!",
        "Error 404: Match not found."
    };

    /**
     * Crea la barra de estado con el estilo base y la etiqueta central.
     */
    public StatusBar() {
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        setOpaque(true);

        label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Monospaced", Font.BOLD, 14));
        label.setForeground(TEXT_COLOR);

        add(label, BorderLayout.CENTER);
    }
    
    /**
     * Limpia el texto actual de la barra de estado.
     */
    public void clearText() {
        label.setText("");
    }
    
    /**
     * Establece un texto específico en la barra de estado.
     *
     * @param text texto a mostrar
     */
    public void setText(String text) {
        label.setText(text);
    }
    
    /**
     * Devuelve una frase aleatoria de la lista general.
     *
     * @return frase aleatoria
     */
    private String getRandomText() {
        return PHRASES[RANDOM.nextInt(PHRASES.length)];
        // return  "DEFAULT TEXT";
    }
    
    /**
     * Muestra una frase genérica aleatoria en la barra de estado.
     */
    public void setDefaultText() {
        label.setText(getRandomText());
    }
    
    /**
     * Muestra una frase aleatoria de acierto.
     */
    public void setRandomNiceMovePhrase() {
        String phrase = NICE_MOVE_PHRASES[RANDOM.nextInt(NICE_MOVE_PHRASES.length)];
        label.setText(phrase);
    }
    
    /**
     * Muestra una frase aleatoria de fallo.
     */
    public void setRandomBadMovePhrase() {
        String phrase = BAD_MOVE_PHRASES[RANDOM.nextInt(BAD_MOVE_PHRASES.length)];
        label.setText(phrase);
    }
    
}