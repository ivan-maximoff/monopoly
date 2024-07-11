package org.monopoly.model.casilla;
import org.monopoly.model.AdmJugador;
import org.monopoly.model.Config;
import org.monopoly.model.Jugador;

public interface Casilla {
    Config.TiposCasillas getTipo();
    void accionar(AdmJugador administrador, Jugador jugador);

}
