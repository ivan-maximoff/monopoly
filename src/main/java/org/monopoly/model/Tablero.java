package org.monopoly.model;
import org.monopoly.model.casilla.Casilla;

import java.util.List;

public class Tablero {
    private final List<Casilla> casillas;

    public Tablero(List<Casilla> lista){
        this.casillas = lista;
    }

    public List<Casilla> getCasillas(){
        return this.casillas;
    }

    public Casilla casillaSiguiente(Casilla casillaActual, int avances){
        int index = (this.casillas.indexOf(casillaActual) + avances )% this.casillas.size();
        return this.casillas.get(index);
    }

    public boolean pasoPorSalida(Casilla casillaActual, int avances){
        return this.casillas.indexOf(casillaActual) + avances >= this.casillas.size();
    }
}
