package org.monopoly.controller.validador;

import org.monopoly.controller.accion.*;
import org.monopoly.model.Config;
import org.monopoly.model.Juego;
import org.monopoly.model.Jugador;
import org.monopoly.model.RegistroComprables;
import org.monopoly.model.casilla.Casilla;
import org.monopoly.model.casilla.Comprable;
import org.monopoly.model.casilla.Construible;
import org.monopoly.model.casilla.Propiedad;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import static org.monopoly.model.Config.TiposCasillas.PROPIEDAD;

public class CalculadoraAccionesCasilla implements CalculadoraDeAcciones {
    private final Juego juego;
    private final RegistroComprables registro;

    public CalculadoraAccionesCasilla(Juego juego) {
        this.juego = juego;
        this.registro = juego.getRegistroComprables();
    }

    public List<Accion> accionesPosibles(Jugador jugador) {
        List<Accion> acciones = new ArrayList<>();

        List<Casilla> opcionesComprar = this.opcionesComprar(jugador);
        if (!opcionesComprar.isEmpty()){acciones.add(new AccionComprar(this.juego, opcionesComprar));}

        List<Casilla> opcionesConstruirEnPropiedad = this.opcionesConstruirEnPropiedad(jugador);
        if (!opcionesConstruirEnPropiedad.isEmpty()){acciones.add(new AccionConstruir(this.juego, opcionesConstruirEnPropiedad));}

        List<Casilla> opcionesVentaConstruccion = this.opcionesVentaConstruccion(jugador);
        if (!opcionesVentaConstruccion.isEmpty()){acciones.add(new AccionVender(this.juego, opcionesVentaConstruccion));}

        List<Casilla> opcionesHipoteca = this.opcionesHipoteca(jugador);
        if (!opcionesHipoteca.isEmpty()){acciones.add(new AccionHipotecar(this.juego, opcionesHipoteca));}

        List<Casilla> opcionesDeshipoteca = this.opcionesDeshipoteca(jugador);
        if (!opcionesDeshipoteca.isEmpty()){acciones.add(new AccionDeshipotecar(this.juego, opcionesDeshipoteca));}

        return acciones;
    }


    //CALCULADORAS DE ACCIONES

    private List<Casilla> opcionesHipoteca(Jugador jugador) {
        List<Casilla> opcionesFiltradas = new ArrayList<>();
        for (Comprable comprable : this.comprables(jugador)) {
            if (!comprable.estaHipotecada() && sinConstrucciones(comprable)){
                opcionesFiltradas.add(comprable);
            }
        }
        return opcionesFiltradas;
    }

    private boolean sinConstrucciones(Casilla casilla){
        if (casilla.getTipo() == PROPIEDAD){
            Construible casteada = (Construible) casilla;
            return casteada.getCantConstruidos() == 0;
        }
        return true;
    }

    private List<Casilla> opcionesDeshipoteca(Jugador jugador) {
        List<Casilla> opcionesFiltradas = new ArrayList<>();
        for (Comprable comprable : this.comprables(jugador)) {
            if (this.juego.alcanzaDinero(comprable.getValorHipoteca())
            && comprable.estaHipotecada()) {
                opcionesFiltradas.add(comprable);
            }
        }
        return opcionesFiltradas;
    }


    private List<Casilla> opcionesComprar(Jugador jugador) {
        if (jugador.estaSobreComprable()){
            Comprable comprable = (Comprable) jugador.getCasillaActual();
            if (!this.registro.tieneDuenio(comprable) && this.juego.alcanzaDinero(comprable.getValorCompra())){ return new ArrayList<>() {{add(comprable);}}; }
        }
        return new ArrayList<>();
    }

    private List<Casilla> opcionesConstruirEnPropiedad(Jugador jugador) {
        List<Casilla> opcionesFiltradas = new ArrayList<>();
        for (Config.ColoresComprables color : Config.ColoresComprables.values()) { //Nos fijamos Barrio por Barrio
            if (this.registro.poseeSetCompleto(jugador, color)                     //Si tiene todo el Barrio
                    && barrioSinHipotecar(this.registro.casasPorBarrio(color).keySet())) {//No tiene ninguna hipoteca en el Barrio
                opcionesFiltradas.addAll(this.mantieneDiferenciaConst(color, Config.DiferenciaContrucciones));//Se filtra las que cumplirian con la regla diferencia de 1
            }
        }
        for (int i = 0 ; i < opcionesFiltradas.size(); i++){
            Propiedad propiedad = (Propiedad) opcionesFiltradas.get(i);
            if (!this.juego.alcanzaDinero(propiedad.getValorConstruir())){
                opcionesFiltradas.remove(propiedad);
            }
        }
        return opcionesFiltradas;
    }

    private List<Casilla> opcionesVentaConstruccion(Jugador jugador){
        List<Casilla> opcionesFiltradas = new ArrayList<>();
        for (Config.ColoresComprables color : Config.ColoresComprables.values()) {
            if (this.registro.poseeSetCompleto(jugador, color)
                    && barrioSinHipotecar(this.registro.casasPorBarrio(color).keySet())) {
                opcionesFiltradas.addAll(this.mantieneDiferenciaVenta(color, Config.DiferenciaContrucciones));
            }
        }
        for (int i = 0; i < opcionesFiltradas.size(); i++){
            Propiedad propiedad = (Propiedad) opcionesFiltradas.get(i);
            if (!this.juego.alcanzaDinero(propiedad.getValorConstruir())
            || !(propiedad.getCantConstruidos()>0)){
                opcionesFiltradas.remove(propiedad);
            }
        }
        return opcionesFiltradas;
    }

    private List<Comprable> comprables(Jugador jugadorActual) {
        List<Comprable> comprables = new ArrayList<>();
        Map<Comprable, Jugador> comprablesJugadores = this.registro.getTablaPropiedades();
        comprablesJugadores.forEach((comprable, jugador) -> {
            if (jugador == jugadorActual) {
                comprables.add(comprable);
            }
        });
        return comprables;
    }
    private List<Casilla> mantieneDiferenciaConst(Config.ColoresComprables color, int diferencia) {
        AtomicInteger min = new AtomicInteger(Integer.MAX_VALUE);
        this.registro.casasPorBarrio(color).forEach((comprable, cant) -> {
            if (cant< min.get()) {
                min.set(cant);
            }
        });
        List<Casilla> mantienenDif = new ArrayList<>();
        this.registro.casasPorBarrio(color).forEach((comprable, cant) -> {
            if (cant+1-min.get()<=diferencia){
                mantienenDif.add(comprable);
            }
        });
        return mantienenDif;
    }

    private boolean barrioSinHipotecar(Set<Propiedad> propiedades){
        for (Propiedad propiedad: propiedades){
            if (propiedad.estaHipotecada()){
                return false;
            }
        }
        return true;
    }

    private List<Casilla> mantieneDiferenciaVenta(Config.ColoresComprables color, int diferencia) {
        AtomicInteger max = new AtomicInteger(0);
        this.registro.casasPorBarrio(color).forEach((comprable, cant) -> {
            if (cant > max.get()) {
                max.set(cant);
            }
        });
        List<Casilla> mantienenDif = new ArrayList<>();
        this.registro.casasPorBarrio(color).forEach((comprable, cant) -> {
            if (max.get()-(cant-1) <= diferencia){
                mantienenDif.add(comprable);
            }
        });
        return mantienenDif;
    }
}

