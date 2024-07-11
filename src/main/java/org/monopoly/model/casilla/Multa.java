package org.monopoly.model.casilla;

import org.monopoly.model.AdmJugador;
import org.monopoly.model.Config;
import org.monopoly.model.Jugador;

public class Multa extends Monetaria {
    public Multa(Config.TiposCasillas tipo, int monto){
        super(tipo, monto);
    }

    public void accionar(AdmJugador administrador, Jugador jugador) {
        if (administrador.alcanzaDinero(jugador, getMonto())){
            administrador.quitarDinero(jugador, this.getMonto());
            administrador.salirDeCrisis(jugador);
        }
        else administrador.entrarEnCrisis(jugador);

    }
}
