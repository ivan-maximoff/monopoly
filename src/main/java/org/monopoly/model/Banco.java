package org.monopoly.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Banco {
    private final Map<Jugador, CuentaBancaria> cuentasJugadores;

    public Banco(List<Jugador> jugadores){
        this.cuentasJugadores = new HashMap<>();
        for (Jugador j : jugadores){
            this.cuentasJugadores.put(j, new CuentaBancaria(Config.DineroInicial));
        }
    }
    public void otorgarDinero(Jugador jugador, int monto){
        this.cuentasJugadores.get(jugador).sumarDinero(monto);
    }

    public void quitarDinero(Jugador jugador, int monto){
        CuentaBancaria cuenta = this.cuentasJugadores.get(jugador);
        cuenta.retirarDinero(monto);
    }

    public void transferir(Jugador receptor, Jugador emisor, int monto){
        if (this.cuentasJugadores.get(emisor).poseeDinero(monto)) {
            this.quitarDinero(emisor, monto);
            this.otorgarDinero(receptor, monto);
        }
    }

    public int consultarDinero(Jugador jugador){
        return this.cuentasJugadores.get(jugador).getDinero();
    }

    public CuentaBancaria getCuentaJugador(Jugador jugador) {
        return this.cuentasJugadores.get(jugador);
    }
}
