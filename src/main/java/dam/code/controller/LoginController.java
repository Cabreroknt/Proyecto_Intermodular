package dam.code.controller;

import dam.code.models.Usuario;
import dam.code.service.BuqueService;
import dam.code.service.TripulanteService;
import dam.code.service.UsuarioService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    // --- MÉTODOS PARA INYECCIÓN DE SERVICIOS (Desde el Main) ---

    public void setUsuarioService(UsuarioService service) { this.usuarioService = service; }
    public void setBuqueService(BuqueService service) { this.buqueService = service; }
    public void setTripulanteService(TripulanteService service) { this.tripulanteService = service; }

    /**
     * Acción del botón "Entrar" del formulario de login
     */
    @FXML
    private void handleLogin() {
        String user = txtUsuario.getText().trim();
        String pass = txtPassword.getText().trim();

        // Validación simple de campos
        if (user.isEmpty() || pass.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor, introduce usuario y contraseña.");
            return;
        }

        try {
            // 1. Consultar a la base de datos a través del servicio
            Usuario usuarioLogueado = usuarioService.login(user, pass);

            if (usuarioLogueado != null) {
                // 2. Si las credenciales son correctas, redirigir según su ROL
                redirigirSegunRol(usuarioLogueado);
            } else {
                mostrarAlerta("Acceso denegado", "Usuario o contraseña incorrectos.");
            }
        } catch (Exception e) {
            mostrarAlerta("Error de conexión", "No se pudo conectar con la base de datos.");
            e.printStackTrace();
        }
    }

    /**
     * Gestiona el cambio de ventana y la inyección de datos según la jerarquía
     */
    private void redirigirSegunRol(Usuario usuario) throws IOException {
        String fxmlDoc = "";

        // Determinamos qué vista cargar basándonos en el rol de la DB
        switch (usuario.getRol().toUpperCase()) {
            case "ADMIN":
                fxmlDoc = "/view/AdminView.fxml";
                break;
            case "CAPITAN":
                fxmlDoc = "/view/CapitanView.fxml";
                break;
            case "TRIPULANTE":
                fxmlDoc = "/view/TripulanteView.fxml";
                break;
            default:
                mostrarAlerta("Error de Rol", "El usuario no tiene un rol válido asignado.");
                return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlDoc));
        Parent root = loader.load();

        // --- INYECCIÓN DINÁMICA SEGÚN EL CONTROLADOR DESTINO ---

        if (usuario.getRol().equals("ADMIN")) {
            AdminController adminCtrl = loader.getController();
            adminCtrl.setBuqueService(buqueService);
            adminCtrl.setTripulanteService(tripulanteService);
            // El admin no suele necesitar su propio objeto usuario porque manda en todo
        }
        else if (usuario.getRol().equals("CAPITAN")) {
            CapitanController capCtrl = loader.getController();
            capCtrl.setBuqueService(buqueService);
            // PASAMOS EL USUARIO: El capitán lo necesita para saber su id_buque
            capCtrl.setUsuarioLogueado(usuario);
        }
        else if (usuario.getRol().equals("TRIPULANTE")) {
            TripulanteController tripuCtrl = loader.getController();
            // PASAMOS EL USUARIO: El tripulante lo necesita para ver su propia info
            tripuCtrl.setUsuarioLogueado(usuario);
        }

        // Ejecutar el cambio de escena en la ventana actual
        Stage stage = (Stage) txtUsuario.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Navigare - Panel de " + usuario.getRol());
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