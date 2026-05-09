/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import memory.Partida;

/**
 * @author Marti Figuls Nolla
 * @author Juan Dalmau Santandreu
 */

public class Player implements Serializable {
    // Definimos el ID de serialización para asegurar la compatibilidad del archivo binario
    private static final long serialVersionUID = 1L;
    
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
        // Bucle for-each más limpio y eficiente
        for (Partida p : partides) {
            total += p.getPoints();
        }
        return total;
    }
}