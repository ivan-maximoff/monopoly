package org.monopoly.model;

import java.util.*;

public class AdmTurnos {
    private final List<Jugador> jugadores;
    private int turno;

    public AdmTurnos(List<Jugador> jugadores){
        this.turno = 0;
        Collections.shuffle(jugadores);
        this.jugadores = jugadores;
    }

    public void siguiente(){
        int iJugador = this.sgteListaCircular();
        this.turno = iJugador;
        while (jugadores.get(iJugador).getEstado() == Config.EstadosJugadores.QUEBRADO){
            iJugador = this.sgteListaCircular();
            this.turno = iJugador;
        }
    }

    public Jugador getJugadorActual(){return this.jugadores.get(this.turno);}

    private int sgteListaCircular(){
        if (this.turno < this.jugadores.size() - 1 ){return this.turno + 1;}
        return 0;
    }
}

