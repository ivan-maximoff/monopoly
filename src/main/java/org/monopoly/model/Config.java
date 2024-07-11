package org.monopoly.model;

import org.monopoly.model.casilla.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Config {
    public enum ColoresJugadores{MORTY, SUMMER, JERRY, BETH, RICK, CARRETILLA, SOMBRERO, BARCO, SRMONOPOLY, AUTO, HOMERO, AYUDANTE, BART,BOLADENIEVE, LISA}
    public enum EstadosJugadores{EN_JUEGO, PRESO, QUEBRADO, CRISIS}
    public enum TiposCasillas{PROPIEDAD, ESTACION, IR_A_CARCEL, MULTA, LOTERIA, CARCEL, SALIDA, PASO}
    public enum ColoresComprables{black, brown, lightblue, pink, orange, red, yellow, green, blue}
    public enum TiposConstrucciones{CASA, HOTEL}

    public enum TipoAcciones{COMPRAR, CONSTRUIR, DESHIPOTECAR, ENTRAR_EN_QUIEBRA, HIPOTECAR, PASAR_TURNO, TIRAR_DADOS, VENDER, PAGAR_FIANZA}
    public enum EtapaAcciones{INICIO, CASILLA, FIN}
    public static int DineroInicial = 1500;
    public static int TurnosCarcel = 3;
    public static int MaxJugadores = 4;
    public static int ValorFianza = 50;
    public static int MaxCasas = 4;
    public static int MaxHoteles = 1;
    public static int MaxConstrucciones = MaxCasas + MaxHoteles;
    public static int DiferenciaContrucciones = 1;
    public static int PagoPorSalida = 200;
    public static int CantDados = 2;

    public static List<Construccion> construcciones(int valorCasa, int valorHotel){
        List<Construccion> construcciones = new ArrayList<>();
        construcciones.add(new Construccion(TiposConstrucciones.CASA, valorCasa));
        construcciones.add(new Construccion(TiposConstrucciones.CASA, valorCasa));
        construcciones.add(new Construccion(TiposConstrucciones.CASA, valorCasa));
        construcciones.add(new Construccion(TiposConstrucciones.CASA, valorCasa));
        construcciones.add(new Construccion(TiposConstrucciones.HOTEL, valorHotel));
        return construcciones;
    }
    public static CasillaSinEfecto carcel = new CasillaSinEfecto(TiposCasillas.CARCEL);
    public static List<Propiedad> ListaPropiedades = new ArrayList<>(){{
        add(new Propiedad(TiposCasillas.PROPIEDAD, "Mediterraneo", 60, 4, ColoresComprables.brown,  construcciones(50, 100), 50));
        add(new Propiedad(TiposCasillas.PROPIEDAD, "Baltco", 60, 6, ColoresComprables.brown,  construcciones(50, 100), 50));
        add(new Propiedad(TiposCasillas.PROPIEDAD, "Central", 100, 10, ColoresComprables.lightblue,  construcciones(50, 100), 50));
        add(new Propiedad(TiposCasillas.PROPIEDAD, "Vermont", 100, 12, ColoresComprables.lightblue, construcciones(50, 100), 50));
        add(new Propiedad(TiposCasillas.PROPIEDAD, "Connecticut", 120, 16, ColoresComprables.lightblue, construcciones(50, 100), 50));
        add(new Propiedad(TiposCasillas.PROPIEDAD, "St.Charles", 140, 20, ColoresComprables.pink, construcciones(50, 100), 100));
        add(new Propiedad(TiposCasillas.PROPIEDAD, "States", 140, 22, ColoresComprables.pink, construcciones(50, 100), 150));
        add(new Propiedad(TiposCasillas.PROPIEDAD, "Virginia", 160, 28, ColoresComprables.pink, construcciones(50, 100), 150));
        add(new Propiedad(TiposCasillas.PROPIEDAD, "St.James", 180, 30, ColoresComprables.orange, construcciones(50, 100), 150));
        add(new Propiedad(TiposCasillas.PROPIEDAD, "Tennesse", 180, 32, ColoresComprables.orange, construcciones(50, 100), 150));
        add(new Propiedad(TiposCasillas.PROPIEDAD, "New York", 200, 36, ColoresComprables.orange, construcciones(50, 100), 150));
        add(new Propiedad(TiposCasillas.PROPIEDAD, "Kentucky", 220, 38, ColoresComprables.red, construcciones(50, 100), 150));
        add(new Propiedad(TiposCasillas.PROPIEDAD, "Indiana", 220, 40, ColoresComprables.red, construcciones(50, 100), 150));
        add(new Propiedad(TiposCasillas.PROPIEDAD, "Illinois", 240, 44, ColoresComprables.red, construcciones(50, 100), 150));
        add(new Propiedad(TiposCasillas.PROPIEDAD, "Atlantic", 260, 46, ColoresComprables.yellow, construcciones(50, 100), 150));
        add(new Propiedad(TiposCasillas.PROPIEDAD, "Ventor", 260, 48, ColoresComprables.yellow, construcciones(50, 100), 150));
        add(new Propiedad(TiposCasillas.PROPIEDAD, "Marvin", 280, 52, ColoresComprables.yellow, construcciones(50, 100), 150));
        add(new Propiedad(TiposCasillas.PROPIEDAD, "Pacific", 300, 54, ColoresComprables.green, construcciones(50, 100), 200));
        add(new Propiedad(TiposCasillas.PROPIEDAD, "North Carolina", 300, 56, ColoresComprables.green, construcciones(50, 100), 200));
        add(new Propiedad(TiposCasillas.PROPIEDAD, "Pennsylvania", 320, 60, ColoresComprables.green, construcciones(50, 100), 200));
        add(new Propiedad(TiposCasillas.PROPIEDAD, "Park Place", 350, 65, ColoresComprables.blue, construcciones(50, 100), 200));
        add(new Propiedad(TiposCasillas.PROPIEDAD, "Boardwalk", 400, 70, ColoresComprables.blue, construcciones(50, 100), 200));
    }};

    public static List<Estacion> ListaEstaciones= new ArrayList<>(){{
        add(new Estacion(TiposCasillas.ESTACION, "Reading", 200, 25, ColoresComprables.black));
        add(new Estacion(TiposCasillas.ESTACION, "Pennsylvania", 200, 25, ColoresComprables.black));
        add(new Estacion(TiposCasillas.ESTACION, "B & O", 200, 25, ColoresComprables.black));
        add(new Estacion(TiposCasillas.ESTACION, "Sort Line", 200, 25, ColoresComprables.black));
    }};

    public static void agregarPropiedad(List<Casilla> lista, AtomicInteger indice, int cantidad){
        for(int i = indice.get(); i < cantidad + indice.get(); i++) {
            lista.add(ListaPropiedades.get(i));
        }
        indice.addAndGet(cantidad);
    }
    public static List<Casilla> ListaCasillas(){
         List<Casilla> listaCasillas = new ArrayList<Casilla>();
         listaCasillas.add(new CasillaSinEfecto(TiposCasillas.SALIDA));
         AtomicInteger indice = new AtomicInteger(0);
         agregarPropiedad(listaCasillas, indice, 2);
         listaCasillas.add(new Multa(TiposCasillas.MULTA, 200));
         listaCasillas.add(ListaEstaciones.get(0));
         agregarPropiedad(listaCasillas, indice, 2);
         listaCasillas.add(new Loteria(TiposCasillas.LOTERIA, 200));
         agregarPropiedad(listaCasillas, indice, 1);

         listaCasillas.add(carcel);

         agregarPropiedad(listaCasillas, indice, 3);
         listaCasillas.add(ListaEstaciones.get(1));
         agregarPropiedad(listaCasillas, indice, 1);
         listaCasillas.add(new Multa(TiposCasillas.MULTA, 200));
         agregarPropiedad(listaCasillas, indice, 2);
         listaCasillas.add(new CasillaSinEfecto(TiposCasillas.PASO));

         agregarPropiedad(listaCasillas, indice, 1);
         listaCasillas.add(new Multa(TiposCasillas.MULTA, 200));
         agregarPropiedad(listaCasillas, indice, 2);
         listaCasillas.add(ListaEstaciones.get(2));
         agregarPropiedad(listaCasillas, indice, 3);

         listaCasillas.add(new IrACarcel(TiposCasillas.IR_A_CARCEL, carcel));

         agregarPropiedad(listaCasillas, indice, 2);
         listaCasillas.add(new Loteria(TiposCasillas.LOTERIA, 200));
         agregarPropiedad(listaCasillas, indice, 1);
         listaCasillas.add(ListaEstaciones.get(3));
         agregarPropiedad(listaCasillas, indice, 1);
         listaCasillas.add(new Multa(TiposCasillas.MULTA, 200));
         agregarPropiedad(listaCasillas, indice, 1);

         return listaCasillas;
    }


    public static Map<Config.ColoresComprables, List<Comprable>> barrios(){
        Map<Config.ColoresComprables, List<Comprable>> TablaColores = new HashMap<>();

        for (Config.ColoresComprables color : Config.ColoresComprables.values()){
            TablaColores.put(color, new ArrayList<>());
        }
        for (Comprable propiedad: Config.ListaPropiedades) {
            TablaColores.get(propiedad.getColor()).add(propiedad);
        }
        for (Comprable estacion: Config.ListaEstaciones) {
            TablaColores.get(estacion.getColor()).add(estacion);
        }
        return TablaColores;
    }
}
