/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacao.util;

import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;
import javafx.util.Duration;
//import org.controlsfx.control.Notifications;

/**
 *
 * @author Edo
 */
public class Mensajes {
    public void show(AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

    public void showModal(AlertType tipo, String titulo, Window padre, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.initOwner(padre);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public Boolean showConfirmation(String titulo, Window padre, String mensaje) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.initOwner(padre);
        alert.setContentText(mensaje);
        Optional<ButtonType> result = alert.showAndWait();

        return result.get() == ButtonType.OK;
    }
    
    //Mensajes custom
    /*public void error(String titulo, String mensaje){
        Notifications notificationBuilder = Notifications.create()
                .title(titulo)
                .text(mensaje)
                .graphic(null)
                .darkStyle()
                .hideAfter(Duration.seconds(1.5))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.showError();
    }
    
    public void confirm(String titulo, String mensaje){
        Notifications notificationBuilder = Notifications.create()
                .title(titulo)
                .text(mensaje)
                .graphic(null)
                .darkStyle()
                .hideAfter(Duration.seconds(1.5))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.showConfirm();
    }
    
    public void information(String titulo, String mensaje){
        Notifications notificationBuilder = Notifications.create()
                .title(titulo)
                .text(mensaje)
                .graphic(null)
                .darkStyle()
                .hideAfter(Duration.seconds(1.5))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.showInformation();
    }
    
    public void warning(String titulo, String mensaje){
        Notifications notificationBuilder = Notifications.create()
                .title(titulo)
                .text(mensaje)
                .graphic(null)
                .darkStyle()
                .hideAfter(Duration.seconds(1.5))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.showWarning();
    }*/
}
