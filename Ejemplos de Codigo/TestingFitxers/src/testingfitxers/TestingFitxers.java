/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package testingfitxers;

/**
 *
 * @author Juan
 */
public class TestingFitxers {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Ejecuta el flujo principal de la aplicación
        (new TestingFitxers()).run();
    }
    
    private void run() {
        System.out.println("=== INICIANDO REGISTRO DE PARTIDAS ===\n");
        
        // 1. Crear el jugador
        Player jugador = new Player("Juan");
        
        // 2. Simular Partida 1: Dificultad Normal
        // El constructor ahora recibe el nombre del jugador, la dificultad y el total de parejas
        Partida partida1 = new Partida(jugador.getName(), Partida.Dificulty.NORMAL, 10);
        partida1.setParellesEncertades(10); // Encontró todas
        partida1.setPoints(150);            // Consiguió 150 puntos
        partida1.setDuracio(45);            // Tardó 45 segundos
        partida1.setEstat(Partida.GameState.WIN);
        
        // Guardamos la partida en la lista del jugador
        jugador.addGame(partida1);
        
        // Guardamos en el archivo log.dat (se llamará automáticamente al toString() en formato CSV)
        System.out.println(partida1.toString());
        
        
        // 3. Simular Partida 2: Dificultad Difícil
        Partida partida2 = new Partida(jugador.getName(), Partida.Dificulty.HARD, 15);
        partida2.setParellesEncertades(6);  // Solo encontró 6 parejas
        partida2.setPoints(30);             // Consiguió 30 puntos
        partida2.setDuracio(120);           // Se le acabó el tiempo (120 segundos)
        partida2.setEstat(Partida.GameState.LOSE);
        
        // Guardamos la partida en la lista del jugador
        jugador.addGame(partida2);
        
        // Guardamos en el archivo log.dat
        System.out.println(partida2.toString());
        
    }
}