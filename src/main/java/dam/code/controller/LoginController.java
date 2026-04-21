package dam.code.controller;

import dam.code.models.Usuario;
import dam.code.service.BuqueService;
import dam.code.service.TripulanteService;
import dam.code.service.UsuarioService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;

    private UsuarioService usuarioService;
    private BuqueService buqueService;
    private TripulanteService tripulanteService;

    // --- MÉTODOS DE INYECCIÓN ---
    public void setUsuarioService(UsuarioService service) { this.usuarioService = service; }
    public void setBuqueService(BuqueService service) { this.buqueService = service; }
    public void setTripulanteService(TripulanteService service) { this.tripulanteService = service; }

    @FXML
    private void handleLogin(ActionEvent event) {
        String user = txtUsuario.getText().trim();
        String pass = txtPassword.getText().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor, introduce tus credenciales.");
            return;
        }

        try {
            // Intentamos el login a través del servicio
            Usuario usuario = usuarioService.login(user, pass);

            if (usuario != null) {
                System.out.println("Login correcto: " + usuario.getUsername() + " [Rol: " + usuario.getRol() + "]");
                redirigirSegunRol(usuario, event);
            } else {
                mostrarAlerta("Acceso Denegado", "Usuario o contraseña incorrectos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error de Servidor", "No se pudo conectar: " + e.getMessage());
        }
    }

    private void redirigirSegunRol(Usuario usuario, ActionEvent event) throws IOException {
        String rutaFxml = "";

        // Normalizamos el rol a mayúsculas para evitar errores de escritura
        String rol = usuario.getRol().toUpperCase();

        switch (rol) {
            case "ADMIN":
                rutaFxml = "/view/AdminView.fxml";
                break;
            case "CAPITAN":
                rutaFxml = "/view/CapitanView.fxml";
                break;
            case "TRIPULANTE":
                rutaFxml = "/view/TripulanteView.fxml";
                break;
            default:
                mostrarAlerta("Error de Configuración", "El rol '" + rol + "' no tiene una vista asignada.");
                return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFxml));
        Parent root = loader.load();
        Object controller = loader.getController();

        // --- INYECCIÓN DINÁMICA DE SERVICIOS Y DATOS ---

        if (controller instanceof AdminController adminCtrl) {
            adminCtrl.setBuqueService(buqueService);
            adminCtrl.setTripulanteService(tripulanteService);
            adminCtrl.setUsuarioService(usuarioService);
        }
        else if (controller instanceof CapitanController capCtrl) {
            // Pasamos el servicio de buques y el usuario logueado
            capCtrl.setBuqueService(buqueService);
            capCtrl.setUsuarioService(usuarioService);
            capCtrl.setUsuarioLogueado(usuario);
        }
        else if (controller instanceof TripulanteController tripuCtrl) {
            tripuCtrl.setUsuarioLogueado(usuario);
        }

        // Configurar y mostrar la nueva escena
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Navigare - Panel de " + rol);
        stage.centerOnScreen();
        stage.show();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}