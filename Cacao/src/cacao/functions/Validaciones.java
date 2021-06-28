/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacao.functions;

import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 *
 * @author Lennyn
 */
public class Validaciones {

    //Metodo para analizar las cartas del jugador hubicadas al lado izquierdo en la mesa de juego y si detecta que hay una en blanco
    //agrega su respectiva carta y la grafica
    public void cartasUsables(Cartas[] cartasJungla, Cartas[] logicas, Partida partida, int jugadorActual) throws IOException {
        int j = 0;
        boolean b = false;
        for (int i = 0; i < cartasJungla.length; i++) {
            b = false;
            while (b == false) {
                if (j < 3) {
                    if (logicas[j] == null) {
                        if (i < 11) {
                            if (partida.getJugadores()[jugadorActual].getCartasJugador()[i] != null) {
                                logicas[j] = partida.getJugadores()[jugadorActual].getCartasJugador()[i];
                                partida.getJugadores()[jugadorActual].borrarCarta(i);
                                b = true;
                                j++;
                            } else {
                                b = true;
                            }
                        } else {
                            break;
                        }
                    } else {
                        j++;
                    }
                } else {
                    break;
                }
            }
        }

    }

    //Metodo para analizar las cartas de jungla hubicadas al lado derecho en la mesa de juego y si detecta que hayn una en blanco
    //agrega su respectiva carta y la grafica
    public void llenarCartasJungla(Cartas[] cartasJungla, Cartas[] logicasSelva, Partida partida) throws IOException {
        int j = 0;
        boolean b = false;
        for (int i = 0; i < cartasJungla.length; i++) {
            b = false;
            while (b == false) {
                if (j < 2) {
                    if (logicasSelva[j] == null) {
                        //System.out.print("Si hay nulas");
                        if (i < 28) {

                            if (cartasJungla[i] != null) {
                                logicasSelva[j] = cartasJungla[i];
                                partida.borrarCarta(i);
                                b = true;
                                j++;
                            } else {
                                b = true;
                            }
                        } else {
                            break;
                        }
                    } else {
                        j++;
                    }
                } else {
                    break;
                }
            }
        }

    }

    //Metodo que analiza si una carta se puede colocar en un respectivo lugar y retorna un boolano (true si se puede y false si no)
    public Boolean validarCartaJugador(Cartas[][] m, String tipo, int x, int y) {
        boolean permitir = true;

        ArrayList<Cartas> ady = new ArrayList<Cartas>();
        ady = getAdyacentes(m, x, y);

        int i = 0;
        boolean s = false;
        while (permitir && i < 9) {
            if (i == 0 || i == 2 || i == 6 || i == 8) { //Si la carta es diagonal
                if (ady.get(i) != null) {
                    s = true;
                    if (tipo.equals("Tbr")) {
                        if (!ady.get(i).getTipo().equals(tipo)) { //Si existe, valida que no sea diferente del tipo
                            permitir = false;
                        }
                    } else if (!ady.get(i).getNombre().equals(tipo)) { //Si existe, valida que no sea diferente del tipo
                        permitir = false;
                    }
                }
            } else if (ady.get(i) != null) { //Carta en vertical/horizontal
                s = true;
                if (tipo.equals("Tbr")) {
                    if (ady.get(i).getTipo().equals(tipo)) { //Si existe, valida que no sea diferente del tipo
                        permitir = false;
                    }
                } else if (ady.get(i).getNombre().equals(tipo)) { //Si existe, valida que no sea igual del tipo 
                    permitir = false;
                }
            }
            i++;
        }
        if (s && permitir) {
            return true;
        }
        return false;
    }

    public ArrayList<Cartas> getAdyacentes(Cartas[][] m, int x, int y) {
        //Se obtienen las cartas adyacentes
        ArrayList<Cartas> ady = new ArrayList<Cartas>();
        ady.add(m[x - 1][y - 1]);
        ady.add(m[x - 1][y]);
        ady.add(m[x - 1][y + 1]);
        ady.add(m[x][y - 1]);
        ady.add(null); //Posicion del centro
        ady.add(m[x][y + 1]);
        ady.add(m[x + 1][y - 1]);
        ady.add(m[x + 1][y]);
        ady.add(m[x + 1][y + 1]);

        return ady;
    }

