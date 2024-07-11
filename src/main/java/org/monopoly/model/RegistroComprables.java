package org.monopoly.model;
import org.monopoly.model.casilla.Comprable;
import org.monopoly.model.casilla.Propiedad;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class RegistroComprables {
    private final Map<Comprable, Jugador> tablaPropiedades;
    private final Map<Config.ColoresComprables, List<Comprable>> barrios;
    private final Map<Config.ColoresComprables, Integer> tablaColores;
    private final Map<Jugador, Map<Config.ColoresComprables, Integer>> tablaColoresJugadores;

    public RegistroComprables(Map<Config.ColoresComprables, List<Comprable>> tablaBarrios, List<Jugador> jugadores) {
        this.tablaPropiedades = new HashMap<>();
        this.barrios = tablaBarrios;

        this.tablaColores = new HashMap<>();
        tablaBarrios.forEach((clave, valor) -> this.tablaColores.put(clave, valor.size()));

        this.tablaColoresJugadores = new HashMap<>();
        for (Jugador jugador : jugadores) {
            Map<Config.ColoresComprables, Integer> tablaJugador = new HashMap<>();
            this.tablaColoresJugadores.put(jugador, tablaJugador);
        }
    }

    private void actualizarcantidad(Map<Config.ColoresComprables, Integer> tabla, Config.ColoresComprables color) {
        if (tabla.containsKey(color)) {
            int nueva_cant = tabla.get(color) + 1;
            tabla.put(color, nueva_cant);
        } else {
            tabla.put(color, 1);
        }
    }

    public void registrarCompra(Comprable comprable, Jugador jugador) {
        this.tablaPropiedades.put(comprable, jugador);
        Config.ColoresComprables color = comprable.getColor();
        Map<Config.ColoresComprables, Integer> tablaJugador = this.tablaColoresJugadores.get(jugador);
        actualizarcantidad(tablaJugador, color);
    }

    public boolean tieneDuenio(Comprable comprable) {
        return this.tablaPropiedades.containsKey(comprable);
    }

    public Jugador obtenerDuenio(Comprable comprable) {
        return (this.tieneDuenio(comprable)) ? this.tablaPropiedades.get(comprable) : null;
    }

    public int obtenerCantSet(Jugador jugador, Config.ColoresComprables color) {
        return this.tablaColoresJugadores.get(jugador).getOrDefault(color, 0);
    }

    public boolean poseeSetCompleto(Jugador jugador, Config.ColoresComprables color) {
        return this.obtenerCantSet(jugador, color) == this.tablaColores.get(color);
    }

    public HashMap<Propiedad, Integer> casasPorBarrio(Config.ColoresComprables color) {
        HashMap<Propiedad, Integer> tabla = new HashMap<>();
        for (Comprable comprable : this.barrios.get(color)) {
            if (comprable.getTipo() == Config.TiposCasillas.PROPIEDAD) {
                Propiedad propiedad = (Propiedad) comprable;
                tabla.put(propiedad, propiedad.getCantConstruidos());
            }
        }
        return tabla;
    }

    public Map<Comprable, Jugador> getTablaPropiedades() {
        return tablaPropiedades;
    }

    public void muerteJugador(Jugador jugador) {
        List<Comprable> comprablesABorrar = new ArrayList<>();
        for(Comprable comprable : this.tablaPropiedades.keySet()) {
            if (this.tablaPropiedades.get(comprable) == jugador) {
                if (comprable.getTipo() == Config.TiposCasillas.PROPIEDAD) {
                    Propiedad propiedad = (Propiedad) comprable;
                    propiedad.demolerConstrucciones();
                }
                comprablesABorrar.add(comprable);
            }
        }
        for (Comprable comprable : comprablesABorrar) {
            this.tablaPropiedades.remove(comprable);
        }
    }
}