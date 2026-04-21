package dam.code.service;

import dam.code.dao.UsuarioDAO;
import dam.code.dao.impl.UsuarioDAOImpl;
import dam.code.models.Usuario;

public class UsuarioService {

    // Instanciamos el DAO (Asegúrate de tener la interfaz UsuarioDAO y su Impl)
    private final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    /**
     * Gestiona el inicio de sesión.
     * @param username Nombre de usuario introducido en el formulario.
     * @param password Contraseña introducida.
     * @return El objeto Usuario completo si es válido, o null si fallan las credenciales.
     */
    public Usuario login(String username, String password) {
        // Validaciones básicas de negocio (estilo Williams)
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return null;
        }

        // Llamamos al DAO para que consulte en la base de datos
        return usuarioDAO.login(username, password);
    }

    /**
     * Ejemplo de registro de usuario (para la parte de "dar de alta")
     */
    public void registrarNuevoUsuario(Usuario u) {
        // Aquí podrías añadir lógica para guardar en JSON también si te lo piden
        usuarioDAO.registrar(u);
    }
}