package dam.code.dao.impl;

import dam.code.config.DatabaseConfig;
import dam.code.dao.RutaDAO;
import dam.code.models.Ruta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RutaDAOImpl implements RutaDAO {

    @Override
    public void registrar(Ruta ruta) {
        String sql = "INSERT INTO rutas (origen, destino, distancia_km) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ruta.getOrigen());
            ps.setString(2, ruta.getDestino());
            ps.setInt(3, ruta.getDistanciaKm());

            ps.executeUpdate();
            System.out.println("Ruta guardada correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al registrar ruta: " + e.getMessage());
        }
    }

    @Override
    public List<Ruta> listarTodas() {
        List<Ruta> rutas = new ArrayList<>();
        String sql = "SELECT * FROM rutas";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                rutas.add(new Ruta(
                        rs.getInt("id_ruta"),
                        rs.getString("origen"),
                        rs.getString("destino"),
                        rs.getInt("distancia_km")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar rutas: " + e.getMessage());
        }
        return rutas;
    }

    @Override
    public void eliminar(int idRuta) {
        String sql = "DELETE FROM rutas WHERE id_ruta = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idRuta);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al eliminar ruta: " + e.getMessage());
        }
    }
}