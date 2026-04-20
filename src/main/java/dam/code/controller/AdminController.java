package dam.code.controller;

import dam.code.models.Buque;
import dam.code.service.BuqueService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminController {

    @FXML private TableView<Buque> tablaBuques;
    @FXML private TableColumn<Buque, Integer> colId;
    @FXML private TableColumn<Buque, String> colNombre;
    @FXML private TableColumn<Buque, String> colEstado;

    private BuqueService service;

    // metodo que llama el LoginController
    public void setBuqueService(BuqueService service) {
        this.service = service;
        cargarDatos(); // Carga los datos en cuanto recibe el servicio
    }

    @FXML
    public void initialize() {
        // Cómo se mapean las columnas con el modelo Buque
        // El nombre entre comillas debe coincidir EXACTO con el nombre del atributo en la clase Buque
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }

    private void cargarDatos() {
        if (service != null) {
            ObservableList<Buque> lista = FXCollections.observableArrayList(service.obtenerTodosLosBuques());
            tablaBuques.setItems(lista);
        }
    }
}