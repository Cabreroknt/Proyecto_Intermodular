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
        String sql = "INSERT INTO buques (nombre, tipo, capacidad, estado) VALUES (?, ?, ?, ?)";
        try (Connection conexion = DatabaseConfig.getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, buque.getNombre());
            ps.setString(2, "Carga"); // Valor por defecto o añadir al modelo
            ps.setInt(3, 500);        // Valor por defecto o añadir al modelo
            ps.setString(4, buque.getEstado().getMensaje());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new BuqueException("Error al registrar buque: " + e.getMessage());
        }
    }

    @Override
    public List<Buque> listar() throws BuqueException {
        List<Buque> buques = new ArrayList<>();
        String sql = "SELECT id_buque, nombre, estado FROM buques"; // Usamos los nombres de columna del SQL

        try (Connection con = DatabaseConfig.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Buque buque = new Buque();
                buque.setId(rs.getInt("id_buque"));
                buque.setNombre(rs.getString("nombre"));

                String estadoRaw = rs.getString("estado");
                if (estadoRaw != null) {
                    // 1. Convertimos a Mayúsculas y quitamos espacios
                    String procesado = estadoRaw.toUpperCase().trim();

                    // 2. Buscamos palabras clave para evitar fallos por tildes
                    if (procesado.contains("REPARA")) {
                        buque.setEstado(EstadoBuque.EN_REPARACION);
                    } else if (procesado.contains("PUERTO")) {
                        buque.setEstado(EstadoBuque.EN_PUERTO);
                    } else if (procesado.contains("NAVEGA")) {
                        buque.setEstado(EstadoBuque.NAVEGANDO);
                    } else {
                        // Intento final por si es un estado nuevo sin tildes
                        try {
                            buque.setEstado(EstadoBuque.valueOf(procesado.replace(" ", "_")));
                        } catch (Exception e) {
                            buque.setEstado(EstadoBuque.EN_PUERTO); // Fallback seguro
                        }
                    }
                }
                buques.add(buque);
            }
        } catch (SQLException e) {
            throw new BuqueException("Error al listar buques: " + e.getMessage());
        }
        return buques;
    }

    @Override
    public void actualizarEstado(int id, String nuevoEstado) throws BuqueException {
        String sql = "UPDATE buques SET estado = ? WHERE id_buque = ?";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new BuqueException("Error al actualizar: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) throws BuqueException {
        String sql = "DELETE FROM buques WHERE id_buque = ?";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new BuqueException("Error al eliminar: " + e.getMessage());
        }
    }
}