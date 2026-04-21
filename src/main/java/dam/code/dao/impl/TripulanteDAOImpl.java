package dam.code.dao.impl;

import dam.code.config.DatabaseConfig;
import dam.code.dao.TripulanteDAO;
import dam.code.exceptions.TripulanteException;
import dam.code.models.Tripulante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TripulanteDAOImpl implements TripulanteDAO {

    @Override
    public void registrar(Tripulante tripulante) throws TripulanteException {
        String sql = "INSERT INTO tripulantes (nombre, rol, id_buque) VALUES (?, ?, ?)";

        try (Connection conexion = DatabaseConfig.getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, tripulante.getNombre());
            ps.setString(2, tripulante.getRol());
            ps.setInt(3, tripulante.getIdBuque());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new TripulanteException("Error al registrar tripulante: " + e.getMessage());
        }
    }

    @Override
    public List<Tripulante> listarTodo() throws TripulanteException {
        List<Tripulante> lista = new ArrayList<>();
        String sql = "SELECT * FROM tripulantes";

        try (Connection con = DatabaseConfig.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Tripulante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("rol"),
                        rs.getInt("id_buque")
                ));
            }

        } catch (SQLException e) {
            throw new TripulanteException("Error al listar tripulantes: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<Tripulante> obtenerTripulantesPorBuque(int idBuque) throws TripulanteException {
        List<Tripulante> tripulantes = new ArrayList<>();
        // Ejemplo de INNER JOIN
        String sql = """
                SELECT t.id, t.nombre, t.rol, b.nombre AS nombre_buque
                FROM tripulantes t
                INNER JOIN buques b ON t.id_buque = b.id
                WHERE t.id_buque = ?
                """;

        try (Connection conexion = DatabaseConfig.getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idBuque);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Tripulante t = new Tripulante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("rol"),
                        idBuque
                );
                // Si el modelo tiene el nombre del buque, lo asigna aquí
                tripulantes.add(t);
            }

        } catch (SQLException e) {
            throw new TripulanteException("Error al obtener tripulación del buque: " + e.getMessage());
        }
        return tripulantes;
    }

    @Override
    public void eliminar(int id) throws TripulanteException {
        String sql = "DELETE FROM tripulantes WHERE id = ?";

        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new TripulanteException("No se pudo eliminar al tripulante: " + e.getMessage());
        }
    }
}