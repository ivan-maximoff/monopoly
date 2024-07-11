package org.monopoly.model;


import org.monopoly.model.casilla.Casilla;
import org.monopoly.model.casilla.Comprable;
import org.monopoly.model.casilla.Construible;
import java.util.*;

public class Juego {
    private final List<Jugador> jugadores;
    private final Tablero tablero;
    private final AdmTurnos admTurnos;
    private final AdmMovimientos admMovimientos;
    private final AdmJugador admJugador;
    private Jugador ganador;

    public Juego( Tablero tablero, List<Jugador> jugadores, Map<Config.ColoresComprables, List<Comprable>> tablaBarrios) {
        this.jugadores = jugadores;
        this.tablero = tablero;
        this.admTurnos = new AdmTurnos(this.jugadores);
        this.admMovimientos = new AdmMovimientos(this.tablero, Config.CantDados);
        this.admJugador = new AdmJugador(this.jugadores, tablaBarrios);
    }

    public void siguienteTurno(){this.admTurnos.siguiente();}

    public void tirarDados(){this.admMovimientos.tirarDados();}

    public void pagarFianza(){this.admJugador.pagarFianza(this.admTurnos.getJugadorActual());}

    public void mover() {
        Jugador jugador = this.admTurnos.getJugadorActual();
        if (!this.validarEncarcelamiento(jugador)) {
            this.actualizarSentencia(jugador);
            return;
        }
        if (this.admMovimientos.mover(jugador)) this.pagarPasoSalida(jugador);
        this.activarPasiva();
    }

    public void activarPasiva(){
        Jugador jugador = this.admTurnos.getJugadorActual();
        this.casillaActual().accionar(this.admJugador, jugador);
    }


    public void comprar(Comprable comprable){
        this.admJugador.comprar(this.admTurnos.getJugadorActual(), comprable);
    }

    public void construirConstruccion(Construible lugar){
        this.admJugador.construirConstruccion(this.admTurnos.getJugadorActual(),lugar);
    }
    public void venderConstruccion(Construible lugar){
        this.admJugador.venderConstruccion(this.admTurnos.getJugadorActual(), lugar);
    }
    public void hipotecar(Comprable compra){
        this.admJugador.hipotecar(this.admTurnos.getJugadorActual(), compra);
    }

    public void deshipotecar(Comprable compra){
        this.admJugador.deshipotecar(this.admTurnos.getJugadorActual(),compra);
    }
    public boolean terminado(){
        return this.unicoEnJuego() || this.hayGanador();
    }

    public Jugador getJugadorActual(){return this.admTurnos.getJugadorActual();}

    public List<Jugador> getJugadores(){return this.jugadores;}

    public RegistroComprables getRegistroComprables(){return this.admJugador.getRegistroComprables();}

    public boolean alcanzaDinero(int cantidad){
        return this.admJugador.alcanzaDinero(this.getJugadorActual(),cantidad);
    }

    public void entrarEnQuiebra(){this.admJugador.entrarEnQuiebra(this.getJugadorActual());}

    private boolean validarEncarcelamiento(Jugador jugador){
        if (jugador.getEstado() == Config.EstadosJugadores.EN_JUEGO){return true;}
        if (jugador.getTurnosCarcel() == 0 || this.admMovimientos.sonDadosIguales()){
            this.admJugador.liberar(jugador);
            this.admMovimientos.tirarDados();
            return true;
        }
        return false;
    }

    private void actualizarSentencia(Jugador jugador){
        jugador.setTurnosCarcel(jugador.getTurnosCarcel()-1);
    }

    private boolean unicoEnJuego(){
        int enJuego = 0;
        Jugador posibleGanador = null;
        for (Jugador jugador: this.jugadores){
            if (jugador.getEstado() != Config.EstadosJugadores.QUEBRADO) {
                enJuego++;
                if (enJuego > 1){return false;}
                posibleGanador = jugador;
            }
        }
        this.ganador = posibleGanador;
        return true;
    }

    private boolean hayGanador(){
        if (this.admJugador.hayGanador()){
            this.ganador = this.admJugador.ganador();
            return true;
        }
        return false;
    }

    private Casilla casillaActual(){
        return this.getJugadorActual().getCasillaActual();
    }
    public Jugador ganador(){
        return this.ganador;
    }

    private void pagarPasoSalida(Jugador jugador){ this.admJugador.otorgarDinero(jugador,Config.PagoPorSalida);}

    public AdmJugador getAdmJugador() {
        return admJugador;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public AdmMovimientos getAdmMovimientos() {
        return admMovimientos;
    }
}
