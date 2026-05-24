/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import core.game.Partida;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class Player implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String name;
    private Partida[] partides;
    private int numPartides;
    private static final int INCREMENT = 10;
    
    public Player(String nom) {
        this.name = nom;
        this.partides = new Partida[INCREMENT];
        this.numPartides = 0;
    }

    public String getName() {
        return name;
    }

    public Partida[] getPartides() {
        Partida[] result = new Partida[numPartides];
        for (int i = 0; i < numPartides; i++) {
            result[i] = partides[i];
        }
        return result;
    }

    public int getNumPartides() {
        return numPartides;
    }

    public void addGame(Partida game) {
        if (numPartides == partides.length) {
            Partida[] nouArray = new Partida[partides.length + INCREMENT];
            for (int i = 0; i < numPartides; i++) {
                nouArray[i] = partides[i];
            }
            partides = nouArray;
        }
        partides[numPartides] = game;
        numPartides++;
    }

    public int getTotalPoints() {
        int total = 0;
        for (int i = 0; i < numPartides; i++) {
            total += partides[i].getPoints();
        }
        return total;
    }
}