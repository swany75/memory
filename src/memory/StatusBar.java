/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memory;

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
    
    // Rock n roll & Nu-Metal Phrases
    private static final String[] PHRASES = {
        "Is there anybody alive out there?",
        "Are you ready?",
        "Are you really ready?",
        "Are you really really ready?",
        "Are you ready to have a good time?",
        "One, two, three, four!",
        "Come on!",
        "Turn it up!",
        "Let me hear you!",
        "Tonight we ride!",
        "No retreat, no surrender!",
        "Is everybody ready?",
        "Take me down to the paradise city",
        "Hello, Cleveland!",
        "For those about to rock, we salute you!",
        "I can't hear you!",
        "Rock and roll will never die!",
        "Scream for me!",
        "Let's see some hands in the air!",
        "Welcome to the jungle!",
        "Alright partner, keep on rollin' baby!",
        "Ladies and gentlemen... introducing!",
        "Get up, come on, get down with the sickness!",
        "Cut my life into pieces, this is my last resort!",
        "Wake up! Grab a brush and put a little makeup!",
        "Let the bodies hit the... FLOOOOOOR!",
        "Crawling in my skin, these wounds they will not heal!",
        "I've given up! On infectious energy!",
        "Bring me to life! (Wake me up inside)",
        "Du... Du hast... Du hast mich!",
        "Welcome to the Black Parade!",
        "Whiskey in the jar-o!",
        "We will, we will, rock you!",
        "But in the end, it doesn't even matter!",
        "Oh shit, here we go again",
        "It's about reality and making some noise",
        "Final lap I'm on top of the world",
        "Do you think you can survive the top?",
        "One two three, four five, Six Seven...",
        "I am the storm that is approaching",
        "Bury the light deep within",
        "You cannot kill me... I am Omega",
        "You cannot kill me... I am Subhuman",
        "Bang, bang, bang, pull my Devil Trigger",
        "All of these thoughts runnin' through my head",
        "Devil's never cry",
        "I've finally found what I was looking for",
        "Rules of Nature!"
    };
    
    // Nice Moves
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
        "You're killing it!",
        "Mi Bombo!",
        "Bomboclat! Rich, Millonare!",
        "Let him cook! I repeat, LET. HIM. COOK.",
        "Sheesh! That memory is built different!",
        "Bro is playing in 4K. Absolute gigachad energy.",
        "Big brain move right there!",
        "Outstanding move! (Insert Chess Meme Here)",
        "We are so back!",
        "Is it possible to learn this power?",
        "Simple history, absolute cinema.",
        "W memory. No cap, pure rizz.",
        "They're beginning to believe...",
        "Calculated. Just like you planned.",
        "This is ten percent luck. Twenty percent skill..."
    };

    // Bad Moves
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
        "Hang in there, you got this!",
        "WASTED",
        "It's over. We're cooked.",
        "Emotional damage!",
        "Bruh. That wasn't it.",
        "Mission failed, we'll get 'em next time.",
        "My disappointment is immeasurable, and my day is ruined.",
        "Wait, that's illegal.",
        "Instructions unclear, matched the wrong cards.",
        "So that was a lie.",
        "Press F to pay respects to that attempt.",
        "Not like this... NOT LIKE THIS!",
        "Error 404: Match not found."
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