package dam.code.controller;

import dam.code.service.BuqueService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtUsuario;

    // El servicio se suele inicializar o pasar desde el AppMain
    private BuqueService buqueService = new BuqueService();

    @FXML
    public void handleLogin() {
        if (!validarCampos()) {
            mostrarError("El campo de usuario es obligatorio");
            return;
        }

        String user = txtUsuario.getText();

        try {
            String fxmlPath = "";
            String titulo = "";

            if (user.equalsIgnoreCase("admin")) {
                fxmlPath = "/view/AdminView.fxml";
                titulo = "Panel de Administración";
            } else if (user.equalsIgnoreCase("capitan")) {
                fxmlPath = "/view/CapitanView.fxml";
                titulo = "Panel del Capitán";
            } else {
                mostrarError("Usuario no reconocido");
                return;
            }

            // CAMBIAR DE ESCENA
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Aquí es donde inyectas el service al siguiente controlador
            if (user.equalsIgnoreCase("admin")) {
                AdminController controller = loader.getController();
                controller.setBuqueService(buqueService); // Le pasas el "motor"
            } else {
                CapitanController controller = loader.getController();
                controller.setBuqueService(buqueService);
            }

            // Configurar y mostrar el Stage
            Stage stage = (Stage) txtUsuario.getScene().getWindow();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            // stage.setResizable(false); // fijar tamaño

        } catch (IOException e) {
            mostrarError("Error al cargar la vista: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean validarCampos() {
        return !txtUsuario.getText().isEmpty();
    }

    private void mostrarError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}