package dam.code.models;

public class Tripulante {

    private int id;
    private String nombre;
    private String rol;
    private int idBuque; // Clave foránea para la relación con la tabla buques

    // Constructor vacío (necesario para DAOs)
    public Tripulante() {
    }

    // Constructor completo (útil para el listar)
    public Tripulante(int id, String nombre, String rol, int idBuque) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
        this.idBuque = idBuque;
    }

    // Getters y Setters (Obligatorios para PropertyValueFactory)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getIdBuque() {
        return idBuque;
    }

    public void setIdBuque(int idBuque) {
        this.idBuque = idBuque;
    }
}