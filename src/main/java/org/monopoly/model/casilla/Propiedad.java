package org.monopoly.model.casilla;

import org.monopoly.model.AdmJugador;
import org.monopoly.model.Config;
import org.monopoly.model.Jugador;

import java.util.List;

public class Propiedad extends Comprable implements Construible{
    private final List<Construccion> construcciones;
    private int cantConstruidos;
    private final int valorConstruir;
    private final int valorDestruir;
    private int valorTotalConstruidos;

    public Propiedad(Config.TiposCasillas tipo, String nombre, int valorCompra, int valorRentaBasica, Config.ColoresComprables color, List<Construccion> listaConstruccion, int valorConstruir){
        super(tipo, nombre, valorCompra, valorRentaBasica, color);
        this.valorConstruir = valorConstruir;
        this.valorDestruir = this.valorConstruir / 2;
        this.construcciones = listaConstruccion;
    }
    public void accionar(AdmJugador admJugador, Jugador jugador) {
        if (admJugador.obtenerDuenio(this) == null || this.estaHipotecada){
            return;
        }
        int renta = this.calcularAlquiler();
        if (!admJugador.alcanzaDinero(jugador, renta)) {
            admJugador.entrarEnCrisis(jugador);
            return;
        }
        admJugador.salirDeCrisis(jugador);
        admJugador.transferir(admJugador.obtenerDuenio(this), jugador, renta);
    }

    protected int calcularAlquiler(){
        return this.valorRentaBasica + this.valorTotalConstruidos;
    }

    public int getValorConstruir() {
        return this.valorConstruir;
    }

    public int getValorDestruir() {
        return this.valorDestruir;
    }

    public int getCantConstruidos() {
        return this.cantConstruidos;
    }

    public void construir(){
        int valorAlquiler = this.construcciones.get(cantConstruidos).getValorAlquiler();
        this.valorTotalConstruidos += valorAlquiler;
        this.cantConstruidos++;
    }

    public void destruir(){
        int valorAlquiler = this.construcciones.get(cantConstruidos).getValorAlquiler();
        this.valorTotalConstruidos -= valorAlquiler;
        this.cantConstruidos--;
    }

    public void demolerConstrucciones(){
        while (this.cantConstruidos > 0){
            this.destruir();
        }
    }

    @Override
    public int getValorAlquiler(){
        return this.valorRentaBasica + this.valorTotalConstruidos;
    }
}
