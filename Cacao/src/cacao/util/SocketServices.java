/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacao.util;

/**
 *
 * @author Josue
 */
import cacao.functions.Jugador;
import cacao.functions.Partida;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketServices extends Observable implements Runnable {
    
    //Atributos para la clase Socket
    
    private int puerto;
    
    private String HOST;
    
    public static Socket sc;
    
    private ArrayList<Socket> clientes;
    
    private Partida j = new Partida();
    
    public boolean ev = false;
    
    public Boolean permitir = false;
    
    public static Boolean cerrar = false;
    
    public SocketServices() {
        
    }
    
    //Constructor de SocketService
    public SocketServices(int puerto, String ip) throws IOException {
        this.puerto = puerto;
        this.HOST = ip;
    }
    
    //Metodo para registrar un jugador
    public void registrar(String host, int puerto) throws IOException {
        //Creo el socket para conectarme con el cliente
        sc = new Socket(host, 5000);
    }
    
    public Partida getRespuesta() {
        return j;
    }
   
    //Este metodo es un hilo que se estara ejecutando continuamente recivendo peticiones del servidor
    @Override
    public void run() {
        DataInputStream dis;
        DataOutputStream out;
        try {
            
            dis = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());
            Partida mensaje;
            double valor;
            while (!cerrar) {
                
                if (!cerrar) {
                    String sms = dis.readUTF();
                    
                    Gson gs = new Gson();
                    Partida jg = gs.fromJson(sms, Partida.class);
                    
                    ClassController.getInstance().partida.setJugadores(jg.getJugadores());
                    
                    if (jg.getPeticion().equals("salir")) {
                        cerrar = true;
                        //sc.close();
                    }
                    
                    if (jg.getPeticion().equals("lleno")) {
                        ClassController.getInstance().partida.setPeticion("lleno");
                    }
                    this.setChanged();
                    this.notifyObservers(sms);
                    this.clearChanged();
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(SocketServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Metodo para recivir datos
    public void enviarDatos(String enviar) throws IOException {
        DataOutputStream dos = new DataOutputStream(sc.getOutputStream());
        //System.out.print(mensaje);
        dos.writeUTF(enviar);
        
    }
    
    public Boolean getPermitir() {
        return permitir;
    }
    
    public void setPermitir(Boolean permitir) {
        this.permitir = permitir;
    }
    
    public Socket getSc() {
        return sc;
    }
    
    public void setSc(Socket sc) {
        SocketServices.sc = sc;
    }
    
    public void cerrarConexion() throws IOException {
        enviarDatos("salir");
        cerrar = true;
        //sc.close();
        
    }
}
