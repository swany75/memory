/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import config.GameSettings;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class Card {
    private String frontImage;
    private boolean isFlipped;

    public Card(String frontImage) {
        this.frontImage = frontImage;
        this.isFlipped = false;
    }

    public String getCurrentImage() {
        return isFlipped ? frontImage : getBackImage();
    }

    public void flip() {
        isFlipped = !isFlipped;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public String getBackImage() {
        return GameSettings.getBackImagePath();
    }
    
    public String getFrontImage() {
        return frontImage;
    }
}
