package dam.code;

import dam.code.controller.LoginController;
import dam.code.service.BuqueService;
import dam.code.service.TripulanteService;
import dam.code.service.UsuarioService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // 1. Instanciamos todos los servicios una sola vez
        // Estos servicios viajarán por toda la jerarquía de ventanas
        UsuarioService usuarioService = new UsuarioService();
        BuqueService buqueService = new BuqueService();
        TripulanteService tripulanteService = new TripulanteService();

        // 2. Cargamos el FXML del Login (punto de entrada)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
        Parent root = loader.load();

        // 3. Obtenemos el LoginController e INYECTAMOS los servicios
        LoginController controller = loader.getController();
        controller.setUsuarioService(usuarioService);
        controller.setBuqueService(buqueService);
        controller.setTripulanteService(tripulanteService);

        // 4. Configuración estética (Estilo Williams: fija y limpia)
        stage.setTitle("Navigare - Sistema de Gestión Marítima");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setWidth(400);
        stage.setHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}