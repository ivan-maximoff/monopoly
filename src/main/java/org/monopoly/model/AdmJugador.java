package org.monopoly.model;

import org.monopoly.model.casilla.Casilla;
import org.monopoly.model.casilla.Comprable;
import org.monopoly.model.casilla.Construible;
import org.monopoly.model.casilla.Propiedad;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdmJugador {
    private final List<Jugador> jugadores;
    private final Banco banco;
    private final RegistroComprables registroComprables;
    private Jugador ganador;

    public AdmJugador(List<Jugador> jugadores, Map<Config.ColoresComprables, List<Comprable> > tablaBarrios){
        this.jugadores = jugadores;
        this.banco = new Banco(this.jugadores);
        this.registroComprables = new RegistroComprables(tablaBarrios, this.jugadores);
    }

    public void comprar(Jugador jugador, Comprable comprable) {
        this.banco.quitarDinero(jugador, comprable.getValorCompra());
        this.registroComprables.registrarCompra(comprable, jugador);
    }

    public void construirConstruccion(Jugador jugador, Construible construible) {
        this.banco.quitarDinero(jugador, construible.getValorConstruir());
        construible.construir();
    }

    public void venderConstruccion(Jugador jugador, Construible construible) {
        this.banco.otorgarDinero(jugador, construible.getValorDestruir());
        construible.destruir();
    }

    public void hipotecar(Jugador jugador, Comprable comprable) {
        this.banco.otorgarDinero(jugador, comprable.getValorHipoteca());
        comprable.hipotecar();
    }

    public void deshipotecar(Jugador jugador, Comprable comprable) {
        this.banco.quitarDinero(jugador, comprable.getValorHipoteca());
        comprable.deshipotecar();
    }

    public void encarcelar(Jugador jugador) {
        jugador.setTurnosCarcel(Config.TurnosCarcel);
        jugador.setEstado(Config.EstadosJugadores.PRESO);
    }

    public void liberar(Jugador jugador) {
        jugador.setTurnosCarcel(0);
        jugador.setEstado(Config.EstadosJugadores.EN_JUEGO);
    }

    public void entrarEnQuiebra(Jugador jugador) {
        jugador.setEstado(Config.EstadosJugadores.QUEBRADO);
        this.banco.quitarDinero(jugador, this.banco.consultarDinero(jugador));
        this.registroComprables.muerteJugador(jugador);
    }

    public void entrarEnCrisis(Jugador jugador) {
        jugador.setEstado(Config.EstadosJugadores.CRISIS);
    }
    public void salirDeCrisis(Jugador jugador) {
        jugador.setEstado(Config.EstadosJugadores.EN_JUEGO);
    }

    public void pagarFianza(Jugador jugador) {
        this.banco.quitarDinero(jugador, Config.ValorFianza);
        this.liberar(jugador);
    }

    public void otorgarDinero(Jugador jugador, int monto) {
        this.banco.otorgarDinero(jugador, monto);
    }

    public void quitarDinero(Jugador jugador, int monto) {
        this.banco.quitarDinero(jugador, monto);
    }

    public void transferir(Jugador receptor, Jugador emisor, int monto) {
        this.banco.transferir(receptor, emisor, monto);
    }

    public boolean alcanzaDinero(Jugador jugador, int cantidad){
        return this.banco.consultarDinero(jugador) > cantidad;
    }

    public int obtenerCantSet(Jugador jugador, Config.ColoresComprables color) {
        return this.registroComprables.obtenerCantSet(jugador, color);
    }

    public Jugador obtenerDuenio(Comprable comprable) {
        return this.registroComprables.obtenerDuenio(comprable);
    }

    public RegistroComprables getRegistroComprables(){
        return this.registroComprables;
    }

    private Map<Comprable, Jugador> getTablaPropiedades(){
        return this.registroComprables.getTablaPropiedades();
    }

    private Map<Jugador, List<Integer>> construcciones(){
        HashMap<Jugador, List<Integer>> construcciones = new HashMap<>();
        for (Jugador jugador: this.jugadores){
            construcciones.put(jugador, new ArrayList<>());
        }
        Map<Comprable, Jugador> propiedadesJugador = this.getTablaPropiedades();
        for(Propiedad propiedad : Config.ListaPropiedades){
            if (propiedadesJugador.containsKey(propiedad)){
                Jugador duenio = propiedadesJugador.get(propiedad);
                construcciones.get(duenio).add(propiedad.getCantConstruidos());
            }
        }
        return construcciones;
    }

    private boolean condicionConstrucciones(List<Integer> construccionesJugador){
        if (construccionesJugador.isEmpty()){
            return false;
        }
        for (Integer cantidad : construccionesJugador){
            if (cantidad < Config.MaxConstrucciones){
                return false;
            }
        }
        return true;
    }

    public boolean hayGanador(){
        Map<Jugador, List<Integer>> construcciones = this.construcciones();
        for (Jugador jugador: this.jugadores){
            List<Integer> construccionesJugador = construcciones.get(jugador);
            if (condicionConstrucciones(construccionesJugador)){
                this.ganador = jugador;
                return true;
            }
        }
        return false;
    }

    protected Jugador ganador(){
        return this.ganador;
    }

    public Banco getBanco() {
        return banco;
    }
}

