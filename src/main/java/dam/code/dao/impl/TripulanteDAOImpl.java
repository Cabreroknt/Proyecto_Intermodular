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
    public void registrar(Tripulante t) throws TripulanteException {
        // En tu DB, registrar un tripulante es insertar en la tabla usuarios
        String sql = "INSERT INTO usuarios (username, password, rol, cargo, id_buque) VALUES (?, ?, 'TRIPULANTE', ?, ?)";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getNombre());
            ps.setString(2, "1234"); // Password por defecto
            ps.setString(3, t.getRol());
            ps.setInt(4, t.getIdBuque());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new TripulanteException("Error al registrar: " + e.getMessage());
        }
    }

    @Override
    public List<Tripulante> listarTodo() throws TripulanteException {
        List<Tripulante> lista = new ArrayList<>();
        // CORRECCIÓN: La tabla se llama usuarios
        String sql = "SELECT id_usuario, username, cargo, id_buque FROM usuarios WHERE rol = 'TRIPULANTE'";

        try (Connection con = DatabaseConfig.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Tripulante t = new Tripulante();
                t.setId(rs.getInt("id_usuario"));
                t.setNombre(rs.getString("username"));
                t.setRol(rs.getString("cargo"));
                t.setIdBuque(rs.getInt("id_buque"));
                lista.add(t);
            }
        } catch (SQLException e) {
            throw new TripulanteException("Error al listar tripulantes: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<Tripulante> obtenerTripulantesPorBuque(int idBuque) throws TripulanteException {
        List<Tripulante> lista = new ArrayList<>();
        String sql = "SELECT id_usuario, username, cargo FROM usuarios WHERE id_buque = ? AND rol = 'TRIPULANTE'";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idBuque);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Tripulante(rs.getInt("id_usuario"), rs.getString("username"), rs.getString("cargo"), idBuque));
            }
        } catch (SQLException e) {
            throw new TripulanteException(e.getMessage());
        }
        return lista;
    }

    @Override
    public void eliminar(int id) throws TripulanteException {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new TripulanteException(e.getMessage());
        }
    }
}