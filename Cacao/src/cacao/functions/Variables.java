/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacao.functions;

/**
 *
 * @author Pipo
 */
public class Variables {
    
    ///Variables necesarias para la mesa de juego
    
    public static boolean cartaTrabajador;

    public static boolean cartaTablero;
    
    public static boolean cartaJungla;
    
    public static boolean num;
    
    public static boolean llenarJungla = false;
    
    public static boolean comprobacion = false;

    public static int cX = 0;
    
    public static int cY = 0;
  
    public Variables() {

    }

    //Setters y getter de los atributos anteriores
    
    public boolean getCartaTrabajador() {
        return cartaTrabajador;
    }

    public void setCartaTrabajador(boolean cartaTrabajador) {
        Variables.cartaTrabajador = cartaTrabajador;
    }

    public boolean getCartaTablero() {
        return cartaTablero;
    }

    public void setCartaTablero(boolean cartaTablero) {
        Variables.cartaTablero = cartaTablero;
    }
    
    public boolean getCartaJungla() {
        return cartaJungla;
    }

    public void setCartaJungla(boolean cartaJungla) {
        Variables.cartaJungla = cartaJungla;
    }
    
    public boolean getNum() {
        return num;
    }

    public void setNum(boolean num) {
        Variables.num = num;
    }
    
    public boolean getLlenarJungla() {
        return llenarJungla;
    }

    public void setLlenarJungla(boolean llenarJungla) {
        Variables.llenarJungla = llenarJungla;
    }
    
    public boolean getComprobacion() {
        return comprobacion;
    }

    public void setComprobacion(boolean comprobacion) {
        Variables.comprobacion = comprobacion;
    }
    
    public int getcX() {
        return cX;
    }

    public void setcX(int cX) {
        Variables.cX = cX;
    }

    public int getcY() {
        return cY;
    }

    public void setcY(int cY) {
        Variables.cY = cY;
    }
    

}
