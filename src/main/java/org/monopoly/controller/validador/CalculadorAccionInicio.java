package org.monopoly.controller.validador;

import org.monopoly.controller.accion.Accion;
import org.monopoly.controller.accion.AccionPagarFianza;
import org.monopoly.controller.accion.AccionTirarDados;
import org.monopoly.model.Config;
import org.monopoly.model.Juego;
import org.monopoly.model.Jugador;
import java.util.ArrayList;
import java.util.List;

public class CalculadorAccionInicio implements CalculadoraDeAcciones {

    private final Juego juego;

    public CalculadorAccionInicio(Juego juego){
        this.juego = juego;
    }

    public List<Accion> accionesPosibles(Jugador jugador) {
        List<Accion> acciones = new ArrayList<>();
        acciones.add( new AccionTirarDados(this.juego));
        if (jugador.getEstado() == Config.EstadosJugadores.PRESO && this.juego.alcanzaDinero(Config.ValorFianza) && jugador.getTurnosCarcel() != 0)
            acciones.add( new AccionPagarFianza(this.juego));
        return acciones;
    }
}
