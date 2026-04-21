package dam.code.models;

import dam.code.models.utils.EstadoBuque;

public class Buque {

    private int id;
    private String nombre;
    private EstadoBuque estado;

    // Constructor vacío (obligatorio para frameworks y DAOs)
    public Buque() {
    }

    // Constructor con parámetros (útil para el registro)
    public Buque(int id, String nombre, EstadoBuque estado) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
    }

    // Getters y Setters (Cruciales para las tablas de JavaFX)

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

    public EstadoBuque getEstado() {
        return estado;
    }

    public void setEstado(EstadoBuque estado) {
        this.estado = estado;
    }
}