/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Juego;

import java.io.Serializable;

/**
 *
 * @author Lennyn
 */
public class Cartas{

   //Atributos de tipo carta
    
   private String tipo;
   
   private String nombre;
    
   private String color;
   
   private int derecha;
   
   private int abajo;
   
   private int izquierda;
   
   private int arriba;

   private int derechaG;
   
   private int abajoG;
   
   private int izquierdaG;
   
   private int arribaG;
   
   private int valor;
   
   private int valor1Templo;
   
   private int valor2Templo;
   
   private int grados;

   //Losetas jungla
   public Cartas(String tipo, String nombre, int derecha, int abajo, int izquierda,
   int arriba,String color,int grados){
      this.tipo = tipo;
      this.nombre = nombre;
      this.derecha = derecha;
      this.abajo = abajo;
      this.izquierda = izquierda;
      this.arriba = arriba;
      this.grados = grados;
      this.derechaG = derecha;
      this.abajoG = abajo;
      this.izquierdaG = izquierda;
      this.arribaG = arriba;
   }
   
   //Plantaciones, mercado, minas
   public Cartas(String tipo, String nombre, int valor, int grados){
       this.tipo = tipo;
       this.nombre = nombre;
       this.valor = valor;      
       this.grados = grados;
   }
   
   //Templos 
   public Cartas(String tipo, String nombre, int valorT1, int valorT2, int grados){
       this.tipo = tipo;
       this.nombre = nombre;
       this.valor1Templo = valorT1;
       this.valor2Templo = valorT2;
       this.grados = grados;
   }
   
   //Agua
   public Cartas(String tipo, String nombre, int grados){
     this.tipo = tipo;
     this.nombre = nombre;
     this.grados = grados;
   }
   
   public Cartas(){
       
   }
   
   //Settres y getters de los artibutos de tipo carta anteriores
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getDerecha() {
        return derecha;
    }

    public void setDerecha(int derecha) {
        this.derecha = derecha;
    }

    public int getAbajo() {
        return abajo;
    }

    public void setAbajo(int abajo) {
        this.abajo = abajo;
    }

    public int getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(int izquierda) {
        this.izquierda = izquierda;
    }

    public int getArriba() {
        return arriba;
    }

    public void setArriba(int arriba) {
        this.arriba = arriba;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getValor1Templo() {
        return valor1Templo;
    }

    public void setValor1Templo(int valor1Templo) {
        this.valor1Templo = valor1Templo;
    }

    public int getValor2Templo() {
        return valor2Templo;
    }

    public void setValor2Templo(int valor2Templo) {
        this.valor2Templo = valor2Templo;
    }
   
    public int getGrados() {
        return grados;
    }

    public void setGrados(int grados) {
        this.grados = grados;
    }
    
    public int getDerechaG() {
        return derechaG;
    }

    public void setDerechaG(int derechaG) {
        this.derechaG = derechaG;
    }

    public int getAbajoG() {
        return abajoG;
    }

    public void setAbajoG(int abajoG) {
        this.abajoG = abajoG;
    }

    public int getIzquierdaG() {
        return izquierdaG;
    }

    public void setIzquierdaG(int izquierdaG) {
        this.izquierdaG = izquierdaG;
    }

    public int getArribaG() {
        return arribaG;
    }

    public void setArribaG(int arribaG) {
        this.arribaG = arribaG;
    }                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
}

