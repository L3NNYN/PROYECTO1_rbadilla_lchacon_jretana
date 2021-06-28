/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacao.controller;

import cacao.functions.AgregarImagen;
import cacao.functions.Cartas;
import cacao.functions.Jugador;
import cacao.functions.Partida;
import cacao.functions.Validaciones;
import cacao.util.SocketServices;
import cacao.functions.Variables;
import cacao.util.FlowController;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Pipo
 */
public class MesaJuegoViewController extends Controller implements Initializable, Observer {

    //Componentes principales
    @FXML
    private VBox vbImagen;

    public static String nombreJ;
    @FXML
    private ScrollPane scScroll;
    @FXML
    private AnchorPane paneMesaJuego;
    @FXML
    private JFXButton btnPasarTurno;
    @FXML
    private Text txtTurnoJugador;
    @FXML
    private GridPane vbJungla;

    private VBox matrizJungla[] = new VBox[2];

    private Cartas logicasSelva[] = new Cartas[2];

    //Componentes Socket Service
    private SocketServices sk = new SocketServices();

    private static SocketServices c = new SocketServices();

    //Componentes de matriz juego
    @FXML
    private GridPane gpMatrizJuego;

    private VBox matrizVbox[][] = new VBox[32][32];

    @FXML
    private JFXButton btnCentrar;
    @FXML
    private JFXButton btnDerecha;

    private static int fla = 0;

    private static int clm = 0;

    private int rot = 0;

    private int cSeleccionada = 0;

    private int jSeleccionada = 0;

    private boolean init = false;

    //Jugador pricipal
    private Partida p = new Partida();

    //Componentes del jugador 1
    @FXML
    private VBox vbContenedorJ1;
    @FXML
    private Text txtNombreJ1;
    @FXML
    private GridPane vbJugador1;

    private VBox botonesJ[] = new VBox[3];

    private Cartas logicas[] = new Cartas[3];

    public static String nombre;

    public static LocalDate edad;

    public static String color;

    @FXML
    private Text txtNuecesJ1;
    @FXML
    private Text txtAguaJ1;
    @FXML
    private Text txtSolJ1;
    @FXML
    private Text txtOroJ1;

    //Componentes del jugador 2
    @FXML
    private VBox vbContenedorJ2;
    @FXML
    private Text txtNombreJ2;
    @FXML
    private GridPane vbJugador2;
    @FXML
    private Text txtNuecesJ2;
    @FXML
    private Text txtAguaJ2;
    @FXML
    private Text txtSolJ2;
    @FXML
    private Text txtOroJ2;

    //Componenetes del jugador 3
    @FXML
    private VBox vbContenedorJ3;
    @FXML
    private Text txtNombreJ3;
    @FXML
    private GridPane vbJugador3;
    @FXML
    private Text txtNuecesJ3;
    @FXML
    private Text txtAguaJ3;
    @FXML
    private Text txtSolJ3;
    @FXML
    private Text txtOroJ3;
    //Componetes del jugador 4
    @FXML
    private VBox vbContenedorJ4;
    @FXML
    private Text txtNombreJ4;
    @FXML
    private GridPane vbJugador4;

    private static Cartas slc = new Cartas();

    public Variables variables = new Variables();

    private Validaciones vl = new Validaciones();

    public boolean recivido = false;
    @FXML
    private JFXButton btnSalir;

    @FXML
    private Text txtNuecesJ4;
    @FXML
    private Text txtAguaJ4;
    @FXML
    private Text txtSolJ4;
    @FXML
    private Text txtOroJ4;
    @FXML

    //Sala de espera
    private Text txtTd;
    @FXML
    private Text txtJ1;
    @FXML
    private Text txtJ2;
    @FXML
    private Text txtJ3;
    @FXML
    private Text txtJ4;
    @FXML
    private JFXButton btnListo;
    @FXML
    private AnchorPane vbSalaEspera;
    @FXML
    private VBox vbCuadroSeleccion;
    @FXML
    private VBox vbDinero;
    @FXML
    private Button btnArriba;
    @FXML
    private Button btnAbajo;
    @FXML
    private Button btnNIzquierda;
    @FXML
    private Button btnNDerecha;

    private int p1 = 0;
    private int p2 = 0;
    @FXML
    private AnchorPane vbGanador;
    @FXML
    private JFXButton btnSalirGn;
    @FXML
    private Text txtGanador;

    //Componentes losetas disponibles
    /**
     * Initializes the controller class.
     */
    public MesaJuegoViewController() {

    }

    //Cuando se inicia la partida se ponen los componentes y botones que no se necesitan invisibles, se inician los arrays, se crean objetos,
    //se envia una peticion al servidor para realizar y se agregan VBox al gridPane(Mesa de juego) paara controlar las losetas y demas funciones.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Se agrega un observer
        c.addObserver(this);
        //Se inician las matrices de VBox
        agregarVbox();
        cartasJugador();
        cartasJungla();
        //Se ponen visibles los botones
        btnDerecha.setDisable(true);
        btnCentrar.setDisable(true);
        btnPasarTurno.setDisable(true);
        //Se inicializan algunos booleanos que se necesitaran mas adelante en true o falce
        variables.setCartaTrabajador(true);
        variables.setCartaTablero(false);
        variables.setCartaJungla(false);
        variables.setNum(false);
        variables.setLlenarJungla(false);
        //Los componentes de los jugadores se ponen invisibles para no mostrarlos en blanco
        //Se pondran visibles cuando ingrese un jugador
        vbContenedorJ1.setVisible(false);
        vbContenedorJ2.setVisible(false);
        vbContenedorJ3.setVisible(false);
        vbContenedorJ4.setVisible(false);
        vbCuadroSeleccion.setVisible(false);

