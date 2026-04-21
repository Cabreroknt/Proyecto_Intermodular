package dam.code.dao;

import dam.code.exceptions.BuqueException;
import dam.code.models.Buque;
import java.util.List;

public interface BuqueDAO {
    void registrar(Buque buque) throws BuqueException;
    List<Buque> listar() throws BuqueException;
    void actualizarEstado(int id, String nuevoEstado) throws BuqueException;
    void eliminar(int id) throws BuqueException;
}