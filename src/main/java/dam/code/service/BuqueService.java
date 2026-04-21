package dam.code.service;

import dam.code.dao.BuqueDAO;
import dam.code.dao.impl.BuqueDAOImpl;
import dam.code.exceptions.BuqueException;
import dam.code.models.Buque;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BuqueService {

    // Instanciamos la implementación como en PeliculaService
    private final BuqueDAO buqueDAO = new BuqueDAOImpl();

    /**
     * Obtiene los buques en formato ObservableList para la UI
     */
    public ObservableList<Buque> obtenerTodosLosBuques() throws BuqueException {
        // Transformamos la List del DAO en ObservableList
        return FXCollections.observableArrayList(buqueDAO.listar());
    }

    /**
     * Registra un buque con validaciones previas
     */
    public void agregarBuque(Buque buque) throws BuqueException {
        validarBuque(buque);
        buqueDAO.registrar(buque);
    }

    /**
     * Lógica de validación antes de ir a la DB
     */
    private void validarBuque(Buque buque) throws BuqueException {
        if (buque.getNombre() == null || buque.getNombre().length() < 1) {
            throw new BuqueException("El nombre del buque debe tener al menos 1 caracteres.");
        }

        if (buque.getEstado() == null) {
            throw new BuqueException("El buque debe tener un estado asignado.");
        }
    }

    /**
     * Método para actualizar estado (útil para el Capitán)
     */
    public void actualizarEstadoBuque(int id, String nuevoEstado) throws BuqueException {
        buqueDAO.actualizarEstado(id, nuevoEstado);
    }
}