        try {
            //Se crea un objeto de tipo jugador, el cual sera el que vamos a utilizar,
            //este se envia al servidor y sera registrado
            Jugador g = new Jugador(nombre, edad, color);
            g.crearCartas(nombre, color);
            p.iniciarArrays();
            p.agregarJugador(g);
            cartasUsables();
            //Se enviara una peticion de registrar un jugador, el cual es el que acabamos de creat
            enviarPeticion("registrar jugador");

            init = true;
            //llenarCartasJungla();
            //Se grafican las cartas del jugador
            for (int i = 0; i < 3; i++) {
                if (logicas[i] != null) {
                    agregarImagen(1, vbJugador1, logicas, botonesJ, null, null, logicas[i], 0, i);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(MesaJuegoViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        visibilidad(false);
    }

    @Override
    public void initialize() {

    }

    //Metodo para poner visible algunos componentes en la pantalla, se envia por parametro true para ponerlos visibles o false para esconderlos
    private void visibilidad(boolean visibilidad) {
        btnSalir.setVisible(visibilidad);
        scScroll.setVisible(visibilidad);
        btnCentrar.setVisible(visibilidad);
        btnDerecha.setVisible(visibilidad);
        btnPasarTurno.setVisible(visibilidad);
        vbImagen.setVisible(visibilidad);
        txtTd.setVisible(visibilidad);
        txtTurnoJugador.setVisible(visibilidad);
        vbJungla.setVisible(visibilidad);

    }

    //Metodo para actualizar los botones correspondientes a la carta que acabamos de poner, y con esto poder recolectar recursos que esten alrededor de nuestra carta
    private void actualizar() {

        vbCuadroSeleccion.setVisible(true);

        //Si hay alguna imagen en la parte de recoleccion de recursos se borra para colo car la mas reciente.
        if (vbDinero.getChildren().size() > 0) {
            vbDinero.getChildren().remove(0);
        }

        //Se agrega lam loseta de trabajador recien puesta para recolectar sus reecursos
        Image im = new Image("/cacao/resources/Cartas/" + slc.getTipo() + slc.getDerecha() + slc.getAbajo() + slc.getIzquierda() + slc.getArriba() + ".png", 86, 86, false, true);
        ImageView nw = new ImageView(im);
        nw.setRotate(slc.getGrados());
        vbDinero.getChildren().add(nw);

        //Se agrega el estilo correspondiente a esta
        vbDinero.setId(color);
        vbDinero.getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());

        int x = p1;
        int y = p2;
        //Derecha
        //Si la loseta a la derecha es diferente de null, se agrega en el boton la cantidad de recursos que se pueden recolectar de ese lado
        if (p.getMatrizLogica()[x][y + 1] != null) {
            btnNDerecha.setText(String.valueOf(p.getMatrizLogica()[x][y].getDerechaG()));
            btnNDerecha.setDisable(false);
        } else {
            btnNDerecha.setText("");
            btnNDerecha.setDisable(true);
        }
        //Abajo
        //Si la loseta de abajo es diferente de null, se agrega en el boton la cantidad de recursos que se pueden recolectar de ese lado
        if (p.getMatrizLogica()[x + 1][y] != null) {
            btnAbajo.setText(String.valueOf(p.getMatrizLogica()[x][y].getAbajoG()));
            btnAbajo.setDisable(false);
        } else {
            btnAbajo.setText("");
            btnAbajo.setDisable(true);
        }

        //Izquierda
        //Si la loseta a la izquierda es diferente de null, se agrega en el boton la cantidad de recursos que se pueden recolectar de ese lado
        if (p.getMatrizLogica()[x][y - 1] != null) {
            btnNIzquierda.setText(String.valueOf(p.getMatrizLogica()[x][y].getIzquierdaG()));
            btnNIzquierda.setDisable(false);
        } else {
            btnNIzquierda.setText("");
            btnNIzquierda.setDisable(true);
        }

        //Arriba
        //Si la loseta arriba es diferente de null, se agrega en el boton la cantidad de recursos que se pueden recolectar de ese lado
        if (p.getMatrizLogica()[x - 1][y] != null) {
            btnArriba.setText(String.valueOf(p.getMatrizLogica()[x][y].getArribaG()));
            btnArriba.setDisable(false);
        } else {
            btnArriba.setText("");
            btnArriba.setDisable(true);
        }
    }

    //Metodo que extiende de observer, sirve para que el metodo que nos recive la informacion del servidor nos mande por parametro la clase
    //cargada con los datos a realizar en la vista
    @Override
    public void update(Observable o, Object arg) {

        //Se obtiene el String de la clase recivida y se convierte a clase para poder realizar los cambioos respectivos
        String cadena = (String) arg;
        Gson gs = new Gson();
        Partida llegada = gs.fromJson(cadena, Partida.class);
        
        //Metodo para poder realizar cambios graficos desde un hilo
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                int tm = 0;
                for (int i = 0; i < 4; i++) {
                    if (llegada.getJugadores()[i] != null) {
                        tm++;
                    }
                }

                //Si la peticion es jugadores este se encarga de agrega los jugadores que se acaban de unir a la parte grafica y logica del jugador 
                if ("Jugadores".equals(llegada.getPeticion())) {
                    
                    //Se agregan todas las partes logicas enviadas por el servidor al jugador actual
                    borrarImagen(14, 15, gpMatrizJuego);
                    p.agregarCarta(14, 15, llegada.getCartasIniciales()[0]);
                    agregarImagen(4, gpMatrizJuego, null, null, matrizVbox, p.getMatrizLogica(), llegada.getCartasIniciales()[0], 14, 15);

                    borrarImagen(15, 16, gpMatrizJuego);
                    p.agregarCarta(15, 16, llegada.getCartasIniciales()[1]);
                    agregarImagen(4, gpMatrizJuego, null, null, matrizVbox, p.getMatrizLogica(), llegada.getCartasIniciales()[1], 15, 16);

                    p.setJugadores(llegada.getJugadores());
                    p.setTurnoJugador(llegada.getTurnoJugador());
                    txtTurnoJugador.setText(p.getTurnoJugador());
                    if (!variables.getLlenarJungla()) {
                        variables.setLlenarJungla(true);
                        p.setCartasJungla(llegada.getCartasJungla());
                        try {
                            llenarCartasJungla();
                            for (int i = 0; i < 2; i++) {
                                agregarImagen(3, vbJungla, logicasSelva, matrizJungla, null, null, logicasSelva[i], i, 0);
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(MesaJuegoViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }

                    //Aqui se agrega el nombre, color, nueces y demas cosas de los demas jugadores en la parte grafica hubicada a la izquierda
                    int contador = 1;
                    for (int i = 0; i < p.getJugadores().length; i++) {
                        if (p.getJugadores()[i] != null) {
                            if (p.getJugadores()[i].getNombre().equals(nombre)) {
                                vbContenedorJ1.setVisible(true);
                                vbContenedorJ1.setId(p.getJugadores()[i].getColor());
                                vbContenedorJ1.getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                                txtNombreJ1.setText(p.getJugadores()[i].getNombre());
                                txtJ1.setText(p.getJugadores()[i].getNombre());
                                if (p.getJugadores()[i].getListo().equals("listo")) {
                                    txtJ1.setStyle("-fx-fill: Blue");
                                } else {

                                }
                            } else {
                                if (contador == 1) {
                                    vbContenedorJ2.setVisible(true);
                                    vbContenedorJ2.setId(p.getJugadores()[i].getColor());
                                    vbContenedorJ2.getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                                    txtNombreJ2.setText(p.getJugadores()[i].getNombre());
                                    txtJ2.setText(p.getJugadores()[i].getNombre());
                                    if (p.getJugadores()[i].getListo().equals("listo")) {
                                        txtJ2.setStyle("-fx-fill: Blue");
                                    } else {

                                    }
                                } else if (contador == 2) {
                                    vbContenedorJ3.setVisible(true);
                                    vbContenedorJ3.setId(p.getJugadores()[i].getColor());
                                    vbContenedorJ3.getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                                    txtNombreJ3.setText(p.getJugadores()[i].getNombre());
                                    txtJ3.setText(p.getJugadores()[i].getNombre());
                                    if (p.getJugadores()[i].getListo().equals("listo")) {
                                        txtJ3.setStyle("-fx-fill: Blue");
                                    } else {

                                    }
                                } else if (contador == 3) {
                                    vbContenedorJ4.setVisible(true);
                                    vbContenedorJ4.setId(p.getJugadores()[i].getColor());
                                    vbContenedorJ4.getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                                    txtNombreJ4.setText(p.getJugadores()[i].getNombre());
                                    txtJ4.setText(p.getJugadores()[i].getNombre());
                                    if (p.getJugadores()[i].getListo().equals("listo")) {
                                        txtJ4.setStyle("-fx-fill: Blue");
                                    } else {

                                    }
                                }
                                contador++;
                            }
                        }
                    }

                    //Si la partida no se ha iniciado se pone la sala de espera visible y se espera a que todos los jugadores le den listo
                    boolean iniciar = true;
                    for (int i = 0; i < 4; i++) {
                        if (p.getJugadores()[i] != null) {
                            if (p.getJugadores()[i].getListo().equals("listo")) {

                            } else {
                                iniciar = false;
                            }
                        }
                    }
                    if (iniciar) {
                        vbSalaEspera.setVisible(false);
                        visibilidad(true);
                    }
                    //Se actualizan las cartas de los jugadores, esto por el motovo de que algun hizo un cambio
                } else if ("actualizar cartas jugadores".equals(llegada.getPeticion())) {
                    for (int i = 0; i < 4; i++) {
                        if (p.getJugadores()[i] != null) {
                            p.getJugadores()[i].setCartasJugador(llegada.getJugadores()[i].getCartasJugador());
                        }
                    }
                //En este metodo se actualizaran los puntajes de algun jugador que tuvo algun cambio en estos,
                //por ejemplo si algun jugador gano oro el servidor envia una actualizacion a todos los jugadores de 
                //actualizar el oro ganado por ese mismo en las mesas de juego, de esta manera podermos ver el puntaje
                //obtenido por todos los jugadores
                } else if ("actualizar puntaje".equals(llegada.getPeticion())) {

                    p.setJugadores(llegada.getJugadores());
                    int contador = 1;
                    for (int i = 0; i < p.getJugadores().length; i++) {
                        if (p.getJugadores()[i] != null) {
                            if (p.getJugadores()[i].getNombre().equals(nombre)) {

                            } else {
                                if (contador == 1) {
                                    System.out.print(p.getJugadores()[i].getNueces());
                                    txtNuecesJ2.setText(String.valueOf(p.getJugadores()[i].getNueces()));
                                    txtAguaJ2.setText(String.valueOf(p.getJugadores()[i].getAgua()));
                                    txtSolJ2.setText(String.valueOf(p.getJugadores()[i].getFichasSol()));
                                    txtOroJ2.setText(String.valueOf(p.getJugadores()[i].getMonedas()));
                                } else if (contador == 2) {
                                    txtNuecesJ3.setText(String.valueOf(p.getJugadores()[i].getNueces()));
                                    txtAguaJ3.setText(String.valueOf(p.getJugadores()[i].getAgua()));
                                    txtSolJ3.setText(String.valueOf(p.getJugadores()[i].getFichasSol()));
                                    txtOroJ3.setText(String.valueOf(p.getJugadores()[i].getMonedas()));
                                } else if (contador == 3) {
                                    txtNuecesJ4.setText(String.valueOf(p.getJugadores()[i].getNueces()));
                                    txtAguaJ4.setText(String.valueOf(p.getJugadores()[i].getAgua()));
                                    txtSolJ4.setText(String.valueOf(p.getJugadores()[i].getFichasSol()));
                                    txtOroJ4.setText(String.valueOf(p.getJugadores()[i].getMonedas()));
                                }
                                contador++;
                            }
                        }
                    }

                    //Este metodo sirve para recivir el turno del siguiente jugador
                } else if ("pasar turno".equals(llegada.getPeticion())) {
                    p.setTurnoJugador(llegada.getTurnoJugador());
                    String turno = llegada.getTurnoJugador();
                    txtTurnoJugador.setText(p.getTurnoJugador());
                    if (p.getTurnoJugador().equals(nombre)) {
                        variables.setCartaTrabajador(true);
                    }

                 //Si alguna loseta fue puesta en la mesa de jugego este metodo recive la peticion del servidor de colocar esa
                 //loceta en la mesa de juego de todos los jugadores
                } else if ("colocar carta jugador".equals(llegada.getPeticion())) {
                    p.setMatrizLogica(llegada.getMatrizLogica());
                    p.setCartaJugada(llegada.getCartaJugada(), llegada.getX(), llegada.getY(), 0);
                    if (!p.getTurnoJugador().equals(nombre)) {
                        borrarImagen(llegada.getX(), llegada.getY(), gpMatrizJuego);
                        matrizVbox[p.getX()][p.getY()].setId(p.getCartaJugada().getColor());
                        matrizVbox[p.getX()][p.getY()].getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                        agregarImagen(2, gpMatrizJuego, null, null, matrizVbox, p.getMatrizLogica(), p.getCartaJugada(), p.getX(), p.getY());
                    }
                 ////Si alguna carta de junglafue puesta en la mesa de jugego este metodo recive la peticion del servidor de colocar esa
                 //loceta en la mesa de juego de todos los jugadores
                } else if ("colocar carta jungla".equals(llegada.getPeticion())) {
                    if (!p.getTurnoJugador().equals(nombre)) {
                        p.setCartasJungla(llegada.getCartasJungla());
                        p.setCartaJugada(llegada.getCartaJugada(), llegada.getX(), llegada.getY(), llegada.getMazo());
                        p.setMatrizLogica(llegada.getMatrizLogica());
                        logicasSelva[llegada.getMazo()] = null;
                        borrarImagen(p.getX(), p.getY(), gpMatrizJuego);
                        agregarImagen(4, gpMatrizJuego, null, null, matrizVbox, p.getMatrizLogica(), p.getCartaJugada(), p.getX(), p.getY());
                        borrarImagen(p.getMazo(), 0, vbJungla);

                    }
                 //Metodo para recivir una actualizacion de todos los jugadores que estan en el servidor
                } else if ("actualizar jugadores".equals(llegada.getPeticion())) {

                    p.setJugadores(llegada.getJugadores());
                    int ct = 0;
                    variables.setNum(true);
                    for (int i = 0; i < 4; i++) {
                        if (p.getJugadores()[i] != null) {
                            ct++;
                            variables.setNum(false);
                        }
                    }
                  //Si alguna carta de jungla fue retirada de la parte izquierda, este metodo vuelve a llenar esa parte con mas cartas
                  //y agrega la parte grafica de estas mismas
                } else if ("actualizar jungla".equals(llegada.getPeticion())) {
                    if (!p.getTurnoJugador().equals(nombre)) {
                        try {
                            llenarCartasJungla();
                        } catch (IOException ex) {
                            Logger.getLogger(MesaJuegoViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        for (int i = 0; i < 2; i++) {
                            if (logicasSelva[i] != null) {
                                matrizJungla[i].setId("color");
                                matrizJungla[i].getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                                agregarImagen(3, vbJungla, logicasSelva, matrizJungla, null, null, logicasSelva[i], i, 0);
                                matrizJungla[i].getStyleClass().clear();
                                agregarImagen(3, vbJungla, logicasSelva, matrizJungla, null, null, logicasSelva[i], i, 0);
                            }
                        }
                    }

                  //Metodo pare recivir un ganador determinado por el servidor
                }else if("ganador".equals(llegada.getPeticion())){
                    txtGanador.setText(llegada.getGanador());
                    vbGanador.setVisible(true);
                }
            }
        });

    }

    //Metodo que agregara y creara los VBox en un arreglo de VBox, despues este agregara a cada uno de sus componentes
    //el evento de click, el cual ayudara a determinar en cual fila y columna del VBox se quiere realizar una acicion.
    private void agregarVbox() {

        for (int i = 0; i <= 31; i++) {
            for (int j = 0; j <= 31; j++) {

                matrizVbox[i][j] = new VBox();
                matrizVbox[i][j].setAlignment(Pos.CENTER);
                matrizVbox[i][j].setPrefHeight(89);
                matrizVbox[i][j].setPrefWidth(89);
                gpMatrizJuego.add(matrizVbox[i][j], j, i);
                final int fl = i;
                final int cl = j;
                variables.setComprobacion(true);
                matrizVbox[i][j].addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //Si no es erl turno del jugador no se puede entrar a relizar algun cambio en la matriz
                        if (p.getTurnoJugador().equals(nombre)) {
                            if (variables.getCartaTablero()) {
                                fla = fl;
                                clm = cl;

                                //If else para difereciar si es carta de trabajor o jungla
                                if (variables.getNum()) {
                                    //Se reliza la validacion respectiva, si no se puede agregar la carta no dentra al if
                                    if (vl.validarCartaJugador(p.getMatrizLogica(), "Jungla", fl, cl)) {
                                        borrarImagen(fl, cl, gpMatrizJuego);
                                        btnDerecha.setDisable(true);
                                        btnCentrar.setDisable(true);
                                        btnPasarTurno.setDisable(false);
                                        agregarImagen(4, gpMatrizJuego, null, null, matrizVbox, p.getMatrizLogica(), logicasSelva[jSeleccionada], fl, cl);
                                        p.agregarCarta(fl, cl, logicasSelva[jSeleccionada]);
                                        p.setCartaJugada(logicasSelva[jSeleccionada], fl, cl, jSeleccionada);
                                        logicasSelva[jSeleccionada] = null;
                                        variables.setNum(false);
                                        borrarImagen(jSeleccionada, 0, vbJungla);
                                        enviarPeticion("colocar carta jungla");
                                        vbCuadroSeleccion.setVisible(true);
                                        actualizar();
                                    }

                                } else {
                                    //Si dentra aqui significa que es una carta de trabajador
                                    //Se reliza la validacion respectiva, si no se puede agregar la carta no dentra al if
                                    if (vl.validarCartaJugador(p.getMatrizLogica(), "Tbr", fl, cl)) {
                                        System.out.print("Validaciones F: " + fl + " C: " + cl + "\n");
                                        variables.setCartaTrabajador(false);
                                        variables.setCartaJungla(true);
                                        borrarImagen(fl, cl, gpMatrizJuego);
                                        btnDerecha.setDisable(false);
                                        btnCentrar.setDisable(false);
                                        slc = logicas[cSeleccionada];
                                        p1 = fl;
                                        p2 = cl;
                                        matrizVbox[fl][cl].setId("opacity");
                                        matrizVbox[fl][cl].getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                                        agregarImagen(2, gpMatrizJuego, null, null, matrizVbox, p.getMatrizLogica(), logicas[cSeleccionada], fl, cl);
                                        p.agregarCarta(fl, cl, logicas[cSeleccionada]);
                                        p.setCartaJugada(logicas[cSeleccionada], fl, cl, 0);
                                        logicas[cSeleccionada] = null;
                                        borrarImagen(0, cSeleccionada, vbJugador1);
                                        enviarPeticion("colocar carta jugador");
                                    }

                                }

                            }
                        }
                    }
                });
            }
        }
    }

    //Metodo par enviar una peticion al servidor 
    public void enviarPeticion(String peticion) {

        //Se convierte la clase Partida con toda la informacion en String, esto mediante la libreria GSon y se envia al servidor.
        p.setPeticion(peticion);
        Gson gson = new Gson();
        String json = gson.toJson(p);
        try {
            c.enviarDatos(json);
        } catch (IOException ex) {
            Logger.getLogger(MesaJuegoViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Metodo que agregara y creara los VBox en un arreglo de VBox, correspondiente a las cartas del jugador, despues este agregara a cada uno de sus componentes
    //el evento de click, el cual ayudara a determinar en cual fila y columna del VBox se quiere realizar una acicion.
    private void cartasJugador() {

        for (int i = 0; i < 3; i++) {

            botonesJ[i] = new VBox();
            botonesJ[i].setAlignment(Pos.CENTER);
            botonesJ[i].setId(color);
            botonesJ[i].getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
            botonesJ[i].setPrefHeight(73);
            botonesJ[i].setPrefWidth(73);
            final int cs = i;
            botonesJ[i].addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    //Si no es erl turno del jugador no se puede entrar a relizar algun cambio en la matriz
                    if (p.getTurnoJugador().equals(nombre)) {
                        if (variables.getCartaTrabajador()) {
                            variables.setCartaTablero(true);
                            variables.setCartaJungla(true);
                            cSeleccionada = cs;
                            logicas[cs].setColor(color);
                            borrarImagen(0, cs, vbJugador1);
                            botonesJ[cs].setId("opacity");
                            botonesJ[cs].getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                            agregarImagen(1, vbJugador1, logicas, botonesJ, null, null, logicas[cs], 0, cs);
                        }
                    }
                }
            });
        }
    }

    //Metodo que agregara y creara los VBox en un arreglo de VBox, correspondiente a las cartas de jungla, despues este agregara a cada uno de sus componentes
    //el evento de click, el cual ayudara a determinar en cual fila y columna del VBox se quiere realizar una acicion.
    private void cartasJungla() {

        for (int i = 0; i < 2; i++) {

            matrizJungla[i] = new VBox();
            matrizJungla[i].setAlignment(Pos.CENTER);
            matrizJungla[i].setPrefHeight(98);
            matrizJungla[i].setPrefWidth(98);
            final int cs = i;
            //vbJungla.add(matrizJungla[i], 0, i);
            matrizJungla[i].addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    //Si no es erl turno del jugador no se puede entrar a relizar algun cambio en la matriz
                    if (p.getTurnoJugador().equals(nombre)) {
                        if (variables.getCartaJungla()) {
                            variables.setCartaTablero(true);
                            variables.setNum(true);
                            variables.setCartaJungla(false);
                            jSeleccionada = cs;
                            matrizJungla[cs].setId("opacity");
                            matrizJungla[cs].getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                            agregarImagen(3, vbJungla, logicasSelva, matrizJungla, null, null, logicasSelva[cs], cs, 0);
                        }
                    }
                }
            });
        }

    }

    //Metrodo para borrar una imagen, recive como parametro el GridPsne, la fila y columna deseada de borrar
    public void borrarImagen(final int row, final int column, GridPane gridPane) {

        ObservableList<Node> childrens = gridPane.getChildren();
        for (Node node : childrens) {
            if (node instanceof VBox && gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                gridPane.getChildren().remove(node);
                break;
            }
        }
    }

    //Metodo para gregar una imagen, recive datos como el tipo, gridPane, carasta, la matriz de vbox, un arreglo de VBox, la fila y la columna.
    public void agregarImagen(int tipo, GridPane gridpane, Cartas[] cartas, VBox[] vbox, VBox[][] mVBox, Cartas[][] cartasM, Cartas carta, int f, int c) {
        AgregarImagen add = new AgregarImagen();
        add.agregarImagen(tipo, gridpane, cartas, vbox, mVBox, cartasM, carta, f, c);
    }

    //Metodo para retornar el socket usado en la partida
    public SocketServices getSocket() {
        return c;
    }

    //metodo para traer los datos del jugador desde el InicioViewController
    public void setDatos(String nombre, LocalDate edad, String color) {
        MesaJuegoViewController.nombre = nombre;
        MesaJuegoViewController.edad = edad;
        MesaJuegoViewController.color = color;

    }

    //Metodo para pasar de turno, se envian datos al servidor como de actualizar las cartas de jungla hubicadas en la izquierda
    //y pasar de turno al jugador como tal
    @FXML
    private void onActiomPasarTurno(ActionEvent event) throws IOException {
        //Cartas trabajador
        llenarCartasJungla();
        cartasUsables();
        pasarTurno();
        enviarPeticion("actualizar jungla");
        enviarPeticion("pasar turno");
        vbCuadroSeleccion.setVisible(false);
        btnPasarTurno.setDisable(true);
        if (logicasSelva[0] == null && logicasSelva[1] == null) {
            enviarPeticion("ganador");
        }
    }

    //Tambien si se pasa de turno se analizaran si ya no hay cartas de jungla disponibles, entonces se terminara
    //si la partida ya termino
    private void pasarTurno() {
        for (int i = 0; i < 3; i++) {
            if (logicas[i] != null) {
                agregarImagen(1, vbJugador1, logicas, botonesJ, null, null, logicas[i], 0, i);
            }
            if (i < 2) {
                if (logicasSelva[i] != null) {
                    matrizJungla[i].setId("color");
                    matrizJungla[i].getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                    agregarImagen(3, vbJungla, logicasSelva, matrizJungla, null, null, logicasSelva[i], i, 0);
                    matrizJungla[i].getStyleClass().clear();
                    agregarImagen(3, vbJungla, logicasSelva, matrizJungla, null, null, logicasSelva[i], i, 0);
                }
            }
        }
    }

    //Metodo para confirmar la colocacion de una carta en la mesa de juego
    @FXML
    private void onActionCentrar(ActionEvent event) throws IOException {

        botonesJ[cSeleccionada].setId(color);
        botonesJ[cSeleccionada].getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());

        borrarImagen(fla, clm, gpMatrizJuego);
        matrizVbox[fla][clm].setId(color);
        matrizVbox[fla][clm].getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
        gpMatrizJuego.add(matrizVbox[fla][clm], clm, fla);

        btnDerecha.setDisable(true);
        btnCentrar.setDisable(true);
    }

    //Metodo para girar una carta, cuando se oprime este boton se analizara cuantos grados tiene la carta, y dependiendo de estos 
    //la carta se girara 90 grados, tambien esto se hace en unos atributos de las cartas para saber la cantidad de recolectores que se
    //tiene por lado
    @FXML
    private void onActionDerecha(ActionEvent event) {

        borrarImagen(fla, clm, gpMatrizJuego);

        if (p.getMatrizLogica()[fla][clm].getGrados() == 0) {
            rot = 90;
            p.getMatrizLogica()[fla][clm].setGrados(90);
            slc.setGrados(rot);
        } else if (p.getMatrizLogica()[fla][clm].getGrados() == 90) {
            rot = 180;
            slc.setGrados(rot);
            p.getMatrizLogica()[fla][clm].setGrados(180);
        } else if (p.getMatrizLogica()[fla][clm].getGrados() == 180) {
            rot = 270;
            p.getMatrizLogica()[fla][clm].setGrados(270);
            slc.setGrados(rot);
        } else if (p.getMatrizLogica()[fla][clm].getGrados() == 270) {
            rot = 0;
            p.getMatrizLogica()[fla][clm].setGrados(0);
            slc.setGrados(rot);
        }

        int derecha = p.getMatrizLogica()[fla][clm].getDerechaG();
        int abajo = p.getMatrizLogica()[fla][clm].getAbajoG();
        int izquierda = p.getMatrizLogica()[fla][clm].getIzquierdaG();
        int arriba = p.getMatrizLogica()[fla][clm].getArribaG();

        p.getMatrizLogica()[fla][clm].setDerechaG(arriba);
        p.getMatrizLogica()[fla][clm].setAbajoG(derecha);
        p.getMatrizLogica()[fla][clm].setIzquierdaG(abajo);
        p.getMatrizLogica()[fla][clm].setArribaG(izquierda);

        matrizVbox[fla][clm].getChildren().get(0).setRotate(rot);

        gpMatrizJuego.add(matrizVbox[fla][clm], clm, fla);

        p.setCartaJugada(p.getMatrizLogica()[fla][clm], fla, clm, 0);

        //Se envia la peticion de colocar la carta de un jugador
        enviarPeticion("colocar carta jugador");
    }

    //Se analiza si hay espacios en blanco en las 3 cartas hubicadas al lado derecho de cada jugador, si es asi
    //estas se llenaran con mas cartas, si el jugador ya no posee cartas no se llenara con nada,
    private void cartasUsables() throws IOException {
        Validaciones vl = new Validaciones();
        vl.cartasUsables(p.getCartasJungla(), logicas, p, jugadorActual());

        int sm = 0;
        for (int i = 0; i < 11; i++) {
            if (p.getJugadores()[jugadorActual()].getCartasJugador()[i] != null) {
                sm++;
            }
        }
        if (init) {
            enviarPeticion("actualizar cartas jugadores");
        }

    }

    //Metodo para detectar si hay espacios en blanco en la parte izquierda de la pantalla, correspondiente a las cartas de la jungla,
    //si hay espacios en blanco se llenaran con mas losetas de jungla, si no hay mas losetas se deja en blanco.
    private void llenarCartasJungla() throws IOException {
        Validaciones vl = new Validaciones();
        vl.llenarCartasJungla(p.getCartasJungla(), logicasSelva, p);
    }

    //Metodo para detectar el jugador actual de la partida
    private int jugadorActual() {
        int jugador = 0;
        boolean escogido = false;
        for (int i = 0; i < p.getJugadores().length; i++) {
            if (escogido == false) {
                if (p.getJugadores()[i].getNombre().equals(nombre)) {
                    jugador = i;
                    escogido = true;
                }
            }
        }
        return jugador;
    }

    //Metodos para retornar datos como partida, noombre, color y demas.
    
    public Partida getP() {
        return p;
    }

    public void setP(Partida p) {
        this.p = p;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getEdad() {
        return edad;
    }

    public String getColor() {
        return color;
    }

    public Variables getVariables() {
        return variables;
    }

    public void setVariables(Variables variables) {
        this.variables = variables;
    }

    //Metodo para salir de la partida, se envia un mensaje al servidor para borrar al jugador y tambien se 
    //cierra el hilo que estaba escuchando las petciones de los jugadores
    @FXML
    private void onActionSalir(ActionEvent event) throws IOException {
        
        p.setSalir(nombre);
        enviarPeticion("salir");
        FlowController.getInstance().goViewInNewStage("InicioView", stage);
        
    }

    //Metodo para determinar si un jugador esta listo en la sala de espera, si todos estan listos la partida comienza,
    //tambien se verifica la cantidad de jugadores que hay, y dependiendo de esta se borraran cierta cantidad de cartas de 
    //jugador o jungla.
    @FXML
    private void onActionListo(ActionEvent event) {
        p.setListo(nombre);
        int cantJ = 0;
        for (int i = 0; i < 4; i++) {
            if (p.getJugadores()[i] != null) {
                cantJ++;
            }
        }
        System.out.print("Jugadores: " + cantJ);
        if (cantJ < 3) {
            int aux1 = 0;
            int aux2 = 0;
            int aux3 = 0;
            int aux4 = 0;
            int aux5 = 0;
            int aux6 = 0;
            for (int i = 0; i < 28; i++) {
                if (p.getCartasJungla()[i] != null) {
                    if (p.getCartasJungla()[i].getTipo().equals("Pla") && p.getCartasJungla()[i].getValor() == 1) {
                        if (aux1 < 2) {
                            p.getCartasJungla()[i] = null;
                            aux1++;
                        }
                    }else if (p.getCartasJungla()[i].getTipo().equals("Mca") && p.getCartasJungla()[i].getValor() == 3) {
                        if (aux2 < 1) {
                            p.getCartasJungla()[i] = null;
                            aux2++;
                        }
                    }else if (p.getCartasJungla()[i].getTipo().equals("Mns") && p.getCartasJungla()[i].getValor() == 1) {
                        if (aux3 < 1) {
                            p.getCartasJungla()[i] = null;
                            aux3++;
                        }
                    }else if (p.getCartasJungla()[i].getTipo().equals("Ag")) {
                        if (aux4 < 1) {
                            p.getCartasJungla()[i] = null;
                            aux4++;
                        }
                    }else if (p.getCartasJungla()[i].getTipo().equals("Tmp")) {
                        if (aux5 < 1) {
                            p.getCartasJungla()[i] = null;
                            aux5++;
                        }
                    }else if (p.getCartasJungla()[i].getTipo().equals("Ads")) {
                        if (aux6 < 1) {
                            p.getCartasJungla()[i] = null;
                            aux6++;
                        }
                    }
                }
            }

        } else if (cantJ >= 3) {
            boolean term1 = false;
            boolean term2 = false;
            for (int i = 0; i < 4; i++) {
                term1 = false;
                term2 = false;
                if (p.getJugadores()[i] != null) {
                    for (int j = 0; j < 11; j++) {
                        if (!term1) {
                            if (p.getJugadores()[i].getCartasJugador()[j] != null) {
                                Cartas nc = p.getJugadores()[i].getCartasJugador()[j];
                                if (nc.getTipo().equals("Tbr") && nc.getDerecha() == 1 && nc.getAbajo() == 1 && nc.getIzquierda() == 1 && nc.getArriba() == 1) {
                                    System.out.print("Ingresado");
                                    p.getJugadores()[i].getCartasJugador()[j] = null;
                                    term1 = true;
                                }
                            }
                        }
                        if (!term2 && cantJ == 4) {
                            Cartas nc = p.getJugadores()[i].getCartasJugador()[j];
                            if (p.getJugadores()[i].getCartasJugador()[j] != null) {
                                if (nc.getTipo().equals("Tbr") && nc.getDerecha() == 1 && nc.getAbajo() == 1 && nc.getIzquierda() == 1 && nc.getArriba() == 1) {
                                    System.out.print("Carta eliminada 2");
                                    p.getJugadores()[i].getCartasJugador()[i] = null;
                                    term2 = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        enviarPeticion("listo");
    }

    //Si se da al boton de arriba, se analizan se analiza la carta que hay arriba y se obtienen los recursos
    @FXML
    private void onActionArriba(ActionEvent event) {
        btnArriba.setDisable(true);
        vl.validarValores(p.getMatrizLogica(), p.getMatrizLogica()[p1][p2], btnNDerecha, btnAbajo, btnNIzquierda, btnArriba, txtNuecesJ1, txtAguaJ1, txtSolJ1, txtOroJ1, p1 - 1, p2, "arriba");
        actualizarPuntajes();
    }

    //Si se da al boton de abajo, se analizan se analiza la carta que hay arriba y se obtienen los recursos
    @FXML
    private void onActionAbajo(ActionEvent event) {
        btnAbajo.setDisable(true);
        vl.validarValores(p.getMatrizLogica(), p.getMatrizLogica()[p1][p2], btnNDerecha, btnAbajo, btnNIzquierda, btnArriba, txtNuecesJ1, txtAguaJ1, txtSolJ1, txtOroJ1, p1 + 1, p2, "abajo");
        actualizarPuntajes();
    }

    //Si se da al boton de arriba, se analizan se analiza la carta que hay izquierda y se obtienen los recursos
    @FXML
    private void onActionNIzquierda(ActionEvent event) {
        btnNIzquierda.setDisable(true);
        vl.validarValores(p.getMatrizLogica(), p.getMatrizLogica()[p1][p2], btnNDerecha, btnAbajo, btnNIzquierda, btnArriba, txtNuecesJ1, txtAguaJ1, txtSolJ1, txtOroJ1, p1, p2 - 1, "izquierda");
        actualizarPuntajes();
    }

    //Si se da al boton de dderecha, se analizan se analiza la carta que hay arriba y se obtienen los recursos
    @FXML
    private void onActionNDerecha(ActionEvent event) {
        btnNDerecha.setDisable(true);
        vl.validarValores(p.getMatrizLogica(), p.getMatrizLogica()[p1][p2], btnNDerecha, btnAbajo, btnNIzquierda, btnArriba, txtNuecesJ1, txtAguaJ1, txtSolJ1, txtOroJ1, p1, p2 + 1, "derecha");
        actualizarPuntajes();
    }

    //Metodo para actualizar el puntaje del jugador
    private void actualizarPuntajes() {
        for (int i = 0; i < 4; i++) {
            if (p.getJugadores()[i] != null) {
                if (p.getJugadores()[i].getNombre().equals(nombre)) {
                    p.getJugadores()[i].setNueces(Integer.parseInt(txtNuecesJ1.getText()));
                    p.getJugadores()[i].setAgua(Integer.parseInt(txtAguaJ1.getText()));
                    p.getJugadores()[i].setMonedas(Integer.parseInt(txtOroJ1.getText()));
                    p.getJugadores()[i].setFichasSol(Integer.parseInt(txtSolJ1.getText()));
                    p.setTurnoJugador(nombre);
                }
            }

        }
        enviarPeticion("actualizar puntajes");
    }

    //metodo para salirse de la partida cuando esta haya terminado
    @FXML
    private void onActionSalirGn(ActionEvent event) {
        p.setSalir(nombre);
        enviarPeticion("salir");
        FlowController.getInstance().goViewInNewStage("InicioView", stage);
    }
}
