package dam.code.controller;

import dam.code.models.Usuario;
import dam.code.service.BuqueService;
import dam.code.service.UsuarioService; // Importante para cerrar sesión
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class CapitanController {

    private Usuario usuarioLogueado;
    private BuqueService buqueService;
    private UsuarioService usuarioService;

    // 1. EL LOGIN LLAMA A ESTE MÉTODO: Debe existir y ser público
    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
        System.out.println("Capitán al mando: " + usuario.getUsername());
    }

    // 2. EL LOGIN LLAMA A ESTE MÉTODO TAMBIÉN
    public void setBuqueService(BuqueService service) {
        this.buqueService = service;
    }

    // 3. SETTER PARA EL USUARIO SERVICE (para poder volver al login)
    public void setUsuarioService(UsuarioService service) {
        this.usuarioService = service;
    }

    @FXML
    private void handleEditarTripulante(ActionEvent event) {
        System.out.println("Botón editar pulsado");
        // Aquí irá tu lógica futura para editar
    }

    @FXML
    private void handleEliminarTripulante(ActionEvent event) {
        System.out.println("Botón eliminar pulsado");
        // Aquí irá tu lógica futura para eliminar
    }

    @FXML
    private void handleCerrarSesion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
            Parent root = loader.load();

            LoginController loginCtrl = loader.getController();
            // Devolvemos los servicios para que el próximo login no falle
            loginCtrl.setUsuarioService(this.usuarioService);
            loginCtrl.setBuqueService(this.buqueService);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}