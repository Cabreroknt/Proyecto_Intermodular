package dam.code.controller;

import dam.code.exceptions.BuqueException;
import dam.code.models.Buque;
import dam.code.models.Tripulante;
import dam.code.service.BuqueService;
import dam.code.service.TripulanteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    private BuqueService buqueService;
    private TripulanteService tripulanteService;

    /**
     * El "initialize" se ejecuta al cargar el FXML.
     * Aquí SOLO configura qué atributo va en cada columna.
     */
    @FXML
    public void initialize() {
        // Configuración columnas Buques (nombres deben coincidir con atributos en la clase Buque)
        colIdBuque.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombreBuque.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEstadoBuque.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Configuración columnas Tripulantes
        colIdTrip.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombreTrip.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colRolTrip.setCellValueFactory(new PropertyValueFactory<>("rol"));

        System.out.println("AdminController inicializado: Columnas vinculadas.");
    }

    // --- MÉTODOS DE INYECCIÓN (Los que llama el LoginController) ---

    public void setBuqueService(BuqueService service) {
        this.buqueService = service;
        cargarDatosBuques();
    }

    public void setTripulanteService(TripulanteService service) {
        this.tripulanteService = service;
        cargarDatosTripulantes();
    }

    // --- LÓGICA DE CARGA ---

    private void cargarDatosBuques() {
        if (buqueService != null) {
            try {

                ObservableList<Buque> lista = buqueService.obtenerTodosLosBuques();
                tablaBuques.setItems(lista);
                System.out.println("Datos de buques cargados.");
            } catch (BuqueException e) {
                System.err.println("Error al cargar buques: " + e.getMessage());
            }
        }
    }

    private void cargarDatosTripulantes() {
        if (tripulanteService != null) {
            ObservableList<Tripulante> lista = FXCollections.observableArrayList(tripulanteService.obtenerTodosLosTripulantes());
            tablaTripulantes.setItems(lista);
            System.out.println("Datos de tripulantes cargados en la tabla.");
        }
    }
}