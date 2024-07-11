package org.monopoly.model.casilla;

import org.monopoly.model.AdmJugador;
import org.monopoly.model.Config;
import org.monopoly.model.Jugador;

public class CasillaSinEfecto implements Casilla {
    private final Config.TiposCasillas tipo;

    public CasillaSinEfecto(Config.TiposCasillas tipo){
        this.tipo = tipo;
    }
    public Config.TiposCasillas getTipo(){
        return this.tipo;
    }

    public void accionar(AdmJugador administrador, Jugador jugador) {
    }
}
