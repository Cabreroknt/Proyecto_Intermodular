package dam.code.controller;

import dam.code.dao.RutaDAO;
import dam.code.dao.impl.RutaDAOImpl;
import dam.code.dao.impl.ViajeDAOImpl;
import dam.code.exceptions.BuqueException;
import dam.code.models.*;
import dam.code.models.utils.EstadoBuque;
import dam.code.service.BuqueService;
import dam.code.service.TripulanteService;
import dam.code.service.UsuarioService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

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

    // --- TABLA DE RUTAS ---
    @FXML private TableView<Ruta> tablaRutas;
    @FXML private TableColumn<Ruta, String> colOrigen;
    @FXML private TableColumn<Ruta, String> colDestino;
    @FXML private TableColumn<Ruta, Integer> colDistancia;

    // --- TABLA DE VIAJES ---
    @FXML private TableView<Viaje> tablaViajes;
    @FXML private TableColumn<Viaje, Integer> colViajeBarco;
    @FXML private TableColumn<Viaje, Integer> colViajeRuta;
    @FXML private TableColumn<Viaje, LocalDate> colViajeFecha;

    // --- SERVICIOS Y DAOS ---
    private BuqueService buqueService;
    private TripulanteService tripulanteService;
    private UsuarioService usuarioService;
    private final RutaDAO rutaDAO = new RutaDAOImpl();
    private final ViajeDAOImpl viajeDAO = new ViajeDAOImpl();

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

        // Mapeo de columnas de Rutas
        colOrigen.setCellValueFactory(new PropertyValueFactory<>("origen"));
        colDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        colDistancia.setCellValueFactory(new PropertyValueFactory<>("distanciaKm"));

        // Mapeo de columnas de Viajes
        colViajeBarco.setCellValueFactory(new PropertyValueFactory<>("idBuque"));
        colViajeRuta.setCellValueFactory(new PropertyValueFactory<>("idRuta"));
        colViajeFecha.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));
    }

    // --- INYECCIÓN DE SERVICIOS ---
    public void setBuqueService(BuqueService s) { this.buqueService = s; cargarDatosBuques(); }
    public void setTripulanteService(TripulanteService s) { this.tripulanteService = s; cargarDatosTripulantes(); }
    public void setUsuarioService(UsuarioService s) { this.usuarioService = s; }

    // --- ACCIÓN: NUEVO BUQUE ---
    @FXML
    private void handleNuevoBuque(ActionEvent event) {
        Dialog<Buque> dialog = new Dialog<>();
        dialog.setTitle("Nuevo Buque");
        ButtonType guardarBtn = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardarBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20));

        TextField txtNombre = new TextField();
        ComboBox<EstadoBuque> cbEstado = new ComboBox<>(FXCollections.observableArrayList(EstadoBuque.values()));
        cbEstado.setValue(EstadoBuque.EN_PUERTO);

        grid.add(new Label("Nombre:"), 0, 0); grid.add(txtNombre, 1, 0);
        grid.add(new Label("Estado:"), 0, 1); grid.add(cbEstado, 1, 1);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> (btn == guardarBtn) ? new Buque(0, txtNombre.getText(), cbEstado.getValue()) : null);

        dialog.showAndWait().ifPresent(b -> {
            try {
                buqueService.agregarBuque(b);
                cargarDatosBuques(); // Refrescar tabla
            } catch (BuqueException e) { mostrarAlerta("Error", e.getMessage()); }
        });
    }

    // --- ACCIÓN: NUEVO TRIPULANTE ---
    @FXML
    private void handleNuevoTripulante(ActionEvent event) {
        Dialog<Tripulante> dialog = new Dialog<>();
        dialog.setTitle("Nuevo Tripulante");
        ButtonType regBtn = new ButtonType("Registrar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(regBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20));

        TextField txtNombre = new TextField();
        TextField txtRol = new TextField();
        ComboBox<Buque> cbBuque = new ComboBox<>();
        try { cbBuque.setItems(buqueService.obtenerTodosLosBuques()); } catch (Exception e) {}

        grid.add(new Label("Nombre:"), 0, 0); grid.add(txtNombre, 1, 0);
        grid.add(new Label("Rol:"), 0, 1); grid.add(txtRol, 1, 1);
        grid.add(new Label("Asignar Buque:"), 0, 2); grid.add(cbBuque, 1, 2);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> (btn == regBtn && cbBuque.getValue() != null)
                ? new Tripulante(0, txtNombre.getText(), txtRol.getText(), cbBuque.getValue().getId()) : null);

        dialog.showAndWait().ifPresent(t -> {
            try {
                tripulanteService.agregarTripulante(t);
                cargarDatosTripulantes(); // Refrescar tabla
            } catch (Exception e) { mostrarAlerta("Error", "Fallo al registrar tripulante."); }
        });
    }

    // --- ACCIÓN: NUEVA RUTA ---
    @FXML
    private void handleNuevaRuta(ActionEvent event) {
        Dialog<Ruta> dialog = new Dialog<>();
        dialog.setTitle("Crear Nueva Ruta");
        ButtonType guardarBtn = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardarBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20));
        TextField ori = new TextField(); TextField des = new TextField(); TextField dist = new TextField();

        grid.add(new Label("Origen:"), 0, 0); grid.add(ori, 1, 0);
        grid.add(new Label("Destino:"), 0, 1); grid.add(des, 1, 1);
        grid.add(new Label("Distancia (Km):"), 0, 2); grid.add(dist, 1, 2);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == guardarBtn) {
                return new Ruta(0, ori.getText(), des.getText(), Integer.parseInt(dist.getText()));
            }
            return null;
        });

        dialog.showAndWait().ifPresent(r -> {
            rutaDAO.registrar(r);
            cargarDatosRutas(); // Refrescar tabla
        });
    }

    // --- ACCIÓN: ASIGNAR VIAJE ---
    @FXML
    private void handleAsignarViaje(ActionEvent event) {
        Dialog<Viaje> dialog = new Dialog<>();
        dialog.setTitle("Asignar Viaje a Buque");
        ButtonType asignarBtn = new ButtonType("Asignar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(asignarBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20));

        ComboBox<Buque> cbB = new ComboBox<>();
        ComboBox<Ruta> cbR = new ComboBox<>();
        DatePicker dp = new DatePicker(LocalDate.now());

        try { cbB.setItems(buqueService.obtenerTodosLosBuques()); } catch(Exception e){}
        cbR.setItems(FXCollections.observableArrayList(rutaDAO.listarTodas()));

        grid.add(new Label("Buque:"), 0, 0); grid.add(cbB, 1, 0);
        grid.add(new Label("Ruta:"), 0, 1); grid.add(cbR, 1, 1);
        grid.add(new Label("Fecha:"), 0, 2); grid.add(dp, 1, 2);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> (btn == asignarBtn && cbB.getValue() != null && cbR.getValue() != null)
                ? new Viaje(0, cbB.getValue().getId(), cbR.getValue().getIdRuta(), dp.getValue()) : null);

        dialog.showAndWait().ifPresent(v -> {
            viajeDAO.asignarViaje(v);
            cargarDatosViajes(); // Refrescar tabla
        });
    }

    // --- MÉTODOS DE CARGA ---
    private void cargarDatosBuques() {
        try { if(buqueService != null) tablaBuques.setItems(buqueService.obtenerTodosLosBuques()); }
        catch (Exception e) { e.printStackTrace(); }
    }

    private void cargarDatosTripulantes() {
        if(tripulanteService != null) tablaTripulantes.setItems(tripulanteService.obtenerTodosLosTripulantes());
    }

    private void cargarDatosRutas() {
        tablaRutas.setItems(FXCollections.observableArrayList(rutaDAO.listarTodas()));
    }

    private void cargarDatosViajes() {
        tablaViajes.setItems(FXCollections.observableArrayList(viajeDAO.listarViajesProgramados()));
    }

    @FXML
    private void handleCerrarSesion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
            Parent root = loader.load();
            LoginController loginCtrl = loader.getController();
            loginCtrl.setUsuarioService(usuarioService);
            loginCtrl.setBuqueService(buqueService);
            loginCtrl.setTripulanteService(tripulanteService);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}