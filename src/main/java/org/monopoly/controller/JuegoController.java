package org.monopoly.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.monopoly.controller.accion.*;
import org.monopoly.controller.validador.CalculadoraDeAcciones;
import org.monopoly.controller.validador.CalculadorAccionFinal;
import org.monopoly.controller.validador.CalculadorAccionInicio;
import org.monopoly.controller.validador.CalculadoraAccionesCasilla;
import org.monopoly.model.Config;
import org.monopoly.model.Juego;
import org.monopoly.model.Jugador;
import org.monopoly.model.casilla.Casilla;
import org.monopoly.model.casilla.Comprable;
import org.monopoly.view.BotonView;
import org.monopoly.view.JugadorView;
import org.monopoly.view.TableroView;

import java.awt.event.MouseEvent;
import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.List;

public class JuegoController {
    private Juego juego;
    private CalculadoraDeAcciones calculadoraInicio;

    private List<JugadorView> jugadorViews;

    private Stage stage;

    @FXML
    private VBox botonera;
    @FXML
    private VBox jugadores;

    @FXML
    private ImageView dado1;

    @FXML
    private ImageView dado2;

    @FXML
    private ImageView jugadorActual;

    @FXML
    private HBox contenedorJuego;

    @FXML
    private VBox contenedorCentral;

    private TableroView tableroView;

    private static final String ESTILO_BOTON = "-fx-min-width: 150; -fx-min-height: 50; -fx-border-radius: 25; -fx-background-radius: 25; -fx-background-color: #F8C471; -fx-border-color: #E59866; -fx-border-width: 3;";
    private static final String ESTILO_BOTON_HOVER = "-fx-min-width: 150; -fx-min-height: 50; -fx-border-radius: 25; -fx-background-radius: 25; -fx-background-color: #A04000; -fx-border-color: #E59866; -fx-border-width: 3;";

    private List<CalculadoraDeAcciones> calculadoras;

    public void setJuego(Juego juego, Stage stage){
        this.stage = stage;
        this.juego = juego;
        this.calculadoraInicio = new CalculadorAccionInicio(this.juego);
        this.calculadoras = new ArrayList<>(){{
            add(new CalculadoraAccionesCasilla(juego));
            add(new CalculadorAccionFinal(juego));
        }};

        List<String> nombres = new ArrayList<>(){{
            add("MATI");
            add("THIAGO");
            add("IVAN");
            add("ANDREA");
        }};

        this.jugadorViews = setJugadores(this.juego.getJugadores(), nombres);
        TableroView tableroView = new TableroView(juego.getTablero(), this.juego.getJugadores());
        this.tableroView = tableroView;
        contenedorCentral.getChildren().add(tableroView.getTablero());

        actualizarDatos();

        actualizarBotonesInicio();

        contenedorJuego.setOnMouseClicked(e -> {
            for (JugadorView jugadorView : jugadorViews) {
                jugadorView.cerrarPropiedades();
            }
        });
        this.stage.show();
    }

    public List<Accion> opcionesAcciones (Jugador jugador){
        List<Accion> acciones = new ArrayList<>();

        for (CalculadoraDeAcciones calc : this.calculadoras){acciones.addAll(calc.accionesPosibles(jugador));}

        return acciones;
    }

    public void actualizarDatos(){
        actualizarJugadorActual();
        actualizarDados();
        actualizarJugadores();
        tableroView.actualizarTablero(this.juego.getJugadores());
        if (this.juego.terminado()) this.terminarJuego();
    }
    public void actualizarBotonesAccion(){
        setBotonesAccion(this.opcionesAcciones(this.juego.getJugadorActual()));
    }
    public void actualizarBotonesInicio(){
        setBotonesAccion(this.calculadoraInicio.accionesPosibles(this.juego.getJugadorActual()));
    }

