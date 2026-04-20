package dam.code.service;

import dam.code.models.Buque;
import java.util.ArrayList;
import java.util.List;

public class BuqueService {

    // metodo faltante a AdminController
    public List<Buque> obtenerTodosLosBuques() {
        List<Buque> listaMock = new ArrayList<>();

        // Barcos de prueba (el orden debe coincidir con el constructor de Buque)
        // Suponiendo: id, nombre, modeloId, capacidad, estado

        //---------PLACE HOLDER DEBE BORRARSE AL FINALIZAR EL PROGRAMA-----------------//
        listaMock.add(new Buque(1, "Estrella del Mar", 101, 500, "En puerto"));
        listaMock.add(new Buque(2, "Poseidón", 102, 1200, "En navegación"));
        listaMock.add(new Buque(3, "Titán", 101, 800, "Mantenimiento"));
        //---------PLACE HOLDER DEBE BORRARSE AL FINALIZAR EL PROGRAMA-----------------//

        return listaMock;
    }
}