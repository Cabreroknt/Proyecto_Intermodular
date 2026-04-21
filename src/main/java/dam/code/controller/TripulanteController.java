package dam.code.controller;

import dam.code.models.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TripulanteController {

    @FXML private Label lblInfo;

    private Usuario usuarioLogueado;

    // El tripulante solo ve, no toca
    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
        lblInfo.setText("Bienvenido, " + usuario.getUsername() + ". Estás asignado al buque ID: " + usuario.getIdBuque());
    }
}