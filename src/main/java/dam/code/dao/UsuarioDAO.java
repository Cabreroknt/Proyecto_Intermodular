package dam.code.dao;

import dam.code.models.Usuario;

public interface UsuarioDAO {
    /**
     * Valida las credenciales y devuelve el Usuario con su Rol e ID de buque
     */
    Usuario login(String user, String pass);

    /**
     * Registra un nuevo usuario/tripulante en la base de datos
     */
    void registrar(Usuario u);
}