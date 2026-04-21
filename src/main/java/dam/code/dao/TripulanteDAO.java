package dam.code.dao;

import dam.code.exceptions.TripulanteException;
import dam.code.models.Tripulante;
import java.util.List;

public interface TripulanteDAO {
    void registrar(Tripulante tripulante) throws TripulanteException;
    List<Tripulante> listarTodo() throws TripulanteException;
    List<Tripulante> obtenerTripulantesPorBuque(int idBuque) throws TripulanteException;
    void eliminar(int id) throws TripulanteException;
}