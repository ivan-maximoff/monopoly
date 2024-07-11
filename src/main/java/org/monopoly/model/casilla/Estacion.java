package org.monopoly.model.casilla;

import org.monopoly.model.AdmJugador;
import org.monopoly.model.Config;
import org.monopoly.model.Jugador;

public class Estacion extends Comprable {
    public Estacion(Config.TiposCasillas tipo, String nombre, int valorCompra, int valorRentaBasica, Config.ColoresComprables color){
        super(tipo, nombre, valorCompra, valorRentaBasica, color);
    }
    public void accionar(AdmJugador admJugador, Jugador jugador) {
        if (admJugador.obtenerDuenio(this) == null || this.estaHipotecada || admJugador.obtenerCantSet(jugador, this.color) > 0){
            return;
        }
        int renta = this.calcularAlquiler(admJugador, jugador);
        if (!admJugador.alcanzaDinero(jugador, renta)) {
            admJugador.entrarEnCrisis(jugador);
            return;
        }
        admJugador.salirDeCrisis(jugador);
        admJugador.transferir(admJugador.obtenerDuenio(this), jugador, renta);
    }
    protected int calcularAlquiler(AdmJugador admJugador, Jugador jugador){
        return this.valorRentaBasica * admJugador.obtenerCantSet(admJugador.obtenerDuenio(this), this.color);
    }
}