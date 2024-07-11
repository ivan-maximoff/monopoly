package org.monopoly.model;

import org.monopoly.model.casilla.Casilla;
import java.util.concurrent.ThreadLocalRandom;

public class AdmMovimientos {
    private final Tablero tablero;
    private final int[] dados;

    public AdmMovimientos(Tablero tablero, int cantDados){
        this.tablero = tablero;
        this.dados = new int[cantDados];
    }

    public void tirarDados(){
        for(int i=0;i < dados.length; i++){this.dados[i]= ThreadLocalRandom.current().nextInt(1, 6 + 1);}
    }

    public int[] getDados(){return dados;}
    public boolean sonDadosIguales(){
        int valor=dados[0];
        for(int dado: dados){if (dado!=valor){return false;}}
        return true;
    }

    public boolean mover(Jugador jugador){
        Casilla casilla = jugador.getCasillaActual();
        int pasos = this.sumarDados(dados);
        jugador.mover(tablero.casillaSiguiente(casilla,pasos));
        return tablero.pasoPorSalida(casilla,pasos);
    }

    private int sumarDados(int[] dados) {
        int suma = 0;
        for (int dado : dados) {suma += dado;}
        return suma;
    }
}

