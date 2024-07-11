package org.monopoly.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.monopoly.MainJavaFx;
import org.monopoly.model.Config;
import org.monopoly.model.Juego;
import org.monopoly.model.Jugador;
import org.monopoly.model.Tablero;
import org.monopoly.model.casilla.Casilla;
import org.monopoly.model.casilla.Comprable;
import org.monopoly.view.BotonView;
import java.io.IOException;
import java.util.*;

public class SeleccionadorController {

    @FXML
    private HBox botonera1;
    @FXML
    private HBox botonera2;
    @FXML
    private HBox botonera3;
    @FXML
    private Label instruccion;

    private int cantJugadores;

    Config.ColoresJugadores[] coloresArr = Config.ColoresJugadores.values();


    private final List<Config.ColoresJugadores> colores = new ArrayList<>();
    private final List<Config.ColoresJugadores> coloresElegidos = new ArrayList<>();

    private static final String ESTILO_BOTON = "-fx-min-width: 100; -fx-min-height: 80; -fx-border-radius: 25; -fx-background-radius: 25; -fx-background-color: #F8C471; -fx-border-color: #E59866; -fx-border-width: 3;";
    private static final String ESTILO_BOTON_HOVER = "-fx-min-width: 100; -fx-min-height: 80; -fx-border-radius: 25; -fx-background-radius: 25; -fx-background-color: #A04000; -fx-border-color: #E59866; -fx-border-width: 3;";


    public void setSeleccionador(){
        colores.addAll(Arrays.asList(coloresArr));
        setearBotones();
    }

    private void setearBotones(){
        for (int i = 2 ; i <= Config.MaxJugadores ; i++){
            String texto = i + " Jugadores";
            int cantJugadores = i;
            botonera1.getChildren().add(BotonView.crearBoton(texto, ESTILO_BOTON, ESTILO_BOTON_HOVER, e -> {
                this.botonera1.getChildren().clear();
                this.cantJugadores = cantJugadores;
                this.instruccion.setText("Seleccione "+cantJugadores+" Jugadores");
                MainJavaFx.resizeStage();
                this.setearBotonesJugadores();
            }));
        }
    }

    private void setearBotonesJugadores(){
        for (Config.ColoresJugadores c : colores){
            Button boton = BotonView.crearBoton("", ESTILO_BOTON, ESTILO_BOTON_HOVER, e -> {
                clearBotoneras();
                this.coloresElegidos.add(c);
                this.colores.remove(c);
                int jRestantes = (cantJugadores-coloresElegidos.size());
                this.instruccion.setText((jRestantes == 0) ? "Presione JUGAR" : "Seleccione "+jRestantes + ((jRestantes == 1) ? " Jugador" : " Jugadores"));
                if (this.coloresElegidos.size() == this.cantJugadores){
                    this.setearBotonesFinal();
                } else {
                    this.setearBotonesJugadores();
                }
            });

            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/" + c + ".png"))));
            imageView.setFitWidth(100);
            imageView.setFitHeight(80);
            boton.setGraphic(imageView);

            agregarABotoneras(boton);
        }
    }

    private void setearBotonesFinal(){
        botonera1.getChildren().add(BotonView.crearBoton("JUGAR", ESTILO_BOTON, ESTILO_BOTON_HOVER, e -> {
            this.cargarSegundaEscena();
            MainJavaFx.closeStage();
        }));
    }

    private void cargarSegundaEscena(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistaMonopoly.fxml"));
            HBox root = loader.load();
            JuegoController juegoController = loader.getController();
            Scene escena = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(escena);
            juegoController.setJuego(crearJuego(), stage);

        }catch (IOException ignored) {}
    }

    public Juego crearJuego(){
        List<Casilla> casillas = Config.ListaCasillas();

        Map<Config.ColoresComprables, List<Comprable>> tablaColores = Config.barrios();
        List<Jugador> jugadores = crearJugadores(casillas.get(0));
        Tablero tablero = new Tablero(casillas);

        return new Juego(tablero, jugadores, tablaColores);
    }

    public List<Jugador> crearJugadores(Casilla salida){
        List<Jugador> jugadores = new ArrayList<>();
        for (int i = 0 ; i < this.cantJugadores ; i++){
            jugadores.add(new Jugador( coloresElegidos.get(i),salida));
        }
        return jugadores;
    }

    private void agregarABotoneras(Button boton){
        int maxBotonera = 5;
        if (botonera1.getChildren().size() < maxBotonera) botonera1.getChildren().add(boton);
        else if (botonera2.getChildren().size() < maxBotonera) botonera2.getChildren().add(boton);
        else if (botonera3.getChildren().size() < maxBotonera) botonera3.getChildren().add(boton);
    }

    private void clearBotoneras(){
        botonera1.getChildren().clear();
        botonera2.getChildren().clear();
        botonera3.getChildren().clear();
    }
}
