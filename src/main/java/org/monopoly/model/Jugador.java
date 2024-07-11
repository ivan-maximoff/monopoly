package org.monopoly.model;

import org.monopoly.model.casilla.Casilla;

import java.util.*;

public class Jugador {
    private final Config.ColoresJugadores color;
    private Casilla casillaActual;
    private Config.EstadosJugadores estado;
    private int turnosCarcel;

    public Jugador(Config.ColoresJugadores color, Casilla salida){
        this.color = color  ;
        this.casillaActual = salida;
        this.estado = Config.EstadosJugadores.EN_JUEGO;
        this.turnosCarcel = 0;
    }

    public void mover(Casilla nuevaCasilla){
        this.casillaActual = nuevaCasilla;
    }

    public void setTurnosCarcel(int turnosCarcel) { this.turnosCarcel = turnosCarcel; }

    public void setEstado(Config.EstadosJugadores estado) { this.estado = estado; }

    public Config.EstadosJugadores getEstado(){
        return this.estado;
    }

    public Casilla getCasillaActual(){
        return this.casillaActual;
    }

    public int getTurnosCarcel(){
        return this.turnosCarcel;
    }

    public Config.ColoresJugadores getColor() { return this.color; }

    public boolean estaSobreComprable(){
        return (this.casillaActual.getTipo() == Config.TiposCasillas.PROPIEDAD ||
                this.casillaActual.getTipo() == Config.TiposCasillas.ESTACION);
    }


    @Override
    public int hashCode() {
        return Objects.hash(this.color);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Jugador otroJugador = (Jugador) obj;
        return Objects.equals(this.color, otroJugador.color);
    }
}
