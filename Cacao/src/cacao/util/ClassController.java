/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacao.util;

/**
 *
 * @author Pipo
 */

import cacao.functions.Partida;
import java.net.Socket;



public class ClassController {

   private static ClassController INSTANCE = null;
    public Partida partida = new Partida();
    public Socket sc = null;

    private static void createInstance() {
        if (INSTANCE == null) {
            synchronized (ClassController.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ClassController();
                }
            }
        }
    }

    public static ClassController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }
    
    

}