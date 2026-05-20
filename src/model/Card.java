/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class Card {
    private String frontImage;
    private static final String BACK_IMAGE = "media/images/backImage1.png";
    private boolean isFlipped;

    public Card(String frontImage) {
        this.frontImage = frontImage;
        this.isFlipped = false;
    }

    public String getCurrentImage() {
        return isFlipped ? frontImage : BACK_IMAGE;
    }

    public void flip() {
        isFlipped = !isFlipped;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public String getBackImage() {
        return BACK_IMAGE;
    }
    
    public String getFrontImage() {
        return frontImage;
    }
}
