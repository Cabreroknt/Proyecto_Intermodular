package dam.code.dao.impl;

import dam.code.config.DatabaseConfig;
import dam.code.dao.UsuarioDAO;
import dam.code.models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public Usuario login(String user, String pass) {
        // La consulta busca por nombre y contraseña
        String sql = "SELECT id_usuario, username, password, rol, id_buque FROM usuarios WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user);
            ps.setString(2, pass);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Creamos el objeto Usuario con los datos de la DB
                    // Usamos getObject para id_buque porque puede ser NULL (para el Admin)
                    Integer idBuque = (rs.getObject("id_buque") != null) ? rs.getInt("id_buque") : null;

                    return new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("rol"),
                            idBuque
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en el login: " + e.getMessage());
        }
        return null; // Si no hay coincidencias o hay error, devuelve null
    }

    @Override
    public void registrar(Usuario u) {
        String sql = "INSERT INTO usuarios (username, password, rol, cargo, id_buque) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getRol());
            // Aquí podrías añadir campos adicionales si tu modelo Usuario los tiene
            ps.setString(4, "Tripulante");

            if (u.getIdBuque() != null) {
                ps.setInt(5, u.getIdBuque());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }

            ps.executeUpdate();
            System.out.println("Usuario registrado correctamente en DB.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}