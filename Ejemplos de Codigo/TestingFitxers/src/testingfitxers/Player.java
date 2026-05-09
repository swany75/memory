/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testingfitxers;

/**
 *
 * @author Juan
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
 
public class Player implements Serializable {
    
    private String name;
    private List<Partida> partides;
    
    public Player(String nom) {
        this.name = nom;
        this.partides = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public List<Partida> getPartides() {
        return partides;
    }
    
    public void addGame(Partida game) {
        partides.add(game);
    }
    
    public int getTotalPoints() {
        int total = 0;
        for (int i = 0; i < partides.size(); i++) {
            total += partides.get(i).getPoints();
        }
        return total;
    }
}