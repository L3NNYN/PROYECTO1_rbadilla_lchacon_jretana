/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacao.functions;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Pipo
 */
public class AgregarImagen {
    
    
    //Metodo para agregar una imagen a cualquier componente de la mesa de juego
    public void agregarImagen(int tipo, GridPane gridpane, Cartas[] cartas, VBox[] vbox, VBox[][] mVBox, Cartas[][] cartasM, Cartas carta, int f, int c){
        
        //Aqui se agregan imagenes a la mesa de juego
        if (tipo == 1) {

            if (vbox[c].getChildren().size() > 0) {
                vbox[c].getChildren().remove(0);
                borrarImagen(f, c, gridpane);
            }
            Image im = new Image("/cacao/resources/Cartas/" + carta.getTipo() + carta.getDerecha() + carta.getAbajo() + carta.getIzquierda() + carta.getArriba() + ".png", 73, 73, false, true);
            ImageView im2 = new ImageView(im);
            vbox[c].getChildren().add(im2);
            gridpane.add(vbox[c], c, f);
        }

        //Aqui se agregan imagenes a la mesa de juego con tamano mas reducido
        if (tipo == 2) {
            if (mVBox[f][c].getChildren().size() > 0) {
                mVBox[f][c].getChildren().remove(0);
                borrarImagen(f, c, gridpane);
            }
            Image im = new Image("/cacao/resources/Cartas/" + carta.getTipo() + carta.getDerecha() + carta.getAbajo() + carta.getIzquierda() + carta.getArriba() + ".png", 89, 89, false, true);
            ImageView im2 = new ImageView(im);
            im2.setRotate(carta.getGrados());
            mVBox[f][c].getChildren().add(im2);
            gridpane.add(mVBox[f][c], c, f);
        }

        //Agregar imagenes de tipo templo a la mesa
        if (tipo == 3) {

            if (vbox[f].getChildren().size() > 0) {
                vbox[f].getChildren().remove(0);
                borrarImagen(f, c, gridpane);
            }

            Image im;
            if (carta.getTipo().equals("Tmp")) {
                im = new Image("/cacao/resources/Cartas/" + carta.getTipo() + carta.getValor1Templo() + carta.getValor2Templo() + ".png", 89, 89, false, true);
            } else {
                im = new Image("/cacao/resources/Cartas/" + carta.getTipo() + carta.getValor() + ".png", 89, 89, false, true);
            }
            ImageView im2 = new ImageView(im);
            vbox[f].getChildren().add(im2);
            gridpane.add(vbox[f], c, f);
        }

        //Agregar imagen de tipo templo
        if (tipo == 4) {
            if (mVBox[f][c].getChildren().size() > 0) {
                mVBox[f][c].getChildren().remove(0);
                borrarImagen(f, c, gridpane);
            }
            Image im;

            if (carta.getTipo().equals("Tmp")) {
                im = new Image("/cacao/resources/Cartas/" + carta.getTipo() + carta.getValor1Templo() + carta.getValor2Templo() + ".png", 89, 89, false, true);
            } else {
                im = new Image("/cacao/resources/Cartas/" + carta.getTipo() + carta.getValor() + ".png", 89, 89, false, true);
            }

            ImageView im2 = new ImageView(im);

            im2.setRotate(carta.getGrados());
            mVBox[f][c].getChildren().add(im2);
            gridpane.add(mVBox[f][c], c, f);
        }
    }
    
    //Metodo para borrar una imagen
    public void borrarImagen(final int row, final int column, GridPane gridPane) {

        ObservableList<Node> childrens = gridPane.getChildren();
        for (Node node : childrens) {
            if (node instanceof VBox && gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                gridPane.getChildren().remove(node);
                break;
            }
        }
    }
    
}
