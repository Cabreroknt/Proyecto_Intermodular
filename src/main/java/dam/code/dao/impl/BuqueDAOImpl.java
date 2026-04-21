package dam.code.dao.impl;

import dam.code.config.DatabaseConfig;
import dam.code.dao.BuqueDAO;
import dam.code.exceptions.BuqueException;
import dam.code.models.Buque;
import dam.code.models.utils.EstadoBuque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuqueDAOImpl implements BuqueDAO {

    @Override
    public void registrar(Buque buque) throws BuqueException {
        String sql = "INSERT INTO buques (nombre, estado) VALUES (?, ?)";

        try (Connection conexion = DatabaseConfig.getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, buque.getNombre());
            ps.setString(2, buque.getEstado().name()); // Guarda el nombre del Enum

            ps.executeUpdate();

        } catch (SQLException e) {
            // Encapsula la SQLException en nuestra excepción propia
            throw new BuqueException("Error al registrar buque: " + e.getMessage());
        }
    }

    @Override
    public List<Buque> listar() throws BuqueException {
        List<Buque> buques = new ArrayList<>();
        String sql = "SELECT * FROM buques";

        try (Connection con = DatabaseConfig.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Buque buque = new Buque();
                buque.setId(rs.getInt("id"));
                buque.setNombre(rs.getString("nombre"));
                // Convierte el String de la DB al Enum de Java
                buque.setEstado(EstadoBuque.valueOf(rs.getString("estado").toUpperCase()));

                buques.add(buque);
            }

        } catch (SQLException e) {
            throw new BuqueException("Error al listar buques: " + e.getMessage());
        }

        return buques;
    }

    @Override
    public void actualizarEstado(int id, String nuevoEstado) throws BuqueException {
        String sql = "UPDATE buques SET estado = ? WHERE id = ?";

        try (Connection conexion = DatabaseConfig.getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new BuqueException("Error al actualizar estado: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) throws BuqueException {
        String sql = "DELETE FROM buques WHERE id = ?";

        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new BuqueException("Error al eliminar buque: " + e.getMessage());
        }
    }
}