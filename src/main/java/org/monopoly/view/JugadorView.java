package org.monopoly.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import org.monopoly.model.Config;
import org.monopoly.model.Juego;
import org.monopoly.model.Jugador;

public class JugadorView {

    private Juego juego;

    private final Jugador jugador;

    private String nombre;

    private HBox tarjeta;

    private PropiedadesView propiedades;

    private String ESTILO_BOTON = "-fx-max-width: 110; -fx-min-height: 40; -fx-min-width: 110; -fx-border-radius: 5; -fx-background-radius: 5; -fx-background-color: #F8C471; -fx-border-color: #E59866; -fx-border-width: 3;";
    private String ESTILO_BOTON_HOVER = "-fx-max-width: 110; -fx-min-height: 40; -fx-min-width: 110; -fx-border-radius: 5; -fx-background-radius: 5; -fx-background-color: #A04000; -fx-border-color: #E59866; -fx-border-width: 3;";

    public JugadorView(Juego juego, Jugador jugador, String nombre) {
        this.juego = juego;
        this.jugador = jugador;
        this.propiedades = new PropiedadesView(this.jugador, juego.getAdmJugador().getRegistroComprables());
        this.nombre = nombre;
        this.tarjeta = crearTarjetaJugador(this.nombre);
    }

    public static String mostrarInfoJugadores(Juego juego, Jugador j){
        StringBuilder content= new StringBuilder();
        content.append("\nEstado: " + j.getEstado());
        content.append("\nDinero: " + juego.getAdmJugador().getBanco().getCuentaJugador(j).getDinero());
        if (j.getEstado() == Config.EstadosJugadores.PRESO) content.append("\nTurnos carcel: " + j.getTurnosCarcel());
        return content.toString();
    }

    private HBox crearTarjetaJugador(String nombre) {
        Image img = new Image(getClass().getResourceAsStream("/images/"+this.jugador.getColor()+".png"));
        ImageView imagen = new ImageView(img);
        imagen.setFitHeight(70);
        imagen.setFitWidth(70);
        Label marco = new Label();
        marco.setAlignment(Pos.CENTER);
        String colorEnJuego = " -fx-background-color: lightgrey";
        String colorCrisis = " -fx-background-color: orange";
        String colorQuebrado = " -fx-background-color: #A04000";
        marco.setStyle("-fx-min-height: 110; -fx-min-width: 115;; -fx-border-color: grey; -fx-border-width: 2.5;");
        marco.setGraphic(imagen);
        AnchorPane pane = new AnchorPane();
        Button propiedades = BotonView.crearBoton("Propiedades", ESTILO_BOTON, ESTILO_BOTON_HOVER, e -> {
            this.propiedades.actualizarPropiedades();
            this.propiedades.mostrarPropiedades();

        });
        VBox contenedorBoton = new VBox(propiedades);
        contenedorBoton.setAlignment(Pos.CENTER);
        contenedorBoton.setStyle("-fx-min-height: 55; -fx-min-width: 90;"+((jugador.getEstado() == Config.EstadosJugadores.CRISIS) ?
                colorCrisis : (jugador.getEstado() == Config.EstadosJugadores.QUEBRADO) ? colorQuebrado : colorEnJuego ) +"; -fx-border-color: grey; -fx-border-width: 2.5;");

        VBox vbox1 = new VBox(marco, contenedorBoton);


        Label labelNombre = new Label(nombre);
        labelNombre.setStyle("-fx-min-height: 40; -fx-min-width: 155; "+((jugador.getEstado() == Config.EstadosJugadores.CRISIS) ?
                               colorCrisis : (jugador.getEstado() == Config.EstadosJugadores.QUEBRADO) ? colorQuebrado : colorEnJuego ) +"; -fx-border-color: grey; -fx-border-width: 2.5;");
        labelNombre.setAlignment(Pos.CENTER);

        Label datos = new Label();
        datos.setText(this.mostrarInfoJugadores(this.juego, this.jugador));
        datos.setStyle(" -fx-min-height: 125; -fx-min-width: 155; "+((jugador.getEstado() == Config.EstadosJugadores.CRISIS) ?
                               colorCrisis : (jugador.getEstado() == Config.EstadosJugadores.QUEBRADO) ? colorQuebrado : colorEnJuego ) +"; -fx-border-color: grey; -fx-border-width: 2.5;");
        datos.setTextAlignment(TextAlignment.LEFT);
        datos.setAlignment(Pos.CENTER);
        datos.setFont(new Font("Arial", 15));



        VBox vbox2 = new VBox(labelNombre, datos, pane);
        HBox tarjeta = new HBox(vbox1, vbox2);
        tarjeta.setStyle(" -fx-pref-width: 275.0; -fx-min-width: 275; -fx-max-width: 275.0; -fx-max-height: 170.0; -fx-min-height: 170.0; "+((jugador.getEstado() == Config.EstadosJugadores.CRISIS) ?
                                colorCrisis : (jugador.getEstado() == Config.EstadosJugadores.QUEBRADO) ? colorQuebrado : colorEnJuego ) +"; -fx-border-color: grey; -fx-border-width: 2.5 ");
        return tarjeta;
    }

    public String getImagen() {
        return "/images/"+this.jugador.getColor()+".png";
    }

    public HBox getTarjeta() {
        return tarjeta;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void actualizarTarjeta(){
        this.tarjeta = crearTarjetaJugador(this.nombre);
    }
    public void cerrarPropiedades(){
        this.propiedades.cerrarPropiedades();
    }
}
