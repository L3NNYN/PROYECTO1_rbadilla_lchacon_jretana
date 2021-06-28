package server;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Juego.Partida;
import server.util.FlowController;

/**
 *
 * @author Pipo
 */
public class Respuesta extends Observable implements Runnable {

    private Socket sc;

    private DataInputStream in;

    private String recivido;

    //Constructor de Server
    public Server sv = new Server();

    public boolean ingresar = true;

    public int borrar;

    public int rsp;

    public boolean cerrarHilo = false;

    //Constructor de Respuesta
    public Respuesta(Socket socket, int rsp) throws IOException {
        this.sc = socket;
        this.rsp = rsp;
    }

    /*Se utiliza el Runnable para recivir peticiones de los clientes, el Runnable
    estara ejecutanse continuamente reciviendo peticiones.
     */
    @Override
    public void run() {

        try {
            //System.out.println("recibir Mensaje");

            //Se crea objeto de tipo Data Input para recivir peticiones
            in = new DataInputStream(sc.getInputStream());

            //While para recivir peticiones
            while (!cerrarHilo) {
                String me = in.readUTF();
                Gson gs = new Gson();
                Partida jg = gs.fromJson(me, Partida.class);

                if (jg.getPeticion().equals("salir")) {
                    for (int i = 0; i < 4; i++) {
                        if (FlowController.getInstance().partida.getJugadores()[i] != null) {
                            if (FlowController.getInstance().partida.getJugadores()[i].getNombre().equals(jg.getSalir())) {
                                FlowController.getInstance().partida.getJugadores()[i] = null;
                                FlowController.getInstance().partida.setPeticion("salir");
                                System.out.print(jg.getSalir() + " a avandonado la partida");
                                borrar = i;
                                ingresar = false;
                                cerrarHilo = true;
                                Gson g = new Gson();
                                String r = g.toJson(FlowController.getInstance().partida);
                                enviarInfo(r);
                                sc.close();
                            }
                        }

                    }
                } else {
                    revisarPeticion(jg);
                }

            }

        } catch (IOException ex) {
            Logger.getLogger(Respuesta.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void revisarPeticion(Partida partida) throws IOException {

        //Se recive una peticin para registrar un jugador, este se agrega en una lista de jugadores y se retorna a todos los clientes conectados
        //para que estos lo tengan
        if (partida.getPeticion().equals("registrar jugador")) {

            FlowController.getInstance().partida.agregarJugador(partida.getJugadores()[0]);
            FlowController.getInstance().partida.setPeticion("Jugadores");
            FlowController.getInstance().partida.setTurnoJugador(FlowController.getInstance().partida.getJugadores()[0].getNombre());
            System.out.print(partida.getJugadores()[0].getNombre() + " se ha conectado a la partida\n");

        //Se recive una peticion de pasar el turno del jugador, entonces aqui se analiza quien es el jugador actual y se pasa al siguiente
        } else if (partida.getPeticion().equals("pasar turno")) {
            boolean escogido = false;
            for (int i = 0; i < cantidad(); i++) {
                if (!escogido) {
                    if (FlowController.getInstance().partida.getJugadores()[i].getNombre().equals(FlowController.getInstance().partida.getTurnoJugador())) {
                        if (i < cantidad() - 1) {
                            FlowController.getInstance().partida.setTurnoJugador(FlowController.getInstance().partida.getJugadores()[i + 1].getNombre());
                            System.out.print("Turno nuevo: " + FlowController.getInstance().partida.getTurnoJugador());
                            escogido = true;
                        } else {
                            FlowController.getInstance().partida.setTurnoJugador(partida.getJugadores()[0].getNombre());
                        }
                    }
                }
            }
            FlowController.getInstance().partida.setPeticion("pasar turno");

            //Se envia la partida cargada con todos los objetos de los jugadores a todos los clientes, esto para corroborar que no haya 
        } else if (partida.getPeticion().equals("actualizar cartas jugadores")) {

            for (int i = 0; i < 4; i++) {
                if (FlowController.getInstance().partida.getJugadores()[i] != null) {
                    FlowController.getInstance().partida.getJugadores()[i].setCartasJugador(partida.getJugadores()[i].getCartasJugador());
                }
            }
            int tm = 0;
            for (int i = 0; i < 11; i++) {
                if (FlowController.getInstance().partida.getJugadores()[0].getCartasJugador()[i] != null) {
                    tm++;
                }
            }
            FlowController.getInstance().partida.setPeticion("actualizar cartas jugadores");

            //Se recive la peticion de colocar la carta de un jugador, por lo tanto esta carta es recivida por el servidor
            //y este actualiza a los demas clientes con esta carta
        } else if (partida.getPeticion().equals("colocar carta jugador")) {

            FlowController.getInstance().partida.setMatrizLogica(partida.getMatrizLogica());
            FlowController.getInstance().partida.setCartaJugada(partida.getCartaJugada(), partida.getX(), partida.getY(), partida.getMazo());
            FlowController.getInstance().partida.setPeticion("colocar carta jugador");

          //Se recive la peticion de colocar la carta de jungla, por lo tanto esta carta es recivida por el servidor
          //y este actualiza a los demas clientes con esta carta
        } else if (partida.getPeticion().equals("colocar carta jungla")) {

            FlowController.getInstance().partida.setMatrizLogica(partida.getMatrizLogica());
            FlowController.getInstance().partida.setCartasJungla(partida.getCartasJungla());
            FlowController.getInstance().partida.setCartaJugada(partida.getCartaJugada(), partida.getX(), partida.getY(), partida.getMazo());
            FlowController.getInstance().partida.setPeticion("colocar carta jungla");

            //Recive la peticion de salir, por lo tanto se cierra su respectivo socket
        } else if (partida.getPeticion().equals("salir")) {
            for (int i = 0; i < 4; i++) {
                if (FlowController.getInstance().partida.getJugadores()[i] != null) {
                    if (FlowController.getInstance().partida.getJugadores()[i].getNombre().equals(partida.getSalir())) {
                        FlowController.getInstance().partida.getJugadores()[i] = null;
                        FlowController.getInstance().partida.setPeticion("salir");
                        System.out.print(i);
                        borrar = i;
                        //ingresar = false;
                        cerrarHilo = true;
                    }
                }

            }
            
            //Se recive la peticion de verificar un ganador, por lo tanto se determina cual jugado tiene mas oro
            //y se envia a todos los clientes
        } else if (partida.getPeticion().equals("ganador")) {

            String ganador = FlowController.getInstance().partida.getJugadores()[0].getNombre();

            for (int i = 0; i < 4; i++) {

                if (FlowController.getInstance().partida.getJugadores()[i] != null && i < 4 && FlowController.getInstance().partida.getJugadores()[i + 1] != null) {
                    if (FlowController.getInstance().partida.getJugadores()[i].getMonedas() < FlowController.getInstance().partida.getJugadores()[i + 1].getMonedas()) {
                        ganador = FlowController.getInstance().partida.getJugadores()[i + 1].getNombre();
                        FlowController.getInstance().partida.setGanador(ganador);
                    } else {
                        FlowController.getInstance().partida.setGanador(ganador);
                    }
                }
            }

            FlowController.getInstance().partida.setPeticion("ganador");

            //Se recive la peticion de actualizar las cartas de jungla, por lo que se actualizan los clientes con estas mismas
        } else if (partida.getPeticion().equals("actualizar jungla")) {

            FlowController.getInstance().partida.setPeticion("actualizar jungla");

            //Se recive la peticion de actualizar las cartas de jungla, por lo que se actualizan los clientes con estos mismos
        } else if (partida.getPeticion().equals("actualizar jugadores")) {

            FlowController.getInstance().partida.setPeticion("actualizar jugadores");

            //Se recive la peticion de actualizar los puntajes de los jugadores, por lo que se actualizan los clientes con estos mismos
            //y se envian a los clientes
        } else if (partida.getPeticion().equals("actualizar puntajes")) {
            for (int i = 0; i < 4; i++) {
                if (FlowController.getInstance().partida.getJugadores()[i] != null) {
                    if (FlowController.getInstance().partida.getJugadores()[i].getNombre().equals(partida.getTurnoJugador())) {
                        System.out.print("Nueces" + partida.getJugadores()[i].getNueces());
                        FlowController.getInstance().partida.getJugadores()[i].setFichasSol(partida.getJugadores()[i].getFichasSol());
                        FlowController.getInstance().partida.getJugadores()[i].setAgua(partida.getJugadores()[i].getAgua());
                        FlowController.getInstance().partida.getJugadores()[i].setNueces(partida.getJugadores()[i].getNueces());
                        FlowController.getInstance().partida.getJugadores()[i].setMonedas(partida.getJugadores()[i].getMonedas());
                    }
                }
            }
            FlowController.getInstance().partida.setPeticion("actualizar puntaje");

            //Se recive la peticion de estar "listo" en la sala de juego
        } else if (partida.getPeticion().equals("listo")) {
            for (int i = 0; i < 4; i++) {
                if (FlowController.getInstance().partida.getJugadores()[i] != null) {
                    if (FlowController.getInstance().partida.getJugadores()[i].getNombre().equals(partida.getListo())) {
                        FlowController.getInstance().partida.getJugadores()[i].setListo("listo");
                        FlowController.getInstance().partida.setPeticion("Jugadores");
                    }
                }
            }
        }

        Gson g = new Gson();
        String r = g.toJson(FlowController.getInstance().partida);
        enviarInfo(r);

    }

    /*Este metodo notificara todos los clientes conectados*/
    public void enviarInfo(String peticion) throws IOException {

        //Server sv = new Server();
        for (int i = 0; i < 4; i++) {

            try {
                if (sv.getClientes()[i] != null) {
                    DataOutputStream dos = new DataOutputStream(sv.getClientes()[i].getOutputStream());
                    dos.writeUTF(peticion);
                }
                //recivido = null;
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    //Metodo para retornar la cantidad de jugadores
    private int cantidad() {

        int num = 0;

        for (int i = 0; i < FlowController.getInstance().partida.getJugadores().length; i++) {
            if (FlowController.getInstance().partida.getJugadores()[i] != null) {
                num++;
            }
        }
        return num;
    }

}