    public void setBotonesAccion(List<Accion> listaAccion){
        for (int i = 0; i < listaAccion.size() ; i++){
            Accion accion = listaAccion.get(i);

            Button nuevo;
            if (accion.getEtapa() == Config.EtapaAcciones.CASILLA) {
                AccionCasilla accionCasilla = (AccionCasilla) accion;
                nuevo = BotonView.crearBoton(accion.getNombre(), ESTILO_BOTON, ESTILO_BOTON_HOVER, e -> {
                    botonera.getChildren().clear();
                    setBotonesCasillas(accionCasilla.getOpciones(), accionCasilla);
                    if (this.juego.getJugadorActual().getEstado() == Config.EstadosJugadores.CRISIS) this.juego.activarPasiva();
                });
            } else {
                nuevo = BotonView.crearBoton(accion.getNombre(), ESTILO_BOTON, ESTILO_BOTON_HOVER, e -> {
                    botonera.getChildren().clear();
                    accion.accionar();
                    actualizarDatos();
                    if (this.juego.getJugadorActual().getEstado() == Config.EstadosJugadores.CRISIS) this.juego.activarPasiva();
                    if (accion.getTipo() == Config.TipoAcciones.PASAR_TURNO || accion.getTipo() == Config.TipoAcciones.PAGAR_FIANZA) actualizarBotonesInicio();
                    else actualizarBotonesAccion();
                });
            }
            this.botonera.getChildren().add(nuevo);
        }
    }

    public void setBotonesCasillas(List<Casilla> casillas, AccionCasilla accion){
        for (int i = 0; i < casillas.size() ; i++){
            Comprable comprable = (Comprable) casillas.get(i);
            Button nuevo = BotonView.crearBoton(comprable.getNombre(), ESTILO_BOTON, ESTILO_BOTON_HOVER, e -> {
                botonera.getChildren().clear();
                accion.setCasilla(comprable);
                accion.accionar();
                this.actualizarDatos();
                this.actualizarBotonesAccion();
            });
            this.botonera.getChildren().add(nuevo);
        }
    }

    private List<JugadorView> setJugadores(List<Jugador> jugadores, List<String> nombres){
        List<JugadorView> jugadorViews = new ArrayList<>();
        for (int i = 0 ; i<jugadores.size() ; i++){
            jugadorViews.add(new JugadorView(this.juego ,jugadores.get(i), nombres.get(i)));
        }
        return jugadorViews;
    }

    private void actualizarJugadores(){
        this.jugadores.getChildren().remove(0, jugadores.getChildren().size());
        for (JugadorView jugadorView : this.jugadorViews){
            jugadorView.actualizarTarjeta();
            this.jugadores.getChildren().add(jugadorView.getTarjeta());
        }
    }

    public void actualizarJugadorActual(){
        for (JugadorView jugadorView : this.jugadorViews){
            if (jugadorView.getJugador() == this.juego.getJugadorActual()){
                this.jugadorActual.setImage(new Image(this.getClass().getResourceAsStream(jugadorView.getImagen())));
            }
        }
    }

    public void actualizarDados(){
        List<ImageView> dados = new ArrayList<>(){{
            add(dado1);
            add(dado2);
        }};
        for (int i = 0 ; i < dados.size() ; i++ ){
            int dado = this.juego.getAdmMovimientos().getDados()[i];
            if (dado != 0) dados.get(i).setImage(new Image(this.getClass().getResourceAsStream("/images/"+dado+".png")));
        }
    }

    private void terminarJuego(){

        Label titulo = new Label("!!!GANADOR!!!");
        titulo.setMinSize(300, 80);
        titulo.setAlignment(Pos.CENTER);
        titulo.setStyle("-fx-background-color: #8FBC72;");
        titulo.setFont(new Font("Arial", 20));
        Label img = new Label();
        ImageView imagen = new ImageView(new Image(getClass().getResourceAsStream("/images/"+this.juego.ganador().getColor().toString()+".png")));
        img.setGraphic(imagen);
        imagen.setFitHeight(100);
        imagen.setFitWidth(100);
        VBox contImg = new VBox(img);
        VBox.setVgrow(contImg, Priority.ALWAYS);
        contImg.setAlignment(Pos.CENTER);
        VBox contenedor = new VBox(titulo,contImg);
        contenedor.setMinSize(300, 400);
        contenedor.setStyle("-fx-background-color: #BFDBAE;");
        Scene scene = new Scene(contenedor);
        Stage stageFinal = new Stage();
        stageFinal.setScene(scene);
        stageFinal.show();
        this.stage.close();
    }
}