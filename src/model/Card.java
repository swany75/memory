/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import core.settings.GameSettings;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class Card {
    private String frontImage;
    private boolean isFlipped;

    /**
     * Crea una carta con su imagen frontal y la deja boca abajo.
     *
     * @param frontImage ruta de la imagen frontal
     */
    public Card(String frontImage) {
        this.frontImage = frontImage;
        this.isFlipped = false;
    }

    /**
     * Invierte el estado de la carta (boca arriba/boca abajo).
     */
    public void flip() {
        isFlipped = !isFlipped;
    }

    /**
     * Indica si la carta está boca arriba.
     *
     * @return {@code true} si está volteada
     */
    public boolean isFlipped() {
        return isFlipped;
    }

    /**
     * Devuelve la ruta de la imagen de reverso configurada.
     *
     * @return ruta de la imagen de reverso
     */
    public String getBackImage() {
        return GameSettings.getBackImagePath();
    }
    
    /**
     * Devuelve la ruta de la imagen frontal de la carta.
     *
     * @return ruta de la imagen frontal
     */
    public String getFrontImage() {
        return frontImage;
    }
}
