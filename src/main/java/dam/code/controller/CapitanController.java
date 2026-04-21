package dam.code.controller;

import dam.code.models.Usuario;
import dam.code.service.BuqueService;
import javafx.fxml.FXML;

public class CapitanController {

    private Usuario usuarioLogueado;
    private BuqueService buqueService;

    // Este es el método que te falta
    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
        System.out.println("Capitán al mando: " + usuario.getUsername() + " de buque ID: " + usuario.getIdBuque());

        // Aquí podrías llamar a un método para cargar solo sus datos
        // cargarDatosPropios();
    }

    public void setBuqueService(BuqueService service) {
        this.buqueService = service;
    }

    @FXML
    public void handleCerrarSesion() {
        // Lógica para volver al login si quieres
    }
}