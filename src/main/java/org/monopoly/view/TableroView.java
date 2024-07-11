package org.monopoly.view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.monopoly.model.Jugador;
import org.monopoly.model.Tablero;
import org.monopoly.model.casilla.Casilla;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TableroView {
    private VBox tablero;
    private int casillasPorLado;
    private List<Casilla> esquinas = new ArrayList<>();
    private List<Casilla> casillasArriba = new ArrayList<>();
    private List<Casilla> casillasDerecha = new ArrayList<>();
    private List<Casilla> casillasAbajo = new ArrayList<>();
    private List<Casilla> casillasIzquierda = new ArrayList<>();

    private HBox contCasillasArriba;
    private HBox contCasillasAbajo;
    private VBox contCasillasDerecha;
    private VBox contCasillasIzquierda;

    private HBox esquina1;
    private HBox esquina2;
    private HBox esquina3;
    private HBox esquina4;

    private List<HBox> litaEsquinas= new ArrayList<>();


    public TableroView(Tablero tablero, List<Jugador> jugadores) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tablero.fxml"));
            this.tablero = fxmlLoader.load();
            this.contCasillasArriba = (HBox) ((HBox) this.tablero.getChildren().get(0)).getChildren().get(1);
            this.contCasillasAbajo = (HBox) ((HBox) this.tablero.getChildren().get(2)).getChildren().get(1);
            this.contCasillasDerecha = (VBox) ((HBox) this.tablero.getChildren().get(1)).getChildren().get(2);
            this.contCasillasIzquierda = (VBox) ((HBox) this.tablero.getChildren().get(1)).getChildren().get(0);

            Label centro = (Label) ((HBox) this.tablero.getChildren().get(1)).getChildren().get(1);
            centro.setAlignment(Pos.CENTER);
            centro.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/centroTablero.png"))));

            this.esquina1 = (HBox) ((HBox) this.tablero.getChildren().get(0)).getChildren().get(0);
            this.esquina2 = (HBox) ((HBox) this.tablero.getChildren().get(0)).getChildren().get(2);
            this.esquina3 = (HBox) ((HBox) this.tablero.getChildren().get(2)).getChildren().get(0);
            this.esquina4 = (HBox) ((HBox) this.tablero.getChildren().get(2)).getChildren().get(2);

            this.litaEsquinas.add(esquina1);
            this.litaEsquinas.add(esquina2);
            this.litaEsquinas.add(esquina4);
            this.litaEsquinas.add(esquina3);


        } catch (IOException e) {}

        setCasillas(tablero.getCasillas());
        actualizarCasillas(jugadores);
    }

    public void setCasillas(List<Casilla> casillas){
        casillasPorLado = (casillas.size()-4) / 4;
        int casillasRestantes = (casillas.size()-4) % 4;
        List<List<Casilla>> listasCasillas = new ArrayList<>(){{
            add(casillasArriba);
            add(casillasDerecha);
            add(casillasAbajo);
            add(casillasIzquierda);
        }};

        int cont = 0;
        for (int i = 0; i < 4; i++) {
            this.esquinas.add(casillas.get(cont));
            cont++;
            for (int j = 0;j < casillas.size() && j < casillasPorLado; j++) {
                listasCasillas.get(i).add(casillas.get(cont));
                cont++;
            }
            if (casillasRestantes > 0){
                listasCasillas.get(i).add(casillas.get(cont));
                cont++;
                casillasRestantes--;
            }
        }

    }

    public void actualizarCasillas(List<Jugador> jugadores) {
        contCasillasArriba.getChildren().clear();
        contCasillasAbajo.getChildren().clear();
        contCasillasDerecha.getChildren().clear();
        contCasillasIzquierda.getChildren().clear();
        esquina1.getChildren().clear();
        esquina2.getChildren().clear();
        esquina3.getChildren().clear();
        esquina4.getChildren().clear();
        for (int i = 0 ; i < 4 ; i++) {
            CasillaView casillaView = new CasillaCuadradaView(this.esquinas.get(i), jugadores);
            HBox.setHgrow(litaEsquinas.get(i), Priority.ALWAYS);
            this.litaEsquinas.get(i).getChildren().add(casillaView.crearCasilla(this.esquinas.get(i)));
        }
        for (Casilla casilla : casillasArriba) {
            CasillaView casillaView = new CasillaArribaView(casilla, jugadores);
            contCasillasArriba.getChildren().add(casillaView.crearCasilla(casilla));
        }
        for (Casilla casilla : casillasDerecha) {
            CasillaView casillaView = new CasillaDerechaView(casilla, jugadores);
            contCasillasDerecha.getChildren().add(casillaView.crearCasilla(casilla));
        }
        for (Casilla casilla : casillasAbajo) {
            CasillaView casillaView = new CasillaAbajoView(casilla, jugadores);
            contCasillasAbajo.getChildren().add(0,casillaView.crearCasilla(casilla));
        }
        for (Casilla casilla : casillasIzquierda) {
            CasillaView casillaView = new CasillaIzquierdaView(casilla, jugadores);
            contCasillasIzquierda.getChildren().add(0,casillaView.crearCasilla(casilla));
        }
    }

    public VBox getTablero() {
        return tablero;
    }

    public void actualizarTablero(List<Jugador> jugadores){
        actualizarCasillas(jugadores);
    }
}
