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
    // Vitales para que el Main y los otros paneles pasen los servicios de vuelta
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

        if (usuarioService == null) {
            mostrarAlerta("Error Crítico", "El sistema no ha inicializado los servicios de base de datos.");
            return;
        }

        try {
            Usuario usuario = usuarioService.login(user, pass);

            if (usuario != null) {
                System.out.println("Acceso concedido: " + usuario.getUsername() + " (" + usuario.getRol() + ")");
                redirigirSegunRol(usuario, event);
            } else {
                mostrarAlerta("Acceso Denegado", "Usuario o contraseña incorrectos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error de Servidor", "Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    private void redirigirSegunRol(Usuario usuario, ActionEvent event) throws IOException {
        String rutaFxml = "";
        String rol = usuario.getRol().toUpperCase();

        // 1. Selección de vista según el rol de la DB
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

        // 2. REPARTO DE SERVICIOS (Inyección de dependencias)
        // Esto evita que al cerrar sesión los servicios se vuelvan null

        if (controller instanceof AdminController adminCtrl) {
            adminCtrl.setBuqueService(buqueService);
            adminCtrl.setTripulanteService(tripulanteService);
            adminCtrl.setUsuarioService(usuarioService);
        }
        else if (controller instanceof CapitanController capCtrl) {
            capCtrl.setBuqueService(buqueService);
            capCtrl.setUsuarioService(usuarioService);
            capCtrl.setUsuarioLogueado(usuario);
        }
        else if (controller instanceof TripulanteController tripuCtrl) {
            // El tripulante necesita los servicios para poder ver datos y cerrar sesión
            tripuCtrl.setBuqueService(buqueService);
            tripuCtrl.setUsuarioService(usuarioService);
            tripuCtrl.setUsuarioLogueado(usuario);
        }

        // 3. Mostrar la nueva ventana
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Navigare - " + rol);
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