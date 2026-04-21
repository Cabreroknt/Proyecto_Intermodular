package dam.code.controller;

import dam.code.models.Usuario;
import dam.code.service.BuqueService;
import dam.code.service.UsuarioService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class TripulanteController {

    // Vinculamos con el Label del FXML para mostrar info personalizada
    @FXML private Label lblBienvenida;
    @FXML private Label lblDetalles;

    private Usuario usuarioLogueado;
    private UsuarioService usuarioService;
    private BuqueService buqueService;

    /**
     * Este método lo llama el LoginController al entrar.
     * Aquí recibimos quién es el tripulante y actualizamos la interfaz.
     */
    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;

        if (usuario != null) {
            // Actualizamos los textos de la vista
            if (lblBienvenida != null) {
                lblBienvenida.setText("Bienvenido, " + usuario.getUsername());
            }
            if (lblDetalles != null) {
                String buqueInfo = (usuario.getIdBuque() != null)
                        ? "Asignado al Buque ID: " + usuario.getIdBuque()
                        : "Sin buque asignado actualmente.";
                lblDetalles.setText("Rol: Tripulante | " + buqueInfo);
            }
            System.out.println("Tripulante cargado: " + usuario.getUsername());
        }
    }

    // --- MÉTODOS DE INYECCIÓN DE SERVICIOS ---

    public void setUsuarioService(UsuarioService service) {
        this.usuarioService = service;
    }

    public void setBuqueService(BuqueService service) {
        this.buqueService = service;
    }

    /**
     * Método para cerrar sesión.
     * Es vital devolver los servicios al LoginController para que el siguiente
     * inicio de sesión no dé error de "NullPointerException".
     */
    @FXML
    private void handleCerrarSesion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
            Parent root = loader.load();

            // Configuramos el controlador del Login antes de mostrarlo
            LoginController loginCtrl = loader.getController();
            loginCtrl.setUsuarioService(this.usuarioService);
            loginCtrl.setBuqueService(this.buqueService);
            // Si tienes TripulanteService en el Admin, pásalo aquí también si lo tienes guardado

            // Cambiamos la escena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Navigare - Login");
            stage.show();

            System.out.println("Sesión de tripulante cerrada correctamente.");
        } catch (IOException e) {
            System.err.println("Error al intentar volver al Login: " + e.getMessage());
            e.printStackTrace();
        }
    }
}