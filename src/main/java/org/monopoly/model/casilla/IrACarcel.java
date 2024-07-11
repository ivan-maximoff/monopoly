package org.monopoly.model.casilla;

import org.monopoly.model.AdmJugador;
import org.monopoly.model.Config;
import org.monopoly.model.Jugador;

public class IrACarcel implements Casilla{

    private final Config.TiposCasillas tipo;
    private final Casilla casillaCarcel;

    public IrACarcel(Config.TiposCasillas tipo, Casilla carcel){
        this.casillaCarcel = carcel;
        this.tipo = tipo;
    }

    public Config.TiposCasillas getTipo() {
        return this.tipo;
    }

    public void accionar(AdmJugador administrador, Jugador jugador) {
        jugador.mover(this.casillaCarcel);
        administrador.encarcelar(jugador);
    }
}
