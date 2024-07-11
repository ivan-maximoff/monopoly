package org.monopoly.model.casilla;

import org.monopoly.model.AdmJugador;
import org.monopoly.model.Config;
import org.monopoly.model.Jugador;

public class Loteria extends Monetaria{
    public Loteria(Config.TiposCasillas tipo, int monto){
        super(tipo, monto);
    }

    public void accionar(AdmJugador administrador, Jugador jugador) {
        administrador.otorgarDinero(jugador, this.getMonto());
    }
}
