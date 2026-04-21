package dam.code.service;

import dam.code.dao.TripulanteDAO;
import dam.code.dao.impl.TripulanteDAOImpl;
import dam.code.exceptions.TripulanteException;
import dam.code.models.Tripulante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TripulanteService {

    // Al igual que con las películas, instanciamos la implementación
    private final TripulanteDAO tripulanteDAO = new TripulanteDAOImpl();

    /**
     * Obtiene todos los tripulantes en un formato que JavaFX entiende
     */
    public ObservableList<Tripulante> obtenerTodosLosTripulantes() {
        try {
            return FXCollections.observableArrayList(tripulanteDAO.listarTodo());
        } catch (TripulanteException e) {
            System.err.println("Error en Service: " + e.getMessage());
            return FXCollections.observableArrayList(); // Lista vacía para evitar fallos en la UI
        }
    }

    /**
     * Agrega un tripulante con validaciones previas
     */
    public void agregarTripulante(Tripulante tripulante) throws TripulanteException {
        validarTripulante(tripulante);
        tripulanteDAO.registrar(tripulante);
    }

    /**
     * Ejemplo de validación al estilo
     */
    private void validarTripulante(Tripulante tripulante) throws TripulanteException {
        if (tripulante.getNombre() == null || tripulante.getNombre().length() < 1) {
            throw new TripulanteException("El nombre del tripulante es demasiado corto.");
        }
        if (tripulante.getRol() == null) {
            throw new TripulanteException("El tripulante debe tener un rol asignado.");
        }
    }

    /**
     * Filtra tripulantes por buque
     */
    public ObservableList<Tripulante> obtenerTripulacionPorBuque(int idBuque) {
        try {
            return FXCollections.observableArrayList(tripulanteDAO.obtenerTripulantesPorBuque(idBuque));
        } catch (TripulanteException e) {
            e.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }
}