    //Metodo para validar las cartas hubicadas a la derecha, izquierda, arriba o abajo de la carta actual, esto para poder sacar los valores de estas y ganar recursos
    public void validarValores(Cartas[][] matriz, Cartas carta, Button derecha, Button abajo, Button izquierda, Button arriba, Text nueces, Text agua, Text soles, Text dinero, int x, int y, String lado) {

        //Izquierda
        if (matriz[x][y] != null) {
            if (matriz[x][y].getTipo().equals("Pla")) {

                int suma = Integer.parseInt(nueces.getText());
                suma += getValorJungla(matriz[x][y]) * getLadoCarta(carta, lado);
                System.out.print("Suma: " + suma);
                if (suma > 5) {
                    while (suma > 5) {
                        suma--;
                    }
                    nueces.setText(String.valueOf(suma));
                } else {
                    nueces.setText(String.valueOf(suma));
                }

            } else if (matriz[x][y].getTipo().equals("Mca")) {

                int ns = Integer.parseInt(nueces.getText());
                int dnr = Integer.parseInt(dinero.getText());
                int ctd = getLadoCarta(carta, lado);
                if (ns < getLadoCarta(carta, lado)) {
                    int aux = ns;
                    ns -= aux;
                    dnr += aux * getValorJungla(matriz[x][y]);
                    nueces.setText(String.valueOf(ns));
                    dinero.setText(String.valueOf(dnr));
                } else {
                    ns -= ctd;
                    dnr += ctd * getValorJungla(matriz[x][y]);
                    nueces.setText(String.valueOf(ns));
                    dinero.setText(String.valueOf(dnr));
                }
            } else if (matriz[x][y].getTipo().equals("Ag")) {

                String ag = agua.getText();

                ArrayList<String> analisis = new ArrayList<String>();
                analisis.add("-10");
                analisis.add("-4");
                analisis.add("-1");
                analisis.add("0");
                analisis.add("2");
                analisis.add("4");
                analisis.add("7");
                analisis.add("11");
                analisis.add("16");

                int num = 0;

                for (int i = 0; i < analisis.size(); i++) {
                    if (agua.getText().equals(analisis.get(i))) {
                        num = i;
                    }
                }

                int aux = 0;
                while (aux < getLadoCarta(carta, lado)) {
                    num++;
                    if (Integer.parseInt(ag) < 16) {
                        ag = analisis.get(num);
                    }
                    aux++;
                }
                agua.setText(ag);

            } else if (matriz[x][y].getTipo().equals("Mns")) {
                int dinr = Integer.parseInt(dinero.getText());
                dinr += getLadoCarta(carta, lado) * getValorJungla(matriz[x][y]);
                dinero.setText(String.valueOf(dinr));

            } else if (matriz[x][y].getTipo().equals("Ads")) {
                int cantSol = Integer.parseInt(soles.getText());
                if (cantSol < 3) {
                    cantSol += getLadoCarta(carta, lado);
                    soles.setText(String.valueOf(cantSol));
                    if (cantSol > 3) {
                        while (cantSol > 3) {
                            cantSol--;
                        }
                        soles.setText(String.valueOf(cantSol));
                    }
                } else {

                }
            }
        }

    }

    //Metodo para ver el valor de una loseta de jungla
    private int getValorJungla(Cartas carta) {
        int valor = carta.getValor();
        return valor;
    }

    //Metodo para saber el valor del lado de una carta
    private int getLadoCarta(Cartas carta, String lado) {
        int valor = 0;
        if (lado.equals("derecha")) {
            valor = carta.getDerechaG();
        } else if (lado.equals("abajo")) {
            valor = carta.getAbajoG();
        } else if (lado.equals("izquierda")) {
            valor = carta.getIzquierdaG();
        } else if (lado.equals("arriba")) {
            valor = carta.getArribaG();
        }
        return valor;
    }

}
