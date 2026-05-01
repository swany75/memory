/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memory;

/**
 *
 * @author Juan
 */

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class StatusBar extends JPanel {
    
    private Color BG_COLOR   = new Color(180, 220, 180);
    private Color TEXT_COLOR = new Color(30, 80, 30);
    
    // Bruce Springsteen Phrases
    private static final String[] PHRASES = {
        "Is there anybody alive out there?",
        "Are you ready?",
        "Are you really ready?",
        "Are yoy really really ready?",
        "Are you ready to have a good time?",
        "One, two, three, four!",
        "Come on!",
        "Turn it up!",
        "Let me hear you!",
        "Tonight we ride!",
        "No retreat, no surrender!",
        "Is everybody ready?"
    };
    
    // Nice Moves (Success)
    private static final String[] NICE_MOVE_PHRASES = {
        "Amazing! You have the memory of an elephant!",
        "Boom! Match found!",
        "Excellent move!",
        "You're on a roll!",
        "Masterfully done!",
        "What an eye you have!",
        "Perfect match!",
        "That's the one! Keep it up.",
        "Spot on!",
        "You're a Memory master!",
        "Pure magic!",
        "Nothing escapes you!",
        "Impressive work!",
        "Great find!",
        "You're killing it!"
    };

    // Bad Moves (Try again)
    private static final String[] BAD_MOVE_PHRASES = {
        "Oh, so close! Try again.",
        "Mmm... not that one.",
        "Don't give up, you're close!",
        "Almost... your memory just missed it!",
        "Give it another shot!",
        "Keep looking!",
        "Uf, I could've sworn it was there...",
        "You almost had it!",
        "Cold, cold!",
        "No worries, the next one is yours!",
        "Stay focused!",
        "On the bright side, now you know where they AREN'T!",
        "Keep calm and play on!",
        "Close, but no cigar!",
        "Hang in there, you got this!"
    };
    
    private static final Random RANDOM = new Random();
    
    private JLabel label;
    
    public StatusBar() {
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        setOpaque(true);

        label = new JLabel(getRandomText());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Monospaced", Font.BOLD, 14));
        label.setForeground(TEXT_COLOR);

        add(label, BorderLayout.CENTER);
    }
    
    public void clearText() {
        label.setText("");
    }
    
    public void setText(String text) {
        label.setText(text);
    }
    
    private String getRandomText() {
        return PHRASES[RANDOM.nextInt(PHRASES.length)];
        // return  "DEFAULT TEXT";
    }
    
    public void setDefaultText() {
        label.setText(getRandomText());
    }
    
    public void setRandomNiceMovePhrase() {
        String phrase = NICE_MOVE_PHRASES[RANDOM.nextInt(NICE_MOVE_PHRASES.length)];
        label.setText(phrase);
    }
    
    public void setRandomBadMovePhrase() {
        String phrase = BAD_MOVE_PHRASES[RANDOM.nextInt(BAD_MOVE_PHRASES.length)];
        label.setText(phrase);
    }
    
}