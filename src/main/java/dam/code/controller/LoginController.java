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

    // Métodos para recibir los servicios desde el Main
    public void setUsuarioService(UsuarioService s) { this.usuarioService = s; }
    public void setBuqueService(BuqueService s) { this.buqueService = s; }
    public void setTripulanteService(TripulanteService s) { this.tripulanteService = s; }

    @FXML
    private void handleLogin(ActionEvent event) {
        String user = txtUsuario.getText().trim();
        String pass = txtPassword.getText().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Introduce tus credenciales.");
            return;
        }

        try {
            Usuario usuario = usuarioService.login(user, pass);

            if (usuario != null) {
                System.out.println("Login ok: " + usuario.getRol());
                redirigirSegunRol(usuario, event);
            } else {
                mostrarAlerta("Error", "Usuario o contraseña incorrectos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error de conexión", "No se pudo conectar con la base de datos.");
        }
    }

    private void redirigirSegunRol(Usuario usuario, ActionEvent event) throws IOException {
        String rutaFxml = switch (usuario.getRol().toUpperCase()) {
            case "ADMIN" -> "/view/AdminView.fxml";
            case "CAPITAN" -> "/view/CapitanView.fxml";
            case "TRIPULANTE" -> "/view/TripulanteView.fxml";
            default -> "";
        };

        FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFxml));
        Parent root = loader.load();
        Object controller = loader.getController();

        // --- INYECCIÓN CRÍTICA PARA CADA ROL ---
        if (controller instanceof AdminController c) {
            c.setBuqueService(buqueService);
            c.setTripulanteService(tripulanteService);
            c.setUsuarioService(usuarioService); // Para que el admin pueda volver al login
        }
        else if (controller instanceof CapitanController c) {
            c.setBuqueService(buqueService);
            c.setUsuarioLogueado(usuario); // El capitán necesita saber su ID de buque
            // Si tu CapitanController también tiene setUsuarioService, añádelo aquí
        }
        else if (controller instanceof TripulanteController c) {
            c.setUsuarioLogueado(usuario);
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Navigare - " + usuario.getRol());
        stage.show();
    }

    private void mostrarAlerta(String t, String m) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(t); a.setHeaderText(null); a.setContentText(m); a.showAndWait();
    }
}