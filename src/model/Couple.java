/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 * 
 * Se que no es correcto llamar Couple a una pareja de cartas
 * Pero de esta forma tengo la clase Card y Couple una al lado de la otra
 *
 */

public class Couple {
    
    private Card cardA;
    private Card cardB;
    private boolean isMatched;

    public Couple(String frontImage) {
        this.cardA = new Card(frontImage);
        this.cardB = new Card(frontImage);
        this.isMatched = false;
    }

    public boolean isCouple(Card c1, Card c2) {
        return c1.getFrontImage().equals(c2.getFrontImage());
    }

    public Card getCardA() { return cardA; }
    public Card getCardB() { return cardB; }

    public boolean isMatched() { return isMatched; }
    public void setMatched(boolean isMatched) { this.isMatched = isMatched; }
    
}
