package dam.code.controller;

import dam.code.exceptions.BuqueException;
import dam.code.models.Buque;
import dam.code.models.Tripulante;
import dam.code.service.BuqueService;
import dam.code.service.TripulanteService;
import dam.code.service.UsuarioService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {

    // --- TABLA DE BUQUES ---
    @FXML private TableView<Buque> tablaBuques;
    @FXML private TableColumn<Buque, Integer> colIdBuque;
    @FXML private TableColumn<Buque, String> colNombreBuque;
    @FXML private TableColumn<Buque, String> colEstadoBuque;

    // --- TABLA DE TRIPULANTES ---
    @FXML private TableView<Tripulante> tablaTripulantes;
    @FXML private TableColumn<Tripulante, Integer> colIdTrip;
    @FXML private TableColumn<Tripulante, String> colNombreTrip;
    @FXML private TableColumn<Tripulante, String> colRolTrip;

    private UsuarioService usuarioService;
    private BuqueService buqueService;
    private TripulanteService tripulanteService;

    /**
     * Se ejecuta automáticamente al cargar el FXML.
     * Vincula las columnas de las tablas con los atributos de los modelos.
     */
    @FXML
    public void initialize() {
        // Mapeo de columnas de Buques
        colIdBuque.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombreBuque.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEstadoBuque.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Mapeo de columnas de Tripulantes
        colIdTrip.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombreTrip.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colRolTrip.setCellValueFactory(new PropertyValueFactory<>("rol"));
    }

    // --- INYECCIÓN DE SERVICIOS ---

    public void setBuqueService(BuqueService service) {
        this.buqueService = service;
        cargarDatosBuques();
    }

    public void setTripulanteService(TripulanteService service) {
        this.tripulanteService = service;
        cargarDatosTripulantes();
    }

    // --- ACCIONES FXML (Botones) ---

    @FXML
    private void handleCerrarSesion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
            Parent root = loader.load();

            LoginController loginCtrl = loader.getController();
            // Ahora 'this.usuarioService' ya está resuelto
            loginCtrl.setUsuarioService(this.usuarioService);
            loginCtrl.setBuqueService(this.buqueService);
            loginCtrl.setTripulanteService(this.tripulanteService);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNuevoBuque(ActionEvent event) {
        System.out.println("Botón 'Añadir Buque' pulsado.");
        // Aquí puedes abrir un diálogo para crear un buque
    }

    @FXML
    private void handleNuevoTripulante(ActionEvent event) {
        System.out.println("Botón 'Añadir Tripulante' pulsado.");
        // Aquí puedes abrir un diálogo para crear un tripulante
    }

    // --- LÓGICA DE CARGA DE DATOS ---

    private void cargarDatosBuques() {
        if (buqueService != null) {
            try {
                ObservableList<Buque> lista = buqueService.obtenerTodosLosBuques();
                tablaBuques.setItems(lista);
            } catch (BuqueException e) {
                System.err.println("Error al cargar buques: " + e.getMessage());
            }
        }
    }

    private void cargarDatosTripulantes() {
        if (tripulanteService != null) {
            ObservableList<Tripulante> lista = FXCollections.observableArrayList(tripulanteService.obtenerTodosLosTripulantes());
            tablaTripulantes.setItems(lista);
        }
    }
    public void setUsuarioService(UsuarioService service) {
        this.usuarioService = service;
    }
}