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

public class Partida implements Serializable {
    
    // Definición de enums necesarios dentro de la clase
    public enum GameState { WIN, LOSE, INTERRUPTED }
    public enum Dificulty { EASY, NORMAL, HARD }
    
    private String playerName; 
    private Dificulty dificultat;
    private int totalParelles;
    private int parellesEncertades;
    private GameState estat;
    private int points;
    private int duracio;
    
    // Constructor corregido y limpio
    public Partida(String playerName, Dificulty dificultat, int totalParelles) {
        this.playerName = playerName; 
        this.dificultat = dificultat;
        this.totalParelles = totalParelles;
        this.parellesEncertades = 0;
        this.estat = GameState.INTERRUPTED;
        this.points = 0;
        this.duracio = 0;
    }

    // --- GETTERS & SETTERS (Necesarios para interactuar con Player y Main) ---

    public String getPlayerName() {
        return playerName;
    }

    public Dificulty getDificultat() {
        return dificultat;
    }

    public int getTotalParelles() {
        return totalParelles;
    }

    public int getParellesEncertades() {
        return parellesEncertades;
    }

    public void setParellesEncertades(int parellesEncertades) {
        this.parellesEncertades = parellesEncertades;
    }

    public GameState getEstat() {
        return estat;
    }

    public void setEstat(GameState estat) {
        this.estat = estat;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getDuracio() {
        return duracio;
    }

    public void setDuracio(int duracio) {
        this.duracio = duracio;
    }

    // Método toString() adaptado perfectamente al formato CSV (log de una sola línea)
    @Override
    public String toString() {
        return playerName + "," + 
               dificultat + "," + 
               parellesEncertades + "," + 
               totalParelles + "," + 
               estat + "," + 
               points + "," + 
               duracio;
    }
}