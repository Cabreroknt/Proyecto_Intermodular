package dam.code.controller;

import dam.code.service.BuqueService;
import javafx.fxml.FXML;

public class CapitanController {

    private BuqueService service;

    // Metodo que "setea" el servicio desde el LoginController
    public void setBuqueService(BuqueService service) {
        this.service = service;
        // Aquí se puede llamar a cargar los datos específicos del capitán
    }

    @FXML
    public void initialize() {
        // Lógica inicial de la vista del capitán
    }
}
