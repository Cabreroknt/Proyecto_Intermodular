package dam.code.dao.impl;

import dam.code.config.DatabaseConfig;
import dam.code.models.Viaje;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViajeDAOImpl {

    /**
     * Método para insertar una nueva asignación en la base de datos
     */
    public void asignarViaje(Viaje viaje) {
        String sql = "INSERT INTO asignacion_viajes (id_buque, id_ruta, fecha_salida) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, viaje.getIdBuque());
            ps.setInt(2, viaje.getIdRuta());
            ps.setDate(3, Date.valueOf(viaje.getFechaSalida()));

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al registrar asignación: " + e.getMessage());
        }
    }

    /**
     * ESTE ES EL MÉTODO QUE TE FALTA
     * Recupera todas las asignaciones de la tabla para mostrarlas en la TableView
     */
    public List<Viaje> listarViajesProgramados() {
        List<Viaje> lista = new ArrayList<>();
        String sql = "SELECT id_asignacion, id_buque, id_ruta, fecha_salida FROM asignacion_viajes";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Viaje v = new Viaje(
                        rs.getInt("id_asignacion"),
                        rs.getInt("id_buque"),
                        rs.getInt("id_ruta"),
                        rs.getDate("fecha_salida").toLocalDate()
                );
                lista.add(v);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar viajes: " + e.getMessage());
        }
        return lista;
    }
}