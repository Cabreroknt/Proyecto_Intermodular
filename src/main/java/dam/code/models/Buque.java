package dam.code.models;

public class Buque {
    private int id;
    private String nombre;
    private int modeloId;
    private int capacidad;
    private String estado;

    // 1. Constructor vacío
    public Buque() {
    }

    // 2. Constructor lleno (El que necesita el Service)
    public Buque(int id, String nombre, int modeloId, int capacidad, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.modeloId = modeloId;
        this.capacidad = capacidad;
        this.estado = estado;
    }

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

    public int getModeloId() {
        return modeloId;
    }

    public void setModeloId(int modeloId) {
        this.modeloId = modeloId;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}