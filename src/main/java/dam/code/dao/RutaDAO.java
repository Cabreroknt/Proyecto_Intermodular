package dam.code.dao;

import dam.code.models.Ruta;
import java.util.List;

public interface RutaDAO {
    void registrar(Ruta ruta);
    List<Ruta> listarTodas();
    void eliminar(int idRuta);
}